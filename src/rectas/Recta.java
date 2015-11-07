package rectas;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import punto.Punto;

public class Recta {
	private Punto puntoInicial;
	private Punto puntoFinal;
	private Rectas tipo;

	public Recta(Punto p1, Punto p2) {
		if(p1.getX() == p2.getX()) {
			tipo = Rectas.VERTICAL;
			if(p1.getX() <= p2.getX()) {
				puntoInicial = p1;
				puntoFinal = p2;
			}
			else {
				puntoInicial = p2;
				puntoFinal = p1;
			}
			
		}
		else if(p1.getY() == p2.getY()) {
			tipo = Rectas.HORIZONTAL;
			if(p1.getY() <= p2.getY()) {
				puntoInicial = p1;
				puntoFinal = p2;
			}
			else {
				puntoInicial = p2;
				puntoFinal = p1;
			}
		}
	}
	
	public Rectas getTipo(){
		return tipo;
	}
	
	public Punto getPuntoInicial() {
		return puntoInicial;
	}
	
	public int getPuntoInicialX() {
		return puntoInicial.getX();
	}
	
	public int getPuntoInicialY() {
		return puntoInicial.getY();
	}
	
	public Punto getPuntoFinal() {
		return puntoFinal;
	}
	
	public int getPuntoFinalX() {
		return puntoFinal.getX();
	}
	
	public int getPuntoFinalY() {
		return puntoFinal.getY();
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
	
	@Override
	public String toString() {
		return "PI: ("+puntoInicial.getX()+", "+puntoInicial.getY()+") "
				+ "| PF: ("+puntoFinal.getX()+", "+puntoFinal.getY()+")";
	}
}
