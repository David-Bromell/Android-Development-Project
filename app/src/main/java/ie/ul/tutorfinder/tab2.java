package ie.ul.tutorfinder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.UserAdaptProfo;


/**
 * A simple {@link Fragment} subclass.
 * CONTACT LIST TAB (CONTACTS)
 */
public class tab2<UserAdapter> extends Fragment {
        // RECYCLER VIEW USED TO DISPLAY CONTACTS
        private RecyclerView recyclerView;
        //BRIDGE BETWEEN RECYCLER VIEW AND DATABASE
        private UserAdapter userAdapter;
        //ARRAY CREATED TO HOLD USERS
        private List<User> User;

        public tab2() {
        // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
                // Inflate the layout for this fragment
                //CREATES VIEW AND INFLATES SAME
                View view =inflater.inflate(R.layout.fragment_tab2, container,false);
                //SETS RECYCLERVIEW = TO RECYCLER VIEW IN LAYOUT
                recyclerView=view.findViewById(R.id.recyclerView);
                //GIVES IT A FIXED SIZE
                recyclerView.setHasFixedSize(true);
                //ARRANGES LAYOUT LINERALY
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                //SET USER TO ARRAYLIST
                User= new ArrayList<>();
                readUser();
                //return inflater.inflate(R.layout.fragment_tab2, container, false);
                return view;
        }
        // FUNCTION TO READ USERS FROM DATABASE
        private void readUser(){
                //GETS CURRENT USER AND SETS THEM TO CURRENT FIREBASE USER
                final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                //SETS REFERENCE = USERS UNDER USERS PATH IN DATABASE
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                //CLEARS USERS TO START WITH EMPTY LIST
                                User.clear();
                                //FOR LOOP TO ITERATE THROUGH USERS AND ALL DETAILS CONTAINED ABOUT THEM IN THE DATABASE (GETCHILDREN IS USED TO GET SUB COLUMNS AND FOR LOOP ALLOWS IT TO ITERATE THROUGH)
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        // SETS USER TO CURRENT VALUE
                                        User user = snapshot.getValue(User.class);
                                        //CHECKS USER IS NOT EMPTY
                                        assert user != null;
                                        //CHECKS FIREBASE USER IS NOT EMPTY
                                        assert firebaseUser != null;
                                        //ADDS USERS AS FOUND IN FOR LOOP
                                        User.add(user);
                                        /*if (user.getUserType().equals("Student")) {
                                                User.add(user);
                                        }
                                        else if (user.getUserType().equals("Tutor")) {
                                                User.add(user);
                                        }*/
                                }

                                userAdapter = (UserAdapter) new UserAdaptProfo(getContext(), User);
                                //SETS ADAPTER TO RECYCLER VIEW
                                recyclerView.setAdapter((RecyclerView.Adapter) userAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                });
        }
}
