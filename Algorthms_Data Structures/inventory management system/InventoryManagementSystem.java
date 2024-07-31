import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InventoryManagementSystem {

    public static class Product {
        private int productId;
        private String productName;
        private int quantity;
        private double price;

        public Product(int productId, String productName, int quantity, double price) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return String.format("Product{productId='%d', productName='%s', quantity=%d, price=%.2f}", 
                                 productId, productName, quantity, price);
        }
    }

    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<Integer, Product> productMap = new HashMap<>();

    public static void addProduct(int id) {
        System.out.print("Enter product name: "); 
        String name = scanner.next();
        System.out.print("Enter product quantity: "); 
        int quantity = scanner.nextInt();
        System.out.print("Enter product price: "); 
        double price = scanner.nextDouble();
        Product newProduct = new Product(id, name, quantity, price);
        productMap.put(newProduct.getProductId(), newProduct);
    }

    public static void updateProduct(int productId) {
        if (productMap.containsKey(productId)) {
            System.out.print("Enter new product name: "); 
            String newName = scanner.next();
            System.out.print("Enter new quantity: "); 
            int newQuantity = scanner.nextInt();
            System.out.print("Enter new price: "); 
            double newPrice = scanner.nextDouble();
            Product updatedProduct = new Product(productId, newName, newQuantity, newPrice);
            productMap.put(productId, updatedProduct);
        } else {
            System.out.println("No product found with ID " + productId);
        }
    }

    public static void deleteProduct(int productId) {
        if (productMap.containsKey(productId)) {
            productMap.remove(productId);
        } else {
            System.out.println("Product ID " + productId + " does not exist.");
        }
    }

    public static void displayProducts() {
        if (productMap.isEmpty()) {
            System.out.println("No products to display.");
        } else {
            productMap.values().forEach(System.out::println);
        }
    }

    public static void main(String[] args) {
        int id = 1;
        while (true) {
            System.out.print("Choose an option:\n1. Add Product\n2. Update Product\n3. Delete Product\n-1 to Exit\nYour choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Current product ID: " + id);
                    addProduct(id++);
                    displayProducts();
                    break;
                case 2:
                    System.out.print("Enter product ID to update: "); 
                    int updateId = scanner.nextInt();
                    updateProduct(updateId);
                    displayProducts();
                    break;
                case 3:
                    System.out.print("Enter product ID to delete: "); 
                    int deleteId = scanner.nextInt();
                    deleteProduct(deleteId);
                    displayProducts();
                    break;
                case -1:
                    System.out.println("Exiting. Thank you!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
