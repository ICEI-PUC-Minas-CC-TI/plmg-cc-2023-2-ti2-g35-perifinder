import static spark.Spark.*;
import service.PesquisaService;

public class Aplicacao {
	
	private static PesquisaService pesquisaService = new PesquisaService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/pesquisa/insert", (request, response) -> pesquisaService.insert(request, response));

        get("/pesquisa/:id", (request, response) -> pesquisaService.get(request, response));
        
        get("/pesquisa/list/:orderby", (request, response) -> pesquisaService.getAll(request, response));

        get("/pesquisa/update/:id", (request, response) -> pesquisaService.getToUpdate(request, response));
        
        post("/pesquisa/update/:id", (request, response) -> pesquisaService.update(request, response));
           
        get("/pesquisa/delete/:id", (request, response) -> pesquisaService.delete(request, response));
    }
}
