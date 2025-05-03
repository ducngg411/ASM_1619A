public class OrderItem {
    private Product product;
    private int quantity;
    private double price;
    
    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice();
    }
    
    public Product getProduct() {
        return product;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public double getPrice() {
        return price;
    }
    
    public double getSubtotal() {
        return quantity * price;
    }
    
    @Override
    public String toString() {
        return String.format("%s | Qty: %d | Price: $%.2f | Subtotal: $%.2f", 
            product.getName(), quantity, price, getSubtotal());
    }
}