package paquetes;

public class PaqueteID extends Paquete{
	private static final long serialVersionUID = 2801296456944192454L;
	private int ID;
	public PaqueteID(int id) {
		this.ID = id;
	}
	public int getID() {
		return ID;
	}
	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.ID;
	}
}
