package com.company;
/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: ServerBInterface.java
 * DESCRIPCIÓN:
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerBInterface extends Remote {

    //
    String [] listar_libros() throws RemoteException;
    //
    void insertar_libro(String libro) throws RemoteException;

}
