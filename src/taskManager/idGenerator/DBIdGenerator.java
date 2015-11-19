package taskManager.idGenerator;

import taskManager.connection.TMConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergey on 09.03.15.
 */
public class DBIdGenerator implements IdGenerator{

    private List<Long> idPool;
    private TMConnectionPool connectionPool;
    private int count = 1;

    public DBIdGenerator(TMConnectionPool connectionPool) {
        idPool = new  ArrayList<>();
        this.connectionPool = connectionPool;
        getIdFromDB(count);
    }

    @Override
    public long generateId() {
        long id = -1;
        if (!idPool.isEmpty()) {
            synchronized (this) {
                if (!idPool.isEmpty()) {
                    id = idPool.remove(0);
                    return id;
                }
            }
        }
        //Thread.sleep(1000l);
        if (idPool.isEmpty()) {
            synchronized (this) {
                if (idPool.isEmpty()) {
                    getIdFromDB(count);
                }
            }
        }
        return generateId();
    }
    private void getIdFromDB(int count){
        Connection connection = connectionPool.get();
        try (PreparedStatement stm = connection.prepareCall("SELECT return_ids(?)")){
            stm.setInt(1, count);
            ResultSet set = stm.executeQuery();
            set.next();
            Array array = set.getArray(1);
            Object[] str = (Object[]) array.getArray();
            for (Object o : str) {
                idPool.add(Long.parseLong(o.toString()));
            }
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
