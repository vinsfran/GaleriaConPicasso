package com.example.vinsfran.galeriaconpicasso.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vinsfran.galeriaconpicasso.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.ViewHolder> {

    private Context context;
    private int[] parties;
    private int layout;

    public PartyAdapter(Context context, int[] parties, int layout) {
        this.context = context;
        this.parties = parties;
        this.layout = layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Picasso.with(context).load(parties[position]).fit().placeholder(R.drawable.spinner).into(holder.image, new Callback() {
            @Override
            public void onSuccess() {
                //holder.image.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError() {
                Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return parties.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.imageViewLayout);
        }

    }
}

