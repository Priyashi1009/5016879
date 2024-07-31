public class CustomerService {
    private CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void findCustomerById(int id) {
        customerRepository.findCustomerById(id);
        System.out.println("customer found by id: " + id +"   (dependency)");
    }
}