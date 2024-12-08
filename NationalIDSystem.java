import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
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
    private ArrayList<UsersRecord> database = new ArrayList<>(); // stores the data
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
                if (age < 0 || age > 160) {
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

        UsersRecord record = new UsersRecord(personalInfo, add);
        database.add(record); // adds into arraylist

        // record.displayInfo();

        String confirmation = getString("Press Y to confirm submission or Press N to cancel\n");

        // confirmation before submitting data
        if (confirmation.equalsIgnoreCase("Y")) {
            System.out.println("Successfully registered. Thank you for using our System."
                    + "\nYour ID number is " + record.getGeneratedID());
            saveInfo();
        } else if (confirmation.equalsIgnoreCase("N")) {
            System.out.println("Submission Cancelled.");
        } else {
            System.out.println("Invalid input. Please enter Y or N.");
        }

    }

    // this method will save the information in file as database using
    // BufferedWriter
    private void saveInfo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Database.txt", true))) {
            for (UsersRecord record : database) {
                PersonalInfo info = record.getInfo();
                Address address = record.getAddress();

                // 32 length
                writer.write(record.convertID() + " | " + info.getLastName() + " | " + info.getFirstName()
                        + " | "
                        + info.getMiddleName() + " | " + info.getGender() + " | "
                        + info.getDateOfBirth() + " | " + info.getBirthCountry() + " | "
                        + info.getBirthProvince()
                        + " | " + info.getStatus() + " | " + info.getBloodType() + " | "
                        + address.getAddress()
                        + " | "
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
                String delimiter[] = line.split("\\|"); // splits the line
                int id = Integer.parseInt(delimiter[0].trim()); // finds the id using delimiter
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

    public void viewRequirements() {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("|     The registrant is encouraged to bring any of the following documents:     |");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println(
                " - PSA-issued Certificate of Live Birth and one (1) government-issued identification\n   document which bears a full name, front-facing photograph, and signature or thumb mark;");
        System.out.println(" - Philippine Passport or ePassport issued by the Department of Foreign Affairs (DFA);");
        System.out.println(
                " - Unified Multi-purpose ldentification (UMID) Card issued by \n   the Government Service lnsurance System (GSIS) or Social Security Systen (SSS); or");
        System.out.println(
                " - Student's License Permit or Non- Professional/Professional Driver's License issued by the LTO.");
    }

    // for admin methods
    public void editRecord() {

    }

    // this method is used to delete a specific record in database file
    public void deleteRecord() {
        File dataFile = new File("Database.txt"); // data file
        File tempDataFile = new File("tempdatabase.txt"); // temp file
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempDataFile))) {
            System.out.print("Enter national ID: ");
            String id = read.nextLine();

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String delimiter[] = currentLine.split("\\|"); // delimiter separates using split
                String searchID = delimiter[0].trim();

                if (searchID.contains(id)) { // database contains the ID to delete
                    found = true;
                    continue;
                }

                writer.write(currentLine); // write all the current to temp file
                writer.newLine();

            }
            if (found) {
                // closes the reader and writer
                // this is important to avoid issues when deleting file
                reader.close();
                writer.close();
                if (dataFile.delete()) { // delete database.txt
                    if (tempDataFile.renameTo(dataFile)) { // then renames the tempDataFile to "Database.txt"
                        System.out.println("Successfully removed ID: " + id);
                    } else {
                        System.out.println("Failed to rename");
                    }
                } else {
                    System.out.println("Failed to delete");
                }
            } else {
                System.out.println("ID NOT FOUND");

            }
        } catch (IOException e) {
            System.out.println("ERROR");
        }
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

}