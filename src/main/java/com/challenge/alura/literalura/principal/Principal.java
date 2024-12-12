package com.challenge.alura.literalura.principal;

import com.challenge.alura.literalura.model.Author;
import com.challenge.alura.literalura.model.Book;
import com.challenge.alura.literalura.model.DatosBook;
import com.challenge.alura.literalura.model.DatosResponse;
import com.challenge.alura.literalura.repository.IBookRepository;
import com.challenge.alura.literalura.service.ConsumoAPI;
import com.challenge.alura.literalura.service.ConvierteDatos;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/?search=";

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

            try {
                opcion = sc.nextInt();

            }catch(InputMismatchException e){
                System.out.println("ERROR: DataType " + e + " Tipo de dato no es el permitido ingrese un número");
            }

            sc.nextLine();

            switch (opcion) {
                case 1:
                    searchBook();
                    break;
                case 2:
                    findBooks();
                    break;
                case 3:
                    findAuthors();
                    break;
                case 4:
                    //findSerieByTitle();
                    break;
                case 5:
                    findByLanguage();
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

        var datosSerie = conversor.obtenerDatos(json, DatosResponse.class);

        return datosSerie.results().stream()
                .filter(d -> d.title().toUpperCase().contains(libro.toUpperCase()))
                .findFirst()
                .orElse(null);
    }

    private void searchBook() {
        DatosBook datos = getDatosBook();

        if (datos != null){
            Book book = new Book(datos);
            repository.save(book);

            saveAuthor(book.getTitle(), datos);

        }else {
            System.out.println("Libro no encontrado");
        }

    }

    private void saveAuthor(String book, DatosBook datos){
        Book findBook = repository.findByTitle(book);

        if (findBook != null){

            List<Author> author = datos.authors().stream()
                    .map(Author::new)
                    .collect(Collectors.toList());

            findBook.setAuthor(author);
            repository.save(findBook);
        }
    }

    private void findBooks(){
        List<Book> books = repository.findAll();

        books.stream()
                .forEach(System.out::println);
    }

    private void findByLanguage(){
        List<Book> books;
        int lang = -1;

        while (lang != 0){
            String menu = """
                    1. EN - Inglés
                    2. ES - Español
                    3. FR - Francés
                    4. DE - Deutsch
                    
                    0. SALIR
                    """;

            System.out.println(menu);

            try {
                lang = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("ERROR: DataType " + e + " Tipo de dato ingresado no compatible ingrese un número");;
            }
            sc.nextLine();

            switch (lang){

                case 1:
                    books = repository.findByLanguage("en");

                    if (!books.isEmpty()){
                        mssgLanguage("inglés", books);
                    }

                    break;
                case 2:
                    books = repository.findByLanguage("es");

                    if (!books.isEmpty()){
                        mssgLanguage("español", books);
                    }

                    break;
                case 3:
                    books = repository.findByLanguage("fr");
                    if (!books.isEmpty()){
                        mssgLanguage("francés", books);
                    }

                    break;
                case 4:
                    books = repository.findByLanguage("de");
                    if (!books.isEmpty()){
                        mssgLanguage("aleman", books);
                    }

                    break;

                case 0:
                    System.out.println("Saliendo de búsqueda por lenguaje");
                    break;

                default:
                    System.out.println("Opción no valida");
            }
        }

    }

    private void findAuthors(){
        List<Author> authors = repository.findAuthors();

        if (!authors.isEmpty()){

            System.out.println("Autores registrados");
            authors.stream()
                    .forEach(System.out::println);
        }else {
            System.out.println("No hay autores registrados");
        }
    }

    private void mssgLanguage(String lang, List<Book> books){
        System.out.println("Libros en " + lang);
        books.stream()
                .forEach(System.out::println);
    }
}
