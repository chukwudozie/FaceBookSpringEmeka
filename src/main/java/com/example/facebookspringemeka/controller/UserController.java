package com.example.facebookspringemeka.controller;

import com.example.facebookspringemeka.DTO.PostDTO;
import com.example.facebookspringemeka.Models.Comment;
import com.example.facebookspringemeka.Models.Post;
import com.example.facebookspringemeka.Models.UserEntity;
import com.example.facebookspringemeka.ServiceImplementation.PostServiceImpl;
import com.example.facebookspringemeka.ServiceImplementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    private UserServiceImpl userService;
    private PostServiceImpl postService;

    @Autowired
    public UserController(UserServiceImpl userService, PostServiceImpl postService) {
        this.userService = userService;
        this.postService = postService;
    }

    /**
     *
     * @param model sets the view of the Index page to the user in session
     * @param request takes the login in and signUp request to the controller for processing
     * @param user accesses the index page for either signUp or Login
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String viewIndexPage(Model model, HttpServletRequest request, @ModelAttribute("user")UserEntity user){

        HttpSession session = request.getSession();
        session.removeAttribute("message");
        session.getAttribute("user");
        model.addAttribute("user",user);
        return "index";

    }

    /**
     *This method handles Registration of new Users to the Database
     *By Handling the Request Form for Registration from the Index Page
     * It Displays the appropriate message subsequently
     */
    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") UserEntity user,HttpServletRequest request){
        HttpSession  session= request.getSession();

        if(userService.registerNewUser(user)){
            session.setAttribute("mess","Registration Successful!!!");
        }
        else{
            session.setAttribute("mess","Invalid Registration, Email has been taken!!!");
        }
        return "redirect:/";
    }

    /**
     *This method checks if a user is already registerd
     * It grants access to the registered User
     * While it redirects User with incorrect credentials to the Index Page and displays error message
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginUser(@ModelAttribute("user") UserEntity user, HttpServletRequest request){

        UserEntity userFromDB = userService.getRegisteredUser(user.getEmail(),user.getPassword());
        HttpSession session = request.getSession();
        System.out.println(userFromDB);
        if(userFromDB!= null){
//            System.out.println("User "+session.getAttributeNames().toString()+"successfully logged in !!");
            session.setAttribute("user",userFromDB);
            return "redirect:/dashboard";
        } else{
            session.setAttribute("mess","Email or Password wrong");
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView showHomePage(HttpServletRequest request){
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        if(user == null){
            ModelAndView model = new ModelAndView("index");
            model.addObject("user", new UserEntity());
            return  model;
        }
        ModelAndView model = new ModelAndView("dashboard");
//        model.addObject("user",user);
        model.addObject("post", new Post());
        model.addObject("commentData", new Comment());

        List<PostDTO> allPosts = postService.getAllPostByUser(user);
        model.addObject("user",user);
        model.addObject("posts",allPosts);
        return  model;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        session.invalidate();

        return "redirect:/";
    }
}
