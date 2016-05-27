package com.company;
/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: Servidor.java
 * DESCRIPCIÓN: Objeto contenedor que almacena la información 
 * de un servidor registrado en el bróker.
 */

import java.util.ArrayList;

public class Servidor {

    private String nombre_registrado; //Nombre del servidor en el registro RMI
    private String host_remoto_IP_port; //IP ó IP y puerto del servidor
    //ArrayList de Servicios con los servicios registrados de este servidor en el bŕoker
    private ArrayList<Servicio> listaServicios = new ArrayList<Servicio>();

    public Servidor(String host, String nombre) {
        nombre_registrado = nombre;
        host_remoto_IP_port = host;
    }
	
	//Getters
    public String getNombre() {
        return nombre_registrado;
    }
	
    public String getIP() {
		int index = host_remoto_IP_port.indexOf(":");
		if(index != -1){
			return host_remoto_IP_port.substring(0, index);
		}
        else{
			return host_remoto_IP_port;
		}
    }
    
    public String getPort() {
		int index = host_remoto_IP_port.indexOf(":");
		if(index != -1){
			return host_remoto_IP_port.substring(index+1);
		}
		else{
			return host_remoto_IP_port;
		}
	}

    public ArrayList<Servicio> getServicios() {
        return listaServicios;
    }
	/*
	 * Añade un nuevo servicio al ArrayList de servicios registrados
	 * del servidor en el bróker.
	 */ 
    public void addServicio(String nombre, String[] param, String retorno) {
        listaServicios.add(new Servicio(nombre, retorno, param));
    }
}
