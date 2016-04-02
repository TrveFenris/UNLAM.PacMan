package server;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

import gameobject.Jugador;

public class DatosPartida {
	
	private ArrayList<Usuario> usuarios;
	private ArrayList<Jugador> jugadores;
	private ReentrantLock semaforoJugador;
    private ReentrantLock semaforoBolitas;
    
    public DatosPartida(){
    	semaforoJugador = new ReentrantLock();
    	semaforoBolitas = new ReentrantLock();
    	usuarios = new ArrayList<>();
    	jugadores = new ArrayList<>();
    };
    
    /**
     * Devuelve una lista con los usuarios actualmente en la partida.
     */
    public ArrayList<Usuario> getUsuarios(){
    	return usuarios;
    }
    
    /**
     * Devuelve una lista con los jugadores actualmente en la partida.
     */
    public ArrayList<Jugador> getJugadores(){
    	return jugadores;
    }
    
    /**
     * Devuelve el semáforo utilizado para coordinar acciones entre jugadores.
     */
    public ReentrantLock getSemaforoJugador(){
    	return semaforoJugador;
    }
    
    /**
     * Devuelve el semáforo utilizado para coordinar acciones entre jugadores.
     */
    public ReentrantLock getSemaforoBolitas(){
    	return semaforoBolitas;
    }
    
    /**
     * Agrega el usuario a la partida
     */
    public void agregarUsuario(Usuario usuario){
    	this.usuarios.add(usuario);
    }
    
    /**
     * Elimina el usuario de la partida
     */
    public void removerUsuario(Usuario usuario){
    	this.usuarios.remove(usuario);
    }
    
    /**
     * Elimina todos los usuarios de la partida
     */
    public void removerTodosLosUsuarios(){
    	this.usuarios.clear();
    }
}
