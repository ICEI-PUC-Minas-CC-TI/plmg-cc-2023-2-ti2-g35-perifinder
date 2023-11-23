package dao;

import model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends DAO {	
	public UsuarioDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	public boolean insert(Usuario usuario) {
	    boolean status = false;
	    try {
	        String sql = "INSERT INTO usuario (id, nome, email, dat_nasc, senha) "
	                   + "VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement st = conexao.prepareStatement(sql);
	        usuario.setId(getMaxId() + 1);
	        st.setInt(1, usuario.getId());  // Use setInt para o campo ID
	        st.setString(2, usuario.getNome());
	        st.setString(3, usuario.getEmail());
	        st.setDate(4, Date.valueOf(usuario.getDat_nasc()));
	        st.setString(5, usuario.getSenha());
	        st.executeUpdate();
	        st.close();
	        status = true;
	    } catch (SQLException u) {  
	        throw new RuntimeException(u);
	    }
	    return status;
	}
	
	public int getMaxId() {
        int maxId = -1; // Valor padrão caso não haja registros na tabela

        String query = "SELECT MAX(id) FROM usuario";

        try (PreparedStatement statement = conexao.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                maxId = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Trate ou registre exceções, se necessário
        }

        return maxId;
    }

	
	public Usuario get(int id) {
		Usuario usuario = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 usuario = new Usuario(rs.getInt("id"), rs.getString("nome"), rs.getString("email"), 
	                				   	rs.getDate("dat_nasc").toLocalDate(), rs.getString("senha"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuario;
	}
	
	
	public List<Usuario> get() {
		return get("");
	}

	
	public List<Usuario> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Usuario> getOrderByNome() {
		return get("nome");		
	}
	
	
	public List<Usuario> getOrderByDat_nasc() {
		return get("dat_nasc");		
	}
	
	
	private List<Usuario> get(String orderBy) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Usuario p = new Usuario(rs.getInt("id"), rs.getString("nome"), rs.getString("email"), 
    				   					rs.getDate("dat_nasc").toLocalDate(), rs.getString("senha"));
	            usuarios.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}
	
	
	public boolean update(Usuario usuario) throws Exception {
		boolean status = false;
		try {  
			String sql = "UPDATE usuario SET nome = ?, email = ?, dat_nasc = ?, senha = ? WHERE id = ?";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setString(1, usuario.getNome());
			st.setString(2, usuario.getEmail());
			st.setDate(3, Date.valueOf(usuario.getDat_nasc()));
			st.setString(4, usuario.getSenha());
			st.setInt(5, usuario.getId());
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM usuario WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean authenticate(String nome, String senha) {
	    try {
	        String sql = "SELECT COUNT(*) FROM usuario WHERE nome = ? AND senha = ?";
	        PreparedStatement st = conexao.prepareStatement(sql);
	        st.setString(1, nome);
	        st.setString(2, senha);
	        ResultSet rs = st.executeQuery();

	        if (rs.next()) {
	            int count = rs.getInt(1);
	            return count > 0; // Se o contador for maior que zero, o usuário e a senha correspondem
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }

	    return false;
	}

}