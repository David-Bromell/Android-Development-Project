package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ie.ul.tutorfinder.MessageTabsActivity;
import ie.ul.tutorfinder.R;
import ie.ul.tutorfinder.User;
import com.google.firebase.auth.FirebaseAuth;


public class UserAdaptProfo extends RecyclerView.Adapter<UserAdaptProfo.ViewHolder> {

    private Context context;
    private List<User> User;

    public UserAdaptProfo(Context context, List<User> User){
        this.context = context;
        this.User = User;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new UserAdaptProfo.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User user = User.get(position);
        holder.username.setText(user.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageTabsActivity.class);
                intent.putExtra("userName", user.getName());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return User.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profo;

        //Constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.Username);
            profo = itemView.findViewById(R.id.profoImage);
        }
    }


}
