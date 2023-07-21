/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.utilidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author SantiagoPaguaga
 */
public final class ScannerUtil {
    
    private static final Scanner sc = new Scanner(System.in).useDelimiter("\n");
    //Creamos un objeto DateTimeFormatter que verifica de manera estricta que el formato de la fecha sea correcta 
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private ScannerUtil() {
    }
    
    public static int leerNumero(){
        boolean bandera = true;
        int numero = 0;
        while (bandera) {
            try {
                numero = sc.nextInt();
                bandera = false;
            } catch (InputMismatchException e) {
                System.out.println("Se esperaba un número entero. Intente nuevamente.");
            } finally {
                sc.nextLine();
            }
        }
        return numero;
    }
    
    public static long leerNumeroLong(){
        boolean bandera = true;
        long numero = 0;
        while (bandera) {
            try {
                numero = sc.nextLong();
                bandera = false;
            } catch (InputMismatchException e) {
                System.out.println("Se esperaba un número entero. Intente nuevamente.");
            } finally {
                sc.nextLine();
            }
        }
        return numero;
    }
    
    public static String leerCadena(){
        return sc.next();   
    }
    
    public static Date leerFecha() {
        boolean bandera = true;
        LocalDate fecha = null;
        while (bandera) {
            try {
                String fechaString = sc.next();
                fecha = LocalDate.parse(fechaString, dateFormatter);
                bandera = false;
            } catch (Exception e) {
                System.out.println("Fecha inválida. Ingrese una fecha en el formato dd/mm/yyyy. Ejemplo: 12/07/2023");
            } finally {
                sc.nextLine();
            }
        }
        return java.sql.Date.valueOf(fecha);
    }
}
