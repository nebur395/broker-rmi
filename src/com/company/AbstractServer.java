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

public abstract class AbstractServer implements ServerInterface{
	
	public String ejecuta_metodo(String servicio, String [] tipoParametros, String retorno, String [] parametros) {
		Class c = this.getClass();
		try{
			Method method = c.getMethod(servicio, getClassArray(tipoParametros));
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
