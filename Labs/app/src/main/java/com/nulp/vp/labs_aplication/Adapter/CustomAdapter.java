package com.nulp.vp.labs_aplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nulp.vp.labs_aplication.Model.Film;
import com.nulp.vp.labs_aplication.R;
import com.nulp.vp.labs_aplication.UI.FilmInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.String.valueOf;

/**
 * Created by Vova0199 on 15.09.2018.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<Film> films;
    private Context mContext;

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvTitle;
        @BindView(R.id.tv_email)
        TextView tvVoteAverage;
        @BindView(R.id.tv_phone)
        TextView tvDescription;
        @BindView(R.id.img_poster_list)
        ImageView imgPoster;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Film currentFilm = films.get(pos);
                        Intent intent = new Intent(mContext, FilmInfo.class);
                        intent.putExtra("title", currentFilm.getTitle());
                        intent.putExtra("description", currentFilm.getOverview());
                        intent.putExtra("image_path", currentFilm.getPosterPath());
                        intent.putExtra("rate_average", currentFilm.getVoteAverage());
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    public CustomAdapter(Context context, List<Film> films) {
        this.films = films;
        mContext = context;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.row_item, parent, false);
        return new ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        Film item = films.get(position);
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/original" + item.getPosterPath())
                .into(holder.imgPoster);
        holder.tvTitle.setText(item.getTitle());
        holder.tvDescription.setText(item.getOverview());
        holder.tvVoteAverage.setText(valueOf(item.getVoteAverage()));
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public void updateAnswers(List<Film> items) {
        films = items;
        notifyDataSetChanged();
    }
}
