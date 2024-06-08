import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class CapaDeNegocioMongoUsuarios {

    private static final String COLLECTION_NAME = "validacion";
    private ConexionMongo MongoDBConnection;

    public boolean validarUsuarios(String email, String password) {
        MongoDatabase database = MongoDBConnection.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        Document query = new Document("email", email).append("password", password);
        Document user = collection.find(query).first();

        return user != null;
    }

}
