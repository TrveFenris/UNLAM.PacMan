package cliente;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainWindowSinDB extends JFrame {
	
	public class MainWindowThreadCliente extends Thread {
		public void run() {
			cliente.enviarMensaje();
		}
	}
	
	/* Members */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnLogin;
	private JButton btnRegistrarUsuario;
	private JLabel lblNombre;
	private JLabel lblPassword;
	private JLabel lblBienvenida;
	private JTextField textFieldNombre;
	private JPasswordField pwdFieldPassword;
	private JTextField txtServidor;
	private JTextField txtPuerto;
	public static MainWindowSinDB frame;
	public static GameWindow gameWindow;
	private UserWindow userWindow;
	
	private Cliente cliente;
	/* Main Application */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MainWindowSinDB();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/* MainWindow Constructor */
	public MainWindowSinDB() {
		frame = this;
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				mensajeSalida();
			}
		});
		setTitle("Pac-Man");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 331, 306);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("PAC-MAN");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Sylfaen", Font.PLAIN, 24));
		lblTitle.setBounds(100, 11, 120, 35);
		contentPane.add(lblTitle);
		JButton btnExit = new JButton("Salir");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mensajeSalida();
			}
		});
		btnExit.setBounds(100, 216, 120, 25);
		contentPane.add(btnExit);
		JButton btnCredits = new JButton("Cr\u00E9ditos");
		btnCredits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Grupo 4: \n-Barja, Alex\n-Figueroa, Matias\n-Maidana, Diego\n-Maita, Martin");
			}
		});
		btnCredits.setBounds(100, 180, 120, 25);
		contentPane.add(btnCredits);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setToolTipText("Introduzca su nombre aqu\u00ED");
		textFieldNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				verificarTextFields();
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					pwdFieldPassword.grabFocus();
			}
		});
		textFieldNombre.setBounds(100, 50, 170, 20);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		pwdFieldPassword = new JPasswordField();
		pwdFieldPassword.setToolTipText("Introduzca su contrase\u00F1a aqu\u00ED");
		pwdFieldPassword.addKeyListener(new KeyAdapter() {
			@Override
			//Ã‚Â¿keyTyped registra la 1ra letra recien la 2da vez que se llama?
			public void keyReleased(KeyEvent e) {
				verificarTextFields();
				if(e.getKeyCode()==KeyEvent.VK_ENTER&&btnLogin.isEnabled()){
					lanzarVentanaUsuario(textFieldNombre.getText());
				}
			}
		});
		pwdFieldPassword.setBounds(100, 81, 170, 20);
		contentPane.add(pwdFieldPassword);
		pwdFieldPassword.setColumns(20);
		
		btnLogin = new JButton("Iniciar sesi\u00F3n");
		btnLogin.setEnabled(false);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtServidor.getText().equals(null) || txtServidor.getText().equals("")) {
					JOptionPane.showMessageDialog(frame,
							"Ingrese un servidor al que conectarse.",
							 "Error",
							 JOptionPane.ERROR_MESSAGE);
					return;
				}
				else if(txtPuerto.getText().equals(null) || txtPuerto.getText().equals("")) {
					JOptionPane.showMessageDialog(frame,
							"Ingrese un puerto al que conectarse.",
							 "Error",
							 JOptionPane.ERROR_MESSAGE);
					return;
				}
				else {
					String server = txtServidor.getText();
					int puerto;
					try {
						puerto = Integer.parseInt(txtPuerto.getText());
					}
					catch(NumberFormatException nfe) {
						JOptionPane.showMessageDialog(frame,
								"Los datos del puerto son inválidos.\nIngrese un número entero",
								 "Error",
								 JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					String username = textFieldNombre.getText();
					try {
						cliente = new Cliente(server, puerto, username);
						lanzarVentanaUsuario(username);
						System.out.println("(Ingrese .exit en cualquier momento para cerrar la aplicacion)");
						new ThreadCliente(cliente.getSocket()).start();
						new MainWindowThreadCliente().start();
					}
					catch(UnknownHostException e1) {
			        	JOptionPane.showMessageDialog(frame,
			        			"No se pudo conectar con el servidor.\nPuede que esté ocupado o no esté en línea.",
								 "Error",
								 JOptionPane.ERROR_MESSAGE);
						return;
					}
			        catch (IOException e2) {
			            JOptionPane.showMessageDialog(frame,
			            		"No se pudo crear el socket.\nInténtelo nuevamente.",
								 "Error",
								 JOptionPane.ERROR_MESSAGE);
						return;
			        }
				}
			}
		});
		btnLogin.setBounds(100, 112, 120, 23);
		contentPane.add(btnLogin);
		
		lblNombre = new JLabel("Usuario");
		lblNombre.setBounds(10, 53, 46, 14);
		contentPane.add(lblNombre);
		
		lblPassword = new JLabel("Contrase\u00F1a");
		lblPassword.setBounds(10, 84, 56, 14);
		contentPane.add(lblPassword);
		
		btnRegistrarUsuario = new JButton("Registrarse");
		btnRegistrarUsuario.setEnabled(false);
		btnRegistrarUsuario.setBounds(100, 146, 120, 23);
		contentPane.add(btnRegistrarUsuario);
		
		lblBienvenida = new JLabel("");
		lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenida.setForeground(new Color(102, 153, 204));
		lblBienvenida.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 18));
		lblBienvenida.setBounds(10, 50, 295, 51);
		lblBienvenida.setVisible(false);
		contentPane.add(lblBienvenida);
		
		JLabel lblServidor = new JLabel("Servidor");
		lblServidor.setBounds(230, 112, 46, 14);
		contentPane.add(lblServidor);
		
		JLabel lblPuerto = new JLabel("Puerto");
		lblPuerto.setBounds(230, 162, 46, 14);
		contentPane.add(lblPuerto);
		
		txtServidor = new JTextField();
		txtServidor.setBounds(230, 131, 86, 20);
		contentPane.add(txtServidor);
		txtServidor.setColumns(10);
		
		txtPuerto = new JTextField();
		txtPuerto.setBounds(230, 181, 86, 20);
		contentPane.add(txtPuerto);
		txtPuerto.setColumns(10);
	}
	
	/* Metodos */
	private void verificarTextFields() {
		if(textFieldNombre.getText().length()>0&&pwdFieldPassword.getPassword().length>0) {
			btnLogin.setEnabled(true);
			btnRegistrarUsuario.setEnabled(true);
		}
		else {
			btnLogin.setEnabled(false);
			btnRegistrarUsuario.setEnabled(false);
		}
	}
	
	private void lanzarVentanaUsuario(String username) {
		if(username.length() > 10) {
			JOptionPane.showMessageDialog(frame,
					"El nick debe contener diez caracteres como maximo.",
					 "Error",
					 JOptionPane.ERROR_MESSAGE);
			return;
		}
		frame.setVisible(false);
		userWindow = new UserWindow(frame,username);
		userWindow.setLocationRelativeTo(null);
		userWindow.setVisible(true);
	}
	
	private void mensajeSalida() {
		int option = JOptionPane.showConfirmDialog(frame,
			    "Â¿Esta seguro que quiere salir?",
			    "Saliendo del juego",
			    JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION) {
			if(cliente != null)
				cliente.cerrarCliente();
			frame.dispose();
		}
	}
	
	public void resetUserAndPassword() {
		textFieldNombre.setText(null);
		pwdFieldPassword.setText(null);
		btnLogin.setEnabled(false);
		btnRegistrarUsuario.setEnabled(false);
	}
	
	protected void cerrarCliente() {
		cliente.cerrarCliente();
	}
}
