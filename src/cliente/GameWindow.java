package cliente;

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

import rectas.Recta;
import rectas.Rectas;
import game.Mapa;
import game.Punto;
import gameobject.Actions;
//import game.Rectas;
import gameobject.Jugador;
import gameobject.Pacman;

public class GameWindow extends JFrame {
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
	//Variables delimitadoras
	private int upperBound;
	private int lowerBound;
	private int leftBound;
	private int rightBound;
	//Variables de acción segun presión de tecla
	private boolean moverAbajo;
	private boolean moverArriba;
	private boolean moverIzquierda;
	private boolean moverDerecha;
	
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
		moverAbajo = false;
		moverArriba = false;
		moverIzquierda = false;
		moverDerecha = false;
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(userWindow != null) {
				mensajeSalida();
			}
		}
		else if(key.getKeyCode() == controles[ARRIBA]) {
			moverArriba = true;
			pacman.cambiarSentido(Actions.ARRIBA);
		}
		else if(key.getKeyCode() == controles[ABAJO]) {
			moverAbajo = true;
			pacman.cambiarSentido(Actions.ABAJO);
		}
		else if(key.getKeyCode() == controles[IZQUIERDA]) {
			moverIzquierda = true;
			pacman.cambiarSentido(Actions.IZQUIERDA);
		}
		else if(key.getKeyCode() == controles[DERECHA]) {
			moverDerecha = true;
			pacman.cambiarSentido(Actions.DERECHA);
		}
	}
	
	private void update(){
		for(Iterator<Jugador>j=jugadores.iterator();j.hasNext();) {
			Jugador jug=j.next();
			jug.actualizarUbicacion(mapa.getArrayRectas());
			System.out.println(jug.getTipoUbicacion());
			switch(jug.getTipoUbicacion()){
				case HORIZONTAL:
					leftBound = jug.getRectaActual(0).getPuntoInicialX();
					rightBound = jug.getRectaActual(0).getPuntoFinalX();
					upperBound = lowerBound = jug.getRectaActual(0).getPuntoInicialY();
					break;
				case VERTICAL:
					upperBound = jug.getRectaActual(0).getPuntoInicialY();
					lowerBound = jug.getRectaActual(0).getPuntoFinalY();
					leftBound = rightBound = jug.getRectaActual(0).getPuntoInicialX();
					break;
				case AMBAS:
					for(int i=0;i<2;i++){
						if(jug.getRectaActual(i).getTipo()==Rectas.HORIZONTAL){
							leftBound = jug.getRectaActual(i).getPuntoInicialX();
							rightBound = jug.getRectaActual(i).getPuntoFinalX();
						}
						else
							if(jug.getRectaActual(i).getTipo()==Rectas.VERTICAL){
								upperBound = jug.getRectaActual(i).getPuntoInicialY();
								lowerBound = jug.getRectaActual(i).getPuntoFinalY();
							}
					}
					break;
			}
			jug.mover();
			restrictBoundaries(jug);
		}
	}

	/**
	 * Asegura que el personaje controlado por un jugador se mantenga sobre las rectas del mapa
	 * @param Jugador
	 */
	private void restrictBoundaries(Jugador j) {
		if( j.getCentroCoordenadasX() < leftBound ) /* Limite izquierdo */		
			j.setLocation(leftBound - (j.getWidth()/2), j.getY());		
		
		if( j.getCentroCoordenadasX() > rightBound ) /* Limite derecho (anda mal) */		
			j.setLocation(rightBound - (j.getWidth()/2), j.getY());		
		
		if( j.getCentroCoordenadasY() < upperBound ) /* Limite hacia arriba */		
			j.setLocation(j.getX(), upperBound - (j.getHeight()/2));		
		
		if( j.getCentroCoordenadasY() > lowerBound ) /* Limite hacia abajo (anda mal) */		
			j.setLocation(j.getX(), lowerBound - (j.getHeight()/2));		
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
}
