import java.util.InputMismatchException;
import java.util.Scanner;

public class Admin {
    private final String username = "admin";
    private final String password = "admin123";
    private Scanner input = new Scanner(System.in);

    private boolean authenticateAdmin() {
        System.out.print("Enter username: ");
        String inputUsername = input.nextLine();
        System.out.print("Enter password: ");
        String inputPassword = input.nextLine();

        return inputUsername.equals(username) && inputPassword.equals(password);

    }

    public void adminMenu() {
        if (!authenticateAdmin()) {
            System.out.println("Login Unsuccessful. Please check your password or username. ");
            return;
        }
        try {
            while (true) {
                System.out.println("========================");
                System.out.println("|      Admin Menu      |");
                System.out.println("========================");
                System.out.println("1 - Edit Record\n2 - Delete Record\n3 - Check Record\n4 - Exit");
                int choice = input.nextInt();

                switch (choice) {
                    case 1 -> {
                    }
                    case 2 -> {
                    }
                    case 3 -> {
                    }
                    case 4 -> {
                        System.out.println("Are you sure you want to quit? (Y/N)");
                        String confirm = input.nextLine().trim();

                        if (confirm.equalsIgnoreCase("Y")) {
                            System.out.println("Exiting the Admin Mode.");
                            return;
                        }
                    }
                    default -> System.out.println("Invalid Input");

                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number. ");
        }

    }

}
