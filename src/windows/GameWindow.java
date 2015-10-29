package tallerjava.windows;

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
		public void run() {
			System.out.println("Comienza el juego");
			Timer timer = new Timer();
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

	/* GameWindow constructor */
	public GameWindow(UserWindow window) {
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
			this.dispose();
			userWindow.setVisible(true);
		}
	}
	
	private void update(){
		pacman.setLocation(pacman.getLocation().x+velX, pacman.getLocation().y+velY);
	}
	
	private void handleKeyPress(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(userWindow != null) {
				mensajeSalida();
			}
		}
		else if(key.getKeyCode() == KeyEvent.VK_UP) {
			velX = 0;
			velY = -5;
		}
		else if(key.getKeyCode() == KeyEvent.VK_DOWN) {
			velX = 0;
			velY = 5;
		}
		else if(key.getKeyCode() == KeyEvent.VK_LEFT) {
			velX = -5;
			velY = 0;
		}
		else if(key.getKeyCode() == KeyEvent.VK_RIGHT) {
			velX = 5;
			velY = 0;
		}
	}
	
	public void runGameLoop() {
		gameLoopThread.start();
	}
}
