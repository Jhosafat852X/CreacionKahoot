import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ServerEndpoint("/EstadoSalaWebSocket")
public class EstadoSalaServer {
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
    private static final Map<String, SalaEstado> salasEstados = Collections.synchronizedMap(new HashMap<String, SalaEstado>());


    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Se abrió una nueva sesión: " + session.getId());
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Se cerró la sesión: " + session.getId());
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("Mensaje recibido desde sesión " + session.getId() + ": " + message);


        Type type = new TypeToken<Map<String, String>>() {}.getType();
        Map<String, String> data = new Gson().fromJson(message, type);


        String codigoSala = data.get("codigoSala");
        String accion = data.get("accion");


        SalaEstado estado;
        synchronized (salasEstados) {
            estado = salasEstados.get(codigoSala);
            if (estado == null) {
                estado = new SalaEstado();
                salasEstados.put(codigoSala, estado);
                System.out.println("Se creó un nuevo estado para la sala " + codigoSala);
            }
        }


        synchronized (estado) {
            switch (accion) {
                case "unirse":
                    estado.nuevoUsuario();
                    System.out.println("Usuario nuevo en sala " + codigoSala);
                    break;
                case "listo":
                    estado.marcarListo();
                    System.out.println("Usuario marcó como listo en sala " + codigoSala);
                    break;
                case "noListo":
                    estado.desmarcarListo();
                    System.out.println("Usuario desmarcó como listo en sala " + codigoSala);
                    break;
                default:
                    break;
            }


            if (estado.getListos() == estado.getTotales() && estado.getTotales() > 0) {
                estado.setFull(true);
                System.out.println("Todos los usuarios están listos en la sala " + codigoSala);
            } else {
                estado.setFull(false);
            }


            System.out.println("Estado actualizado para sala " + codigoSala + ": Totales=" + estado.getTotales() + ", Listos=" + estado.getListos() + ", isFull=" + estado.isFull());
        }


        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("codigoSala", codigoSala);
        responseMap.put("estado", estado);
        String jsonResponse = new Gson().toJson(responseMap);


        for (Session s : sessions) {
            if (s.isOpen()) {
                s.getBasicRemote().sendText(jsonResponse);
            }
        }
    }

    private static class SalaEstado {
        private int totales = 0;
        private int listos = 0;
        private boolean isFull = false;

        public synchronized void nuevoUsuario() {
            totales++;
            System.out.println("Nuevo usuario registrado: totales=" + totales);
        }

        public synchronized void marcarListo() {
            listos++;
            System.out.println("Usuario marcó como listo: listos=" + listos);
        }

        public synchronized void desmarcarListo() {
            if (listos > 0) {
                listos--;
                System.out.println("Usuario desmarcó como listo: listos=" + listos);
            }
        }

        public synchronized int getTotales() {
            return totales;
        }

        public synchronized int getListos() {
            return listos;
        }

        public synchronized boolean isFull() {
            return isFull;
        }

        public synchronized void setFull(boolean full) {
            isFull = full;
        }

        @Override
        public String toString() {
            return "SalaEstado{ totales=" + totales + ", listos=" + listos + ", isFull=" + isFull + " }";
        }
    }
}
