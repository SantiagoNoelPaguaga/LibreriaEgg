/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.persistencia;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import libreria.entidades.Libro;
import libreria.excepciones.DAOException;


/**
 *
 * @author SantiagoPaguaga
 */
public class LibroDao extends DAO<Libro>{
    
    @Override
    public void guardar(Libro l) throws Exception{
            if (l == null) {
                throw new DAOException("Debe ingresar un libro");
            }
            abrirConexion();
            Libro existeLibro = em.find(Libro.class, l.getIsbn());
            cerrarConexion();
            if(existeLibro != null){
                throw new DAOException("Ya existe un libro con el ISBN ingresado");
            }
            super.guardar(l); 
    }
    
    public void modificar(Libro l) throws Exception{
        if(l == null){
            throw new DAOException("Debe ingresar un libro");
        }
        super.modificar(l);
    }
    
    public void eliminar(Long isbn) throws Exception{
        if(isbn == null){
            throw new DAOException("Debe ingresar el ISBN del libro");
        }
        Libro l = buscarLibroPorISBN(isbn);
        super.eliminar(l);
    }
    
    public List<Libro> buscarLibrosPorTitulo(String titulo) throws Exception{
        abrirConexion();
        List<Libro> libros = em.createQuery("SELECT l FROM Libro l WHERE l.titulo = :titulo").setParameter("titulo", titulo).getResultList();
        cerrarConexion();
        if(libros.isEmpty()){
            throw new DAOException("No se encontró ningún libro con el título " + titulo);
        }
        return libros;
    }
    
    public Libro buscarLibroPorISBN(Long isbn) throws Exception{
        try {
            if (isbn == null) {
                throw new DAOException("Debe ingresar el ISBN del libro");
            }
            abrirConexion();
            Libro libro = (Libro) em.createQuery("SELECT l FROM Libro l WHERE l.isbn = :isbn").setParameter("isbn", isbn).getSingleResult();
            cerrarConexion();
            return libro;
        } catch (NoResultException e) {
            throw new DAOException("No se encontró el libro con el ISBN " + isbn);
        } 
    }
    
    public List<Libro> buscarLibrosPorNombreDeAutor(String nombre) throws Exception{
        abrirConexion();
        List<Libro> libros = em.createQuery("SELECT l FROM Libro l WHERE l.autor.nombre = :nombre").setParameter("nombre", nombre).getResultList();
        cerrarConexion();
        if(libros.isEmpty()){
            throw new DAOException("No se encontraron libros para el autor " + nombre);
        }
        return libros;
    }
    
    public List<Libro> buscarLibrosPorNombreDeEditorial(String nombre) throws Exception{
        abrirConexion();
        List<Libro> libros = em.createQuery("SELECT l FROM Libro l WHERE l.editorial.nombre = :nombre").setParameter("nombre", nombre).getResultList();
        cerrarConexion();
        if(libros.isEmpty()){
            throw new DAOException("No se encontraron libros de la editorial " + nombre);
        }
        return libros;
    }
    
    public List<Libro> listadoLibros(){
        abrirConexion();
        List<Libro> libros = em.createQuery("SELECT l FROM Libro l").getResultList();
        cerrarConexion();
        return libros;
    }
    
     public int cantidadLibros() throws Exception {
        abrirConexion();
        Long cantidad = em.createQuery("SELECT COUNT(l) FROM Libro l", Long.class).getSingleResult();
        cerrarConexion();
        return cantidad.intValue(); 
    }
    
}
