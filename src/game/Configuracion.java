package game;

/**
 * Contiene los distintos parametros configurables del juego
 */
public enum Configuracion {
	TIEMPO_PARTIDA(60), //En segundos
	MAX_JUGADORES_PARTIDA (4),
	PUERTO_INICIAL (5070),
	RANGO_PUERTOS (50),
	MAX_CLIENTES (12),
	//PACMAN
	PACMAN_VELOCIDAD (2),
	PACMAN_DISTANCIA_PARA_COMER_BOLITA (10),
	PACMAN_PUNTAJE_POR_BOLITA (20),
	PACMAN_PUNTAJE_POR_BOLITA_ESPECIAL (200),
	PACMAN_PUNTAJE_POR_FANTASMA_COMIDO (500),
	PACMAN_PERDIDA_DE_PUNTAJE_POR_MUERTE (200),
	//FANTASMA
	FANTASMA_VELOCIDAD (PACMAN_VELOCIDAD.getValor()/2),
	FANTASMA_PUNTAJE_POR_PACMAN_COMIDO (1000),
	FANTASMA_PUNTAJE_POR_FANTASMA_COMIDO (250),
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
