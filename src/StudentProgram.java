import util.Option;
import util.Interface;
import util.input;
import util.Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentProgram extends Option implements Interface {
    public ArrayList<Student> students;
    public Integer option;
    public final ArrayList<Integer> defaultOption;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static class Program {
        public static void main(String[] args) {
            StudentProgram stupro = new StudentProgram();
            do {
                stupro.start();
                switch (stupro.option) {
                    case 1 -> stupro.create();
                    case 2 -> stupro.update();
                    case 3 -> stupro.search();
                    case 4 -> stupro.delete();
                    case 5 -> stupro.show();
                    case 6 -> stupro.exitaplication();
                    default -> Option.alertMessage("");
                }
            }while (stupro.defaultOption.contains(stupro.option));
        }
    }
    public StudentProgram() {
        this.students = new ArrayList<>(){
            {
            add(new Student(1,"Ing Muyleang","Male",21,"DevOps",100.00));
            }
        };
        this.defaultOption = new ArrayList<>(){
            {
            for (int i=1;i<7;i++){
                add(i);
                }
            }
        };
    }

    public void start(){
        //called super menu
        super.menu();
        do {
            this.option = input.Obj(Integer.class, "Choose your option 1-6: ");
            if (!defaultOption.contains(option)){
                Option.alertMessage("invalid option should be 1-6");
            }
        }while (!defaultOption.contains(option));
    }

    @Override
    public void create() {
        System.out.println(ANSI_GREEN+"==============="+ "Insert student" + "==============="+ANSI_RESET);
        Student student = new Student();
        do {
            //check with id
            student.id = input.Obj(Integer.class, "Id");
            if (this.checkStudentId(student.id)){
                //check it exist or not
                Option.alertMessage("student id:"+ student.id +" already exist");
            }
        }while (this.checkStudentId(student.id));

        student.name = input.Obj(String.class, "Name");
        student.gender = input.Obj(String.class, "gender");
        student.age = input.Obj(Integer.class, "age");
        student.RoomName = input.Obj(String.class, "Class Name");
        student.score = input.Obj(Double.class, "score");
        this.students.add(student);
        super.successMessage("Insert");
        super.enter();
    }

    @Override
    public void show() {
        System.out.println(ANSI_GREEN+"==============="+ "Show students information" + "==============="+ANSI_RESET);
        //check when if student exist or not input
        System.out.println(this.formatHeader());
        if (students.isEmpty()){
            alertMessage("The students is empty.");
            super.enter();
            return;
        }
        for (Student student: students){
            System.out.println(this.formatStudent(student));
        }
        super.enter();
    }

    @Override
    public void search() {
        if (students.isEmpty()){
            Option.alertMessage("The students is empty.");
            super.enter();
            return;
        }
        ArrayList<Integer> defsearch= new ArrayList<>(List.of(1,2,3,4));
        Integer sOption;
        do {
            System.out.println(ANSI_GREEN+"==============="+ "Search students information" + "==============="+ANSI_RESET);
            //noted important!!!!
            super.searchMenu();
            do {
                sOption = input.Obj(Integer.class, "Choose search option 1-5");
                if (!defsearch.contains(sOption)){
                    Option.alertMessage("invalid value should be 1-5");
                }
            }while (!defsearch.contains(sOption));

            if (sOption.equals(5)){
                return;
            }
            HashMap<Integer,String> searchBy = new HashMap<>(){{
                put(1,"id");
                put(2,"name");
                put(3,"gender");
                put(4,"className");
            }};
            String field = input.Obj(String.class, searchBy.get(sOption)+" to search");
            this.showInfo(searchBy.get(sOption),field);
            super.enter();
        }while (defsearch.contains(sOption));
    }
    @Override
    public void update() {
        System.out.println(ANSI_GREEN+"==============="+ "Update students information" + "==============="+ANSI_RESET);
        if (students.isEmpty()){
            //same to search or show same function
            Option.alertMessage("The students is empty.");
            super.enter();
            return;
        }
        Integer id = input.Obj(Integer.class,"id to update");
        if (this.checkStudentId(id)){
            //to string for read and show
            Student student = this.getStu(id);
            System.out.println("ID: "+ student.id +
                    "\t Name: "+ student.name +
                    "\t Gender: "+ student.gender +
                    "\t Age: "+ student.age +
                    "\t Class Name: "+ student.RoomName +
                    "\t Score: "+ student.score
            );
            student.name = input.Obj(String.class, "Name");
            student.gender = input.Obj(String.class, "gender");
            student.age = input.Obj(Integer.class, "age");
            student.RoomName = input.Obj(String.class, "Room name");
            student.score = input.Obj(Double.class, "score");
            students.set(students.indexOf(student),student);
            super.successMessage("updated");
        }else {
            Option.alertMessage("Student id:"+ id +" isn't exist.");
        }
        super.enter();
    }
    @Override
    public void delete() {
        System.out.println(ANSI_GREEN+"==============="+ "Delete students information" + "==============="+ANSI_RESET);
        if (students.isEmpty()){
            Option.alertMessage("The students is empty.");
            super.enter();
            return;
        }
        Integer id = input.Obj(Integer.class,"id to delete");
        if (this.checkStudentId(id)){
            students.remove(this.getStu(id));
            super.successMessage("deleted");
        }else {
            Option.alertMessage("Student id:"+ id +" isn't exist.");
        }
        super.enter();
    }
    @Override
    public void exitaplication() {
        System.out.println("Are you sure you want to exit");
        System.out.println("Press"+ANSI_GREEN+" Y to Exit"+ANSI_RESET+"\t/\t"+ ANSI_RED+"N to cancel"+ANSI_RESET+" to exited.");
        String exited;
        do {
            exited = input.Obj(String.class,"Please input :");
            if (exited.equalsIgnoreCase("y")){
                System.exit(0);
            }else if (exited.equalsIgnoreCase("n")){
                break;
            }else {
                Option.alertMessage("invalid value should be y or n");
            }
        }while (true);
    }
    private Student getStu(Integer id){
        for (Student student: students){
            if (student.id.equals(id)){
                return student;
            }
        }
        return new Student();
    }
    private boolean checkStudentId(Integer id){
        if (!students.isEmpty()){
            ArrayList<Integer> checkId = new ArrayList<>();
            for (Student student : students) {
                checkId.add(student.id);
            }
            return checkId.contains(id);
        }
        return false;
    }
    private void showInfo(String field, String value){
        System.out.println(this.formatHeader());
        for (Student student: students){
            //String str="";
            switch (field){
                case "id":
                    if (student.id.equals(Integer.parseInt(value))){
                        System.out.println(this.formatStudent(student));
                    }
                    break;
                case "name":
                    if (student.name.toLowerCase().startsWith(value.toLowerCase())){
                        System.out.println(this.formatStudent(student));
                    }
                    break;
                case "gender":
                    if (student.gender.toLowerCase().startsWith(value.toLowerCase())){
                        System.out.println(this.formatStudent(student));
                    }
                case "className":
                    if (student.RoomName.toLowerCase().startsWith(value.toLowerCase())){
                        System.out.println(this.formatStudent(student));
                    }
                    break;
                default:
                    alertMessage("Wrong option");
            }
        }
    }

    private String formatHeader(){
        String head = "ID";
        head+= String.format("%-15s","");
        head += String.format("%-15s", "NAME");
        head += String.format("%-15s", "GENDER");
        head += String.format("%-15s", "AGE");
        head += String.format("%-15s", "CLASSNAME");
        head += String.format("%-15s", "SCORE");
        return head;
    }

    private String formatStudent(Student student){
        String str="";
        str = String.format("%-15s",String.format("%04d",student.id));
        str+= String.format("%-18s",student.name);
        str+= String.format("%-15s",student.gender);
        str+= String.format("%-15s",student.age);
        str+= String.format("%-15s",student.RoomName);
        str+= String.format("%-15s",student.score);
        return str;
    }

}

