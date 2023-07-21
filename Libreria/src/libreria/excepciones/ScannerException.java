/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.excepciones;

/**
 *
 * @author SantiagoPaguaga
 */
public class ScannerException extends Exception{

    /**
     * Creates a new instance of <code>ScannerException</code> without detail
     * message.
     */
    public ScannerException() {
    }

    /**
     * Constructs an instance of <code>ScannerException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ScannerException(String msg) {
        super(msg);
    }
}
