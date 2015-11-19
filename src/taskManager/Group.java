package taskManager;

/**
 * Created by sergey on 21.03.15.
 */
public class Group {
    private long id;
    private String name;
    private long pid;

    public Group(long id, String name, long pid) {
        this.id = id;
        this.name = name;
        this.pid = pid;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPid() {
        return pid;
    }
}
