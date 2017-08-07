package com.mosquefinder.arnal.prayertimesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mosquefinder.arnal.prayertimesapp.adapter.NamesAdapter;
import com.mosquefinder.arnal.prayertimesapp.data.Names;

import java.util.ArrayList;
import java.util.List;

public class NamesActivity extends AppCompatActivity {
    public static List<Names> namesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names);

        namesList.add(new Names(R.raw.name_al_malik, R.drawable.name_al_malik));
        namesList.add(new Names(R.raw.name_al_waheed, R.drawable.name_al_waheed));
        namesList.add(new Names(R.raw.name_ar_razzaaq, R.drawable.name_ar_razzaaq));
        namesList.add(new Names(R.raw.name_al_aleem, R.drawable.name_al_aleem));
        namesList.add(new Names(R.raw.name_al_barr, R.drawable.name_al_barr));
        namesList.add(new Names(R.raw.name_al_hafeedh, R.drawable.name_al_hafeedh));
        namesList.add(new Names(R.raw.name_al_muhsin, R.drawable.name_al_muhsin));
        namesList.add(new Names(R.raw.name_al_musawwir, R.drawable.name_al_musawwir));

        NamesAdapter namesAdapter = new NamesAdapter(this, namesList);
        namesAdapter.setNameList(namesList);

        LinearLayoutManager mLayoutManager = new GridLayoutManager(this, 2);

        //titleAdapter = new TitleAdapter(getContext(), nameList);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.names_recycler_view);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(namesAdapter);

        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        namesAdapter.setListener(new NamesAdapter.Listener() {
            @Override
            public void onClick(int position) {

                Names response = namesList.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("objectNames" , response );
                Intent intent = new Intent(NamesActivity.this, DetailNamesActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


       // addImagesToThegallery();

    }



    /*  private void addImagesToThegallery() {
        LinearLayout imageGallery = (LinearLayout) findViewById(R.id.imageGallery);
        for (Integer image : images) {
            imageGallery.addView(getImageView(image));
        }
    }
    private View getImageView(Integer image) {
        ImageView imageView = new ImageView(getApplicationContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, 0,0, 0);
        imageView.setLayoutParams(lp);
        imageView.setImageResource(image);
        return imageView;
    }*/
}
