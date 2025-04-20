package ru.alishev.springcourse.Project2Boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.Project2Boot.models.Book;
import ru.alishev.springcourse.Project2Boot.models.Person;
import ru.alishev.springcourse.Project2Boot.repositories.BookRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll(Integer page, Integer booksPerPage, boolean sortByYear) {
        if(page != null && booksPerPage != null) {
            if (sortByYear) {
                return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
            }
            else{
                return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
            }
        }
        else{
            if(sortByYear) {
                return bookRepository.findAll(Sort.by("year"));
            }
            else{
                return bookRepository.findAll();
            }
        }
    }

    @Transactional(readOnly = true)
    public List<Book> findNameStartingWith(String part) {
        if(!part.equals("") && part != null){
            return bookRepository.findByTitleIsStartingWith(part);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Book findById(int id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book){
        book.setBook_id(id);
        bookRepository.save(book);
    }

    // сделать книгу свободной
    @Transactional
    public void doFree(int id){
        Book book = findById(id);
        book.setOwner(null);
        book.setDateOfAppointment(null);
        bookRepository.save(book);
    }

    // назначить книгу
    @Transactional
    public void toAppoint(int id, Person person){
        Book book = findById(id);
        book.setOwner(person);
        book.setDateOfAppointment(new Date());
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }
}
