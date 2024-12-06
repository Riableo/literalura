package com.challenge.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAuthor(
        @JsonAlias("name") String nameAuthor,
        @JsonAlias("birth_year") Date birthDate,
        @JsonAlias("death_year") Date deceasingDate
) {
}
