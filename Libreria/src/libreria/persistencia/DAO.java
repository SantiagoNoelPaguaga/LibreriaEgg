/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author SantiagoPaguaga
 * @param <T>
 */
public class DAO<T> {
    
    protected final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("LibreriaPU");
    protected EntityManager em = EMF.createEntityManager();
    
    protected void abrirConexion(){
        if(!em.isOpen()){
            em = EMF.createEntityManager();
        }
    }
    
    protected void cerrarConexion(){
        if(em.isOpen()){
            em.close();
        }
    }
    
    protected void guardar(T objeto) throws Exception{
        abrirConexion();
        em.getTransaction().begin();
        em.persist(objeto);
        em.getTransaction().commit();
        cerrarConexion();
    }
    
    protected void modificar(T objeto) throws Exception{
        abrirConexion();
        em.getTransaction().begin();
        em.merge(objeto);
        em.getTransaction().commit();
        cerrarConexion();
    }
    
    protected void eliminar(T objeto) throws Exception{
        abrirConexion();
        em.getTransaction().begin();
        T objetoAdministrado = em.merge(objeto);
        em.remove(objetoAdministrado);
        em.getTransaction().commit();
        cerrarConexion();
    }
    
}
