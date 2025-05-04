public class OrderQueue {
    private QueueNode front;
    private QueueNode rear;
    
    private class QueueNode {
        Order order;
        QueueNode next;
        
        QueueNode(Order order) {
            this.order = order;
            this.next = null;
        }
    }
    
    public OrderQueue() {
        this.front = null;
        this.rear = null;
    }
    
    // Check if queue is empty
    public boolean isEmpty() {
        return front == null;
    }
    
    // Add a new order to the queue (enqueue)
    public void enqueue(Order order) {
        QueueNode newNode = new QueueNode(order);
        
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        
        System.out.println("Order #" + order.getId() + " has been placed in the queue.");
    }
    
    // Remove an order from the queue (dequeue)
    public Order dequeue() {
        // If queue is empty, return null
        if (isEmpty()) {
            return null;
        }
        
        QueueNode temp = front;
        front = front.next;
        
        if (front == null) {
            rear = null;
        }
        
        return temp.order;
    }
    
    // Get the front order without removing it (peek)
    public Order peek() {
        if (isEmpty()) {
            return null;
        }
        return front.order;
    }
    
    // Count number of orders in queue
    public int size() {
        int count = 0;
        QueueNode current = front;
        
        while (current != null) {
            count++;
            current = current.next;
        }
        
        return count;
    }
    
    // Get all orders in the queue as an array
    public Order[] getAllOrders() {
        if (isEmpty()) {
            return new Order[0];
        }
        
        // Count orders
        int count = size();
        
        // Create array and populate
        Order[] orders = new Order[count];
        QueueNode current = front;
        for (int i = 0; i < count; i++) {
            orders[i] = current.order;
            current = current.next;
        }
        
        return orders;
    }
    
    // Search for an order by ID
    public Order searchById(int orderId) {
        QueueNode current = front;
        
        while (current != null) {
            if (current.order.getId() == orderId) {
                return current.order;
            }
            current = current.next;
        }
        
        return null; // Order not found
    }
}