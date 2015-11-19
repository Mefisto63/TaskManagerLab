package lockStrategy.pessimistic;

import lockStrategy.LockStrategy;

import java.io.IOException;
import java.sql.Connection;

/**
 * Created by sergey on 15.03.15.
 */
public class PessimisticLock implements LockStrategy {
    private MapOfBlockingTasks map;

    public PessimisticLock(Connection connection) {
        this.map = new MapOfBlockingTasks(connection);
    }

    @Override
    public void checkBlock(long id) throws IOException {
        if (!map.lockData(id)){
            throw new IOException("Now this task is changing by another user. Please wait a minute and repeat");
        }
    }

    @Override
    public void unlockData(long id) {
        map.unlockData(id);
    }

    @Override
    public void addData(long id) {
        map.addData(id);
    }

    @Override
    public void checkVersion(long id, long version) throws IOException {

    }
}
