package com.alura.literalura.repository;

import com.alura.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("""
            SELECT a from Author a
            WHERE a.name LIKE :name%
            AND a.birthYear = :birthYear
            AND a.deathYear = :deathYear
            """)
    Author findAuthor(String name, int birthYear, int deathYear);


    @Query("""
            SELECT a from Author a
            WHERE a.deathYear >= :year
            """)
    List<Author> findAuthorsAliveIn(int year);

    @Query("""
            SELECT a from Author a
            WHERE LOWER(a.name) LIKE %:name%
            """)
    List<Author> findAuthorsByName(String name);
}
