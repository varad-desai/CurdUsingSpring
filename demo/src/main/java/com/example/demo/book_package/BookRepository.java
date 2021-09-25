package com.example.demo.book_package;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
