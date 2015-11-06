package gameobject;

import game.Configuracion;
import game.Punto;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Fantasma extends Jugador{
	protected int pacmansComidos;
	protected int vecesAturdido;
	protected int muertes;

	public Fantasma(JLabel img,String nombre) {
		super(img,nombre,Configuracion.FANTASMA_VELOCIDAD.getValor());
		pacmansComidos=vecesAturdido=muertes=0;
	}

	public void reaparecer(){
		
	}
	
	public void dibujar(JPanel area){
		area.add(getImagen());
	}
	
	public void actualizarPuntaje(){
		this.actualizarPuntaje(Configuracion.FANTASMA_PUNTAJE_POR_PACMAN_COMIDO.getValor()*pacmansComidos
								-Configuracion.FANTASMA_PERDIDA_DE_PUNTAJE_POR_ATURDIMIENTO.getValor()*vecesAturdido
								-Configuracion.FANTASMA_PERDIDA_DE_PUNTAJE_POR_MUERTE.getValor()*muertes);
	}
	
	public static JLabel crearLabel(Punto posInicial){
		ImageIcon icon = new ImageIcon("img/pacman.gif");
		JLabel l = new JLabel(icon);
		l.setBounds(posInicial.getX(),posInicial.getY(), 50, 50);
		return l;
	}
}
