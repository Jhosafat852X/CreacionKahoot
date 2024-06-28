import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
@ServerEndpoint("/JuegoWebSocket")
public class JuegoWebSocket {
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
    private static final Map<String, SalaJuego> salasJuegos = Collections.synchronizedMap(new HashMap<String, SalaJuego>());


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

        SalaJuego juego;
        synchronized (salasJuegos) {
            juego = salasJuegos.get(codigoSala);
            if (juego == null) {
                juego = new SalaJuego(codigoSala);
                salasJuegos.put(codigoSala, juego);
                System.out.println("Se creó un nuevo juego para la sala " + codigoSala);
            }
        }

        if ("respuesta".equals(accion)) {
            String usuarioId = data.get("usuarioId");
            String respuestaSeleccionada = data.get("respuestaSeleccionada");
            int tiempoRespuesta = Integer.parseInt(data.get("tiempoRespuesta"));
            juego.registrarRespuesta(usuarioId, respuestaSeleccionada, tiempoRespuesta);

            // Verificar si es el momento de mostrar al ganador
            if (juego.esUltimaPreguntaRespondida()) {
                System.out.println("¡Última pregunta respondida! Determinando ganador...");

                String ganador = juego.obtenerGanador();
                System.out.println("Ganador: " + ganador); // Imprimir el ganador en consola

                enviarMensajeGanador(ganador);
            }
        } else if ("siguiente".equals(accion)) {
            juego.siguientePregunta();
        }

        // Construir la respuesta para enviar al cliente
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("codigoSala", codigoSala);
        responseMap.put("pregunta", juego.getPreguntaActual());

        // Convertir a JSON
        String jsonResponse = new Gson().toJson(responseMap);

        // Enviar la respuesta a todas las sesiones abiertas
        for (Session s : sessions) {
            if (s.isOpen()) {
                s.getBasicRemote().sendText(jsonResponse);
            }
        }
    }

    private void enviarMensajeGanador(String ganador) {
        System.out.println("Enviando mensaje de ganador a las sesiones WebSocket...");

        // Construir el mensaje JSON para indicar la acción de mostrar al ganador
        Map<String, Object> message = new HashMap<>();
        message.put("accion", "mostrarGanador");
        message.put("ganador", ganador);
        String jsonMessage = new Gson().toJson(message);

        for (Session s : sessions) {
            if (s.isOpen()) {
                try {
                    s.getBasicRemote().sendText(jsonMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }





}