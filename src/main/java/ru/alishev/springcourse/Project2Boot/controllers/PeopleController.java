package ru.alishev.springcourse.Project2Boot.controllers;

import jakarta.validation.Valid;
import ru.alishev.springcourse.Project2Boot.models.Person;
import ru.alishev.springcourse.Project2Boot.services.PeopleService;
import ru.alishev.springcourse.Project2Boot.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    //private final PersonDAO personDAO;
    private final PersonValidator personValidator;
//    private final DataSource dataSource;
//    private final BookDAO bookDAO;


//    @Autowired
//    public PeopleController(PersonDAO personDAO, PersonValidator personValidator, DataSource dataSource, BookDAO bookDAO) {
//        this.personDAO = personDAO;
//        this.personValidator = personValidator;
//        this.dataSource = dataSource;
//        this.bookDAO = bookDAO;
//    }

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PersonValidator personValidator, PeopleService peopleService) {
        this.personValidator = personValidator;
        this.peopleService = peopleService;
    }


    @GetMapping()
    public String index(Model model){
        // Получим людей из дао и передадим их на представление
        //model.addAttribute("people", personDAO.index());
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        // получим одного человека из дао и передадим его на представление
        //model.addAttribute("person", personDAO.show(id));
        Person person = peopleService.findById(id);
        model.addAttribute("person", person);
        // получим список книг человека из book дао и передадим его на представление
        model.addAttribute("personsBooks", peopleService.findBooksById(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute Person person){
       // model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){

        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return "people/new";
        }
        //System.out.println(person.getPerson_id());
        //personDAO.save(person);
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        //model.addAttribute("person", personDAO.show(id));
        model.addAttribute("person", peopleService.findById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){

        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }

}
