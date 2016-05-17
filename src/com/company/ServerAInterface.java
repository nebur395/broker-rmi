package com.company;
/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: ServerAInterface.java
 * DESCRIPCIÓN:
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerAInterface extends Remote {

    //
    String dar_fecha() throws RemoteException;

    //
    String dar_hora() throws RemoteException;

}
