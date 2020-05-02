package ie.ul.tutorfinder;

//USED TO HANDLE CONSTRUCTORS ALONG WITH GETTERS AND SETTERS FOR MESSAGING
public class Messages {

    //SENDER
    private String sender;
    //RECIPIENT
    private String recipient;
    //MESSAGE
    private String message;

    public Messages() {
        //Mandatory Default Constructor
    }

    //CONSTRUCTOR FOR SENDER RECIPIENT AND MESSAGE
    public Messages(String sender, String recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }


    //GETTERS AND SETTERS FOR SENDER, RECIEVER AND MESSAGE
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
