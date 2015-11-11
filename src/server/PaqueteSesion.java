package server;

import java.util.*;

/**
 *Paquete de datos para el manejo de inicio de sesion, cierre de sesion, y registro de usuario
 */
public class PaqueteSesion implements java.io.Serializable{

	private static final long serialVersionUID = 3L;
	private String nombre;
	private String password;
	private TipoPaquete tipo;
	private boolean resultado;
	private ArrayList<String> listaPartidas;
	private String mensaje;
	
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
		listaPartidas= new ArrayList<String>();
		mensaje = null;
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
	 * Indica que el paquete se utilizará para solicitar la lista de partidas disponibles.
	 */
	public void setBuscarPartida(){
		this.tipo=TipoPaquete.BUSCAR_PARTIDA;
	}
	
	/**
	 * Indica que el paquete se utilizará para solicitar el ingreso a una partida.
	 */
	public void setUnirseAPartida(){
		this.tipo=TipoPaquete.ENTRAR_EN_PARTIDA;
	}
	
	/**
	 * Utilizado por el servidor para consultar la accion que desea realizar el cliente
	 */
	public TipoPaquete getTipoPaquete(){
		return tipo;
	}
	
	/**
	 * Agrega la informacion de una partida al paquete
	 * @param nombre
	 * @param cantJugadores
	 */
	public void agregarPartida(String nombre, int cantJugadores){
		listaPartidas.add(nombre+" "+Integer.toString(cantJugadores));
	}
	
	/**
	 * Devuelve la lista con informacion de las partidas disponibles.
	 */
	public ArrayList<String> getInfoPartidas(){
		return listaPartidas;
	}
	
	/**
	 * Guarda un mensaje para enviar al servidor.
	 */
	public void setMensaje(String mensaje){
		this.mensaje=mensaje;
	}
	
	/**
	 * Obtiene el mensaje grabado en el paquete.
	 */
	public String getMensaje(){
		return this.mensaje;
	}
}
