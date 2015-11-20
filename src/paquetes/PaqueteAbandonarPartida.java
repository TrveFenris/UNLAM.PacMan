package paquetes;

public class PaqueteAbandonarPartida extends Paquete{

	private static final long serialVersionUID = 2236084928910201115L;
	
	public PaqueteAbandonarPartida() {
	}
	
	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.ABANDONAR_PARTIDA;
	}
}
