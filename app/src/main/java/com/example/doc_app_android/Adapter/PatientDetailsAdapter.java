package com.example.doc_app_android.Adapter;

import android.animation.LayoutTransition;
import android.content.Context;
import android.provider.ContactsContract;
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
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

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
        holder.binding.patientName.setText(data.get(position).getName());
        holder.binding.expandablePatientName.setText(data.get(position).getName());
        holder.binding.expandablePatientLastcheckup.setText("Last Checkup: " + "16-07-2021");
        holder.binding.patientAge.setText(data.get(position).getAge());
        holder.binding.patientCaselevel.setText("Operation");
        holder.binding.patientState.setText(data.get(position).getState());
        Picasso.get()
                .load(data.get(position).getImage())
                .into(holder.binding.profileImage);
        Picasso.get()
                .load(data.get(position).getImage())
                .into(holder.binding.expandableProfileImage);
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
                    Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.fadein);
                    binding.patientRow.setVisibility(View.GONE);


//                    binding.patientRow.startAnimation(animation);
                    //TransitionManager.beginDelayedTransition(binding.patientDetailsRow, new AutoTransition());
//                    binding.expandableLayout.setVisibility(View.VISIBLE);
//                    slideDown(binding.expandableLayout);
                    expand(binding.expandableLayout);


                }
            });

            binding.closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.expandableLayout.setVisibility(View.GONE);
                    //collapse(binding.expandableLayout, binding);
                    //TransitionManager.beginDelayedTransition(binding.patientDetailsRow, new AutoTransition());

                    binding.patientRow.setVisibility(View.VISIBLE);


                }
            });

            binding.addDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }


        public void expand(final View v) {
            int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
            int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
            final int targetHeight = v.getMeasuredHeight();

            // Older versions of android (pre API 21) cancel animations for views with a height of 0.
            v.getLayoutParams().height = 1;
            v.setVisibility(View.VISIBLE);
            Animation a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    v.getLayoutParams().height = interpolatedTime == 1
                            ? ViewGroup.LayoutParams.WRAP_CONTENT
                            : (int) (targetHeight * interpolatedTime);
                    v.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            // Expansion speed of 1dp/ms
            a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
            v.startAnimation(a);
        }

        public void collapse(final View v, HomeSinglePatientRowBinding binding) {
            final int initialHeight = v.getMeasuredHeight();

            Animation a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    if (interpolatedTime == 1) {
                        v.setVisibility(View.GONE);
                    } else {
                        v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                        v.requestLayout();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            // Collapse speed of 1dp/ms
            a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
            v.startAnimation(a);

            binding.patientRow.setVisibility(View.VISIBLE);
        }
    }
}
