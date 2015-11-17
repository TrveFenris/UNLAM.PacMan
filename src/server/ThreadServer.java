package server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import paquetes.Paquete;
import paquetes.PaqueteBolitaEliminada;
import paquetes.PaqueteBuscarPartida;
import paquetes.PaqueteCoordenadas;
import paquetes.PaqueteLogin;
import paquetes.PaqueteLogout;
import paquetes.PaqueteRegistro;
import paquetes.PaqueteSkins;
import paquetes.PaqueteUnirsePartida;

public class ThreadServer extends Thread {

    private Server servidor;
    private DataBase database;
    private Usuario user;
    private String partida; //Utilizado para cachear la partida en la que se encuentra el usuario
    private boolean running;
    
    public ThreadServer(Server servidor, Usuario usuario) {
        this.servidor=servidor;
        this.database=servidor.getDatabase(); 
        usuario.setNombre("");
        user = usuario;
        running = true;
    }

    public synchronized  void run() {
        try {
        	ObjectInputStream is = new ObjectInputStream(new DataInputStream(user.getSocket().getInputStream()));
        	while(running){
        		Paquete paquete=(Paquete)is.readObject();
                if (!user.getSocket().isClosed()) {
                	ObjectOutputStream o = user.getOutputStream();
    	            //ACCIONES A REALIZAR SEGUN EL TIPO DE PAQUETE RECIBIDO
    	            switch(paquete.getTipo()) {
						case BOLITA_ELIMINADA:
							PaqueteBolitaEliminada paqBolita = (PaqueteBolitaEliminada)paquete;
							for(Usuario u : servidor.getUsuariosEnPartida(user.getPartida())){
		            			if(u.getSocket()!=user.getSocket() && !u.getSocket().isClosed()){
		            				ObjectOutputStream os = u.getOutputStream();
		            				os.writeObject(paqBolita);
		            			}
							}
							break;
							
						case BUSCAR_PARTIDA:
							o.writeObject(enviarListaDePartidas());
							break;
							
						case COORDENADAS:
							PaqueteCoordenadas paqCoord = (PaqueteCoordenadas)paquete;
							for(Usuario u : servidor.getUsuariosEnPartida(user.getPartida())){
		            			if(u.getSocket()!=user.getSocket() && !u.getSocket().isClosed()){
		            				ObjectOutputStream os = u.getOutputStream();
		            				os.writeObject(paqCoord);
		            			}
		            		}
							break;
						
						case ID: break; //el server no deberia recibir este paquete
						
						case JUGADOR_ELIMINADO:
							break;
						
						case LOGIN:
							PaqueteLogin paqLogin = (PaqueteLogin) paquete;
							user.setNombre(paqLogin.getNombreUsuario());
							System.out.println(user.getNombre()+" se ha conectado al servidor");
							paqLogin.setResultado(true);
							//paqLogin.setResultado(database.verificarDatos(paqLogin.getNombreUsuario(), paqLogin.getPassword()));
							o.writeObject(paqLogin);
							break;
							
						case LOGOUT:
							PaqueteLogout paqLogout = (PaqueteLogout) paquete;
							paqLogout.setResultado(true);
							running=false;
							o.writeObject(paqLogout);
							break;	
						
						case PARTIDA: break; //el server no deberia recibir este paquete
						
						case REGISTRO:
							PaqueteRegistro paqReg = (PaqueteRegistro) paquete;
							paqReg.setResultado(false);
							//paqReg.setResultado(database.registrarUsuario(paqReg.getNombreUsuario(), paqReg.getPassword()));
							running=false;
							o.writeObject(paqReg);
							break;
						
						case SCORE:
							break;
							
						case SERVIDOR_LLENO: break; //No deberia recibirlo
						
						case SKINS:
							PaqueteSkins paqSkins = (PaqueteSkins)paquete;
							//int idJugador = user.getId();
							//String partidaJugador = user.getPartida();
							//for(Jugadores j : servidor.get)
							break;
							
						case UNIRSE_PARTIDA:
							PaqueteUnirsePartida paqUnir = (PaqueteUnirsePartida)paquete;
							boolean res = servidor.agregarAPartida(user, paqUnir.getNombrePartida());
							paqUnir.setResultado(res);
							o.writeObject(paqUnir);
							break;

						default:
							System.out.println("Paquete desconocido.");
							break;
    	            }
                }
        	}
        	user.getSocket().close();
        	servidor.eliminarCliente(user);
        	System.out.println(user.getNombre()+" se ha desconectado del servidor");
        }
        catch(EOFException e){
            try {
            	user.getSocket().close();
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
            	user.getSocket().close();
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
    
    private PaqueteBuscarPartida enviarListaDePartidas(){
    	PaqueteBuscarPartida paquete = new PaqueteBuscarPartida();
    	System.out.println("PREPARANDO LISTA");
    	for(String partida : servidor.getPartidas()){
    		System.out.println("S: "+partida);
    		paquete.agregarPartida(partida, servidor.getCantJugadores(partida));
    	}
        return paquete;
    }
    
    public void pararThread(){
    	running = false;
    }
}
