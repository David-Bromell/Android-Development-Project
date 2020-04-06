package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ie.ul.tutorfinder.R;
import ie.ul.tutorfinder.User;
import com.google.firebase.auth.FirebaseAuth;


public class UserAdaptProfo extends RecyclerView.Adapter<UserAdaptProfo.ViewHolder> {

    private Context mContext;
    private List<User> mUser;

    public UserAdaptProfo(Context mContext, List<User> mUser){
        this.mContext = mContext;
        this.mUser = mUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);
        return new UserAdaptProfo.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = mUser.get(position);
        holder.username.setText(user.getName());


    }

    @Override
    public int getItemCount() {
        return mUser.size();
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
