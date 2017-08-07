package com.mosquefinder.arnal.prayertimesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.mosquefinder.arnal.prayertimesapp.R;
import com.mosquefinder.arnal.prayertimesapp.data.Names;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnal on 8/1/17.
 */

public class NamesAdapter  extends RecyclerView.Adapter<NamesAdapter.ViewHolder>{
    private Listener listener;
    private List<Names> namesList;

    public interface Listener {
        void onClick(int position);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
    public void setNameList(List<Names> titlesList) {
        this.namesList.clear();
        this.namesList.addAll(titlesList);
        notifyDataSetChanged();
    }
    public NamesAdapter(Context context, List<Names> nameList) {
        this.namesList = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.names_cardview);
        }
    }
    @Override
    public NamesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.names_cardview, parent, false);

        NamesAdapter.ViewHolder view = new NamesAdapter.ViewHolder(inflate);
        return view;
    }

    @Override
    public void onBindViewHolder(NamesAdapter.ViewHolder holder, final int position) {
        Names name = namesList.get(position);
        final ImageView imageView = holder.imageView;
        imageView.setImageResource(name.getImageResourceID());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (namesList.isEmpty() ? 0: namesList.size());
    }
}
