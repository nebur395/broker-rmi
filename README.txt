Instrucciones para ejecutar el broker en RMI con sus respectivos servidores y cliente.

NOTA: Todos los scripts, a excepción de "lanzaClienteC.sh" se lanzan en segundo plano, por lo que 
pueden ejecutarse todos en la misma terminal. Para finalizarlos, ejecutar el comando "ps", comprobar
los procesos "java" y matarlos con el comando "kill".

1º - Abrir los scripts "lanzaBroker.sh", "lanzaServerA.sh", "lanzaServerB.sh" y "lanzaClienteC.sh".

2º - Modificar la ruta que se indica en estos scripts para que coincida con la ruta en la que se encuentra
descargado el proyecto actualmente (cambiar la ruta previa al directorio "broker-rmi").

NOTA: Actualmente el proyecto se encuentra configurado para funcionar en local. Si se quiere lanzar en local
para comprobar su funcionamiento, sigue los pasos 8 a 11. Si se quiere lanzar en distribuido, seguir todos
los pasos que se muestran a continuación.

3º - Determinar la IP de la máquina donde se va a lanzar el Broker. 

4º - Modificar el parámetro "localhost" del script "lanzaBroker.sh" cambiándolo por la IP correspondiente a la máquina del Broker.

5º - Modificar el atributo ipBroker en los fuentes ServerA.java, ServerB.java y ClienteC.java cambiándolo 
por la IP correspondiente a la máquina del Broker. (Esto se debe a que el enunciado del ejercicio especificaba explícitamente
que esos 3 componentes han de conocer la localización del Broker ANTES de compilarse).

6º - Determinar la(s) IP(s) de la(s) máquina(s) donde se vayan a lanzar ServerA y ServerB.

7º - Modificar el parámetro "localhost" de los srcripts "lanzaServerA.sh" y "lanzaServerB.sh" cambiándolo por la IP correspondiente
a la(s) máquina(s) donde se vaya a lanzar cada uno.

8º - Ejecutar en primer lugar el script lanzaBroker.sh. Se mostrará un mensaje que dirá "Broker registrado".

9º - Ejecutar el script lanzaServerA.sh. Esperar hasta que se muestren los mensajes: 
"ServerA registrado en registro RMI"
"ServerA registrado en Broker"
"Servicios de ServerA registrados en Broker"

10º - Ejecutar el script lanzaServerB.sh. Esperar hasta que se muestren los mensajes: 
"ServerB registrado en registro RMI"
"ServerB registrado en Broker"
"Servicios de ServerB registrados en Broker"

11º - Ejecutar el script lanzaClienteC.sh. 

En este punto ya estará todo el proyecto montado y el cliente podrá hacer uso de los servicios listados.

