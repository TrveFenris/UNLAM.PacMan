package server;

import game.ConfiguracionSprites;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Usuario {
	private int inGameId;
	private String nombre;
	private Socket socket;
	private ThreadServer sesion;
	private String partida;
	private ObjectOutputStream outputStream;
	private ConfiguracionSprites skinPacman;
	private ConfiguracionSprites skinFantasma;
	private boolean ready;
	private boolean esPacman;
	@Deprecated
	private ArrayList<Usuario> usuariosEnPartida;
	
	public Usuario(Socket socket){
		this.socket = socket;
		this.partida="";
		try {
			//this.outputStream = new ObjectOutputStream(new DataOutputStream(this.socket.getOutputStream()));
			this.outputStream = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	public ObjectOutputStream getOutputStream(){
		return outputStream;
	}
	
	public ConfiguracionSprites getSkinPacman(){
		return skinPacman;
	}
	public ConfiguracionSprites getSkinFantasma(){
		return skinFantasma;
	}
	public void setSkinPacman(ConfiguracionSprites skin){
		skinPacman = skin;
	}
	public void setSkinFantasma(ConfiguracionSprites skin){
		skinFantasma = skin;
	}
	public boolean esPacman() {
		return esPacman;
	}
	public void setEsPacman(boolean set) {
		esPacman = set;
	}
	public boolean isReady() {
		return ready;
	}
	public void setReady(boolean estado) {
		ready = estado;
	}
	@Deprecated
	public void actualizarUsuariosEnPartida(ArrayList<Usuario> usuarios){
		this.usuariosEnPartida=usuarios;
	}
	@Deprecated
	public ArrayList<Usuario> getUsuariosEnPartida(){
		return usuariosEnPartida;
	}
}
