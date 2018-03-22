package todo;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.UUID;
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
    public void setRole(Role role) {
        this.role = role;
    }

    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }

    public String getUuid(){
        return uuid;
    }
    
    public void setUuid(String uuid){
        this.uuid = uuid;
    }
    
    public void save() throws SQLException {
        DatabaseUserInserter databaseUserInserter = new DatabaseUserInserter();
        databaseUserInserter.insertUser(this);
    }

    private String uuid;
    private String key;
    private Role role;

    public User() {
        try {
            UUID uuid = UUID.randomUUID();
            this.uuid = uuid.toString();
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
