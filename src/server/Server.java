package server;

import game.Configuracion;
import game.Mapa;
import game.Partida;
import gameobject.Jugador;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Objeto que permite aceptar y manejar conexiones
 */
public class Server {

    private ServerSocket servidor;
    private Socket cliente;
    private int cantActualClientes;
    private ArrayList<Socket> sockets;
    private int max_clientes;
    private int puerto;
    private String nombreHost;
    private String IPHost;
    private DataBase database;
    private ServerWindow serverWindow;
    private ArrayList<Usuario> usuarios;
    private HashMap<Partida, DatosPartida> infoPartidas;
    private HashMap<String, Partida> nombresDePartida;
    
    /**
     * Crea un nuevo servidor en un puerto determinado, con un maximo de clientes a manejar,
     * y la ventana principal, donde escribirá su lista de nombres.
     * @param port
     * @param max_conexiones
     */
    public Server(int port, int max_conexiones, ServerWindow serverWindow) throws IOException{
        nombreHost = InetAddress.getLocalHost().getHostName().toString();
        IPHost = InetAddress.getLocalHost().getHostAddress().toString();
        this.serverWindow=serverWindow;
        puerto = port;
        max_clientes = max_conexiones;
        cantActualClientes = 0;
        sockets = new ArrayList<Socket>();
        database = new DataBase();
        servidor = new ServerSocket(puerto);
        usuarios = new ArrayList<Usuario>();
        this.nombresDePartida=new HashMap<String, Partida>(); 
        this.infoPartidas=new HashMap<Partida, DatosPartida>();
    }

    /**
     * Devuelve el nombre del servidor
     */
    public String getNombreHost() {
        return nombreHost;
    }

    /**
     * Devuelve el ip del servidor
     */
    public String getIPHost() {
        return IPHost;
    }
    
    /**
     * Devuelve la cantidad maxima de clientes que maneja el servidor
     */
    public int getMax_clientes() {
        return max_clientes;
    }

    /**
     * Devuelve el puerto de escucha del servidor
     */
    public int getPuerto() {
        return puerto;
    }
    
    /**
     * Devuelve la lista de sockets utilizados por el servidor
     */
    public ArrayList<Socket> getListaSockets() {
        return sockets;
    }
    
    /**
     * Elimina una partida de la lista de partidas del servidor.
     * @param nombre
     */
    public void eliminarPartida(String nombre){
    	
    }
    
    /**
     * Devuelve la lista de partidas que se estan ejecutando.
     */
    public ArrayList<String> getPartidas(){
    	ArrayList<String> nombres = new ArrayList<String>();
    	for(Partida p : nombresDePartida.values()){
    		nombres.add(p.getNombre());
    	}
    	return nombres;
    }
    
    /**
     * Agrega el usuario a la partida seleccionada.
     * @return true/false -Si pudo o no, agregar al usuario.
     */
    public boolean agregarUsuarioAPartida(Usuario usuario, String partida){
    			Partida p = nombresDePartida.get(partida);
    			if(p!=null){
    				DatosPartida datos = infoPartidas.get(p);
	    			if(datos.getUsuarios().size()>Configuracion.MAX_JUGADORES_PARTIDA.getValor()){
	    				return false;
	    			}
	    			/*
	    			if(usuario.esPacman()) {
	    				nombresDePartida.get(partida).agregarJugador(new Pacman(new Punto(0,0), usuario.getNombre(), usuario.getSkinPacman(), usuario.getId()) ); //FIXME
	    			}
	    			else {
	    				nombresDePartida.get(partida).agregarJugador(new Fantasma(new Punto(0,0), usuario.getNombre(), usuario.getSkinFantasma(), usuario.getId()) ); //FIXME
	    			}
	    			*/
	    			datos.agregarUsuario(usuario);
	    			usuario.setPartida(partida);
	    			System.out.println(usuario.getNombre()+" agregado a la partida " + partida);
	    			return true;
    		}
    	return false;
    }
    
    /**
     * Agrega el jugador a la partida seleccionada.
     * @return true/false -Si pudo o no, agregar al jugador.
     */
    public void agregarJugadorAPartida(Jugador jugador, String partida){
    			Partida p = nombresDePartida.get(partida);
	    		p.agregarJugador(jugador);
    }
    
    public void eliminarDePartida(Usuario usuario, String partida) {
    	Partida p = nombresDePartida.get(partida);
    	DatosPartida datos = infoPartidas.get(p);
    	datos.removerUsuario(usuario);
    	System.out.println(usuario.getNombre()+" ha abandonado la partida "+partida+"\t"+datos.getUsuarios().size());
    	serverWindow.actualizarListaDePartidas();
    }
    
    public void eliminarTodosLosJugadoresDePartida(String partida){
    	Partida p = nombresDePartida.get(partida);
    	DatosPartida datos = infoPartidas.get(p);
    	datos.removerTodosLosUsuarios();
    }
    /**
     * Acepta una conexion
     * @return Socket con la informacion del cliente
     */
    public Socket aceptarConexion() {
    	cliente=null;
        try {
        	servidor.setSoTimeout(1000);
            cliente = servidor.accept();
            cantActualClientes++;
            //FIXME
            if (cantActualClientes > max_clientes) {
                //DataOutputStream d = new DataOutputStream(cliente.getOutputStream());
                //d.writeUTF("Servidor Lleno");
                cliente.close();
                cantActualClientes--;
                return null;
            }
            else{
            	sockets.add(cliente);
            }
        } 
        catch(SocketTimeoutException e){
        	return null;
        }
        catch (IOException e) {
            System.out.println("Error al aceptar conexiones, Cerrando el Servidor...");
            System.exit(-1);
        } 
        return cliente;
    }

    /**
     * Detiene el servidor
     */
    public void pararServidor() {
        try {
        	if(!servidor.isClosed())
        		servidor.close();
        }
        catch (IOException e) {
            System.out.println("Error al cerrar el servidor");
        }
    }
    
    /**
     *Actualiza el contador de clientes conectados, y lo elimina de la lista de nombres del MainWindow
     */
    public void eliminarCliente(Usuario usuario){
    	if(!usuario.getSocket().isClosed()){
    		try {
				usuario.getSocket().close();
				if(!usuario.getPartida().equals("")){
					Partida p = nombresDePartida.get(usuario.getPartida());
			    	DatosPartida datos = infoPartidas.get(p);
					datos.removerUsuario(usuario);
					System.out.println(usuario.getNombre()+" se ha desconectado de la partida "+usuario.getPartida());
				}
				usuarios.remove(usuario);
				cantActualClientes--;
				serverWindow.actualizarListaDeNombres();
			} catch (IOException e) {
				System.out.println("El socket del cliente "+usuario.getNombre() + " ya estaba cerrado.");
			}
    	}
    	
    }
    
    /**
     * Devuelve el objeto DataBase utilizado por el servidor
     */
    public DataBase getDatabase(){
    	return database;
    }
	
	public void agregarUsuario(Usuario u){
		usuarios.add(u);
	}
	
	public ArrayList<Usuario> getListaUsuarios(){
		return usuarios;
	}
	
	public ArrayList<Usuario> getUsuariosEnPartida(String nombrePartida){
		Partida p = nombresDePartida.get(nombrePartida);
    	DatosPartida datos = infoPartidas.get(p);
		return datos.getUsuarios();
	}
	
	public int getCantJugadores(String nombrePartida){
		return nombresDePartida.get(nombrePartida).getCantJugadores();
	}
	
	public int getCantUsuariosEnPartida(String nombrePartida){
		Partida p = nombresDePartida.get(nombrePartida);
    	DatosPartida datos = infoPartidas.get(p);
		return datos.getUsuarios().size();
	}
	
	public HashMap<String, Partida> getNombresDePartida() {
		return nombresDePartida;
	}
	
	/**
	 * Crea una partida en el servidor
	 * @param nombre
	 * @return true/false -Si se pudo crear la partida.
	 */
	public boolean crearPartida(String nombre){
		if(nombresDePartida.containsKey(nombre)){
			return false;
		}
		else {
			Partida p = new Partida(nombre);
			p.agregarMapa(new Mapa("mapaoriginal"));//TODO Ojo con el mapa, con cada uno cambia el punto de "spawn"
			p.getMapa().generarBolitas();
			nombresDePartida.put(nombre, p);
			infoPartidas.put(p, new DatosPartida());
			return true;
		}
	}
	
	public ReentrantLock getSemaforoJugador(String nombrePartida){
		Partida p = nombresDePartida.get(nombrePartida);
    	DatosPartida datos = infoPartidas.get(p);
		return datos.getSemaforoJugador();
	}
	
	public ReentrantLock getSemaforoBolita(String nombrePartida){
		Partida p = nombresDePartida.get(nombrePartida);
    	DatosPartida datos = infoPartidas.get(p);
		return datos.getSemaforoBolitas();
	}
}
