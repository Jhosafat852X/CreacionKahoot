import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class CapaDeNegocioMongoUsuarios {

    private static final String COLLECTION_NAME = "Usuarios";
    private ConexionMongo MongoDBConnection;

    public boolean validarUsuarios(String email, String password) {
        MongoDatabase database = MongoDBConnection.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        Document query = new Document("correo", email).append("contraseña", password);
        Document user = collection.find(query).first();

        return user != null;
    }

    public Document obtenerUsuario(String email) {
        MongoDatabase database = MongoDBConnection.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        Document query = new Document("correo", email);
        return collection.find(query).first();
    }

    // Método para obtener todos los datos de la colección "Usuarios"
    // Método para obtener todos los datos de la colección "Usuarios"
    public String obtenerDatosUsuarios() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        // No se necesita una consulta específica, se obtienen todos los documentos de la colección
        MongoCursor<Document> cursor = collection.find().iterator();

        StringBuilder datosUsuarios = new StringBuilder();
        try {
            while (cursor.hasNext()) {
                Document user = cursor.next();
                datosUsuarios.append(user.toJson()).append("<br>"); // Agregar los datos del usuario como una cadena JSON
            }
        } finally {
            cursor.close();
        }

        if (datosUsuarios.length() > 0) {
            return datosUsuarios.toString(); // Devuelve todos los datos de los usuarios
        } else {
            return "No se encontraron datos de usuarios en la colección."; // Mensaje de error si no se encuentran usuarios
        }
    }



}
