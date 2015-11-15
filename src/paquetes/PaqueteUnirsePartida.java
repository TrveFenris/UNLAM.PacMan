package paquetes;

public class PaqueteUnirsePartida extends Paquete {
	private static final long serialVersionUID = 529617164265803804L;

	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.UNIRSE_PARTIDA;
	}

}
