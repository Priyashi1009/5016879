public class StudentController {
    private Student student;
    private StudentView view;

    public String getStudentName() {
        return student.name;
    }

    public void setStudentName(String name) {
        student.name = name;
    }

    public int getStudentId() {
        return student.id;
    }

    public void setStudentId(int id) {
        student.id = id;
    }

    public char getStudentGrade() {
        return student.grade;
    }

    public void setStudentGrade(char grade) {
        student.grade = grade;
    }

    public StudentController(Student student, StudentView view) {
        this.student = student;
        this.view = view;
    }

    public void updateView() {
        view.displayStudentDetails(student.getName(), student.getId(), student.getGrade());
    }
}