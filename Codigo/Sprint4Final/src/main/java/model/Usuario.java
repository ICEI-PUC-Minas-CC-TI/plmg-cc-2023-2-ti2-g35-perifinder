package model;

import java.time.LocalDate;

public class Usuario {
	private int id;
	private String nome;
	private String email;
	private LocalDate dat_nasc;
	private String senha;
	
	public Usuario(int id, String nome, String email,LocalDate dat_nasc, String senha) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.dat_nasc = dat_nasc;
		this.senha = senha;
	}
	
	public Usuario() {
		this.id = -1;
		this.nome = "";
		this.email = "";
		this.dat_nasc = LocalDate.now();
		this.senha = "";
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDat_nasc() {
		return dat_nasc;
	}

	public void setDat_nasc(LocalDate dat_nasc) {
		this.dat_nasc = dat_nasc;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email +
				", data de nascimento=" + dat_nasc + ", senha="+ senha +"]";
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Usuario) obj).getId());
	}

	

	
	
	
}