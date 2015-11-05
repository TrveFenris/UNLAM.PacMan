package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
@Deprecated
public class MainServer {
    public static void main(String[] args) {
        Socket socket = null;
        boolean bandera = true;
        Server servidor = null;
        int puerto = 50000;
        int maximoConexiones = 4;

        System.out.println("SERVIDOR DE CHAT:\n-----------------------------\n");
        System.out.println("Ingrese el puerto a utilizar: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
        	puerto = Integer.parseInt(br.readLine());
        }
        catch (IOException e) {
            System.out.println("Error al ingresar el puerto, cerrando aplicacion...");
            System.exit(-1);
        }
        System.out.println("Ingrese la cantidad maxima de usuarios a soportar: ");

        try {
            maximoConexiones = Integer.parseInt(br.readLine());
        }
        catch (IOException e) {
            System.out.println("Error al ingresar el puerto, cerrando aplicacion...");
            System.exit(-1);
        }

        servidor = new Server(puerto, maximoConexiones);

        System.out.println("DATOS DEL SERVIDOR:\n-------------------\n");
        System.out.println("Nombre del Servidor:\t" + servidor.getNombreHost());
        System.out.println("IP del Servidor:\t" + servidor.getIPHost());
        System.out.println("Puerto de Escucha:\t" + servidor.getPuerto());
        System.out.println("Cantidad Maxima de Clientes:\t" + servidor.getMax_clientes());
        System.out.println("\nServidor en escucha...");

        while (bandera) {
        	System.out.println("ESPERANDO");
            socket = servidor.aceptarConexion();
            if (socket != null)
            	new ThreadServer(socket, servidor.getLista(),servidor).start();
        }
        servidor.pararServidor();
    }
}
