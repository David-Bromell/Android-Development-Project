package ie.ul.tutorfinder;

public class User {
    private String Name, email, phone, userType, Birthdate = null;

    public User() {

    }

    public User(String name, String email, String phone, String userType, String birthdate) {
        this.Name = name;
        this.email = email;
        this.phone = phone;
        this.userType = userType;
        this.Birthdate = birthdate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(String birthdate) {
        Birthdate = birthdate;
    }



}
