package com.company;

/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: ServerB.java
 * DESCRIPCIÓN:
 */

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerB extends AbstractServer {
	
	public static final int port = 1099;
    private static String ipRegistro = ""; //IP del host del registro RMI
    private ArrayList<String> listaLibros = new ArrayList<String>();
    private static final String ipBroker = "localhost"; //IP del broker, se sabe antes de compilarse

    /**
     * Metodo constructor de la clase que asigna la IP de registro
     */
    public ServerB(String ipRegistro) {
        this.ipRegistro = ipRegistro;
    }

    /**
     *
     */
    public String[] listar_libros() {
        String[] array = new String[listaLibros.size()];
        return listaLibros.toArray(array);
    }

    /**
     *
     */
    public void insertar_libro(String libro) {
        listaLibros.add(libro);
        System.out.printf("Libro '%s' introducido%n", libro);
    }

    public static void main (String [] args) {
        try {
            //Se crea un stub y posteriormente se introduce al registro
            ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(new
                    ServerB(args[0]), 0);
            Registry registry = null;       
			try{
				registry = LocateRegistry.createRegistry(port);
			}
			catch(RemoteException e){
				registry = LocateRegistry.getRegistry(port); 
			}
            String nombre_registro = "ServerBInterface";
            registry.bind(nombre_registro, stub);
			System.err.println("ServerB registrado en registro RMI");
            // Se coge el objeto remoto del broker
            registry = LocateRegistry.getRegistry(ipBroker);
            BrokerInterface brokerInterface = (BrokerInterface) registry.lookup("BrokerInterface");
            // Se registra el servidor dentro del broker
            brokerInterface.registrar_servidor(ipRegistro, nombre_registro);
            System.err.println("ServerB registrado en Broker");
            // Se registran los servicios dentro del broker
            brokerInterface.registrar_servicio(nombre_registro, "listar_libros", new String[0],
                    "String[]");
            String[] parametros = {"String libro"};
            brokerInterface.registrar_servicio(nombre_registro, "insertar_libro", parametros,
                    "void");
            System.err.println("Servicios de ServerB registrados en Broker");        

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
