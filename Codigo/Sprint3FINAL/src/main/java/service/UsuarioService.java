package service;

import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
import dao.DAO;
import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;

public class UsuarioService {

	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_AUTHENTICATE = 2;
	private final int FORM_ORDERBY_NOME = 2;
	
	
	public UsuarioService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Usuario(), FORM_ORDERBY_NOME);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_AUTHENTICATE, new Usuario(), orderBy);
	}

	
	public void makeForm(int tipo, Usuario usuario, int orderBy) {
		String nomeArquivo = "CadastroLogin.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umUsuario = "";
	
		if(orderBy == FORM_INSERT || orderBy == FORM_AUTHENTICATE) {
			String action = "/usuario/";
			String name, nome, buttonLabel;
			if (orderBy == FORM_INSERT){
			action += "insert";
			name = "Cadastro de Usuários";
			nome = "";
			buttonLabel = "Criar";
			
			umUsuario += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umUsuario += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ nome +"\"></td>";
			umUsuario += "\t\t\t<td>Email: <input class=\"input--register\" type=\"text\" name=\"email\" value=\""+ usuario.getEmail() +"\"></td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td>Data de Nascimento: <input class=\"input--register\" type=\"date\" name=\"dat_nasc\" value=\""+ usuario.getDat_nasc() + "\"></td>";
			umUsuario += "\t\t\t<td>Senha: <input class=\"input--register\" type=\"password\" name=\"senha\" value=\""+ usuario.getSenha() +"\"></td>";
			umUsuario += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>"; // Adicionar o href index
			umUsuario += "\t\t\t<td align=\"left\"><font \"><b>&nbsp;&nbsp;&nbsp;<a href=\"/usuario/cadastro/2\"\">Fazer Login</a></b></font></td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t</table>";
			umUsuario += "\t</form>";
			
		} else if (orderBy == FORM_AUTHENTICATE) { 
			action += "authenticate";
			name = "Login";
			buttonLabel = "Entrar";
			umUsuario += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umUsuario += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ usuario.getNome() +"\"></td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td>Senha: <input class=\"input--register\" type=\"password\" name=\"senha\" value=\""+ usuario.getSenha() +"\"></td>";
			umUsuario += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>"; // Adicionar o href index
			umUsuario += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/usuario/cadastro/1\"\">Cadastro</a></b></font></td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t</table>";
			umUsuario += "\t</form>";
		}else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-PRODUTO>", umUsuario);
					
		}
	}
	
	public Object insert(Request request, Response response) throws Exception {
		
		String nome = request.queryParams("nome");
		String email = request.queryParams("email");
		LocalDate dat_nasc = LocalDate.parse(request.queryParams("dat_nasc"));
		String senha = DAO.toMD5(request.queryParams("senha")); // Criptografando a senha
		
		String resp = "";
		
		Usuario usuario = new Usuario(-1, nome, email, dat_nasc, senha);
		
		if(usuarioDAO.insert(usuario) == true) {
            resp = "Usuario (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Usuario (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
	
//	public Object get(Request request, Response response) {
//		int id = Integer.parseInt(request.params(":id"));		
//		Usuario usuario = (Usuario) usuarioDAO.get(id);
//		
//		if (usuario != null) {
//			response.status(200); // success
//			makeForm(FORM_DETAIL, usuario, FORM_ORDERBY_NOME);
//        } else {
//            response.status(404); // 404 Not found
//            String resp = "Usuario " + id + " não encontrado.";
//    		makeForm();
//    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
//        }
//
//		return form;
//	}

	
//	public Object getToUpdate(Request request, Response response) {
//		int id = Integer.parseInt(request.params(":id"));		
//		Usuario usuario = (Usuario) usuarioDAO.get(id);
//		
//		if (usuario != null) {
//			response.status(200); // success
//			makeForm(FORM_UPDATE, usuario, FORM_ORDERBY_NOME);
//        } else {
//            response.status(404); // 404 Not found
//            String resp = "Usuario " + id + " não encontrado.";
//    		makeForm();
//    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
//        }
//
//		return form;
//	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) throws Exception{
        int id = Integer.parseInt(request.params(":id"));
		Usuario usuario = usuarioDAO.get(id);
        String resp = "";       

        if (usuario != null) {
        	usuario.setNome(request.queryParams("nome"));
        	usuario.setEmail(request.queryParams("email"));
        	usuario.setDat_nasc(LocalDate.parse(request.queryParams("dat_nasc")));
        	usuario.setSenha(DAO.toMD5(request.queryParams("senha")));
        	usuarioDAO.update(usuario);
        	response.status(200); // success
            resp = "Usuario (ID " + usuario.getId() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuario (ID " + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Usuario usuario = usuarioDAO.get(id);
        String resp = "";       

        if (usuario != null) {
            usuarioDAO.delete(id);
            response.status(200); // success
            resp = "Usuario (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuario (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
	
	public Object authenticate(Request request,Response response) throws Exception {
		String nome = request.queryParams("nome"); // Recebe o nome do formulário
	    String senha = DAO.toMD5(request.queryParams("senha")); // Recebe a senha do formulário
	    String resp = "";
	    
	    if (nome != null && senha != null) {
	        if (usuarioDAO.authenticate(nome, senha)) {
	            response.status(200); // Sucesso
	            resp = "Autenticação bem-sucedida!";
	        } else {
	            response.status(401); // Não autorizado
	            resp = "Nome de usuário ou senha incorretos";
	        }
	    } else {
	        response.status(400); // Solicitação inválida
	        resp = "Os campos de nome de usuário e senha são obrigatórios";
	    }
	    makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">")
    + "<script>" + "if (confirm('"+ resp +"')) {" +"window.location.href = 'http://127.0.0.1:5500/src/main/resources/public/index.html';" + "}" + "</script>";
	}
}
