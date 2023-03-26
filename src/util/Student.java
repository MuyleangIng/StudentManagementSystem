package util;

public class Student extends Person{
    public Double score;
    public String RoomName;

    public Student() {}

    public Student(Integer id, String name, String gender, Integer age, String RoomName, Double score) {
        super(id, name, gender, age);
        this.RoomName = RoomName;
        this.score = score;
    }
}
