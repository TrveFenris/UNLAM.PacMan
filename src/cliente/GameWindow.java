package cliente;

import game.Mapa;
import gameobject.Actions;
import gameobject.Bolita;
import gameobject.Jugador;
import gameobject.Pacman;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
	//CONSTANTES PARA EL MANEJO COMPRENSIBLE DEL VECTOR CONTROLES
	private final int ARRIBA=0;
	private final int ABAJO=1;
	private final int IZQUIERDA=2;
	private final int DERECHA=3;
	//Mapa
	private Mapa mapa;
	private ArrayList<Jugador> jugadores;
	private Pacman pacman;
	//Variables de acción segun presión de tecla
	private Rectas ultimaDireccion;
	private Actions ultimaAccion;
	
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		//MAPA
		mapa = new Mapa();
		mapa.dibujar(contentPane); //Dibuja los caminos y genera las bolitas
		jugadores=new ArrayList<Jugador>();
		//Creacion de pacman
		pacman = new Pacman(Pacman.crearLabel(new Punto(5,25)), lblName.getText());
		ultimaAccion=Actions.QUIETO;
		pacman.dibujar(contentPane);
		jugadores.add(pacman);
		userWindow = window;
		gameRunning = true;
		gameLoopThread = new GameThread();
		this.setTitle("PAC-MAN");
	}
	
	private void mensajeSalida(){
		int option = JOptionPane.showConfirmDialog(this,
			    "¿Esta seguro que quiere salir?",
			    "Saliendo del juego",
			    JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION){
			gameRunning = false;
			gameLoopThread.timer.cancel();
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
			ultimaAccion=Actions.ARRIBA;
			if(ultimaDireccion==Rectas.HORIZONTAL)
				return;
			pacman.cambiarSentido(Actions.ARRIBA);
		}
		else if(key.getKeyCode() == controles[ABAJO]) {
			ultimaAccion=Actions.ABAJO;
			if(ultimaDireccion==Rectas.HORIZONTAL)
				return;
			pacman.cambiarSentido(Actions.ABAJO);
		}
		else if(key.getKeyCode() == controles[IZQUIERDA]) {
			ultimaAccion=Actions.IZQUIERDA;
			if(ultimaDireccion==Rectas.VERTICAL)
				return;
			pacman.cambiarSentido(Actions.IZQUIERDA);
		}
		else if(key.getKeyCode() == controles[DERECHA]) {
			ultimaAccion=Actions.DERECHA;
			if(ultimaDireccion==Rectas.VERTICAL)
				return;
			pacman.cambiarSentido(Actions.DERECHA);
		}
	}
	
	private void update(){
		for(Iterator<Jugador>j=jugadores.iterator();j.hasNext();) {
			Jugador jug=j.next();
			jug.actualizarUbicacion(mapa.getArrayRectas());
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
			restrictBoundaries(jug);
			calcularColisiones (jug);
		}
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
		
		for(Iterator<Bolita>it=mapa.getArrayBolitas().iterator();it.hasNext();) {
			Bolita b = it.next();
			if(b.isAlive() && j.colisionaCon(b)) {
				mapa.removerBolita(b);
				it = mapa.getArrayBolitas().iterator();
			}
		}
		/* Metodo mas eficiente para calcular las colisiones con bolitas.
		 * Tiene un problema: Si el pacman tiene la posibilidad de comer dos
		 * bolitas casi cercanas, solo comera una.
		 * Lo dejo comentado porque es muy probable que sirva mas que la
		 * forma anterior una vez tengamos el mapa real
		 * Pacman no deberia comer dos bolitas a la vez... */
		/*
		for(Bolita b : mapa.getArrayBolitas()) {
			if(b.isAlive() && j.colisionaCon(b)) {
				mapa.removerBolita(b);
				break;
			}
		}
		*/
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
	
	/*Thread que maneja el Game Loop */
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
}
