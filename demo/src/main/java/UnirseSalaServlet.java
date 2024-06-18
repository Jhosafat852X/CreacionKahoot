import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/UnirseSalaServlet")
public class UnirseSalaServlet extends HttpServlet {
    private CapaDeNegocioMongoSalas CNMS;

    @Override
    public void init() throws ServletException {
        CNMS = new CapaDeNegocioMongoSalas();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codigoSala = request.getParameter("codigoSala");

        if (CNMS.salaExiste(codigoSala)) {
            // Sala existe, redirige a la página de espera
            response.sendRedirect(request.getContextPath() + "/esperaSala.jsp?codigoSala=" + codigoSala);
        } else {
            // Sala no existe, redirige al formulario de unirse a la sala con mensaje de error
            request.setAttribute("error", "El código de sala no existe.");
            request.getRequestDispatcher("/unirseSala.jsp").forward(request, response);
        }
    }
}
