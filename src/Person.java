public class Person {

    //data member
    protected String name;
    private String gender;
    private String dateOfBirth;
    private String phoneNumber;
    private String address;
    private String icNum;

    public Person(){
        this("","","","","","");
    }

    public Person(String name, String gender, String dateOfBirth, String phoneNumber, String address, String icNum){
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.icNum = icNum;

    }

    public String getAddress() {
        return address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getIcNum() {
        return icNum;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setIcNum(String icNum) {
        this.icNum = icNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }




    //method

    /* [need set current date]
    public int calculateAge(String dateOfBirth) {
        return (currentDate - dateOfBirth);

    }
    */
}
