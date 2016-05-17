package com.company;

/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: ServerB.java
 * DESCRIPCIÓN:
 */
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerB implements ServerBInterface, Runnable {

    private String ipRegistro; //IP del host del registro RMI

    /**
     *	Metodo constructor de la clase que asigna la IP de registro
     */
    public ServerB(String ipRegistro) {
        this.ipRegistro = ipRegistro;
    }

    /**
     *
     */
    public String [] listar_libros() {
        return null;
    }

    /**
     *
     */
    public void insertar_libro(String libro) {

    }

    public void run() {
        try {
            //Se crea un stub y posteriormente se introduce al registro
            ServerAInterface stub = (ServerAInterface) UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.getRegistry(ipRegistro);

            registry.bind("ServerBInterface", stub);


        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
