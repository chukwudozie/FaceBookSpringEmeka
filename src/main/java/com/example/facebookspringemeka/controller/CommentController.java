package com.example.facebookspringemeka.controller;

import com.example.facebookspringemeka.DTO.CommentDTO;
import com.example.facebookspringemeka.Models.Comment;
import com.example.facebookspringemeka.Models.Post;
import com.example.facebookspringemeka.Models.UserEntity;
import com.example.facebookspringemeka.ServiceImplementation.CommentServiceImpl;
import com.example.facebookspringemeka.ServiceImplementation.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {

    private CommentServiceImpl commentService;
    private PostServiceImpl postService;

    @Autowired
    public CommentController(CommentServiceImpl commentService, PostServiceImpl postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @RequestMapping(value = "/commentPost", method = RequestMethod.POST)
    public String addCommentToPost(@ModelAttribute("commentData")Comment comment, HttpServletRequest request,
                                   HttpSession session){
        System.out.println("In the controller");
        UserEntity user = (UserEntity) session.getAttribute("user");
        if(user==null) return "redirect:/";
        Long postId = Long.parseLong(request.getParameter("postId"));
        comment.setUser(user);
        if(commentService.createNewComment(user.getId(), postId,comment)){
            System.out.println("Comment Controller: Comment made");
         session.setAttribute("message","Comment successful");
        } else{
            System.out.println("Comment Controller: Comment not made");
            session.setAttribute("message","Comment not made");
        }
        return "redirect:/dashboard";
    }

    @RequestMapping(value = "/comment/{posts}", method = RequestMethod.POST)
    public String showCommentsForPost(@PathVariable("post")Long postId, Model model, HttpSession session){

        System.out.println("In the controller to show comments");
        UserEntity user = (UserEntity) session.getAttribute("user");
        if(user == null) return "redirect:/";

        Long post_Id = postId;

        List<CommentDTO> commentList = commentService.getAllCommentsForPost(post_Id);
        if(commentList.size() == 0){
            System.out.println("NO comments");
            return "redirect:/dashboard";
        }
        System.out.println("there are comments");
        model.addAttribute("commentData",commentList);
        model.addAttribute("user",user);
        return "comment";

    }
    @RequestMapping(value = "/editComment", method = RequestMethod.POST)
    public String editComment(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        Long postId = Long.parseLong(request.getParameter("postId"));
        Long userId = Long.parseLong(request.getParameter("userId"));
        Long commentId = Long.parseLong(request.getParameter("commentId"));
        String comment = request.getParameter("editedComment");

        UserEntity user = (UserEntity) session.getAttribute("user");

        if(user == null) return "redirect:/";

        if(commentService.editComment(commentId, user, postId, comment)){
            session.setAttribute("message", "Comment edited successfully");
        }else {
            session.setAttribute("message", "Failed to edit comment");
        }

        return "redirect:/comment/"+postId;
    }
}
