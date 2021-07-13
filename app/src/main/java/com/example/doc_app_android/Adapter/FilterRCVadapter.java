package com.example.doc_app_android.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.style.IconMarginSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

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
    public int preSelectionPos , openedFrag =0;
    public SharedPreferences pref;
    Fragment temp =  null;
    public FilterRCVadapter(Context context) {
        pref = context.getSharedPreferences("tokenFile",Context.MODE_PRIVATE);
        this.context = context;
        if(!pref.getBoolean("isDoc",false)){ // patient initial fragment for checkUp history
            temp = patientCheckUpHistory.newInstance();
            AppCompatActivity appCompatActivity = (AppCompatActivity)context ;
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.Home_frameLayout,temp).commit();
        }else{
            // case for initial screen for doctor
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
        holder.binding.setFilterData(data.get(position));
        if(position==preSelectionPos){
            holder.binding.buttonDynamic.setTextColor(AppCompatResources.getColorStateList(context,R.color.white));
            holder.binding.buttonDynamic.setBackground(AppCompatResources.getDrawable(context,R.drawable.btn_theme_1));
        }else{
            holder.binding.buttonDynamic.setTextColor(AppCompatResources.getColorStateList(context,R.color.scnd_grey));
            holder.binding.buttonDynamic.setBackground(AppCompatResources.getDrawable(context,R.drawable.btn_theme_2));
        }
    }

    private void callFragments(String filterData, int position) {
        if(openedFrag!=position){
            openedFrag = position;

            if(pref.getBoolean("isDoc",false)){
                // TODO
            }else{

                Log.d("TAG", "callFragments: " + filterData);
            }
            switch (filterData.trim()){
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
            AppCompatActivity appCompatActivity = (AppCompatActivity)context ;
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.Home_frameLayout,temp).commit();
        }
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
            Log.e("TAG", "onClick: previous" + preSelectionPos);
            notifyItemChanged(preSelectionPos);
            preSelectionPos = getAdapterPosition();
            Log.e("TAG", "onClick: next" + getAdapterPosition());
            notifyItemChanged(preSelectionPos);
            callFragments(binding.buttonDynamic.getText().toString(),preSelectionPos);

        }
    }

}