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
    }
}

class PermanentAddress {
    private String address;
    private String barangay;
    private String place;
    private String province;
    private String country;
    private String zipCode;

    public void setAddress(String address, String barangay, String place, String province, String country,
            String code) {
        this.address = address;
        this.barangay = barangay;
        this.place = place;
        this.province = province;
        this.country = country;
        this.zipCode = code;
    }

    public void displayInfo() {
        System.out.println("Permanent Address");
        System.out.println("Address (Tirahan): " + address);
        System.out.println("Barangay: " + barangay);
        System.out.println("City/Municipality *Lungsod/Bayan): " + place);
        System.out.println("Province (Probinsya): " + province);
        System.out.println("County (Bansa): " + country);
        System.out.println("ZIP Code: " + zipCode);
    }

}

class PresentAddress extends PermanentAddress {
    private String deliverID;
    private String mobileNumber;
    private String email;

    public void additionalInfo(String deliver, String number, String email) {
        this.deliverID = deliver;
        this.mobileNumber = number;
        this.email = email;
    }

    public void displayInfo() {
        System.out.println("Deliver my PSN/PhilID to: " + deliverID);
        System.out.println("Mobile no: " + mobileNumber);
        System.out.println("Email Address: " + email);
    }
}

class Admin {

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
        // PersonalInfo
        // System.out.print("Enter First Name (Pangalan): ");
        String firstName = getString("Enter First Name (Pangalan): ");
        System.out.print("Enter Middle Name (Gitnang Pangalan): ");
        String middleName = read.nextLine();
        System.out.print("Enter Last Name (Apelyido): ");
        String lastName = read.nextLine();

        // System.out.print("Does your name have suffix? (Y/N): ");
        // String choice = read.nextLine();

        System.out.print("Enter Sex (Kasarian): ");
        String gender = read.nextLine();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        System.out.print("Enter date of birth (YYYY/MM/DD): ");
        String birthDate = read.nextLine();

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
        System.out.print("Enter Birth Country: ");
        String birthCountry = read.nextLine();
        System.out.print("Enter Birth Province: ");
        String birthProvince = read.nextLine();
        System.out.print("Enter Birth City/Municipality: ");
        String birthPlace = read.nextLine();

        System.out.print("Enter Marital Status: ");
        String maritalStatus = read.nextLine();
        System.out.print("Enter Blood Type: ");
        String bloodType = read.nextLine();

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