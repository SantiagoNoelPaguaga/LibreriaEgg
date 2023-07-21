/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.persistencia;

import java.util.List;
import libreria.entidades.Cliente;
import libreria.excepciones.DAOException;

/**
 *
 * @author SantiagoPaguaga
 */
public class ClienteDao extends DAO<Cliente>{
    
    @Override
    public void guardar(Cliente cliente) throws Exception{
        if(cliente == null){
            throw new DAOException("Debe ingresar el cliente");
        }
        super.guardar(cliente);
    }
    
    public void eliminar(Integer id) throws Exception{
        Cliente cliente = buscarClientePorId(id);
        super.eliminar(cliente);
    }
    
    @Override
    public void modificar(Cliente cliente) throws Exception{
        if(cliente == null){
            throw new DAOException("Debe ingresar un cliente");
        }
        super.modificar(cliente);
    }
    
    public Cliente buscarClientePorId(Integer id) throws Exception{
        if(id == null){
            throw new DAOException("Debe ingresar el ID del cliente");
        }
        abrirConexion();
        Cliente cliente = em.find(Cliente.class, id);
        cerrarConexion();
        if(cliente == null){
            throw new DAOException("No se encontró el cliente con la ID " + id);
        }
        return cliente;
    }
    
    public List<Cliente> listadoClientes() throws Exception{
        abrirConexion();
        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c").getResultList();
        cerrarConexion();
        if(clientes.isEmpty()){
            throw new DAOException("No se encontraron clientes");
        }
        return clientes;
    }
    
     public int cantidadClientes() throws Exception{
        abrirConexion();
        Long cantidad = em.createQuery("SELECT COUNT(c) FROM Cliente c", Long.class).getSingleResult();
        cerrarConexion();
        return cantidad.intValue();
    }
}
