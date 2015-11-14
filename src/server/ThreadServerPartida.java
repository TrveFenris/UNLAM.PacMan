package server;

import game.Configuracion;
import game.Partida;

import java.util.ArrayList;

public class ThreadServerPartida extends Thread{
	
    private Server servidor;
    private String nombrePartida;
    private Partida partida;
    private boolean running;
    private ThreadServerPartida thisThread;
    //private ArrayList<Socket> jugadores;
    //private ArrayList<UserThread> threads;
    private ArrayList<Usuario> usuarios;
    
    /**
     * Crea un thread que actua como servidor para la comunicacion realizada durante una partida.
     * @param serv -Servidor que provee la partida.
     * @param nombre -Nombre publico de la partida.
     */
    public ThreadServerPartida(Server serv, String nombre) {
        servidor = serv;
        nombrePartida = nombre;
        running = true;
        usuarios = new ArrayList<Usuario>();
        thisThread = this;
        System.out.println("Partida "+ nombrePartida +" creada.");
    }
    
    /**
     * Agrega un cliente a la partida.
     * @param jugador
     * @return true/false -Si el jugador puede unirse a la partida.
     */
//    public boolean agregarCliente(Socket jugador){
//    	if(getCantJugadores()>Configuracion.MAX_JUGADORES.getValor()){
//    		return false;
//    	}
//    	for(int i=0; i<Configuracion.MAX_JUGADORES.getValor();i++){
//    		if(jugadores[i]==null){
//    			jugadores[i]=jugador;
//    			threads[i]=new UserThread(jugador, i, servidor, jugadores, thisThread);
//    		}
//    	}
//    	System.out.println("Jugador agregado a la partida "+nombrePartida);
//    	return true;
//    }
    
    /**
     * Agrega un usuario a la partida.
     * @param usuario
     * @return true/false -Si el jugador puede unirse a la partida.
     */
    public boolean agregarUsuario(Usuario usuario){
    	if(getCantJugadores()>Configuracion.MAX_JUGADORES.getValor()){
    		return false;
    	}
    	usuarios.add(usuario);
    	System.out.println(usuario.getNombre()+" agregado a la partida "+nombrePartida);
    	if(usuarios.size()==2){
    		for(Usuario s : usuarios){
        		s.setUserThread(new UserThread(s, 1, servidor, thisThread, usuarios));
            	s.getUserThread().start();	
        	}
    	}
    	return true;
    }
    
    /**
     * Elimina un jugador de la partida.
     * @param jugador
     */
    public void removerCliente(Usuario usuario){
    	usuarios.remove(usuario);
    }
    
    /**
     * Devuelve un String que representa el nombre público de la partida.
     */
    public String getNombre(){
    	return nombrePartida;
    }
    public Partida getPartida() {
    	return partida;
    }
    /**
     * Devuelve la lista de jugadores que se encuentran en la partida.
     */
//    public ArrayList<Socket> getJugadores(){
//    	return jugadores;
//    }
    
    /**
     * Devuelve la cantidad de jugadores en la partida.
     */
    public int getCantJugadores(){
    	return usuarios.size();
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
//    	for(Socket j : jugadores){
//    		new UserThread(j, servidor, jugadores, thisThread).start();
//    	}
    }
}
