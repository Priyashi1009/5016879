import java.util.Scanner;

public class SortCustomerOrder {

    public static class Order {
        private int orderId;
        private String customerName;
        private double totalPrice;

        public Order(int orderId, String customerName, double totalPrice) {
            this.orderId = orderId;
            this.customerName = customerName;
            this.totalPrice = totalPrice;
        }

        public int getOrderId() {
            return orderId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "orderId=" + orderId +
                    ", customerName='" + customerName + '\'' +
                    ", totalPrice=" + totalPrice +
                    '}';
        }
    }

    private static Scanner sc = new Scanner(System.in);
    private static Order[] orders = new Order[100];
    private static int orderCount = 0;

    public static void addOrder() {
        System.out.print("Enter order ID: ");
        int id = sc.nextInt();
        sc.nextLine();  // Consume the newline
        System.out.print("Enter customer name: ");
        String customerName = sc.nextLine();
        System.out.print("Enter total price: ");
        double totalPrice = sc.nextDouble();
        orders[orderCount++] = new Order(id, customerName, totalPrice);
    }

    public static void bubbleSort() {
        boolean swapped;
        for (int i = 0; i < orderCount - 1; i++) {
            swapped = false;
            for (int j = 0; j < orderCount - i - 1; j++) {
                if (orders[j].getTotalPrice() > orders[j + 1].getTotalPrice()) {
                    Order temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
        System.out.println("Orders have been sorted using Bubble Sort.");
    }

    public static void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    private static int partition(int low, int high) {
        double pivot = orders[high].getTotalPrice();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (orders[j].getTotalPrice() < pivot) {
                i++;
                Order temp = orders[i];
                orders[i] = orders[j];
                orders[j] = temp;
            }
        }
        Order temp = orders[i + 1];
        orders[i + 1] = orders[high];
        orders[high] = temp;
        return i + 1;
    }

    public static void displayOrders() {
        System.out.println("List of Orders:");
        for (int i = 0; i < orderCount; i++) {
            System.out.println(orders[i]);
        }
    }

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("Select an option:");
            System.out.println("1. Add New Order");
            System.out.println("2. Sort Orders (Bubble Sort)");
            System.out.println("3. Sort Orders (Quick Sort)");
            System.out.println("4. Display All Orders");
            System.out.println("-1. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addOrder();
                    break;
                case 2:
                    bubbleSort();
                    displayOrders();
                    break;
                case 3:
                    quickSort(0, orderCount - 1);
                    System.out.println("Orders have been sorted using Quick Sort.");
                    displayOrders();
                    break;
                case 4:
                    displayOrders();
                    break;
                case -1:
                    System.out.println("Thank you for using the system!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, please select a valid option.");
            }
        }
        sc.close();
    }
}
