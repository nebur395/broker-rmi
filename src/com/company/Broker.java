package com.company;

/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: Broker.java
 * DESCRIPCIÓN:
 */

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class Broker implements BrokerInterface {
	public static final int port = 1099;
    ArrayList<Servidor> servidores = new ArrayList<Servidor>(); 
    private static String ip;

    /**
     * Metodo constructor de la clase que asigna la IP de registro
     */
    public Broker(String ip) {
		this.ip=ip;
    }

    /**
     * Método que, dado el nombre de un servicio alojado en un servidor registrado
     * en el bróker, y los parámetros necesarios para dicho servicio, ejecuta el
     * servicio especificado y devuelve su resultado en un String.
     * @param nom_servicio - Nombre del servicio que se desea ejecutar.
     * @param parametros_servicio - Array con los parámetros del servicio a ejecutar.
     * @return Un String con la respuesta del servicio ejecutado.
     */
    public String ejecutar_servicio(String nom_servicio, String[] parametros_servicio) {
        boolean encontrado = false;
        Iterator<Servidor> iterServidor = servidores.iterator();
        Servidor servidor = null;
        String tipo_retorno = "";
        String[] lista_param = null;
        // Se itera en la lista de servidores con sus servicios, hasta encontrar el servicio
        // [nom_servicio] y dejar en la variable [servidor] el servidor que contiene dicho servicio
        while (iterServidor.hasNext() && !encontrado) {
            servidor = iterServidor.next();
            Iterator<Servicio> servicio = servidor.getServicios().iterator();
            while (servicio.hasNext() && !encontrado) {
                Servicio iterServ = servicio.next();
                tipo_retorno = iterServ.getTipoRetorno();
                lista_param = iterServ.getListaParam();

                if (iterServ.getNombre().equals(nom_servicio)) {
                    encontrado = true;
                }
            }
        }
        if (encontrado) {
            try {
                // Se coge el objeto remoto del broker
                Registry registry = LocateRegistry.getRegistry(servidor.getIP());
                ServerInterface server = (ServerInterface) registry.lookup(servidor.getNombre());
                return server.ejecuta_metodo(nom_servicio,lista_param,tipo_retorno,parametros_servicio);
                
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * Método accesible para cualquier servidor externo que quiera registrarse en el
     * bŕoker. Dados una IP y un nombre, el bróker registra a ese servidor en su
     * lista de servidores registrados.
     * @param host_remoto_IP_port - IP ó IP y puerto del servidor a registrar
     * @param nombre_registrado - Nombre con el que el servidor se ha registrado
     * en el registro de RMI.
     */ 
    public void registrar_servidor(String host_remoto_IP_port, String nombre_registrado) {
        Servidor servidor = new Servidor(host_remoto_IP_port, nombre_registrado);
        servidores.add(servidor);
    }

    /**
     * Método accesible para cualquier servidor externo que quiera registrar sus 
     * servicios en el bróker. El servidor proporcionará el nombre del servicio, una
     * lista de los parémetros del servicio y el tipo de retorno de dicho servicio.
     * También especificará el nombre del servidor.
     * @param nombre_registrado - Nombre con el que el servidor se ha registrado
     * en el registro de RMI.
     * @param nom_servicio - Nombre del servicio a registrar en el bróker.
     * @param lista_param - Array con los parámetros necesarios para ejecutar
     * el servicio.
     * @param tipo_retorno - Tipo del objeto de retorno del servicio cuando se
     * ejecuta.
     */ 
    public void registrar_servicio(String nombre_regitrado, String nom_servicio, String[]
            lista_param, String tipo_retorno) {
        for (Servidor servidor : servidores) {
            if (servidor.getNombre().equals(nombre_regitrado)) {
                servidor.addServicio(nom_servicio, lista_param, tipo_retorno);
            }
        }
    }

    /**
     * Método que devuelve un ArrayList de Strings con el nombre y los
     * parámetros de todos los servicios disponibles en el bróker.
     * @return ArrayList de Strings con el nombre y los parámetros de
     * los servicios que ofrece el bróker.
     */ 
    public ArrayList<String> listar_servicios() {
        ArrayList<String> listado = new ArrayList<String>();
        Iterator<Servidor> iterServer = servidores.iterator();
        while (iterServer.hasNext()) {
            Iterator<Servicio> iterServicio = iterServer.next().getServicios().iterator();
            while (iterServicio.hasNext()) {
                listado.add(iterServicio.next().toString());
            }
        }
        return listado;
    }

	/**
	 * Método Main del bróker. Crea un stub del Bróker, crea o localiza
	 * el registro de RMI y enalaza el stub en el registro.
	 */ 
    public static void main (String [] args) {
        try {
			Broker bro = new Broker(args[0]);
			System.setProperty("java.rmi.server.hostname", ip);
		    //Se crea un stub y posteriormente se introduce al registro
            BrokerInterface stub = (BrokerInterface) UnicastRemoteObject.exportObject(bro, 0);
            Registry registry = null; 
            //Intenta crear un nuevo registro o lo localiza si ya existe uno.      
			try{
				registry = LocateRegistry.createRegistry(port);
			}
			catch(RemoteException e){
				registry = LocateRegistry.getRegistry(port); 
			}
			//Enlaza el stub en el registro.
            registry.bind("BrokerInterface", stub);
			System.err.println("Broker registrado");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
