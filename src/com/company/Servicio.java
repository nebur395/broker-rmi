package com.company;
/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: Servicio.java
 * DESCRIPCIÓN: Objeto contenedor que almacena la información
 * acerca de un Servicio registrado en el bróker.
 */

import java.util.Arrays;

public class Servicio {

    private String nom_servicio; //Nombre del servicio
    private String tipo_retorno; //Tipo del objeto de retorno del servicio
    private String[] lista_param; //Array con los parámetros del servicio

    public Servicio(String nombre, String retorno, String[] param) {
        nom_servicio = nombre;
        tipo_retorno = retorno;
        lista_param = param;
    }
    
	//Getters
    public String getNombre() {
        return nom_servicio;
    }

    public String getTipoRetorno() {
        return tipo_retorno;
    }

    public String[] getListaParam() {
        return lista_param;
    }
    
    /*
     * Reescritura del método toString de la clase Object de Java.
     */ 
	@Override
    public String toString() {
        String parametros = Arrays.toString(lista_param);
        return tipo_retorno + " " + nom_servicio + "(" + parametros + ")";
    }
}
