package ie.ul.tutorfinder;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab1 extends Fragment {

    FirebaseUser fbuser;
    DatabaseReference ref;
    Intent intent;
    private Button send;
    EditText txtBox;



    public tab1() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fbuser = FirebaseAuth.getInstance().getCurrentUser();
        send = getView().findViewById(R.id.SendBtn);
        txtBox = getView().findViewById(R.id.MessageTxt);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = txtBox.getText().toString();
                txtBox.setText("");

            }
        });




        return inflater.inflate(R.layout.fragment_tab1, container, false);
    }



        private void sendMessage(String sender, String recipient) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

            HashMap<String, Object> hasher = new HashMap<>();
            hasher.put("Send", sender);
            hasher.put("Receiver", recipient);
            hasher.put("Message", sender);


            reference.child("Messages").push().setValue(hasher);
        }









}
