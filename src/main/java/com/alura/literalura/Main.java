package com.alura.literalura;

import com.alura.literalura.dto.BookDTO;
import com.alura.literalura.dto.QueryResultDTO;
import com.alura.literalura.service.DBService;
import com.alura.literalura.service.DataConverter;
import com.alura.literalura.service.RequestAPI;

import java.util.List;
import java.util.Scanner;

public class Main {

    private Scanner scanner = new Scanner(System.in);
    private RequestAPI connection = new RequestAPI();
    private DataConverter converter = new DataConverter();
    private String URL = "https://gutendex.com/books/";
    private DBService service = new DBService();
    public Main(DBService service) {
        this.service = service;
    }

    public void printMenu(){
        int option = -1;
        String menu = """
                =====================================================
                =====================================================
                                    MENU
                    1) Buscar libro por titulo
                    2) Mostrar todos los libros registrados
                    3) Mostrar todos los autores registrados
                    4) Mostrar autores vivos en un año determinado
                    5) Mostrar libros según idioma
                    6) Mostrar estadisticas de los libros registrados
                    7) Mostrar TOP 10 Libros según cantidad de descargas
                    8) Buscar autores por su nombre
                    0) Salir del programa
                =====================================================
                =====================================================
                """;

        while (option != 0){
            System.out.println(menu);
            try {
                System.out.println("Ingrese una opción: ");
                option = Integer.valueOf(scanner.nextLine());
                switch (option){
                    case 1:
                        searchBookByTitle();
                        break;
                    case 2:
                        service.printStoredBooks();
                        break;
                    case 3:
                        service.printStoredAuthors();
                        break;
                    case 4:
                        printAuthorsAliveInYear();
                        break;
                    case 5:
                        printBooksByLanguage();
                        break;
                    case 6:
                        service.getStatistics();
                        break;
                    case 7:
                        service.getTop10Books();
                        break;
                    case 8:
                        getAuthorsByName();
                        break;
                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no valida");
                        break;
                }

            }catch (NumberFormatException e){
                System.out.println("Opción no valida: " + e.getMessage());
            }
        }
        System.out.println("Programa terminado.");
        System.exit(0);
    }

    private void getAuthorsByName() {
        System.out.println("Ingrese el nombre del autor que desea buscar: ");
        String name = scanner.nextLine();
        service.getAuthorsByName(name);
    }

    private void printBooksByLanguage() {
        System.out.println("""
                Idiomas disponibles:
                es - Español
                en - Inglés
                fr - Francés
                pt - Portugués
                
                Ingrese el idioma de los libros que desea ver: 
                """);
        String language = scanner.nextLine();
        service.getBooksByLanguage(language);
    }

    private void printAuthorsAliveInYear() {
        int year = 0;
        boolean validInput = false;
        while (!validInput){
            try{
                System.out.println("Ingrese el año en el que desea ver los autores que estaban vivos: ");
                year = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e){
                System.out.println("Entrada invalida. Por favor, ingrese un año valido.");
            }
        }
        service.getAuthorsAliveIn(year);
    }


    private void searchBookByTitle() {
        System.out.println("Ingrese el nombre del libro que desea buscar: ");
        String title = scanner.nextLine();
        String json = connection.getData(URL + "?search=" + title.replace(" ", "+").toLowerCase());

        if(json.isEmpty() || json.contains("\"count\":0")){
            System.out.println("Libro no encontrado");
        }
        else {
            QueryResultDTO queryResults = converter.convert(json, QueryResultDTO.class);
            List<BookDTO> booksResults = queryResults.results();
            service.saveBook(booksResults.get(0));
        }
    }
}
