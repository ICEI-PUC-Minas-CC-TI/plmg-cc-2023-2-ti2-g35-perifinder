package app;

import static spark.Spark.*;
import service.ProdutoService;
import service.UsuarioService;
import service.ListaService;

public class Aplicacao {
	
	private static ProdutoService produtoService = new ProdutoService();
	private static UsuarioService usuarioService = new UsuarioService();
    private static ListaService listaService = new ListaService();

    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/produto/insert", (request, response) -> produtoService.insert(request, response));

        get("/produto/:id", (request, response) -> produtoService.get(request, response));
        
        get("/produto/list/:orderby", (request, response) -> produtoService.getAll(request, response));

        get("/produto/update/:id", (request, response) -> produtoService.getToUpdate(request, response));
        
        post("/produto/update/:id", (request, response) -> produtoService.update(request, response));
           
        get("/produto/delete/:id", (request, response) -> produtoService.delete(request, response));

        //Lista Service

        post("/produto/insert", (request, response) -> listaService.insert(request, response));

        get("/produto/:id", (request, response) -> listaService.get(request, response));
        
        get("/produto/lista/:orderby", (request, response) -> listaService.getAll(request, response));

        get("/produto/update/:id", (request, response) -> listaService.getToUpdate(request, response));
        
        post("/produto/update/:id", (request, response) -> listaService.update(request, response));
           
        get("/produto/delete/:id", (request, response) -> listaService.delete(request, response));

        get("/produto/listar/:id", (request, response) -> listaService.getProdutoPorNome(request, response));

        //Usuario Service

        post("/usuario/insert", (request, response) -> usuarioService.insert(request, response));

        get("/usuario/cadastro/:orderby", (request, response) -> usuarioService.getAll(request, response));

        post("/usuario/update/:id", (request, response) -> usuarioService.update(request, response));

        get("/usuario/delete/:id", (request, response) -> usuarioService.delete(request, response));

        post("/usuario/authenticate", (request, response) -> usuarioService.authenticate(request, response)); 
    }
}