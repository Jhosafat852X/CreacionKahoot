import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID; // Importar para generar UUIDs

public class CapaDeNegocioMongoSalas {

    private MongoDatabase database;

    public CapaDeNegocioMongoSalas() {
        database = ConexionMongo.getDatabase();
    }

    public boolean salaExiste(String codigoSala) {
        MongoCollection<Document> coleccionSalas = database.getCollection("Salas");

        // Consultar si existe una sala con el código dado
        Document query = new Document("codigo", codigoSala);
        MongoCursor<Document> cursor = coleccionSalas.find(query).iterator();

        try {
            if (cursor.hasNext()) {
                // Si hay resultados, la sala existe
                return true;
            } else {
                // No hay resultados, la sala no existe
                return false;
            }
        } finally {
            cursor.close();
        }
    }

    public void crearSala(String nombre, String codigo, String creadorId, String salaId) {
        MongoCollection<Document> coleccionSalas = database.getCollection("Salas");

        // Crear un registro para depuración
        System.out.println("Creando sala con los siguientes datos:");
        System.out.println("salaId: " + salaId);
        System.out.println("nombre: " + nombre);
        System.out.println("codigo: " + codigo);
        System.out.println("creadorId: " + creadorId);

        Document salaDoc = new Document("_id", salaId)
                .append("nombre", nombre)
                .append("codigo", codigo)
                .append("creador_id", creadorId)
                .append("fecha_creacion", new java.util.Date());

        // Imprimir el documento antes de insertarlo
        System.out.println("Documento de sala a insertar:");
        System.out.println(salaDoc.toJson());

        coleccionSalas.insertOne(salaDoc);
    }
    public void guardarPregunta(String salaId, String preguntaId, String pregunta, String archivoNombre, String respuesta1, String respuesta2, String respuesta3, String respuesta4, String respuestaCorrecta, int tiempo, int puntos) {
        MongoCollection<Document> coleccionPreguntas = database.getCollection("Preguntas");

        ArrayList<String> respuestas = new ArrayList<>();
        respuestas.add(respuesta1);
        respuestas.add(respuesta2);
        respuestas.add(respuesta3);
        respuestas.add(respuesta4);

        Document preguntaDoc = new Document("_id", preguntaId)
                .append("sala_id", salaId)
                .append("pregunta", pregunta)
                .append("archivo", archivoNombre)
                .append("respuestas", respuestas)
                .append("respuestaCorrecta", respuestaCorrecta)
                .append("tiempo", tiempo)
                .append("puntos", puntos);

        coleccionPreguntas.insertOne(preguntaDoc);
    }
}
