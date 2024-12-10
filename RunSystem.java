import java.util.InputMismatchException;
import java.util.Scanner;

public class RunSystem {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        NationalIDSystem system = new NationalIDSystem();
        Admin admin = new Admin();
        Utils utils = new Utils();

        while (true) {
            try {

                System.out.println("=============================================");
                System.out.println("       National ID  Information System       ");
                System.out.println("=============================================");
                System.out
                        .println("1 - Register\n2 - Find ID\n3 - View Requirements\n4 - Located Registration\n5- Exit");
                int option = input.nextInt();
                input.nextLine();

                switch (option) {
                    case 0 -> { // admin is hidden in user interface
                        utils.clearConsole();
                        admin.adminMenu(); // admin menu
                    }
                    case 1 -> system.fillOut();
                    case 2 -> system.findID();
                    case 3 -> system.viewRequirements();
                    case 4 -> system.locatedRegistration();
                    case 5 -> { // exit
                        System.out.println("Are you sure you want to quit? (Y/N)");
                        String confirm = input.nextLine().trim();

                        if (confirm.equalsIgnoreCase("Y")) {
                            System.out.println("Thank you for using our system! Exiting the Program...");
                            input.close();
                            System.exit(0);
                        } else if (confirm.equalsIgnoreCase("N")) {
                            continue;
                        } else {
                            System.out.println("Invalid Input. Press Y to Exit or Press N to Cancel");
                        }

                    }

                    default -> System.out.println("Invalid input");
                }

            } catch (InputMismatchException e) {
                System.out.println("Please enter an number to select!");
                input.nextLine(); // new line then continue the loop
            }
        }

    }
}