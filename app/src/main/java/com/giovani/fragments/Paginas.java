package com.giovani.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giovani.greenhat.R;
import com.giovani.transformers.DepthPageTransformer;

/**
 * Created by DarkGeat on 3/28/2016.
 */
public class Paginas extends Fragment {

    public int NUM_PAGES = 5;
    private int[] images = new int[]{
            R.drawable.portada,
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p3,
            R.drawable.p4,
            R.drawable.p5,
            R.drawable.p6,
            R.drawable.p7,
            R.drawable.p8
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NUM_PAGES = images.length;
        View v = inflater.inflate(R.layout.fragment_paginas,container,false);
        ViewPager pager = (ViewPager)v.findViewById(R.id.viewPager);
        pager.setAdapter(new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager()));
        pager.setPageTransformer(true, new DepthPageTransformer());
        return v;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Pagina_Libro bookPage = new Pagina_Libro();
            Bundle bundle = new Bundle();
            bundle.putInt("page",images[position]);
            bookPage.setArguments(bundle);
            return bookPage;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
