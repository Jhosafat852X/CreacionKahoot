import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.bson.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet("/CrearPreguntaServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 10,  // Umbral de tamaño de archivo: 10 KB
        maxFileSize = 1024 * 1024,      // Tamaño máximo del archivo individual: 1 MB
        maxRequestSize = 1024 * 1024 * 5 // Tamaño máximo total de solicitud: 5 MB
)
public class CrearPreguntaServlet extends HttpServlet {
    private CapaDeNegocioMongoSalas CNMS;

    @Override
    public void init() throws ServletException {
        super.init();
        CNMS = new CapaDeNegocioMongoSalas();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Obtener parámetros de texto directamente
            String salaId = request.getParameter("salaId");
            System.out.println("salaId: " + salaId);

            String pregunta = request.getParameter("pregunta");
            System.out.println("pregunta: " + pregunta);

            int tiempo = Integer.parseInt(request.getParameter("tiempo"));
            System.out.println("tiempo: " + tiempo);

            int puntos = Integer.parseInt(request.getParameter("puntos"));
            System.out.println("puntos: " + puntos);

            String respuesta1 = request.getParameter("respuesta1");
            System.out.println("respuesta1: " + respuesta1);

            String respuesta2 = request.getParameter("respuesta2");
            System.out.println("respuesta2: " + respuesta2);

            String respuesta3 = request.getParameter("respuesta3");
            System.out.println("respuesta3: " + respuesta3);

            String respuesta4 = request.getParameter("respuesta4");
            System.out.println("respuesta4: " + respuesta4);

            String respuestaCorrecta = request.getParameter("respuestaCorrecta");
            System.out.println("respuestaCorrecta: " + respuestaCorrecta);

            // Obtener el archivo subido
            Part filePart = request.getPart("archivo");
            String archivoNombre = null;
            if (filePart != null && filePart.getSize() > 0) {
                archivoNombre = filePart.getSubmittedFileName();

                // Guardar el archivo en la ruta específica
                String rutaGuardado = "C:\\Users\\giova\\IdeaProjects\\CreacionKahoot\\demo\\src\\main\\webapp\\Imagenes\\" + archivoNombre;
                Files.copy(filePart.getInputStream(), Paths.get(rutaGuardado));

                System.out.println("Archivo guardado en: " + rutaGuardado);
            }

            // Generar un UUID único para la pregunta
            String preguntaId = UUID.randomUUID().toString();

            // Guardar la pregunta en la base de datos o donde sea necesario
            CNMS.guardarPregunta(salaId, preguntaId, pregunta, archivoNombre, respuesta1, respuesta2, respuesta3, respuesta4, respuestaCorrecta, tiempo, puntos);

            // Mostrar una alerta de éxito (esto se manejará en el front-end con JavaScript)
            response.getWriter().println("<script>alert('Pregunta añadida correctamente');</script>");

            // Redirigir de vuelta a crearPregunta.jsp para añadir otra pregunta
            response.sendRedirect(request.getContextPath() + "/crearPregunta.jsp?salaId=" + salaId);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar la pregunta: " + e.getMessage());
        }
    }
}
