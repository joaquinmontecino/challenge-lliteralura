package com.alura.literalura.model;

import com.alura.literalura.dto.BookDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
public class Book {
    @Id
    private Long id;
    private String title;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> languages;
    private int downloadCount;

    @JoinTable(name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Author> authors = new ArrayList<>();

    public Book() {}

    public Book(BookDTO bookDTO){
        this.id = bookDTO.id();
        this.title = bookDTO.title();
        this.languages = bookDTO.languages();
        this.downloadCount = bookDTO.downloadCount();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    @Override
    public String toString() {
        return "~~~~~~~~~LIBRO~~~~~~~~~  " +
                "\n Título: "+this.getTitle()
                +" \n Autor(es): "+  String.join(" y ", this.getAuthors().stream().map(a -> a.getName()).collect(Collectors.toList()))
                +" \n Idioma(s): "+ String.join(", ", this.getLanguages())
                +" \n Número de descargas: "+this.getDownloadCount()
                +" \n~~~~~~~~~~~~~~~~~~~~~~";
    }
}
