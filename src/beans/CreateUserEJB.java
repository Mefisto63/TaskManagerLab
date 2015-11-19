package beans;

import javax.ejb.Remote;

/**
 * Created by sergey on 25.03.15.
 */

@Remote
public interface CreateUserEJB {
    boolean createUser(String name, String pass, long idGroup);
}
