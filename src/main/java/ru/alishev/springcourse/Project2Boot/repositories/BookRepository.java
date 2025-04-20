package ru.alishev.springcourse.Project2Boot.repositories;

import ru.alishev.springcourse.Project2Boot.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleIsStartingWith(String part);
}
