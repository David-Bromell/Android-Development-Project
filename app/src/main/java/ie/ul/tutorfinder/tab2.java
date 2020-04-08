package ie.ul.tutorfinder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 *
 */
public class tab2<UserAdapter> extends Fragment {

        private RecyclerView recyclerView;
        private UserAdapter userAdapter;
        private List<User> User;

public tab2() {
        // Required empty public constructor
        }

@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_tab2, container,false);
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        User= new ArrayList<>();
        readUser();
        return view;

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_tab2, container, false);
        }
        private void readUser(){
                final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                User.clear();
                                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                        User user = snapshot.getValue(User.class);
                                        assert user != null;
                                        assert firebaseUser !=null;
                                        if(user.getName()!= null){
                                                User.add(user);

                                        }

                                }


                        userAdapter = (UserAdapter) new UserAdaptProfo(getContext(), User);
                                recyclerView.setAdapter((RecyclerView.Adapter) userAdapter);





                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                });
        }
}
