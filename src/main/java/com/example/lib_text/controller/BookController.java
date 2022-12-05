package com.example.lib_text.controller;

import com.example.lib_text.model.Book;
import com.example.lib_text.model.History;
import com.example.lib_text.model.User;
import com.example.lib_text.service.IBookService;
import com.example.lib_text.service.IDepositHistoryService;
import com.example.lib_text.service.IHistoryService;
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
public class BookController {

    @Autowired
    IBookService bookService;
    @Autowired
    IHistoryService historyService;
    @Autowired
    IUserService userService;
    @Autowired
    IDepositHistoryService depositHistoryService;

    @RequestMapping(path = "/newBook",method = RequestMethod.POST)
    public String newBook(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "price")
            double price,
            @RequestParam(name = "author")
            String author,
            @RequestParam(name = "count")
            int count,
            @RequestParam(name = "categoryId")
            int categoryId, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        Book book = bookService.newBook(name,price,author,count,categoryId);
        historyService.addHistory(user.getId(),book.getId(),count);
        model.addAttribute("tip","上架成功");
        return "newBook";
    }

    @RequestMapping(path = "/borrowBook",method = RequestMethod.POST)
    public String borrowBook(
            @RequestParam(name = "id")
            int id,
            @RequestParam(name = "userId")
            int userId,Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        Book book = bookService.getBookById(id);
        History history = historyService.getHistoryByUserIdAndBookIdAndType(userId,id,"预约");
        if(history != null){
            if(book.getCount() > 0){
                User user1 = userService.getUserById(userId);
                if((user1.getBorrowBookPrice() + book.getPrice()) <= user1.getDeposit()){
                    bookService.borrowBook(id);
                    historyService.borrowBook(userId,id);
                    userService.borrowBookPrice(userId, book.getPrice());
                    depositHistoryService.newDepositHistory(userId,"借书", book.getPrice());
                    model.addAttribute("tip","借取成功");
                    return "borrowBook";
                }else{
                    model.addAttribute("tip","该用户押金不足，需要充值或归还已借的图书");
                    return "borrowBook";
                }
            }else if(book.getCount() == 0 && history.getUserId() == userId){
                User user1 = userService.getUserById(userId);
                if((user1.getBorrowBookPrice() + book.getPrice()) <= user1.getDeposit()){
                    historyService.changeBookType(userId,id);
                    userService.borrowBookPrice(userId, book.getPrice());
                    depositHistoryService.newDepositHistory(userId,"借书", book.getPrice());
                    model.addAttribute("tip","借取成功");
                    return "borrowBook";
                }else{
                    model.addAttribute("tip","该用户押金不足，需要充值或归还已借的图书");
                    return "borrowBook";
                }
            }else{
                model.addAttribute("tip","库存不足，借取失败");
                return "borrowBook";
            }
        }else{
            if(book.getCount() > 0){
                User user1 = userService.getUserById(userId);
                if((user1.getBorrowBookPrice() + book.getPrice()) <= user1.getDeposit()){
                    bookService.borrowBook(id);
                    historyService.borrowBook(userId,id);
                    userService.borrowBookPrice(userId, book.getPrice());
                    depositHistoryService.newDepositHistory(userId,"借书", book.getPrice());
                    model.addAttribute("tip","借取成功");
                    return "borrowBook";
                }else{
                    model.addAttribute("tip","该用户押金不足，需要充值或归还已借的图书");
                    return "borrowBook";
                }
            }else{
                model.addAttribute("tip","库存不足，借取失败");
                return "borrowBook";
            }
        }

    }

    @RequestMapping(path = "/returnBook",method = RequestMethod.POST)
    public String returnBook(
            @RequestParam(name = "id")
            int id,
            @RequestParam(name = "userId")
            int userId,Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        Book book = bookService.getBookById(id);
        if(book.getCount() == book.getInitialCount()){
            model.addAttribute("tip","该书不是本图书馆的书，归还失败");
            return "returnBook";
        }else{
            bookService.returnBook(id);
            historyService.returnBook(userId,id);
            userService.returnBookPrice(userId,book.getPrice());
            depositHistoryService.newDepositHistory(userId,"还书", book.getPrice());
            model.addAttribute("tip","归还成功");
            return "returnBook";
        }
    }

    @RequestMapping(path = "bookListForUserByPage",method = RequestMethod.POST)
    public String bookListForUser(
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<Book> bookList = bookService.getBookList(of);
        model.addAttribute("bookList",bookList);
        return "bookListForUser";
    }

    @RequestMapping(path = "bookListForUserByAuthor",method = RequestMethod.POST)
    public String bookListForUser(
            @RequestParam(name = "name")
            String author,
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<Book> bookList = bookService.getBookByAuthor(author,of);
        model.addAttribute("bookList",bookList);
        return "bookListForUser";
    }

    @RequestMapping(path = "bookListForUserByCategoryId",method = RequestMethod.POST)
    public String bookListForUserByCategoryId(
            @RequestParam(name = "categoryId")
            int categoryId,
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<Book> bookList = bookService.getBookByCategoryId(categoryId,of);
        model.addAttribute("bookList",bookList);
        return "bookListForUser";
    }

    @ResponseBody
    @RequestMapping(path = "/book",method = RequestMethod.GET)
    public Book getBookByName(int bookId){
        Book book = bookService.getBookById(bookId);
        return book;
    }

    @RequestMapping(path = "bookListByPage",method = RequestMethod.POST)
    public String bookList(
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<Book> bookList = bookService.getBookList(of);
        model.addAttribute("bookList",bookList);
        return "bookList";
    }

    @RequestMapping(path = "bookListByAuthor",method = RequestMethod.POST)
    public String bookList(
            @RequestParam(name = "name")
            String author,
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<Book> bookList = bookService.getBookByAuthor(author,of);
        model.addAttribute("bookList",bookList);
        return "bookList";
    }

    @RequestMapping(path = "bookListByCategoryId",method = RequestMethod.POST)
    public String bookListByCategoryId(
            @RequestParam(name = "categoryId")
            int categoryId,
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登录");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<Book> bookList = bookService.getBookByCategoryId(categoryId,of);
        model.addAttribute("bookList",bookList);
        return "bookList";
    }

    @RequestMapping(path = "/reserveBook",method = RequestMethod.POST)
    public String reserveBook(
            @RequestParam(name = "id")
            int bookId,Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        Book book = bookService.getBookById(bookId);
        if(book.getCount() == 0){
            model.addAttribute("tip","库存不足，预约失败");
            return "reserve";
        }else{
            User user1 = userService.getUserById(user.getId());
            if((user1.getBorrowBookPrice() + 5) <= user1.getDeposit()){
                bookService.reserveBook(bookId);
                historyService.receiveBook(user.getId(), bookId);
                userService.reserveBook(user.getId(), 5);
                depositHistoryService.newDepositHistory(user.getId(),"预约", 5);
                model.addAttribute("tip","预约成功");
                return "reserve";
            }else{
                model.addAttribute("tip","该用户押金不足，预约失败");
                return "reserve";
            }
        }
    }
}
