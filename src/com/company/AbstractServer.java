package com.company;
/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: ServerAInterface.java
 * DESCRIPCIÓN:
 */

import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract class AbstractServer implements Remote{
	
	protected String ejecuta_metodo(String servicio, String [] tipoParametros, String retorno, String [] parametros) throws RemoteException{
		Method method = this.getMethod(servicio, getClassArray(tipoParametros));
		if(retorno.equals("void")){
			method.invoke(this, parametros);
			return "";
		}
		else if(retorno.equals("String []")){
			return (method.invoke(this, parametros)).toString();
		}
		else{
			return method.invoke(this, parametros);
		}
	}
	
	protected Class[] getClassArray(String [] param){
		Class [] array = new Class[param.length];
		for(int i=0;param.length;i++){
			array[i] = Class.forName(param[i].substring(0, indexOf(" ")));
		}
		return array;
	}
}
