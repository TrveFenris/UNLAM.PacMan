package game;

/**
 * Contiene los distintos parametros configurables del juego
 */
public enum Configuracion {
	//PACMAN
	PACMAN_VELOCIDAD (2),
	PACMAN_PUNTAJE_POR_BOLITA (20),
	PACMAN_PUNTAJE_POR_BOLITA_ESPECIAL (200),
	PACMAN_PUNTAJE_POR_FANTASMA_COMIDO (500),
	PACMAN_PERDIDA_DE_PUNTAJE_POR_MUERTE (200),
	//FANTASMA
	FANTASMA_VELOCIDAD (1),
	FANTASMA_PUNTAJE_POR_PACMAN_COMIDO (1000),
	FANTASMA_PERDIDA_DE_PUNTAJE_POR_MUERTE (200),
	FANTASMA_PERDIDA_DE_PUNTAJE_POR_ATURDIMIENTO (100),
	;
	//Nombre de los campos (en orden)
	private int valor;
	
	//Constructor
	private Configuracion(int v){
		this.valor=v;
	}
	
	/**
	 * Devuelve el valor entero de un parametro
	 */
	public int getValor(){
		return valor;
	}
}
