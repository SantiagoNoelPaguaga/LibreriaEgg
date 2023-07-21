/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.persistencia;

import java.util.List;
import javax.persistence.NoResultException;
import libreria.entidades.Editorial;
import libreria.excepciones.DAOException;

/**
 *
 * @author SantiagoPaguaga
 */
public class EditorialDao extends DAO<Editorial>{
    
    @Override
    public void guardar(Editorial e) throws Exception{
        if(e == null){
            throw new DAOException("Debe ingresar una editorial");
        }
        super.guardar(e);
    }
    
    public void modificar(Editorial e) throws Exception{
        if(e == null){
            throw new DAOException("Debe ingresar una editorial");
        }
        super.modificar(e);
    }
    
    public void eliminar(Integer id) throws Exception{
        if(id == null){
            throw new DAOException("Debe ingresar la ID de la Editorial");
        }
        Editorial e = buscarEditorialPorId(id);
        super.eliminar(e);
    }
    
    public Editorial buscarEditorialPorId(Integer id) throws Exception{
        try {
            if (id == null) {
                throw new DAOException("Debe ingresar el ID de la Editorial");
            }
            abrirConexion();
            Editorial editorial = (Editorial) em.createQuery("SELECT e FROM Editorial e WHERE e.id = :id").setParameter("id", id).getSingleResult();
            cerrarConexion();
            return editorial;
        } catch (NoResultException e) {
            throw new DAOException("No se encontró la editorial con el ID " + id);
        }  
    }
    
    public List<Editorial> listadoEditoriales(){
        abrirConexion();
        List<Editorial> editoriales = em.createQuery("SELECT e FROM Editorial e").getResultList();
        cerrarConexion();
        return editoriales;
    }
    
     public int cantidadEditoriales() throws Exception{
        abrirConexion();
        Long cantidad = em.createQuery("SELECT COUNT(e) FROM Editorial e", Long.class).getSingleResult();
        cerrarConexion();
        return cantidad.intValue();
    }
}
