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

public class Broker implements BrokerInterface, Runnable {

    private String ipRegistro; //IP del host del registro RMI

    /**
     *	Metodo constructor de la clase que asigna la IP de registro
     */
    public Broker(String ipRegistro) {
        this.ipRegistro = ipRegistro;
    }

    /**
     *
     */
    public void ejecuta(String server, String [] args) {
        ;
    }

    /**
     *
     */
    public void registrar_servidor(String host_remoto_IP_port, String nombre_registrado) {

    }
    /**
     *
     */
    public void registrar_servicio(String nombre_regitrado, String nom_servicio, String [] lista_param, String tipo_retorno) {

    }
    /**
     *
     */
    public void listar_servicios(String noseque) {

    }

    public void run() {
        try {
            //Se crea un stub y posteriormente se introduce al registro
            ServerAInterface stub = (ServerAInterface) UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.getRegistry(ipRegistro);

            registry.bind("BrokerInterface", stub);


        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
