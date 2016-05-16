package com.company;
/*
 * AUTORES: Rubén Moreno Jimeno 680882
 * FICHERO:
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
