import java.util.Scanner;

public class FinancialForecast {

    public double predictFutureValue(double initialValue, double growthRate, int years) {
        if (years == 0) {
            return initialValue;
        }
        return predictFutureValue(initialValue * (1 + growthRate), growthRate, years - 1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FinancialForecast forecast = new FinancialForecast();
        int choice;

        do {
             System.out.println();
            System.out.println("Financial Forecasting");
            System.out.println("1. Predict Future Value");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter initial value: ");
                double initialValue = scanner.nextDouble();

                System.out.print("Enter annual growth rate: ");
                double growthRate = scanner.nextDouble();

                System.out.print("Enter number of years: ");
                int years = scanner.nextInt();

                double futureValue = forecast.predictFutureValue(initialValue, growthRate, years);
                System.out.printf("Future Value after %d years: %.2f%n", years, futureValue);

            } else if (choice != 2) {
                System.out.println("Invalid choice");
            }

        } while (choice != 2);

        System.out.println("Thank you, Byee!");
        scanner.close();
    }
}
