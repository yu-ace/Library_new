package com.example.lib_text.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.lib_text.model.History;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    IUserService userService;
    @Autowired
    IDepositHistoryService depositHistoryService;

    @RequestMapping(path = "/login",method = RequestMethod.POST)
    public String login(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "password")
            String password, Model model, HttpSession session){
        User user = userService.getUserByName(name);
        if(user == null){
            model.addAttribute("error","该用户不存在");
            return "login";
        }
        if(user.getIsDelete() == 1){
            model.addAttribute("error","该用户已注销");
            return "login";
        }
        if(user.getPassword().equals(password)){
            if(user.getIdentity().equals("读者")){
                session.setAttribute("user",user);
                return "redirect:/readerBoard";
            }else{
                session.setAttribute("user",user);
                return "redirect:/userBoard";
            }
        }else{
            model.addAttribute("error","密码错误");
            return "login";
        }
    }

    @RequestMapping(path = "/reg",method = RequestMethod.POST)
    public String reg(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "password")
            String password,
            @RequestParam(name = "deposit")
            double deposit,
            Model model){
        User user = userService.getUserByName(name);
        if(name.length() == 0 || password.length() == 0){
            model.addAttribute("error","你输入的账户或密码为空，注册失败");
            return "registerUser";
        }
        if(user == null){
            User user1 = userService.newReader(name,password,deposit);
            depositHistoryService.newDepositHistory(user1.getId(),"充值",deposit);
            model.addAttribute("error","注册成功，请登录");
            return "login";
        }
        if(user.getIsDelete() == 1){
            if(user.getPassword().equals(password)){
                userService.changeUserIsDelete(name);
                model.addAttribute("error","该用户已重新激活");
                return "login";
            }else{
                model.addAttribute("error","该用户名已注销，本次密码错误，激活失败");
                return "registerUser";
            }
        }else{
            model.addAttribute("error","该用户名已存在，注册失败");
            return "registerUser";
        }
    }

    @ResponseBody
    @RequestMapping(path = "/user",method = RequestMethod.GET)
    public User getUserNameById(int userId){
        User user = userService.getUserById(userId);
        User user1 = new User();
        BeanUtil.copyProperties(user,user1,true);
        user1.setPassword(null);
        return user1;
    }

    @RequestMapping(path = "userListByPage",method = RequestMethod.POST)
    public String userList(
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<User> userList = userService.getUserList(0,of);
        model.addAttribute("userList",userList);
        return "userList";
    }

    @RequestMapping(path = "userListByIdentity",method = RequestMethod.POST)
    public String userListByIdentity(
            @RequestParam(name = "identity")
            String identity,
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<User> userList = userService.getUserListByIdentity(0,identity,of);
        model.addAttribute("userList",userList);
        return "userList";
    }

    @RequestMapping(path = "userByName",method = RequestMethod.POST)
    public String userByName(
            @RequestParam(name = "name")
            String name,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        User user1 = userService.getUserByName(name);
        model.addAttribute("userList",user1);
        return "userList";
    }

    @RequestMapping(path = "userById",method = RequestMethod.POST)
    public String userById(
            @RequestParam(name = "id")
            int id,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        User user1 = userService.getUserById(id);
        model.addAttribute("userList",user1);
        return "userList";
    }

    @RequestMapping(path = "registerUser",method = RequestMethod.POST)
    public String registerUser(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "password")
            String password,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        if(name.length() == 0 || password.length() == 0){
            model.addAttribute("tip","你输入的账户或密码为空，添加失败");
            return "registerUser";
        }
        User user1 = userService.getUserByName(name);
        if(user1 == null){
            userService.newUser(name,password);
            model.addAttribute("tip","添加成功");
            return "registerUser";
        }
        if(user1.getIsDelete() == 1){
            if(user1.getPassword().equals(password)){
                userService.changeUserIsDelete(name);
                model.addAttribute("tip","该用户已重新激活");
                return "registerUser";
            }else{
                model.addAttribute("tip","该用户名已注销，本次密码错误，激活失败");
                return "registerUser";
            }
        }else{
            model.addAttribute("tip","该用户名已存在，注册失败");
            return "registerUser";
        }
    }

    @RequestMapping(path = "deleteUser",method = RequestMethod.POST)
    public String deleteUser(
            @RequestParam(name = "id")
            int id,
            @RequestParam(name = "password")
            String password,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        User user1 = userService.getUserById(id);
        if(user1 == null){
            model.addAttribute("tip","该用户不存在，删除失败");
            return "deleteUser";
        }
        if(user1.getIsDelete() == 1){
            model.addAttribute("tip","该用户名已注销，无需删除");
            return "deleteUser";
        }
        if(user1.getName().equals("admin")){
            model.addAttribute("tip","该用户名无法被删除");
            return "deleteUser";
        }
        if(user1.getPassword().equals(password)){
            userService.deleteUserById(id);
            model.addAttribute("tip","删除成功");
            return "deleteUser";
        }else{
            model.addAttribute("tip","密码错误，删除失败");
            return "deleteUser";
        }
    }

    @RequestMapping(path = "changePasswordForUser",method = RequestMethod.POST)
    public String changePasswordForUser(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "password")
            String password,
            @RequestParam(name = "newPassword")
            String newPassword,
            @RequestParam(name = "newPassword1")
            String newPassword1,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        User user1 = userService.getUserByName(name);
        if(user1 == null){
            model.addAttribute("tip","该用户不存在，更改失败");
            return "changePasswordForUser";
        }
        if(user1.getIsDelete() == 1){
            model.addAttribute("tip","该用户名已注销，更改失败");
            return "changePasswordForUser";
        }
        if(user1.getName().equals("admin")){
            model.addAttribute("tip","该用户名无法被更改");
            return "changePasswordForUser";
        }
        if(user1.getPassword().equals(password)){
            if(newPassword.equals(newPassword1) && !newPassword.equals(password)){
                userService.changePassword(name,newPassword);
                model.addAttribute("tip","更改成功");
                return "changePasswordForUser";
            }else if(newPassword.equals(password) || newPassword1.equals(password)){
                model.addAttribute("tip","新密码和老密码相同，更改失败");
                return "changePasswordForUser";
            }else{
                model.addAttribute("tip","两次新密码不同，更改失败");
                return "changePasswordForUser";
            }
        }else{
            model.addAttribute("tip","密码错误，更改失败");
            return "changePasswordForUser";
        }
    }

    @RequestMapping(path = "changePassword",method = RequestMethod.POST)
    public String changePassword(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "password")
            String password,
            @RequestParam(name = "newPassword")
            String newPassword,
            @RequestParam(name = "newPassword1")
            String newPassword1,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        User user1 = userService.getUserByName(name);
        if(user1 == null){
            model.addAttribute("tip","该用户不存在，更改失败");
            return "changePassword";
        }
        if(user1.getIsDelete() == 1){
            model.addAttribute("tip","该用户名已注销，更改失败");
            return "changePassword";
        }
        if(user1.getName().equals("admin")){
            model.addAttribute("tip","该用户名无法被更改");
            return "changePassword";
        }
        if(user1.getPassword().equals(password)){
            if(newPassword.equals(newPassword1) && !newPassword.equals(password)){
                userService.changePassword(name,newPassword);
                model.addAttribute("tip","更改成功");
                return "changePassword";
            }else if(newPassword.equals(password) || newPassword1.equals(password)){
                model.addAttribute("tip","新密码和老密码相同，更改失败");
                return "changePassword";
            }else{
                model.addAttribute("tip","两次新密码不同，更改失败");
                return "changePassword";
            }
        }else{
            model.addAttribute("tip","密码错误，更改失败");
            return "changePassword";
        }
    }


    @RequestMapping(path = "/addDeposit",method = RequestMethod.POST)
    public String addDeposit(
            @RequestParam(name = "add")
            double price,
            @RequestParam(name = "id")
            int userId,Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        userService.addDeposit(userId,price);
        depositHistoryService.newDepositHistory(userId,"充值",price);
        model.addAttribute("tip","充值成功");
        return "deposit1";
    }

    @RequestMapping(path = "/cutDeposit",method = RequestMethod.POST)
    public String cutDeposit(
            @RequestParam(name = "cut")
            double price,
            @RequestParam(name = "id")
            int userId,Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        User user1 = userService.getUserById(userId);
        if((user1.getBorrowBookPrice() + price) <= user1.getDeposit()){
            userService.cutDeposit(userId,price);
            depositHistoryService.newDepositHistory(userId,"提取",price);
            model.addAttribute("tip","提取成功");
            return "deposit1";
        }else{
            model.addAttribute("tip","对不起，你的押金不足，请减少提取的金额，或归还借取的图书");
            return "deposit1";
        }
    }

    @ResponseBody
    @RequestMapping(path = "/deposit2",method = RequestMethod.GET)
    public User getUserDeposit(int userId){
        User user = userService.getUserById(userId);
        return user;
    }
}
