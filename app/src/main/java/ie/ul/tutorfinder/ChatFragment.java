package ie.ul.tutorfinder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import Adapter.MessageAdapter;


public class ChatFragment extends Fragment {


    private EditText txtBox;
    private TextView bubble;
    private RecyclerView mMessagesList;
    private final List<Messages> messagesList = new ArrayList<>();
    private LinearLayoutManager mLinearLayout;
    private MessageAdapter mAdapter;
    private FirebaseUser mCurrentUserId;
    private String mChatUser;
    private FirebaseAuth mAuth;
   // private HashMap<String, Object> hasher = new HashMap<>();

    private DatabaseReference mRootRef;
    private FirebaseAuth fbuser;
    public String dbref;




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
        mAuth = FirebaseAuth.getInstance();
        txtBox = getView().findViewById(R.id.MessageTxt);
        mCurrentUserId = mAuth.getCurrentUser();
        mAdapter = new MessageAdapter(getContext(), messagesList);
        mMessagesList= (RecyclerView)getView().findViewById(R.id.messages_list);
        mLinearLayout = new LinearLayoutManager(getActivity());
        mMessagesList.setHasFixedSize(true);
        mMessagesList.setLayoutManager(mLinearLayout);
        mMessagesList.setAdapter(mAdapter);
        loadMessages();


        //CREATES BUTTON LISTENER
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CONVERTS MESSAGE IN TEXT BOX TO STRING
                String message = txtBox.getText().toString();
                //RESETS TEXT BOX TO BE EMPTY AFTER MESSAGE IS SENT
                txtBox.setText("");

                //HERE SENDER RECIPIENT AND MESSAGE OBJECTS ARE PASSED TO SEND MESSAGE FUNCTION
                sendMessage("sender","reciever", message);
            }
        });
    }


    private void loadMessages() {


        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //SETS REFERENCE = USERS UNDER USERS PATH IN DATABASE
        bubble = getView().findViewById(R.id.message_text_layout);
        final String message = txtBox.getText().toString();
      //  final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().getRoot();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Messages").getRoot();
        reference.child("Messages").child(message).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Messages Message = dataSnapshot.child(message).getValue(Messages.class);
               //reference.child("Messages").push().setValue(Message);
                messagesList.add(Message);
                mAdapter.notifyDataSetChanged();
                mAdapter = new MessageAdapter(getContext(), messagesList);
                mMessagesList.setAdapter((RecyclerView.Adapter) mAdapter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
