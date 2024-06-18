import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID; // Para generar identificadores únicos

@WebServlet("/CrearSalaServlet")
public class CrearSalaServlet extends HttpServlet {

    private CapaDeNegocioMongoSalas CNMS;

    public void init() {
        CNMS = new CapaDeNegocioMongoSalas();
    }

    // CrearSalaServlet.java

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String nombreSala = request.getParameter("nombreSala");
            String codigoSala = request.getParameter("codigoSala");
            String creadorId = request.getParameter("creadorId");

            // Aquí puedes agregar impresiones para verificar los valores obtenidos
            System.out.println("Nombre de la sala: " + nombreSala);
            System.out.println("Código de la sala: " + codigoSala);
            System.out.println("ID del creador: " + creadorId);

            // Generar un UUID único para la sala y crearla
            String salaId = UUID.randomUUID().toString();
            CNMS.crearSala(nombreSala, codigoSala, creadorId, salaId);

            // Redirigir a la página para crear preguntas
            response.sendRedirect(request.getContextPath() + "/crearPregunta.jsp?salaId=" + salaId);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Datos inválidos proporcionados: " + e.getMessage());
        }
    }


}
