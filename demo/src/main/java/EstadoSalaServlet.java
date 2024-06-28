import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/EstadoSalaServlet")
public class EstadoSalaServlet extends HttpServlet {

    private static final Map<String, SalaEstado> salasEstados = new HashMap<>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codigoSala = request.getParameter("codigoSala");
        String accion = request.getParameter("accion");

        SalaEstado estado;
        synchronized (salasEstados) {
            if (!salasEstados.containsKey(codigoSala)) {
                estado = new SalaEstado();
                salasEstados.put(codigoSala, estado);
            } else {
                estado = salasEstados.get(codigoSala);
            }
        }

        synchronized (estado) {
            switch (accion) {
                case "unirse":
                    estado.nuevoUsuario();
                    break;
                case "listo":
                    estado.marcarListo();
                    break;
                case "noListo":
                    estado.desmarcarListo();
                    break;
                default:
                    // Manejar otro tipo de acciones si es necesario
                    break;
            }
        }

        // Redirigir a esperaSala.jsp
        response.sendRedirect("unirseSala.jsp");
    }

    private static class SalaEstado {
        private int totales = 0;
        private int listos = 0;

        public synchronized void nuevoUsuario() {
            totales++;
        }

        public synchronized void marcarListo() {
            listos++;
        }

        public synchronized void desmarcarListo() {
            if (listos > 0) {
                listos--;
            }
        }

        public synchronized int getTotales() {
            return totales;
        }

        public synchronized int getListos() {
            return listos;
        }

        @Override
        public String toString() {
            return "SalaEstado{ totales=" + totales + ", listos=" + listos + " }";
        }
    }
}
