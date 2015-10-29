package game;

public class Mapa {
	private Recta [] rectas;
	
	public Mapa(){
		rectas = new Recta [10]; /*Cambiar el 10 por la dimensión que corresponda*/
		/* Creo las distintas rectas (hardcodeadas) que conforman el mapa */
		/* crea una recta vertical, p ej */
		//agregarRecta(new Punto(10,10), new Punto(10,50), 0); 
	}
	
	private void agregarRecta(Punto p1, Punto p2, int i) {
		if(i < rectas.length)
			rectas[i] = new Recta(p1,p2);
	}
}
