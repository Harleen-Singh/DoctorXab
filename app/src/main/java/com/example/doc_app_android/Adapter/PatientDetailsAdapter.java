package com.example.doc_app_android.Adapter;

import android.animation.LayoutTransition;
import android.content.Context;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.doc_app_android.HomeFragments.CheckupDetailsPatient;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.ProfileData;
import com.example.doc_app_android.databinding.HomeSinglePatientRowBinding;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class PatientDetailsAdapter extends RecyclerView.Adapter<PatientDetailsAdapter.PatientDetailsHolder> {

    private ArrayList<ProfileData> data = new ArrayList<>();
    private Context mContext;

    public PatientDetailsAdapter(Context mContext) {

        this.mContext = mContext;
    }

    public void setdata(ArrayList<ProfileData> data) {
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
        if(!TextUtils.isEmpty(data.get(position).getName()) && !TextUtils.isEmpty(data.get(position).getName()) && !TextUtils.isEmpty(data.get(position).getAge()) && !TextUtils.isEmpty(data.get(position).getState()) && !TextUtils.isEmpty(data.get(position).getImage())){
            holder.binding.patientName.setText(data.get(position).getName());
            holder.binding.expandablePatientName.setText(data.get(position).getName());
            holder.binding.expandablePatientLastcheckup.setText("Last Checkup: " + "16-07-2021");
            holder.binding.patientAge.setText(data.get(position).getAge());
            holder.binding.patientCaselevel.setText("Operation");
            holder.binding.patientState.setText(data.get(position).getState());
            Picasso.get()
                    .load(data.get(position).getImage())
                    .placeholder(R.drawable.doctor_profile_image)
                    .into(holder.binding.profileImage);
            Picasso.get()
                    .load(data.get(position).getImage())
                    .placeholder(R.drawable.doctor_profile_image)
                    .into(holder.binding.expandableProfileImage);
        }

        boolean isExpandedLayout = data.get(position).isExpanded();
        boolean isExpandedPatientRow = data.get(position).isRowExpanded();


        holder.binding.patientRow.setVisibility(isExpandedPatientRow? View.VISIBLE : View.GONE);
        holder.binding.expandableLayout.setVisibility(isExpandedLayout? View.VISIBLE : View.GONE);



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
                    ProfileData profileData = data.get(getAbsoluteAdapterPosition());
                    profileData.setRowExpanded(!profileData.isRowExpanded());
                    profileData.setExpanded(true);
                    notifyItemChanged(getAbsoluteAdapterPosition());
                }
            });

            binding.closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProfileData profileData = data.get(getAbsoluteAdapterPosition());
                    profileData.setExpanded(!profileData.isExpanded());
                    profileData.setRowExpanded(true);
                    notifyItemChanged(getAbsoluteAdapterPosition());

                }
            });

            binding.addDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentHome_container, new CheckupDetailsPatient()).commit();
                }
            });

        }





    }
}
