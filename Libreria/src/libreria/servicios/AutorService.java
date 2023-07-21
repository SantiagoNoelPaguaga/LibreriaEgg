/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.servicios;

import java.util.List;
import libreria.entidades.Autor;
import libreria.excepciones.AutorException;
import libreria.persistencia.AutorDao;
import libreria.utilidades.ScannerUtil;

/**
 *
 * @author SantiagoPaguaga
 */
public class AutorService {
    
    private final AutorDao dao;

    public AutorService() {
        this.dao = new AutorDao();
    }
    
    public void guardarAutor() throws Exception{
        System.out.println("Ingrese el nombre del autor");
        String nombre = ScannerUtil.leerCadena();
        if(nombre == null || nombre.trim().isEmpty()){
            throw new AutorException("Debe ingresar el nombre del autor");
        }
        dao.guardar(new Autor(nombre));
        System.out.println("Autor registrado correctamente!");
    }
    
    public void modificarAutor() throws Exception{
        System.out.println("Ingrese el ID del Autor que desea modificar");
        mostrarAutores();
        Integer id = ScannerUtil.leerNumero();
        if(id == null){
            throw new AutorException("Debe ingresar un ID de autor");
        }
        Autor autor = buscarAutorPorId(id);
        System.out.println("Ingrese el nuevo nombre que tendrá el autor");
        autor.setNombre(ScannerUtil.leerCadena());
        dao.modificar(autor);
        System.out.println("Autor modificado correctamente!");
    }
    
    public void eliminarAutor() throws Exception{
        System.out.println("Ingrese el ID del autor que desea eliminar");
        mostrarAutores();
        Integer id = ScannerUtil.leerNumero();
        if(id == null){
            throw new AutorException("Debe ingresar un ID de autor");
        }
        dao.eliminar(id);
        System.out.println("Autor eliminado correctamente!");
    }
    
    public Autor buscarAutorPorId(Integer id) throws Exception{
        return dao.buscarAutorPorId(id);
    }
    
    public void buscarAutorPorNombre() throws Exception{
        System.out.println("Ingresar el nombre del autor");
        String nombre = ScannerUtil.leerCadena();
        if(nombre == null || nombre.trim().isEmpty()){
            throw new AutorException("Debe ingresar un nombre");
        }
        List<Autor> autores = dao.buscarAutorPorNombre(nombre);
        
        System.out.println("Listado de Autores con el nombre " + nombre + ":");
        for (Autor a : autores) {
            System.out.println(a.toString());
        }
        System.out.println("");
    }
    
    public void mostrarAutores(){
        List<Autor> autores = dao.listadoAutores();
        
        for (Autor a : autores) {
            System.out.println("ID: " + a.getId() + " - Nombre: " + a.getNombre());
        }
        System.out.println("");
    }
    
    public int cantidadAutores() throws Exception{
        return dao.cantidadAutores();
    }
    
}
