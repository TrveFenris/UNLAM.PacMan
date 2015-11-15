package server;

import java.net.Socket;
import java.util.ArrayList;

public class Usuario {
	private int inGameId;
	private String nombre;
	private Socket socket;
	private ThreadServer sesion;
	private String partida;
	private ArrayList<Usuario> usuariosEnPartida;
	
	public Usuario(Socket socket){
		this.socket = socket;
	}
	
	public void setId(int inGameId){
		this.inGameId = inGameId;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public void setSocket(Socket socket){
		this.socket = socket;
	}
	
	public void setSesion(ThreadServer thread){
		this.sesion = thread;
	}
	
	public int getId(){
		return inGameId;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public Socket getSocket(){
		return socket;
	}
	
	public ThreadServer getSesion(){
		return sesion;
	}
	
	public void setPartida(String nombrePartida){
		this.partida=nombrePartida;
	}
	
	public String getPartida(){
		return partida;
	}
	
	public void actualizarUsuariosEnPartida(ArrayList<Usuario> usuarios){
		this.usuariosEnPartida=usuarios;
	}
	
	public ArrayList<Usuario> getUsuariosEnPartida(){
		return usuariosEnPartida;
	}
}
