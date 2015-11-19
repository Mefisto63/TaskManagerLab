package lockStrategy;

import java.io.IOException;

/**
 * Created by sergey on 15.03.15.
 */
public interface LockStrategy {
    void checkBlock(long id) throws IOException;
    void unlockData(long id);
    void addData(long id);
    void checkVersion(long id, long version) throws IOException;
}
