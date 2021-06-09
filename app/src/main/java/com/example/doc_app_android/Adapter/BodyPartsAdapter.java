package com.example.doc_app_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BodyPartsAdapter extends RecyclerView.Adapter<BodyPartsAdapter.BodyPartsHolder>{

    private ArrayList<BodyParts> data;
    private Context mContext;

    public BodyPartsAdapter(ArrayList<BodyParts> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public BodyPartsHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.bparts_single_row, parent, false);
        return new BodyPartsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BodyPartsAdapter.BodyPartsHolder holder, int position) {
        holder.category_btn.setText(data.get(position).getBodyPartName().toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class BodyPartsHolder extends RecyclerView.ViewHolder{

        private final Button category_btn;

        public BodyPartsHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            category_btn = (Button)itemView.findViewById(R.id.button_dynamic);
        }
    }
}
