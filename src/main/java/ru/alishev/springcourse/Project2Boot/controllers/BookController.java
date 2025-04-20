package ru.alishev.springcourse.Project2Boot.controllers;

import jakarta.validation.Valid;
import ru.alishev.springcourse.Project2Boot.models.Book;
import ru.alishev.springcourse.Project2Boot.models.Person;
import ru.alishev.springcourse.Project2Boot.services.BookService;
import ru.alishev.springcourse.Project2Boot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {


//    private final BookDAO bookDAO;
//    private final PersonDAO personDAO;
//    private final DataSource dataSource;
    private final BookService bookService;
    private final PeopleService peopleService;


    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(name="page", required=false) Integer page,
                        @RequestParam(name="books_per_page", required=false) Integer booksPerPage,
                        @RequestParam(name="sort_by_year", required=false) boolean sortByYear){
        // Получим книги из bookService и передадим их на представление
        System.out.println(page + " " + booksPerPage + " " + sortByYear);
        model.addAttribute("books", bookService.findAll(page, booksPerPage, sortByYear));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        // получим книгу из дао и передадим ее на представление
        Book book = bookService.findById(id);
        //model.addAttribute("person", new Person());
        model.addAttribute("book", book);
        model.addAttribute("owner", book.getOwner());
        model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute Book book){
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "books/new";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookService.findById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id){

        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/doFree")
    public String doFree(@PathVariable("id") int id){
        bookService.doFree(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/toAppoint")
    public String toAppoint(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        bookService.toAppoint(id, person);
        return "redirect:/books/{id}";
    }

    @GetMapping("/search")
    public String search(){
        return "books/search";
    }

    @GetMapping("/search1")
    public String search(Model model, @RequestParam("query") String query){
        model.addAttribute("books", bookService.findNameStartingWith(query));
        return "books/search";
    }

}
