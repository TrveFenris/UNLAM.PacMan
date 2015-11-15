package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import paquetes.Paquete;
import paquetes.PaqueteBolitaEliminada;
import paquetes.PaqueteCoordenadas;
import paquetes.PaqueteID;
import paquetes.PaqueteSesion;
import paquetes.PaqueteSkins;

public class ThreadServerSesion extends Thread {

    private Socket clientSocket;
    private Server servidor;
    private DataBase database;
    private boolean locked;
    private Usuario user;
    
    public ThreadServerSesion(Server servidor, Usuario usuario) {
        clientSocket = usuario.getSocket();
        this.servidor=servidor;
        this.database=servidor.getDatabase();
        //paquete=null;
        usuario.setNombre("");
        user = usuario;
    }

    public synchronized  void run() {
        try {
        	boolean run = true;
        	while(run){
        		while(locked){
            		try {
	            		System.out.println("threadSesion de "+user.getNombre()+" BLOQUEADO");
    					wait();
	            		System.out.println("threadSesion de "+user.getNombre()+" DESBLOQUEADO");
    				} catch (InterruptedException e) {
    					//
    				}
            	}
        		DataInputStream data= new DataInputStream(clientSocket.getInputStream());
            	ObjectInputStream is = new ObjectInputStream(data);
        		//paquete=(PaqueteSesion)is.readObject();
        		Paquete paquete=(Paquete)is.readObject();
                if (!clientSocket.isClosed()) {
    	            DataOutputStream d = new DataOutputStream(clientSocket.getOutputStream());
    	            ObjectOutputStream o = new ObjectOutputStream(d);
    	            //ACCIONES A REALIZAR SEGUN EL TIPO DE PAQUETE RECIBIDO
    	            switch(paquete.getTipo()) {
						case BOLITA_ELIMINADA:
							PaqueteBolitaEliminada paqBolita = (PaqueteBolitaEliminada)paquete;
							//paquete = paqBolita;
							break;
						
						case COORDENADAS:
							PaqueteCoordenadas paqCoord = (PaqueteCoordenadas)paquete;
							//paquete = paqCoord;
							break;
						
						case ID:
							PaqueteID paqId = (PaqueteID)paquete;
							//paquete = paqId;
							break;
						
						case SESION:
							PaqueteSesion paqSesion = (PaqueteSesion)paquete;
							switch(paqSesion.getSolicitud()){
								case LOGIN:
									user.setNombre(paqSesion.getNombre());
									System.out.println(user.getNombre()+" se ha conectado al servidor");
									paqSesion.setResultado(true);
									//paq.setResultado(database.verificarDatos(paq.getNombre(), paq.getPassword()));
									paquete = paqSesion;
									break;
								case LOGOUT:
									paqSesion.setResultado(true);
									run=false;
									paquete = paqSesion;
									break;
								case REGISTRO:
									paqSesion.setResultado(false);
									//paquete.setResultado(database.registrarUsuario(paquete.getNombre(), paquete.getPassword()));
									run=false;
									paquete = paqSesion;
									break;
								case BUSCAR_PARTIDA:
									enviarListaDePartidas(paqSesion);
									paquete = paqSesion;
									break;
								case UNIRSE_PARTIDA:
									System.out.println(paqSesion.getMensaje());
									boolean res=servidor.agregarAPartida(user, paqSesion.getMensaje());
									paqSesion.setResultado(res);
									if(res==true)
										bloquear();
									paquete = paqSesion;
									break;
							}
							break;
						case SKINS:
							PaqueteSkins paqSkins = (PaqueteSkins)paquete;
							//paquete = paqSkins;
							break;
						default:
							System.out.println("Paquete desconocido.");
							break;
    	            }
    	            o.writeObject(paquete);
    	            System.out.println("DATOS ENVIADOS");
                }
        	}
        	 System.out.println(user.getNombre()+" se ha desconectado del servidor");
        	 clientSocket.close();
        	 servidor.eliminarCliente(user);
        }
        catch(EOFException e){
            try {
                clientSocket.close();
                servidor.eliminarCliente(user);
                System.out.println(user.getNombre()+" se ha desconectado del servidor");
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        catch(IOException e) {
        	e.printStackTrace();
            try {
                clientSocket.close();
                servidor.eliminarCliente(user);
                System.out.println(user.getNombre()+" se ha desconectado del servidor");
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
    }
    
    private void enviarListaDePartidas(PaqueteSesion paquete){
    	System.out.println("PREPARANDO LISTA");
            for(ThreadServerPartida partida : servidor.getPartidas()){
            	paquete.agregarPartida(partida.getNombre(),partida.getCantJugadores());
            }
    }

    public synchronized void bloquear(){
    	locked = true;
    }
    
    public synchronized void desbloquear(){
    	locked = false;
    	notify();
    }
}
