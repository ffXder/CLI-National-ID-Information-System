public class Address {
    private String address, barangay, place, province, country, zipCode, mobileNumber, email;

    public Address(String address, String barangay, String place, String province, String country,
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

    // getters
    public String getAddress() {
        return address;
    }

    public String getBarangay() {
        return barangay;
    }

    public String getPlace() {
        return place;
    }

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getMobileNum() {
        return mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    // display
    public void displayInfo() {
        System.out.println("---------------------------------------");
        System.out.println("                Address                ");
        System.out.println("---------------------------------------");
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
