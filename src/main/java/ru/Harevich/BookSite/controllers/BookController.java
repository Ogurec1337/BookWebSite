package ru.Harevich.BookSite.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.Harevich.BookSite.Services.BookService;
import ru.Harevich.BookSite.Services.UserService;
import ru.Harevich.BookSite.models.Book;
import ru.Harevich.BookSite.models.User;
import ru.Harevich.BookSite.security.MyUserDetails;
import ru.Harevich.BookSite.util.BookValidator;
import ru.Harevich.BookSite.util.UserValidator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final BookService bookService;
    private Optional<User> currentUser;
    private final BookValidator bookValidator;

    @ModelAttribute
    public void addUserToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if((authentication.isAuthenticated()) && !(authentication instanceof AnonymousAuthenticationToken)) {
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            Optional<User> user = userService.getByUsername(userDetails.getUsername());
            currentUser = user;
            if(user.isPresent())
                model.addAttribute("user", user.get());
        }

    }


    @Autowired
    public BookController(UserService userService, UserValidator userValidator, BookService bookService, BookValidator bookValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.bookService = bookService;
        this.bookValidator = bookValidator;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user){
        return "/registration";
    }
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult){
        userValidator.validate(user,bindingResult);
        if(bindingResult.hasErrors())
            return "/registration";
        userService.registrate(user);
        return "redirect:/";
    }

    @GetMapping()
    public String catalog(Model model,
                          @RequestParam(name="page",required = false,defaultValue = "1")int page,
                          @RequestParam(name="sort",required = false,defaultValue = "name")String sort){
        List<Book> books = bookService.getBooks(page,sort);
        model.addAttribute("books",books);
        model.addAttribute("page",page);
        model.addAttribute("sort",sort);
        return "/catalog";
    }
    @GetMapping("/manage")
    public String manage(@ModelAttribute("user") User user,
                         @ModelAttribute("book") Book book){
        return "/manage";
    }

    @GetMapping("/manage/delete")
    public String deletingBookOrUser(@RequestParam(name="searching")String searching,
                                     @RequestParam(name="pattern")String pattern,
                                     Model model) {
        List<User> users = null;
        List<Book> books = null;
        if(searching.equals("user")){
            Optional<List<User>> optional = userService.getByNameStartingWith(pattern);
            if(optional.isPresent())
                users = optional.get();
        }
        if(searching.equals("book")){
            Optional<List<Book>> optional = bookService.getByNameStartingWith(pattern);
            if(optional.isPresent())
                books = optional.get();
        }
        model.addAttribute("books",books);
        model.addAttribute("users",users);

        return "/manage";
    }

    @GetMapping("/book/{url}")
    public String book(@PathVariable("url")String url, Model model){
        model.addAttribute("book",bookService.getByUrl(url));
        return "/book";
    }

    @GetMapping("/cart")
    public String cart(){
        return "/cart";
    }
    @GetMapping("/supply")
    public String supply(@ModelAttribute("book") Book book){
        return "/supply";
    }

    @Transactional
    @PostMapping("/manage/deleteBook")
    public String deleteBook(@RequestParam("id")int id){
        bookService.delete(id);
        return "/manage";
    }
    @Transactional
    @PostMapping("/manage/deleteUser")
    public String deleteUser(@RequestParam("id")int id){
        userService.delete(id);
        return "/manage";
    }

    @Transactional
    @PostMapping("/createBook")
    public String creatingBook(@ModelAttribute("book") @Valid Book book,
                                      BindingResult bindingResult,
                               @RequestParam("file") MultipartFile file){

        bookValidator.validate(book,bindingResult);
        if(bindingResult.hasErrors())
            return "/supply";
        bookService.create(book);
            if (!file.isEmpty()) {
                try {
                    String UPLOADED_FOLDER = "C:\\Users\\yurap\\IdeaProjects\\BookSite\\src\\main\\resources\\static\\images\\books";
                    // Создаем директорию, если она не существует
                    File dir = new File(UPLOADED_FOLDER);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    // Сохраняем файл в указанную директорию
                    String filePath = UPLOADED_FOLDER +"\\"+book.getUrl() + ".png";
                    File dest = new File(filePath);
                    file.transferTo(dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bookService.create(book);
            }
        return "redirect:/";
    }
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/notFound";
    }

    @PostMapping("/cart/{bookurl}")
    @Transactional
    public String addToCart(@PathVariable("bookurl") String bookurl){
        currentUser.get().getCart().getBooks().add(bookService.getByUrl(bookurl));

        return new StringBuilder().
                append("redirect:/book/").append(bookurl).toString();
    }

    @PostMapping("/cart/delete/{bookurl}")
    @Transactional
    public String deleteFromCart(@PathVariable("bookurl") String bookurl){
        currentUser.get().getCart().getBooks().remove(bookService.getByUrl(bookurl));
        return "redirect:/cart";
    }

    @GetMapping("/search")
    public String search(Model model,
                          @RequestParam(name="page",required = false,defaultValue = "1")int page,
                          @RequestParam(name="sort",required = false,defaultValue = "name")String sort,
                          @RequestParam(name="pattern",required = true,defaultValue = "")String pattern){
        List<Book> books = bookService.searchForBooks(page,sort,pattern);
        model.addAttribute("books",books);
        model.addAttribute("page",page);
        model.addAttribute("sort",sort);
        model.addAttribute("pattern",pattern);
        return "/catalog";
    }

}
