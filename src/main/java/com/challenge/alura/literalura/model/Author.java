package com.challenge.alura.literalura.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameAuthor;
    private Date birthDate;
    private Date deceaseDate;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getDeceaseDate() {
        return deceaseDate;
    }

    public void setDeceaseDate(Date deceaseDate) {
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
