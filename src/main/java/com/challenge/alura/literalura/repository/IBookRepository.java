package com.challenge.alura.literalura.repository;

import com.challenge.alura.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookRepository extends JpaRepository<Book, Long> {
}