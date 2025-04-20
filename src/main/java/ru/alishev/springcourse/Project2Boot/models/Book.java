package ru.alishev.springcourse.Project2Boot.models;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    @Column(name = "date_of_appointment")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateOfAppointment;

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;

    @Column(name = "title")
    @NotEmpty(message = "not empty")
    @Size(min = 3, max = 100, message = "size from 3 to 100 chars")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "not empty")
    @Size(min = 3, max = 100, message = "size from 3 to 100 chars")
    private String author;

    @Column(name = "year")
    private int year;

//    Страна, Город, индекс (6 цифр)
//    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address should be in this format: Country, City, Post Code(6 digits)")
//    private String address;

    public Book(Person owner, int book_id, String title, String author, int year) {
        this.owner = owner;
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book() {

    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(Date dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    public long getBetween(){
        Date currentDate = new Date();
        return (currentDate.getTime() - dateOfAppointment.getTime())/1000/60/60/24;
    }
    public boolean isDelay(){
        return this.getBetween() > 10;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                '}';
    }
}
