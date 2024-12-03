import java.util.Scanner;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import java.time.LocalDate;
import java.io.*;

interface Forms {
    public void fillOut();

    public void retrieveInfo(int id);
}

class UsersRecord {
    private PersonalInfo personalInfo;
    private Address address;

    // using this class to contain both address and personalinfo
    public UsersRecord(PersonalInfo info, Address add) {
        this.personalInfo = info;
        this.address = add;
    }

    // getters
    public PersonalInfo getInfo() {
        return personalInfo;
    }

    public Address getAddress() {
        return address;
    }

    public void displayInfo() {
        personalInfo.displayInfo();
        address.displayInfo();
    }
}

class NationalIDSystem implements Forms {
    private Map<Integer, UsersRecord> database = new HashMap<>(); // hashmap to store data with key and value
    private Scanner read = new Scanner(System.in);
    private Random generate = new Random();

    // for prompt
    private String getString(String prompt) {
        System.out.print(prompt);
        return read.nextLine().trim();
    }

    // this method will validate if the user input a valid gender
    private String getValidatedGender() {
        String gender;
        while (true) {
            gender = getString("Enter Sex (Kasarian): ");
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("M")
                    || gender.equalsIgnoreCase("F")) {
                return gender;
            } else {
                System.out.println("Invalid gender. Please enter Male or Female.");
            }
        }

    }

    // this method will validate if the user input a valid date
    private String getValidatedBirthDate() {
        while (true) {
            try {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                String birthDate = getString("Enter date of birth (YYYY/MM/DD): ");
                LocalDate date = LocalDate.parse(birthDate, format);

                if (date.isAfter(LocalDate.now())) {
                    System.out.println("Date cannot be in the future. Please try again.");
                    continue;
                }

                Period period = Period.between(date, LocalDate.now());
                int age = period.getYears();

                if (age < 0) {
                    System.out.println("Please enter a valid date.");
                    continue;
                }

                System.out.println("Age: " + age);
                return birthDate; // Return the valid date
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY/MM/DD.");
            }
        }
    }

    // this method will validate the blood type inputted by users
    private String getValidatedBloodType() {
        while (true) {
            String bloodType = getString("Enter Blood Type: ").toUpperCase();
            if (bloodType.matches("^(A|B|O|AB)[+-]$")) {
                return bloodType;
            } else {
                System.out.println("Please enter a valid blood type.");
            }
        }

    }

    /*
     * private String getSuffix() {
     * String choice = getString("Does you name have suffix (Y/N): ");
     * if (choice.equalsIgnoreCase("Y")) {
     * String suffix = getString("Enter Suffix (e.g Sr.,III): ");
     * return suffix;
     * }
     * 
     * }
     */

    public void fillOut() {

        // Display
        System.out.println("Please fill out the information needed.");
        String firstName = getString("Enter First Name (Pangalan): ");
        String middleName = getString("Enter Middle Name (Gitnang Pangalan): ");
        String lastName = getString("Enter Last Name (Apelyido): ");

        String gender = getValidatedGender().toUpperCase();
        String birthDate = getValidatedBirthDate();
        String birthCountry = getString("Enter Birth Country: ");
        String birthProvince = getString("Enter Birth Province: ");
        String birthPlace = getString("Enter Birth City/Municipality: ");
        String maritalStatus = getString("Enter Marital Status: ");
        String bloodType = getValidatedBloodType().toUpperCase();

        // Address
        System.out.println("Please fill out your address");
        String address = getString("Enter Address: ");
        String barangay = getString("Enter Barangay: ");
        String place = getString("Enter City/Municipality: ");
        String province = getString("Enter Province: ");
        String country = getString("Enter Country: ");
        String zipCode = getString("Enter Zip Code: ");
        String mobileNumber = getString("Enter Mobile no.: ");
        String email = getString("Enter Email: ");

        PersonalInfo personalInfo = new PersonalInfo(firstName, middleName, lastName, gender,
                birthDate,
                birthCountry,
                birthProvince, birthPlace, maritalStatus, bloodType); // adds info

        Address add = new Address(); // adds address
        add.setAddress(address, barangay, place, province, country, zipCode, mobileNumber, email);

        // personalInfo.displayInfo(); // display the info
        // add.displayInfo(); // display the address

        String confirmation = getString("Press Y to confirm submission or Press N to cancel\n");

        // adds confirmation before submitting data
        if (confirmation.equalsIgnoreCase("Y")) {
            int nationalID = generate.nextInt(100) + 50; // genarate id using random numbers
            database.put(nationalID, new UsersRecord(personalInfo, add));
            System.out.println("Successfully registered. Thank you for using our System."
                    + "\nYour ID number is " + nationalID);

            saveInfo();
        } else if (confirmation.equalsIgnoreCase("N")) {
            System.out.println("Successfully cancelled. Thank you for using our system.");
        } else {
            System.out.println("Invalid input. Please enter Y or N.");
        }

    }

    // this method will save the information in file as database
    private void saveInfo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Database.txt", true))) {
            for (Map.Entry<Integer, UsersRecord> e : database.entrySet()) {
                int id = e.getKey();
                UsersRecord record = e.getValue();
                PersonalInfo info = record.getInfo();

                // test
                writer.write("| " + id + " ");
                writer.write(info.getLastName() + ", " + info.getFirstName() + ", " + info.getMiddleName() + ", "
                        + info.getDateOfBirth() + ", " + info.getBloodType());
                writer.write("|\n");
                writer.close();

            }

        } catch (IOException e) {
            System.out.println("ERROR");
        }

    }

    private void loadInfo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Database.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("ERROR");
        }
    }

    // this method will retrieve the information
    public void retrieveInfo(int id) {

        if (database.containsKey(id)) {
            UsersRecord record = database.get(id);
            System.out.println("ID number: " + id);
            record.displayInfo();
            loadInfo();
        } else {
            System.out.println("The ID Number " + id + " is not found");
        }
    }

    public void findID() {
        try {
            System.out.print("Enter national ID: ");
            int ID = read.nextInt();
            read.nextLine();
            retrieveInfo(ID);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter your Number ID");
            read.nextLine();
        }
    }
}

public class RunSystem {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        NationalIDSystem system = new NationalIDSystem();

        while (true) {
            System.out.println("=============================================");
            System.out.println(" Welcome to Philippine Identification System ");
            System.out.println("=============================================");
            System.out.println("1 - Register\n2 - Find ID\n3 - Exit");
            int option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1 -> system.fillOut();
                case 2 -> system.findID();
                case 3 -> {
                    System.out.println("Are you sure you want to quit? (Y/N)");
                    String confirm = input.nextLine().trim();

                    if (confirm.equalsIgnoreCase("Y")) {
                        System.out.println("Thank you for using our system! Exiting the Program.");
                        input.close();
                        System.exit(0);
                    }

                }
                default -> System.out.println("Invalid input");
            }
        }
    }
}