package com.example.doc_app_android.Adapter;

import android.animation.LayoutTransition;
import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.ProfileData;
import com.example.doc_app_android.databinding.HomeSinglePatientRowBinding;


import java.util.ArrayList;

public class PatientDetailsAdapter extends RecyclerView.Adapter<PatientDetailsAdapter.PatientDetailsHolder> {

    private ArrayList<ProfileData> data =  new ArrayList<>();
    private Context mContext;

    public PatientDetailsAdapter(Context mContext) {

        this.mContext = mContext;
    }

    public void setdata(ArrayList<ProfileData> data){
        this.data = data;

    }


    @NonNull
    @Override
    public PatientDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HomeSinglePatientRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.home_single_patient_row, parent, false);
        return new PatientDetailsHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull PatientDetailsAdapter.PatientDetailsHolder holder, int position) {
        holder.binding.patientName.setText(data.get(position).getName());
        holder.binding.expandablePatientName.setText(data.get(position).getName());
        holder.binding.expandablePatientLastcheckup.setText("Last Checkup: " + "16-07-2021");
        holder.binding.patientAge.setText("21");
        holder.binding.patientCaselevel.setText("Operation");
        holder.binding.patientState.setText("Chandigarh");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PatientDetailsHolder extends RecyclerView.ViewHolder {

        private final HomeSinglePatientRowBinding binding;

        public PatientDetailsHolder(@NonNull HomeSinglePatientRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.openButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.patientRow.setVisibility(View.GONE);
                    TransitionManager.beginDelayedTransition(binding.patientDetailsRow, new AutoTransition());
                    binding.expandableLayout.setVisibility(View.VISIBLE);
                }
            });

            binding.closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.expandableLayout.setVisibility(View.GONE);
                    TransitionManager.beginDelayedTransition(binding.patientDetailsRow, new AutoTransition());
                    binding.patientRow.setVisibility(View.VISIBLE);

                }
            });


        }
    }
}
