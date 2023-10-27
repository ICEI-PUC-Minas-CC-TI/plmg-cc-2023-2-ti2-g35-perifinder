package dao;

import model.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO extends DAO {	
	public ProdutoDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	public boolean insert(Produto produto) {
	    boolean status = false;
	    try {
	        String sql = "INSERT INTO produto (id, nome, descricao, imagem, categoria) "
	                   + "VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement st = conexao.prepareStatement(sql);
	        produto.setId(getMaxId() + 1);
	        System.out.print(getMaxId());
	        st.setInt(1, produto.getID());  // Use setInt para o campo ID
	        st.setString(2, produto.getNome());
	        st.setString(3, produto.getDescricao());
	        st.setString(4, produto.getImagem());
	        st.setString(5, produto.getCategoria());
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

        String query = "SELECT MAX(id) FROM produto";

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

	
	public Produto get(int id) {
		Produto produto = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 produto = new Produto(rs.getInt("id"), rs.getString("nome"),  rs.getString("descricao"), 
	                				    rs.getString("imagem"), 
	        			                rs.getString("categoria"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return produto;
	}
	
	
	public List<Produto> get() {
		return get("");
	}

	
	public List<Produto> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Produto> getOrderByNome() {
		return get("nome");		
	}
	
	
	public List<Produto> getOrderByCategoria() {
		return get("categoria");		
	}
	
	
	private List<Produto> get(String orderBy) {
		List<Produto> produtos = new ArrayList<Produto>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Produto p = new Produto(rs.getInt("id"), rs.getString("nome"),  rs.getString("descricao"), 
	                				    rs.getString("imagem"), 
	        			                rs.getString("categoria"));
	            produtos.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return produtos;
	}
	
	
	public boolean update(Produto produto) {
		boolean status = false;
		try {  
			String sql = "UPDATE produto SET nome = ?, descricao = ?, categoria = ? WHERE id = ?";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setString(1, produto.getNome());
			st.setString(2, produto.getDescricao());
			st.setString(3, produto.getCategoria());
			st.setInt(4, produto.getID()); // Substitua "getId()" pelo método apropriado que retorna o ID do produto
	
			int rowsUpdated = st.executeUpdate();
			if (rowsUpdated > 0) {
				status = true;
			}
			
			st.close();
		} catch (SQLException e) {  
			throw new RuntimeException(e);
		}
		return status;
	}
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM produto WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	public List<Produto> getProdutoPorNome(String nomePesquisa) {

        List<Produto> produtos = new ArrayList<Produto>();

        try {
            String sql = "SELECT * FROM produto";

            if (nomePesquisa != null && !nomePesquisa.isEmpty()) {
                sql += " WHERE descricao LIKE ?";
            }

            PreparedStatement preparedStatement = conexao.prepareStatement(sql);

            if (nomePesquisa != null && !nomePesquisa.isEmpty()) {
                preparedStatement.setString(1, "%" + nomePesquisa + "%");
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Produto p = new Produto(rs.getInt("id"), rs.getString("nome"),  rs.getString("descricao"), 
                        rs.getString("imagem"), 
                        rs.getString("categoria"));
                produtos.add(p);
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

	public List<Produto> getProdutoPorNome(String nomePesquisa, String orderBy) {
        List<Produto> produtos = new ArrayList<Produto>();

        try {
            String sql = "SELECT * FROM produto";

            if (nomePesquisa != null && !nomePesquisa.isEmpty()) {
                sql += " WHERE descricao LIKE ?";
            }

            if (orderBy != null && !orderBy.isEmpty()) {
                sql += " ORDER BY " + orderBy;
            }

            PreparedStatement preparedStatement = conexao.prepareStatement(sql);

            if (nomePesquisa != null && !nomePesquisa.isEmpty()) {
                preparedStatement.setString(1, "%" + nomePesquisa + "%");
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Produto p = new Produto(rs.getInt("id"), rs.getString("nome"),  rs.getString("descricao"), 
                                        rs.getString("imagem"), 
                                        rs.getString("categoria"));
                produtos.add(p);
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }
}