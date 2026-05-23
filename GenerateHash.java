import org.mindrot.jbcrypt.BCrypt;

public class GenerateHash {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java GenerateHash <password>");
            System.exit(1);
        }
        String password = args[0];
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(10));
        System.out.println("Password: " + password);
        System.out.println("BCrypt Hash: " + hash);
    }
}
