import java.util.Scanner;
import java.util.Base64;
import java.util.Date;

class User {
    String username;
    String password;
    String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}

class Service {
    private User[] users;
    private int userCount;

    public Service(int maxUsers) {
        users = new User[maxUsers];
        userCount = 0;
    }

    // Register a new user
    public void registerUser(String username, String password, String role) {
        if (userCount >= users.length) {
            System.out.println("User limit reached. Cannot register more users.");
            return;
        }
        users[userCount] = new User(username, password, role);
        userCount++;
        System.out.println("User successfully registered!");
    }

    // Simulate JWT token generation 
    public String generateJwt(User user) {
        String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payload = "{\"sub\":\"" + user.getUsername() + "\",\"role\":\"" + user.getRole() + "\",\"exp\":\"" + (System.currentTimeMillis() + 3600000) + "\"}";

        // Base64 encode header and payload
        String base64Header = Base64.getUrlEncoder().encodeToString(header.getBytes());
        String base64Payload = Base64.getUrlEncoder().encodeToString(payload.getBytes());

        return base64Header + "." + base64Payload; // Simulating JWT without signature
    }

    // Simulate JWT token validation
    public String validateJwt(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 2) {
            System.out.println("Invalid token format.");
            return null;
        }

        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
        if (payload.contains("\"exp\":")) {
            String expirationStr = payload.split("\"exp\":\"")[1].split("\"")[0];
            long expirationTime = Long.parseLong(expirationStr);
            if (System.currentTimeMillis() > expirationTime) {
                System.out.println("Token is expired.");
                return null;
            }
        }

        // Extract username from the token payload
        String username = payload.split("\"sub\":\"")[1].split("\"")[0];
        return username;
    }

    // Authenticate a user and return JWT token
    public String loginUser(String username, String password) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getUsername().equals(username) && users[i].getPassword().equals(password)) {
                System.out.println("Login successful!");
                return generateJwt(users[i]); // Return the JWT token after successful login
            }
        }
        System.out.println("Invalid username or password.");
        return null;
    }

    // Check if user has the required role based on JWT
    public boolean checkAccess(String token, String requiredRole) {
        String username = validateJwt(token);
        if (username == null) return false; // Invalid token

        for (int i = 0; i < userCount; i++) {
            if (users[i].getUsername().equals(username) && users[i].getRole().equalsIgnoreCase(requiredRole)) {
                System.out.println("Access granted to " + requiredRole + " resources successfully!.");
                return true;
            }
        }
        System.out.println("Access denied! You don't have the required role.");
        return false;
    }
}

public class Authentication {
    public static void main(String[] args) {
    	
        // Initialize Service with a limit of 5 users
        Service authService = new Service(5);
        Scanner scanner = new Scanner(System.in);

        // Step 1: User Registration
        System.out.println("Register a new user:");
        System.out.print("Enter your username: ");
        String newUsername = scanner.nextLine();
        System.out.print("Enter your password: ");
        String newPassword = scanner.nextLine();
        System.out.print("Enter your role (ADMIN, USER, MODERATOR): ");
        String newRole = scanner.nextLine();

        authService.registerUser(newUsername, newPassword, newRole);

        // Step 2: User Login
        System.out.println("\nLogin to your account:");
        System.out.print("Enter your username: ");
        String loginUsername = scanner.nextLine();
        System.out.print("Enter your password: ");
        String loginPassword = scanner.nextLine();

        // Login user and generate JWT token
        String jwtToken = authService.loginUser(loginUsername, loginPassword);

        if (jwtToken != null) { // If login is successful
            System.out.println("\nYour JWT token: " + jwtToken); // Display JWT token

            // Step 3: Role-based Access Control
            System.out.println("\nCheck access to certain resources:");
            System.out.print("Enter the role required for access (ADMIN, USER, MODERATOR): ");
            String requiredRole = scanner.nextLine();

            // Check access with the token
            authService.checkAccess(jwtToken, requiredRole);
        }

        scanner.close();
    }
}
