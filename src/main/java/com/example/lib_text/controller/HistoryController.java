package com.example.lib_text.controller;

import com.example.lib_text.model.Book;
import com.example.lib_text.model.History;
import com.example.lib_text.model.User;
import com.example.lib_text.service.IBookService;
import com.example.lib_text.service.IHistoryService;
import com.example.lib_text.service.IUserService;
import com.example.lib_text.service.impl.HistoryService;
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
public class HistoryController {

    @Autowired
    IHistoryService historyService;
    @Autowired
    IBookService bookService;
    @Autowired
    IUserService userService;

    @RequestMapping(path = "historyListForUserByPage",method = RequestMethod.POST)
    public String historyListForUser(
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<History> historyList = historyService.getHistoryList(of);
        model.addAttribute("historyList",historyList);
        return "historyListForUser";
    }

    @RequestMapping(path = "historyForUserByUserId",method = RequestMethod.POST)
    public String historyForUserByUserId(
            @RequestParam(name = "id")
            int userId,
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<History> historyList = historyService.getHistoryListByUserId(userId,of);
        model.addAttribute("historyList",historyList);
        return "historyListForUser";
    }

    @RequestMapping(path = "historyListForUserByType",method = RequestMethod.POST)
    public String historyListForUserByType(
            @RequestParam(name = "type")
            String type,
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<History> historyList = historyService.getHistoryListByType(type,of);
        model.addAttribute("historyList",historyList);
        return "historyListForUser";
    }

    @RequestMapping(path = "historyForUserByBookId",method = RequestMethod.POST)
    public String historyForUserByBookId(
            @RequestParam(name = "id")
            int bookId,
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<History> historyList = historyService.getHistoryLIstByBookId(bookId,of);
        model.addAttribute("historyList",historyList);
        return "historyListForUser";
    }


    @RequestMapping(path = "historyListForReaderByPage",method = RequestMethod.POST)
    public String historyListForReaderByPage(
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<History> historyList = historyService.getHistoryListByUserId(user.getId(),of);
        model.addAttribute("historyList",historyList);
        return "historyList";
    }

    @RequestMapping(path = "historyListForReaderByType",method = RequestMethod.POST)
    public String historyListForReaderByType(
            @RequestParam(name = "type")
            String type,
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<History> historyList = historyService.getHistoryListByUserIdAndType(user.getId(),type,of);
        model.addAttribute("historyList",historyList);
        return "historyList";
    }

    @RequestMapping(path = "historyForReaderByBookId",method = RequestMethod.POST)
    public String historyForReaderByBookId(
            @RequestParam(name = "id")
            int bookId,
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<History> historyList = historyService.getHistoryListByUserIdAndBookId(user.getId(),bookId,of);
        model.addAttribute("historyList",historyList);
        return "historyList";
    }

    @RequestMapping(path = "historyForReaderBuyBookByBookId",method = RequestMethod.POST)
    public String historyForReaderBuyBookByBookId(
            @RequestParam(name = "id")
            int bookId,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        History history = historyService.getHistoryByUserIdAndBookId(user.getId(), bookId);
        if(history != null && history.getType().equals("借取")){
            historyService.buyBook(user.getId(), bookId);
            bookService.changeBookInitialCount(bookId);
            userService.buyBook(user.getId(), bookService.getBookById(bookId).getPrice());
            model.addAttribute("tip","购买成功");
            return "buyBook";
        }else if(history != null && !history.getType().equals("借取")){
            model.addAttribute("tip","本书已归还或未借取，无法购买");
            return "buyBook";
        }else{
            model.addAttribute("tip","你的历史记录中没有该图书，购买失败");
            return "buyBook";
        }
    }
}
