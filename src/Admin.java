public class Admin extends User {
    private String role;
    
    public Admin() {
        super();
        this.role = "Admin";
    }

    public Admin(int id, String name, String username, String password, String email, String phone) {
        super(id, name, username, password, email, phone);
        this.role = "Admin";
    }

    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return "[ID: " + getId() + ", Name: " + getName() + 
                ", Username: " + getUsername() + ", Email: " + getEmail() + 
                ", Phone: " + getPhone() + "]";
    }
    
}
