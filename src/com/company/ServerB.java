package com.company;

/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: ServerB.java
 * DESCRIPCIÓN: Servidor que ofrece los métodos lista_libros e insertar_libro
 * a través del bróker, registrándose previamente en el registro RMI.
 */

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerB extends AbstractServer {
	
	public static final int port = 1099; //Puerto por defecto de RMI
    private static String ipRegistro = ""; //IP del host del registro RMI
    //ArrayList con los libros introducidos en el servidor.
    private ArrayList<String> listaLibros = new ArrayList<String>();
    private static final String ipBroker = "localhost"; //IP del broker, se sabe antes de compilarse

    /**
     * Metodo constructor de la clase que asigna la IP de registro
     */
    public ServerB(String ipRegistro) {
        this.ipRegistro = ipRegistro;
    }

    /**
     * Devuelve un array con los libros introducidos en el servidor.
     * @return Array de Strings con los libros introducidos en el servidor.
     */
    public String[] listar_libros() {
        String[] array = new String[listaLibros.size()];
        return listaLibros.toArray(array);
    }

    /**
     * Método que, dado el String con el nombre de un libro, lo introduce
     * en el servidor para almacenarlo.
     * @param libro - Nombre del libro a introducir.
     */
    public void insertar_libro(String libro) {
        listaLibros.add(libro);
        System.out.printf("Libro '%s' introducido%n", libro);
    }
	
	/**
	 * Método main del servidor. Crea un stub del servidor, crea o localiza
	 * el registro de RMI y enalaza el stub en el registro. A continuación
	 * obtiene el registro donde se encuentra el bróker y obtiene la interfaz
	 * del bróker. Finalmente se registra en el bróker y registra también sus
	 * servicios.
	 */ 
    public static void main (String [] args) {
        try {
			ServerB server = new ServerB(args[0]);
			System.setProperty("java.rmi.server.hostname", ipRegistro);
            //Se crea un stub y posteriormente se introduce al registro
            ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(server, 0);
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
