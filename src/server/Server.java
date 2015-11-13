package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

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
    private ArrayList<ThreadServerPartida> partidas;

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
        partidas = new ArrayList<ThreadServerPartida>();
        
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
     * Agrega una partida a la lista de partidas del servidor.
     * @param partida
     */
    public void agregarPartida(ThreadServerPartida partida){
    	partidas.add(partida);
    }
    
    /**
     * Elimina una partida de la lista de partidas del servidor.
     * @param partida
     */
    public void eliminarPartida(ThreadServerPartida partida){
    	partidas.remove(partida);
    }
    
    /**
     * Devuelve la lista de Threads de partidas que se están ejecutando.
     */
    public ArrayList<ThreadServerPartida> getPartidas(){
    	return partidas;
    }
    
    /**
     * Agrega el jugador a la partida seleccionada.
     * @return true/false -Si pudo o no, agregar al jugador.
     */
    public boolean agregarAPartida(Socket jugador, String partida){
    	for(ThreadServerPartida p : partidas){
    		if(p.getNombre().equals(partida)){
    			return p.agregarCliente(jugador);
    		}
    	}
    	return false;
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
            if (cantActualClientes > max_clientes) {
                DataOutputStream d = new DataOutputStream(cliente.getOutputStream());
                d.writeUTF("Servidor Lleno");
                cliente.close();
                cantActualClientes--;
                return null;
            }
            else{
            	sockets.add(cliente);
               // System.out.println("La Conexion numero " + cantActualClientes 
               // 					+ " fue aceptada correctamente.");
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
    public void eliminarCliente(){
    	cantActualClientes--;
    }
    
    /**
     * Devuelve el objeto DataBase utilizado por el servidor
     */
    public DataBase getDatabase(){
    	return database;
    }
    
    public void agregarNombre(String nombre){
		serverWindow.agregarNombre(nombre);
	}
	
	public void removerNombre(String nombre){
		serverWindow.removerNombre(nombre);
	}
}
