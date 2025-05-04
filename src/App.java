import java.util.Scanner;

public class App {
    private static UserManager userManager = new UserManager();
    private static ProductManager productManager = new ProductManager();
    private static OrderManager orderManager = new OrderManager();
    private static OrderQueue orderQueue = new OrderQueue();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        boolean running = true;
        
        while (running) {
            User user = login();
            if(user == null){
                System.out.println("Login failed. Exiting application.");
                running = false;
            }
            else if (user instanceof Customer) {
                boolean continueSession = customerMenu((Customer) user);
                if (!continueSession) {
                    running = false;
                }
            } 
            else if (user instanceof Admin) {
                boolean continueSession = adminMenu(((Admin) user));
                if (!continueSession) {
                    running = false;
                }
            } 
        }
    }

    private static boolean customerMenu(Customer customer) {
        System.out.println("Welcome " + customer.getName() + "!");
        int choice = 0;
        do{
            System.out.println("\n===== CUSTOMER MENU =====");
            System.out.println("1. View Products");
            System.out.println("2. Search Products");
            System.out.println("3. Place order");
            System.out.println("4. View order history");
            System.out.println("5. Track an order");
            System.out.println("6. Update profile");
            System.out.println("7. Logout");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            choice = getValidIntInput();
            
            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    searchProducts();
                    break;
                case 3:
                    placeOrder(customer);
                    break;
                case 4:
                    viewOrderHistory(customer);
                    break;
                case 5:
                    trackOrder(customer);
                    break;
                case 6:
                    updateProfile(customer);
                    break;
                case 7:
                    System.out.println("Logging out " + customer.getName() + "...");
                    System.out.println("Logged out successfully.");
                    return true; 
                case 0:
                    System.out.println("Exiting application. Goodbye!");
                    return false; 
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while(choice != 0 && choice != 7);
        
        return false; 
    }

    private static boolean adminMenu(Admin admin) {
        System.out.println("Welcome " + admin.getName() + "!");
        int choice = 0;
        do {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. View Products");
            System.out.println("2. Search Products");
            System.out.println("3. View Users");
            System.out.println("4. Add New Product");
            System.out.println("5. Update Product");
            System.out.println("6. Remove Product");
            System.out.println("7. View Orders in Queue");
            System.out.println("8. Process Orders");
            System.out.println("9. Logout");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            choice = getValidIntInput();
            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    searchProducts();
                    break;
                case 3:
                    viewAllUsers();
                    break;
                case 4:
                    addNewProduct();
                    break;
                case 5:
                    updateProduct();
                    break;
                case 6:
                    removeProduct();
                    break;
                case 7:
                    viewOrdersInQueue();
                    break;
                case 8:
                    processOrders();
                    break;
                case 9:
                    System.out.println("Logging out " + admin.getName() + "...");
                    System.out.println("Logged out successfully.");
                    return true;
                case 0:
                    System.out.println("Exiting application. Goodbye!");
                    return false; 
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0 && choice != 9);
        
        return false; 
    }

    private static User login() {
        System.out.println("===== LOGIN =====");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        User user = userManager.authenticate(username, password);
        if (user == null) {
            System.out.println("Invalid username or password.");
        }
        return user;
    }
    
    private static void viewProducts() {
        System.out.println("\n===== PRODUCTS =====");
        Product[] products = productManager.getAllProducts();
        
        if (products.length == 0) {
            System.out.println("No products available.");
            return;
        }
        
        for (Product product : products) {
            System.out.println(product);
        }
    }
    
    private static void searchProducts() {
        System.out.println("\n===== SEARCH PRODUCTS =====");
        System.out.print("Enter search keyword (title, author, or description): ");
        String keyword = scanner.nextLine().trim();
        
        if (keyword.isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return;
        }
        
        Product[] results = productManager.searchProducts(keyword);
        
        if (results.length == 0) {
            System.out.println("No products found matching '" + keyword + "'.");
            return;
        }
        
        System.out.println("\nSearch results for '" + keyword + "':");
        System.out.println("Found " + results.length + " product(s):\n");
        
        for (Product product : results) {
            System.out.println(product);
        }
    }
    
    private static void placeOrder(Customer customer) {
        System.out.println("\n===== PLACE ORDER =====");
        
        // Get shipping address
        System.out.print("Enter your shipping address: ");
        String shippingAddress = scanner.nextLine().trim();
        if (shippingAddress.isEmpty()) {
            System.out.println("Shipping address is required. Order cancelled.");
            return;
        }
        
        Product[] products = productManager.getAllProducts();
        
        if (products.length == 0) {
            System.out.println("No products available for ordering.");
            return;
        }
        
        // Create a new order with shipping address using OrderManager (with auto ID assignment)
        Order order = orderManager.createOrder(customer.getId(), customer.getName(), shippingAddress);
        boolean continueShopping = true;
        
        while (continueShopping) {
            // Display available products
            System.out.println("\nAvailable Products:");
            for (Product product : products) {
                if (product.getQuantity() > 0) {
                    System.out.println(product);
                }
            }
            
            // Get product choice
            System.out.print("\nEnter product ID to purchase (0 to finish): ");
            int productId = getValidIntInput();
            
            if (productId == 0) {
                continueShopping = false;
                continue;
            }
            
            // Find the product
            Product selectedProduct = productManager.getProductById(productId);
            if (selectedProduct == null) {
                System.out.println("Invalid product ID.");
                continue;
            }
            
            if (selectedProduct.getQuantity() <= 0) {
                System.out.println("Sorry, this product is out of stock.");
                continue;
            }
            
            // Get quantity
            System.out.print("Enter quantity: ");
            int quantity = getValidIntInput();
            
            if (quantity <= 0) {
                System.out.println("Quantity must be greater than 0.");
                continue;
            }
            
            if (quantity > selectedProduct.getQuantity()) {
                System.out.println("Sorry, only " + selectedProduct.getQuantity() + " items available.");
                continue;
            }
            
            // Add to order
            OrderItem item = new OrderItem(selectedProduct, quantity);
            order.addItem(item);
            
            // Update product quantity
            productManager.updateProductQuantity(productId, selectedProduct.getQuantity() - quantity);
            
            System.out.println("Added to cart: " + item);
            
            // Ask if they want to continue shopping
            System.out.print("Continue shopping? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (!response.equals("y")) {
                continueShopping = false;
            }
        }
        
        // Check if order has items
        OrderItem[] items = order.getItems();
        if (items.length == 0) {
            System.out.println("Order cancelled. No items added to cart.");
            return;
        }
        
        // Ask if they want to sort the items
        System.out.println("\nWould you like to sort your order items?");
        System.out.println("1. Sort by title");
        System.out.println("2. Sort by author");
        System.out.println("3. Sort by price");
        System.out.println("0. No sorting");
        System.out.print("Select an option: ");
        int sortOption = getValidIntInput();
        
        switch (sortOption) {
            case 1:
                order.sortItemsByTitle();
                System.out.println("Items sorted by title.");
                break;
            case 2:
                order.sortItemsByAuthor();
                System.out.println("Items sorted by author.");
                break;
            case 3:
                order.sortItemsByPrice();
                System.out.println("Items sorted by price.");
                break;
            default:
                System.out.println("No sorting applied.");
        }
        
        // Display order summary
        System.out.println("\n===== ORDER SUMMARY =====");
        for (OrderItem item : order.getItems()) {
            System.out.println(item);
        }
        System.out.println("------------------------");
        System.out.printf("Total: $%.2f\n", order.getTotalAmount());
        
        // Confirm order
        System.out.print("\nConfirm order? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y")) {
            // Add to order queue for processing
            orderQueue.enqueue(order);
            
            // Also add to customer's order history
            customer.addOrder(order);
            
            System.out.println("Order placed successfully! Order ID: " + order.getId());
            System.out.println("Your order has been placed in the queue and is awaiting processing.");
            
        } else {
            System.out.println("Order cancelled.");
            // Restore product quantities
            for (OrderItem item : items) {
                Product product = item.getProduct();
                productManager.updateProductQuantity(
                    product.getId(), 
                    product.getQuantity() + item.getQuantity()
                );
            }
        }
    }
    
    private static void viewOrderHistory(Customer customer) {
        System.out.println("\n===== ORDER HISTORY =====");
        Order[] orders = customer.getOrderHistory();
        
        if (orders.length == 0) {
            System.out.println("No order history found.");
            return;
        }
        
        for (Order order : orders) {
            System.out.println(order);
            System.out.println("Items:");
            OrderItem[] items = order.getItems();
            for (OrderItem item : items) {
                System.out.println("  - " + item);
            }
            System.out.println();
        }
    }
    
    private static void trackOrder(Customer customer) {
        System.out.println("\n===== TRACK ORDER =====");
        System.out.print("Enter the order ID to track: ");
        int orderId = getValidIntInput();
        
        // Search through customer's order history
        Order[] orders = customer.getOrderHistory();
        boolean found = false;
        
        for (Order order : orders) {
            if (order.getId() == orderId) {
                found = true;
                System.out.println("\nOrder found:");
                System.out.println(order);
                System.out.println("\nShipping Address: " + order.getShippingAddress());
                System.out.println("Status: " + order.getStatus());
                System.out.println("\nItems:");
                OrderItem[] items = order.getItems();
                for (OrderItem item : items) {
                    System.out.println("  - " + item);
                }
                break;
            }
        }
        
        if (!found) {
            System.out.println("No order found with ID: " + orderId);
        }
    }
    
    private static void updateProfile(Customer customer) {
        System.out.println("\n===== UPDATE PROFILE =====");
        System.out.println("Current Information:");
        System.out.println("Name: " + customer.getName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Phone: " + customer.getPhone());
        
        System.out.println("\nEnter new information (leave blank to keep current):");
        
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            name = customer.getName();
        }
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty()) {
            email = customer.getEmail();
        }
        
        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();
        if (phone.isEmpty()) {
            phone = customer.getPhone();
        }
        
        customer.updateProfile(name, email, phone);
        System.out.println("Profile updated successfully!");
    }
    
    private static int getValidIntInput() {
        int input = 0;
        boolean validInput = false;
        
        while (!validInput) {
            try {
                input = Integer.parseInt(scanner.nextLine().trim());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
        
        return input;
    }

    private static double getValidDoubleInput() {
        double input = 0.0;
        boolean validInput = false;
        
        while (!validInput) {
            try {
                input = Double.parseDouble(scanner.nextLine().trim());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
        
        return input;
    }

    private static void viewAllUsers() {
        System.out.println("\n===== VIEW USERS =====");
        User[] users = userManager.getAllUsers();
        if (users.length == 0) {
            System.out.println("No users found.");
            return;
        }
        for (User user : users) {
            if (user instanceof Customer) {
                System.out.println("Customer: " + user.toString());
            } else if (user instanceof Admin) {
                System.out.println("Admin: " + user.toString());
            }
        }
    }

    private static void addNewProduct() {
        System.out.println("\n===== ADD NEW PRODUCT =====");
        System.out.print("Enter product title: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Enter product price: ");
        double price = getValidDoubleInput();
        
        System.out.print("Enter product quantity: ");
        int quantity = getValidIntInput();

        System.out.println("Enter product description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Enter product author (leave blank if not a book): ");
        String author = scanner.nextLine().trim();
        
        Product newBook = productManager.createNewProduct(name, price, quantity, description, author);
        System.out.println("Book added successfully! ID: " + newBook.getId());
    }

    private static void updateProduct() {
        System.out.println("\n===== UPDATE PRODUCT =====");
        System.out.print("Enter product ID to update: ");
        int productId = getValidIntInput();
        
        Product product = productManager.getProductById(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        
        System.out.println("Current Information:");
        System.out.println(product);
        
        System.out.print("Enter new title (leave blank to keep current): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            product.setName(name);
        }
        
        System.out.print("Enter new price (leave blank to keep current): ");
        String priceInput = scanner.nextLine().trim();
        if (!priceInput.isEmpty()) {
            double price = Double.parseDouble(priceInput);
            product.setPrice(price);
        }
        
        System.out.print("Enter new quantity (leave blank to keep current): ");
        String quantityInput = scanner.nextLine().trim();
        if (!quantityInput.isEmpty()) {
            int quantity = Integer.parseInt(quantityInput);
            product.setQuantity(quantity);
        }
        
        System.out.print("Enter new description (leave blank to keep current): ");
        String description = scanner.nextLine().trim();
        if (!description.isEmpty()) {
            product.setDescription(description);
        }
        
        System.out.print("Enter new author (leave blank to keep current): ");
        String author = scanner.nextLine().trim();
        if (!author.isEmpty()) {
            product.setAuthor(author);
        }
        
        System.out.println("Product updated successfully!");
    }

    private static void removeProduct() {
        System.out.println("\n===== REMOVE PRODUCT =====");
        System.out.print("Enter product ID to remove: ");
        int productId = getValidIntInput();
        
        Product product = productManager.getProductById(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        
        productManager.removeProductById(productId);
        System.out.println("Product removed successfully!");
    }

    private static void viewOrdersInQueue() {
        System.out.println("\n===== ORDERS IN QUEUE =====");
        Order[] orders = orderQueue.getAllOrders();
        
        if (orders.length == 0) {
            System.out.println("No orders in queue.");
            return;
        }
        
        for (Order order : orders) {
            System.out.println(order);
            System.out.println("Status: " + order.getStatus());
            System.out.println("------------------------");
        }
    }

    private static void processOrders() {
        System.out.println("\n===== PROCESS ORDERS =====");
        if (orderQueue.isEmpty()) {
            System.out.println("No orders in queue to process.");
            return;
        }
        
        Order order = orderQueue.dequeue();
        if (order != null) {
            order.setStatus("Processed");
            System.out.println("Order #" + order.getId() + " has been processed.");
        } else {
            System.out.println("No orders to process.");
        }
    }
}

