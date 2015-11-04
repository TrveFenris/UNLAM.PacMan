package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {
	/* Members */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static MainWindow frame;
	public static GameWindow gameWindow;
	private JButton btnLogin;
	private JButton btnRegistrarUsuario;
	private String UserName;
	private JLabel lblNombre;
	private JLabel lblPassword;
	private JTextField textFieldNombre;
	private JPasswordField pwdFieldPassword;
	private JLabel lblBienvenida;
	private UserWindow userWindow;
	//AGREGADO
	private BaseDatos bd=null;
	
	/* Main Application */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MainWindow();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/* MainWindow Constructor */
	public MainWindow() {
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
			//Â¿keyTyped registra la 1ra letra recien la 2da vez que se llama?
			public void keyReleased(KeyEvent e) {
				verificarTextFields();
				if(e.getKeyCode()==KeyEvent.VK_ENTER&&btnLogin.isEnabled()){
					UserName=new String(textFieldNombre.getText());
					lanzarVentanaUsuario();
				}
			}
		});
		pwdFieldPassword.setBounds(100, 81, 170, 20);
		contentPane.add(pwdFieldPassword);
		pwdFieldPassword.setColumns(20);
		
		btnLogin = new JButton("Iniciar sesi\u00F3n");
		btnLogin.setEnabled(false);
		btnLogin.setBounds(100, 112, 120, 23);
		contentPane.add(btnLogin);
		
		//AGREGADO
		bd = new BaseDatos();
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				UserName=new String(textFieldNombre.getText());
				String password= new String(pwdFieldPassword.getPassword());
				boolean estado = bd.Verificar(UserName,password);
				try{
					if(estado==true){
						lanzarVentanaUsuario();
					}
					else{
						JOptionPane.showMessageDialog(null, "Error al ingresar el usuario o contrase\u00F1a");
					}
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(null,"Error no de la base de datos","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		//FIN
		
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
		
		// Agregado
		btnRegistrarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserName=new String(textFieldNombre.getText());
				String password= new String(pwdFieldPassword.getPassword());
				boolean estado = false;
				try{
					estado = bd.VerificarUsuario(UserName);
					if(estado != true)
					{
						bd.agregar(UserName, password);
						lanzarVentanaUsuario();
					}
					else{
						JOptionPane.showMessageDialog(null, "Usuario ya registrado");
						textFieldNombre.setText("");
						pwdFieldPassword.setText("");
					}
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null,"Error no de la base de datos","Error",JOptionPane.ERROR_MESSAGE);					
				}
			}
		});
		//FIN
		
		lblBienvenida = new JLabel("");
		lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenida.setForeground(new Color(102, 153, 204));
		lblBienvenida.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 18));
		lblBienvenida.setBounds(10, 50, 295, 51);
		lblBienvenida.setVisible(false);
		contentPane.add(lblBienvenida);
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
	
	private void lanzarVentanaUsuario() {
		if(UserName.length() > 10) {
			JOptionPane.showMessageDialog(frame,
					"El nick debe contener diez caracteres como maximo.",
					 "Error",
					 JOptionPane.ERROR_MESSAGE);
			return;
		}
		frame.setVisible(false);
		//userWindow = new UserWindow(frame,UserName);
		userWindow.setLocationRelativeTo(null);
		userWindow.setVisible(true);
	}
	
	private void mensajeSalida() {
		int option = JOptionPane.showConfirmDialog(frame,
			    "¿Esta seguro que quiere salir?",
			    "Saliendo del juego",
			    JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION) {
			frame.dispose();
		}
	}
	
	public void resetUserAndPassword() {
		textFieldNombre.setText(null);
		pwdFieldPassword.setText(null);
		btnLogin.setEnabled(false);
		btnRegistrarUsuario.setEnabled(false);
	}
}
