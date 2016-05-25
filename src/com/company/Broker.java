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
import java.util.Iterator;

public class Broker implements BrokerInterface {
	public static final int port = 1099;
    ArrayList<Servidor> servidores = new ArrayList<Servidor>();
    public static String ip; 

    /**
     * Metodo constructor de la clase que asigna la IP de registro
     */
    public Broker(String ip) {
		this.ip=ip;
    }

    /**
     *
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
     *
     */
    public void registrar_servidor(String host_remoto_IP_port, String nombre_registrado) {
        Servidor servidor = new Servidor(host_remoto_IP_port, nombre_registrado);
        servidores.add(servidor);
    }

    /**
     *
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
     *
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

    public static void main (String [] args) {
        try {
			System.setProperty("java.rmi.server.hostname", ip);
		    //Se crea un stub y posteriormente se introduce al registro
            BrokerInterface stub = (BrokerInterface) UnicastRemoteObject.exportObject(new
                    Broker(args[0]), 0);
            Registry registry = null;       
			try{
				registry = LocateRegistry.createRegistry(port);
			}
			catch(RemoteException e){
				registry = LocateRegistry.getRegistry(port); 
			}

            registry.bind("BrokerInterface", stub);
			System.err.println("Broker registrado");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
