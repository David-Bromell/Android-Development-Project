package ie.ul.tutorfinder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;


public class ChatFragment extends Fragment {

    private EditText txtBox;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        //SETS SEND BUTTON = TO SEND BUTTON BY LAYOUT ID
        Button send = (Button) Objects.requireNonNull(getView()).findViewById(R.id.SendBtn);
        //SETS TEXT BOX = TO TEXT BOX BY LAYOUT ID
        txtBox = getView().findViewById(R.id.MessageTxt);
        //CREATES BUTTON LISTENER
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CONVERTS MESSAGE IN TEXT BOX TO STRING
                String message = txtBox.getText().toString();
                //RESETS TEXT BOX TO BE EMPTY AFTER MESSAGE IS SENT
                txtBox.setText("");
                //HERE SENDER RECIPIENT AND MESSAGE OBJECTS ARE PASSED TO SEND MESSAGE FUNCTION
                sendMessage("","", message);
            }
        });
    }

    // SEND MESSAGE FUNCTION USES HASHMAP TO PUSH MESSAGE TO DATABASE
    private void sendMessage(String sender, String recipient, String message) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        //HASHMAP CREATED AND SENDER, RECIEVER AND MESSAGE ARE PASSED TO FIREBASE REALTIME DB
        HashMap<String, Object> hasher = new HashMap<>();
        hasher.put("Sent by", sender);
        hasher.put("Received by", recipient);
        hasher.put("Message", message);
        //SETS ABOVE VARIABLES TO RELEVANT VALUES IN MESSAGES
        reference.child("Messages").push().setValue(hasher);
    }
}
