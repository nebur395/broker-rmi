package com.company;

/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: ServerA.java
 * DESCRIPCIÓN: Servidor que ofrece los métodos dar_hora y dar_fecha
 * a través del bróker, registrándose previamente en el registro RMI.
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

public class ServerA extends AbstractServer {
	
	public static final int port = 1099; //Puerto por defecto de RMI
    private static String ipRegistro; //IP del host del registro RMI
    private static final String ipBroker = "localhost"; //IP del broker, se sabe antes de compilarse

    /**
     *	Metodo constructor de la clase que asigna la IP de registro
     */
    public ServerA(String ipRegistro) {
        this.ipRegistro = ipRegistro;
    }

    /**
     * Devuelve la hora actual del sistema donde se ejecuta el servidor.
     * @return String con la hora actual del sistema.
     */
    public String dar_hora() {
        DateFormat dateF = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return dateF.format(date);
    }

    /**
     * Devuelve la fecha actual del sistema donde se ejecuta el servidor.
     * @return String con la fecha actual del sistema.
     */
    public String dar_fecha() {
        DateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateF.format(date);
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
			ServerA server = new ServerA(args[0]);
			System.setProperty("java.rmi.server.hostname", ipRegistro);
            //Se crea un stub y posteriormente se introduce al registro
            ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = null;    
            //Intenta crear un nuevo registro o lo localiza si ya existe uno.    
			try{
				registry = LocateRegistry.createRegistry(port);
			}
			catch(RemoteException e){
				registry = LocateRegistry.getRegistry(port); 
			}
            String nombre_registro = "ServerAInterface";
            registry.bind(nombre_registro, stub);
			System.err.println("ServerA registrado en registro RMI");
            // Se coge el objeto remoto del broker
            registry = LocateRegistry.getRegistry(ipBroker);
            BrokerInterface brokerInterface = (BrokerInterface) registry.lookup("BrokerInterface");
            // Se registra el servidor dentro del broker
            brokerInterface.registrar_servidor(ipRegistro,nombre_registro);
            System.err.println("ServerA registrado en Broker");
            // Se registran los servicios dentro del broker
            brokerInterface.registrar_servicio(nombre_registro, "dar_hora",new String[0],"String");
            brokerInterface.registrar_servicio(nombre_registro, "dar_fecha",new String[0],"String");
            System.err.println("Servicios de ServerA registrados en Broker");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
