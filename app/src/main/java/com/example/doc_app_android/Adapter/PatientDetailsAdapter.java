package com.example.doc_app_android.Adapter;

import android.animation.LayoutTransition;
import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.doc_app_android.R;


import java.util.ArrayList;

public class PatientDetailsAdapter extends RecyclerView.Adapter<PatientDetailsAdapter.PatientDetailsHolder> {

    private ArrayList<PatientDetails> data;
    private Context mContext;

    public PatientDetailsAdapter(ArrayList<PatientDetails> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public PatientDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.home_single_patient_row, parent, false);
        return new PatientDetailsHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PatientDetailsAdapter.PatientDetailsHolder holder, int position) {

        holder.name.setText(data.get(position).getName());
        holder.expandableName.setText(data.get(position).getName());
        holder.lastCheckup.setText("Last Checkup: " + data.get(position).getLast_checkUp());
        holder.age.setText(data.get(position).getAge());
        holder.caseLevel.setText(data.get(position).getCase_level());
        holder.state.setText(data.get(position).getState());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PatientDetailsHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView expandableName;
        private TextView lastCheckup;
        private TextView age;
        private TextView caseLevel;
        private TextView state;
        private ImageView open_button;
        private ImageView close_button;
        private RelativeLayout expandable_layout;
        private LinearLayoutCompat linearLayout;
        private LinearLayoutCompat patient_row;


        public PatientDetailsHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.patient_name);
            expandableName = (TextView) itemView.findViewById(R.id.expandable_patient_name);
            lastCheckup = (TextView) itemView.findViewById(R.id.expandable_patient_lastcheckup);
            age = (TextView) itemView.findViewById(R.id.patient_age);
            caseLevel = (TextView) itemView.findViewById(R.id.patient_caselevel);
            state = (TextView) itemView.findViewById(R.id.patient_state);

            open_button = (ImageView) itemView.findViewById(R.id.open_button);
            close_button = (ImageView) itemView.findViewById(R.id.close_button);
            expandable_layout = (RelativeLayout) itemView.findViewById(R.id.expandable_layout);
            linearLayout = (LinearLayoutCompat) itemView.findViewById(R.id.patient_details_row);
            patient_row = (LinearLayoutCompat) itemView.findViewById(R.id.patient_row);

            open_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    patient_row.setVisibility(View.GONE);
                    TransitionManager.beginDelayedTransition(linearLayout, new AutoTransition());
                    expandable_layout.setVisibility(View.VISIBLE);
                }
            });

            close_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expandable_layout.setVisibility(View.GONE);
                    TransitionManager.beginDelayedTransition(linearLayout, new AutoTransition());
                    patient_row.setVisibility(View.VISIBLE);

                }
            });


        }
    }
}
