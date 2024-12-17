package com.challenge.alura.literalura.repository;

import com.challenge.alura.literalura.model.Author;
import com.challenge.alura.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IBookRepository extends JpaRepository<Book, Long> {

    Book findByTitle(String book);

    @Query("SELECT b FROM Book b JOIN FETCH b.authors WHERE UPPER(b.title) = :book")
    Book findExistBook(String book);

    @Query("SELECT b FROM Book b JOIN FETCH b.authors")
    List<Book> findAll();

    @Query("SELECT b FROM Book b JOIN FETCH b.authors a WHERE b.language = :language")
    List<Book> findByLanguage(String language);

    @Query("SELECT a FROM Author a LEFT JOIN FETCH a.books b")
    List<Author> findAuthors();

    @Query("SELECT a FROM Author a LEFT JOIN FETCH a.books b WHERE a.nameAuthor = :nameAuthor")
    List<Author> findAuthor(String nameAuthor);

    @Query("SELECT a FROM Author a LEFT JOIN FETCH a.books b WHERE a.birthDate <= :year AND a.deceaseDate >= :year")
    List<Author> findAuthorsAlive(Integer year);

    @Query("SELECT b FROM Book b JOIN FETCH b.authors a ORDER BY b.downloads DESC LIMIT 10")
    List<Book> findBookdownload();

    @Query("SELECT a FROM Author a LEFT JOIN FETCH a.books b WHERE a.nameAuthor LIKE %:nameAuthor%")
    Optional<Author> findAuthorByName(String nameAuthor);
}