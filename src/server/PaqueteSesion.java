package server;

public class PaqueteSesion implements java.io.Serializable{

	private static final long serialVersionUID = 3L;
	private String nombre;
	private String password;
	private boolean sesion; //true = inicio de sesion; false = cierre de sesion
	private boolean ack;
	
	/**
	 * Crea un paquete de datos para la comunicacion durante el inicio y cierre de sesion
	 * @param nombre
	 * @param password
	 */
	public PaqueteSesion(String nombre, String password){
		this.nombre=nombre;
		this.password=password;
		this.ack=false;
		this.sesion=true;
	}
	/**
	 * 
	 * @return nombre del usuario que envio el paquete
	 */
	public String getNombre(){
		return nombre;
	}
	
	/**
	 * 
	 * @return contraseña ingresada por el usuario que envio el paquete
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * Permite consultar si se pudo iniciar sesion correctamente
	 * @return 	true: si el inicio de sesion fue exitoso -
	 * 			false: si hubo algún problema
	 */
	public boolean getAck(){
		return this.ack;
	}
	
	public void setAck(boolean v){
		this.ack=v;
	}
	
	/**
	 * Indica que el paquete se utilizara para iniciar sesion
	 */
	public void setIniciarSesion(){
		this.sesion=true;
	}
	
	/**
	 * Indica que el paquete se utilizara para cerrar sesion
	 */
	public void setCerrarSesion(){
		this.sesion=false;
	}
	
	/**
	 * Utilizado por el servidor para consultar si el cliente desea iniciar o cerrar una sesion
	 * @return	true: inicio de sesion -
	 * 			false: cierre de sesion
	 */
	public boolean getSesion(){
		return sesion;
	}
}
