package com.company;
/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: AbstractServer.java
 * DESCRIPCIÓN: Clase abstracta que hereda de la interfaz remota
 * ServerInterface y que implementa el método ejecuta_metodo.
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract class AbstractServer implements ServerInterface{
	
	/**
	 * Método que, dado el nombre de otro método, sus parámetros
	 * y su tipo de retorno, busca ese método empleando la 
	 * reflexividad de Java sobre la propia clase y lo ejecuta, 
	 * devolviendo el resultado en un String.
	 * @param servicio - Nombre del método a ejecutar
	 * @param tipoParametros - Array con los tipos de los parámetros del 
	 * método a ejecutar
	 * @param retorno - Tipo del parámetro de retorno del método a ejecutar
	 * @param parametros - Array con los parámetros del método a ejecutar
	 * @return Un String con la respuesta del método ejecutado.
	 */
	public String ejecuta_metodo(String servicio, String [] tipoParametros, String retorno, String [] parametros) {
		Class c = this.getClass();
		try{
			//Se obtiene le método mediante reflexividad
			Method method = c.getMethod(servicio, getClassArray(tipoParametros));
			//Comprueba el tipo de retorno
			if(retorno.equals("void")){
				method.invoke(this, parametros);
				return "";
			}
			else if(retorno.equals("String[]")){
				String [] lista = (String [])method.invoke(this, parametros);
				String respuesta = "";
				for(String s:lista){
					respuesta+=s +"\n";
				}
				return respuesta;
			}
			else{
				return (String)method.invoke(this, parametros);
			}
		}
		catch (NoSuchMethodException e){
			e.printStackTrace();
		}
		catch (IllegalAccessException e){
			e.printStackTrace();
		}
		catch (InvocationTargetException e){
			e.printStackTrace();
		}
		return"";
	}
	
	/**
	 * Método que devuelve un array de objetos tipo Class
	 * con las clases de los tipos especificados en el parámetro
	 * [param].
	 * @param param - Array de Strings con un conjunto de tipos de objetos java. 
	 * @return Array de objetos tipo Class con las clases de los tipos
	 * especificados en [param].
	 */ 
	protected Class[] getClassArray(String [] param){
		Class [] array = new Class[param.length];
		try{
			for(int i=0;i<param.length;i++){
				int index = param[i].indexOf(" ");
				if((param[i].substring(0, index)).equals("String")){
					array[i] = Class.forName("java.lang.String");
				}
			}
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		return array;
	}
}
