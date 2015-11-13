package server;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IngresoNombrePartida extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private IngresoNombrePartida thisWindow;
	private ServerWindow main;
	private JTextField textFieldNombre;

	/**
	 * Create the frame.
	 */
	public IngresoNombrePartida(ServerWindow main) {
		setTitle("Crear partida");
		this.main=main;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 172);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 160, 30, 160, 30};
		gridBagLayout.rowHeights = new int[]{30, 30, 30, 30, 30};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNombrePartida = new JLabel("Nombre para la partida:");
		GridBagConstraints gbc_lblNombrePartida = new GridBagConstraints();
		gbc_lblNombrePartida.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombrePartida.gridx = 1;
		gbc_lblNombrePartida.gridy = 1;
		getContentPane().add(lblNombrePartida, gbc_lblNombrePartida);
		
		textFieldNombre = new JTextField();
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombre.gridx = 3;
		gbc_textFieldNombre.gridy = 1;
		getContentPane().add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 1;
		gbc_btnCancelar.gridy = 3;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textFieldNombre.getText().equals(null) || textFieldNombre.getText().equals("")) {
					JOptionPane.showMessageDialog(thisWindow,
							"Ingrese un nombre para la partida.",
							 "Error",
							 JOptionPane.ERROR_MESSAGE);
				}
				else{
					main.crearPartida(textFieldNombre.getText());
					dispose();
				}
			}
		});
		GridBagConstraints gbc_btnCrear = new GridBagConstraints();
		gbc_btnCrear.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCrear.insets = new Insets(0, 0, 5, 5);
		gbc_btnCrear.gridx = 3;
		gbc_btnCrear.gridy = 3;
		getContentPane().add(btnCrear, gbc_btnCrear);
	}

}
