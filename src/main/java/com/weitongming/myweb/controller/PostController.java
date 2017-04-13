package com.weitongming.myweb.controller;

import com.weitongming.myweb.model.*;
import com.weitongming.myweb.service.PostService;
import com.weitongming.myweb.service.ReplyService;
import com.weitongming.myweb.service.TopicService;
import com.weitongming.myweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class PostController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private ReplyService replyService;


    //去发帖的页面
    @RequestMapping("/toPublish.do")
    public String toPublish(Model model){
        List<Topic> topicList = topicService.listTopic();
        model.addAttribute("topicList",topicList);
        return "publish";
    }

    //发帖
    @RequestMapping("/publishPost.do")
    public String publishPost(Post post) {
        int id = postService.publishPost(post);
        return "redirect:toPost.do?pid="+id;
    }


    //按时间，倒序，列出帖子
    @RequestMapping("/listPostByTime.do")
    public String listPostByTime(int curPage,Model model){
        PageBean<Post> pageBean = postService.listPostByTime(curPage);
        List<User> userList = userService.listUserByTime();
        List<User> hotUserList = userService.listUserByHot();
        model.addAttribute("pageBean",pageBean);
        model.addAttribute("userList",userList);
        model.addAttribute("hotUserList",hotUserList);
        return "index";
    }

    /**
     * 去帖子详情界面
     * @param pid
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/toPost.do")
    public String toPost(int pid,Model model,HttpSession session){
        Integer sessionUid = (Integer) session.getAttribute("uid");
        //获取帖子信息
        Post post = postService.getPostByPid(pid);
        //获取评论信息
        List<Reply> replyList = replyService.listReply(pid);

        //判断用户是否已经点赞

        boolean liked = false;
        if(sessionUid!=null){
            liked = postService.getLikeStatus(pid,sessionUid);
        }
        //向模型中添加数据
        model.addAttribute("post",post);
        model.addAttribute("replyList",replyList);
        model.addAttribute("liked",liked);
        return "post";
    }

    /**
     *
     //异步点赞
     * @param pid
     * @param session
     * @return
     */
    @RequestMapping(value = "/ajaxClickLike.do",produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String ajaxClickLike(int pid, HttpSession session){
        int sessionUid = (int) session.getAttribute("uid");
        return postService.clickLike(pid,sessionUid);
    }

    /**
     * 用于搜索界面翻页
     * @param keyWorld
     * @param currPage
     * @param model
     * @return
     */
    @RequestMapping(value = "/searchPost/{keyWorld}-{currPage}")
    public String searchPostByPage(@PathVariable("keyWorld") String keyWorld ,
                                           @PathVariable("currPage") int currPage,Model model)
    {

        PageBean<Post> postPageBean = postService.search(keyWorld,currPage);
        model.addAttribute("postPageBean",postPageBean);
        model.addAttribute("keyworld",keyWorld);
        return "search_results";
    }

    /**
     *  用于首页搜索
     * @param keyWorld
     * @param model
     * @return
     */
    @RequestMapping(value = "/searchPost/")
    public String searchPostByPage(String keyWorld,Model model){
        if (keyWorld == null)
            keyWorld = "";
        //开始时间
        long start = System.currentTimeMillis();
        PageBean<Post> postPageBean = postService.search(keyWorld,1);
        //结束时间
        long end = System.currentTimeMillis();
        long cost = end-start;
        model.addAttribute("cost",cost);
        model.addAttribute("postPageBean",postPageBean);
        model.addAttribute("keyworld",keyWorld);
        return "search_results";
    }
}
