package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import game.Configuracion;
import paquetes.PaqueteCoordenadas;

public class ThreadServerPartida {
	
	private class UserThread extends Thread {
		
		private Socket jugador;
		private ArrayList<Socket> jugadores;
	    private Server servidor;
	    private boolean running;
	    private ThreadServerPartida partida;
		
		public UserThread(Socket jugador, Server servidor, ArrayList<Socket> jugadores, ThreadServerPartida partida){
			this.jugador = jugador;
			this.jugadores = jugadores;
			this.servidor = servidor;
			this.partida = partida;
			this.running = true;
		}
		
		public void run() {
			try {
	        	boolean running=true;
	        	while(running){
	        		DataInputStream data= new DataInputStream(jugador.getInputStream());
	            	ObjectInputStream is = new ObjectInputStream(data);
	        		PaqueteCoordenadas paquete=(PaqueteCoordenadas)is.readObject();
	        		for(Socket s : jugadores){
	        			if(s!=jugador && !jugador.isClosed()){
	        				DataOutputStream d = new DataOutputStream(jugador.getOutputStream());
		    	            ObjectOutputStream o = new ObjectOutputStream(d); 
		    	            o.writeObject(paquete);
	        			}
	        		}
	        	}
	        	System.out.println("Un usuario se ha desconectado del servidor");
			}
	        catch(EOFException e){
	                System.out.println("Un usuario se ha desconectado del servidor");
	        }
	        catch(IOException e) {
	                System.out.println("Un usuario se ha desconectado del servidor");
	        } catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			System.out.println("FIN DEL UserThread");
		}
		
		public void pararThread(){
			running=false;
		}
	}
	
	private ArrayList<Socket> jugadores;
    private Server servidor;
    private String nombrePartida;
    private boolean running;
    private ThreadServerPartida thisThread;
    
    /**
     * Crea un thread que actua como servidor para la comunicacion realizada durante una partida.
     * @param serv -Servidor que provee la partida.
     * @param nombre -Nombre publico de la partida.
     */
    public ThreadServerPartida(Server serv, String nombre) {
        servidor = serv;
        nombrePartida = nombre;
        running = true;
        jugadores = new ArrayList<Socket>();
        thisThread = this;
        System.out.println("Partida "+ nombrePartida +" creada.");
    }
    
    /**
     * Agrega un cliente a la partida.
     * @param jugador
     * @return true/false -Si el jugador puede unirse a la partida.
     */
    public boolean agregarCliente(Socket jugador){
    	if(jugadores.size()>Configuracion.MAX_JUGADORES.getValor()){
    		return false;
    	}
    	jugadores.add(jugador);
    	return true;
    }
    
    /**
     * Elimina un jugador de la partida.
     * @param jugador
     */
    public void removerCliente(Socket jugador){
    	jugadores.remove(jugador);
    }
    
    /**
     * Devuelve un String que representa el nombre público de la partida.
     */
    public String getNombre(){
    	return nombrePartida;
    }
    
    /**
     * Devuelve la lista de jugadores que se encuentran en la partida.
     */
    public ArrayList<Socket> getJugadores(){
    	return jugadores;
    }
    
    /**
     * Devuelve la cantidad de jugadores en la partida.
     */
    public int getCantJugadores(){
    	return jugadores.size();
    }
    
    /**
     * Detiene el thread.
     */
    public void detener(){
    	running=false;
    	System.out.println("Partida "+nombrePartida+" detenida.");
    }
    
    /**
     * Inicia el thread.
     */
    
    public void run() {
    	for(Socket j : jugadores){
    		new UserThread(j, servidor, jugadores, thisThread).start();
    	}
    }
}
