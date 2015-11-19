package server;

import game.Configuracion;
import game.Mapa;
import game.Partida;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

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
    private HashMap<Partida, ArrayList<Usuario>> partidas;
    private HashMap<String, Partida> nombresDePartida;
    /**
     * Crea un nuevo servidor en un puerto determinado, con un maximo de clientes a manejar,
     * y la ventana principal, donde escribirá su lista de nombres.
     * @param port
     * @param max_conexiones
     */
    public Server(int port, int max_conexiones, ServerWindow serverWindow) throws IOException{
        try {
            nombreHost = InetAddress.getLocalHost().getHostName().toString();
            IPHost = InetAddress.getLocalHost().getHostAddress().toString();
        }
        catch (UnknownHostException e1) {
            e1.printStackTrace();
        }
        this.serverWindow=serverWindow;
        puerto = port;
        max_clientes = max_conexiones;
        cantActualClientes = 0;
        sockets = new ArrayList<Socket>();
        database = new DataBase();
        servidor = new ServerSocket(puerto);
        usuarios = new ArrayList<Usuario>();
        this.partidas=new HashMap<Partida, ArrayList<Usuario>>();
        this.nombresDePartida=new HashMap<String, Partida>(); 
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
     * Devuelve la lista de partidas que se están ejecutando.
     */
    public ArrayList<String> getPartidas(){
    	ArrayList<String> nombres = new ArrayList<String>();
    	for(Partida p : nombresDePartida.values()){
    		nombres.add(p.getNombre());
    	}
    	return nombres;
    }
    
    /**
     * Agrega el jugador a la partida seleccionada.
     * @return true/false -Si pudo o no, agregar al jugador.
     */
    public boolean agregarAPartida(Usuario usuario, String partida){
    	for(String nombrePartida : nombresDePartida.keySet()){
    		if(nombrePartida.equals(partida)){
    			Partida p = nombresDePartida.get(nombrePartida);
    			if(p.getCantJugadores()>Configuracion.MAX_JUGADORES_PARTIDA.getValor()){
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
    			partidas.get(p).add(usuario);
    			usuario.setPartida(partida);
    			System.out.println(usuario.getNombre()+" agregado a la partida " + nombrePartida);
    			return true;
    		}
    	}
    	return false;
    }
    public void eliminarDePartida(Usuario usuario, String partida) {
    	//nombresDePartida.get(partida).
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
            e.printStackTrace();
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
            e.printStackTrace();
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
					partidas.get(nombresDePartida.get(usuario.getPartida())).remove(usuario);
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
		return partidas.get(nombresDePartida.get(nombrePartida));
	}
	
	public int getCantJugadores(String nombrePartida){
		return nombresDePartida.get(nombrePartida).getCantJugadores();
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
			p.agregarMapa(new Mapa("mapa1"));
			partidas.put(p,new ArrayList<Usuario>());
			nombresDePartida.put(nombre, p);
			return true;
		}
	}
}
