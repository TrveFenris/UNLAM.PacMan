package server;

public class PaqueteLogin implements java.io.Serializable{

	private static final long serialVersionUID = 3L;
	private String nombre;
	private String password;
	private boolean ack;
	
	public PaqueteLogin(String nombre, String password){
		this.nombre=nombre;
		this.password=password;
		this.ack=false;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public String getPassword(){
		return password;
	}
	
	public boolean getAck(){
		return this.ack;
	}
	
	public void setAck(boolean v){
		this.ack=v;
	}
}
