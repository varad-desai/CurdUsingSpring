package com.example.demo.course_package;

//import com.example.demo.enrolment_package.Enrolment;
import com.example.demo.enrolment_package.Enrolment;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Course")
@Table(name = "course")
public class Course {

    /**
     * Attributes
     */

    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    private Integer id;

    @Column(
            name =  "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @Column(
            name = "department",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String department;

    /**
     * Getters and Setters
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Enrolment> getEnrolments() { return enrolments; }

    public void setEnrolments(List<Enrolment> enrolments) { this.enrolments = enrolments; }

    /**
     * Constructors:
     * 1. Default
     * 2. Parameterized without id
     * 3. Parameterized with id
     */

    public Course() {
    }

    public Course(String name, String department) {
        this.name = name;
        this.department = department;
    }


    /**
     * toString()
     * @return
     */
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }

    public void addEnrolment(Enrolment enrolment){
        if(!this.enrolments.contains(enrolment)){
            this.enrolments.add(enrolment);
            enrolment.setCourse(this);
        }
    }

    public void removeEnrolmentFromCourse(Enrolment enrolment){
        if(this.enrolments.contains(enrolment)){
            this.enrolments.remove(enrolment);
            enrolment.setCourse(null);
        }
    }
    /**
     * Connecting Course with Student
     */

    @JsonManagedReference
    @OneToMany(
//            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            cascade = CascadeType.ALL,
            mappedBy = "course",
            fetch = FetchType.EAGER
    )
    private List<Enrolment> enrolments = new ArrayList<>();

}
