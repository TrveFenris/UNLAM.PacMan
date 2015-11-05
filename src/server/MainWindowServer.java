package server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;

public class MainWindowServer extends JFrame {
	//Thread de escucha del servidor
	private class ListenThread extends Thread {
		public void run() {
			while (bandera) {
	        	System.out.println("ESPERANDO");
	            socket = servidor.aceptarConexion();
	            if (socket != null)
	            	new ThreadServerLogin(socket,servidor).start();
	        }
	        servidor.pararServidor();
	        System.out.println("FIN DEL THREAD");
		}
		public void pararThread(){
			bandera=false;
		}
	}
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNombre;
	private JTextField textFieldIP;
	private JTextField textFieldPuerto;
	public static MainWindowServer frame;
	
	private Server servidor = null;
	private int puerto = 5055;
	private boolean bandera;
	private int maxClientes=6;
	private Socket socket = null;
	private ListenThread threadEscucha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MainWindowServer();
					frame.setVisible(true);			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindowServer() {
		setAlwaysOnTop(true);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				mensajeSalida();
			}
		});
		setTitle("Server PacMan");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 433, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 276, 22, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblServer = new JLabel("SERVER");
		lblServer.setForeground(SystemColor.textHighlight);
		lblServer.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 18));
		GridBagConstraints gbc_lblServer = new GridBagConstraints();
		gbc_lblServer.insets = new Insets(0, 0, 5, 5);
		gbc_lblServer.gridx = 3;
		gbc_lblServer.gridy = 0;
		contentPane.add(lblServer, gbc_lblServer);
		
		JLabel lblNombre = new JLabel("Nombre");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		contentPane.add(lblNombre, gbc_lblNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setEditable(false);
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombre.gridx = 3;
		gbc_textFieldNombre.gridy = 1;
		contentPane.add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel lblIp = new JLabel("IP");
		GridBagConstraints gbc_lblIp = new GridBagConstraints();
		gbc_lblIp.insets = new Insets(0, 0, 5, 5);
		gbc_lblIp.gridx = 1;
		gbc_lblIp.gridy = 2;
		contentPane.add(lblIp, gbc_lblIp);
		
		textFieldIP = new JTextField();
		textFieldIP.setEditable(false);
		GridBagConstraints gbc_textFieldIP = new GridBagConstraints();
		gbc_textFieldIP.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldIP.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldIP.gridx = 3;
		gbc_textFieldIP.gridy = 2;
		contentPane.add(textFieldIP, gbc_textFieldIP);
		textFieldIP.setColumns(10);
		
		JLabel lblPuerto = new JLabel("Puerto");
		GridBagConstraints gbc_lblPuerto = new GridBagConstraints();
		gbc_lblPuerto.insets = new Insets(0, 0, 0, 5);
		gbc_lblPuerto.gridx = 1;
		gbc_lblPuerto.gridy = 3;
		contentPane.add(lblPuerto, gbc_lblPuerto);
		
		textFieldPuerto = new JTextField();
		textFieldPuerto.setEditable(false);
		GridBagConstraints gbc_textFieldPuerto = new GridBagConstraints();
		gbc_textFieldPuerto.insets = new Insets(0, 0, 0, 5);
		gbc_textFieldPuerto.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPuerto.gridx = 3;
		gbc_textFieldPuerto.gridy = 3;
		contentPane.add(textFieldPuerto, gbc_textFieldPuerto);
		textFieldPuerto.setColumns(10);
		
		//SERVIDOR
		servidor = new Server(puerto, maxClientes);
		textFieldNombre.setText(servidor.getNombreHost());
		textFieldPuerto.setText(Integer.toString(servidor.getPuerto()));
		textFieldIP.setText(servidor.getIPHost());
		bandera=true;
		threadEscucha=new ListenThread();
		threadEscucha.start();
		System.out.println("SERVIDOR CREADO");
	}
	
	private void mensajeSalida() {
		int option = JOptionPane.showConfirmDialog(frame,
			    "¿Esta seguro que quiere salir?",
			    "Saliendo del juego",
			    JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION) {
			threadEscucha.pararThread();
			System.out.println("SERVER CERRADO");
			frame.dispose();
		}
	}
}
