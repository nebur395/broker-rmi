package com.company;
/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: ServerAInterface.java
 * DESCRIPCIÓN:
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BrokerInterface extends Remote {

    //
    void ejecuta(String server, String [] args) throws RemoteException;
    //
    void registraServer(String noseque) throws RemoteException;
    //
    void registraServicio(String noseque) throws RemoteException;
    //
    void listar_servicios(String noseque) throws RemoteException;

}
