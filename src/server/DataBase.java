package server;

import java.sql.*;

import javax.swing.JOptionPane;

/**
 * Objeto que permite conectarse a una base de datos y realizar consultas sobre la misma
 */
public class DataBase {
	private Connection conexion = null;
	
	/**
	 * Conecta a la base de datos establecida por defecto
	 */
	private void Conectar(){	
		try{
			//cargar el driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=PACMAN";
		    String user = "sa";
		    String pass = "unlm2015";
		    conexion = DriverManager.getConnection(dbURL, user, pass);
			if(conexion!=null)
				System.out.println("Conexion exitosa");
		}
		catch(ClassNotFoundException ex){
			JOptionPane.showMessageDialog(null, ex, "Error1 en la Conexion con la BD "+ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion=null;
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex, "Error2 en la Conexion con la BD "+ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        	conexion=null;
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex, "Error3 en la Conexion con la BD "+ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion=null;
        }
	}
	
	/**
	 * Ejecuta una consulta en la base de datos
	 * @param SQL
	 * @return si la cunsulta fue exitosa o no.
	 */
	public boolean Consultar(String SQL){
		//primero establecer la coneccion
		this.Conectar();
		ResultSet rs=null;
		Statement sentencia=null;
		boolean estado = false;
		try{
			sentencia=conexion.createStatement();
			rs=sentencia.executeQuery(SQL);
			while(rs.next())
				estado=true;
		}
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null,"No se pudo lograr la conexion con la base de datos","Error",JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null,"Error logico","Error",JOptionPane.ERROR_MESSAGE);
		}finally {
			try {
			sentencia.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		return estado;
	}
	
	/**
	 * @param usuario
	 * @param password
	 * @return Si la combinacion de usuario+password es valida o no.
	 */
	public boolean verificarDatos(String usuario, String password){
		String sql = "SELECT * FROM USUARIO WHERE Usuario='"+usuario+"' AND Password='"+password+"'";
		boolean estado= false;
		
		try{
			estado = this.Consultar(sql);
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null,"Error logico","Error",JOptionPane.ERROR_MESSAGE);
		}
		return estado;
	}
	
	
	/**
	 * @param usuario
	 * @return si el usuario existe o no en la base de datos.
	 */
	private boolean verificarUsuario(String usuario){
		String sql = "SELECT * FROM USUARIO WHERE Usuario='"+usuario+"'";
		boolean estado= false;
		
		try{
			estado = this.Consultar(sql);
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null,"Error no de la base de datos","Error",JOptionPane.ERROR_MESSAGE);
		}
		return estado;
	}
	
	/**
	 * Registra un usuario en la base de datos
	 * @param usuario
	 * @param password
	 */
	public boolean registrarUsuario( String usuario, String password) {
			PreparedStatement pstmt = null;
			this.Conectar();
			if(verificarUsuario(usuario)==false)
				return false;
			try {
					pstmt = conexion.prepareStatement("Insert into Usuario values(?, ?)");
					pstmt.setString(1, usuario);
					pstmt.setString(2, password);
					pstmt.execute();			
				} catch(SQLException sqle) {
					sqle.printStackTrace();
					JOptionPane.showMessageDialog(null,"No se pudo lograr la coneccion con la base de datos","Error",JOptionPane.ERROR_MESSAGE);
				} finally {
					try {
					pstmt.close();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,"Error no de la base de datos","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			return true;
		}
}
