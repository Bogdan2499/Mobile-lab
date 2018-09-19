package com.nulp.vp.labs_aplication.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nulp.vp.labs_aplication.Model.User;
import com.nulp.vp.labs_aplication.R;

import java.util.ArrayList;

/**
 * Created by Vova0199 on 15.09.2018.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.RecViewHolder> {
    private ArrayList<User> alist;

    public CustomAdapter(ArrayList<User> alist) {
        this.alist = alist;
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new RecViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecViewHolder holder, int position) {
        User user = alist.get(position);
        holder.tvName.setText(user.getName());
        holder.tvSurname.setText(user.getSurname());
        holder.tvEmail.setText(user.getEmail());
        holder.tvPhone.setText(user.getPhone());
    }

    @Override
    public int getItemCount() {
        return alist.size();
    }

    class RecViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvSurname, tvEmail, tvPhone;

        public RecViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvSurname = itemView.findViewById(R.id.tv_surname);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvPhone = itemView.findViewById(R.id.tv_phone);
        }
    }
}
