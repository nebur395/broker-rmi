package com.company;
/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: BrokerInterface.java
 * DESCRIPCIÓN: Interfaz que extiende de la clase Remote de RMI y que
 * especifica los métodos remotos que el bróker que la implemente tendrá
 * disponibles.
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface BrokerInterface extends Remote {

    /**
     * Método que, dado el nombre de un servicio alojado en un servidor registrado
     * en el bróker, y los parámetros necesarios para dicho servicio, ejecuta el
     * servicio especificado y devuelve su resultado en un String.
     * @param nom_servicio - Nombre del servicio que se desea ejecutar.
     * @param parametros_servicio - Array con los parámetros del servicio a ejecutar.
     * @return Un String con la respuesta del servicio ejecutado.
     */
    String ejecutar_servicio(String nom_servicio, String[] parametros_servicio) throws
            RemoteException;

    /**
     * Método accesible para cualquier servidor externo que quiera registrarse en el
     * bŕoker. Dados una IP y un nombre, el bróker registra a ese servidor en su
     * lista de servidores registrados.
     * @param host_remoto_IP_port - IP ó IP y puerto del servidor a registrar
     * @param nombre_registrado - Nombre con el que el servidor se ha registrado
     * en el registro de RMI.
     */ 
    void registrar_servidor(String host_remoto_IP_port, String nombre_registrado) throws
            RemoteException;

    /**
     * Método accesible para cualquier servidor externo que quiera registrar sus 
     * servicios en el bróker. El servidor proporcionará el nombre del servicio, una
     * lista de los parémetros del servicio y el tipo de retorno de dicho servicio.
     * También especificará el nombre del servidor.
     * @param nombre_registrado - Nombre con el que el servidor se ha registrado
     * en el registro de RMI.
     * @param nom_servicio - Nombre del servicio a registrar en el bróker.
     * @param lista_param - Array con los parámetros necesarios para ejecutar
     * el servicio.
     * @param tipo_retorno - Tipo del objeto de retorno del servicio cuando se
     * ejecuta.
     */ 
    void registrar_servicio(String nombre_regitrado, String nom_servicio, String[] lista_param,
                            String tipo_retorno) throws RemoteException;

    /**
     * Método que devuelve un ArrayList de Strings con el nombre y los
     * parámetros de todos los servicios disponibles en el bróker.
     * @return ArrayList de Strings con el nombre y los parámetros de
     * los servicios que ofrece el bróker.
     */ 
    ArrayList<String> listar_servicios() throws RemoteException;

}
