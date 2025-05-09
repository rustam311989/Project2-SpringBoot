package ru.alishev.springcourse.Project2Boot.repositories;

import ru.alishev.springcourse.Project2Boot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByFio(String fullName);
}