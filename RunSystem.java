import java.util.Scanner;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import java.time.LocalDate;

interface Forms {
    public void fillOut();

    public void retrieveInfo(int id);
}

class PersonalInfo {
    private String firstName;
    private String middleName;
    private String lastName;
    private String nameSuffix;
    private String gender;
    private String dateOfBirth;
    private String birthCountry;
    private String birthProvince;
    private String birthPlace;
    private String maritalStatus;
    private String bloodType;

    public PersonalInfo(String firstName, String middleName, String lastName, String gender,
            String dateOfBirth, String birthCountry, String birthProvince, String place, String status, String type) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.birthCountry = birthCountry;
        this.birthProvince = birthProvince;
        this.birthPlace = place;
        this.maritalStatus = status;
        this.bloodType = type;
    }

    // constructor overloading adding additional data type
    public PersonalInfo(String firstName, String middleName, String lastName, String suffix, String gender,
            String dateOfBirth, String birthCountry, String birthProvince, String place, String status, String type) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nameSuffix = suffix; // additional data type
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.birthCountry = birthCountry;
        this.birthProvince = birthProvince;
        this.birthPlace = place;
        this.maritalStatus = status;
        this.bloodType = type;
    }

    public void displayInfo() {
        System.out.println("---------------------------------------");
        System.out.println("Personal Information ");
        System.out.println("First Name (Pangalan): " + firstName
                + "\nMiddle Name (Gitnang Pangalan): " + middleName
                + "\nLastName (Apelyido): " + lastName);
        System.out.println("Sex (Kasarian): " + gender);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Birth Country (Lugar ng Kapanganakan): " + birthCountry);
        System.out.println("Birth Province (Probinsiya ng Kapanganakan): " + birthProvince);
        System.out.println("City/Municipality (Lungsod Ng Kapanganakan): " + birthPlace);
        System.out.println("Marital Status: " + maritalStatus);
        System.out.println("Blood Type (Uri ng Dugo): " + bloodType);
        System.out.println("--------------------------------------");
    }
}

class PermanentAddress {
    private String address;
    private String barangay;
    private String place;
    private String province;
    private String country;
    private String zipCode;
    private String mobileNumber;
    private String email;

    public void setAddress(String address, String barangay, String place, String province, String country,
            String code, String number, String email) {
        this.address = address;
        this.barangay = barangay;
        this.place = place;
        this.province = province;
        this.country = country;
        this.zipCode = code;
        this.mobileNumber = number;
        this.email = email;
    }

    public void displayInfo() {
        System.out.println("---------------------------------------");
        System.out.println("Address");
        System.out.println("Address (Tirahan): " + address);
        System.out.println("Barangay: " + barangay);
        System.out.println("City/Municipality *Lungsod/Bayan): " + place);
        System.out.println("Province (Probinsya): " + province);
        System.out.println("County (Bansa): " + country);
        System.out.println("ZIP Code: " + zipCode);
        System.out.println("Mobile no: " + mobileNumber);
        System.out.println("Email Address: " + email);
        System.out.println("---------------------------------------");
    }

}

class Admin {
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
            System.out.print("Login Unsuccessful. Please check your password or username. ");
            return;
        }
        while (true) {
            System.out.println("========================");
            System.out.println("|      Admin Menu      |");
            System.out.println("========================");
            System.out.println("1 - Edit Record\n2 - Delete Record\n3 - Check Record\n4- Exit");
            int choice = input.nextInt();
        }
    }

}

class NationalIDSystem implements Forms {
    private Map<Integer, PersonalInfo> database = new HashMap<>();
    private Scanner read = new Scanner(System.in);
    private Random generate = new Random();
    boolean isConfirmed = false;

    // for prompt
    private String getString(String prompt) {
        System.out.print(prompt);
        return read.nextLine();
    }

    public void fillOut() {

        // Display
        System.out.println("Please fill out the information needed.");
        String firstName = getString("Enter First Name (Pangalan): ");
        String middleName = getString("Enter Middle Name (Gitnang Pangalan): ");
        String lastName = getString("Enter Last Name (Apelyido): ");

        // System.out.print("Does your name have suffix? (Y/N): ");
        // String choice = read.nextLine();

        String gender = getString("Enter Sex (Kasarian): ");

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String birthDate = getString("Enter date of birth (YYYY/MM/DD): ");

        try {
            LocalDate date = LocalDate.parse(birthDate, format);

            if (date.isBefore(LocalDate.now())) {
                Period period = Period.between(date, LocalDate.now()); // calculate the age from the year to present
                int age = period.getYears(); // get the years
                System.out.println("Age: " + age);
                throw new IllegalArgumentException("Invalid date of birth!");
            }
        } catch (Exception e) {
            System.out.println("You must follow the format YYYY/MM/DD.");
        }

        // Address
        String birthCountry = getString("Enter Birth Country: ");
        String birthProvince = getString("Enter Birth Province: ");
        String birthPlace = getString("Enter Birth City/Municipality: ");

        String maritalStatus = getString("Enter Marital Status: ");
        String bloodType = getString("Enter Blood Type: ");

        PersonalInfo personalInfo = new PersonalInfo(firstName, middleName, lastName, gender, birthDate, birthCountry,
                birthProvince, birthPlace, maritalStatus, bloodType);

        int nationalID = generate.nextInt(100) + 50; // genarate id using random numbers
        database.put(nationalID, personalInfo);
        System.out.println("Successfully registered. Thank you for using our System."
                + "\nYour ID number is " + nationalID);
    }

    // this method will retrieve the information
    public void retrieveInfo(int id) {

        if (database.containsKey(id)) {
            PersonalInfo person = database.get(id);
            System.out.println("ID number: " + id);
            person.displayInfo();
        } else {
            System.out.println("The ID Number " + id + " is not found");
        }
    }

    public void findID() {
        try {
            System.out.print("Enter national ID: ");
            int ID = read.nextInt();
            retrieveInfo(ID);
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
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
            System.out.println("1 - Register\n2 - Retrieve Information\n3 - Exit");
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