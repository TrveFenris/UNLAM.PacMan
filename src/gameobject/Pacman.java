package gameobject;

import game.Configuracion;
import game.Punto;

import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Pacman extends Jugador {	
	private boolean superpoder;
	private Calendar timerSuperpoder;
	private int bolitasComidas;
	private int muertes;
	private int fantasmasComidos;
	
	public Pacman(JLabel img, String nombre) {
		super(img,nombre,Configuracion.PACMAN_VELOCIDAD.getValor());
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
		this.actualizarPuntaje(Configuracion.PACMAN_PUNTAJE_POR_BOLITA.getValor()*bolitasComidas
								-Configuracion.PACMAN_PERDIDA_DE_PUNTAJE_POR_MUERTE.getValor()*muertes
								+Configuracion.PACMAN_PUNTAJE_POR_FANTASMA_COMIDO.getValor()*fantasmasComidos);
	}
	
	/**
	 * Inicializa el label necesario para la construccion de un objeto Pacman
	 * @param posInicial
	 * @return
	 */
	public static JLabel crearLabel(Punto posInicial){
		ImageIcon icon = new ImageIcon("img/pacman.gif");
		JLabel l = new JLabel(icon);
		l.setBounds(posInicial.getX(),posInicial.getY(), 50, 50);
		return l;
	}
}
