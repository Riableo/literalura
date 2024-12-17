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
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/?search=";

    private final IBookRepository repository;
    private final Scanner sc = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();

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
                    6 - Top 10 libros mas descargados
                    7 - Buscar autor por nombre
                    
                    0 - Salir
                    """;

            System.out.println(menu);

            try {
                opcion = sc.nextInt();

            }catch(InputMismatchException e){
                System.out.println("ERROR: DataType " + e + " Tipo de dato no es el permitido ingrese un número");
                opcion = -1;
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
                    findAuthorsAlive();
                    break;
                case 5:
                    findByLanguage();
                    break;
                case 6:
                    findBookDownload();
                    break;
                case 7:
                    findByAuthorName();
                    break;

                case 0:
                    System.out.println("Cerrando aplicacion....");
                    break;

                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    private DatosBook getDatosBook(String libro) {

        // Get info about Book
        String bookEncode;
        bookEncode = URLEncoder.encode(libro, StandardCharsets.UTF_8);

        String json = consumoAPI.obtenerDatos(URL_BASE + bookEncode);

        var datosBook = conversor.obtenerDatos(json, DatosResponse.class);

        return datosBook.results().stream()
                .filter(d -> d.title().toUpperCase().contains(libro.toUpperCase()))
                .findFirst()
                .orElse(null);
    }

    private void searchBook() {
        System.out.println("Ingrese el titulo a buscar");
        String libro = sc.nextLine();

        Book existsBook = existBook(libro.toUpperCase());

        if (existsBook != null){

            System.out.println("Libro existente " + libro);
            System.out.printf("""
                    ********    ********
                    * Author: %s       *
                    * Book: %s         *
                    * Language: %s     *
                    * Downloads: %s    *
                    ********************
                    """, existsBook.getAuthor().get(0).getNameAuthor(), existsBook.getTitle(), existsBook.getLanguage(), existsBook.getDownloads());

        }else {

            DatosBook datos = getDatosBook(libro);

            if (datos != null){

                Book book = new Book(datos);
                repository.save(book);

                saveAuthor(book.getTitle(), datos);

            }else {
                System.out.println("Libro no encontrado");
            }
        }


    }

    private Book existBook(String book){
        return repository.findExistBook(book);
    }

    private void saveAuthor(String book, DatosBook datos){
        List<Author> author;
        Book findBook = repository.findByTitle(book);

        if (findBook != null){

            List<Author> authorExist = findAuthor(datos.authors().get(0).nameAuthor());

            if (authorExist.isEmpty()){

                author = datos.authors().stream()
                        .map(Author::new)
                        .collect(Collectors.toList());

                findBook.setAuthor(author);
                repository.save(findBook);
            }else {
                findBook.setAuthor(authorExist);
                repository.save(findBook);
            }
        }
    }

    private List<Author> findAuthor(String nameAuthor){
        return repository.findAuthor(nameAuthor);
    }

    private void findBooks(){
        List<Book> books = repository.findAll();

        books.forEach(System.out::println);
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
                System.out.println("ERROR: DataType " + e + " Tipo de dato ingresado no compatible ingrese un número");
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

            System.out.println("Autores registrados \n");
            authors.forEach(System.out::println);
        }else {
            System.out.println("No hay autores registrados \n");
        }
    }

    private void findAuthorsAlive(){
        System.out.println("Ingrese el año especifico para buscar autores vivos");
        Integer year = sc.nextInt();

        List<Author> authors = repository.findAuthorsAlive(year);

        if (!authors.isEmpty()){

            System.out.println("Autores con vida en " + year + " : \n");
            authors.forEach(System.out::println);

        }else{
            System.out.println("No se encontraron autores vivos \n");
        }
    }

    private void findBookDownload(){
        int counter = 1;
        List<Book> books = repository.findBookdownload();

        System.out.println("Top 10 Libros mas descargados \n");

        for (Book book : books) {
            System.out.println(counter + ". Libro " + book.getTitle() + " - Descargas " + book.getDownloads() + "\n");
            counter++;
        }
    }

    private void findByAuthorName(){
        System.out.println("Ingrese nombre de autor");
        String nameAuthor = sc.nextLine();

        Optional<Author> authors = repository.findAuthorByName(nameAuthor);

        if (authors.isPresent()){
            authors.ifPresent(System.out::println);
        }else {
            System.out.println("No se ha encontrado ningún autor con el nombre: " + nameAuthor);
        }
    }

    private void mssgLanguage(String lang, List<Book> books){
        System.out.println("Libros en " + lang + "\n");
        books.forEach(System.out::println);
    }
}
