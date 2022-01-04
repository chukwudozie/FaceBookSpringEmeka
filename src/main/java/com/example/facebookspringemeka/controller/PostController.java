package com.example.facebookspringemeka.controller;

import com.example.facebookspringemeka.Models.Post;
import com.example.facebookspringemeka.Models.UserEntity;
import com.example.facebookspringemeka.ServiceImplementation.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class PostController {

    private PostServiceImpl postService;

    @Autowired
    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @RequestMapping(value = "/processPost", method = RequestMethod.POST)
    public String addUserPost(@ModelAttribute("post")Post post, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        UserEntity user =  (UserEntity) session.getAttribute("user");
//        System.out.println("User in post controller :" + user);
        session.setAttribute("user",user);
        System.out.println("User from session : " + user);
        if(user == null)
        {
            System.out.println("User from session is null");
            return "redirect:/";
        }
        try {
            System.out.println("There is a user from session");
            Part part = request.getPart("file");
            String imageName = part.getSubmittedFileName();
            post.setPostImagename(imageName);
            post.setUser(user);
            System.out.println(post.getUser());
            InputStream stream = part.getInputStream();
            String path = "/Users/mac/IdeaProjects/FaceBookSpringEmeka/src/main/resources/static/image"
                    + File.separator+post.getPostImagename();
            uploadFile(stream,path);
            System.out.println("file path set");
           if(postService.createNewPost(user.getId(), post)){
               System.out.println("Post successfully made");
               session.setAttribute("message","Post Successfully completed");

           } else {
               System.out.println("Error Uploading Post try again");
               session.setAttribute("message","Error Uploading Post try again");
           }


        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        return "redirect:/dashboard";
    }

    @RequestMapping(value = "/editPost", method = RequestMethod.POST)
    public String editUserPost(@ModelAttribute("post")Post post, HttpSession session){

        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null) return  "redirect:/";
        if(postService.editPost(user.getId(), post.getPostId(), post.getPostTitle(),post.getPostBody())){
            System.out.println("From edit: Update Successful");
            session.setAttribute("message","Post Edited Successfully");
        } else {
            System.out.println("From Edit Page:Couldn't Update Post");
            session.setAttribute("message","Error Editing Post");
        }
        return "redirect:/dashboard";
    }

    @RequestMapping(value = "/edit/{post}", method = RequestMethod.GET)
    public String showEditPage(@PathVariable("post")Long postId, Model model, HttpSession session){
        UserEntity user = (UserEntity) session.getAttribute("user");
        if(user == null) {
            System.out.println("No user in session for edit post");
            return "redirect:/";
        }
        List<Post> post = postService.getPostByID(postId);
        model.addAttribute("postData", post.get(0));
        model.addAttribute("user",user);
        System.out.println("Getting the edit Page");
        return "editpost";

    }

    @RequestMapping(value = "/deletePost", method = RequestMethod.GET)
    public String deletePost(HttpServletRequest request, HttpSession session){

        System.out.println("Deleting Process... delete Post controller");
        UserEntity user = (UserEntity) session.getAttribute("user");

        if(user == null) {
            System.out.println("From Controller: No User in session to Delete");
            return "redirect:/";
        }
        System.out.println("From controller: there is a user in session to delete");
        Long postId = Long.parseLong(request.getParameter("postId"));
        if(postService.deletePost(postId, user.getId())){
            System.out.println("From controller: Post Deleted!");
            session.setAttribute("message","Post successfully deleted");
        }
        else{
            System.out.println("From Controller: Error deleting Post");
            session.setAttribute("message", "Error deleting Post");
        }

        return "redirect:/dashboard";

    }

    private boolean uploadFile(InputStream stream, String path) {
        boolean test = false;

        try{
            byte[] byt = new byte[stream.available()];
            stream.read(byt);
            FileOutputStream fops = new FileOutputStream(path);
            fops.write(byt);
            fops.flush();
            fops.close();
            test = true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return test;

    }
}
