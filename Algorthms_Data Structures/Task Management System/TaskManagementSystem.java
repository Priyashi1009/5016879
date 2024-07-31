import java.util.Scanner;

public class TaskManagementSystem {

    static class Task {
        private int taskId;
        private String taskName;
        private String status;
        private Task next;

        public Task(int taskId, String taskName, String status) {
            this.taskId = taskId;
            this.taskName = taskName;
            this.status = status;
            this.next = null;
        }

        public int getTaskId() {
            return taskId;
        }

        public String getTaskName() {
            return taskName;
        }

        public String getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return "Task{" +
                    "taskId=" + taskId +
                    ", taskName='" + taskName + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

    static class TaskManager {
        private Task head;

        public TaskManager() {
            head = null;
        }

        public void addTask(Task task) {
            if (head == null) {
                head = task;
            } else {
                Task current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = task;
            }
        }

        public Task searchTask(int taskId) {
            Task current = head;
            while (current != null) {
                if (current.getTaskId() == taskId) {
                    return current;
                }
                current = current.next;
            }
            return null;
        }

        public void traverseTasks() {
            Task current = head;
            while (current != null) {
                System.out.println(current);
                current = current.next;
            }
        }

        public boolean deleteTask(int taskId) {
            if (head == null) {
                return false;
            }
            if (head.getTaskId() == taskId) {
                head = head.next;
                return true;
            }
            Task current = head;
            while (current.next != null) {
                if (current.next.getTaskId() == taskId) {
                    current.next = current.next.next;
                    return true;
                }
                current = current.next;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager manager = new TaskManager();

        while (true) {
            System.out.println("\nTask Management System");
            System.out.println("1. Add Task");
            System.out.println("2. Search Task");
            System.out.println("3. Display All Tasks");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Task ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Task Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Task Status: ");
                    String status = scanner.nextLine();

                    manager.addTask(new Task(id, name, status));
                    System.out.println("Task added.");
                    break;

                case 2:
                    System.out.print("Enter Task ID to search: ");
                    int searchId = scanner.nextInt();
                    scanner.nextLine();

                    Task task = manager.searchTask(searchId);
                    if (task != null) {
                        System.out.println("Task found: " + task);
                    } else {
                        System.out.println("Task not found.");
                    }
                    break;

                case 3:
                    System.out.println("All Tasks:");
                    manager.traverseTasks();
                    break;

                case 4:
                    System.out.print("Enter Task ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();

                    boolean isDeleted = manager.deleteTask(deleteId);
                    if (isDeleted) {
                        System.out.println("Task deleted.");
                    } else {
                        System.out.println("Task not found.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
