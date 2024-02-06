package com.plainpixel.finalapplication;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import java.util.List;

public class PurchaseDetails_Adapater extends RecyclerView.Adapter<PurchaseDetails_Adapater.ViewHolder> {

    private List<PurchaseItem> purchaseItemList;

    public PurchaseDetails_Adapater(List<PurchaseItem> purchaseItemList)
    {
        this.purchaseItemList= purchaseItemList;
    }


    @NonNull
    @Override
    public PurchaseDetails_Adapater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_purchasedetails, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseDetails_Adapater.ViewHolder holder, int position) {
        PurchaseItem purchaseItem = purchaseItemList.get(position);

        holder.itemname.setText(purchaseItem.getItemName());
        holder.cost.setText("Kes" + " " + " " + purchaseItem.getItemCost());
        holder.quantity.setText("X" +  " " + " " + purchaseItem.getItemQuantity().toString());
    }

    @Override
    public int getItemCount() {
        return purchaseItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView itemname, cost, quantity, orderstatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemname = itemView.findViewById(R.id.itemname);
            cost = itemView.findViewById(R.id.cost);
            quantity = itemView.findViewById(R.id.quantity);
            //orderstatus = itemView.findViewById(R.id.orderstatus);
        }
    }
}