/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.servicios;

import java.util.List;
import libreria.entidades.Autor;
import libreria.entidades.Editorial;
import libreria.entidades.Libro;
import libreria.excepciones.LibroException;
import libreria.persistencia.LibroDao;
import libreria.utilidades.ScannerUtil;

/**
 *
 * @author SantiagoPaguaga
 */
public class LibroService {
    
    private final LibroDao dao;
    private final AutorService as;
    private final EditorialService es;
    
    public LibroService(AutorService as, EditorialService es) {
        this.dao = new LibroDao();
        this.as = as;
        this.es = es;
    }
    
    public void guardarLibro() throws Exception{
        if(as.cantidadAutores() == 0 && es.cantidadEditoriales() == 0){
            throw new LibroException("Aún no hay autores ni editoriales cargados para poder asignarle al libro, primero debe agregar un autor y una editorial");
        }
        if(as.cantidadAutores() == 0){
            throw new LibroException("Aún no hay autores cargados para poder asignarle al libro, primero debe agregar un autor");
        }
        if(es.cantidadEditoriales() == 0){
            throw new LibroException("Aún no hay Editoriales cargadas para poder asignarle al libro, primero debe agregar una editorial");
        }
        System.out.println("Ingrese el ID del Autor");
        as.mostrarAutores();
        Integer idAutor = ScannerUtil.leerNumero();
        Autor autor = as.buscarAutorPorId(idAutor);
        if(autor == null){
            throw new LibroException("Debe ingresar un autor");
        }
        System.out.println("Ingrese el ID de la Editorial");
        es.mostrarEditoriales();
        Integer idEditorial = ScannerUtil.leerNumero();
        Editorial editorial = es.buscarEditorialPorId(idEditorial);
        if(editorial == null){
            throw new LibroException("Debe ingresar una editorial");
        }
        System.out.println("Ingrese el ISBN del libro");
        Long isbn = ScannerUtil.leerNumeroLong();
        if(isbn == null){
            throw new LibroException("Debe ingresar el ISBN");
        }
        System.out.println("Ingrese el título del libro");
        String titulo = ScannerUtil.leerCadena();
        if(titulo == null || titulo.trim().isEmpty()){
            throw new LibroException("Debe ingresar el título");
        }
        System.out.println("Ingrese el año del libro");
        Integer anio = ScannerUtil.leerNumero();
        if(anio == null){
            throw new LibroException("Debe ingresar el año");
        }
        System.out.println("Ingrese la cantidad de ejemplares del libro");
        Integer ejemplares = ScannerUtil.leerNumero();
        if(ejemplares == null){
            throw new LibroException("Debe ingresar la cantidad de ejemplares");
        }      
        Libro l = new Libro(isbn, titulo, anio, ejemplares, autor, editorial);
        dao.guardar(l);
        System.out.println("Libro registrado con éxito!");
    }
    
    public void modificarLibro() throws Exception{
        int opcion;
        System.out.println("Ingrese el ISBN del libro que desea modificar");
        mostrarLibros();
        Long isbn = ScannerUtil.leerNumeroLong();
        if(isbn == null){
            throw new LibroException("Debe ingresar el ISBN");
        }
        Libro l = buscarLibroPorISBN(isbn);
        do {            
            System.out.println("Elija que campo del libro quiere modificar\n" +
                               "1. Título\n" +
                               "2. Año\n" +
                               "3. Autor\n" +
                               "4. Editorial\n" +
                               "5. Confirmar Cambios\n"); 
            opcion = ScannerUtil.leerNumero();
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el título del libro");
                    String titulo = ScannerUtil.leerCadena();
                    if (titulo == null || titulo.trim().isEmpty()) {
                        throw new LibroException("Debe ingresar el título");
                    }
                    l.setTitulo(titulo);
                    break;
                case 2:
                    System.out.println("Ingrese el año del libro");
                    Integer anio = ScannerUtil.leerNumero();
                    if (anio == null) {
                        throw new LibroException("Debe ingresar el año");
                    }
                    l.setAnio(anio);
                    break;
                case 3:
                    System.out.println("Ingrese el ID del nuevo Autor");
                    as.mostrarAutores();
                    Integer idAutor = ScannerUtil.leerNumero();
                    Autor autor = as.buscarAutorPorId(idAutor);
                    if (autor == null) {
                        throw new LibroException("Debe ingresar un autor");
                    }
                    l.setAutor(autor);
                    break;
                case 4:
                    System.out.println("Ingrese el ID de la nueva Editorial");
                    es.mostrarEditoriales();
                    Integer idEditorial = ScannerUtil.leerNumero();
                    Editorial editorial = es.buscarEditorialPorId(idEditorial);
                    if (editorial == null) {
                        throw new LibroException("Debe ingresar una editorial");
                    }
                    l.setEditorial(editorial);
                    break;
                case 5:
                    System.out.println("Guardando cambios...");
                    break;
                default:
                    System.out.println("La opción ingresada es incorrecta");
            }
        } while (opcion != 5);
        dao.modificar(l);
        System.out.println("Libro modificado exitosamente!");
    }
    
    public void eliminarLibro() throws Exception{
        System.out.println("Ingrese el ISBN del libro que desea eliminar");
        mostrarLibros();
        Long isbn = ScannerUtil.leerNumeroLong();
        if(isbn == null){
            throw new LibroException("Debe ingresar el ISBN");
        }
        dao.eliminar(isbn);
        System.out.println("Libro eliminado exitosamente!");
    }
    
    public void mostrarLibros(){
        List<Libro> libros = dao.listadoLibros();
        
        for (Libro l : libros) {
            System.out.println("ISBN: " + l.getIsbn() + " - Título: " + l.getTitulo() + " - Autor: " + l.getAutor().getNombre() + " - Editorial: " + l.getEditorial().getNombre());
        }
        System.out.println("");
    }
    
    public void buscarLibrosPorTitulo() throws Exception{
        System.out.println("Ingresa el título del libro");
        String titulo = ScannerUtil.leerCadena();
        if(titulo == null || titulo.trim().isEmpty()){
            throw new LibroException("Debe ingresar un título");
        }
        List<Libro> libros = dao.buscarLibrosPorTitulo(titulo);
        
        for (Libro l : libros) {
            System.out.println(l.toString());
        }
        System.out.println("");
    }
    
    public Libro buscarLibroPorISBN(Long isbn) throws Exception{
        return dao.buscarLibroPorISBN(isbn);
    }
    
    public void mostrarLibroPorISBN() throws Exception{
        System.out.println("Ingresa el ISBN del libro");
        Long isbn = ScannerUtil.leerNumeroLong();
        if(isbn == null){
            throw new LibroException("Debe ingresar el ISBN");
        }
        Libro libro = buscarLibroPorISBN(isbn);
        System.out.println(libro.toString() + "\n");
    }
    
    public void buscarLibrosPorNombreDeAutor() throws Exception{
        System.out.println("Ingrese el nombre del autor");
        String nombre = ScannerUtil.leerCadena();
        if(nombre == null || nombre.isEmpty()){
            throw new LibroException("Debe ingresar un nombre de autor");
        }
        List<Libro> libros = dao.buscarLibrosPorNombreDeAutor(nombre);
        
        for (Libro l : libros) {
            System.out.println(l.toString());
        }
        System.out.println("");
    }
    
    public void buscarLibrosPorNombreDeEditorial() throws Exception{
        System.out.println("Ingrese el nombre de la editorial");
        String nombre = ScannerUtil.leerCadena();
        if(nombre == null || nombre.isEmpty()){
            throw new LibroException("Debe ingresar un nombre de editorial");
        }
        List<Libro> libros = dao.buscarLibrosPorNombreDeEditorial(nombre);
        
        for (Libro l : libros) {
            System.out.println(l.toString());
        }
        System.out.println("");
    }
    
    //Métodos para modificar ejemplares enviando un libro por parámetro
    public void modificarEjemplares(Libro libro) throws Exception{
        dao.modificar(libro);
    }
    
    public int cantidadLibros() throws Exception{
        return dao.cantidadLibros();
    }
}
