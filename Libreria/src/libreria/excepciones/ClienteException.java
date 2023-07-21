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
public class ClienteException extends Exception{

    /**
     * Creates a new instance of <code>ClienteException</code> without detail
     * message.
     */
    public ClienteException() {
    }

    /**
     * Constructs an instance of <code>ClienteException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ClienteException(String msg) {
        super(msg);
    }
}
