package com.coding.aisleassignment.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.coding.aisleassignment.R;
import com.coding.aisleassignment.databinding.FragmentHomeBinding;
import com.google.android.material.badge.BadgeDrawable;

import java.util.ArrayList;

import jp.wasabeef.blurry.Blurry;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bitmap originalBitmapOne = BitmapFactory.decodeResource(getResources(), R.drawable.photo_2);
        Blurry.with(requireActivity()).from(originalBitmapOne).into(binding.teena);
        Bitmap originalBitmapTwo = BitmapFactory.decodeResource(getResources(), R.drawable.photo_3);
        Blurry.with(requireActivity()).from(originalBitmapTwo).into(binding.beena);
        ArrayList<Integer> menuList =new ArrayList<>();
        Menu menu = binding.bottomNavigation.getMenu();
        for (int i = 0; i < binding.bottomNavigation.getMenu().size(); i++) {
            MenuItem menuItem = menu.getItem(i);
                menuList.add(menuItem.getItemId());
        }
        Log.d("TAG", "onViewCreated: "+menuList);

        BadgeDrawable notes=binding.bottomNavigation.getOrCreateBadge(menuList.get(1));
        notes.setVisible(true);
        notes.setNumber(9);
        BadgeDrawable matches=binding.bottomNavigation.getOrCreateBadge(menuList.get(2));
        matches.setVisible(true);
        matches.setNumber(50);

    }
}