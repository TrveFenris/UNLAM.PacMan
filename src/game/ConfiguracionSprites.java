package game;

public enum ConfiguracionSprites {
	//PACMAN
	//PACMAN_SPRITE ("img/pacman.gif"),
	// BowWow Skin
	PACMAN_BOWWOW_ARRIBA("img/pacman/pacman-bowwow-arriba.gif"),
	PACMAN_BOWWOW_ABAJO("img/pacman/pacman-bowwow-abajo.gif"),
	PACMAN_BOWWOW_IZQUIERDA("img/pacman/pacman-bowwow-izquierda.gif"),
	PACMAN_BOWWOW_DERECHA("img/pacman/pacman-bowwow-derecha.gif"),
	// Brasil Skin
	PACMAN_BRASIL_ARRIBA("img/pacman/pacman-brasil-arriba.gif"),
	PACMAN_BRASIL_ABAJO("img/pacman/pacman-brasil-abajo.gif"),
	PACMAN_BRASIL_IZQUIERDA("img/pacman/pacman-brasil-izquierda.gif"),
	PACMAN_BRASIL_DERECHA("img/pacman/pacman-brasil-derecha.gif"),
	// Loco Skin
	PACMAN_LOCO_ARRIBA("img/pacman/pacman-loco-arriba.gif"),
	PACMAN_LOCO_ABAJO("img/pacman/pacman-loco-abajo.gif"),
	PACMAN_LOCO_IZQUIERDA("img/pacman/pacman-loco-izquierda.gif"),
	PACMAN_LOCO_DERECHA("img/pacman/pacman-loco-derecha.gif"),
	// Malvado Skin
	PACMAN_MALVADO_ARRIBA("img/pacman/pacman-malvado-arriba.gif"),
	PACMAN_MALVADO_ABAJO("img/pacman/pacman-malvado-abajo.gif"),
	PACMAN_MALVADO_IZQUIERDA("img/pacman/pacman-malvado-izquierda.gif"),
	PACMAN_MALVADO_DERECHA("img/pacman/pacman-malvado-derecha.gif"),
	// Melon Skin
	PACMAN_MELON_ARRIBA("img/pacman/pacman-melon-arriba.gif"),
	PACMAN_MELON_ABAJO("img/pacman/pacman-melon-abajo.gif"),
	PACMAN_MELON_IZQUIERDA("img/pacman/pacman-melon-izquierda.gif"),
	PACMAN_MELON_DERECHA("img/pacman/pacman-melon-derecha.gif"),
	// Default Skin
	PACMAN_DEFAULT_ARRIBA("img/pacman/pacman-normal-arriba.gif"),
	PACMAN_DEFAULT_ABAJO("img/pacman/pacman-normal-abajo.gif"),
	PACMAN_DEFAULT_IZQUIERDA("img/pacman/pacman-normal-izquierda.gif"),
	PACMAN_DEFAULT_DERECHA("img/pacman/pacman-normal-derecha.gif"),
	// Yin skin
	PACMAN_YIN_ARRIBA("img/pacman/pacman-yin-arriba.gif"),
	PACMAN_YIN_ABAJO("img/pacman/pacman-yin-abajo.gif"),
	PACMAN_YIN_IZQUIERDA("img/pacman/pacman-yin-izquierda.gif"),
	PACMAN_YIN_DERECHA("img/pacman/pacman-yin-derecha.gif"),
	//FANTASMA
	//FANTASMA_SPRITE ("img/pacman.gif"),
	// BowWow Skin
	FANTASMA_BOWWOW_ARRIBA("img/fantasma/fantasma-bowwow-arriba.gif"),
	FANTASMA_BOWWOW_ABAJO("img/fantasma/fantasma-bowwow-abajo.gif"),
	FANTASMA_BOWWOW_IZQUIERDA("img/fantasma/fantasma-bowwow-izquierda.gif"),
	FANTASMA_BOWWOW_DERECHA("img/fantasma/fantasma-bowwow-derecha.gif"),
	// Brasil Skin
	FANTASMA_BRASIL_ARRIBA("img/fantasma/fantasma-brasil-arriba.gif"),
	FANTASMA_BRASIL_ABAJO("img/fantasma/fantasma-brasil-abajo.gif"),
	FANTASMA_BRASIL_IZQUIERDA("img/fantasma/fantasma-brasil-izquierda.gif"),
	FANTASMA_BRASIL_DERECHA("img/fantasma/fantasma-brasil-derecha.gif"),
	// Loco Skin
	FANTASMA_LOCO_ARRIBA("img/fantasma/fantasma-loco-arriba.gif"),
	FANTASMA_LOCO_ABAJO("img/fantasma/fantasma-loco-abajo.gif"),
	FANTASMA_LOCO_IZQUIERDA("img/fantasma/fantasma-loco-izquierda.gif"),
	FANTASMA_LOCO_DERECHA("img/fantasma/fantasma-loco-derecha.gif"),
	// Malvado Skin
	FANTASMA_MALVADO_ARRIBA("img/fantasma/fantasma-malvado-arriba.gif"),
	FANTASMA_MALVADO_ABAJO("img/fantasma/fantasma-malvado-abajo.gif"),
	FANTASMA_MALVADO_IZQUIERDA("img/fantasma/fantasma-malvado-izquierda.gif"),
	FANTASMA_MALVADO_DERECHA("img/fantasma/fantasma-malvado-derecha.gif"),
	// Melon Skin
	FANTASMA_MELON_ARRIBA("img/fantasma/fantasma-melon-arriba.gif"),
	FANTASMA_MELON_ABAJO("img/fantasma/fantasma-melon-abajo.gif"),
	FANTASMA_MELON_IZQUIERDA("img/fantasma/fantasma-melon-izquierda.gif"),
	FANTASMA_MELON_DERECHA("img/fantasma/fantasma-melon-derecha.gif"),
	// Default Skin
	FANTASMA_DEFAULT_ARRIBA("img/fantasma/fantasma-normal-arriba.gif"),
	FANTASMA_DEFAULT_ABAJO("img/fantasma/fantasma-normal-abajo.gif"),
	FANTASMA_DEFAULT_IZQUIERDA("img/fantasma/fantasma-normal-izquierda.gif"),
	FANTASMA_DEFAULT_DERECHA("img/fantasma/fantasma-normal-derecha.gif"),
	// Yin skin
	FANTASMA_YIN_ARRIBA("img/fantasma/fantasma-yin-arriba.gif"),
	FANTASMA_YIN_ABAJO("img/fantasma/fantasma-yin-abajo.gif"),
	FANTASMA_YIN_IZQUIERDA("img/fantasma/fantasma-yin-izquierda.gif"),
	FANTASMA_YIN_DERECHA("img/fantasma/fantasma-yin-derecha.gif"),
	//BOLITAS
	BOLITA ("img/bolitas/bolitaNormal.gif"),
	BOLITA_ESPECIAL ("img/bolitas/bolitaEspecial.gif"),
	//MAPA
	;
	//Nombre de los campos (en orden)
	private String valor;
	
	//Constructor
	private ConfiguracionSprites(String v){
		this.valor=v;
	}

	public String getValor(){
		return valor;
	}
}
