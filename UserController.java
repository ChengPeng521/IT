package com.SMS.controller;

import com.SMS.pojo.entity.User;
import com.SMS.service.IUserService;
import com.SMS.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    // @ResponseBody 返回json格式数据
    // json集合表示: [{},{}]
    // json对象：{'id',1,'name':'zhangsan'}
    // http://localhost:8080/user/selectAll
    @RequestMapping("/selectAll")
    public String selectAll(Model model) {
        // soutm
        System.out.println("UserController.selectAll");
        List<User> list = userService.selectAll();
        model.addAttribute("list",list);
        return "/user/list";
    }


    @RequestMapping("/selectByPage")
    public String selectByPage(@RequestParam(defaultValue = "1") Integer pageNo,
                               @RequestParam(defaultValue = "10") Integer pageSize, Model model) {
        // soutm
        System.out.println("UserController.selectByPage");
        PageInfo pageInfo = userService.selectByPage(pageNo,pageSize);

        //把list数据放到内存里面
        model.addAttribute("pageInfo", pageInfo);
        //转发到user_list界面展示
        return "/user/list";
    }

    // 删
    @RequestMapping("/deleteById")
    public String deleteById(Integer id) {
        userService.deleteById(id);
        // 删除之后，应该查找展示最新数据
        // redirect重定向: 告诉浏览器发送请求/user/selectAll
        return "redirect:/user/selectAll";
    }

    @RequestMapping("/deleteAll")
    public String deleteAll(Integer [] ids){
        userService.deleteAll(ids);
        return "redirect:/user/selectByPage";
    }
    @RequestMapping("/welcome")
    public String welcome() {
        return "/welcome1";
    }
    // 增
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "/user/add";
    }

    @RequestMapping("/add")
    public String add(User user) {
        userService.add(user);
        // 添加之后，应该查找展示最新数据
        // redirect重定向: 告诉浏览器发送请求/user/selectAll
        return "redirect:/user/selectByPage";
    }

    // 改

    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id, Model model) {
        User user = userService.selectById(id);
        model.addAttribute("user", user);
        return "/user/update";
    }
    @RequestMapping("/update")
    public String update(User user) {
        userService.update(user);
        // 更新之后，应该查找展示最新数据
        // redirect重定向: 告诉浏览器发送请求/user/selectAll
        return "redirect:/user/selectByPage";
    }
    // 查
    @RequestMapping("/toGet")
    public String toGet() {
        return "/user/get";
    }

    @RequestMapping("/get")
    public String get(User user,Model model) {

        // soutm
        System.out.println("UserController.get");
        List<User> list =  userService.get(user);
        model.addAttribute("list",list);
        return "/user/get_list";
    }

}
