package com.company;

/*
 * AUTORES: Rubén Moreno Jimeno 680882 e Iñigo Gascón Royo 685215
 * FICHERO: ServerA.java
 * DESCRIPCIÓN:
 */

import java.net.SocketPermission;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class ClienteC {

    private static final String ipBroker = "localhost"; //IP del broker, se sabe antes de compilarse

    public static void main(String[] args) {
        try {
            // Se coge el objeto remoto del broker
            Registry registry = LocateRegistry.getRegistry(ipBroker);
            BrokerInterface brokerInterface = (BrokerInterface) registry.lookup("BrokerInterface");
            ArrayList<String> lista_servicios = brokerInterface.listar_servicios();
            int iterServ = 1;
            System.out.println("Escoja un número del 1 al 5 para seleccionar una de estos " +
                    "servicios:");
            for (String servicio : lista_servicios) {
                System.out.println(iterServ + ". " + servicio);
                iterServ++;
            }
            System.out.println(iterServ + ". Finalizar programa");
            Scanner teclado = new Scanner(System.in);
            boolean parar = false;
            while (!parar & teclado.hasNextInt()) {
                int opcionEscogida = teclado.nextInt();
                if (opcionEscogida == 5) {
                    parar = true;
                    teclado.close();
                } else {
					String servicioEscogido = lista_servicios.get(opcionEscogida - 1);
                    if (servicioEscogido.contains("insertar_libro")) {
                        System.out.println("Introduzca el parámetro: ");
                        teclado.nextLine();
                        String [] parametros = {teclado.nextLine()};
						brokerInterface.ejecutar_servicio("insertar_libro",parametros);
						
                    } else {
                        int i = servicioEscogido.indexOf(" ");
                        int i2 = servicioEscogido.indexOf("(");
                        String servicio = servicioEscogido.substring(i+1, i2);
                        System.out.println("LOG1: " + servicio);
                        String respuesta = brokerInterface.ejecutar_servicio(servicio,new String[0]);
                        System.out.println(respuesta);
                    }
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
