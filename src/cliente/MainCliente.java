package cliente;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

@Deprecated
public class MainCliente {

    public static void main(String args[]) {

        String host = "localhost";
        int puerto = 50000;
        String nombre="User";

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
        try {
            puerto = Integer.parseInt(br.readLine());
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }

        System.out.println("Ingrese su nombre de usuario: ");
        try {
            //System.in.skip(System.in.available());
        	nombre=br.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        EventQueue.invokeLater(new Runnable() {
        	@Override
			public void run() {
				try {
					MainWindowSinDB window = new MainWindowSinDB();
					window.toFront();
					window.repaint();
					window.setLocationRelativeTo(null);
					window.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        Cliente cliente = null;
        try {
        	cliente = new Cliente(host, puerto);
        }
        catch(UnknownHostException e1) {

		}
        catch (IOException e2) {

        }
        System.out.println("(Ingrese .exit en cualquier momento para cerrar la aplicacion)");
        new ThreadCliente(cliente.getSocket()).start();
        cliente.enviarMensaje();
        cliente.cerrarCliente();
    }
}
