package com.example.androiddam.proyectofinalandroid.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androiddam.proyectofinalandroid.R;

import java.util.List;
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notifications_fr extends Fragment {
    private PagerAdapter mPagerAdapter;
    FloatingActionButton fab;
    View view;

    public Notifications_fr() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.notifications_fr, container, false);
        this.initialisePaging();

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return view;
    }

    private void initialisePaging() {

        List<Fragment> fragments = new Vector<Fragment>();

        // Add fragments here
        fragments.add(Fragment.instantiate(view.getContext(), Mensajes_fr.class.getName()));
        fragments.add(Fragment.instantiate(view.getContext(), Solicitudes_fr.class.getName()));

        this.mPagerAdapter  = new SectionsPagerAdapter(super.getChildFragmentManager(), fragments);
        ViewPager pager = (ViewPager)view.findViewById(R.id.container);
        pager.setAdapter(this.mPagerAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position>0)
                    fab.animate().translationY(250);
                else {
                    fab.animate().translationY(0);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return this.fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Mensajes";
                case 1:
                    return "Solicitudes";
            }
            return null;
        }
    }

}
