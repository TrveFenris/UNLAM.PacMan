package server;

import java.net.Socket;

public class Usuario {
	private int inGameId;
	private String nombre;
	private Socket socket;
	private ThreadServerSesion sesion;
	private UserThread userThread;
	
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
	
	public void setSesion(ThreadServerSesion thread){
		this.sesion = thread;
	}
	
	public void setUserThread(UserThread thread){
		this.userThread = thread;
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
	
	public ThreadServerSesion getSesion(){
		return sesion;
	}
	
	public UserThread getUserThread(){
		return userThread;
	}
}
