package com.SMS.controller;

import com.SMS.pojo.entity.Customer;
import com.SMS.pojo.entity.User;
import com.SMS.service.ICustomerService;
import com.SMS.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;
    // @ResponseBody 返回json格式数据
    // json集合表示: [{},{}]
    // json对象：{'id',1,'name':'zhangsan'}
    // http://localhost:8080/customer/selectAll
    @RequestMapping("/selectAll")
    public String selectAll(Model model) {
        // soutm
        System.out.println("CustomerController.selectAll");
        List<Customer> list = customerService.selectAll();
        model.addAttribute("list",list);
        return "/customer/list";
    }


    @RequestMapping("/selectByPage")
    public String selectByPage(@RequestParam(defaultValue = "1") Integer pageNo,
                               @RequestParam(defaultValue = "10") Integer pageSize, Model model) {
        // soutm
        System.out.println("CustomerController.selectByPage");
        PageInfo pageInfo = customerService.selectByPage(pageNo,pageSize);

        //把list数据放到内存里面
        model.addAttribute("pageInfo", pageInfo);
        //转发到customer_list界面展示
        return "/customer/list";
    }

    // 删
    @RequestMapping("/deleteById")
    public String deleteById(Integer id) {
        customerService.deleteById(id);
        // 删除之后，应该查找展示最新数据
        // redirect重定向: 告诉浏览器发送请求/customer/selectAll
        return "redirect:/customer/selectAll";
    }

    @RequestMapping("/deleteAll")
    public String deleteAll(Integer [] ids){
        customerService.deleteAll(ids);
        return "redirect:/customer/selectByPage";
    }

    // 增
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "/customer/add";
    }

    @RequestMapping("/add")
    public String add(Customer customer) {
        customerService.add(customer);
        // 添加之后，应该查找展示最新数据
        // redirect重定向: 告诉浏览器发送请求/customer/selectAll
        return "redirect:/customer/selectByPage";
    }

    // 改

    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id, Model model) {
        Customer customer = customerService.selectById(id);
        model.addAttribute("customer", customer);
        return "/customer/update";
    }
    @RequestMapping("/update")
    public String update(Customer customer) {
        customerService.update(customer);
        // 更新之后，应该查找展示最新数据
        // redirect重定向: 告诉浏览器发送请求/customer/selectAll
        return "redirect:/customer/selectByPage";
    }
    @RequestMapping("/toGet")
    public String toGet() {
        return "/customer/get";
    }

    @RequestMapping("/get")
    public String get(Customer customer, Model model) {

        // soutm
        System.out.println("CustomerService.get");
        List<Customer> list =  customerService.get(customer);
        model.addAttribute("list",list);
        return "/customer/get_list";
    }
}
