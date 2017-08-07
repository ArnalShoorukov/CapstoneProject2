package com.mosquefinder.arnal.prayertimesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mosquefinder.arnal.prayertimesapp.Quran.Ayah;
import com.mosquefinder.arnal.prayertimesapp.Quran.Surah;
import com.mosquefinder.arnal.prayertimesapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnal on 8/6/17.
 */

public class AyahAdapter extends RecyclerView.Adapter<AyahAdapter.ViewHolder> {


    private Context context;
    private List<Ayah> ayahList;

    public void setAyahList(List<Ayah> ayahList) {
        this.ayahList.clear();
        this.ayahList.addAll(ayahList);
        notifyDataSetChanged();
    }

    public AyahAdapter(Context context, List<Ayah> ayahListList) {
        this.context = context;
        this.ayahList = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView qurantext, numberAyah;
        public TextView nameSurah;

        public ViewHolder(View view) {
            super(view);
            //nameSurah = (TextView)view.findViewById(R.id.name_surah);
            qurantext = (TextView) view.findViewById(R.id.quran_text);
            nameSurah = (TextView) view.findViewById(R.id.name_surah_arabic);
            numberAyah = (TextView) view.findViewById(R.id.number);
        }
    }

    @Override
    public AyahAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ayah_adapter, parent, false);

        ViewHolder view = new ViewHolder(inflate);
        return view;

      /*  View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.names_cardview, parent, false);

        NamesAdapter.ViewHolder view = new NamesAdapter.ViewHolder(inflate);
        return view;*/
    }

    @Override
    public void onBindViewHolder(AyahAdapter.ViewHolder holder, final int position) {
        Ayah ayah = ayahList.get(position);
        //  ayah.getSurah().getEnglishName();
        final TextView textView = holder.nameSurah;
        textView.setText(ayah.getSurah().getEnglishName());

        final TextView textView1 = holder.numberAyah;
        textView1.setText(Integer.toString(ayah.getNumberInSurah()));

        final TextView quranText = holder.qurantext;
        quranText.setText(ayah.getText());

       /* for (int i=0; i<ayahList.size(); i++){
            quranText.setText(ayahList.get(i).getText());
            Log.d("Adapter", ayahList.get(i).getText());
        }*/
    }

    @Override
    public int getItemCount() {
        return (ayahList.isEmpty() ? 0 : ayahList.size());
    }
}
