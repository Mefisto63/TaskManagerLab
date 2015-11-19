package beans;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by sergey on 24.03.15.
 */
@Stateless(name = "CreateUserEJB")
public class CreateUserBean implements CreateUserEJB{
    @Resource(mappedName = "java:/DSTaskManager")
    private DataSource ds;
    private Connection connection;

    @PostConstruct
    public void initialize(){
        try {

            System.out.println("DS 1 = " + ds.toString());
            connection = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CreateUserBean() {
    }

    @Override
    public boolean createUser(String name, String pass, long idGroup){
        try (PreparedStatement stm = connection.prepareStatement("INSERT INTO users (name_log, pass, id_group) VALUES (?,?,?)")) {
            stm.setString(1, name);
            stm.setString(2, pass);
            stm.setLong(3, idGroup);
            stm.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
