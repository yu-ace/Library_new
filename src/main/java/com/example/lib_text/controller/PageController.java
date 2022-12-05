package com.example.lib_text.controller;

import com.example.lib_text.dao.IDepositHistoryDao;
import com.example.lib_text.model.Book;
import com.example.lib_text.model.DepositHistory;
import com.example.lib_text.model.History;
import com.example.lib_text.model.User;
import com.example.lib_text.service.IBookService;
import com.example.lib_text.service.IDepositHistoryService;
import com.example.lib_text.service.IHistoryService;
import com.example.lib_text.service.IUserService;
import com.example.lib_text.service.impl.BookService;
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
public class PageController {

    @Autowired
    IBookService bookService;
    @Autowired
    IHistoryService historyService;
    @Autowired
    IUserService userService;
    @Autowired
    IDepositHistoryService depositHistoryService;

    @RequestMapping(path = "/",method = RequestMethod.GET)
    public String index(){
        return "login";
    }

    @RequestMapping(path = "/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }


    @RequestMapping(path = "readerBoard",method = RequestMethod.GET)
    public String readerBoard(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        model.addAttribute("user",user);
        return "readerBoard";
    }

    @RequestMapping(path = "userBoard",method = RequestMethod.GET)
    public String userBoard(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        model.addAttribute("user",user);
        return "userBoard";
    }

    @RequestMapping(path = "newBook",method = RequestMethod.GET)
    public String newBook(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        return "newBook";
    }

    @RequestMapping(path = "borrowBook",method = RequestMethod.GET)
    public String borrowBook(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        return "borrowBook";
    }

    @RequestMapping(path = "returnBook",method = RequestMethod.GET)
    public String returnBook(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        return "returnBook";
    }

    @RequestMapping(path = "bookListForUser",method = RequestMethod.GET)
    public String bookListForUser(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(0,10);
        Page<Book> bookList = bookService.getBookList(of);
        model.addAttribute("bookList",bookList);
        return "bookListForUser";
    }

    @RequestMapping(path = "historyListForUser",method = RequestMethod.GET)
    public String historyListForUser(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(0,10);
        Page<History> historyList = historyService.getHistoryList(of);
        model.addAttribute("historyList",historyList);
        return "historyListForUser";
    }

    @RequestMapping(path = "userList",method = RequestMethod.GET)
    public String userList(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(0,10);
        Page<User> userList = userService.getUserList(0,of);
        model.addAttribute("userList",userList);
        return "userList";
    }

    @RequestMapping(path = "registerUser",method = RequestMethod.GET)
    public String registerUser(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        return "registerUser";
    }

    @RequestMapping(path = "deleteUser",method = RequestMethod.GET)
    public String deleteUser(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        return "deleteUser";
    }

    @RequestMapping(path = "changePasswordForUser",method = RequestMethod.GET)
    public String changePasswordForUser(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        return "changePasswordForUser";
    }

    @RequestMapping(path = "bookList",method = RequestMethod.GET)
    public String bookList(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(0,10);
        Page<Book> bookList = bookService.getBookList(of);
        model.addAttribute("bookList",bookList);
        return "bookList";
    }

    @RequestMapping(path = "historyList",method = RequestMethod.GET)
    public String historyList(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(0,10);
        Page<History> historyList = historyService.getHistoryListByUserId(user.getId(),of);
        model.addAttribute("historyList",historyList);
        return "historyList";
    }

    @RequestMapping(path = "changePassword",method = RequestMethod.GET)
    public String changePassword(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        return "changePassword";
    }

    @RequestMapping(path = "deposit",method = RequestMethod.GET)
    public String deposit(Model model,Model model1, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(0,10);
        Page<User> userList = userService.getUserListByIdentity(0,"读者",of);
        model.addAttribute("user",userList);
        PageRequest of1 = PageRequest.of(0,10);
        Page<DepositHistory> depositHistoryPage = depositHistoryService.getDepositHistory(of1);
        model1.addAttribute("depositHistory",depositHistoryPage);
        return "deposit";
    }

    @RequestMapping(path = "reserve",method = RequestMethod.GET)
    public String reserve(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        return "reserve";
    }

    @RequestMapping(path = "depositForReader",method = RequestMethod.GET)
    public String depositForReader(Model model,Model model1, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(0,10);
        Page<DepositHistory> depositHistoryPage = depositHistoryService.getDepositHistoryByUserId(user.getId(), of);
        model.addAttribute("depositHistory",depositHistoryPage);
        User user1 = userService.getUserById(user.getId());
        model1.addAttribute("deposit",user1);
        return "depositForReader";
    }

}
