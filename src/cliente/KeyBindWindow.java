package cliente;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyBindWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConfigWindow configWindow;
	private int control;

	/* KeyBindingsWindow Constructor */
	public KeyBindWindow(ConfigWindow window, int c) {
		configWindow=window;
		control=c;
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				switch(control){
					case 0:
						configWindow.setControlArriba(e.getKeyCode());
						configWindow.setEnabled(true);
						dispose();
						break;
					case 1:
						configWindow.setControlAbajo(e.getKeyCode());
						configWindow.setEnabled(true);
						dispose();
						break;
					case 2:
						configWindow.setControlIzq(e.getKeyCode());
						configWindow.setEnabled(true);
						dispose();
						break;
					case 3:
						configWindow.setControlDer(e.getKeyCode());
						configWindow.setEnabled(true);
						dispose();
						break;
					default: break;
					}
			}
		});
		setTitle("Modificacion de controles");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 283, 97);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPresioneUnaTecla = new JLabel("Presione una tecla");
		lblPresioneUnaTecla.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 14));
		lblPresioneUnaTecla.setHorizontalAlignment(SwingConstants.CENTER);
		lblPresioneUnaTecla.setBounds(10, 11, 257, 48);
		contentPane.add(lblPresioneUnaTecla);
	}
}
