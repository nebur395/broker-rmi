package com.company;
/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: ServerInterface.java
 * DESCRIPCIÓN: Interfaz que extiende de la clase Remote de
 * RMI y que especifica los métodos remotos que obtendrán los
 * servidores que hereden de ella.
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	
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
	String ejecuta_metodo(String servicio, String [] tipoParametros, String retorno, String [] parametros) throws RemoteException;
}
