package windows;

import java.sql.*;

import javax.swing.JOptionPane;

public class BaseDatos {
	private Connection conexion = null;
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
	
	public boolean Verificar(String usuario, String password){
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
	
	public boolean VerificarUsuario(String usuario){
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
	
	public void agregar( String usuario, String password) {
			PreparedStatement pstmt = null;
			this.Conectar();
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
		}
}
