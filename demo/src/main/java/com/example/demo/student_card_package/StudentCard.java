package com.example.demo.student_card_package;

import com.example.demo.student_package.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity(name="StudentCard")
@Table(
        name = "student_card",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "student_card_number_unique",
                        columnNames = "card_number"
                )
        }
)
public class StudentCard {

    @Id
    @SequenceGenerator(
            name = "student_card_sequence",
            sequenceName = "student_card_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_card_sequence"
    )
    Integer id;

    @Column(
            name = "card_number",
            nullable = false,
            unique = true,
            columnDefinition = "",
            length = 9
    )
    Integer card_number;

    @JsonBackReference(value = "student_student_card")
    @OneToOne(
//            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "student_student_card_fk"
            )
    )
    Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentCard(Integer card_number, Student student) {
        this.card_number = card_number;
        this.student = student;
    }

    public StudentCard() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCard_number() {
        return card_number;
    }

    public void setCard_number(Integer card_number) {
        this.card_number = card_number;
    }

    @Override
    public String toString() {
        return "StudentCard{" +
                "id=" + id +
                ", card_number=" + card_number +
                '}';
    }
}
