public class Test {
    public static void main(String[] args) {
        System.out.println("Dependency Testing");
        CustomerRepository CustRepo=new CustomerRepositoryImpl();

        CustomerService CustService=new CustomerService(CustRepo);

        CustService.findCustomerById(1);
    }
}
