package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import paquetes.PaqueteSesion;

public class ThreadServerSesion extends Thread {

    private Socket clientSocket;
    private Server servidor;
    private DataBase database;
    private PaqueteSesion paquete;
    private String nombre;
    
    public ThreadServerSesion(Socket socket,Server serv, DataBase database) {
        clientSocket = socket;
        servidor=serv;
        this.database=database;
        paquete=null;
        nombre="user";
    }

    public synchronized  void run() {
        try {
        	boolean run = true;
        	boolean locked = false;
        	while(run){
        		while(locked){
            		try {
            			System.out.println("BLOQUEADO");
    					wait();
    					System.out.println("DESBLOQUEADO");
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
    	            switch(paquete.getTipoPaquete()){
    	            	case LOGIN:
    	            		nombre=paquete.getNombre();
    	            		System.out.println(paquete.getNombre()+" se ha conectado al servidor");
    	            		paquete.setResultado(true);
    	            		servidor.agregarNombre(nombre);
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
    	            	case ENTRAR_EN_PARTIDA:
    	            		System.out.println(paquete.getMensaje());
    	            		boolean res=servidor.agregarAPartida(clientSocket, paquete.getMensaje());
    	            		paquete.setResultado(res);
    	            		if(res==true)
    	            			locked = true;
    	            		break;
    	            }
    	            o.writeObject(paquete);
    	            System.out.println("DATOS ENVIADOS");
                }
        	}
        	 System.out.println(nombre+" se ha desconectado del servidor");
        	 clientSocket.close();
        	 servidor.eliminarCliente();
        	 servidor.removerNombre(nombre);
        }
        catch(EOFException e){
            try {
                clientSocket.close();
                servidor.removerNombre(nombre);
                servidor.eliminarCliente();
                System.out.println(nombre+" se ha desconectado del servidor");
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        catch(IOException e) {
        	e.printStackTrace();
            try {
                clientSocket.close();
                servidor.removerNombre(nombre);
                servidor.eliminarCliente();
                System.out.println(nombre+" se ha desconectado del servidor");
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

}
