package com.company;

/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: ServerA.java
 * DESCRIPCIÓN:
 */
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ServerA extends AbstractServer implements Runnable {

    private String ipRegistro; //IP del host del registro RMI
    private final String ipBroker = "localhost"; //IP del broker, se sabe antes de compilarse

    /**
     *	Metodo constructor de la clase que asigna la IP de registro
     */
    public ServerA(String ipRegistro) {
        this.ipRegistro = ipRegistro;
    }

    /**
     *
     */
    public String dar_hora() {
        DateFormat dateF = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return dateF.format(date);
    }

    /**
     *
     */
    public String dar_fecha() {
        DateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateF.format(date);
    }

    public void run() {
        try {
            //Se crea un stub y posteriormente se introduce al registro
            ServerAInterface stub = (ServerAInterface) UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.getRegistry(ipRegistro);
            String nombre_registro = "ServerAInterface";
            registry.bind(nombre_registro, stub);

            // Se coge el objeto remoto del broker
            registry = LocateRegistry.getRegistry(ipBroker);
            BrokerInterface brokerInterface = (BrokerInterface) registry.lookup("BrokerInterface");
            // Se registra el servidor dentro del broker
            brokerInterface.registrar_servidor(ipRegistro,nombre_registro);
            // Se registran los servicios dentro del broker
            brokerInterface.registrar_servicio(nombre_registro, "dar_hora",new String[0],"String");
            brokerInterface.registrar_servicio(nombre_registro, "dar_fecha",new String[0],"String");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
