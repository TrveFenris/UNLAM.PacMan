package gameobject;

public enum Direcciones {
	ARRIBA(0),
	ABAJO(1),
	IZQUIERDA(2),
	DERECHA(3),
	NINGUNA(4),
	;
	
	private int valor;
	
	private Direcciones(int d) {
		valor = d;
	}
	
	public int getValor() {
		return valor;
	}
}
