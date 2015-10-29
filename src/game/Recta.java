package game;

public class Recta {
	protected Punto puntoInicial;
	protected Punto puntoFinal;
	protected Rectas tipo;
	
	protected Recta(Punto p1, Punto p2) {
		puntoInicial = p1;
		puntoFinal = p2;
		if(puntoInicial.getX() == puntoFinal.getX()) {
			tipo = Rectas.VERTICAL;
		}
		else if(puntoInicial.getY() == puntoFinal.getY()) {
			tipo = Rectas.HORIZONTAL;
		}
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
}
