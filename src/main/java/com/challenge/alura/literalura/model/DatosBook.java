package com.challenge.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosBook(
        @JsonAlias("title") String title,
        @JsonAlias("authors") String nameAuthor,
        @JsonAlias("languages") String language,
        @JsonAlias("download_count") int downloads
) {
}
