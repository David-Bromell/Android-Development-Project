package ie.ul.tutorfinder;

//USED TO HANDLE CONSTRUCTORS ALONG WITH GETTERS AND SETTERS FOR MESSAGING
public class Messages {

    //Sender
    private String Sender;
    //Recipient
    private String Recipient;
    //Message
    private String Message;


    public Messages() {
        //Mandatory Default Constructor
    }

    //CONSTRUCTOR FOR Sender Recipient AND Message
    public Messages(String Message, String Sender, String Recipient) {
        this.Message = Message;
        this.Sender = Sender;
        this.Recipient = Recipient;

    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
    public String getMessage() {
        return Message;
    }



    //GETTERS AND SETTERS FOR Sender, RECIEVER AND Message
    public void setSender(String Sender) {
        this.Sender = Sender;
    }
    public String getSender() {
        return Sender;
    }

    public void setRecipient(String Recipient) {
        this.Recipient = Recipient;
    }

    public String getRecipient() {
        return Recipient;
    }



}
