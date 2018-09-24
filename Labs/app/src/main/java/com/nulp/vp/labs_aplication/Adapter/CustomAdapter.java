package com.nulp.vp.labs_aplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nulp.vp.labs_aplication.Model.Film;
import com.nulp.vp.labs_aplication.R;

import java.util.List;

/**
 * Created by Vova0199 on 15.09.2018.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<Film> films;
    private Context mContext;
    private PostItemListener mItemListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName, tvSurname, tvEmail, tvPhone;
        PostItemListener mItemListener;

        ViewHolder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSurname = itemView.findViewById(R.id.tv_surname);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvPhone = itemView.findViewById(R.id.tv_phone);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Film item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(item.getId());

            notifyDataSetChanged();
        }
    }

    public CustomAdapter(Context context, List<Film> films, PostItemListener itemListener) {
        this.films = films;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.row_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {

        Film item = films.get(position);

        holder.tvName.setText(item.getTitle());
//        holder.tvSurname.setText(item.getId().toString());
        holder.tvPhone.setText(item.getOverview().toString());
        holder.tvEmail.setText(item.getVoteCount().toString());
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public void updateAnswers(List<Film> items) {
        films = items;
        notifyDataSetChanged();
    }

    private Film getItem(int adapterPosition) {
        return films.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }
}