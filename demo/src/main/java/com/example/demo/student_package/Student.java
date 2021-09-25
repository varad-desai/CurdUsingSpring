package com.example.demo.student_package;

import com.example.demo.book_package.Book;
//import com.example.demo.enrolment_package.Enrolment;
import com.example.demo.enrolment_package.Enrolment;
import com.example.demo.student_card_package.StudentCard;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name="Student")
@Table(
        name = "student",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "student_email_unique",
                        columnNames = "email"
                )
        }
)
public class Student {

    /**
     * Attributes
     */

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Integer id;

    @Column(
            name = "full_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT",
            unique = true
    )
    private String email;

    /**
     * Connection with Student Card - one to one
     */
    @JsonManagedReference(value = "student_student_card")
    @OneToOne(
            mappedBy = "student",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private StudentCard studentCard;

    /**
     * Connection with Book - one to many
     */
    @JsonManagedReference(value = "student_book")
    @OneToMany(
            mappedBy = "student",
            orphanRemoval = true,
            cascade = { CascadeType.PERSIST, CascadeType.REMOVE },
            fetch = FetchType.LAZY
    )
    private List<Book> books = new ArrayList<>();

    /**
     * Connection with Course - Many to Many
     */

    @JsonManagedReference(
            value = "student_enrolment"
    )
    @OneToMany(
//            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "student",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Enrolment> enrolments = new ArrayList<>();


    /**
     * Constructors
     * 1. Default
     * 2. Parameterized with id
     * 3. Parameterized without id
     */


    public Student() {
    }

    public Student(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;

    }

    public Student(String name, String email) {
        this.name = name;
        this.email = email;

    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StudentCard getStudentCard() {
        return studentCard;
    }

    public void setStudentCard(StudentCard studentCard) {
        this.studentCard = studentCard;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Enrolment> getEnrolments() { return enrolments; }

    public void setEnrolments(List<Enrolment> enrolments) { this.enrolments = enrolments; }

    /**
     * Methods to manage books of the Student
     * these methods are used to keep books and students in sync
     */


    public void addBook(Book book){
        if(!this.books.contains(book)){
            this.books.add(book);
            book.setStudent(this);
        }
    }

    public void removeBook(Book book){
        if(this.books.contains(book)){
            this.books.remove(book);
            book.setStudent(null);
        }
    }

    /**
     * Methods to manage Courses of the Student
     * these methods are used to keep courses and students in sync
     */

    public void addEnrolment(Enrolment enrolment){
        if(!this.enrolments.contains(enrolment)){
            this.enrolments.add(enrolment);
            enrolment.setStudent(this);
        }
    }

    public void removeEnrolment(Enrolment enrolment){
        if(this.enrolments.contains(enrolment)){
            this.enrolments.remove(enrolment);
            enrolment.setStudent(null);
        }
    }

    /**
     * toString()
     * @return
     */
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
