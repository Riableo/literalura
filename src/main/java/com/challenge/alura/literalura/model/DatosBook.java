package com.challenge.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosBook(
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<DatosAuthor> authors,
        @JsonAlias("languages") List<String> language,
        @JsonAlias("download_count") int downloads
) {
}
