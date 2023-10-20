import java.util.Objects;

public class Pesquisa {
    private int id;
    private String nome;
    private String tipo;
    private int nivel;

    public Pesquisa() {
        id = -1;
        nome = "";
        tipo = "";
        nivel = 1; // Nível inicial padrão é 1
    }

    public Pesquisa(int id, String nome, String tipo, int nivel) {
        setId(id);
        setNome(nome);
        setTipo(tipo);
        setNivel(nivel);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        if (nivel >= 1) {
            this.nivel = nivel;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pesquisa pesquisa = (Pesquisa) o;
        return id == pesquisa.id &&
                nivel == pesquisa.nivel &&
                Objects.equals(nome, pesquisa.nome) &&
                Objects.equals(tipo, pesquisa.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, tipo, nivel);
    }

    @Override
    public String toString() {
        return "Pesquisa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", nivel=" + nivel +
                '}';
    }
}
