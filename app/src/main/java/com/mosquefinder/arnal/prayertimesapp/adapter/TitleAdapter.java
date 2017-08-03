package com.mosquefinder.arnal.prayertimesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mosquefinder.arnal.prayertimesapp.Fragment.AllFragment;
import com.mosquefinder.arnal.prayertimesapp.R;
import com.mosquefinder.arnal.prayertimesapp.data.Dua;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnal on 7/29/17.
 */

public class TitleAdapter  extends RecyclerView.Adapter<TitleAdapter.ViewHolder>{
    private Listener listener;
    private Context context;
    private List<Dua> titleList;

    public interface Listener {
        void onClick(int position);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
    public void setDuaList(List<Dua> titlesList) {
        this.titleList.clear();
        this.titleList.addAll(titlesList);
        notifyDataSetChanged();
    }
    public TitleAdapter(Context context, List<Dua> nameList) {
        this.context = context;
        this.titleList = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.title_cardview);
        }
    }
    @Override
    public TitleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.title_cardview, parent, false);

        ViewHolder view = new ViewHolder(inflate);
        return view;
    }

    @Override
    public void onBindViewHolder(TitleAdapter.ViewHolder holder, final int position) {
        Dua name = titleList.get(position);
        final TextView textView = holder.textView;
        textView.setText(name.getmTitle());
        Log.d("Title Adapter", name.getmTitle());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (titleList.isEmpty() ? 0: titleList.size());
    }
}
