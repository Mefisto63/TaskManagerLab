package lockStrategy.optimistic;

import lockStrategy.LockStrategy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sergey on 18.03.15.
 */
public class OptimisticLock implements LockStrategy {
    private MapOfVersionTasks map;
    private Connection connection;

    public OptimisticLock(Connection connection) {
        this.connection = connection;
        this.map = new MapOfVersionTasks(connection);
    }

    @Override
    public synchronized void checkVersion(long id, long version) throws IOException {
        if (!map.checkVersion(id, version)){
            throw new IOException("This task was changed by another user. Please update page and repeat");
        } else {
            try (PreparedStatement stm = connection.prepareStatement("SELECT * FROM returnversion(?)")){
                stm.setLong(1, id);
                ResultSet rs = stm.executeQuery();
                rs.next();
                version = rs.getLong(1);
                map.setTask(id, version);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void checkBlock(long id) throws IOException {

    }

    @Override
    public void unlockData(long id) {

    }

    @Override
    public void addData(long id) {
        map.setTask(id, 1);
    }

}
