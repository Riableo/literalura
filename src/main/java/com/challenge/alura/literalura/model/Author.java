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
    private Author nameAuthor;
    private Date birthDate;
    private Date deceaseDate;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;

    public Author(Author nameAuthor, Date birthDate, Date deceaseDate, List<Book> books) {
        this.nameAuthor = nameAuthor;
        this.birthDate = birthDate;
        this.deceaseDate = deceaseDate;
        this.books = books;
    }

    public Author getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(Author nameAuthor) {
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
                ", books=" + books;
    }
}
