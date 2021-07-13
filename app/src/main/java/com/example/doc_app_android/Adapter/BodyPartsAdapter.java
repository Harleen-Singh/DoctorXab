package com.example.doc_app_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BodyPartsAdapter extends RecyclerView.Adapter<BodyPartsAdapter.BodyPartsHolder> {

    private ArrayList<BodyParts> data;
    //private ArrayList<PatientDetails> data1;
    private Context mContext;
    private FragmentManager fragmentManager;
    private final OnPartListener onPartListener;
    // private RecyclerView rcv;

    public BodyPartsAdapter(ArrayList<BodyParts> data, Context mContext, OnPartListener onPartListener) {
        this.data = data;
        this.mContext = mContext;
        this.onPartListener = onPartListener;
//        this.rcv = rcv;
    }

    @NonNull
    @NotNull
    @Override
    public BodyPartsHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_filter, parent, false);
        return new BodyPartsHolder(view, onPartListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BodyPartsAdapter.BodyPartsHolder holder, int position) {
        holder.category_btn.setText(data.get(position).getBodyPartName().toString());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class BodyPartsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Button category_btn;
        private final OnPartListener onPartListener;

        public BodyPartsHolder(@NonNull @NotNull View itemView, OnPartListener onPartListener) {
            super(itemView);
            category_btn = (Button) itemView.findViewById(R.id.button_dynamic);
            this.onPartListener = onPartListener;
            category_btn.setOnClickListener(this);


//            category_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Drawable bg = mContext.getResources().getDrawable(R.drawable.btn_theme_1);
//                    category_btn.setBackground(bg);
//                    category_btn.setTextColor(mContext.getResources().getColor(R.color.white));
//                    fragmentManager.beginTransaction().replace(R.id.patient_container, new DataLoaderFragment()).commit();
//                }
//            });
        }

        @Override
        public void onClick(View v) {
            onPartListener.onPartClick(getAdapterPosition(), category_btn);
        }
    }

    public interface OnPartListener {
        void onPartClick(int position, Button btn);
    }
}
