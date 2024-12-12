package com.challenge.alura.literalura.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameAuthor;
    private Integer birthDate;
    private Integer deceaseDate;
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private List<Book> books;

    public Author(){}

    public Author(DatosAuthor datosAuthor) {
        this.nameAuthor = datosAuthor.nameAuthor();
        this.birthDate = datosAuthor.birthDate();
        this.deceaseDate = datosAuthor.deceasingDate();
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public Integer getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Integer birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getDeceaseDate() {
        return deceaseDate;
    }

    public void setDeceaseDate(Integer deceaseDate) {
        this.deceaseDate = deceaseDate;
    }

    public List<Book> getBooks(){
        return books;
    }

    public void setBooks(List<Book> books){
        this.books = books;
    }

    @Override
    public String toString() {
        return " nameAuthor=" + nameAuthor +
                ", birthDate=" + birthDate +
                ", deceaseDate=" + deceaseDate +
                ", books=" + books.stream().map(Book::getTitle).toList();
    }
}
