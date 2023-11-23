package model;
//novo
/*
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
*/

public class Produto {
	private int id;
	private String nome;
	private String descricao;
	private String imagem;
	private String categoria;
	
	
	
	public Produto() {
		id = -1;
		nome = "";
		descricao = "";
		imagem = "";
		categoria = "";
	}

	public Produto(int id, String nome, String descricao, String imagem, String categoria) {
		setId(id);
		setNome(nome);
		setDescricao(descricao);
		setImagem(imagem);
		setCategoria(categoria);
	}		
	
	public int getID() {
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}





	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Produto: " + nome + "   Descrição: " + descricao + "   Imagem: " + imagem + "   Categoria: "
				+ categoria;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getID() == ((Produto) obj).getID());
	}	
}