package game;

import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PacMan extends Jugador {	
	private boolean superpoder;
	private Calendar timerSuperpoder;
	private int bolitasComidas;
	private int muertes;
	private int fantasmasComidos;
	
	public PacMan(JLabel img, String nombre) {
		super(img,nombre,1);
		superpoder = false;
		bolitasComidas=muertes=fantasmasComidos=0;
	}
	
	public void reaparecer(){
		
	}
	
	public boolean tieneSuperpoder(){
		return superpoder;
	}
	
	public void startTimer() {
		timerSuperpoder.clear();
	}
	
	public long getTimerMilliseconds() {
		return timerSuperpoder.getTimeInMillis();
	}
	
	public void dibujar(JPanel area){
		area.add(getImagen());
	}
	
	public void actualizarPuntaje(){
		this.actualizarPuntaje(50*bolitasComidas-200*muertes+500*fantasmasComidos);
	}
	
	public static JLabel crearLabel(Punto posInicial){
		ImageIcon icon = new ImageIcon("img/pacman.gif");
		JLabel l = new JLabel(icon);
		l.setBounds(posInicial.getX(),posInicial.getY(), 50, 50);
		return l;
	}
}
