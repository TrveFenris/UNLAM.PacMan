package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;

import paquetes.PaqueteCoordenadas;
import punto.Punto;
import server.PaqueteSesion;

public class Cliente {

    private Socket cliente;
    private String nombre;
    private String password;
    private int puerto;

    public int getPuerto() {
        return puerto;
    }

    public Cliente(String direccion, int port) throws UnknownHostException, IOException{
    	puerto = port;
    	cliente = new Socket(direccion, port);
        nombre = password=null;
    }

    public Socket getSocket() {
        return cliente;
    }

    /**
     * Establece los datos del cliente
     * @param nombre
     * @param password
     */
    public void setDatos(String nombre, String password) {
        this.nombre = nombre;
        this.password=password;
    }

    public void enviarMensaje() {
    	/* Seccion comentada porque readLine se comporta de modo bloqueante
    	 * y no deja cerrar el thread que lanza la MainWindow, utilizado
    	 * para ejecutar este metodo*/
        /*
    	try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
          	//Se lee desde el host del usuario y dirige el flujo o informacion al server
            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
            String data;
            while(!cliente.isClosed()) {}
            
            while ((data=br.readLine())!= null) {
                if (data.contains(".exit")) {
                    cerrarCliente();
                }
                else if (!data.equals("")) {
                	dos.writeUTF("["+horaDelMensaje()+"] " + nombre + ": " + data);
                }
            }
            
            cerrarCliente();
            System.out.println("Sali del while");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
        	cerrarCliente();
        }
        */
        	 while(!cliente.isClosed()) {} //Ciclo mientras el socket este abierto
        	 cerrarCliente();
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
    public boolean iniciarSesion(){
    	boolean respuesta=false;
    	try {
    		DataOutputStream d = new DataOutputStream(cliente.getOutputStream());
            ObjectOutputStream o = new ObjectOutputStream(d);
        	PaqueteSesion paquete = new PaqueteSesion(nombre, password);
        	paquete.setIniciarSesion();
        	o.writeObject(paquete);
        	//
            DataInputStream data= new DataInputStream(cliente.getInputStream());
            ObjectInputStream is = new ObjectInputStream(data);
            paquete=(PaqueteSesion)is.readObject();
            if(paquete.getResultado()){
            	respuesta=true;
	        }
            else
            	respuesta=false;
        }
        catch(EOFException e){
        	System.out.println("Error en la comunicación con el servidor (iniciarSesion)");
            cerrarCliente();
        }
        catch(IOException e) {
        	System.out.println("Error: IOException (iniciarSesion)");
        	cerrarCliente();
        } catch (ClassNotFoundException e1) {
			cerrarCliente();
		}
    	return respuesta;
    }
    
    /**
     * Registra el usuario en el servidor
     * @return si el registro fue exitoso o no.
     */
    public boolean registrarUsuario(){
    	boolean respuesta=false;
    	try {
    		DataOutputStream d = new DataOutputStream(cliente.getOutputStream());
            ObjectOutputStream o = new ObjectOutputStream(d);
        	PaqueteSesion paquete = new PaqueteSesion(nombre, password);
        	paquete.setRegistrarUsuario();
        	o.writeObject(paquete);
        	//
            DataInputStream data= new DataInputStream(cliente.getInputStream());
            ObjectInputStream is = new ObjectInputStream(data);
            paquete=(PaqueteSesion)is.readObject();
            if(paquete.getResultado()){
            	respuesta=true;
	        }
            else
            	respuesta=false;
        }
        catch(EOFException e){
        	System.out.println("Error en la comunicación con el servidor (iniciarSesion)");
            cerrarCliente();
        }
        catch(IOException e) {
        	System.out.println("Error: IOException (iniciarSesion)");
        	cerrarCliente();
        } catch (ClassNotFoundException e1) {
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
    		DataOutputStream d = new DataOutputStream(cliente.getOutputStream());
            ObjectOutputStream o = new ObjectOutputStream(d);
        	PaqueteSesion paquete = new PaqueteSesion(nombre, password);
        	paquete.setCerrarSesion();
        	o.writeObject(paquete);
        	//
            DataInputStream data= new DataInputStream(cliente.getInputStream());
            ObjectInputStream is = new ObjectInputStream(data);
            paquete=(PaqueteSesion)is.readObject();
            if(paquete.getResultado()){
            	respuesta=true;
	        }
            else
            	respuesta=false;
            cerrarCliente();
        }
        catch(EOFException e){
        	System.out.println("Error en la comunicación con el servidor (iniciarSesion)");
        	respuesta=false;
            cerrarCliente();
        }
        catch(IOException e) {
        	System.out.println("Error: IOException (iniciarSesion)");
        	respuesta=false;
        	cerrarCliente();
        } catch (ClassNotFoundException e1) {
        	respuesta=false;
			cerrarCliente();
		}
    	return respuesta;
    }
    
    /**
     * Solicita la lista de partidas disponibles al servidor.
     * @return PaqueteListaPartidas -Paquete con informacion de las partidas disponibles. Si hubo un error devuelve null.
     */
    public ArrayList<String> buscarPartidas(){;
    	try {
    		DataOutputStream d = new DataOutputStream(cliente.getOutputStream());
            ObjectOutputStream o = new ObjectOutputStream(d);
        	PaqueteSesion paquete = new PaqueteSesion(nombre, password);
        	paquete.setBuscarPartida();
        	o.writeObject(paquete);
            DataInputStream data= new DataInputStream(cliente.getInputStream());
            ObjectInputStream is = new ObjectInputStream(data);
            paquete=(PaqueteSesion)is.readObject();
            return paquete.getInfoPartidas();
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
    		DataOutputStream d = new DataOutputStream(cliente.getOutputStream());
            ObjectOutputStream o = new ObjectOutputStream(d);
        	PaqueteSesion paquete = new PaqueteSesion(nombre, password);
        	paquete.setUnirseAPartida();
        	paquete.setMensaje(nombre);
        	o.writeObject(paquete);
            DataInputStream data= new DataInputStream(cliente.getInputStream());
            ObjectInputStream is = new ObjectInputStream(data);
            paquete=(PaqueteSesion)is.readObject();
            return paquete.getResultado();
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
    public Punto actualizarPosiciones(Punto p){
    	try {
    		DataOutputStream d = new DataOutputStream(cliente.getOutputStream());
            ObjectOutputStream o = new ObjectOutputStream(d);
        	PaqueteCoordenadas paquete = new PaqueteCoordenadas(p, 0);
        	o.writeObject(paquete);
            DataInputStream data= new DataInputStream(cliente.getInputStream());
            ObjectInputStream is = new ObjectInputStream(data);
            paquete=(PaqueteCoordenadas)is.readObject();
            return paquete.getCoordenadas();
        }
        catch(EOFException e){
        	System.out.println("Error en la comunicación con el servidor (actualizarPosiciones)");
        	return null;
        }
        catch(IOException e) {
        	System.out.println("Error: IOException (actualizarPosiciones)");
        	return null;
        } catch (ClassNotFoundException e1) {
        	return null;
		}
    }
}
