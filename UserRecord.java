import java.util.Random;

class UsersRecord {
    private PersonalInfo personalInfo;
    private Address address;
    private Random generate = new Random();

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

    public int getGeneratedID() {
        return generate.nextInt(100) + 1; // creates id between 1 to 100

    }

    public String convertID() { // this method is used for writing in file
        int id = getGeneratedID();
        String StringID = Integer.toString(id); // converts id into string
        return StringID;
    }

    public void printInfo() {
        // length 32
        System.out.println(convertID() + " | " + personalInfo.getLastName() + " | " + personalInfo.getFirstName()
                + " | "
                + personalInfo.getMiddleName() + " | " + personalInfo.getGender() + " | "
                + personalInfo.getDateOfBirth() + " | " + personalInfo.getBirthCountry() + " | "
                + personalInfo.getBirthProvince()
                + " | " + personalInfo.getStatus() + " | " + personalInfo.getBloodType() + " | " + address.getAddress()
                + " | "
                + address.getBarangay() + " | " + address.getPlace() + " | " + address.getProvince() + " | "
                + address.getZipCode() + " | " + address.getMobileNum() + " | " + address.getEmail());
    }

    public void displayInfo() {
        personalInfo.displayInfo();
        address.displayInfo();
    }

}