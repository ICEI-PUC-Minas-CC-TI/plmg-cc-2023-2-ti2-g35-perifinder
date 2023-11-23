package model;

public class Precos {
    private int id;
    private int idProduto;
    private String nomeLoja;
    private String imagemLoja;
    private float preço;

    public Precos(){
        this(-1, -1, "", "", 0);
    }

    public Precos(int id, int idProduto, String nomeLoja, String imagemLoja, float preço) {
        this.id = id;
        this.idProduto = idProduto;
        this.nomeLoja = nomeLoja;
        this.imagemLoja = imagemLoja;
        this.preço = preço;
    }

    public int getID() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setNomeLoja(String nomeLoja) {
		this.nomeLoja = nomeLoja;
	}

    public String getNomeLoja() {
		return nomeLoja;
	}

	public String getImagemLoja() {
		return imagemLoja;
	}

	public void setPreço(Float preço) {
		this.preço = preço;
	}

	public float getPreço() {
		return preço;
	}

    @Override
	public String toString() {
		return "Loja: " + nomeLoja + "   Imagem: " + imagemLoja + "   Preço: " + preço;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getID() == ((Produto) obj).getID());
	}	
}
