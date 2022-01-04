package com.example.facebookspringemeka.controller;

import com.example.facebookspringemeka.Models.UserEntity;
import com.example.facebookspringemeka.ServiceImplementation.LikeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PostLikesController {

    private LikeServiceImpl likeService;

    @Autowired
    public PostLikesController(LikeServiceImpl likeService) {
        this.likeService = likeService;
    }

    @RequestMapping(value = "/likePost",method = RequestMethod.POST)
    public @ResponseBody String likeAPost(HttpServletRequest request, HttpSession session){
        UserEntity user = (UserEntity) session.getAttribute("user");

        if(user == null) return "redirect:/";

        Long postId = Long.valueOf(request.getParameter("postId"));
        String likeAction = request.getParameter("action");
        if(likeService.likeAPost(user,postId,likeAction)){
            return "successful";
        } else{
            session.setAttribute("message","Server error");
        }
        return "";
    }
}
