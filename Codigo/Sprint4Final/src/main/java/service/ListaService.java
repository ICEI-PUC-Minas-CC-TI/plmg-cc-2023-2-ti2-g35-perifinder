package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.ProdutoDAO;
import model.Produto;
import spark.Request;
import spark.Response;


public class ListaService {
    
	private ProdutoDAO produtoDAO = new ProdutoDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_NOME = 2;
	private final int FORM_ORDERBY_CATEGORIA = 3;
	
	public ListaService() {
		makeForm();
	}

	//private Connection conexao;
	
	public void makeForm() {
		makeForm(FORM_INSERT, new Produto(), FORM_ORDERBY_NOME);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Produto(), orderBy);
	}

	
	public void makeForm(int tipo, Produto produto, int orderBy) {
		String nomeArquivo = "lista.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		/*String umProduto = "";
		if(tipo != FORM_INSERT) {
			umProduto += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/produto/list/1\">Novo Produto</a></b></font></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t</table>";
			umProduto += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/produto/";
			String name, nome, descricao, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Produto";
				nome = "";
				descricao = "";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + produto.getID();
				name = "Atualizar Produto (ID " + produto.getID() + ")";
				nome = produto.getNome();
				descricao = produto.getDescricao();
				buttonLabel = "Atualizar";
			}
			umProduto += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umProduto += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ nome +"\"></td>";
			umProduto += "\t\t\t<td>&nbsp;Descrição: <input class=\"input--register\" type=\"text\" name=\"descricao\" value=\""+ descricao +"\"></td>";
			umProduto += "\t\t\t<td>&nbsp;Categoria: <input class=\"input--register\" type=\"text\" name=\"categoria\" value=\""+ produto.getCategoria().toString() + "\"></td>";
			umProduto += "\t\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t</tr>";

			

			umProduto += "\t</table>";
			umProduto += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umProduto += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Produto (ID " + produto.getID() + ")</b></font></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;Nome: "+ produto.getNome() +"</td>";
			umProduto += "\t\t\t<td>&nbsp;Descrição: "+ produto.getDescricao() +"</td>";
			umProduto += "\t\t\t<td>Categoria: "+ produto.getCategoria() +"</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-PRODUTO>", umProduto);*/
		
		String lista = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		lista += "\n<tr>\n" +
        		"\t<td><a href=\"/produto/lista/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
        		"\t<td><a href=\"/produto/lista/" + FORM_ORDERBY_CATEGORIA + "\"><b>Categoria</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"</tr>\n";
		
		List<Produto> produtos;
		if (orderBy == FORM_ORDERBY_NOME) {		produtos = produtoDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_CATEGORIA) {			produtos = produtoDAO.getOrderByCategoria();
		} else {											produtos = produtoDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Produto p : produtos) {
			bgcolor = (i++ % 2 == 0) ? "#fff3f4" : "#ffffff";
			lista += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td>" + p.getCategoria() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n";
		}
		lista += "</table>";		
		form = form.replaceFirst("<LISTAR-PRODUTO>", lista);				
	}
	
	public Object insert(Request request, Response response) {

		String nome = request.queryParams("nome");
		String descricao = request.queryParams("descricao");
	    String imagem = request.queryParams("imagem");
	    String categoria = request.queryParams("categoria");

		String resp = "";
		
		Produto produto = new Produto(-1, nome, descricao, imagem, categoria);
		
		if(produtoDAO.insert(produto) == true) {
            resp = "Produto (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Produto (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Produto produto = (Produto) produtoDAO.get(id);
		
		if (produto != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, produto, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Produto " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Produto produto = (Produto) produtoDAO.get(id);
		
		if (produto != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, produto, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Produto " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Produto produto = produtoDAO.get(id);
        String resp = "";       

        if (produto != null) {
			produto.setNome(request.queryParams("nome"));
        	produto.setDescricao(request.queryParams("descricao"));
			produto.setCategoria(request.queryParams("categoria"));
        	
        	produtoDAO.update(produto);
        	response.status(200); // success
            resp = "Produto (ID " + produto.getID() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Produto (ID \" + produto.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Produto produto = produtoDAO.get(id);
        String resp = "";       

        if (produto != null) {
            produtoDAO.delete(id);
            response.status(200); // success
            resp = "Produto (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Produto (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

    public Object getProdutoPorNome(Request request, Response response) {
        String nomePesquisa = request.queryParams("nome"); // Recupera o parâmetro de consulta "nome"
        String orderBy = request.queryParams("orderBy"); // Opcional: Recupera o parâmetro de consulta "orderBy"
        String list = "";

        List<Produto> produtos = produtoDAO.getProdutoPorNome(nomePesquisa, orderBy);

        list += "\n<tr>\n" +
                "\t<td><a href=\"/produto/listar/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
                "\t<td><a href=\"/produto/listar/" + FORM_ORDERBY_CATEGORIA + "\"><b>Categoria</b></a></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
                "</tr>\n";
        int i = 0;
        String bgcolor = "";
        for (Produto p : produtos) {
            bgcolor = (i++ % 2 == 0) ? "#fff3f4" : "#ffffff";
            list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
                      "\t<td>" + p.getNome() + "</td>\n" +
                      "\t<td>" + p.getCategoria() + "</td>\n" +
                      "\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n";
        }
        list += "</table>";
         return form = form.replaceFirst("<LISTAR-PRODUTO>", list);
    }
}