package com.example.doc_app_android.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.Dialogs.docListFragment;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.DocData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class docListAdapter extends RecyclerView.Adapter<docListAdapter.viewHolder>{
    public ArrayList<DocData> data;
    docListFragment docDialog;
    private CharSequence date;
    public docListAdapter(ArrayList<DocData> arr, CharSequence date, docListFragment docDialog) {
        data = arr;
        this.date = date;
        this.docDialog = docDialog;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.doclist_single,null);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Picasso.get().load(data.get(position).getImage()).error(R.drawable.errorimgload).placeholder(R.drawable.loading).into(holder.image);
//        Picasso.get().load(data.get(position).getImage()).error(R.drawable.errorimgload).into(holder.image);
        holder.name.setText(data.get(position).getName());
        Log.e("TAG", "onBindViewHolder: "+ data.get(position).getName() );
        String docName = data.get(position).getName().toUpperCase();
        DocData newData= data.get(position);
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogs dialog = new dialogs();
                dialog.displayConfirmationDialog("CONFIRM","Do You Want To Book Appointment With Doc " + docName , v.getContext() , newData , date , docDialog );
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class viewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CircleImageView image;
        ConstraintLayout click;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.docImage);
            name = itemView.findViewById(R.id.Doc_name);
            click = itemView.findViewById(R.id.click);
        }
    }
}
