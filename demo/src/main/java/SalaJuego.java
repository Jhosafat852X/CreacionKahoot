import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.websocket.Session;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalaJuego {
    private List<Pregunta> preguntas;
    private int preguntaActualIndex;
    private Map<String, Integer> puntuaciones;
    private Map<String, Integer> respuestasCorrectasPorUsuario;
    private List<Session> sessions; // Para manejar las sesiones de WebSocket

    public SalaJuego(String codigoSala) {
        preguntas = new ArrayList<>();
        puntuaciones = new HashMap<>();
        preguntaActualIndex = 0;
        sessions = new ArrayList<>(); // Inicialización de la lista de sesiones
        respuestasCorrectasPorUsuario = new HashMap<>(); // Inicialización del mapa de respuestas correctas

        cargarPreguntasDesdeDB(codigoSala);
    }
    public boolean esUltimaPreguntaRespondida() {
        return preguntaActualIndex == preguntas.size() - 1;
    }
    private void cargarPreguntasDesdeDB(String codigoSala) {
        MongoDatabase database = ConexionMongo.getDatabase();
        MongoCollection<Document> salasCollection = database.getCollection("Salas");
        MongoCollection<Document> preguntasCollection = database.getCollection("Preguntas");

        Document sala = salasCollection.find(new Document("codigo", codigoSala)).first();
        if (sala != null) {
            String salaIdStr = sala.getString("_id");

            for (Document preguntaDoc : preguntasCollection.find(new Document("sala_id", salaIdStr))) {
                String texto = preguntaDoc.getString("pregunta");
                List<String> respuestas = (List<String>) preguntaDoc.get("respuestas");
                String respuestaCorrecta = preguntaDoc.getString("respuestaCorrecta");
                preguntas.add(new Pregunta(texto, respuestas, respuestaCorrecta));
            }
        } else {
            System.out.println("No se encontró la sala con el código: " + codigoSala);
        }
    }

    public Pregunta getPreguntaActual() {
        if (preguntaActualIndex < preguntas.size()) {
            return preguntas.get(preguntaActualIndex);
        }
        return null;
    }

    public void siguientePregunta() {
        if (preguntaActualIndex < preguntas.size() - 1) {
            preguntaActualIndex++;
            // Aquí deberías enviar la siguiente pregunta a todos los clientes conectados
            enviarPreguntaActual();
        } else {
            // Aquí se determina al ganador y se envía el mensaje
            String ganador = obtenerGanador();
            enviarMensajeGanador(ganador);
        }
    }

    public void registrarRespuesta(String usuarioId, String respuestaSeleccionada, int tiempoRespuesta) {
        Pregunta pregunta = getPreguntaActual();
        if (pregunta != null) {
            if (respuestaSeleccionada.equals(pregunta.respuestaCorrecta)) {
                int puntos = 1000 - tiempoRespuesta;

                // Obtener el puntaje actual del usuario
                Integer puntajeActual = puntuaciones.get(usuarioId);
                if (puntajeActual == null) {
                    puntajeActual = 0;
                }
                // Sumar los puntos al puntaje actual
                puntajeActual += puntos;
                puntuaciones.put(usuarioId, puntajeActual);

                // Incrementar el contador de respuestas correctas del usuario
                Integer respuestasCorrectas = respuestasCorrectasPorUsuario.get(usuarioId);
                if (respuestasCorrectas == null) {
                    respuestasCorrectas = 0;
                }
                respuestasCorrectas++;
                respuestasCorrectasPorUsuario.put(usuarioId, respuestasCorrectas);
            }
        }
    }


    private void enviarPreguntaActual() {
        Pregunta pregunta = getPreguntaActual();
        if (pregunta != null) {
            // Construir el mensaje con la pregunta actual
            Map<String, Object> mensaje = new HashMap<>();
            mensaje.put("codigoSala", "codigo_de_sala"); // Aquí deberías enviar el código real de la sala
            mensaje.put("pregunta", pregunta);

            // Convertir a JSON y enviar a todos los clientes conectados
            String mensajeJson = new Gson().toJson(mensaje);
            for (Session session : sessions) {
                if (session.isOpen()) {
                    try {
                        session.getBasicRemote().sendText(mensajeJson);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    String obtenerGanador() {
        String ganador = "";
        int maxRespuestasCorrectas = -1;
        for (Map.Entry<String, Integer> entry : respuestasCorrectasPorUsuario.entrySet()) {
            String usuarioId = entry.getKey();
            int respuestasCorrectas = entry.getValue();
            if (respuestasCorrectas > maxRespuestasCorrectas) {
                maxRespuestasCorrectas = respuestasCorrectas;
                ganador = usuarioId; // Puedes almacenar la sesión o identificación única del usuario si lo tienes
            }
        }
        return ganador;
    }


    private void enviarMensajeGanador(String ganador) {
        Map<String, Object> mensaje = new HashMap<>();
        mensaje.put("codigoSala", "codigo_de_sala"); // Aquí deberías enviar el código real de la sala
        mensaje.put("accion", "ganador");
        mensaje.put("ganador", ganador); // Aquí envías el nombre del usuario ganador o su ID

        String mensajeJson = new Gson().toJson(mensaje);
        for (Session session : sessions) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(mensajeJson);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Pregunta {
        String texto;
        List<String> respuestas;
        String respuestaCorrecta;

        public Pregunta(String texto, List<String> respuestas, String respuestaCorrecta) {
            this.texto = texto;
            this.respuestas = respuestas;
            this.respuestaCorrecta = respuestaCorrecta;
        }
    }
}