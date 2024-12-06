package com.challenge.alura.literalura.principal;

import com.challenge.alura.literalura.model.DatosBook;
import com.challenge.alura.literalura.repository.IBookRepository;
import com.challenge.alura.literalura.service.ConsumoAPI;
import com.challenge.alura.literalura.service.ConvierteDatos;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Scanner;

public class Principal {

    private static final String URL_BASE = "http://www.gutendex.com/books?search=";

    private IBookRepository repository;
    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    public Principal(IBookRepository repository) {
        this.repository = repository;
    }

    public void showMenu(){
        var opcion = -1;

        while (opcion != 0){
            var menu = """
                    1 - Buscar libro
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un año especifico
                    5 - Listar libros por idiomas
                    
                    0 - Salir
                    """;

            System.out.println(menu);
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    searchBook();
                    break;
                case 2:
                    //buscarEpisodio();
                    break;
                case 3:
                    //showSeries();
                    break;
                case 4:
                    //findSerieByTitle();
                    break;
                case 5:
                    //findTop5Series();
                    break;

                case 0:
                    System.out.println("Cerrando aplicacion....");
                    break;

                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    private DatosBook getDatosBook() {
        System.out.println("Ingrese el titulo a buscar");
        String libro = sc.nextLine();

        // Get info about Book
        String bookEncode = null;
        try {
            bookEncode = URLEncoder.encode(libro, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("ERROR: No codificable " + e);
        }

        String json = consumoAPI.obtenerDatos(URL_BASE + bookEncode);

        var datosSerie = conversor.obtenerDatos(json, DatosBook.class);

        return datosSerie;
    }

    private void searchBook() {
        DatosBook datos = getDatosBook();
        System.out.println(datos);
    }
}
