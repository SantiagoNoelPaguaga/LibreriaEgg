/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.servicios;

import java.util.Date;
import java.util.List;
import libreria.entidades.Cliente;
import libreria.entidades.Libro;
import libreria.entidades.Prestamo;
import libreria.excepciones.PrestamoException;
import libreria.persistencia.PrestamoDao;
import libreria.utilidades.ScannerUtil;

/**
 *
 * @author SantiagoPaguaga
 */
public class PrestamoService {
    
    private final PrestamoDao dao;
    private final ClienteService cs;
    private final LibroService ls;

    public PrestamoService(LibroService ls, ClienteService cs) {
        this.dao = new PrestamoDao();
        this.cs = cs;
        this.ls = ls;
    }
    
    public void guardarPrestamo() throws Exception{
        if(ls.cantidadLibros() == 0 && cs.cantidadClientes() == 0){
            throw new PrestamoException("Aún no hay libros ni clientes cargados para poder asignarle al préstamo, primero debe agregar un libro y un cliente");
        }
        if(ls.cantidadLibros() == 0){
            throw new PrestamoException("Aún no hay libros cargados para poder asignarle al préstamo, primero debe agregar un libro");
        }
        if(cs.cantidadClientes() == 0){
            throw new PrestamoException("Aún no hay clientes cargados para poder asignarle al préstamo, primero debe agregar un cliente");
        }
        System.out.println("Ingrese el ISBN del libro a prestar");
        ls.mostrarLibros();
        Long isbn = ScannerUtil.leerNumeroLong();
        if(isbn == null){
            throw new PrestamoException("Debe ingresar el ISBN del libro");
        }
        Libro libro = ls.buscarLibroPorISBN(isbn);
        if(libro.getEjemplaresRestantes() == 0){
            throw new PrestamoException("No quedan más ejemplares disponibles del libro solicitado");
        }
        System.out.println("Ingrese el ID del cliente");
        cs.mostrarClientes();
        Integer id = ScannerUtil.leerNumero();
        if(id == null){
            throw new PrestamoException("Debe ingresar el ID del cliente");
        }
        Cliente cliente = cs.buscarClientePorId(id);
        System.out.println("Ingrese la fecha de inicio del préstamo (formato dd/MM/yyyy)");
        Date fechaPrestamo = ScannerUtil.leerFecha();
        System.out.println("Ingrese la fecha de devolución del préstamo(formato dd/MM/yyyy)");
        Date fechaDevolucion = ScannerUtil.leerFecha();
        boolean bandera = true;
        do {            
            if (fechaPrestamo.after(fechaDevolucion)) {
                System.out.println("Error: Ingresó una fecha de devolución anterior a la fecha actual, vuelva a ingresar las fechas");
                System.out.println("Ingrese la fecha de inicio del préstamo (formato dd/MM/yyyy)");
                fechaPrestamo = ScannerUtil.leerFecha();
                System.out.println("Ingrese la fecha de devolución del préstamo(formato dd/MM/yyyy)");
                fechaDevolucion = ScannerUtil.leerFecha();
            } 
//            else if(fechaPrestamo.equals(fechaDevolucion)){
//                System.out.println("No se puede devolver un libro el mismo día del préstamo");
//                System.out.println("Ingrese la fecha de inicio del préstamo (formato dd/MM/yyyy)");
//                fechaPrestamo = ScannerUtil.leerFecha();
//                System.out.println("Ingrese la fecha de devolución del préstamo(formato dd/MM/yyyy)");
//                fechaDevolucion = ScannerUtil.leerFecha();}
            else {
                bandera = false;
            }
        } while (bandera);
        dao.guardar(new Prestamo(fechaPrestamo, fechaDevolucion, libro, cliente));
        libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() + 1);
        libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() - 1);
        ls.modificarEjemplares(libro);
        System.out.println("Préstamo registrado exitosamente!");
    }
    
     //El método para eliminar prestamos lo comente para evitar que se eliminen prestamos que aún no fueron
    //completados, ya que en esta entidad en particular estamos realizando lo que se conoce como bajas lógicas
    //Es decir, no eliminamos el préstamo por completo de la base de datos, solo cambiamos el valor de su atributo
    //boolean alta, cuando este está en false podemos decir que se encuentra finalizado, es decir sería muy similar
    //a decir que fue eliminado, esto lo vamos a implementar en el metodo devolverLibro(), ya que en ese momento
    //consideramos que el prestamo se completo correctamente.
    
//    public void eliminarPrestamo() throws Exception{
//        System.out.println("Ingrese el ID del préstamo que desea eliminar");
//        mostrarPrestamosActivos();
//        Integer id = ScannerUtil.leerNumero();
//        if(id == null){
//            throw new PrestamoException("Debe ingresar el ID del préstamo");
//        }
//        dao.eliminar(id);
//        System.out.println("Préstamo eliminado exitosamente!");
//    }
    
    public void modificarPrestamo() throws Exception{
        System.out.println("Ingrese el ID del préstamo que desea modificar");
        mostrarPrestamosActivos();
        Integer id = ScannerUtil.leerNumero();
        if(id == null){
            throw new PrestamoException("Debe ingresar el ID del préstamo");
        }
        Prestamo p = buscarPrestamoPorId(id);
        int opcion;
        do {            
            System.out.println("Ingrese el campo que desea modificar\n" + 
                               "1. Fecha Inicio\n" +
                               "2. Fecha Devolución\n" +
                               "3. Libro\n" +
                               "4. Cliente\n" +
                               "5. Guardar Cambios\n");
            opcion = ScannerUtil.leerNumero();
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese la fecha de inicio del préstamo (formato dd/MM/yyyy)");
                    Date fechaPrestamo = ScannerUtil.leerFecha();
                    p.setFechaPrestamo(fechaPrestamo);
                    System.out.println("¿Desea modificar otro campo? Para Guardar cambios y finalizar presione 5");
                    break;
                case 2:
                    System.out.println("Ingrese la fecha de devolución del préstamo(formato dd/MM/yyyy)");
                    Date fechaDevolucion = ScannerUtil.leerFecha();
                    p.setFechaDevolucion(fechaDevolucion);
                    break;
                case 3:
                    System.out.println("Ingrese el ISBN del libro a prestar");
                    ls.mostrarLibros();
                    Long isbn = ScannerUtil.leerNumeroLong();
                    if (isbn == null) {
                        throw new PrestamoException("Debe ingresar el ISBN del libro");
                    }
                    Libro libro = ls.buscarLibroPorISBN(isbn);
                    p.setLibro(libro);
                    break;
                case 4:
                    System.out.println("Ingrese el ID del cliente");
                    cs.mostrarClientes();
                    Integer idCliente = ScannerUtil.leerNumero();
                    if (idCliente == null) {
                        throw new PrestamoException("Debe ingresar el ID del cliente");
                    }
                    Cliente cliente = cs.buscarClientePorId(idCliente);
                    p.setCliente(cliente);
                    break;
                case 5:
                    System.out.println("Guardando cambios...");
                    break;
                default:
                    System.out.println("La opción ingresada es incorrecta.");
            }
        } while (opcion != 5);
        dao.modificar(p);
        System.out.println("Préstamo modificado exitosamente!");
    }
    
    public void devolverLibro() throws Exception{
        //los préstamos que se encuentran activos tienen en su atributo alta el valor de true
        //Cuando un cliente devuelve el libro el préstamo debería finalizarse, para indicar de alguna forma
        //que el Cliente ya realizó la devolución, cuando eso suceda cambiaremos el valor de alta = false
        System.out.println("Ingrese el ID del Préstamo");
        mostrarPrestamosActivos();
        Integer id = ScannerUtil.leerNumero();
        if(id == null){
            throw new PrestamoException("Debe ingresar el ID del Préstamo");
        }
        Prestamo prestamo = buscarPrestamoPorId(id);
        if(!prestamo.getAlta()){
            throw new PrestamoException("El préstamo indicado corresponde a un préstamo en estado finalizado, ya devolvió el libro correspondiente!");
        }
        //Cambiamos el valor de alta del préstamo a false, para indicar que se encuentra en estado finalizado
        prestamo.setAlta(false);
        prestamo.getLibro().setEjemplaresRestantes(prestamo.getLibro().getEjemplaresRestantes() + 1);
        prestamo.getLibro().setEjemplaresPrestados(prestamo.getLibro().getEjemplaresPrestados() - 1);
        modificarValorAltaPrestamo(prestamo);
        ls.modificarEjemplares(prestamo.getLibro());
        System.out.println("Préstamo finalizado! El libro se devolvió con éxito.");
    }
    
    public void modificarValorAltaPrestamo(Prestamo prestamo) throws Exception{
        dao.modificar(prestamo);
    }
    
    public Prestamo buscarPrestamoPorId(Integer id) throws Exception{
        return dao.buscarPrestamoPorId(id);
    }
    
    public void mostrarPrestamosActivos() throws Exception{
        List<Prestamo> prestamos = dao.listadoPrestamosActivos();
        
        for (Prestamo p : prestamos) {
            System.out.println(p.toString());
        }
        System.out.println("");
    }
    
    public void mostrarPrestamosFinalizados() throws Exception{
        List<Prestamo> prestamos = dao.listadoPrestamosFinalizados();
        
        for (Prestamo p : prestamos) {
            System.out.println(p.toString());
        }
        System.out.println("");
    }
    
    public void mostrarPrestamosParaUnCliente() throws Exception{
        System.out.println("Ingrese el ID del cliente");
        cs.mostrarClientes();
        Integer id = ScannerUtil.leerNumero();
        if(id == null){
            throw new PrestamoException("Debe ingresar el ID del cliente");
        }
        Cliente cliente = cs.buscarClientePorId(id);
        
        List<Prestamo> prestamosActivos = dao.listadoPrestamosActivosParaUnCliente(cliente.getId());
        List<Prestamo> prestamosFinalizados = dao.listadoPrestamosFinalizadosParaUnCliente(cliente.getId());
        
        System.out.println("\nPréstamos Activos:");
        for (Prestamo pa : prestamosActivos) {
            System.out.println(pa);
        }
        System.out.println("\nPréstamos Finalizados:");
        for (Prestamo pf : prestamosFinalizados) {
            System.out.println(pf);
        }
        
    }
}
