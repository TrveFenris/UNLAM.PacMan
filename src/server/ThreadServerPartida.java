package server;

import java.net.Socket;
import java.util.ArrayList;

import game.Configuracion;

public class ThreadServerPartida {
	private ArrayList<Socket>jugadores;
    private Server servidor;
    private String nombrePartida;
    
    /**
     * Crea un thread que actua como servidor para la comunicacion realizada durante una partida.
     * @param serv -Servidor que provee la partida.
     * @param nombre -Nombre publico de la partida.
     */
    public ThreadServerPartida(Server serv, String nombre) {
        servidor=serv;
        nombrePartida=nombre;
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
}
