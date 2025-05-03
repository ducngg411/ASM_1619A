public class OrderManager {
    private OrderNode head;
    private int nextId;
    
    // Custom linked list for orders
    private class OrderNode {
        Order order;
        OrderNode next;
        
        OrderNode(Order order) {
            this.order = order;
            this.next = null;
        }
    }
    
    public OrderManager() {
        head = null;
        nextId = 1;
    }
    
    // Add a new order with automatic ID assignment
    public void addOrder(Order order) {
        // Set the ID if it's not already set (equals to 0)
        if (order.getId() == 0) {
            // Create a new order with the next available ID
            order.setId(nextId++);
        }
        
        OrderNode newOrder = new OrderNode(order);
        if (head == null) {
            head = newOrder;
        } else {
            OrderNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newOrder;
        }
    }
    
    // Create and add a new order with automatic ID
    public Order createOrder(int customerId, String customerName, String shippingAddress) {
        Order order = new Order(0, customerId, customerName, shippingAddress);
        addOrder(order);
        return order;
    }
    
    // Get all orders
    public Order[] getAllOrders() {
        if (head == null) {
            return new Order[0];
        }
        
        // Count orders
        int count = 0;
        OrderNode current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        
        // Create array and populate
        Order[] orders = new Order[count];
        current = head;
        for (int i = 0; i < count; i++) {
            orders[i] = current.order;
            current = current.next;
        }
        
        return orders;
    }
    
    // Get order by ID
    public Order getOrderById(int id) {
        OrderNode current = head;
        while (current != null) {
            if (current.order.getId() == id) {
                return current.order;
            }
            current = current.next;
        }
        return null;
    }
    
    // Get next available order ID
    public int getNextId() {
        return nextId;
    }
}