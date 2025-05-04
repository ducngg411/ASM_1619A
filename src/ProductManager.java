public class ProductManager {
    private ProductNode head;
    private int nextId;
    
    // Custom linked list for products
    private class ProductNode {
        Product product;
        ProductNode next;
        
        ProductNode(Product product) {
            this.product = product;
            this.next = null;
        }
    }
    
    public ProductManager() {
        head = null;
        nextId = 1;
        // Add some demo products
        initializeProducts();
    }
    
    private void initializeProducts() {
        addProduct(new Product(nextId++, "The Great Gatsby", 12.99, 8, "Classic novel", "F. Scott Fitzgerald"));
        addProduct(new Product(nextId++, "To Kill a Mockingbird", 14.99, 12, "Award-winning novel", "Harper Lee"));
        addProduct(new Product(nextId++, "1984", 10.99, 15, "Dystopian novel", "George Orwell"));
        addProduct(new Product(nextId++, "Pride and Prejudice", 9.99, 10, "Romantic novel", "Jane Austen"));
        addProduct(new Product(nextId++, "The Hobbit", 15.99, 7, "Fantasy novel", "J.R.R. Tolkien"));
    }
    
    public void addProduct(Product product) {
        if (product.getId() == 0) {
            product.setId(nextId++);
        }
        
        ProductNode newProduct = new ProductNode(product);
        if (head == null) {
            head = newProduct;
        } else {
            ProductNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newProduct;
        }
    }
    
    // Create and add a book product with automatic ID
    public Product createNewProduct(String name, double price, int quantity, String description, String author) {
        Product product = new Product(0, name, price, quantity, description, author);
        addProduct(product);
        return product;
    }
    
    // Get all products
    public Product[] getAllProducts() {
        if (head == null) {
            return new Product[0];
        }
        
        // Count products
        int count = 0;
        ProductNode current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        
        // Create array and populate
        Product[] products = new Product[count];
        current = head;
        for (int i = 0; i < count; i++) {
            products[i] = current.product;
            current = current.next;
        }
        
        return products;
    }
    
    // Get product by id
    public Product getProductById(int id) {
        ProductNode current = head;
        while (current != null) {
            if (current.product.getId() == id) {
                return current.product;
            }
            current = current.next;
        }
        return null;
    }
    
    // Update product quantity after purchase
    public boolean updateProductQuantity(int id, int newQuantity) {
        ProductNode current = head;
        while (current != null) {
            if (current.product.getId() == id) {
                current.product.setQuantity(newQuantity);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Remove product by id
    public boolean removeProductById(int id) {
        if (head == null) {
            return false; 
        }
        
        if (head.product.getId() == id) {
            head = head.next;
            return true;
        }
        
        ProductNode current = head;
        while (current.next != null) {
            if (current.next.product.getId() == id) {
                current.next = current.next.next; 
                return true;
            }
            current = current.next;
        }

        return false; 
    }

    // Search products by name or author
    public Product[] searchProducts(String keyword) {
        if (head == null || keyword == null || keyword.trim().isEmpty()) {
            return new Product[0];
        }
        
        keyword = keyword.toLowerCase().trim();
        
        // First, count matching products
        int count = 0;
        ProductNode current = head;
        while (current != null) {
            if (current.product.getName().toLowerCase().contains(keyword) || 
                current.product.getAuthor().toLowerCase().contains(keyword) ||
                current.product.getDescription().toLowerCase().contains(keyword)) {
                count++;
            }
            current = current.next;
        }
        
        if (count == 0) {
            return new Product[0];
        }
        
        // Create array and populate with matching products
        Product[] results = new Product[count];
        current = head;
        int index = 0;
        
        while (current != null) {
            if (current.product.getName().toLowerCase().contains(keyword) || 
                current.product.getAuthor().toLowerCase().contains(keyword) ||
                current.product.getDescription().toLowerCase().contains(keyword)) {
                results[index++] = current.product;
            }
            current = current.next;
        }
        
        return results;
    }
}