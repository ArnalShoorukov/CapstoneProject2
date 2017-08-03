package com.mosquefinder.arnal.prayertimesapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mosquefinder.arnal.prayertimesapp.DetailDuaActivity;
import com.mosquefinder.arnal.prayertimesapp.R;
import com.mosquefinder.arnal.prayertimesapp.adapter.TitleAdapter;
import com.mosquefinder.arnal.prayertimesapp.data.Dua;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment {
    private TitleAdapter titleAdapter;
    private LinearLayoutManager mLayoutManager;
    private static final String TAG = AllFragment.class.getSimpleName();

    public static List<Dua> nameList = new ArrayList<>();
    public AllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final List<Dua> titleList = new ArrayList<>();
        View rootView = inflater.inflate(R.layout.fragment_all, container, false);
        titleList.add(new Dua(getString(R.string.id_first), getString(R.string.title_first),getString(R.string.english_first),getString(R.string.reference_first),getString(R.string.benefit_title),R.raw.dua_first, R.drawable.dua_first));
        titleList.add(new Dua(getString(R.string.id_second), getString(R.string.title_second),getString(R.string.english_second),getString(R.string.reference_second),getString(R.string.benefit_second),R.raw.dua_second, R.drawable.dua_second));
        titleList.add(new Dua(getString(R.string.id_third), getString(R.string.title_third),getString(R.string.english_third),getString(R.string.reference_third),getString(R.string.benefit_third),R.raw.dua_third, R.drawable.dua_third));
        titleList.add(new Dua(getString(R.string.id_four),getString(R.string.title_four),getString(R.string.english_four),getString(R.string.reference_four),getString(R.string.benefit_four),R.raw.dua_four, R.drawable.d4));
        titleList.add(new Dua(getString(R.string.id_five), getString(R.string.title_five),getString(R.string.english_five),getString(R.string.reference_five),getString(R.string.benefit_five),R.raw.dua_five, R.drawable.d5));
        titleList.add(new Dua(getString(R.string.id_six), getString(R.string.title_six),getString(R.string.english_six),getString(R.string.reference_six),getString(R.string.benefit_six),R.raw.dua_six, R.drawable.d6));
        titleList.add(new Dua(getString(R.string.id_seven), getString(R.string.title_seven),getString(R.string.english_seven),getString(R.string.reference_seven),getString(R.string.benefit_seven),R.raw.dua_seven, R.drawable.d7));
        titleList.add(new Dua(getString(R.string.id_eight), getString(R.string.title_eight),getString(R.string.english_eight),getString(R.string.reference_eight),getString(R.string.benefit_eight),R.raw.dua_eight, R.drawable.d8));
        titleList.add(new Dua(getString(R.string.id_nine), getString(R.string.title_nine),getString(R.string.english_nine),getString(R.string.reference_nine),getString(R.string.benefit_nine),R.raw.dua_nine, R.drawable.d9));
        titleList.add(new Dua(getString(R.string.id_ten), getString(R.string.title_ten),getString(R.string.english_ten),getString(R.string.reference_ten),getString(R.string.benefit_ten),R.raw.dua_ten, R.drawable.d10));
        nameList = titleList;

        titleAdapter = new TitleAdapter(getContext(), nameList);
        titleAdapter.setDuaList(titleList);

        mLayoutManager = new GridLayoutManager(getContext(), 1);

        //titleAdapter = new TitleAdapter(getContext(), nameList);
        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.dua_recycler_view);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(titleAdapter);

        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        titleAdapter.setListener(new TitleAdapter.Listener() {
            @Override
            public void onClick(int position) {

                Dua response = nameList.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("object" , response );
                Intent intent = new Intent(getContext(), DetailDuaActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
