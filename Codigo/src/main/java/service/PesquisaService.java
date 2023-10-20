import dao.DAO;
import model.Pesquisa;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PesquisaService {

    private DAO pesquisaDAO = new DAO();

    public PesquisaService() {
        setupRoutes();
    }

    private void setupRoutes() {
        Spark.staticFileLocation("/public");
        Spark.port(8080);

        Spark.get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Pesquisa> allPesquisa = pesquisaDAO.getAll();
            model.put("pesquisaList", allPesquisa);
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, "templates/index.vm")
            );
        });

        Spark.get("/pesquisa/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(request.params(":id"));
            Pesquisa pesquisa = pesquisaDAO.get(id);
            model.put("pesquisa", pesquisa);
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, "templates/detail.vm")
            );
        });

        Spark.post("/pesquisa", (request, response) -> {
            String name = request.queryParams("name");
            if (name != null && !name.isEmpty()) {
                Pesquisa newPesquisa = new Pesquisa(name);
                pesquisaDAO.insert(newPesquisa);
            }
            response.redirect("/");
            return null;
        });

        Spark.post("/pesquisa/:id/update", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            Pesquisa pesquisa = pesquisaDAO.get(id);
            if (pesquisa != null) {
                String newName = request.queryParams("newName");
                if (newName != null && !newName.isEmpty()) {
                    pesquisa.setName(newName);
                    pesquisaDAO.update(pesquisa);
                }
            }
            response.redirect("/");
            return null;
        });

        Spark.get("/pesquisa/:id/delete", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            Pesquisa pesquisa = pesquisaDAO.get(id);
            if (pesquisa != null) {
                pesquisaDAO.delete(id);
            }
            response.redirect("/");
            return null;
        });
    }

    public static void main(String[] args) {
        new PesquisaService();
    }
}
