package todo;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Labalve
 */
public class User {

    public enum Role {
        ADMIN, USER
    }

    public Role getRole() {
        return role;
    }

    public String getKey() {
        return key;
    }
    
    public void save() throws SQLException {
        DatabaseUserInserter databaseUserInserter = new DatabaseUserInserter();
        databaseUserInserter.insertUser(this);
    }

    private String key;
    private Role role;

    public User() {
        try {
            this.role = Role.USER;
            this.key = generateKey();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String generateKey() throws NoSuchAlgorithmException {
        int keyLen = 100;
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[keyLen / 8];
        random.nextBytes(bytes);
        return DatatypeConverter.printHexBinary(bytes).toLowerCase();
    }

}
