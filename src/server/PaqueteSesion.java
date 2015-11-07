package server;

import cliente.TipoPaquete;
/**
 *Paquete de datos para el manejo de inicio de sesion, cierre de sesion, y registro de usuario
 */
public class PaqueteSesion implements java.io.Serializable{

	private static final long serialVersionUID = 3L;
	private String nombre;
	private String password;
	private TipoPaquete tipo;
	private boolean resultado;
	
	/**
	 * Crea un paquete de datos para la comunicacion durante el inicio y cierre de sesion.
	 * @param nombre
	 * @param password
	 */
	public PaqueteSesion(String nombre, String password){
		this.nombre=nombre;
		this.password=password;
		this.resultado=false;
		this.tipo=TipoPaquete.LOGIN;
	}
	/**
	 * 
	 * @return nombre del usuario que envio el paquete.
	 */
	public String getNombre(){
		return nombre;
	}
	
	/**
	 * 
	 * @return contraseña ingresada por el usuario que envio el paquete.
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * Permite consultar si se pudo realizar la operacion solicitada correctamente.
	 * @return 	si la operacion fue exitosa o no.
	 */
	public boolean getResultado(){
		return this.resultado;
	}
	
	/**
	 * Guarda el resultado de la operacion realizada
	 */
	public void setResultado(boolean valor){
		this.resultado=valor;
	}
	
	/**
	 * Indica que el paquete se utilizara para iniciar sesion.
	 */
	public void setIniciarSesion(){
		this.tipo=TipoPaquete.LOGIN;
	}
	
	/**
	 * Indica que el paquete se utilizara para cerrar sesion.
	 */
	public void setCerrarSesion(){
		this.tipo=TipoPaquete.LOGOUT;
	}
	
	/**
	 * Indica que el paquete se utilizara para registrar un nuevo usuario en la base de datos del servidor.
	 */
	public void setRegistrarUsuario(){
		this.tipo=TipoPaquete.REGISTRO;
	}
	
	/**
	 * Utilizado por el servidor para consultar la accion que desea realizar el cliente
	 */
	public TipoPaquete getTipoPaquete(){
		return tipo;
	}
}
