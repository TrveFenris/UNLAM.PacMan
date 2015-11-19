package server;

import game.Partida;
import gameobject.Pacman;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import paquetes.Paquete;
import paquetes.PaqueteBolitaEliminada;
import paquetes.PaqueteBuscarPartida;
import paquetes.PaqueteCoordenadas;
import paquetes.PaqueteID;
import paquetes.PaqueteLanzarPartida;
import paquetes.PaqueteLogin;
import paquetes.PaqueteLogout;
import paquetes.PaquetePartida;
import paquetes.PaqueteRegistro;
import paquetes.PaqueteSkins;
import paquetes.PaqueteUnirsePartida;
import paquetes.PaquetejugadorListo;
import punto.Punto;

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
        	//ObjectInputStream is = new ObjectInputStream(new DataInputStream(user.getSocket().getInputStream()));
        	ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(user.getSocket().getInputStream()));
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
		            				os.flush();
		            			}
							}
							break;
							
						case BUSCAR_PARTIDA:
							o.writeObject(enviarListaDePartidas());
							o.flush();
							break;
							
						case COORDENADAS:
							PaqueteCoordenadas paqCoord = (PaqueteCoordenadas)paquete;
							for(Usuario u : servidor.getUsuariosEnPartida(user.getPartida())){
		            			if(u.getSocket()!=user.getSocket() && !u.getSocket().isClosed()){
		            				ObjectOutputStream os = u.getOutputStream();
		            				os.writeObject(paqCoord);
		            				os.flush();
		            			}
		            		}
							break;
						
						case ID: break; //el server no deberia recibir este paquete
						
						case JUGADOR_ELIMINADO:
							break;
						case JUGADOR_LISTO:
							PaquetejugadorListo paqListo = (PaquetejugadorListo)paquete;
							user.setReady(paqListo.isReady());
							break;
						
						case LANZAR_PARTIDA:
							PaqueteLanzarPartida paqLaunch = (PaqueteLanzarPartida) paquete;
							if(servidor.getUsuariosEnPartida(partida)!=null && servidor.getUsuariosEnPartida(partida).size()>=2) {
								int usuariosListos = 0;
								for(Usuario u : servidor.getUsuariosEnPartida(partida)) {
									if(u.isReady()) {
										usuariosListos++;
									}
								}
								if(usuariosListos>=2) { //Cambiar por mayor a 2
									//servidor.getNombresDePartida().get(partida);
									o.writeObject(new PaquetePartida(servidor.getNombresDePartida().get(partida)));
									o.flush();
								}
								else {
									paqLaunch.setReady(false);
									o.writeObject(paqLaunch);
									o.flush();
								}
							}
							else {
								paqLaunch.setReady(false);
								o.writeObject(paqLaunch);
								o.flush();
							}
							break;
						
						case LOGIN:
							PaqueteLogin paqLogin = (PaqueteLogin) paquete;
							user.setNombre(paqLogin.getNombreUsuario());
							System.out.println(user.getNombre()+" se ha conectado al servidor");
							paqLogin.setResultado(true);
							//paqLogin.setResultado(database.verificarDatos(paqLogin.getNombreUsuario(), paqLogin.getPassword()));
							o.writeObject(paqLogin);
							o.flush();
							break;
							
						case LOGOUT:
							PaqueteLogout paqLogout = (PaqueteLogout) paquete;
							paqLogout.setResultado(true);
							running=false;
							o.writeObject(paqLogout);
							o.flush();
							break;	
						
						case PARTIDA: break; //el server no deberia recibir este paquete
						
						case REGISTRO:
							PaqueteRegistro paqReg = (PaqueteRegistro) paquete;
							paqReg.setResultado(false);
							//paqReg.setResultado(database.registrarUsuario(paqReg.getNombreUsuario(), paqReg.getPassword()));
							running=false;
							o.writeObject(paqReg);
							o.flush();
							break;
						
						case SCORE:
							break;
							
						case SERVIDOR_LLENO: break; //No deberia recibirlo
						/*
						case SKINS:
							PaqueteSkins paqSkins = (PaqueteSkins)paquete;
							//int idJugador = user.getId();
							//String partidaJugador = user.getPartida();
							//for(Jugadores j : servidor.get)
							break;
						*/
						case UNIRSE_PARTIDA:
							PaqueteUnirsePartida paqUnir = (PaqueteUnirsePartida)paquete;
							boolean res = servidor.agregarAPartida(user, paqUnir.getNombrePartida());
							paqUnir.setResultado(res);
							o.writeObject(paqUnir);
							o.flush();
							
							//Aca se deberia sortear el ID para que al agregar el user a la partida queden dentro todos sus datos
							
							
							if(res) {
								PaqueteSkins paqSkinsUser = (PaqueteSkins) is.readObject();
								user.setSkinPacman(paqSkinsUser.getSkinPacman());
								user.setSkinFantasma(paqSkinsUser.getSkinFantasma());
								Partida part = servidor.getNombresDePartida().get(paqUnir.getNombrePartida());
								//Habria que controlar esto con un semaforo? para que varios threads no puedan modificar la partida a la vez...
								part.sortearIDs();
								int id = part.getIdsDisponibles().get(0);
								o.writeObject(new PaqueteID(id));
								o.flush();
								part.tomarID(id);
								//servidor.getNombresDePartida().get(paqUnir.getNombrePartida()).tomarID(id);
								servidor.getNombresDePartida().get(paqUnir.getNombrePartida()).agregarJugador(new Pacman(new Punto(15,35), user.getNombre(), user.getSkinPacman(), id));
							}
							break;

						default:
							System.out.println("Paquete desconocido.");
							break;
    	            }
                }
        	}
        	servidor.eliminarCliente(user);
        	System.out.println(user.getNombre()+" se ha desconectado del servidor");
        }
        catch(EOFException e){
                servidor.eliminarCliente(user);
                System.out.println(user.getNombre()+" se ha desconectado del servidor");
        }
        catch(IOException e) {
        	e.printStackTrace();
                servidor.eliminarCliente(user);
                System.out.println(user.getNombre()+" se ha desconectado del servidor");
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
