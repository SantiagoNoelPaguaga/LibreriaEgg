/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.persistencia;

import java.util.List;
import libreria.entidades.Prestamo;
import libreria.excepciones.DAOException;

/**
 *
 * @author SantiagoPaguaga
 */
public class PrestamoDao extends DAO<Prestamo>{
    
    @Override
    public void guardar(Prestamo p) throws Exception{
        if(p == null){
            throw new DAOException("Debe ingresar un préstamo");
        }
        super.guardar(p);
    }
    
    @Override
    public void modificar(Prestamo p) throws Exception{
        if(p == null){
            throw new DAOException("Debe ingresar un préstamo");
        }
        super.modificar(p);
    }
    
    public void eliminar(Integer id) throws Exception{
        Prestamo p = buscarPrestamoPorId(id);
        super.eliminar(p);
    }
    
    public Prestamo buscarPrestamoPorId(Integer id) throws Exception{
        if(id == null){
            throw new DAOException("Debe ingresar un ID de Préstamo");
        }
        abrirConexion();
        Prestamo prestamo = em.find(Prestamo.class, id);
        cerrarConexion();
        if(prestamo == null){
            throw new DAOException("No se encontró ningún préstamo con el ID " + id);
        }
        return prestamo;
    }
    
    public List<Prestamo> listadoPrestamosActivos() throws Exception{
        abrirConexion();
        //Solo vamos a listar los préstamos que se encuentren activos, es decir con su valor del atributo alta = true
        //Ya que cuando un cliente devuelve el libro el préstamo debería finalizarse, para indicar de alguna forma
        //que el Cliente ya realizó la devolución, cuando eso suceda cambiaremos el valor de alta = false
        List<Prestamo> prestamos = em.createQuery("SELECT p FROM Prestamo p WHERE p.alta = :alta").setParameter("alta", true).getResultList();
        cerrarConexion();
        if(prestamos.isEmpty()){
            throw new DAOException("No se encontraron préstamos");
        }
        return prestamos;
    }
    
    public List<Prestamo> listadoPrestamosFinalizados() throws Exception{
        abrirConexion();
        //Solo vamos a listar los préstamos que se encuentren finalizados, es decir con su valor del atributo alta = false
        //Ya que cuando un cliente devuelve el libro el préstamo debería finalizarse, para indicar de alguna forma
        //que el Cliente ya realizó la devolución, cuando eso suceda cambiaremos el valor de alta = false
        List<Prestamo> prestamos = em.createQuery("SELECT p FROM Prestamo p WHERE p.alta = :alta").setParameter("alta", false).getResultList();
        cerrarConexion();
        if(prestamos.isEmpty()){
            throw new DAOException("No se encontraron préstamos");
        }
        return prestamos;
    }
    
    public List<Prestamo> listadoPrestamosActivosParaUnCliente(Integer id) throws Exception{
        abrirConexion();
        List<Prestamo> prestamos = em.createQuery("SELECT p FROM Prestamo p WHERE p.alta = :alta AND p.cliente.id = :id").setParameter("alta", true).setParameter("id", id).getResultList();
        cerrarConexion();
        if(prestamos.isEmpty()){
            System.out.println("No se encontraron préstamos activos para el cliente");
        }
        return prestamos;
    }
 
    public List<Prestamo> listadoPrestamosFinalizadosParaUnCliente(Integer id) throws Exception{
        abrirConexion();
        List<Prestamo> prestamos = em.createQuery("SELECT p FROM Prestamo p WHERE p.alta = :alta AND p.cliente.id = :id").setParameter("alta", false).setParameter("id", id).getResultList();
        cerrarConexion();
        if(prestamos.isEmpty()){
            System.out.println("No se encontraron préstamos finalizados para el cliente");
        }
        return prestamos;
    }
    
}
