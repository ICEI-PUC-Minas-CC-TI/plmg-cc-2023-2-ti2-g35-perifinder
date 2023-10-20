import model.Pesquisa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAO extends DAO {
    public DAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public boolean insert(Pesquisa pesquisa) {
        boolean status = false;
        try {
            String sql = "INSERT INTO pesquisa (nome, tipo, nivel) VALUES (?, ?, ?)";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, pesquisa.getNome());
            st.setString(2, pesquisa.getTipo());
            st.setInt(3, pesquisa.getNivel());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public Pesquisa get(int id) {
        Pesquisa pesquisa = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM pesquisa WHERE id=" + id;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                pesquisa = new Pesquisa(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("tipo"),
                    rs.getInt("nivel")
                );
            }
            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return pesquisa;
    }

    public List<Pesquisa> getAll() {
        List<Pesquisa> pesquisas = new ArrayList<>();

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM pesquisa";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Pesquisa pesquisa = new Pesquisa(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("tipo"),
                    rs.getInt("nivel")
                );
                pesquisas.add(pesquisa);
            }
            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return pesquisas;
    }

    public boolean update(Pesquisa pesquisa) {
        boolean status = false;
        try {
            String sql = "UPDATE pesquisa SET nome = ?, tipo = ?, nivel = ? WHERE id = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, pesquisa.getNome());
            st.setString(2, pesquisa.getTipo());
            st.setInt(3, pesquisa.getNivel());
            st.setInt(4, pesquisa.getId());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public boolean delete(int id) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM pesquisa WHERE id = " + id);
            st.close();
            status = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }
}
