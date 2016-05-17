package com.company;
/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: Servicio.java
 * DESCRIPCIÓN:
 */

public class Servicio {
	
	private String nom_servicio;
	private String tipo_retorno;
	private String [] lista_param;
	
	public Servicio(String nombre, String retorno, String [] param){
		nom_servicio=nombre;
		tipo_retorno = retorno;
		lista_param = param;
	}
	
	public String getNombre(){
		return nom_servicio;
	}
	
	public String getTipoRetorno(){
		return tipo_retorno;
	}
	
	public String[] getListaParam(){
		return lista_param;
	}
}
