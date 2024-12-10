import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Admin {
    private final String username = "admin";
    private final String password = "admin123";
    private Scanner input = new Scanner(System.in);
    private NationalIDSystem system = new NationalIDSystem();
    private Map<String, String> adminAcc = new HashMap<>();
    private Utils utils = new Utils();

    public Admin() {
        adminAcc.put(username, password); // add username (key) and password (value) using hashmap
    }

    private boolean authenticateAdmin() {
        System.out.println("------------------------");
        System.out.println("       Admin Login      ");
        System.out.println("------------------------");
        System.out.print("Enter username: ");
        String inputUsername = input.nextLine();
        System.out.print("Enter password: ");
        String inputPassword = input.nextLine();

        utils.loading(3);
        return adminAcc.containsKey(inputUsername) && adminAcc.containsValue(inputPassword);

    }

    public void adminMenu() {
        // authenticate before accessing admin
        if (!authenticateAdmin()) {
            utils.clearConsole(); // added clear console
            System.out.println("Login Unsuccessful. Please check your password or username. ");
            return;
        }

        utils.clearConsole(); // clears console after log in
        while (true) {
            try {

                System.out.println("========================");
                System.out.println("|      Admin Menu      |");
                System.out.println("========================");
                System.out
                        .println("1 - Edit Record\n2 - Delete Record\n3 - Check Record\n4 - Log Out\n5 - Exit Program");
                int choice = input.nextInt();

                switch (choice) {
                    case 1 -> system.editRecord();
                    case 2 -> system.deleteRecord();

                    case 3 -> system.checkRecords();
                    case 4 -> { // log out
                        input.nextLine();
                        System.out.println("Are you sure you want to log out? (Y/N)");
                        String confirm = input.nextLine().trim();

                        if (confirm.equalsIgnoreCase("Y")) {
                            System.out.println("Exiting the Admin Mode...");
                            utils.clearConsole();
                            return;
                        } else if (confirm.equalsIgnoreCase("N")) {
                            continue;
                        } else {
                            System.out.println("Invalid Input. Press Y to Log out or Press N to Cancel");
                        }
                    }
                    case 5 -> { // exit program
                        input.nextLine();
                        System.out.println("Are you sure you want to exit? (Y/N)");
                        String confirm = input.nextLine().trim();

                        if (confirm.equalsIgnoreCase("Y")) {
                            System.out.println("Exiting Program...");
                            input.close();
                            System.exit(0);
                        } else if (confirm.equalsIgnoreCase("N")) {
                            continue;
                        } else {
                            System.out.println("Invalid Input. Press Y to Exit or Press N to Cancel");
                        }
                    }
                    default -> System.out.println("Please select a number based on the selections");

                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number. ");
                input.nextLine(); // avoids stopping the program and proceeds the program
            }
        }

    }

}
