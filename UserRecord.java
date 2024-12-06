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