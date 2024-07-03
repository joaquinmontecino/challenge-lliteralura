package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDTO(
        @JsonAlias("id") Long id,
        @JsonAlias("title") String title,
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("download_count") int downloadCount,
        @JsonAlias("authors") List<AuthorDTO> authors
) {
}
