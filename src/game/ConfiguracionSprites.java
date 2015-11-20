package game;

import gameobject.Direcciones;

import java.util.ArrayList;

public enum ConfiguracionSprites {
	/* PACMAN */
	
	// BowWow Skin
	PACMAN_BOWWOW("img/pacman/pacman-bowwow-arriba.gif", "img/pacman/pacman-bowwow-abajo.gif",
	"img/pacman/pacman-bowwow-izquierda.gif", "img/pacman/pacman-bowwow-derecha.gif"),
	// Brasil Skin
	PACMAN_BRASIL("img/pacman/pacman-brasil-arriba.gif", "img/pacman/pacman-brasil-abajo.gif",
	"img/pacman/pacman-brasil-izquierda.gif", "img/pacman/pacman-brasil-derecha.gif"),
	// Loco Skin
	PACMAN_LOCO("img/pacman/pacman-loco-arriba.gif", "img/pacman/pacman-loco-abajo.gif",
	"img/pacman/pacman-loco-izquierda.gif", "img/pacman/pacman-loco-derecha.gif"),
	// Malvado Skin
	PACMAN_MALVADO("img/pacman/pacman-malvado-arriba.gif", "img/pacman/pacman-malvado-abajo.gif",
	"img/pacman/pacman-malvado-izquierda.gif", "img/pacman/pacman-malvado-derecha.gif"),
	// Melon Skin
	PACMAN_MELON("img/pacman/pacman-melon-arriba.gif", "img/pacman/pacman-melon-abajo.gif",
	"img/pacman/pacman-melon-izquierda.gif", "img/pacman/pacman-melon-derecha.gif"),
	// Default Skin
	PACMAN_DEFAULT("img/pacman/pacman-normal-arriba.gif", "img/pacman/pacman-normal-abajo.gif",
	"img/pacman/pacman-normal-izquierda.gif", "img/pacman/pacman-normal-derecha.gif"),
	// Yin skin
	PACMAN_YIN("img/pacman/pacman-yin-arriba.gif", "img/pacman/pacman-yin-abajo.gif",
	"img/pacman/pacman-yin-izquierda.gif", "img/pacman/pacman-yin-derecha.gif"),
	
	/* FANTASMA */
	
	// BowWow Skin
	FANTASMA_BOWWOW("img/fantasma/fantasma-bowwow-arriba.gif", "img/fantasma/fantasma-bowwow-abajo.gif",
	"img/fantasma/fantasma-bowwow-izquierda.gif", "img/fantasma/fantasma-bowwow-derecha.gif"),
	// Brasil Skin
	FANTASMA_BRASIL("img/fantasma/fantasma-brasil-arriba.gif", "img/fantasma/fantasma-brasil-abajo.gif",
	"img/fantasma/fantasma-brasil-izquierda.gif", "img/fantasma/fantasma-brasil-derecha.gif"),
	// Loco Skin
	FANTASMA_LOCO("img/fantasma/fantasma-loco-arriba.gif",
	"img/fantasma/fantasma-loco-abajo.gif",
	"img/fantasma/fantasma-loco-izquierda.gif",
	"img/fantasma/fantasma-loco-derecha.gif"),
	// Malvado Skin
	FANTASMA_MALVADO("img/fantasma/fantasma-malvado-arriba.gif", "img/fantasma/fantasma-malvado-abajo.gif",
	"img/fantasma/fantasma-malvado-izquierda.gif", "img/fantasma/fantasma-malvado-derecha.gif"),
	// Melon Skin
	FANTASMA_MELON("img/fantasma/fantasma-melon-arriba.gif", "img/fantasma/fantasma-melon-abajo.gif",
	"img/fantasma/fantasma-melon-izquierda.gif", "img/fantasma/fantasma-melon-derecha.gif"),
	// Default Skin
	FANTASMA_DEFAULT("img/fantasma/fantasma-normal-arriba.gif", "img/fantasma/fantasma-normal-abajo.gif",
	"img/fantasma/fantasma-normal-izquierda.gif", "img/fantasma/fantasma-normal-derecha.gif"),
	// Yin skin
	FANTASMA_YIN("img/fantasma/fantasma-yin-arriba.gif", "img/fantasma/fantasma-yin-abajo.gif",
	"img/fantasma/fantasma-yin-izquierda.gif", "img/fantasma/fantasma-yin-derecha.gif"),
	//BOLITAS
	BOLITA ("img/bolitas/bolitaNormal.gif"),
	BOLITA_ESPECIAL ("img/bolitas/bolitaEspecial.gif"),
	//MAPA
	//ICONOS
	ICONO_READY("img/icons/check.gif"),
	ICONO_NOTREADY("img/icons/cancel-icon.gif"),
	;

	private ArrayList<String> valores = new ArrayList<String>();
	//Constructor
	private ConfiguracionSprites(String v) {
		valores.add(v);
	}
	//Constructor sobrecargado con direcciones
	private ConfiguracionSprites(String arriba, String abajo, String izquierda, String derecha) {
		valores.add(arriba);
		valores.add(abajo);
		valores.add(izquierda);
		valores.add(derecha);
	}
	public String getValor() {
		return valores.get(0);
	}
	public String getValor(Direcciones direccion) {
		return valores.get(direccion.getValor());
	}
}
