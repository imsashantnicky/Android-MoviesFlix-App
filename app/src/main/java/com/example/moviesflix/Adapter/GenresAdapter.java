package com.example.moviesflix.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesflix.ModelClass.GenresModelClass;
import com.example.moviesflix.R;

import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.ViewHolder> {
    List<GenresModelClass> genresModelClassList;
    Context context;

    public GenresAdapter(List<GenresModelClass> genresModelClassList, Context context) {
        this.genresModelClassList = genresModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public GenresAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.genres_recv_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresAdapter.ViewHolder holder, int position) {
        holder.genrestxt.setText(genresModelClassList.get(position).getGenresTxt());
    }

    @Override
    public int getItemCount() {
        return genresModelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView genrestxt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            genrestxt = itemView.findViewById(R.id.genres_txt_view);
        }
    }
}
