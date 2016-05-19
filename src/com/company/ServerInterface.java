package com.company;
/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: ServerAInterface.java
 * DESCRIPCIÓN:
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	
	String ejecuta_metodo(String servicio, String [] tipoParametros, String retorno, String [] parametros) throws RemoteException;
}
