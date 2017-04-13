package com.weitongming.myweb.controller;

import com.weitongming.myweb.model.BlogWithUserInfo;
import com.weitongming.myweb.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/4.
 */
@Controller
@RequestMapping("/")
public class BolgController {

    @Autowired
    private BlogService blogService;
    @RequestMapping("blog/")
    public String blog(ModelAndView modelAndView){
        List<BlogWithUserInfo> blogs =  blogService.getBlogListByTime(1);
        modelAndView.addObject("blogs",blogs);
        return "blog";
    }
}
