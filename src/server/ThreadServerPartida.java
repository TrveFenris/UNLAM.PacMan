package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import game.Configuracion;

public class ThreadServerPartida {
	private ArrayList<Socket>jugadores;
    private Server servidor;
    private String nombrePartida;
    private boolean run;
    
    /**
     * Crea un thread que actua como servidor para la comunicacion realizada durante una partida.
     * @param serv -Servidor que provee la partida.
     * @param nombre -Nombre publico de la partida.
     */
    public ThreadServerPartida(Server serv, String nombre) {
        servidor = serv;
        nombrePartida = nombre;
        run = true;
        jugadores = new ArrayList<Socket>();
    }
    
    /**
     * Agrega un cliente a la partida.
     * @param jugador
     * @return true/false -Si el jugador puede unirse a la partida.
     */
    public boolean agregarCliente(Socket jugador){
    	if(jugadores.size()>Configuracion.MAX_JUGADORES.getValor()){
    		return false;
    	}
    	jugadores.add(jugador);
    	return true;
    }
    
    /**
     * Devuelve un String que representa el nombre público de la partida.
     */
    public String getNombre(){
    	return nombrePartida;
    }
    
    /**
     * Devuelve la lista de jugadores que se encuentran en la partida.
     */
    public ArrayList<Socket> getJugadores(){
    	return jugadores;
    }
    
    /**
     * Devuelve la cantidad de jugadores en la partida.
     */
    public int getCantJugadores(){
    	return jugadores.size();
    }
    
    /**
     * Detiene el thread.
     */
    public void detener(){
    	run=false;
    	System.out.println("Partida "+nombrePartida+" detenida.");
    }
    
    /**
     * Inicia el thread.
     */
    
    public void run() {
//        try {
//        	boolean run=true;
//        	while(run){
//        		DataInputStream data= new DataInputStream(clientSocket.getInputStream());
//            	ObjectInputStream is = new ObjectInputStream(data);
//        		paquete=(PaqueteSesion)is.readObject();
//        		nombre=paquete.getNombre();
//        		System.out.println(paquete.getNombre()+" se ha conectado al servidor");
//                if (!clientSocket.isClosed()) {
//    	            DataOutputStream d = new DataOutputStream(clientSocket.getOutputStream());
//    	            ObjectOutputStream o = new ObjectOutputStream(d);
//    	            //ACCIONES A REALIZAR SEGUN EL TIPO DE PAQUETE RECIBIDO
//    	            switch(paquete.getTipoPaquete()){
//    	            	case LOGIN:
//    	            		paquete.setResultado(true);
//    	            		servidor.agregarNombre(nombre);
//    	            		//paquete.setResultado(database.verificarDatos(paquete.getNombre(), paquete.getPassword()));
//    	            		break;
//    	            	case LOGOUT:
//    	            		paquete.setResultado(true);
//    	            		run=false;
//    	            		break;
//    	            	case REGISTRO:
//    	            		paquete.setResultado(false);
//    	            		//paquete.setResultado(database.registrarUsuario(paquete.getNombre(), paquete.getPassword()));
//    	            		run=false;
//    	            		break;
//    	            }
//    	            o.writeObject(paquete);
//                }
//        	}
//        	 System.out.println(nombre+" se ha desconectado del servidor");
//        	 clientSocket.close();
//        	 servidor.eliminarCliente();
//        	 servidor.removerNombre(nombre);
//             servidor.eliminarCliente();
//        }
//        catch(EOFException e){
//            try {
//                clientSocket.close();
//                servidor.removerNombre(nombre);
//                servidor.eliminarCliente();
//                System.out.println(nombre+" se ha desconectado del servidor");
//            }
//            catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }
//        catch(IOException e) {
//        	e.printStackTrace();
//            try {
//                clientSocket.close();
//                servidor.removerNombre(nombre);
//                servidor.eliminarCliente();
//                System.out.println(nombre+" se ha desconectado del servidor");
//            }
//            catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        } catch (ClassNotFoundException e1) {
//			e1.printStackTrace();
//		}
    }
}
