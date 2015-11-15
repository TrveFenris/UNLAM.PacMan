package paquetes;

public class PaqueteRegistro extends Paquete {
	private static final long serialVersionUID = -8912490712122305221L;
	private String nombreUsuario;
	private String password;
	private boolean resultado;
	
	public PaqueteRegistro(String user, String pass) {
		resultado = false;
		nombreUsuario = user;
		password = pass;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public String getPassword() {
		return password;
	}
	public boolean getResultado() {
		return resultado;
	}
	public void setResultado(boolean result) {
		resultado = result;
	}
	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.REGISTRO;
	}

}
