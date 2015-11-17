package cliente;

import game.ConfiguracionSprites;
import game.Mapa;
import game.Partida;
import gameobject.Bolita;
import gameobject.Direcciones;
import gameobject.Jugador;
import gameobject.Pacman;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import paquetes.Paquete;
import paquetes.PaqueteBolitaEliminada;
import paquetes.PaqueteCoordenadas;
import paquetes.PaqueteJugadorEliminado;
import paquetes.PaqueteScore;
import punto.Punto;
import rectas.Rectas;

public class GameWindow extends JFrame {
	
	/* Variables Miembro */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblName;
	private UserWindow userWindow;
	private boolean gameRunning;
	private GameThread gameLoopThread;
	private int[]controles;
	//ID que representa al jugador local
	private int IDJugadorLocal = 1;
	//CONSTANTES PARA EL MANEJO COMPRENSIBLE DEL VECTOR CONTROLES
	private final int ARRIBA=0;
	private final int ABAJO=1;
	private final int IZQUIERDA=2;
	private final int DERECHA=3;
	//Mapa

	private Partida partida;
	private Jugador jugadorLocal;
	//Variables de accion segun presion de tecla
	private Rectas ultimaDireccion;
	private Direcciones ultimaAccion;
	//Variables auxiliares
	private ListenThread threadEscucha;
	
	/* GameWindow constructor */
	public GameWindow(UserWindow window) {
		setResizable(false);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				handleKeyPress(arg0);
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(userWindow != null) {
					mensajeSalida();
				}
			}
		});
			
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(Color.BLACK);
		//contentPane.setOpaque(true);
		lblName = new JLabel("New label");
		lblName.setForeground(Color.CYAN);
		lblName.setFont(Font.getFont(Font.SANS_SERIF));
		lblName.setBounds(5, 5, 774, 14);
		contentPane.add(lblName);
		//PARTIDA PROVENIENTE DEL SERVER
		//PaquetePartida packet = (PaquetePartida)userWindow.getCliente().recibirDatosPartida();
		//partida = packet.getPartida();
		//CREACION DE PARTIDA LOCAL
		partida = new Partida("Local");
		partida.agregarMapa(new Mapa("mapa1"));
		partida.getMapa().dibujar(contentPane);
		//Creacion de pacman, por ahora se inicializa con la skin por defecto
		partida.agregarJugador(new Pacman(new Punto(15,35), lblName.getText(), ConfiguracionSprites.PACMAN_DEFAULT, 1));
		ultimaAccion=Direcciones.NINGUNA;
		//Creación del 2do jugador (manejado por otra ventana)
		partida.agregarJugador(new Pacman(new Punto(15,35), "botMalvado", ConfiguracionSprites.PACMAN_MALVADO, 2));
		for(Jugador j: partida.getJugadores()) {
			j.dibujar(contentPane);
			if(j.getID() == IDJugadorLocal) {
				jugadorLocal = j;
			}
		}
		userWindow = window;
		gameRunning = true;
		gameLoopThread = new GameThread();
		threadEscucha = new ListenThread();
		threadEscucha.start();
		this.setTitle("PAC-MAN");
	}
	
	private void mensajeSalida(){
		int option = JOptionPane.showConfirmDialog(this,
			    "Â¿Esta seguro que quiere salir?",
			    "Saliendo del juego",
			    JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION){
			gameRunning = false;
			gameLoopThread.timer.cancel();
			threadEscucha.pararThread();
			this.dispose();
			userWindow.setVisible(true);
		}
	}
	
	public void runGameLoop() {
		gameLoopThread.start();
	}
	
	private void handleKeyPress(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(userWindow != null) {
				mensajeSalida();
			}
		}
		else if(key.getKeyCode() == controles[ARRIBA]) {
			ultimaAccion=Direcciones.ARRIBA;
			if(ultimaDireccion==Rectas.HORIZONTAL)
				return;
			jugadorLocal.cambiarSentido(Direcciones.ARRIBA);
		}
		else if(key.getKeyCode() == controles[ABAJO]) {
			ultimaAccion=Direcciones.ABAJO;
			if(ultimaDireccion==Rectas.HORIZONTAL)
				return;
			jugadorLocal.cambiarSentido(Direcciones.ABAJO);
		}
		else if(key.getKeyCode() == controles[IZQUIERDA]) {
			ultimaAccion=Direcciones.IZQUIERDA;
			if(ultimaDireccion==Rectas.VERTICAL)
				return;
			jugadorLocal.cambiarSentido(Direcciones.IZQUIERDA);
		}
		else if(key.getKeyCode() == controles[DERECHA]) {
			ultimaAccion=Direcciones.DERECHA;
			if(ultimaDireccion==Rectas.VERTICAL)
				return;
			jugadorLocal.cambiarSentido(Direcciones.DERECHA);
		}
	}
	/*
	private void update(){
		for(Jugador jug : partida.getJugadores()) {
			jug.actualizarUbicacion(partida.getMapa().getArrayRectas());
			ultimaDireccion=jug.getTipoUbicacion();
			switch(ultimaDireccion){
				case HORIZONTAL:
					jug.setLeftBound(jug.getRectaActual(0).getPuntoInicialX());
					jug.setRightBound(jug.getRectaActual(0).getPuntoFinalX());
					jug.setUpperBound(jug.getRectaActual(0).getPuntoInicialY());
					jug.setLowerBound(jug.getRectaActual(0).getPuntoInicialY());
					break;
				case VERTICAL:
					jug.setUpperBound(jug.getRectaActual(0).getPuntoInicialY());
					jug.setLowerBound(jug.getRectaActual(0).getPuntoFinalY());
					jug.setLeftBound(jug.getRectaActual(0).getPuntoInicialX());
					jug.setRightBound(jug.getRectaActual(0).getPuntoInicialX());
					
					break;
				case AMBAS:
					//System.out.println("Interseccion");
					for(int i=0;i<2;i++){
						if(jug.getRectaActual(i).getTipo()==Rectas.HORIZONTAL){
							jug.setLeftBound(jug.getRectaActual(i).getPuntoInicialX());
							jug.setRightBound(jug.getRectaActual(i).getPuntoFinalX());
						}
						else
							if(jug.getRectaActual(i).getTipo()==Rectas.VERTICAL){
								jug.setUpperBound(jug.getRectaActual(i).getPuntoInicialY());
								jug.setLowerBound(jug.getRectaActual(i).getPuntoFinalY());
							}
					}
					jug.cambiarSentido(ultimaAccion);
					break;
			}
			jug.mover();
			userWindow.getCliente().enviarPosicion(jug.getLocation());
			semaforo.lock();	     
			try {
				for(Jugador j : partida.getJugadores()) {
					//partida.getJugador(1).setLocation(paux.getX(), paux.getY());
					if(j.getID()!=IDJugadorLocal)
						j.setLocation(paux.getX(), paux.getY());
				}
			} 
			finally {
				semaforo.unlock();
			}
			restrictBoundaries(jug);
			calcularColisiones (jug);
		}
	}
	*/
	private void update(){
		jugadorLocal.actualizarUbicacion(partida.getMapa().getArrayRectas());
		ultimaDireccion=jugadorLocal.getTipoUbicacion();
		switch(ultimaDireccion){
			case HORIZONTAL:
				jugadorLocal.setLeftBound(jugadorLocal.getRectaActual(0).getPuntoInicialX());
				jugadorLocal.setRightBound(jugadorLocal.getRectaActual(0).getPuntoFinalX());
				jugadorLocal.setUpperBound(jugadorLocal.getRectaActual(0).getPuntoInicialY());					
				jugadorLocal.setLowerBound(jugadorLocal.getRectaActual(0).getPuntoInicialY());
				break;
			case VERTICAL:
				jugadorLocal.setUpperBound(jugadorLocal.getRectaActual(0).getPuntoInicialY());
				jugadorLocal.setLowerBound(jugadorLocal.getRectaActual(0).getPuntoFinalY());
				jugadorLocal.setLeftBound(jugadorLocal.getRectaActual(0).getPuntoInicialX());
				jugadorLocal.setRightBound(jugadorLocal.getRectaActual(0).getPuntoInicialX());
				break;
			case AMBAS:
				//System.out.println("Interseccion");
				for(int i=0;i<2;i++){
					if(jugadorLocal.getRectaActual(i).getTipo()==Rectas.HORIZONTAL){
						jugadorLocal.setLeftBound(jugadorLocal.getRectaActual(i).getPuntoInicialX());
						jugadorLocal.setRightBound(jugadorLocal.getRectaActual(i).getPuntoFinalX());
					}
					else
						if(jugadorLocal.getRectaActual(i).getTipo()==Rectas.VERTICAL){
							jugadorLocal.setUpperBound(jugadorLocal.getRectaActual(i).getPuntoInicialY());
							jugadorLocal.setLowerBound(jugadorLocal.getRectaActual(i).getPuntoFinalY());
						}
				}
				jugadorLocal.cambiarSentido(ultimaAccion);
				break;
		}
		jugadorLocal.mover();
		userWindow.getCliente().enviarPosicion(jugadorLocal); //Aun no anda porque no recibo un ID generado por el server
		restrictBoundaries(jugadorLocal);
		calcularColisiones (jugadorLocal);
	}
	
	/**
	 * Asegura que el personaje controlado por un jugador se mantenga sobre las rectas del mapa
	 * @param Jugador
	 */
	private void restrictBoundaries(Jugador j) {
		if( j.getCentroCoordenadasX() < j.getLeftBound() ) /* Limite izquierdo */		
			j.setLocation(j.getLeftBound() - (j.getWidth()/2), j.getY());		
		
		if( j.getCentroCoordenadasX() > j.getRightBound() ) /* Limite derecho (anda mal) */		
			j.setLocation(j.getRightBound() - (j.getWidth()/2), j.getY());		
		
		if( j.getCentroCoordenadasY() < j.getUpperBound() ) /* Limite hacia arriba */		
			j.setLocation(j.getX(), j.getUpperBound() - (j.getHeight()/2));		
		
		if( j.getCentroCoordenadasY() > j.getLowerBound() ) /* Limite hacia abajo (anda mal) */		
			j.setLocation(j.getX(), j.getLowerBound() - (j.getHeight()/2));		
 	}
	
	private void calcularColisiones(Jugador j) {
		Iterator<Bolita> it = partida.getMapa().getArrayBolitas().iterator();
		while(it.hasNext()) {
			Bolita b = it.next();
			if(b.isAlive() && j.colisionaCon(b)) {
				userWindow.getCliente().enviarDatosPartida(new PaqueteBolitaEliminada(partida.getMapa().getArrayBolitas().indexOf(b), b));
				b.setAliveState(false);
				b.borrarImagen();
				it.remove();
			}
		}
	}
	
	public void setControles(int[] controles){
		this.controles=new int[4];
		for(int i=0;i<4;i++){
			this.controles[i]=controles[i];
		}
	}

	public void setNameLabel(String s){
		lblName.setText(s);
	}
	
	/**
	 * Thread que maneja el Game Loop 
	 * */
	private class GameThread extends Thread {
		private Timer timer;
		
		public void run() {
			System.out.println("Comienza el juego");
			timer = new Timer();
			timer.schedule( new TimerTask() {
			    public void run() {
			       if(gameRunning){
			    	   update();
			       }
			    }
			 }, 0, 16);
		}
	}
	
	/*
	 * Thread que recibe actualizaciones de los demas jugadores
	 *//*
	private class ListenThread extends Thread {
		
		private boolean running;
		
		public void run() {
			running = true;
			while(running){
				PaqueteCoordenadas p = userWindow.getCliente().recibirPosicion();
				if(p!=null){
					for(Jugador j : partida.getJugadores()){
						if(j.getID()==p.getIDJugador()){
							j.setLocation(p.getCoordenadas().getX(), p.getCoordenadas().getY());
							j.cambiarSentido(p.getDireccion());
						}
					}
				}
			}
		}
		
		public void pararThread(){
			running = false;
		}
	}*/
	/**
	 * Thread que recibe actualizaciones de los demas jugadores
	 */
	private class ListenThread extends Thread {
		
		private boolean running;
		public void run() {
			running = true;
			while(running){
				Paquete p = userWindow.getCliente().recibirDatosPartida();
				if(p!=null){
					switch(p.getTipo()) {
						case BOLITA_ELIMINADA:
							PaqueteBolitaEliminada paqBolElim = (PaqueteBolitaEliminada)p;
							//partida.getMapa().removerBolita(paqBolElim.getBolita()); //Esto se usara cuando el server envie la partida ya construida al cliente
							partida.getMapa().removerBolita(paqBolElim.getIndice());
							break;

						case COORDENADAS:
							PaqueteCoordenadas paqCoord = (PaqueteCoordenadas)p;
							for(Jugador j : partida.getJugadores()){
								if(j.getID()==paqCoord.getIDJugador()){
									j.setLocation(paqCoord.getCoordenadas().getX(), paqCoord.getCoordenadas().getY());
									j.cambiarSentido(paqCoord.getDireccion());
								}
							}
							break;

						case JUGADOR_ELIMINADO:
							PaqueteJugadorEliminado paqJugElim = (PaqueteJugadorEliminado)p;
							break;

						case SCORE:
							PaqueteScore paqScore = (PaqueteScore)p;
							break;

						case ID: break; //No deberia recibirse
						case BUSCAR_PARTIDA: break; //No deberia recibirse	
						case LOGIN: break; //No deberia recibirse
						case LOGOUT: break; //No deberia recibirse
						case PARTIDA: break; //No deberia recibirse
						case REGISTRO: break; //No deberia recibirse
						case SERVIDOR_LLENO: break; //No deberia recibirse
						case SKINS: break; //No deberia recibirse
						case UNIRSE_PARTIDA: break; //No deberia recibirse
						default: System.out.println("GameWindow: Paquete Desconocido."); break;
					}
				}
			}
		}
		public boolean isRunning(){
			return running;
		}
		public void pararThread(){
			running = false;
		}
	}
}
