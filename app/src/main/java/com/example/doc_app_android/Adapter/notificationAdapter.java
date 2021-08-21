package com.example.doc_app_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.NotiData;
import com.example.doc_app_android.databinding.SingleListNotiBinding;
import com.example.doc_app_android.services.notiService;

import java.util.ArrayList;

public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.ViewHolder> {
    private ArrayList<NotiData> notiData;

    public Context mContext;
    notiService service = new notiService();
    public notificationAdapter(Context xontext) {
        mContext = xontext;
        notiData = new ArrayList<>();
    }

    public void setData(ArrayList<NotiData> d){
        this.notiData = d;
    }

    @NonNull
    @Override
    public notificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleListNotiBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_list_noti,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull notificationAdapter.ViewHolder holder, int position) {
        holder.binding.setNotiData(notiData.get(position));
        holder.binding.executePendingBindings();

        if(Integer.parseInt(notiData.get(position).getIcon())==1){
            holder.binding.imageView2.setBackground(AppCompatResources.getDrawable(mContext,R.drawable.download));
        }

        holder.binding.clickableArea.setOnClickListener(v -> {
            if(!notiData.get(position).getStatus().equals("1"))
                displayConfirmationDialog(notiData.get(position),position);
            else
                holder.binding.clickableArea.setBackgroundColor( mContext.getResources().getColor(R.color.white));
        });
    }

    @Override
    public int getItemCount() {
        return notiData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SingleListNotiBinding binding;
        public ViewHolder(@NonNull SingleListNotiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public final void displayConfirmationDialog(NotiData NotificationData, int position) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext, R.style.AlertDialog);
        builder.setMessage(NotificationData.getData());
        builder.setTitle("Confirmation");
        builder.setPositiveButton("Accept Appointment" ,(dialog, which) -> {
            service.acceptReq(NotificationData,mContext);
            notiData.get(position).setStatus("1");
        });
        builder.setNegativeButton("Reject Appointment" , (dialog, which) -> {
            service.rejectRequest(NotificationData,mContext);
            notiData.get(position).setStatus("1");
        });
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().getWindowStyle();
    }
}
