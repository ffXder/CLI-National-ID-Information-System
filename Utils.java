import java.io.IOException;

//utils by capyBruh
public class Utils {
    public void clearConsole() { // for clearing the console
        try {
            // For Windows systems
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Unix-based systems
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            // Fallback: Print new lines
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
        }
    }

    public void sleep(int seconds) { // loading dots
        try {
            System.out.print("Logging in ");
            for (int i = 0; i < seconds; i++) {
                System.out.print(". ");
                Thread.sleep(1000); // converts seconds to milliseconds
            }
            System.out.println(); // new line
        } catch (InterruptedException e) {
            System.out.println("Sleep interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // preserve the interrupt status
        }
    }
}