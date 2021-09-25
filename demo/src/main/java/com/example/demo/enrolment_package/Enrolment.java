package com.example.demo.enrolment_package;

import com.example.demo.course_package.Course;
import com.example.demo.student_package.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity(name = "Enrolment")
@Table(name = "enrolment")
public class Enrolment {

    @Id
    @SequenceGenerator(
            name = "enrolment_sequence",
            sequenceName = "enrolment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "enrolment_sequence"
    )
    private Integer id;

    @JsonBackReference(value = "student_enrolment")
    @ManyToOne
    @JoinColumn(
            name = "student_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "student_enrolment_fk")
    )
    private Student student;

    @JsonBackReference(value = "defaultReference")
    @ManyToOne
    @JoinColumn(
            name = "course_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "course_enrolment_fk")
    )
    private Course course;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Enrolment() {
    }

    public Enrolment(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    @Override
    public String toString() {
        return "Enrolment{" +
                "id=" + id +
                ", student=" + student +
                ", course=" + course +
                '}';
    }
}
