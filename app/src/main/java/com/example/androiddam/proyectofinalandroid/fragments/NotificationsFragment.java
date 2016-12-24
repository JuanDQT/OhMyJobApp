package com.example.androiddam.proyectofinalandroid.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.adapters.NotificationsAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications2, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        setUpTabLayout(createFragments());

        return view;
    }

    private ArrayList<Fragment> createFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new Mensajes_fr());
        fragments.add(new Solicitudes_fr());

        return fragments;
    }

    private void setUpTabLayout(ArrayList<Fragment> fragments) {
        viewPager.setAdapter(new NotificationsAdapter(getFragmentManager(), fragments));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Mensajes");
        tabLayout.getTabAt(1).setText("Solicitudes");
    }

}
