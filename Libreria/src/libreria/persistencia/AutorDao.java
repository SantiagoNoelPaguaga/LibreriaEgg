/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.persistencia;

import java.util.List;
import javax.persistence.NoResultException;
import libreria.entidades.Autor;
import libreria.excepciones.DAOException;

/**
 *
 * @author SantiagoPaguaga
 */
public class AutorDao extends DAO<Autor>{
    
    @Override
    public void guardar(Autor a) throws Exception{
        if(a == null){
            throw new DAOException("Debe ingresar un autor");
        }
        super.guardar(a);
    }
    
    public void modificar(Autor a) throws Exception{
        if(a == null){
            throw new DAOException("Debe ingresar un autor");
        }
        super.modificar(a);
    }
    
    public void eliminar(Integer id) throws Exception{
        if(id == null){
            throw new DAOException("Debe ingresar la ID del Autor");
        }
        Autor a = buscarAutorPorId(id);
        super.eliminar(a);
    }
    
    public Autor buscarAutorPorId(Integer id) throws Exception{
        try {
            if (id == null) {
                throw new DAOException("Debe ingresar la ID del Autor");
            }
            abrirConexion();
            Autor autor = (Autor) em.createQuery("SELECT a FROM Autor a WHERE a.id = :id").setParameter("id", id).getSingleResult();
            cerrarConexion();
            return autor;
        } catch (NoResultException e) {
            throw new DAOException("No se encontró el autor con la ID " + id);
        }  
    }
    
    public List<Autor> buscarAutorPorNombre(String nombre) throws Exception{
        if(nombre == null){
            throw new DAOException("Debe ingresar el nombre del autor.");
        }
        abrirConexion();
        List<Autor> autores = em.createQuery("SELECT a FROM Autor a WHERE a.nombre = :nombre").setParameter("nombre", nombre).getResultList();
        cerrarConexion();
        if(autores.isEmpty()){
            throw new DAOException("No se encontró ningún autor con el nombre " + nombre);
        }
        return autores;
    }
    
    public List<Autor> listadoAutores(){
        abrirConexion();
        List<Autor> autores = em.createQuery("SELECT a FROM Autor a").getResultList();
        cerrarConexion();
        return autores;
    }
    
    public int cantidadAutores() throws Exception{
        abrirConexion();
        Long cantidad = em.createQuery("SELECT COUNT(a) FROM Autor a", Long.class).getSingleResult();
        cerrarConexion();
        return cantidad.intValue();
    }
}
