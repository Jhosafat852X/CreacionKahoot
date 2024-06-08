import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class ConexionMongo {
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    public static void ConexionAMongoBD(){
        MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017"); // Cambia la URI según tu configuración
        mongoClient = new MongoClient(uri);
        database = mongoClient.getDatabase("usuarios");
    }

    public static MongoDatabase getDatabase(){
        if(database == null){
            ConexionAMongoBD();
        }

        return database;
    }

    public static void CerrarConexionAMongo(){
        if(mongoClient != null){
            mongoClient.close();
        }
    }


}