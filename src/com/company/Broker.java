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
import java.util.ArrayList;

public class Broker implements BrokerInterface, Runnable {

    private String ipRegistro; //IP del host del registro RMI
    ArrayList<Servidor> servidores = new ArrayList<Servidor>();

    /**
     * Metodo constructor de la clase que asigna la IP de registro
     */
    public Broker(String ipRegistro) {
        this.ipRegistro = ipRegistro;
    }

    /**
     *
     */
    public void ejecuta(String server, String[] args) {
        ;
    }

    /**
     *
     */
    public void registrar_servidor(String host_remoto_IP_port, String nombre_registrado) {
        Servidor servidor = new Servidor(host_remoto_IP_port, nombre_registrado);
        servidores.add(servidor);
    }

    /**
     *
     */
    public void registrar_servicio(String nombre_regitrado, String nom_servicio, String[] lista_param, String tipo_retorno) {
        for (Servidor servidor : servidores) {
            if (servidor.getNombre().equals(nombre_regitrado)) {
                servidor.addServicio(nom_servicio, lista_param, tipo_retorno);
            }
        }
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
