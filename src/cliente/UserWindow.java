package cliente;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class UserWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private UserWindow thisWindow;
	private GameWindow gameWindow;
	private MainWindowSinDB mainWindow;
	private ConfigWindow configWindow;
	private JPanel contentPane;
	private JButton btnCerrarSesion;
	private JButton btnConfig;
	private JButton btnBuscarPartida;
	private JLabel lblBienvenida;
	private Cliente cliente;
	//CONFIGURACION
	private String userName;
	private int arriba;
	private int abajo;
	private int izquierda;
	private int derecha;
	
	/* UserWindow Constructor */
	//public UserWindow(MainWindow window,String nombre) {
	public UserWindow(MainWindowSinDB window,String nombre, Cliente cliente) {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(mainWindow != null) {
					confirmarCerrarSesion();
				}
			}
		});
		this.cliente=cliente;
		mainWindow = window;
		thisWindow = this;
		userName = nombre;
		setTitle("Menu principal");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 441, 263);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 97, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 13, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblBienvenida = new JLabel("");
		lblBienvenida.setForeground(new Color(51, 153, 204));
		lblBienvenida.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 18));
		lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenida.setText("¡Bienvenid@ "+userName+"! ");
		GridBagConstraints gbc_lblBienvenida = new GridBagConstraints();
		gbc_lblBienvenida.insets = new Insets(0, 0, 5, 0);
		gbc_lblBienvenida.gridheight = 2;
		gbc_lblBienvenida.gridwidth = 4;
		gbc_lblBienvenida.gridx = 0;
		gbc_lblBienvenida.gridy = 1;
		contentPane.add(lblBienvenida, gbc_lblBienvenida);
		
		btnBuscarPartida = new JButton("Buscar partida");
		btnBuscarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//lanzarJuego();
				buscarPartida();
			}
		});
		GridBagConstraints gbc_btnBuscarPartida = new GridBagConstraints();
		gbc_btnBuscarPartida.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBuscarPartida.insets = new Insets(0, 0, 5, 5);
		gbc_btnBuscarPartida.gridx = 1;
		gbc_btnBuscarPartida.gridy = 4;
		contentPane.add(btnBuscarPartida, gbc_btnBuscarPartida);
		
		btnConfig = new JButton("Configuracion");
		btnConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lanzarVentanaConfiguracion();
			}
		});
		GridBagConstraints gbc_btnConfig = new GridBagConstraints();
		gbc_btnConfig.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnConfig.insets = new Insets(0, 0, 5, 5);
		gbc_btnConfig.gridx = 2;
		gbc_btnConfig.gridy = 4;
		contentPane.add(btnConfig, gbc_btnConfig);
		
		btnCerrarSesion = new JButton("Cerrar sesion");
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirmarCerrarSesion();
			}
		});
		GridBagConstraints gbc_btnCerrarSesion = new GridBagConstraints();
		gbc_btnCerrarSesion.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCerrarSesion.gridwidth = 2;
		gbc_btnCerrarSesion.insets = new Insets(0, 0, 0, 5);
		gbc_btnCerrarSesion.gridx = 1;
		gbc_btnCerrarSesion.gridy = 6;
		contentPane.add(btnCerrarSesion, gbc_btnCerrarSesion);
		
		cargarControles();
	}

	/* Metodos */
	private void confirmarCerrarSesion(){
		int res= JOptionPane.showConfirmDialog(this,
			    "¿Esta seguro?",
			    "Cerrando sesion",
			    JOptionPane.YES_NO_OPTION);
		if(res == JOptionPane.YES_OPTION) {
			mainWindow.resetUserAndPassword();
			mainWindow.setVisible(true);
			cliente.cerrarCliente();
			this.dispose();
		}
	}

	public void lanzarJuego(){
		this.setVisible(false);
		gameWindow = new GameWindow(this);
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setNameLabel(userName);
		gameWindow.setVisible(true);
		gameWindow.runGameLoop();
		gameWindow.setControles(this.getControles());
	}
	
	private void buscarPartida(){
		ArrayList<String> datos = cliente.buscarPartidas();
		if(datos==null){
			System.out.println("Error al recibir la lista de partidas.");
			return;
		}
		PartidasDisponibles winPartidas = new PartidasDisponibles(datos, thisWindow, cliente);
		winPartidas.setVisible(true);
		this.setVisible(false);
	}
	
	/**
	 * Crga los controles por defecto.
	 */
	private void cargarControles() {
			this.arriba=KeyEvent.VK_W;
			this.abajo=KeyEvent.VK_S;
			this.derecha=KeyEvent.VK_D;
			this.izquierda=KeyEvent.VK_A;
	}
	
	public void setControles(int arriba, int abajo, int izquierda, int derecha) {
		this.arriba=arriba;
		this.abajo=abajo;
		this.derecha=derecha;
		this.izquierda=izquierda;
	}
	
	/**
	 * Devuelve un vector de enteros con los keyCode de los controles asignados. El orden es: arriba (0), abajo(1), izquierda(2), derecha(3).
	 * @return
	 */
	public int[] getControles() {
		int[] controles=new int[4];
		controles[0]=arriba;
		controles[1]=abajo;
		controles[2]=izquierda;
		controles[3]=derecha;
		return controles;
	}
	
	private void lanzarVentanaConfiguracion() {
		this.setVisible(false);
		configWindow = new ConfigWindow(this);
		configWindow.setLocationRelativeTo(null);
		configWindow.setVisible(true);
	}
}
