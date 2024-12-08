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

    public void findID();
}

public class NationalIDSystem implements Forms {
    private Map<Integer, UsersRecord> database = new HashMap<>(); // hashmap to store data with key and value
    private Scanner read = new Scanner(System.in);
    private Random generate = new Random();

    // for prompt
    private String getString(String prompt) {
        if (prompt.equalsIgnoreCase("EXIT")) {
            return prompt;
        } else {
            System.out.print(prompt);
            return read.nextLine().trim();
        }
    }

    // this method will validate if the user input a valid gender
    private String validateGender() {
        String gender;
        while (true) {
            gender = getString("Enter Sex (Kasarian): ");
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                return gender;
            } else {
                System.out.println("Invalid gender. Please enter Male or Female.");
            }
        }

    }

    // this method will validate if the user input a valid date
    private String validateBirthDate() {
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
                int age = period.getYears(); // compute the birthdata input and current year

                // validates if the date for registration is valid
                if (age < 0 || age > 120) {
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

    private String validateStatus() {
        while (true) {
            String maritalStatus = getString("Enter Marital Status: ");

            if (maritalStatus.matches("[S|s]ingle|[M|m]arried|[W|w]idowed")) {
                return maritalStatus;
            } else {
                System.out.println("Invalid! Please try again");
            }
        }
    }

    // this method will validate the blood type inputted by users
    private String validatedBloodType() {
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

    private int generateID() {
        return generate.nextInt(100) + 1;

    }

    public void fillOut() {

        // Display
        System.out.println("Please fill out the information needed.");
        String firstName = getString("Enter First Name (Pangalan): ");
        String middleName = getString("Enter Middle Name (Gitnang Pangalan): ");
        String lastName = getString("Enter Last Name (Apelyido): ");

        String gender = validateGender().toUpperCase();
        String birthDate = validateBirthDate();
        String birthCountry = getString("Enter Birth Country: ");
        String birthProvince = getString("Enter Birth Province: ");
        String birthPlace = getString("Enter Birth City/Municipality: ");
        String maritalStatus = validateStatus();
        String bloodType = validatedBloodType().toUpperCase();

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

        Address add = new Address(address, barangay, place, province, country, zipCode, mobileNumber, email); // adds
                                                                                                              // address

        // personalInfo.displayInfo(); // display the info
        // add.displayInfo(); // display the address

        String confirmation = getString("Press Y to confirm submission or Press N to cancel\n");

        // confirmation before submitting data
        if (confirmation.equalsIgnoreCase("Y")) {
            int nationalID = generateID(); // genarate id using random numbers
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

    // this method will save the information in file as database using
    // BufferedWriter
    private void saveInfo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Database.txt", true))) {
            for (Map.Entry<Integer, UsersRecord> e : database.entrySet()) {
                int id = e.getKey();
                UsersRecord record = e.getValue();
                PersonalInfo info = record.getInfo();
                Address address = record.getAddress();

                // 32
                writer.write(id + " | " + info.getLastName() + " | " + info.getFirstName() + " | "
                        + info.getMiddleName() + " | " + info.getGender() + " | "
                        + info.getDateOfBirth() + " | " + info.getBirthCountry() + " | " + info.getBirthProvince()
                        + " | " + info.getStatus() + " | " + info.getBloodType() + " | " + address.getAddress() + " | "
                        + address.getBarangay() + " | " + address.getPlace() + " | " + address.getProvince() + " | "
                        + address.getZipCode() + " | " + address.getMobileNum() + " | " + address.getEmail());
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("ERROR");
        }

    }

    // load the info from file using BufferedReader
    private void loadInfo(int searchID) {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("Database.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String delimiter[] = line.split("\\|");
                int id = Integer.parseInt(delimiter[0].trim());
                if (id == searchID) {
                    System.out.println(
                            "ID | Last Name | First Name | Middle Name | Date of Birth | Birth Country |  Birth Province | Birth City/Municipality | Marital Status | Blood Type | Address | Barangay | City/Munipality | Province | Zip Code | Mobile No. | Email");
                    System.out.println(line);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("ID NOT FOUND");
            }

        } catch (IOException e) {
            System.out.println("ERROR");
        }
    }

    // for admin methods
    public void editRecord() {

    }

    public void deleteRecord() {

    }

    public void checkRecords() {
        try (BufferedReader recordReader = new BufferedReader(new FileReader("Database.txt"))) {
            String line;
            System.out.println("---------------------------");
            System.out.println("|          Records        | ");
            System.out.println("---------------------------");
            System.out.println(
                    "ID | Last Name | First Name | Middle Name | Date of Birth | Birth Country | Birth City/Municipality | Marital Status | Blood Type | Address | Barangay | City/Munipality | Province | Zip Code | Mobile No. | Email");
            while ((line = recordReader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("---------------------------");
        } catch (IOException e) {
            System.out.println("ERROR" + e.getMessage());
        }
    }
    /*
     * public void retrieveInfo(int id) {
     * loadInfo(id);
     * if (database.containsKey(id)) {
     * UsersRecord record = database.get(id);
     * System.out.println("ID number: " + id);
     * record.displayInfo();
     * // loadInfo(id); // find id in database
     * } else {
     * System.out.println("The ID Number " + id + " is not found");
     * }
     * }
     */

    public void findID() {
        try {
            System.out.print("Enter national ID: ");
            int ID = read.nextInt();
            read.nextLine();
            loadInfo(ID);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter your Number ID");
            read.nextLine();
        }
    }
}