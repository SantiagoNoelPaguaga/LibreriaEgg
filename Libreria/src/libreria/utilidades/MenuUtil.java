/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.utilidades;

import libreria.servicios.AutorService;
import libreria.servicios.ClienteService;
import libreria.servicios.EditorialService;
import libreria.servicios.LibroService;
import libreria.servicios.PrestamoService;

/**
 *
 * @author SantiagoPaguaga
 */
public class MenuUtil {
    
    private static final AutorService as = new AutorService();
    private static final EditorialService es = new EditorialService();
    private static final ClienteService cs = new ClienteService();
    private static final LibroService ls = new LibroService(as,es);
    private static final PrestamoService ps = new PrestamoService(ls,cs);
        
    public static void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n=============================== Menú Principal ===============================\n\n"
                    + "                     1. Alta/Baja/Modificación Autor.\n"
                    + "                     2. Alta/Baja/Modificación Editorial.\n"
                    + "                     3. Alta/Baja/Modificación Libro.\n"
                    + "                     4. Alta/Baja/Modificación Cliente.\n"
                    + "                     5. Alta/Modificación Prestamo.\n"
                    + "                     6. Devolver Libro.\n"
                    + "                     7. Buscar autor por nombre.\n"
                    + "                     8. Buscar libro por ISBN.\n"
                    + "                     9. Buscar libro por título.\n"
                    + "                    10. Buscar libro/s por nombre de Autor.\n"
                    + "                    11. Buscar libro/s por nombre de Editorial.\n"
                    + "                    12. Mostrar Préstamos Activos.\n"
                    + "                    13. Mostrar Préstamos Completados.\n"
                    + "                    14. Mostrar todos los préstamos para un cliente.\n"
                    + "                    15. Salir.\n\n"
                    + "==============================================================================\n");

            opcion = ScannerUtil.leerNumero();
            try {
                switch (opcion) {
                    case 1:
                        System.out.println("\n================================= Menú Autor =================================\n\n"
                                + "1. Agregar Autor\n"
                                + "2. Modificar Autor\n"
                                + "3. Eliminar Autor\n"
                                + "4. Volver al Menú Principal\n\n"
                                + "==============================================================================\n");
                        opcion = ScannerUtil.leerNumero();
                        switch (opcion) {
                            case 1:
                                as.guardarAutor();
                                break;
                            case 2:
                                as.modificarAutor();
                                break;
                            case 3:
                                as.eliminarAutor();
                                break;
                            case 4:
                                System.out.println("Volviendo al Menú Principal...");
                                break;
                            default:
                                System.out.println("La opción ingresada es incorrecta.");
                        }
                        break;
                    case 2:
                        System.out.println("\n=============================== Menú Editorial ===============================\n\n"
                                + "1. Agregar Editorial\n"
                                + "2. Modificar Editorial\n"
                                + "3. Eliminar Editorial\n"
                                + "4. Volver al Menú Principal\n\n"
                                + "==============================================================================\n");
                        opcion = ScannerUtil.leerNumero();
                        switch (opcion) {
                            case 1:
                                es.guardarEditorial();
                                break;
                            case 2:
                                es.modificarEditorial();
                                break;
                            case 3:
                                es.eliminarEditorial();
                                break;
                            case 4:
                                System.out.println("Volviendo al Menú Principal...");
                                break;
                            default:
                                System.out.println("La opción ingresada es incorrecta.");
                        }
                        break;
                    case 3:
                        System.out.println("\n================================= Menú Libro =================================\n\n"
                                + "1. Agregar Libro\n"
                                + "2. Modificar Libro\n"
                                + "3. Eliminar Libro\n"
                                + "4. Volver al Menú Principal\n\n"
                                + "==============================================================================\n");
                        opcion = ScannerUtil.leerNumero();
                        switch (opcion) {
                            case 1:
                                ls.guardarLibro();
                                break;
                            case 2:
                                ls.modificarLibro();
                                break;
                            case 3:
                                ls.eliminarLibro();
                                break;
                            case 4:
                                System.out.println("Volviendo al Menú Principal...");
                                break;
                            default:
                                System.out.println("La opción ingresada es incorrecta.");
                        }
                        break;
                    case 4:
                        System.out.println("\n================================ Menú Cliente ================================\n\n"
                                + "1. Agregar Cliente\n"
                                + "2. Modificar Cliente\n"
                                + "3. Eliminar Cliente\n"
                                + "4. Volver al Menú Principal\n\n"
                                + "==============================================================================\n");
                        opcion = ScannerUtil.leerNumero();
                        switch (opcion) {
                            case 1:
                                cs.guardarCliente();
                                break;
                            case 2:
                                cs.modificarCliente();
                                break;
                            case 3:
                                cs.eliminarCliente();
                                break;
                            case 4:
                                System.out.println("Volviendo al Menú Principal...");
                                break;
                            default:
                                System.out.println("La opción ingresada es incorrecta.");
                        }
                        break;
                    case 5:
                        System.out.println("\n=============================== Menú Préstamo ===============================\n\n"
                                + "1. Agregar Préstamo\n"
                                + "2. Modificar Préstamo\n"
                                + "3. Volver al Menú Principal\n\n"
                                + "==============================================================================\n");
                        opcion = ScannerUtil.leerNumero();
                        switch (opcion) {
                            case 1:
                                ps.guardarPrestamo();
                                break;
                            case 2:
                                ps.modificarPrestamo();
                                break;
                            case 3:
                                System.out.println("Volviendo al Menú Principal...");
                                break;
                            default:
                                System.out.println("La opción ingresada es incorrecta.");
                        }
                        break;
                    case 6:
                        ps.devolverLibro();
                        break;
                    case 7:
                        as.buscarAutorPorNombre();
                        break;
                    case 8:
                        ls.mostrarLibroPorISBN();
                        break;
                    case 9:
                        ls.buscarLibrosPorTitulo();
                        break;
                    case 10:
                        ls.buscarLibrosPorNombreDeAutor();
                        break;
                    case 11:
                        ls.buscarLibrosPorNombreDeEditorial();
                        break;
                    case 12:
                        ps.mostrarPrestamosActivos();
                        break;
                    case 13:
                        ps.mostrarPrestamosFinalizados();
                        break;
                    case 14:
                        ps.mostrarPrestamosParaUnCliente();
                        break;
                    case 15:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("La opción ingresada es incorrecta.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (opcion != 15);
    }
}
