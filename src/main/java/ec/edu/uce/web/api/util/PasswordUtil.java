package ec.edu.uce.web.api.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    
    public static String encriptar(String contrasena) {
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("Contraseña no puede ser nula o vacía");
        }
        return BCrypt.hashpw(contrasena, BCrypt.gensalt());
    }
    
    public static boolean verificar(String contrasenaPlana, String contrasenaEncriptada) {
        if (contrasenaPlana == null || contrasenaEncriptada == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(contrasenaPlana, contrasenaEncriptada);
        } catch (Exception e) {
            return false;
        }
    }
}