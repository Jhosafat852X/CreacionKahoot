public class Usuario {

    private String _id; // Puedes ajustar el tipo según sea necesario
    private String correo;
    private String contraseña; // No recomendado almacenar contraseñas en texto plano en un bean, solo como ejemplo
    private String nombre;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(String _id, String correo, String contraseña, String nombre) {
        this._id = _id;
        this.correo = correo;
        this.contraseña = contraseña;
        this.nombre = nombre;
    }

    // Getters y Setters
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
