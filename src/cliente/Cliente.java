package cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;

public class Cliente {

    private Socket cliente;
    private String nombre = null;
    private int puerto;

    public int getPuerto() {
        return puerto;
    }

    public Cliente(String direccion, int port, String name) throws UnknownHostException, IOException{
    	puerto = port;
    	cliente = new Socket(direccion, port);
        nombre = name;
    }

    public Socket getSocket() {
        return cliente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void enviarMensaje() {
    	/* Seccion comentada porque readLine se comporta de modo bloqueante
    	 * y no deja cerrar el thread que lanza la MainWindow, utilizado
    	 * para ejecutar este metodo*/
        /*
    	try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
          	//Se lee desde el host del usuario y dirige el flujo o informacion al server
            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
            String data;
            while(!cliente.isClosed()) {}
            
            while ((data=br.readLine())!= null) {
                if (data.contains(".exit")) {
                    cerrarCliente();
                }
                else if (!data.equals("")) {
                	dos.writeUTF("["+horaDelMensaje()+"] " + nombre + ": " + data);
                }
            }
            
            cerrarCliente();
            System.out.println("Sali del while");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
        	cerrarCliente();
        }
        */
        	 while(!cliente.isClosed()) {} //Ciclo mientras el socket este abierto
        	 cerrarCliente();
    }

    public void cerrarCliente() {
        try {
        	if(cliente != null && !cliente.isClosed()) {
        		System.out.println("Cerrando cliente...");
        		cliente.close();
        	}
            //System.exit(1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String horaDelMensaje() {
        Calendar cal = Calendar.getInstance();
        return +cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE)
                + ":" + cal.get(Calendar.SECOND);
    }
}
