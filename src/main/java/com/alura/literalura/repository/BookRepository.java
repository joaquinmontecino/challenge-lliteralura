package com.alura.literalura.repository;

import com.alura.literalura.model.Author;
import com.alura.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(Long id);

    @Query("""
            SELECT DISTINCT b FROM Book b
            JOIN b.languages l
            WHERE :language IN (l)
            """)
    List<Book> findBooksByLanguage(String language);

    @Query("""
            SELECT b from Book b
            ORDER BY b.downloadCount
            DESC LIMIT 10
            """)
    List<Book> findTop10Books();

}
