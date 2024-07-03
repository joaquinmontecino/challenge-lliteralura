package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AuthorDTO(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") int birthYear,
        @JsonAlias("death_year") int deathYear
) {

}
