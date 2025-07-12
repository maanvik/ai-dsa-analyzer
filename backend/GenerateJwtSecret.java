import java.security.SecureRandom;
import java.util.Base64;

public class GenerateJwtSecret {
    public static void main(String[] args) {
        // Generate a secure random key (256 bits = 32 bytes)
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[32];
        random.nextBytes(key);
        
        // Encode to Base64
        String secret = Base64.getEncoder().encodeToString(key);
        
        System.out.println("Generated JWT Secret:");
        System.out.println(secret);
        System.out.println("\nCopy this value to your environment variable JWT_SECRET");
    }
} 