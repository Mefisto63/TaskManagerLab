package lockStrategy.optimistic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sergey on 18.03.15.
 */
public class MapOfVersionTasks {
    private Map<Long, Long> map;

    public MapOfVersionTasks(Connection connection) {
        this.map = new HashMap<>();
        try (PreparedStatement stm = connection.prepareStatement("SELECT tasks.id, tasks.ver FROM tasks")){
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                map.put(rs.getLong(1), rs.getLong(2));
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

    public boolean checkVersion(long id, long version){
        if (map.get(id) != version){
            return false;
        }
        return true;
    }

    public void setTask(long id, long version){
        map.put(id, version);
    }
}
