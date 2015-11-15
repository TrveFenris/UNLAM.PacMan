package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

import paquetes.PaqueteSesion;

public class ThreadServerSesion extends Thread {

    private Socket clientSocket;
    private Server servidor;
    private DataBase database;
    private PaqueteSesion paquete;
    private boolean locked;
    private Usuario user;
    
    public ThreadServerSesion(Server servidor, Usuario usuario) {
        clientSocket = usuario.getSocket();
        this.servidor=servidor;
        this.database=servidor.getDatabase();
        paquete=null;
        usuario.setNombre("user");
        user = usuario;
    }

    public synchronized  void run() {
        try {
        	boolean run = true;
        	while(run){
        		while(locked){
            		try {
	            		System.out.println("threadSesion de "+user.getNombre()+" BLOQUEADO");
    					wait();
	            		System.out.println("threadSesion de "+user.getNombre()+" DESBLOQUEADO");
    				} catch (InterruptedException e) {
    					//
    				}
            	}
        		DataInputStream data= new DataInputStream(clientSocket.getInputStream());
            	ObjectInputStream is = new ObjectInputStream(data);
        		paquete=(PaqueteSesion)is.readObject();
                if (!clientSocket.isClosed()) {
    	            DataOutputStream d = new DataOutputStream(clientSocket.getOutputStream());
    	            ObjectOutputStream o = new ObjectOutputStream(d);
    	            //ACCIONES A REALIZAR SEGUN EL TIPO DE PAQUETE RECIBIDO
    	            switch(paquete.getSolicitud()){
    	            	case LOGIN:
    	            		user.setNombre(paquete.getNombre());
    	            		System.out.println(user.getNombre()+" se ha conectado al servidor");
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
    	            		run=false;
    	            		break;
    	            	case BUSCAR_PARTIDA:
    	            		enviarListaDePartidas();
    	            		break;
    	            	case UNIRSE_PARTIDA:
    	            		System.out.println(paquete.getMensaje());
    	            		boolean res=servidor.agregarAPartida(user, paquete.getMensaje());
    	            		paquete.setResultado(res);
    	            		if(res==true)
    	            			bloquear();
    	            		break;
    	            }
    	            o.writeObject(paquete);
    	            System.out.println("DATOS ENVIADOS");
                }
        	}
        	 System.out.println(user.getNombre()+" se ha desconectado del servidor");
        	 clientSocket.close();
        	 servidor.eliminarCliente();
        }
        catch(EOFException e){
            try {
                clientSocket.close();
                servidor.eliminarCliente();
                System.out.println(user.getNombre()+" se ha desconectado del servidor");
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        catch(IOException e) {
        	e.printStackTrace();
            try {
                clientSocket.close();
                servidor.eliminarCliente();
                System.out.println(user.getNombre()+" se ha desconectado del servidor");
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
    }
    
    private void enviarListaDePartidas(){
    	System.out.println("PREPARANDO LISTA");
            for(ThreadServerPartida partida : servidor.getPartidas()){
            	paquete.agregarPartida(partida.getNombre(),partida.getCantJugadores());
            }
    }

    public void bloquear(){
    	locked = true;
    }
    
    public void desbloquear(){
    	locked = false;
    }
}
