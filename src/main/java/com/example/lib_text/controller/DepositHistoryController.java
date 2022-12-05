package com.example.lib_text.controller;

import com.example.lib_text.dao.IDepositHistoryDao;
import com.example.lib_text.model.DepositHistory;
import com.example.lib_text.model.User;
import com.example.lib_text.service.IDepositHistoryService;
import com.example.lib_text.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class DepositHistoryController {

    @Autowired
    IDepositHistoryService depositHistoryService;
    @Autowired
    IUserService userService;

    @RequestMapping(path = "/depositForReaderByPage",method = RequestMethod.POST)
    public String depositForReaderByPage(
            @RequestParam(name = "number")
            int n, Model model,Model model1, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<DepositHistory> depositHistoryPage = depositHistoryService.getDepositHistoryByUserId(user.getId(), of);
        model.addAttribute("depositHistory",depositHistoryPage);
        User user1 = userService.getUserById(user.getId());
        model1.addAttribute("deposit",user1);
        return "depositForReader";
    }

    @RequestMapping(path = "/depositByPage",method = RequestMethod.POST)
    public String depositByPage(
            @RequestParam(name = "number")
            int n, Model model,Model model1, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<DepositHistory> depositHistoryPage = depositHistoryService.getDepositHistory(of);
        model.addAttribute("depositHistory",depositHistoryPage);
        PageRequest of1 = PageRequest.of(0,10);
        Page<User> userList = userService.getUserListByIdentity(0,"读者",of1);
        model.addAttribute("user",userList);
        return "deposit";
    }
}
