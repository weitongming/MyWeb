package com.weitongming.myweb.controller;

import com.weitongming.myweb.model.PageBean;
import com.weitongming.myweb.model.Post;
import com.weitongming.myweb.model.Topic;
import com.weitongming.myweb.model.User;
import com.weitongming.myweb.service.PostService;
import com.weitongming.myweb.service.TopicService;
import com.weitongming.myweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/")
public class TopicController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;
    /**
     * 列出所有话题
     * @param model
     * @return
     */
    @RequestMapping("/listTopic.do")
    public String listTopic(Model model){
        List<Topic> topicList = topicService.listTopic();
        model.addAttribute("topicList",topicList);
        return "topic";
    }

    @RequestMapping("/topic/{type}-{page}")
    public String showTopicByType(Model model, HttpServletRequest request,
                                  @PathVariable("type") int type,@PathVariable("page") int page){
        //记录访问信息
        userService.record(request.getRequestURL(),request.getContextPath(),request.getRemoteAddr());
        //列出帖子
        PageBean<Post> pageBean = postService.listPostByTimeAndType(page,type);
        //列出用户
        List<User> userList = userService.listUserByTime();
        //列出活跃用户
        List<User> hotUserList = userService.listUserByHot();
        //向模型中添加数据
        model.addAttribute("pageBean",pageBean);
        model.addAttribute("type",type);
        model.addAttribute("userList",userList);
        model.addAttribute("hotUserList",hotUserList);
        return "topic_post_list";
    }




    @RequestMapping("/listImage.do")
    public String listImage(Model model){
        List<String> imageList = topicService.listImage();
        model.addAttribute("imageList",imageList);
        return "image";
    }
}





