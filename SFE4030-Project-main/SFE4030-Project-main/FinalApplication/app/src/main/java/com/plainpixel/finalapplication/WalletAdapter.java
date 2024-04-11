package com.plainpixel.finalapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {

    private Context context;
    private List<WalletItem> walletItemList; // Replace String with the actual data type you want to display

    public WalletAdapter(Context context, List<WalletItem> walletItemList) {
        this.context = context;
        this.walletItemList = walletItemList;
    }


    @NonNull
    @Override
    public WalletAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_wallet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletAdapter.ViewHolder holder, int position) {

        WalletItem walletItem = walletItemList.get(position);

        holder.transactcode.setText(walletItem.getTranscationCode());
        holder.orderdate.setText(walletItem.getOrderDate());
        holder.totalordercost.setText("Kes" + "" + walletItem.getTotalOrderCost().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String transactionId = walletItem.getTranscationCode();
                Intent intent = new Intent(context, PurchaseDetails.class);
                intent.putExtra("transactionId", transactionId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return walletItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView transactcode, orderdate, totalordercost;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            transactcode = itemView.findViewById(R.id.transactcode);
            orderdate = itemView.findViewById(R.id.orderdate);
            totalordercost = itemView.findViewById(R.id.totalordercost);
        }
    }
}
