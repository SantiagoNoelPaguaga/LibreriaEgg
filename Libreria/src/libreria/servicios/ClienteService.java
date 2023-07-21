/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.servicios;

import java.util.List;
import libreria.entidades.Cliente;
import libreria.excepciones.ClienteException;
import libreria.persistencia.ClienteDao;
import libreria.utilidades.ScannerUtil;

/**
 *
 * @author SantiagoPaguaga
 */
public class ClienteService {
    
    private final ClienteDao dao;

    public ClienteService() {
        dao = new ClienteDao();
    }
    
    public void guardarCliente() throws Exception{
        System.out.println("Ingrese el DNI del cliente");
        Long documento = ScannerUtil.leerNumeroLong();
        if(documento == null){
            throw new ClienteException("Debe ingresar el DNI");
        }
        System.out.println("Ingrese el nombre del cliente");
        String nombre = ScannerUtil.leerCadena();
        if(nombre == null || nombre.trim().isEmpty()){
            throw new ClienteException("Debe ingresar el nombre");
        }
        System.out.println("Ingrese el apellido del cliente");
        String apellido = ScannerUtil.leerCadena();
        if(apellido == null || apellido.trim().isEmpty()){
            throw new ClienteException("Debe ingresar el apellido");
        }
        System.out.println("Ingrese el teléfono del cliente");
        String telefono = ScannerUtil.leerCadena();
        if(telefono == null || telefono.trim().isEmpty()){
            throw new ClienteException("Debe ingresar el teléfono");
        }
        dao.guardar(new Cliente(documento, nombre, apellido, telefono));
        System.out.println("El cliente fue registrado con éxito!");
    }
    
    public void eliminarCliente() throws Exception{
        System.out.println("Ingrese el ID del cliente que desea eliminar");
        mostrarClientes();
        Integer id = ScannerUtil.leerNumero();
        if(id == null){
            throw new ClienteException("Debe ingresar el ID del cliente");
        }
        dao.eliminar(id);
        System.out.println("Cliente eliminado exitosamente!");
    }
    
    public void modificarCliente() throws  Exception{
        System.out.println("Ingrese el ID del cliente que desea modificar");
        mostrarClientes();
        Integer id = ScannerUtil.leerNumero();
        if(id == null){
            throw new ClienteException("Debe ingresar el ID del cliente");
        }
        Cliente cliente = buscarClientePorId(id);
        int opcion;
        do {            
            System.out.println("Ingrese el campo que desea modificar\n" + 
                               "1. DNI\n" +
                               "2. Nombre\n" +
                               "3. Apellido\n" +
                               "4. Teléfono\n" +
                               "5. Guardar Cambios\n");
            opcion = ScannerUtil.leerNumero();
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nuevo DNI del cliente");
                    Long documento = ScannerUtil.leerNumeroLong();
                    if (documento == null) {
                        throw new ClienteException("Debe ingresar el DNI");
                    }
                    cliente.setDocumento(documento);
                    break;
                case 2:
                    System.out.println("Ingrese el nuevo nombre del cliente");
                    String nombre = ScannerUtil.leerCadena();
                    if (nombre == null || nombre.trim().isEmpty()) {
                        throw new ClienteException("Debe ingresar el nombre");
                    }
                    cliente.setNombre(nombre);
                    break;
                case 3:
                    System.out.println("Ingrese el nuevo apellido del cliente");
                    String apellido = ScannerUtil.leerCadena();
                    if (apellido == null || apellido.trim().isEmpty()) {
                        throw new ClienteException("Debe ingresar el apellido");
                    }
                    cliente.setApellido(apellido);
                    break;
                case 4:
                    System.out.println("Ingrese el nuevo teléfono del cliente");
                    String telefono = ScannerUtil.leerCadena();
                    if (telefono == null || telefono.trim().isEmpty()) {
                        throw new ClienteException("Debe ingresar el teléfono");
                    }
                    cliente.setTelefono(telefono);
                    break;
                case 5:
                    System.out.println("Guardando cambios...");
                    break;
                default:
                    System.out.println("La opción ingresada es incorrecta.");
            }
        } while (opcion != 5);
        dao.modificar(cliente);
        System.out.println("El cliente fue modificado exitosamente!");
    }
    
    public Cliente buscarClientePorId(Integer id) throws Exception{
        return dao.buscarClientePorId(id);
    }
    
    public void mostrarClientes() throws Exception{
        List<Cliente> clientes = dao.listadoClientes();
        
        for (Cliente c : clientes) {
            System.out.println(c.toString());
        }
        System.out.println("");
    }
    
    public int cantidadClientes() throws Exception{
        return dao.cantidadClientes();
    }
}
