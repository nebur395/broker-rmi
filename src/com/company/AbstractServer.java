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

public abstract class AbstractServer implements Remote{
	
	protected String ejecuta_metodo(String servicio, String [] tipoParametros, String retorno, String [] parametros) throws RemoteException{
		Class c = this.getClass();
		try{
			Method method = c.getMethod(servicio, getClassArray(tipoParametros));
			if(retorno.equals("void")){
				method.invoke(this, parametros);
				return "";
			}
			else if(retorno.equals("String []")){
				return (method.invoke(this, parametros)).toString();
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
	
	protected Class[] getClassArray(String [] param){
		Class [] array = new Class[param.length];
		try{
			for(int i=0;param.length<i;i++){
				int index = param[i].indexOf(" ");
				array[i] = Class.forName(param[i].substring(0, index));
			}
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		return array;
	}
}
