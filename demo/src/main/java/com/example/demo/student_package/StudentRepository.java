package com.example.demo.student_package;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // JPQL
    @Query("SELECT s FROM Student s where s.email = ?1")
    Optional<Student> findStudentByEmail(String email);
//
//    // JPQL with named parameters
//    @Query("SELECT s FROM Student s where s.name = :full_name and s.email = :email")
//    List<Student> findStudentByNameAndEmail(
//            @Param("full_name") String name,
//            @Param("email") String email
//    );
//
//    // Native query with named parameters
//    @Query(value = "SELECT * FROM student WHERE id>=:id", nativeQuery = true)
//    List<Student> selectStudentsWithIdGreaterThanOrEqualNative(@Param("id") Integer id);




    // Always prefer using JPQL with named parameters
    // because native queries are database specific
    // JPQL will run for any database

//    @Transactional
//    @Modifying
//    @Query("DELETE FROM student WHERE id = :student_id")
//    int deleteStudentById(@Param("student_id") Integer id);
}
