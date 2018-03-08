package todo;

/**
 *
 * @author Labalve
 */
public class DatabaseServer {

    private String dbms;
    private String serverName;
    private String portNumber;
    /**
     * @return the dbms
     */
    public String getDbms() {
        return dbms;
    }

    /**
     * @param dbms the dbms to set
     */
    public void setDbms(String dbms) {
        this.dbms = dbms;
    }

    /**
     * @return the serverName
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * @param serverName the serverName to set
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    /**
     * @return the portNumber
     */
    public String getPortNumber() {
        return portNumber;
    }

    /**
     * @param portNumber the portNumber to set
     */
    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

}
