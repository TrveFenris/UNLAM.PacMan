package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadServerSesion extends Thread {

    private Socket clientSocket;
    private Server servidor;
    private DataBase database;
    private PaqueteSesion paquete;
    
    public ThreadServerSesion(Socket socket,Server serv, DataBase database) {
        clientSocket = socket;
        servidor=serv;
        this.database=database;
        paquete=null;
    }

    public void run() {
        try {
        	boolean run=true;
        	while(run){
        		DataInputStream data= new DataInputStream(clientSocket.getInputStream());
            	ObjectInputStream is = new ObjectInputStream(data);
        		paquete=(PaqueteSesion)is.readObject();
                if (!clientSocket.isClosed()) {
    	            DataOutputStream d = new DataOutputStream(clientSocket.getOutputStream());
    	            ObjectOutputStream o = new ObjectOutputStream(d);
    	            //ACCIONES A REALIZAR SEGUN EL TIPO DE PAQUETE RECIBIDO
    	            switch(paquete.getTipoPaquete()){
    	            	case LOGIN:
    	            		paquete.setResultado(true);
    	            		//paquete.setResultado(database.verificarDatos(paquete.getNombre(), paquete.getPassword()));
    	            		break;
    	            	case LOGOUT:
    	            		paquete.setResultado(true);
    	            		run=false;
    	            		break;
    	            	case REGISTRO:
    	            		paquete.setResultado(false);
    	            		//paquete.setResultado(database.registrarUsuario(paquete.getNombre(), paquete.getPassword()));
    	            		break;
    	            }
    	            o.writeObject(paquete);
                }
        	}
        }
        catch(EOFException e){
            try {
            	System.out.println("Cerrando cliente");
                clientSocket.close();
                servidor.eliminarCliente();
                System.out.println("Un cliente se ha desconectado.");
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        catch(IOException e) {
        	e.printStackTrace();
            try {
            	System.out.println("Cerrando cliente");
                clientSocket.close();
                servidor.eliminarCliente();
                System.out.println("Un cliente se ha desconectado.");
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
    }
}
