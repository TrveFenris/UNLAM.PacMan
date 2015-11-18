package cliente;

import gameobject.Jugador;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;

import paquetes.Paquete;
import paquetes.PaqueteBuscarPartida;
import paquetes.PaqueteCoordenadas;
import paquetes.PaqueteLogin;
import paquetes.PaqueteLogout;
import paquetes.PaqueteRegistro;
import paquetes.PaqueteUnirsePartida;

public class Cliente {

    private Socket cliente;
    private int puerto;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public int getPuerto() {
        return puerto;
    }

    public Cliente(String direccion, int port) throws UnknownHostException, IOException{
    	puerto = port;
    	cliente = new Socket(direccion, port);
    	//outputStream = new ObjectOutputStream(new DataOutputStream(cliente.getOutputStream()));
    	outputStream = new ObjectOutputStream(new BufferedOutputStream(cliente.getOutputStream()));
    }

    public Socket getSocket() {
        return cliente;
    }

    public void cerrarCliente() {
        try {
        	if(cliente != null && !cliente.isClosed()) {
        		System.out.println("Cerrando cliente...");
        		cliente.close();
        	}
            //System.exit(1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String horaDelMensaje() {
        Calendar cal = Calendar.getInstance();
        return +cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE)
                + ":" + cal.get(Calendar.SECOND);
    }
    
    /**
     * Inicia sesion en el servidor
     * @return si el inicio de sesion fue exitoso o no.
     */
    public boolean iniciarSesion(String user, String pass){
    	boolean respuesta=false;
    	try {
    		//Datos a enviar
            PaqueteLogin paquete = new PaqueteLogin(user, pass);
        	outputStream.writeObject(paquete);
        	outputStream.flush();
        	//Datos a recibir
            //inputStream = new ObjectInputStream(new DataInputStream(cliente.getInputStream()));
            inputStream = new ObjectInputStream(new BufferedInputStream(cliente.getInputStream()));
            try {
            	paquete=(PaqueteLogin)inputStream.readObject();
            	if(paquete.getResultado()) {
            		respuesta=true;
            	}
            }
            catch(ClassCastException e) {
            	System.out.println("Error: No se recibio un paquete de Logout.");
            }
        }
        catch(EOFException e){
        	System.out.println("Error en la comunicación con el servidor (iniciarSesion)");
            cerrarCliente();
        }
        catch(IOException e) {
        	System.out.println("Error: IOException (iniciarSesion)");
        	cerrarCliente();
        }
    	catch (ClassNotFoundException e1) {
			cerrarCliente();
		}
    	return respuesta;
    }
    
    /**
     * Registra el usuario en el servidor
     * @return si el registro fue exitoso o no.
     */
    public boolean registrarUsuario(String user, String pass){
    	boolean respuesta=false;
    	try {
    		//Datos a enviar
        	PaqueteRegistro paquete = new PaqueteRegistro(user, pass);
            outputStream.writeObject(paquete);
            outputStream.flush();
        	//Datos a recibir
            //inputStream = new ObjectInputStream(new DataInputStream(cliente.getInputStream()));
            inputStream = new ObjectInputStream(new BufferedInputStream(cliente.getInputStream()));
            try {
            	paquete=(PaqueteRegistro)inputStream.readObject();
            	if(paquete.getResultado()) {
            		respuesta=true;
            	}
            }
            catch(ClassCastException e) {
            	System.out.println("Error: No se recibio un paquete de Logout.");
            }
        }
        catch(EOFException e){
        	System.out.println("Error en la comunicación con el servidor (iniciarSesion)");
            cerrarCliente();
        }
        catch(IOException e) {
        	System.out.println("Error: IOException (iniciarSesion)");
        	cerrarCliente();
        }
    	catch (ClassNotFoundException e1) {
			cerrarCliente();
		}
    	return respuesta;
    }
    
    /**
     * Cierra una sesion de usuario, desconectandose del servidor
     * @return true/false, si el cierre de sesion fue exitoso o no.
     */
    public boolean cerrarSesion(){
    	boolean respuesta=false;
    	try {
    		//Datos a enviar
        	PaqueteLogout paquete = new PaqueteLogout();
            outputStream.writeObject(paquete);
            outputStream.flush();
        	//Datos a recibir
            try {
            	paquete=(PaqueteLogout)inputStream.readObject();
            	
            }
            catch(ClassCastException e) {
            	System.out.println("Error: No se recibio un paquete de Logout.");
            }
            
            if(paquete.getResultado()){
            	respuesta=true;
            	cerrarCliente();
	        }
        }
        catch(EOFException e){
        	System.out.println("Error en la comunicación con el servidor (iniciarSesion)");
            cerrarCliente();
        }
        catch(IOException e) {
        	System.out.println("Error: IOException (iniciarSesion)");
        	cerrarCliente();
        }
    	catch (ClassNotFoundException e1) {
			cerrarCliente();
		}
    	return respuesta;
    }
    
    /**
     * Solicita la lista de partidas disponibles al servidor.
     * @return PaqueteListaPartidas -Paquete con informacion de las partidas disponibles. Si hubo un error devuelve null.
     */
    public ArrayList<AbstractMap.SimpleImmutableEntry<String, Integer>> buscarPartidas(){;
    	try {
    		//Datos a enviar
        	PaqueteBuscarPartida paquete = new PaqueteBuscarPartida();
            outputStream.writeObject(paquete);
            outputStream.flush();
            //Datos a recibir
            try {
            	PaqueteBuscarPartida paqueteBuscar=(PaqueteBuscarPartida)inputStream.readObject();
            	return paqueteBuscar.getPartidas();
            }
            catch(ClassCastException ex) {
            	System.out.println("ERROR: El cliente recibio un paquete erroneo (Esperado un PaqueteBuscarPartida).");
            	return null;
            }
        }
        catch(EOFException e){
        	System.out.println("Error en la comunicación con el servidor (buscarPartidas)");
        	return null;
        }
        catch(IOException e) {
        	System.out.println("Error: IOException (buscarPartidas)");
        	return null;
        } catch (ClassNotFoundException e1) {
        	return null;
		}
    }
    
    /**
     * Solicita unirse a la partida seleccionada.
     * @param nombre -Nombre de la partida a la que desea unirse.
     * @return true/false, si pudo unirse a la partida o no.
     */
    public boolean unirseAPartida(String nombre){
    	boolean resultado = false;
    	try {
    		//Datos a enviar
        	PaqueteUnirsePartida paquete = new PaqueteUnirsePartida(nombre);
        	outputStream.writeObject(paquete);
        	outputStream.flush();
        	//Datos a recibir
            try {
            	paquete=(PaqueteUnirsePartida)inputStream.readObject();
            	return paquete.getResultado();
            }
            catch(ClassCastException e) {
            	return false;
            }
        }
        catch(EOFException e){
        	System.out.println("Error en la comunicación con el servidor (unirsePartida)");
        	return resultado;
        }
        catch(IOException e) {
        	System.out.println("Error: IOException (unirsePartida)");
        	return resultado;
        } catch (ClassNotFoundException e1) {
        	return resultado;
		}
    }
    
    /**
     * Envia la posicion actual al servidor, y recibe la pocision de los otros jugadores.
     */
    public void enviarPosicion(Jugador j){
    	try {
        	PaqueteCoordenadas paquete = new PaqueteCoordenadas(j.getLocation(),/*j.getID()*/2, j.getSentido());
        	outputStream.writeObject(paquete);
        	outputStream.flush();
        }
        catch(EOFException e){
        	System.out.println("Error en la comunicación con el servidor (enviarPosicion)");
        }
        catch(IOException e) {
        	System.out.println("Error: IOException (enviarPosicion)");
        }
    }
    
    public PaqueteCoordenadas recibirPosicion(){
    	try {
    		cliente.setSoTimeout(1000);
            try {
            	PaqueteCoordenadas paquete=(PaqueteCoordenadas)inputStream.readObject();
            	return paquete;
            }
            catch(ClassCastException e) {
        		System.out.println("Error: No se recibio un paquete de coordenadas");
        		return null;
        	}            
        }
        catch(EOFException e){
        	System.out.println("Error en la comunicación con el servidor (recibirPosicion)");
        	return null;
        }
    	catch(SocketTimeoutException e){
    		//System.out.println("TimeOut");
        	return null;
        }
        catch(IOException e) {
        	System.out.println("Error: IOException (recibirPosicion)");
        	return null;
        } catch (ClassNotFoundException e) {
        	System.out.println("Error: ClassNotFoundException (recibirPosicion)");
        	return null;
		}
    }
    
    public void enviarDatosPartida(Paquete paquete) {
    	try {
        	outputStream.writeObject(paquete);
        	outputStream.flush();
        }
        catch(EOFException e){
        	System.out.println("Error en la comunicación con el servidor (enviarDatosPartida)");
        }
        catch(IOException e) {
        	System.out.println("Error: IOException (enviarDatosPartida)");
        }
    }
    
    public Paquete recibirDatosPartida() {
    	try {
			return (Paquete) inputStream.readObject();
		} catch (ClassNotFoundException e) {
			System.out.println("Error de serializacion (recibirDatosPartida)");
			return null;
		} catch (IOException e) {
			System.out.println("Error: IOException (recibirDatosPartida)");
			return null;
		}
    }
}
