/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Nick
 */
public class NoWarpsException extends RuntimeException {

    public NoWarpsException() {
    }

    public NoWarpsException(String message) {
        super(message);
    }

}
