package windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

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
			 }, 0, 100);
		}
	}
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblName;
	private JLabel pacman;
	private UserWindow userWindow;
	private boolean gameRunning;
	private int velX; //testing
	private int velY; //testing
	private GameThread gameLoopThread;
	private int[]controles;
	//CONSTANTES PARA EL MANEJO COMPRENSIBLE DEL VECTOR CONTROLES
	private final int ARRIBA=0;
	private final int ABAJO=1;
	private final int IZQUIERDA=2;
	private final int DERECHA=3;

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
		
		lblName = new JLabel("New label");
		lblName.setBounds(5, 5, 774, 14);
		contentPane.add(lblName);
		
		pacman = new JLabel("A");
		pacman.setHorizontalAlignment(SwingConstants.CENTER);
		pacman.setFont(new Font("Tahoma", Font.PLAIN, 40));
		pacman.setIcon(null);
		pacman.setBackground(Color.YELLOW);
		pacman.setBounds(21, 56, 50, 50);
		contentPane.add(pacman);
		userWindow = window;
		velX = 0;
		velY = 0;
		gameRunning = true;
		gameLoopThread = new GameThread();
	}
	
	public void setNameLabel(String s){
		lblName.setText(s);
	}
	
	private void mensajeSalida(){
		int option = JOptionPane.showConfirmDialog(this,
			    "¿Está seguro que quiere salir?",
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
		pacman.setLocation(pacman.getLocation().x + velX, pacman.getLocation().y + velY);
		restrictBoundaries();
	}
	
	/* Calcula y mueve al objeto si se paso de los límites de la ventana. */		
	private void restrictBoundaries() {		
		if( pacman.getX() < 0 ) /* Límite izquierdo */		
			pacman.setLocation(0, pacman.getY());		
		
		if( pacman.getX() + pacman.getWidth() >= this.getWidth() ) /* Límite derecho */		
			pacman.setLocation(this.getWidth() - pacman.getWidth(), pacman.getY());		
		
		if( pacman.getLocation().y < 0 ) /* Límite hacia arriba */		
			pacman.setLocation(pacman.getX(), 0);		
		
		if( pacman.getY() + pacman.getHeight() >= this.getHeight() ) /* Límite hacia abajo (anda mal) */		
			pacman.setLocation(pacman.getX(), this.getHeight() - pacman.getHeight());		
 	}
	
	private void handleKeyPress(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(userWindow != null) {
				mensajeSalida();
			}
		}
		else if(key.getKeyCode() == controles[ARRIBA]) {
			velX = 0;
			velY = -5;
		}
		else if(key.getKeyCode() == controles[ABAJO]) {
			velX = 0;
			velY = 5;
		}
		else if(key.getKeyCode() == controles[IZQUIERDA]) {
			velX = -5;
			velY = 0;
		}
		else if(key.getKeyCode() == controles[DERECHA]) {
			velX = 5;
			velY = 0;
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
