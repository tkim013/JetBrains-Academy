package tracker;

public class Student {

    private String id;
    private int points;
    private double completed;

    public Student() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getCompleted() {
        return completed;
    }

    public void setCompleted(double completed) {
        this.completed = completed;
    }
}
