package com.SMS.controller;

import com.SMS.pojo.entity.Goods;
import com.SMS.pojo.entity.GoodsType;
import com.SMS.pojo.entity.Provider;
import com.SMS.pojo.vo.GoodsVo;
import com.SMS.pojo.vo.PurchaseVo;
import com.SMS.service.IGoodsService;
import com.SMS.service.IGoodsTypeService;
import com.SMS.service.IProviderService;
import com.SMS.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IGoodsTypeService goodsTypeService;
    @Autowired
    private IProviderService providerService;
    // @ResponseBody 返回json格式数据
    // json集合表示: [{},{}]
    // json对象：{'id',1,'name':'zhangsan'}
    // http://localhost:8080/goods/selectAll
    @RequestMapping("/selectAll")
    public String selectAll(Model model) {
        // soutm
        System.out.println("GoodsController.selectAll");
        List<Goods> list = goodsService.selectAll();
        model.addAttribute("list",list);
        return "/goods/list";
    }


    @RequestMapping("/selectByPage")
    public String selectByPage(@RequestParam(defaultValue = "1") Integer pageNo,
                               @RequestParam(defaultValue = "10") Integer pageSize, Model model) {
        // soutm
        System.out.println("GoodsController.selectByPage");
        PageInfo pageInfo = goodsService.selectByPage(pageNo,pageSize);

        //把list数据放到内存里面
        model.addAttribute("pageInfo", pageInfo);
        //转发到goods_list界面展示
        return "/goods/list";
    }

    // 删
    @RequestMapping("/deleteById")
    public String deleteById(Integer id) {
        goodsService.deleteById(id);
        // 删除之后，应该查找展示最新数据
        // redirect重定向: 告诉浏览器发送请求/goods/selectAll
        return "redirect:/goods/selectAll";
    }

    @RequestMapping("/deleteAll")
    public String deleteAll(Integer [] ids){
        goodsService.deleteAll(ids);
        return "redirect:/goods/selectByPage";
    }

    // 增
    @RequestMapping("/toAdd")
    public String toAdd(Model model) {
        List<GoodsType> list = goodsTypeService.selectAll();
        model.addAttribute("list",list);
        List<Provider> listt = providerService.selectAll();
        model.addAttribute("listt",listt);
        return "/goods/add";
    }

    @RequestMapping("/add")
    public String add(Goods goods) {
        goodsService.add(goods);
        // 添加之后，应该查找展示最新数据
        // redirect重定向: 告诉浏览器发送请求/goods/selectAll
        return "redirect:/goods/selectByPage";
    }

    // 改

    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id, Model model) {
        Goods goods = goodsService.selectById(id);
        model.addAttribute("goods", goods);
        List<GoodsType> list = goodsTypeService.selectAll();
        model.addAttribute("list",list);
        List<Provider> listt = providerService.selectAll();
        model.addAttribute("listt",listt);
        return "/goods/update";
    }
    @RequestMapping("/update")
    public String update(Goods goods) {
        goodsService.update(goods);
        // 更新之后，应该查找展示最新数据
        // redirect重定向: 告诉浏览器发送请求/goods/selectAll
        return "redirect:/goods/selectByPage";
    }


    @RequestMapping("/toGet")
    public String toGet(Model model) {

        return "/goods/get";
    }

    @RequestMapping("/get")
    public String get(GoodsVo goodsVo, Model model) {

        // soutm
        List<GoodsVo> list =  goodsService.get(goodsVo);
        model.addAttribute("list",list);
        return "/goods/get_list";
    }
}
