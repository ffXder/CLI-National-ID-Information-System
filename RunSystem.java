import java.util.Scanner;

public class RunSystem {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        NationalIDSystem system = new NationalIDSystem();
        Admin admin = new Admin();

        while (true) {
            System.out.println("=============================================");
            System.out.println("         Welcome to National ID System       ");
            System.out.println("=============================================");
            System.out.println("1 - Register\n2 - Find ID\n3 - Exit");
            int option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 0 -> admin.adminMenu();
                case 1 -> system.fillOut();
                case 2 -> system.findID();
                case 3 -> {
                    System.out.println("Are you sure you want to quit? (Y/N)");
                    String confirm = input.nextLine().trim();

                    if (confirm.equalsIgnoreCase("Y")) {
                        System.out.println("Thank you for using our system! Exiting the Program.");
                        input.close();
                        System.exit(0);
                    } else if (confirm.equalsIgnoreCase("N")) {
                        return;
                    } else {
                        System.out.println("Invalid input your choices are (Y/N)");
                    }

                }
                default -> System.out.println("Invalid input");
            }
        }
    }
}