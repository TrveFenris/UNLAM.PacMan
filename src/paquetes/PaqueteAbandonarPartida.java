package paquetes;

public class PaqueteAbandonarPartida extends Paquete {

	private static final long serialVersionUID = 6799481996971774797L;
	private boolean pacman;

	public PaqueteAbandonarPartida() {
		pacman=false;
	}
	
	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.ABANDONAR_PARTIDA;
	}
	
	public void setPacman(){
		pacman=true;
	}
	
	public boolean isPacman(){
		return pacman;
	}

}
