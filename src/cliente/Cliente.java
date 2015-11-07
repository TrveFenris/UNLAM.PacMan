package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import server.PaqueteSesion;
import java.util.*

public class Cliente {

    private Socket cliente;
    private String nombre;
    private String password;
    private int puerto;

    public int getPuerto() {
        return puerto;
    }

    public Cliente(String direccion, int port) throws UnknownHostException, IOException{
    	puerto = port;
    	cliente = new Socket(direccion, port);
        nombre = password=null;
    }

    public Socket getSocket() {
        return cliente;
    }

    /**
     * Establece los datos del cliente
     * @param nombre
     * @param password
     */
    public void setDatos(String nombre, String password) {
        this.nombre = nombre;
        this.password=password;
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
    
    /**
     * Inicia sesion en el servidor
     * @return si el inicio de sesion fue exitoso o no.
     */
    public boolean iniciarSesion(){
    	boolean respuesta=false;
    	try {
    		DataOutputStream d = new DataOutputStream(cliente.getOutputStream());
            ObjectOutputStream o = new ObjectOutputStream(d);
        	PaqueteSesion paquete = new PaqueteSesion(nombre, password);
        	paquete.setIniciarSesion();
        	o.writeObject(paquete);
        	//
            DataInputStream data= new DataInputStream(cliente.getInputStream());
            ObjectInputStream is = new ObjectInputStream(data);
            paquete=(PaqueteSesion)is.readObject();
            if(paquete.getAck()){
            	respuesta=true;
	        }
            else
            	respuesta=false;
        }
        catch(EOFException e){
        	System.out.println("Error en la comunicación con el servidor (iniciarSesion)");
            cerrarCliente();
        }
        catch(IOException e) {
        	System.out.println("Error: IOException (iniciarSesion)");
        	cerrarCliente();
        } catch (ClassNotFoundException e1) {
			cerrarCliente();
		}
    	return respuesta;
    }
    
    /**
     * Registra el usuario en el servidor
     * @return si el registro fue exitoso o no.
     */
    public boolean registrarUsuario(){
    	boolean respuesta=false;
    	try {
    		DataOutputStream d = new DataOutputStream(cliente.getOutputStream());
            ObjectOutputStream o = new ObjectOutputStream(d);
        	PaqueteSesion paquete = new PaqueteSesion(nombre, password);
        	paquete.setRegistrarUsuario();
        	o.writeObject(paquete);
        	//
            DataInputStream data= new DataInputStream(cliente.getInputStream());
            ObjectInputStream is = new ObjectInputStream(data);
            paquete=(PaqueteSesion)is.readObject();
            if(paquete.getAck()){
            	respuesta=true;
	        }
            else
            	respuesta=false;
        }
        catch(EOFException e){
        	System.out.println("Error en la comunicación con el servidor (iniciarSesion)");
            cerrarCliente();
        }
        catch(IOException e) {
        	System.out.println("Error: IOException (iniciarSesion)");
        	cerrarCliente();
        } catch (ClassNotFoundException e1) {
			cerrarCliente();
		}
    	return respuesta;
    }
}
