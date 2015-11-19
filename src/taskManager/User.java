package taskManager;

/**
 * Created by sergey on 21.03.15.
 */
public class User {
    private long id;
    private String name;
    private long idGroup;

    public User(long id, String name, long idGroup) {
        this.id = id;
        this.name = name;
        this.idGroup = idGroup;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getIdGroup() {
        return idGroup;
    }
}
