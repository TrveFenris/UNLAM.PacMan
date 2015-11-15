package paquetes;

import java.util.ArrayList;

/**
 *Paquete de datos para el manejo de inicio de sesion, cierre de sesion, y registro de usuario
 */
public class PaqueteSesion extends Paquete {

	private static final long serialVersionUID = 4360740960940604321L;
	private String nombre;
	private String password;
	private Solicitudes solicitud;
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
		this.solicitud = Solicitudes.LOGIN;
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
	 * @return contrase�a ingresada por el usuario que envio el paquete.
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
	
	public void setSolicitud(Solicitudes s) {
		solicitud = s;
	}
	
	/**
	 * Indica que el paquete se utilizara para iniciar sesion.
	 */
	/*
	public void setIniciarSesion(){
		solicitud=Solicitudes.LOGIN;
	}
	
	/**
	 * Indica que el paquete se utilizara para cerrar sesion.
	 */
	/*
	public void setCerrarSesion(){
		solicitud=Solicitudes.LOGOUT;
	}
	
	/**
	 * Indica que el paquete se utilizara para registrar un nuevo usuario en la base de datos del servidor.
	 */
	/*
	public void setRegistrarUsuario(){
		solicitud=Solicitudes.REGISTRO;
	}
	
	/**
	 * Indica que el paquete se utilizar� para solicitar la lista de partidas disponibles.
	 */
	/*
	public void setBuscarPartida(){
		solicitud=Solicitudes.BUSCAR_PARTIDA;
	}
	
	/**
	 * Indica que el paquete se utilizar� para solicitar el ingreso a una partida.
	 */
	/*
	public void setUnirseAPartida(){
		solicitud=Solicitudes.ENTRAR_EN_PARTIDA;
	}
	
	/**
	 * Utilizado por el servidor para consultar la accion que desea realizar el cliente
	 */
	public TipoPaquete getTipo(){
		return TipoPaquete.SESION;
	}
	
	public Solicitudes getSolicitud(){
		return solicitud;
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
