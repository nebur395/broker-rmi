package com.company;
/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: ServerAInterface.java
 * DESCRIPCIÓN:
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BrokerInterface extends Remote {

    //
    String ejecutar_servicio(String nom_servicio, String[] parametros_servicio) throws
            RemoteException;

    //
    void registrar_servidor(String host_remoto_IP_port, String nombre_registrado) throws
            RemoteException;

    //
    void registrar_servicio(String nombre_regitrado, String nom_servicio, String[] lista_param,
                            String tipo_retorno) throws RemoteException;

    //
    void listar_servicios(String noseque) throws RemoteException;

}
