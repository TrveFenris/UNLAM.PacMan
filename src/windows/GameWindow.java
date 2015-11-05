package windows;

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

import game.Mapa;
import game.Punto;
import game.Recta;
//import game.Rectas;
import game.Rectas;
import gameobject.Jugador;
import gameobject.PacMan;

public class GameWindow extends JFrame {
	
	public class GameThread extends Thread {
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
	private PacMan pacman;
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
		mapa = new Mapa(contentPane);
		mapa.dibujar(); //Dibuja los caminos y genera las bolitas
		jugadores=new ArrayList<Jugador>();
		//CreaciÃ³n de pacman
		Punto p = new Punto(5,25);
		pacman = new PacMan(PacMan.crearLabel(p), lblName.getText());
		pacman.dibujar(contentPane);
		jugadores.add(pacman);
		//
		userWindow = window;
		gameRunning = true;
		gameLoopThread = new GameThread();
		this.setTitle("PAC-MAN");
	}
	
	public void setNameLabel(String s){
		lblName.setText(s);
	}
	
	private void mensajeSalida(){
		int option = JOptionPane.showConfirmDialog(this,
			    "Â¿Esta seguro que quiere salir?",
			    "Saliendo del juego",
			    JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION){
			gameRunning = false;
			gameLoopThread.timer.cancel();
			this.dispose();
			userWindow.setVisible(true);
		}
	}
	
	private void update(){
		for(Iterator<Jugador>j=jugadores.iterator();j.hasNext();) {
			int contadorRectas=0;
			Jugador jug=j.next();
			for(Iterator<Recta>k=mapa.getArrayRectas().iterator();k.hasNext();) {
				Recta rec = k.next();
				if(jug.estaEn(rec)) {
					contadorRectas++;
				}
			}
			for(Iterator<Recta>k=mapa.getArrayRectas().iterator();k.hasNext();) {
				Recta rec = k.next();
				if(contadorRectas==1) {
					if(jug.estaEn(rec)) {
						if(rec.getTipo() == Rectas.HORIZONTAL) {
							leftBound = rec.getPuntoInicial().getX();
							rightBound = rec.getPuntoFinal().getX();
							upperBound = lowerBound = rec.getPuntoInicial().getY();
						}
						else if(rec.getTipo() == Rectas.VERTICAL) {
							upperBound = rec.getPuntoInicial().getY();
							lowerBound = rec.getPuntoFinal().getY();
							leftBound = rightBound = rec.getPuntoInicial().getX();
						}
					}
				}
				else if(contadorRectas>1) {
					if(jug.estaEn(rec)) {
						if(rec.getTipo() == Rectas.HORIZONTAL) {
							leftBound = rec.getPuntoInicial().getX();
							rightBound = rec.getPuntoFinal().getX();
						}
						else if(rec.getTipo() == Rectas.VERTICAL) {
							upperBound = rec.getPuntoInicial().getY();
							lowerBound = rec.getPuntoFinal().getY();
						}
					}
				}
			}
					//System.out.println("Estoy en una recta");
		
			jug.mover();
			restrictBoundaries(jug);
		}
	}
	
	/* Calcula y mueve al objeto si se paso de los limites de la ventana. */
	/*
	private void restrictBoundaries(Jugador j) {
		if( j.getX() < 0 ) // Limite izquierdo
			j.setLocation(0, j.getY());		
		
		if( j.getX() + j.getWidth() >= this.getWidth() ) // Limite derecho (anda mal)		
			j.setLocation(this.getWidth() - j.getWidth(), j.getY());		
		
		if( j.getLocation().getY() < 0 ) // Limite hacia arriba		
			j.setLocation(j.getX(), 0);		
		
		if( j.getY() + j.getHeight() >= this.getHeight() ) // Limite hacia abajo (anda mal)	
			j.setLocation(j.getX(), this.getHeight() - j.getHeight());		
 	}
	*/
	private void restrictBoundaries(Jugador j) {
		if( j.getCentroCoordenadas().getX() < leftBound ) /* Limite izquierdo */		
			j.setLocation(leftBound - (j.getWidth()/2), j.getY());		
		
		if( j.getCentroCoordenadas().getX() > rightBound ) /* Limite derecho (anda mal) */		
			j.setLocation(rightBound - (j.getWidth()/2), j.getY());		
		
		if( j.getCentroCoordenadas().getY() < upperBound ) /* Limite hacia arriba */		
			j.setLocation(j.getX(), upperBound - (j.getHeight()/2));		
		
		if( j.getCentroCoordenadas().getY() > lowerBound ) /* Limite hacia abajo (anda mal) */		
			j.setLocation(j.getX(), lowerBound - (j.getHeight()/2));		
 	}
	/*
	private void calculateBounds() {
		
	}
	*/
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
	
	public void setControles(int[] controles){
		this.controles=new int[4];
		for(int i=0;i<4;i++){
			this.controles[i]=controles[i];
		}
	}
	
	public void runGameLoop() {
		gameLoopThread.start();
	}
}
