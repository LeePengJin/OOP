public class Staff extends Person {

    //data members
    private String staffId;
    protected Person person;
    private String position;
    private int workHour;
    private double salaryRate;

    public Staff(){
        this("", null, "", 0, 0);
    }

    public Staff(String staffId, Person person, String position, int workHour, double salaryRate){

        this.staffId = staffId;
        this.person = person;
        this.position = position;
        this.workHour = workHour;
        this.salaryRate = salaryRate;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getPosition() {
        return position;
    }

    public int getWorkHour() {
        return workHour;
    }

    public double getSalaryRate() {
        return salaryRate;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setWorkHour(int workHour) {
        this.workHour = workHour;
    }

    public void setSalaryRate(double salaryRate) {
        this.salaryRate = salaryRate;
    }

    public double calSalary(int workHour, double salaryRate){
        return workHour * salaryRate;
    }
}

