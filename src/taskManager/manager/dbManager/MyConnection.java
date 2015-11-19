package taskManager.manager.dbManager;

import org.postgresql.jdbc4.Jdbc4Connection;
import org.postgresql.util.HostSpec;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by sergey on 19.02.15.
 */
public class MyConnection extends Jdbc4Connection implements Connection {


    public MyConnection(HostSpec[] hostSpecs, String user, String database, Properties info, String url) throws SQLException {
        super(hostSpecs, user, database, info, url);
    }

    @Override
    public void close()  {

    }
}
