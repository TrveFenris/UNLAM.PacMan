package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import paquetes.PaqueteCoordenadas;

public class UserThread extends Thread {
	
	private Socket userSocket;
	private ArrayList<Usuario> jugadores;
    private Server servidor;
    private boolean running;
    private Usuario user;
    private int id;
    private ThreadServerPartida partida;
	
   // public UserThread(Socket jugador, int id, Server servidor, Socket[] jugadores, ThreadServerPartida partida){
    //UserThread(jugador, 1, servidor, usuarios)
	public UserThread(Usuario jugador, int id, Server servidor, ThreadServerPartida partida, ArrayList<Usuario> jugadores){
		this.userSocket = jugador.getSocket();
		this.jugadores = jugadores;
		this.servidor = servidor;
		this.partida = partida;
		this.running = true;
		this.id = id;
		this.user = jugador;
	}
	
	public void run() {
		try {
        	boolean running=true;
        	System.out.println("Iniciado UserThread de " +user.getNombre());
        	//user.getSesion().getSemaforo().lock();
        	while(running){
        		DataInputStream data= new DataInputStream(userSocket.getInputStream());
            	ObjectInputStream is = new ObjectInputStream(data);
            	try {
            		PaqueteCoordenadas paquete=(PaqueteCoordenadas)is.readObject();
            		//System.out.println(paquete.getCoordenadas().toString());
            		for(Usuario u : jugadores){
            			if(u.getSocket()!=userSocket){
            				DataOutputStream d = new DataOutputStream(u.getSocket().getOutputStream());
            				ObjectOutputStream o = new ObjectOutputStream(d); 
            				o.writeObject(paquete);
            			}
            		}
            	}
            	catch(ClassCastException ex) {
            		System.out.println("El UserThread numero "+id+" recibio un paquete erroneo");
            	}
//        		for(Socket s : jugadores){
//        			if(s!=jugador && !jugador.isClosed()){
//        				DataOutputStream d = new DataOutputStream(s.getOutputStream());
//	    	            ObjectOutputStream o = new ObjectOutputStream(d); 
//	    	            o.writeObject(paquete);
//        			}
//        		}
        	}
        	System.out.println("Un usuario se ha desconectado del servidor (FIN NORMAL)");
        	user.getSesion().desbloquear();
		}
        catch(EOFException e){
                System.out.println("Un usuario se ha desconectado del servidor (EOFException)");
                partida.removerCliente(user);
                user.getSesion().desbloquear();
        }
        catch(IOException e) {
                System.out.println("Un usuario se ha desconectado del servidor (IOException)");
                partida.removerCliente(user);
                user.getSesion().desbloquear();
        } catch (ClassNotFoundException e1) {
        	partida.removerCliente(user);
        	user.getSesion().desbloquear();
		}
		System.out.println("FIN DEL UserThread");
	}
	
	public void pararThread(){
		running=false;
	}
}
