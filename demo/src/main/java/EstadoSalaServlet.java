import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/EstadoSalaServlet")
public class EstadoSalaServlet extends HttpServlet {
    private Map<String, SalaEstado> salasEstados = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codigoSala = obtenerCodigoSalaDesdeCookie(request);

        String requestId = UUID.randomUUID().toString(); // Genera un UUID único para la solicitud
        System.out.println("Inicio de doGet(), solicitud ID: " + requestId);
        System.out.println("Código Sala obtenido: " + codigoSala);

        SalaEstado estado;
        synchronized (salasEstados) {
            estado = salasEstados.get(codigoSala);
            if (estado == null) {
                estado = new SalaEstado();
                salasEstados.put(codigoSala, estado);
                System.out.println("Nuevo estado creado y añadido a salasEstados.");
            } else {
                System.out.println("Estado obtenido de salasEstados: " + estado);
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(estado));
        System.out.println("Respuesta JSON enviada: " + new Gson().toJson(estado));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codigoSala = request.getParameter("codigoSala");
        String accion = request.getParameter("accion");

        String requestId = UUID.randomUUID().toString(); // Genera un UUID único para la solicitud
        System.out.println("Inicio de doPost(), solicitud ID: " + requestId);
        System.out.println("Código Sala recibido: " + codigoSala + ", Acción: " + accion);

        SalaEstado estado;
        synchronized (salasEstados) {
            estado = salasEstados.get(codigoSala);
            if (estado == null) {
                estado = new SalaEstado();
                salasEstados.put(codigoSala, estado);
                System.out.println("Nuevo estado creado y añadido a salasEstados.");
            } else {
                System.out.println("Estado obtenido de salasEstados: " + estado);
            }
        }

        synchronized (estado) {
            if ("unirse".equals(accion)) {
                estado.nuevoUsuario();
                System.out.println("Método nuevoUsuario() ejecutado, totales incrementado a: " + estado.getTotales());
            } else if ("listo".equals(accion)) {
                estado.marcarListo();
                System.out.println("Método marcarListo() ejecutado, total actual: " + estado.getTotales());
            } else if ("noListo".equals(accion)) {
                estado.desmarcarListo();
                System.out.println("Método desmarcarListo() ejecutado, total actual: " + estado.getTotales());
            }
            System.out.println("Método getTotales() ejecutado, retornando: " + estado.getTotales());
        }

        // Guardar el código de sala en una cookie
        guardarCodigoSalaEnCookie(codigoSala, response);


        // Redirigir a una nueva URL después del procesamiento POST
        response.sendRedirect(request.getContextPath() + "/unirseSala.jsp");
        System.out.println("Fin de doPost(), redirigiendo a: " + request.getContextPath() + "/unirseSala.jsp");
    }

    private String obtenerCodigoSalaDesdeCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("codigoSala".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void guardarCodigoSalaEnCookie(String codigoSala, HttpServletResponse response) {
        Cookie codigoSalaCookie = new Cookie("codigoSala", codigoSala);
        codigoSalaCookie.setMaxAge(60 * 60 * 24); // Duración de la cookie en segundos (opcional)
        response.addCookie(codigoSalaCookie);
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
