public class Test {
    public static void main(String[] args) {
        Student student = new Student();
        student.setName(" Priyashi Roy");
        student.setId(1);
        student.setGrade('A');

        StudentView view = new StudentView();

        StudentController controller = new StudentController(student, view);

        controller.updateView();

        controller.setStudentName("Swati Wedi");
        controller.setStudentId(2);
        controller.setStudentGrade('B');

        controller.updateView();
    }
}