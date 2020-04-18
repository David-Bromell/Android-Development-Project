package ie.ul.tutorfinder;

public class User {
    private String Name, email, phone, userType, Birthdate, Longitude, Latitude = null;

    public User() {

    }

    public User(String name, String email, String phone, String userType, String birthdate, String longitude, String Latitude) {
        this.Name = name;
        this.email = email;
        this.phone = phone;
        this.userType = userType;
        this.Birthdate = birthdate;
        this.Longitude = longitude;
        this.Latitude = Latitude;
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


    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
}


