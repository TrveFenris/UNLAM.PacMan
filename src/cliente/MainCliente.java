package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainCliente {

    public static void main(String args[]) {

        String host = "localhost";
        int puerto = 50000;

        System.out.println("CLIENTE DEL CHAT:\n----------------------------\n");
        System.out.println("Ingrese la IP o Nombre del Servidor a conectarse: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            host = br.readLine();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        
        System.out.println("Ingrese el puerto del servidor a conectar: ");
        //disServer = new BufferedReader(new InputStreamReader(System.in));
        try {
            puerto = Integer.parseInt(br.readLine());
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }

        Cliente cliente = new Cliente(host, puerto);
        System.out.println("Ingrese su nombre de usuario: ");
        try {
            //System.in.skip(System.in.available());
            cliente.setNombre(br.readLine());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("(Ingrese .exit en cualquier momento para cerrar la aplicacion)");

        new ThreadCliente(cliente.getSocket()).start();

        cliente.enviarMensaje();
        cliente.cerrarCliente();
    }
}
