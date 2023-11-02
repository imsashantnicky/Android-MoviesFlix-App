package com.example.moviesflix.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesflix.ModelClass.TopCastModelClass;
import com.example.moviesflix.R;

import java.util.List;

public class TopCastAdapter extends RecyclerView.Adapter<TopCastAdapter.ViewHolder> {
    List<TopCastModelClass> topCastModelClassList;
    Context context;

    public TopCastAdapter(List<TopCastModelClass> topCastModelClassList, Context context) {
        this.topCastModelClassList = topCastModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public TopCastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.top_cast_recv_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopCastAdapter.ViewHolder holder, int position) {
        holder.name.setText(topCastModelClassList.get(position).getCastName());
        holder.profile.setText(topCastModelClassList.get(position).getCastProfile());
        Glide.with(context).load(topCastModelClassList.get(position).getCastImg()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return topCastModelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, profile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cast_profile_imv);
            name = itemView.findViewById(R.id.cast_name_txt);
            profile = itemView.findViewById(R.id.cast_profile_txt);

        }
    }
}
