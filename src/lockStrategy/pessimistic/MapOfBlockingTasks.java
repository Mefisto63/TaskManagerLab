package lockStrategy.pessimistic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sergey on 17.03.15.
 */
public class MapOfBlockingTasks {
    private Map<Long, Boolean> map;

    public MapOfBlockingTasks(Connection connection) {
        map = new HashMap<>();
        try (PreparedStatement stm = connection.prepareStatement("SELECT tasks.id FROM tasks")){
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                map.put(rs.getLong(1), false);
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

    public boolean lockData(long id) {
        if (!map.get(id)){
            map.put(id, true);
            return true;
        }
        return false;
    }

    public void unlockData(long id){
        map.put(id, false);
    }

    public void addData(long id){
        map.put(id, false);
    }
}
