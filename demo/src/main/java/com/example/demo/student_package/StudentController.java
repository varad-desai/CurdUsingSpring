package com.example.demo.student_package;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    StudentService student_service;

    @Autowired
    public StudentController(StudentService student_service) {
        this.student_service = student_service;
    }

    /**
     * Get all Students
     * @return List of Students
     */
    @GetMapping(path = "getAllStudents")
    public List<Student> get_all_students(){
        return student_service.getAllStudents();
    }

    /**
     * Get Student by Id
     * @param id
     * @return Student
     */
    @GetMapping(path = "getStudentById/{id}")
    public ResponseEntity<Object> get_student_by_id(@PathVariable Integer id){
        Student s = student_service.getStudentById(id);

        if(s.getId() == -1){
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }

        System.out.println("s ="+s);
        return new ResponseEntity<Object>(s, HttpStatus.OK);

    }

    /**
     * Add Student to database.
     * @param student
     * @return Http status
     */
    @PostMapping(path = "addStudent", produces = "application/json")
    public ResponseEntity<Object> post_student(@RequestBody Student student){

        HttpStatus response_from_service = student_service.addStudent(student);
        return new ResponseEntity<>(response_from_service);
    }

    @DeleteMapping(path = "deleteStudentById/{id}")
    public ResponseEntity<Object> delete_student(@PathVariable Integer id){
        HttpStatus response_from_service = student_service.deleteStudentById(id);
        return new ResponseEntity<>(response_from_service);
    }


}
