package com.example.doc_app_android.PatentHomeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.doc_app_android.Adapter.X_Ray_adapter;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.Xray_data;
import com.example.doc_app_android.databinding.FragmentXRayScanBinding;
import com.example.doc_app_android.view_model.FragmentXrayScanViewModel;

import java.util.ArrayList;

public class FragmentXRayScan extends Fragment {

    public FragmentXRayScan() {
        // Required empty public constructor
    }

    public static FragmentXRayScan newInstance() {
        FragmentXRayScan fragment = new FragmentXRayScan();
        return fragment;
    }

    FragmentXRayScanBinding binding;
    private FragmentXrayScanViewModel model;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_x_ray_scan, null, false);
        X_Ray_adapter adapter = new X_Ray_adapter(getContext());
        binding.XRAYrcv.setAdapter(adapter);
        model = new ViewModelProvider(requireActivity()).get(FragmentXrayScanViewModel.class);
        binding.progressbar.setVisibility(View.VISIBLE);
        model.get_Xray_data().observe(getViewLifecycleOwner(), new Observer<ArrayList<Xray_data>>() {
            @Override
            public void onChanged(ArrayList<Xray_data> xray_data) {
                adapter.setdata(xray_data);
                adapter.notifyDataSetChanged();
                if (xray_data.isEmpty()) {
//                    binding.TopLayout.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.no_data));
                    binding.noDataPres.setVisibility(View.VISIBLE);
                    binding.noDataPres1.setVisibility(View.VISIBLE);
                }
                binding.progressbar.setVisibility(View.GONE);
                binding.swipe2refreshXray.setRefreshing(false);
            }
        });

        binding.swipe2refreshXray.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                model.refresh();
            }
        });
        return binding.getRoot();
    }
}