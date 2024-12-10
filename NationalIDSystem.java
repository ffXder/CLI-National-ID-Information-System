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
    private int generateID = generate.nextInt(1000) + 500; // creates id between 500 to 1000
    private Utils utils = new Utils();

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
        System.out.println("------------------------------------------------------");
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

        utils.clearConsole(); // clears console
        record.displayInfo(); // then display info's inputted by user

        String confirmation = getString("Press Y to confirm submission or Press N to cancel\n");

        // confirmation before submitting data
        if (confirmation.equalsIgnoreCase("Y")) {
            System.out.println("Successfully registered. Thank you for using our System."
                    + "\nYour ID number is " + generateID);
            saveInfo();
        } else if (confirmation.equalsIgnoreCase("N")) {
            System.out.println("Submission Cancelled.");
        } else {
            System.out.println("Invalid input. Please enter Y or N.");

        }

    }

    // this method will save the information in file as database
    private void saveInfo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Database.txt", true))) {
            for (UsersRecord record : database) {
                PersonalInfo info = record.getInfo();
                Address address = record.getAddress();

                // 34 length
                // 18 length without |
                writer.write(generateID + " | " + info.getLastName() + " | " + info.getFirstName()
                        + " | "
                        + info.getMiddleName() + " | " + info.getGender() + " | "
                        + info.getDateOfBirth() + " | " + info.getBirthCountry() + " | "
                        + info.getBirthProvince() + " | " + info.getBirthPlace()
                        + " | " + info.getStatus() + " | " + info.getBloodType() + " | "
                        + address.getAddress()
                        + " | "
                        + address.getBarangay() + " | " + address.getPlace() + " | " + address.getProvince() + " | "
                        + address.getCountry() + " | "
                        + address.getZipCode() + " | " + address.getMobileNum() + " | " + address.getEmail());
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("ERROR");
        }

    }

    public void findID() {
        System.out.print("Enter national ID: ");
        String ID = read.nextLine().trim();
        loadInfo(ID);
    }

    // load the info from file using BufferedReader
    private void loadInfo(String searchID) {
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("Database.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String delimiter[] = line.split("\\|"); // splits the line
                String id = delimiter[0].trim(); // finds the id using delimiter
                if (id == searchID) {
                    System.out.println(
                            "ID | Last Name | First Name | Middle Name | Date of Birth | Birth Country |  Birth Province | Birth City/Municipality | Marital Status | Blood Type | Address | Barangay | City/Munipality | Province | Country | Zip Code | Mobile No. | Email");
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

    public void viewRequirements() {
        utils.clearConsole();
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("|     The registrant is encouraged to bring any of the following documents:     |");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println(
                " 1. PSA-issued Certificate of Live Birth and one (1) government-issued identification\n    document which bears a full name, front-facing photograph, and signature or thumb mark;");
        System.out.println(" 2. Philippine Passport or ePassport issued by the Department of Foreign Affairs (DFA);");
        System.out.println(
                " 3. Unified Multi-purpose ldentification (UMID) Card issued by \n    the Government Service lnsurance System (GSIS) or Social Security Systen (SSS); or");
        System.out.println(
                " 4. Student's License Permit or Non- Professional/Professional Driver's License issued by the LTO.");
    }

    public void locatedRegistration() {
        utils.clearConsole();
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("REGION IV-A - CALABARZON");
        System.out.println("BATANGAS");
        System.out.println("1. G/F Bldg. C Fiesta World, Marawoy, Lipa City, Batangas"
                + "\n2. Unit CTZ-GF-Block E 5 & 6 Citimart Plaza Complex, Calicanto Batangas City\n");
        System.out.println("CAVITE");
        System.out.println(
                "Lower Ground Floor Boochico Holding Inc., Governor’s Drive, Brgy. San Agustin, Trece Martires City, Cavite\n");
        System.out.println("LAGUNA");
        System.out.println("Go Bldg., Barangay Bagong Bayan, Maharlika Highway, San Pablo City, Laguna\n");
        System.out.println("RIZAL");
        System.out.println(
                "G/F Games and Garments International Inc.,# 6 Sen. Lorenzo Sumulong Memorial Circle (formerly Circumferential Rd)., Brgy. San Jose, Antipolo City\n");
        System.out.println("QUEZON");
        System.out.println("Third Floor, Pacific Mall, Landco Business Park, M.L. Tagarao St., Lucena City");
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    // for admin methods
    public void editRecord() {
        File dataFile = new File("Database.txt"); // data file
        File tempDataFile = new File("temp.txt"); // temp file
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempDataFile))) {

            // edit method
            System.out.print("Enter ID that you want to Edit : ");
            String id = read.nextLine().trim();

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String delimiter[] = currentLine.split("\\|");
                String searchID = delimiter[0].trim();

                if (searchID.equals(id)) {
                    found = true;
                    System.out.println("Record: \n" + currentLine);

                    System.out.println("Leave blank to keep the current info");
                    String newLastName = getString("Enter new Last Name: ");
                    String newFirstName = getString("Enter new First Name: ");
                    String newMiddleName = getString("Enter new Middle Name: ");
                    String newAddress = getString("Enter new Address: ");
                    String newBarangay = getString("Enter new Barangay: ");
                    String newPlace = getString("Enter new City/Municipality: ");
                    String newProvince = getString("Enter new Province: ");
                    String newCountry = getString("Enter new Country: ");
                    String newZipCode = getString("Enter new Zip Code: ");
                    String newMobileNo = getString("Enter new Mobile No.: ");
                    String newEmail = getString("Enter new Email: ");

                    // if the newRecords is empty it will not change the name else it will add the
                    // new
                    // first name
                    String updateLastName = newLastName.isEmpty() ? delimiter[1].trim() : newLastName;
                    String updateFirstName = newFirstName.isEmpty() ? delimiter[2].trim() : newFirstName;
                    String updateMiddleName = newMiddleName.isEmpty() ? delimiter[3].trim() : newMiddleName;
                    String updateAddress = newAddress.isEmpty() ? delimiter[11].trim() : newAddress;
                    String updateBarangay = newBarangay.isEmpty() ? delimiter[12].trim() : newBarangay;
                    String updatePlace = newPlace.isEmpty() ? delimiter[13].trim() : newPlace;
                    String updateProvince = newProvince.isEmpty() ? delimiter[14].trim() : newProvince;
                    String updateCountry = newCountry.isEmpty() ? delimiter[15].trim() : newCountry;
                    String updateZipCode = newZipCode.isEmpty() ? delimiter[16].trim() : newZipCode;
                    String updateMobileNo = newMobileNo.isEmpty() ? delimiter[17].trim() : newMobileNo;
                    String updateEmail = newEmail.isEmpty() ? delimiter[18].trim() : newEmail;

                    String updatedRecord = id + " | " + updateLastName + " | " + updateFirstName + " | "
                            + updateMiddleName + " | " + delimiter[4] + " | " + delimiter[5] + " | " + delimiter[6]
                            + " | " + delimiter[7] + " | " + delimiter[8] + " | " + delimiter[9] + " | " + delimiter[10]
                            + " | " + updateAddress + " | " + updateBarangay + " | " + updatePlace + " | "
                            + updateProvince + " | "
                            + updateCountry + " | " + updateZipCode + " | " + updateMobileNo + " | " + updateEmail;

                    writer.write(updatedRecord);
                    writer.newLine();

                } else {
                    writer.write(currentLine);
                    writer.newLine();
                }

            }

            if (!found) {
                System.out.println("ID not found");
            }

            if (tempDataFile.exists() && found) {
                writer.close();
                reader.close();
                if (dataFile.delete()) {
                    tempDataFile.renameTo(dataFile);
                    System.out.println("Successfully changed record");
                } else {
                    System.out.println("Failed to replace the original database file.");
                }
            } else {
                tempDataFile.delete();
            }

        } catch (IOException e) {
            System.out.println("ERROR");
        }
    }

    // this method is used to delete a specific record in database file
    public void deleteRecord() {
        File dataFile = new File("Database.txt"); // data file
        File tempDataFile = new File("temp.txt"); // temp file
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempDataFile))) {
            System.out.print("Enter ID that you want to Delete : ");
            String id = read.nextLine().trim();

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String delimiter[] = currentLine.split("\\|"); // delimiter separates using split
                String searchID = delimiter[0].trim();

                if (searchID.equals(id)) { // database contains the ID to delete
                    found = true;
                    System.out.println("Record that you want to delete: \n" + currentLine);
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
                    "ID | Last Name | First Name | Middle Name | Date of Birth | Birth Country | Birth City/Municipality | Marital Status | Blood Type | Address | Barangay | City/Munipality | Province | Country | Zip Code | Mobile No. | Email");
            while ((line = recordReader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("---------------------------");
        } catch (IOException e) {
            System.out.println("ERROR" + e.getMessage());
        }
    }
}