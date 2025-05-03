public class UserManager {
    private UserNode head;
    private int nextId;
    
    // Custom linked list for users
    private class UserNode {
        User user;
        UserNode next;
        
        UserNode(User user) {
            this.user = user;
            this.next = null;
        }
    }
    
    public UserManager() {
        head = null;
        nextId = 1;
        // Add sample users
        initializeUsers();
    }
    
    private void initializeUsers() {
        // Add a sample customer
        Customer customer = new Customer(nextId++, "John Doe", "john", "password", "john@example.com", "123-456-7890");
        addUser(customer);
        Customer customer2 = new Customer(nextId++, "Jane Smith", "jane", "password", "john@example.com", "123-456-7890");
        addUser(customer2);
        Admin admin = new Admin(nextId++, "Duc Nguyen", "ducngg411", "Ducham2004", "admin@example.com", "098-765-4321");
        addUser(admin);
    }
    
    // Add a new user with automatic ID assignment
    public void addUser(User user) {
        // Set the ID if it's not already set (equals to 0)
        if (user.getId() == 0) {
            user.setId(nextId++);
        }
        
        UserNode newUser = new UserNode(user);
        if (head == null) {
            head = newUser;
        } else {
            UserNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newUser;
        }
    }
    
    // Create and add a new customer with automatic ID
    public Customer createCustomer(String name, String username, String password, String email, String phone) {
        Customer customer = new Customer(0, name, username, password, email, phone);
        addUser(customer);
        return customer;
    }
    
    // Create and add a new admin with automatic ID
    public Admin createAdmin(String name, String username, String password, String email, String phone) {
        Admin admin = new Admin(0, name, username, password, email, phone);
        addUser(admin);
        return admin;
    }
    
    // Authenticate user
    public User authenticate(String username, String password) {
        UserNode current = head;
        while (current != null) {
            if (current.user.getUsername().equals(username) && 
                current.user.getPassword().equals(password)) {
                return current.user;
            }
            current = current.next;
        }
        return null;
    }
    
    // Get user by ID
    public User getUserById(int id) {
        UserNode current = head;
        while (current != null) {
            if (current.user.getId() == id) {
                return current.user;
            }
            current = current.next;
        }
        return null;
    }

    // Get all users
    public User[] getAllUsers() {
        if (head == null) {
            return new User[0];
        }
        
        int count = 0;
        UserNode current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        
        User[] users = new User[count];
        current = head;
        for (int i = 0; i < count; i++) {
            users[i] = current.user;
            current = current.next;
        }
        
        return users;
    }
}