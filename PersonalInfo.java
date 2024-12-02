public class PersonalInfo {
    private String firstName, middleName, lastName, nameSuffix, gender, dateOfBirth, birthCountry, birthProvince,
            birthPlace, maritalStatus, bloodType;

    public PersonalInfo() {
    }

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

class Address extends PersonalInfo {
    private String type;
    private String address, barangay, place, province, country, zipCode, mobileNumber, email;

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
        System.out.println("City/Municipality (Lungsod/Bayan): " + place);
        System.out.println("Province (Probinsya): " + province);
        System.out.println("County (Bansa): " + country);
        System.out.println("ZIP Code: " + zipCode);
        System.out.println("Mobile no: " + mobileNumber);
        System.out.println("Email Address: " + email);
        System.out.println("---------------------------------------");
    }
}