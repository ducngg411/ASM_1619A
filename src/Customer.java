public class Customer extends User {
    private OrderNode orderHistory;
    
    public Customer() {
        super();
    }
    
    public Customer(int id, String name, String username, String password, String email, String phone) {
        super(id, name, username, password, email, phone);
        this.orderHistory = null;
    }
    
    private class OrderNode {
        Order order;
        OrderNode next;
        
        OrderNode(Order order) {
            this.order = order;
            this.next = null;
        }
    }
    
    public void addOrder(Order order) {
        OrderNode newOrder = new OrderNode(order);
        if (orderHistory == null) {
            orderHistory = newOrder;
        } else {
            OrderNode current = orderHistory;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newOrder;
        }
    }
    
    public Order[] getOrderHistory() {
        if (orderHistory == null) {
            return new Order[0];
        }
        
        // Count orders
        int count = 0;
        OrderNode current = orderHistory;
        while (current != null) {
            count++;
            current = current.next;
        }
        
        // Create array and populate
        Order[] orders = new Order[count];
        current = orderHistory;
        for (int i = 0; i < count; i++) {
            orders[i] = current.order;
            current = current.next;
        }
        
        return orders;
    }

    @Override
    public String toString() {
        return "[ID: " + getId() + ", Name: " + getName() + 
                ", Username: " + getUsername() + ", Email: " + getEmail() + 
                ", Phone: " + getPhone() + "]";
    }
}
