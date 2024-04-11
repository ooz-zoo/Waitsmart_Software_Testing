package com.plainpixel.finalapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    final CartAdapter cartAdapter = this;

    private List<Cartitem> cartItems = new ArrayList<>();
    private Context context;
    private OnQuantityChangeListener quantityChangeListener;

    private TextView TotalKes;

    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }

    public CartAdapter(List<Cartitem> cartItems, Context context, OnQuantityChangeListener listener, TextView TotalKes)
    {
        this.cartItems = cartItems;
        this.context = context;
        this.quantityChangeListener = listener;
        this.TotalKes = TotalKes;
    }

    @NonNull
    @Override

    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        // Bind data to the ViewHolder views
        Cartitem cartItem = cartItems.get(position);

        holder.itemtitle.setText(cartItem.getItemName());
        holder.itemcost.setText("Kes " + cartItem.getItemPrice());
        holder.count.setText(String.valueOf(cartItem.getQuantity()));
        holder.itemtotalcost.setText(String.valueOf(cartItem.getTotalcost()));
        holder.itemtotalcost.setText("Kes " + cartItem.getTotalcost());


        if (cartItem.getItemImage() != null && !cartItem.getItemImage().isEmpty()) {
            Picasso.get().load(cartItem.getItemImage()).into(holder.itemImage);
        }

        holder.minusportion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantitystring = cartItem.getQuantity();
                int quantity = Integer.parseInt(quantitystring);
                if (quantity > 1)
                {
                    quantity--;
                    cartItem.setQuantity(String.valueOf(quantity));

                    String pricestring = cartItem.getItemPrice();
                    int itemprice = Integer.parseInt(pricestring);

                    int totalcost = itemprice * quantity;
                    cartItem.setTotalcost(totalcost);

                    holder.itemtotalcost.setText("Kes" + totalcost);

                    notifyItemChanged(holder.getAdapterPosition());

                    notifyDataSetChanged();
                    if(quantityChangeListener != null)
                    {
                        quantityChangeListener.onQuantityChanged();
                    }
                }
            }
        });

        holder.plusportion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantitystring = cartItem.getQuantity();
                int quantity = Integer.parseInt(quantitystring);

                quantity++;
                cartItem.setQuantity(String.valueOf(quantity));

                String pricestring = cartItem.getItemPrice();
                int itemprice = Integer.parseInt(pricestring);

                int totalcost = itemprice * quantity;
                cartItem.setTotalcost(totalcost);

                holder.itemtotalcost.setText("Kes" + totalcost);

                notifyItemChanged(holder.getAdapterPosition());

                notifyDataSetChanged();
                if(quantityChangeListener != null)
                {
                    quantityChangeListener.onQuantityChanged();
                }
            }
        });

        holder.deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = holder.getAdapterPosition();
                if(position != RecyclerView.NO_POSITION)
                {
                    Cartitem cartItem = cartItems.get(position);
                    if (cartItems.size() == 1)
                    {
                        cartItems.clear();
                    } else {
                        cartItems.remove(position);
                        cartAdapter.notifyItemRemoved(position);
                    }

                   // remove a cart item from the SharedPreferences storage by deleting its corresponding preference entries using the unique key
                    SharedPreferences sharedPreferences = context.getSharedPreferences("CartPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String uniquekey = cartItem.getItemName() + "_" + cartItem.getItemPrice() + "_" + cartItem.getItemImage() + "_" + cartItem.getQuantity() + "_" + cartItem.getTotalcost();
                    editor.remove("itemName_" + uniquekey);
                    editor.remove("itemPrice_" + uniquekey);
                    editor.remove("itemImage_" + uniquekey);
                    editor.remove("itemquantity_" + uniquekey);
                    editor.remove("itemtotalcost_" + uniquekey);

                    int deletedItemCost = cartItem.getTotalcost();


                    //cartItems.remove(position);
                    notifyItemRemoved(position);

                    //Remove the cost of the deleted item from the total cost
                    int totalCartCost = calculateTotalCartCost();

                    // Update the TextView displaying the total cost
                    TotalKes.setText("Kes " + totalCartCost);

                    editor.clear();

                    for (int i = 0; i < cartItems.size(); i++) {
                        Cartitem cartitem = cartItems.get(i);
                        editor.putString("itemName_" +uniquekey, cartItem.getItemName());
                        editor.putString("itemPrice_"+uniquekey, cartItem.getItemPrice());
                        editor.putString("itemImage_" + uniquekey, cartItem.getItemImage());
                        editor.putString("itemquantity_" + uniquekey, cartItem.getQuantity());
                        editor.putInt("itemtotalcost_" + uniquekey, cartItem.getTotalcost());
                    }
                    editor.apply();

                    if (cartItems.isEmpty())
                    {
                        Intent intent = new Intent(context, empty_cart.class);
                        context.startActivity(intent);
                        ((Activity) context) .finish();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    private int calculateTotalCartCost()
    {
        int totalCost = 0;
        for (Cartitem cartitem : cartItems)
        {
            totalCost += cartitem.getTotalcost();
        }
        return totalCost;
    }


    public static class CartViewHolder extends RecyclerView.ViewHolder
    {
        ImageView itemImage, minusportion, plusportion, deleteitem;
        TextView count, itemtitle, itemcost, itemtotalcost;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the views
            itemtitle = itemView.findViewById(R.id.itemtitle);
            itemcost = itemView.findViewById(R.id.itemcost);
            itemImage = itemView.findViewById(R.id.itemImage);
            count = itemView.findViewById(R.id.count);
            itemtotalcost = itemView.findViewById(R.id.itemtotalcost);
            minusportion = itemView.findViewById(R.id.minusportion);
            plusportion = itemView.findViewById(R.id.plusportion);
            deleteitem = itemView.findViewById(R.id.deleteitem);

        }
    }
}
