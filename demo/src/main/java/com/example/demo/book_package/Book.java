package com.example.demo.book_package;

import com.example.demo.student_package.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Book")
@Table(name = "book")
public class Book {

    /**
     * Attributes of Book
     */

    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    private Integer id;

    @Column(
            name = "book_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String book_name;

    @Column(
            name = "created_at",
            nullable = false
    )
    private Date created_at;

    /**
     * Getters and Setters
     *
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    /**
     * Connection with a Student
     */
    @JsonBackReference(value = "student_book")
    @ManyToOne(

    )
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "student_book_fk")
    )
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * toString() method for Book
     */
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", book_name='" + book_name + '\'' +
                ", created_at=" + created_at +
                ", student=" + student +
                '}';
    }

    /**
     * Constructors:
     * 1. Default
     * 2. Parameterized without book's id
     * 3. Parameterized with book's id
     */

    public Book() {
    }

    public Book(String book_name, Date created_at, Student student) {
        this.book_name = book_name;
        this.created_at = created_at;
        this.student = student;
    }

    public Book(Integer id, String book_name, Date created_at, Student student) {
        this.id = id;
        this.book_name = book_name;
        this.created_at = created_at;
        this.student = student;
    }
}
