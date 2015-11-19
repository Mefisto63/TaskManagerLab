package taskManager.connection;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergey on 18.02.15.
 */
public class TMConnectionPool {
    private List<Connection> pool;

    public TMConnectionPool() {
        pool = new ArrayList<>();
    }

    public void putTMConnection(Connection connection) {
        pool.add(connection);
    }

    public Connection get() {
        try {
            Connection connection = null;
            //Thread.sleep(1000l);
            if (!pool.isEmpty()) {
                synchronized (this) {
                    if (!pool.isEmpty()) {
                        //Thread.sleep(1000l);
                        //System.out.println("start getting connection");
                        connection = pool.remove(0);
                        return connection;
                    } //else throw new RuntimeException("123");
                }
            } //else throw new RuntimeException("123");
            Thread.sleep(5000l);
            return get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
