package ru.alishev.springcourse.Project2Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name="person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_id;

    @Column(name = "fio")
    @NotEmpty(message = "not empty")
    @Size(min = 3, max = 100, message = "size from 3 to 100 chars")
    private String fio;

    @Column(name = "year")
    @Min(value = 0, message = "Age should be greater than 3")
    private int year;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

//    Страна, Город, индекс (6 цифр)
//    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address should be in this format: Country, City, Post Code(6 digits)")
//    private String address;

    public Person(int person_id, String fio, int year) {
        this.person_id = person_id;
        this.fio = fio;
        this.year = year;
    }

    public Person() {

    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "person_id=" + person_id +
                ", fio='" + fio + '\'' +
                ", year=" + year +
                ", books=" + books +
                '}';
    }
}
