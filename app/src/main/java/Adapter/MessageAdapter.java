package Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import ie.ul.tutorfinder.Messages;
import ie.ul.tutorfinder.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Messages> mMessageList;
    private FirebaseAuth mAuth;

    public MessageAdapter(Context context, List<Messages> mMessageList) {
        this.mMessageList = mMessageList;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layour,parent,false);
        return new MessageViewHolder(view);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {

        mAuth = FirebaseAuth.getInstance();
       String currentUserId = mAuth.getCurrentUser().getUid();

        Messages c =mMessageList.get(position);

        String from_user = c.getSender();

        if(from_user.equals(currentUserId)){
            holder.messageText.setBackgroundColor(Color.BLUE);
            holder.messageText.setTextColor(Color.WHITE);
        }else{

            holder.messageText.setBackgroundColor(R.drawable.message_text_background);
            holder.messageText.setTextColor(Color.WHITE);
        }

    holder.messageText.setText(c.getMessage());
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageText;
        public ImageView profoImage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            messageText= (TextView)itemView.findViewById(R.id.message_text_layout);
            profoImage= (ImageView)itemView.findViewById(R.id.message_profile_layout);


        }
    }

}


