/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.servicios;

import java.util.List;
import libreria.entidades.Editorial;
import libreria.excepciones.EditorialException;
import libreria.persistencia.EditorialDao;
import libreria.utilidades.ScannerUtil;

/**
 *
 * @author SantiagoPaguaga
 */
public class EditorialService {
    
    private final EditorialDao dao;

    public EditorialService() {
        dao = new EditorialDao();
    }
    
    public void guardarEditorial() throws Exception{
        System.out.println("Ingrese el nombre de la editorial");
        String nombre = ScannerUtil.leerCadena();
        if(nombre == null || nombre.trim().isEmpty()){
            throw new EditorialException("Debe ingresar un nombre");
        }
        dao.guardar(new Editorial(nombre));
        System.out.println("Editorial registrada correctamente!");
    }
    
    public void modificarEditorial() throws Exception{
        System.out.println("Ingrese el ID de la editorial que desea modificar");
        mostrarEditoriales();
        Integer id = ScannerUtil.leerNumero();
        if(id == null){
            throw new EditorialException("Debe ingresar el ID de la editorial");
        }
        Editorial e = buscarEditorialPorId(id);
        System.out.println("Ingrese el nuevo nombre de la editorial");
        e.setNombre(ScannerUtil.leerCadena());
        dao.modificar(e);
        System.out.println("Editorial modificada correctamente");
    }
    
    public void eliminarEditorial() throws Exception{
        System.out.println("Ingrese el ID de la editorial que desea eliminar");
        mostrarEditoriales();
        Integer id = ScannerUtil.leerNumero();
        if(id == null){
            throw new EditorialException("Debe ingresar el ID de la editorial");
        }
        dao.eliminar(id);
        System.out.println("Editorial eliminada con éxito!");
    }
    
    public Editorial buscarEditorialPorId(Integer id) throws Exception{
        return dao.buscarEditorialPorId(id);
    }
    
    public void mostrarEditoriales(){
        List<Editorial> editoriales = dao.listadoEditoriales();
        
        for (Editorial e : editoriales) {
            System.out.println("ID: " + e.getId() + " - Nombre: " + e.getNombre());
        }
        System.out.println("");
    }
    
    public int cantidadEditoriales() throws Exception{
        return dao.cantidadEditoriales();
    }
    
}
