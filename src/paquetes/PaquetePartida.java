package paquetes;

import game.Partida;

public class PaquetePartida extends Paquete {
	private static final long serialVersionUID = 1980892811804533163L;
	private Partida partida;
	
	public PaquetePartida(Partida partida) {
		this.partida = partida;
	}
	public Partida getPartida() {
		return partida;
	}
	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.PARTIDA;
	}

}
