package ie.ul.tutorfinder;

import java.util.Date;

public class User {
    public String Name, email, phone, userType, Birthdate = null;

    public User() {

    }

    public User(String name, String email, String phone, String userType, String birthdate) {
        this.Name = name;
        this.email = email;
        this.phone = phone;
        this.userType = userType;
        this.Birthdate = birthdate;
    }
}
