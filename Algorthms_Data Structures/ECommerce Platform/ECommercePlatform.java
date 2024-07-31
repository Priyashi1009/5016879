import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class ECommercePlatform {

    public static class Product {
        private int productId;
        private String productName;
        private String category;

        public Product(int productId, String productName, String category) {
            this.productId = productId;
            this.productName = productName;
            this.category = category;
        }

        public int getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public String getCategory() {
            return category;
        }

        @Override
        public String toString() {
            return String.format("Product{productId=%d, productName='%s', category='%s'}", productId, productName, category);
        }
    }

    private static final int INITIAL_CAPACITY = 100;
    private static Product[] unsortedProducts = new Product[INITIAL_CAPACITY];
    private static Product[] sortedProducts = new Product[INITIAL_CAPACITY];
    private static int productCount = 0;

    static Scanner sc = new Scanner(System.in);

    public static void addProduct() {
        System.out.print("Enter product ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume the newline
        System.out.print("Enter product name: ");
        String name = sc.nextLine();
        System.out.print("Enter product category: ");
        String category = sc.nextLine();

        Product product = new Product(id, name, category);
        unsortedProducts[productCount] = product;
        sortedProducts[productCount] = product;
        productCount++;

        // Sort the sortedProducts array by product ID
        Arrays.sort(sortedProducts, 0, productCount, Comparator.comparingInt(Product::getProductId));
    }

    public static Product linearSearch(Product[] products, int productId) {
        for (int i = 0; i < productCount; i++) {
            if (products[i].getProductId() == productId) {
                return products[i];
            }
        }
        return null;
    }

    public static Product binarySearch(Product[] products, int productId) {
        int left = 0;
        int right = productCount - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (products[mid].getProductId() == productId) {
                return products[mid];
            }

            if (products[mid].getProductId() < productId) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    public static void displayProducts() {
        System.out.println("Products List:");
        for (int i = 0; i < productCount; i++) {
            System.out.println(sortedProducts[i]);
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.print("Menu Options:\n1. Add New Product\n2. Search Product (Linear)\n3. Search Product (Binary)\n4. Show All Products\nEnter -1 to Exit\nPlease enter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    System.out.print("Enter product ID for linear search: ");
                    int linearSearchId = sc.nextInt();
                    Product linearSearchResult = linearSearch(unsortedProducts, linearSearchId);
                    if (linearSearchResult != null) {
                        System.out.println("Product found: " + linearSearchResult);
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter product ID for binary search: ");
                    int binarySearchId = sc.nextInt();
                    Product binarySearchResult = binarySearch(sortedProducts, binarySearchId);
                    if (binarySearchResult != null) {
                        System.out.println("Product found: " + binarySearchResult);
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                case 4:
                    displayProducts();
                    break;
                case -1:
                    System.out.println("Thank you for using the platform!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }
}

