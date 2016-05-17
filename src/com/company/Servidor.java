package com.company;
/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: Servidor.java
 * DESCRIPCIÓN:
 */

import java.util.ArrayList;

public class Servidor {

    private String nombre_registrado;
    private String host_remoto_IP_port;
    private ArrayList<Servicio> listaServicios = new ArrayList<Servicio>();

    public Servidor(String host, String nombre) {
        nombre_registrado = nombre;
        host_remoto_IP_port = host;
    }

    public String getNombre() {
        return nombre_registrado;
    }

    public String getIP() {
		int index = host_remoto_IP_port.indexOf(":");
        return host_remoto_IP_port.substring(0, index);
    }
    
    public String getPort() {
		int index = host_remoto_IP_port.indexOf(":");
		return host_remoto_IP_port.substring(index+1);
	}

    public ArrayList<Servicio> getServicios() {
        return listaServicios;
    }

    public void addServicio(String nombre, String[] param, String retorno) {
        listaServicios.add(new Servicio(nombre, retorno, param));
    }
}
