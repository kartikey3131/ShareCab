package com.example.sharecab;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.listholder> {

    Context context;
    ArrayList<User2> data;

    public ListAdapter(Context context, ArrayList<User2> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ListAdapter.listholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.driver_data,parent,false);
        listholder l =new listholder(view);
        return l;
    }

    @Override
    public void onBindViewHolder(@NonNull listholder holder, int position) {
        final User2 u = data.get(position);
        holder.name.setText(data.get(position).getName());
//        holder.contact.setText(data.get(position).getContact());
        holder.price.setText(data.get(position).getPrice());

        String temp=data.get(position).getAvailability();
        if(temp=="Available")
        {
            holder.availability.setText(temp);}
        else{
            holder.availability.setTextColor(Color.parseColor("#FF0000"));
            holder.availability.setText(temp);
        }
        holder.model.setText(data.get(position).getModel());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,RiderProfile.class);
                intent.putExtra("Name",u.getName());
                intent.putExtra("Contact",u.getContact());
                intent.putExtra("Model",u.getModel());
                intent.putExtra("Number",u.getCarNumber());
                intent.putExtra("Price",u.getPrice());
                intent.putExtra("Status",u.getAvailability());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class listholder extends RecyclerView.ViewHolder {
        TextView name;
//        TextView contact;
        TextView model;
        TextView availability;
        TextView price;
        RelativeLayout linearLayout;
        public listholder(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.name);
//            contact =itemView.findViewById(R.id.contact);
            model =itemView.findViewById(R.id.vehModel);
            availability =itemView.findViewById(R.id.availability);
            price =itemView.findViewById(R.id.textViewPrice);
            linearLayout =itemView.findViewById(R.id.list_product);
        }
    }
}
