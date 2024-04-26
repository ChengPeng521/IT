package com.SMS.controller;

import com.SMS.pojo.entity.Provider;
import com.SMS.service.IProviderService;
import com.SMS.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/provider")
public class ProviderController {

    @Autowired
    private IProviderService providerService;
    // @ResponseBody 返回json格式数据
    // json集合表示: [{},{}]
    // json对象：{'id',1,'name':'zhangsan'}
    // http://localhost:8080/provider/selectAll
    @RequestMapping("/selectAll")
    public String selectAll(Model model) {
        // soutm
        System.out.println("ProviderController.selectAll");
        List<Provider> list = providerService.selectAll();
        model.addAttribute("list",list);
        return "/provider/list";
    }


    @RequestMapping("/selectByPage")
    public String selectByPage(@RequestParam(defaultValue = "1") Integer pageNo,
                               @RequestParam(defaultValue = "10") Integer pageSize, Model model) {
        // soutm
        System.out.println("ProviderController.selectByPage");
        PageInfo pageInfo = providerService.selectByPage(pageNo,pageSize);

        //把list数据放到内存里面
        model.addAttribute("pageInfo", pageInfo);
        //转发到provider_list界面展示
        return "/provider/list";
    }

    // 删
    @RequestMapping("/deleteById")
    public String deleteById(Integer id) {
        providerService.deleteById(id);
        // 删除之后，应该查找展示最新数据
        // redirect重定向: 告诉浏览器发送请求/provider/selectAll
        return "redirect:/provider/selectAll";
    }

    @RequestMapping("/deleteAll")
    public String deleteAll(Integer [] ids){
        providerService.deleteAll(ids);
        return "redirect:/provider/selectByPage";
    }

    // 增
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "/provider/add";
    }

    @RequestMapping("/add")
    public String add(Provider provider) {
        providerService.add(provider);
        // 添加之后，应该查找展示最新数据
        // redirect重定向: 告诉浏览器发送请求/provider/selectAll
        return "redirect:/provider/selectByPage";
    }

    // 改

    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id, Model model) {
        Provider provider = providerService.selectById(id);
        model.addAttribute("provider", provider);
        return "/provider/update";
    }
    @RequestMapping("/update")
    public String update(Provider provider) {
        providerService.update(provider);
        // 更新之后，应该查找展示最新数据
        // redirect重定向: 告诉浏览器发送请求/provider/selectAll
        return "redirect:/provider/selectByPage";
    }
    @RequestMapping("/toGet")
    public String toGet() {
        return "/provider/get";
    }

    @RequestMapping("/get")
    public String get(Provider provider, Model model) {

        // soutm
        System.out.println("UserController.get");
        List<Provider> list =  providerService.get(provider);
        model.addAttribute("list",list);
        return "/provider/get_list";
    }
}
