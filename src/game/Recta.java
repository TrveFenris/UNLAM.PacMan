package game;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Recta {
	protected Punto puntoInicial;
	protected Punto puntoFinal;
	protected Rectas tipo;
	
	protected Recta(Punto p1, Punto p2) {
		if(p1.modulo()>p2.modulo()){
			puntoInicial = p2;
			puntoFinal = p1;
		}
		else{
			puntoInicial = p1;
			puntoFinal = p2;
		}
		if(puntoInicial.getX() == puntoFinal.getX())
			tipo = Rectas.VERTICAL;
		else 
			if(puntoInicial.getY() == puntoFinal.getY())
				tipo = Rectas.HORIZONTAL;
	}
	
	public Rectas getTipo(){
		return tipo;
	}
	
	public Punto getPuntoInicial() {
		return puntoInicial;
	}
	
	public Punto getPuntoFinal() {
		return puntoFinal;
	}
	
	public int getLongitud(){
		int l=0;
		if(tipo==Rectas.HORIZONTAL)
			l= puntoFinal.getX()-puntoInicial.getX();
		else if(tipo==Rectas.VERTICAL)
			l= puntoFinal.getY()-puntoInicial.getY();
		return l;
	}
	
	public void dibujar(JPanel area){
		JLabel camino = new JLabel();
		if(tipo==Rectas.HORIZONTAL)
			camino.setBounds(puntoInicial.getX(), puntoInicial.getY(), puntoFinal.getX()-puntoInicial.getX(), 1);
		else
			if(tipo==Rectas.VERTICAL)
				camino.setBounds(puntoInicial.getX(), puntoInicial.getY(), 1, puntoFinal.getY()-puntoInicial.getY());
		camino.setBackground(Color.GREEN);
		camino.setOpaque(true);
		area.add(camino);
	}
}
