import java.util.Date;

public class Order {
    private int id;
    private int customerId;
    private String customerName;
    private String shippingAddress;
    private Date orderDate;
    private double totalAmount;
    private OrderItemNode items;
    private String status;
    
    public Order(int id, int customerId) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = new Date();
        this.totalAmount = 0.0;
        this.items = null;
        this.status = "Pending";
    }
    
    public Order(int id, int customerId, String customerName, String shippingAddress) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.orderDate = new Date();
        this.totalAmount = 0.0;
        this.items = null;
        this.status = "Pending";
    }
    
    // Additional constructor for auto-ID assignment
    public Order(int customerId, String customerName, String shippingAddress) {
        this.id = 0; // Will be set by OrderManager
        this.customerId = customerId;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.orderDate = new Date();
        this.totalAmount = 0.0;
        this.items = null;
        this.status = "Pending";
    }
    
    private class OrderItemNode {
        OrderItem item;
        OrderItemNode next;
        
        OrderItemNode(OrderItem item) {
            this.item = item;
            this.next = null;
        }
    }
    
    public void addItem(OrderItem item) {
        OrderItemNode newItem = new OrderItemNode(item);
        if (items == null) {
            items = newItem;
        } else {
            OrderItemNode current = items;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newItem;
        }
        totalAmount += item.getSubtotal();
    }
    
    public OrderItem[] getItems() {
        if (items == null) {
            return new OrderItem[0];
        }
        
        int count = 0;
        OrderItemNode current = items;
        while (current != null) {
            count++;
            current = current.next;
        }
        
        OrderItem[] orderItems = new OrderItem[count];
        current = items;
        for (int i = 0; i < count; i++) {
            orderItems[i] = current.item;
            current = current.next;
        }
        
        return orderItems;
    }
    
    public void sortItemsByTitle() {
        if (items == null || items.next == null) {
            return; // 0 or 1 items, already sorted
        }
        
        OrderItemNode sorted = null;
        OrderItemNode current = items;
        
        while (current != null) {
            OrderItemNode next = current.next;
            
            // Special case for the first insertion
            if (sorted == null || sorted.item.getProduct().getName().compareTo(current.item.getProduct().getName()) >= 0) {
                current.next = sorted;
                sorted = current;
            } else {
                // Find the position to insert
                OrderItemNode temp = sorted;
                while (temp.next != null && 
                       temp.next.item.getProduct().getName().compareTo(current.item.getProduct().getName()) < 0) {
                    temp = temp.next;
                }
                current.next = temp.next;
                temp.next = current;
            }
            
            current = next;
        }
        
        items = sorted;
    }
    
    // Sort items by author name using insertion sort algorithm
    public void sortItemsByAuthor() {
        if (items == null || items.next == null) {
            return; // 0 or 1 items, already sorted
        }
        
        OrderItemNode sorted = null;
        OrderItemNode current = items;
        
        while (current != null) {
            OrderItemNode next = current.next;
            
            // Special case for the first insertion
            if (sorted == null || sorted.item.getProduct().getAuthor().compareTo(current.item.getProduct().getAuthor()) >= 0) {
                current.next = sorted;
                sorted = current;
            } else {
                // Find the position to insert
                OrderItemNode temp = sorted;
                while (temp.next != null && 
                       temp.next.item.getProduct().getAuthor().compareTo(current.item.getProduct().getAuthor()) < 0) {
                    temp = temp.next;
                }
                current.next = temp.next;
                temp.next = current;
            }
            
            current = next;
        }
        
        items = sorted;
    }
    
    // Sort items by price using insertion sort algorithm
    public void sortItemsByPrice() {
        if (items == null || items.next == null) {
            return; // 0 or 1 items, already sorted
        }
        
        OrderItemNode sorted = null;
        OrderItemNode current = items;
        
        while (current != null) {
            OrderItemNode next = current.next;
            
            // Special case for the first insertion
            if (sorted == null || sorted.item.getPrice() >= current.item.getPrice()) {
                current.next = sorted;
                sorted = current;
            } else {
                // Find the position to insert
                OrderItemNode temp = sorted;
                while (temp.next != null && temp.next.item.getPrice() < current.item.getPrice()) {
                    temp = temp.next;
                }
                current.next = temp.next;
                temp.next = current;
            }
            
            current = next;
        }
        
        items = sorted;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getShippingAddress() {
        return shippingAddress;
    }
    
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    public Date getOrderDate() {
        return orderDate;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return String.format("Order #%d | Customer: %s | Date: %s | Status: %s | Total: $%.2f", 
                             id, customerName, orderDate.toString(), status, totalAmount);
    }
}