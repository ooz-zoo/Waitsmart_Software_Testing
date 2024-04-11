package com.plainpixel.finalapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<User> userArrayList;

    public MyAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = userArrayList.get(position);
        holder.FullName.setText(user.getFullName());
        holder.ContactEmail.setText(user.getContactEmail());
        holder.Balance.setText(user.getBalance());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    User clickedUser = userArrayList.get(clickedPosition);
                    openCashierProfilePage(clickedUser);
                }
            }
        });
    }

    private void openCashierProfilePage(User user) {
        Intent intent = new Intent(context, CashierProfilePage.class);
        // Pass the user's information to the CashierProfilePage activity
        intent.putExtra("user_full_name", user.getFullName());
        intent.putExtra("user_email", user.getContactEmail());
        intent.putExtra("user_contact", user.getContactNumber());
        intent.putExtra("user_balance", user.getBalance());
        intent.putExtra("user_level", user.getLevel());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView FullName, ContactEmail, Balance;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            FullName = itemView.findViewById(R.id.tvFullName);
            ContactEmail = itemView.findViewById(R.id.tvEmail);
            Balance = itemView.findViewById(R.id.tvBalance);
        }
    }
}
