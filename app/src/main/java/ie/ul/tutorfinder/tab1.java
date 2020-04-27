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


public class tab1 extends Fragment {

    private EditText txtBox;

    public tab1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        Button send = (Button) Objects.requireNonNull(getView()).findViewById(R.id.SendBtn);
        txtBox = getView().findViewById(R.id.MessageTxt);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = txtBox.getText().toString();
                txtBox.setText("");
                sendMessage("","", message);
            }
        });
    }

    // this is what should push the message to the DB as a hashmap
    private void sendMessage(String sender, String recipient, String message) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hasher = new HashMap<>();
        hasher.put("Sent by", sender);
        hasher.put("Received by", recipient);
        hasher.put("Message", message);

        reference.child("Messages").push().setValue(hasher);
    }
}
