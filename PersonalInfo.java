public class PersonalInfo {
    private String firstName, middleName, lastName, nameSuffix, gender, dateOfBirth, birthCountry, birthProvince,
            birthPlace, maritalStatus, bloodType;

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

    // getters
    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public String getBirthProvince() {
        return birthProvince;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public String getStatus() {
        return maritalStatus;
    }

    public String getBloodType() {
        return bloodType;
    }

    // display
    public void displayInfo() {
        System.out.println("---------------------------------------");
        System.out.println("          Personal Information         ");
        System.out.println("---------------------------------------");
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

    // add info to file database
    public void addInfo() {
        System.out.println("");
    }
}