package ie.ul.tutorfinder;

public class friend_requests {


    public friend_requests(){

    }

    public friend_requests(String request_type) {
        this.request_type = request_type;
    }

    private String request_type;

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }
}