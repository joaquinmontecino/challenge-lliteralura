package com.alura.literalura.service;

import com.alura.literalura.dto.AuthorDTO;
import com.alura.literalura.dto.BookDTO;
import com.alura.literalura.model.Author;
import com.alura.literalura.model.Book;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;

@Service
public class DBService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public void saveBook(BookDTO bookDTO) {
        Optional<Book> requestedBook = bookRepository.findById(bookDTO.id());
        if (requestedBook.isPresent()){
            System.out.println("El libro ya fue registrado en el sistema previamente.");
        }
        else {
            List<Author> authors = addAuthors(bookDTO.authors());
            Book book = new Book(bookDTO);
            for (Author a : authors){
                book.addAuthor(a);
            }
            bookRepository.save(book);
            System.out.println(book);
        }
    }

    public List<Author> addAuthors(List<AuthorDTO> authorsDTO){
        List<Author> authors = new ArrayList<>();
        for (AuthorDTO a : authorsDTO){
            Author author = authorRepository.findAuthor(a.name(), a.birthYear(), a.deathYear());
            if (author == null){
                authors.add(new Author(a));
            } else {
                authors.add(author);
            }
        }
        return authors;
    }

    public void printStoredBooks() {
        List<Book> books = bookRepository.findAll();
        printList(books);
    }

    public void printStoredAuthors() {
        List<Author> authors = authorRepository.findAll();
        printList(authors);
    }

    public void getAuthorsAliveIn(int year) {
        List<Author> authors = authorRepository.findAuthorsAliveIn(year);
        printList(authors);
    }

    public void getBooksByLanguage(String language) {
        List<Book> books = bookRepository.findBooksByLanguage(language);
        if (books.isEmpty()){
            System.out.println("No se encontró ningún libro en el idioma que especificaste.");
        }
        else {
            printList(books);
        }
    }

    public void getStatistics() {
        List<Book> books = bookRepository.findAll();
        DoubleSummaryStatistics stats = new DoubleSummaryStatistics();
        for (Book book : books){
            stats.accept(book.getDownloadCount());
        }
        System.out.println("ESTADISTICAS");
        System.out.println("Cantidad de libros registrados: " + stats.getCount());
        System.out.println("Media de descargas: " + stats.getAverage());
        System.out.println("Mayor número de descargas: " + stats.getMax());
        System.out.println("Menor número de descargas: " + stats.getMin());
        System.out.println("Suma de descargas de los libros registrados: " + stats.getSum());

    }

    public void getTop10Books() {
        List<Book> books = bookRepository.findTop10Books();
        int i = 1;
        for (Book book : books){
            System.out.println(i + ". " + book.getTitle() +
                    "\n\t Descargas: " + book.getDownloadCount());
            i++;
        }
    }

    public void getAuthorsByName(String name) {
        List<Author> authors = authorRepository.findAuthorsByName(name.toLowerCase());
        if (authors.isEmpty()){
            System.out.println("No se encontró ningún autor con el nombre que especificaste.");
        }
        else {
            printList(authors);
        }
    }

    public <T> void printList(List<T> list){
        for (T item : list){
            System.out.println(item);
        }
    }
}
