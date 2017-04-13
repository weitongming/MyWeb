package com.weitongming.myweb.controller;


import com.weitongming.myweb.model.FileInfo;
import com.weitongming.myweb.model.PageBean;
import com.weitongming.myweb.model.Post;
import com.weitongming.myweb.model.User;
import com.weitongming.myweb.service.FileService;
import com.weitongming.myweb.service.PostService;
import com.weitongming.myweb.service.UserService;
import com.weitongming.myweb.util.MyConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;
//
//    @Autowired
//    private FileService fileService;



    /**
     * 去主页
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/toIndex.do")
    public String toIndex(Model model, HttpServletRequest request){
        System.out.println(request.getRemoteAddr());
        //记录访问信息
        userService.record(request.getRequestURL(),request.getContextPath(),request.getRemoteAddr());
        //列出帖子
        PageBean<Post> pageBean = postService.listPostByTime(1);
        //列出用户
        List<User> userList = userService.listUserByTime();
        //列出活跃用户
        List<User> hotUserList = userService.listUserByHot();
        //向模型中添加数据
        model.addAttribute("pageBean",pageBean);
        model.addAttribute("userList",userList);
        model.addAttribute("hotUserList",hotUserList);
        return "index2";
    }

    /**
     * 去主页
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/forumIndex")
    public String forumIndex(Model model, HttpServletRequest request){

        //记录访问信息
        userService.record(request.getRequestURL(),request.getContextPath(),request.getRemoteAddr());
        //列出帖子
        PageBean<Post> pageBean = postService.listPostByTime(1);
        //列出用户
        List<User> userList = userService.listUserByTime();
        //列出活跃用户
        List<User> hotUserList = userService.listUserByHot();
        //向模型中添加数据
        model.addAttribute("pageBean",pageBean);
        model.addAttribute("userList",userList);
        model.addAttribute("hotUserList",hotUserList);
        return "index";
    }

    //上传图片
//    @RequestMapping(value = "/upload.do", method = {RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
//    public
//    @ResponseBody
//    String upload(MultipartFile file, HttpSession session) throws IOException {
//        FileInfo fileInfo = new FileInfo();
//        User user = (User) session.getAttribute("user");
//        //配置上传者
//        fileInfo.setUploaderID(user.getUid() + "");
//        //设置文件长度
//        fileInfo.setFileLenght(file.getSize());
//        //获取并拼接文件路径
//        String path = user.getUid()+"\\"+file.getOriginalFilename();
//        //配置文件路径
//        fileInfo.setFilePath(path);
//        //配置文件名称
//        fileInfo.setFileName(file.getOriginalFilename());
//        //配置文件类型
//        fileInfo.setFileType(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1));
//        //配置文件UUID
//        fileInfo.setFileUUID(UUID.randomUUID().toString());
//        File saveFile = new File(MyConstant.NGINX_UPLOAD_FILE_PATH + user.getUid()+"\\"+file.getOriginalFilename());
//        //创建文件夹
//        saveFile.mkdirs();
//        //存储文件
//        try {
//            file.transferTo(saveFile);
//        } catch (IllegalStateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        fileService.uploadFile(fileInfo);
//        return "SUCCESS";
//
//    }

}
