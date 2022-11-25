package engines.kernel;

public interface Observer {

    //method to updateEntities the observer, used by subject
    public void updateEntities();

    //attach with subject to observe
    public void setSubject(Subject sub);
}
