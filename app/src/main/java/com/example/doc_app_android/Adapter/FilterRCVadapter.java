package com.example.doc_app_android.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.DoctorHomeFragments.DataLoaderFragment;
import com.example.doc_app_android.PatentHomeFragments.AppointmentsFragment;
import com.example.doc_app_android.PatentHomeFragments.FragmentPrescription;
import com.example.doc_app_android.PatentHomeFragments.FragmentXRayScan;
import com.example.doc_app_android.PatentHomeFragments.patientCheckUpHistory;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.FilterData;
import com.example.doc_app_android.databinding.SingleFilterBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FilterRCVadapter extends RecyclerView.Adapter<FilterRCVadapter.FilterVH> {

    public ArrayList<FilterData> data = new ArrayList<>();
    public Context context;
    public int preSelectionPos;
    public SharedPreferences pref;
    private SharedPreferences.Editor editor;
    Fragment temp = null;
    private boolean ispatientHistoryfragment;

    public FilterRCVadapter(Context context, boolean ispatientHistoryfragment) {
        this.context = context;
        this.ispatientHistoryfragment = ispatientHistoryfragment;
        if(ispatientHistoryfragment){
            temp = patientCheckUpHistory.newInstance();
            AppCompatActivity appCompatActivity = (AppCompatActivity) context;
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.patient_info_frame, temp).commit();
        }
    }

    public FilterRCVadapter(Context context) {
        pref = context.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        this.context = context;
        if (!pref.getBoolean("isDoc", false)) { // patient initial fragment for checkUp history
            temp = patientCheckUpHistory.newInstance();
            AppCompatActivity appCompatActivity = (AppCompatActivity) context;
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.Home_frameLayout, temp).commit();
        } else {

            temp = DataLoaderFragment.newInstance("1");
            AppCompatActivity appCompatActivity = (AppCompatActivity) context;
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.Home_frameLayout, temp).commit();
        }
    }

    public void setFilter(ArrayList<FilterData> dataList) {
        data = dataList;
    }

    @NonNull
    @Override
    public FilterVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SingleFilterBinding binding = SingleFilterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FilterVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FilterRCVadapter.FilterVH holder, int position) {
        Log.e("HomeIssues", "onBindHolder is called with position: " + position);

        holder.binding.setFilterData(data.get(position));
        if (position == preSelectionPos) {
            holder.binding.buttonDynamic.setTextColor(AppCompatResources.getColorStateList(context, R.color.white));
            holder.binding.buttonDynamic.setBackground(AppCompatResources.getDrawable(context, R.drawable.btn_theme_1));
        } else {
            holder.binding.buttonDynamic.setTextColor(AppCompatResources.getColorStateList(context, R.color.scnd_grey));
            holder.binding.buttonDynamic.setBackground(AppCompatResources.getDrawable(context, R.drawable.btn_theme_2));
        }
    }

    private void callPatientCheckupFragments(String data, int position, FilterData problem_id){

        switch (data.trim()) {
            case "CHECKUP HISTORY":
                temp = patientCheckUpHistory.newInstance();
                break;
            case "X-RAY/SCAN":
                temp = FragmentXRayScan.newInstance();
                break;
            case "APPOINTMENT":
                temp = AppointmentsFragment.newInstance();
                break;
        }
        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.patient_info_frame, temp).commit();

    }

    private void callFragments(String filterData, int position, FilterData problem_id) {
        Log.e("TestingRecyclerAdapter", "FilterData For Doctor: " + filterData);

            if (pref.getBoolean("isDoc", false)) {
                // This part of code controls the opening of different screens through Patient home dynamic buttons.
                temp = DataLoaderFragment.newInstance(problem_id.getId());
            } else {
                // This part of code controls the opening of different screens through Patient home dynamic buttons.
                Log.d("TAG", "callFragments: " + filterData);
                switch (filterData.trim()) {
                    case "CHECKUP HISTORY":
                        temp = patientCheckUpHistory.newInstance();
                        break;
                    case "X-RAY/SCAN":
                        temp = FragmentXRayScan.newInstance();
                        break;
                    case "PRESCRIPTION":
                        temp = FragmentPrescription.newInstance();
                        break;
                    case "APPOINTMENT":
                        temp = AppointmentsFragment.newInstance();
                        break;
                }
            }
            AppCompatActivity appCompatActivity = (AppCompatActivity) context;
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.Home_frameLayout, temp).commit();


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class FilterVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        SingleFilterBinding binding;

        public FilterVH(@NonNull @NotNull SingleFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.buttonDynamic.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.e("TAG", "onClick: previous: " + preSelectionPos);
            notifyItemChanged(preSelectionPos);
            preSelectionPos = getAdapterPosition();
            Log.e("TAG", "onClick: next:" + getAdapterPosition());

            notifyItemChanged(preSelectionPos);
            if(ispatientHistoryfragment){
                callPatientCheckupFragments(binding.buttonDynamic.getText().toString(), preSelectionPos, data.get(preSelectionPos));
            } else {
                callFragments(binding.buttonDynamic.getText().toString(), preSelectionPos, data.get(preSelectionPos));
            }

        }
    }

}
