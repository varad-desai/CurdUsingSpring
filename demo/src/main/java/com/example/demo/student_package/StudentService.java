package com.example.demo.student_package;

import com.example.demo.book_package.Book;
import com.example.demo.course_package.CourseRepository;
import com.example.demo.enrolment_package.Enrolment;
import com.example.demo.enrolment_package.EnrolmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    StudentRepository studentRepository;
    EnrolmentRepository enrolmentRepository;
    CourseRepository courseRepository;

    @Autowired
    public StudentService(  StudentRepository studentRepository,
                            EnrolmentRepository enrolmentRepository,
                            CourseRepository courseRepository
    ) {

        this.studentRepository = studentRepository;
        this.enrolmentRepository = enrolmentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> getStudent(){
        List<Student> list = new ArrayList<>();
        Student s = new Student(
                1,
                "Varad Desai",
                "varad.desai@gmail.com"
    );
        list.add(s);
        return list;
    }

    @Transactional
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student getStudentById(Integer id){
        if(!studentRepository.existsById(id)){
            return new Student(-1,"student not found","student not found");
        }
        return studentRepository.findById(id).get();
    }

    public HttpStatus addStudent(Student student){
        if(student.getEmail() == null || student.getName() == null){
            return HttpStatus.BAD_REQUEST;
        } else if(studentRepository.findStudentByEmail(student.getEmail()).isPresent()){
            return HttpStatus.CONFLICT;
        } else {
            studentRepository.save(student);
            return HttpStatus.OK;
        }
    }

    public HttpStatus deleteStudentById(Integer id){

        if(!studentRepository.existsById(id)){
            return HttpStatus.NOT_FOUND;
        } else {

            for(Enrolment e:enrolmentRepository.findAll()){
                if(e.getStudent().getId() == id){
                    e.getStudent().removeEnrolment(e);
                    e.getCourse().removeEnrolmentFromCourse(e);
                    enrolmentRepository.deleteById(e.getId());
                }
            }

//            for(Book b:studentRepository.findById(id).get().getBooks()){
//                b.getStudent().removeBook(b);
//            }
//            studentRepository.findById(id).get().getBooks().clear();

            try {
                studentRepository.deleteById(id);
            } catch (Exception e) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
            return HttpStatus.OK;
        }

    }

}
