package com.example.doc_app_android.PatentHomeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.doc_app_android.Adapter.prescListAdapter;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.PrescData;
import com.example.doc_app_android.databinding.FragmentPrescriptionBinding;
import com.example.doc_app_android.databinding.ListPrescBinding;
import com.example.doc_app_android.view_model.FragmentPrescViewModel;

import java.util.ArrayList;

public class FragmentPrescription extends Fragment {

    public FragmentPrescription() {
        // Required empty public constructor
    }

    public static FragmentPrescription newInstance() {
        FragmentPrescription fragment = new FragmentPrescription();
        return fragment;
    }

    public FragmentPrescriptionBinding binding;
    private ListPrescBinding listPrescBinding;
    public FragmentPrescViewModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_prescription,null,false);
        prescListAdapter adapter = new prescListAdapter();
        binding.parentRview.setAdapter(adapter);
        ((SimpleItemAnimator) binding.parentRview.getItemAnimator()).setSupportsChangeAnimations(false);
        model = new ViewModelProvider(requireActivity()).get(FragmentPrescViewModel.class);
        ArrayList<PrescData> prescDatatemp = new ArrayList<>();
        adapter.setData(prescDatatemp);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        binding.parentRview.setLayoutManager(mLayoutManager);
        binding.progressbar.setVisibility(View.VISIBLE);
        model.getPrescData().observeForever( new Observer<ArrayList<PrescData>>() {
            @Override
            public void onChanged(ArrayList<PrescData> prescData) {
                adapter.setData(prescData);
                adapter.notifyDataSetChanged();
                binding.progressbar.setVisibility(View.GONE);
                binding.swipe2refresh.setRefreshing(false);
                if (prescData.isEmpty()){
                    binding.noDataPres.setVisibility(View.VISIBLE);
                    binding.noDataPres1.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.parentRview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                binding.swipe2refresh.setEnabled(mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0); // 0 is for first item position
            }
        });

        binding.swipe2refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                model.refresh();
            }
        });
        return binding.getRoot();
    }
}