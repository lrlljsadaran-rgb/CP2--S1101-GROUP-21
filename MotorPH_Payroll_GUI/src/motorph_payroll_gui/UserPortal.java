/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph_payroll_gui;

/**
 * @author Group 21
 * Manages user access levels and security inside the application.
 */
public class UserPortal {
    private String username;
    private String password;
    private String role;

    public UserPortal(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean authenticateUser() {
        // Restricted exclusively to self-service employee credentials
        if (username.equals("employee") && password.equals("12345")) {
            this.role = "Regular Employee";
            return true;
        }
        return false;
    }

    public void resetPassword() {
        System.out.println("Password reset successful.");
    }

    public String getRole() { return role; }
    public String getUsername() { return username; }
    
}
