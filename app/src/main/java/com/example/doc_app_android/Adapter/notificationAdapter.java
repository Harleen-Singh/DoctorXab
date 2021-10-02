package com.example.doc_app_android.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.Dialogs.AppointmentConfirmationDialogFragment;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.NotiData;
import com.example.doc_app_android.databinding.SingleListNotiBinding;
import com.example.doc_app_android.services.notiService;

import java.util.ArrayList;

public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.ViewHolder> {
    private ArrayList<NotiData> notiData;

    public Context mContext;
    notiService service = new notiService();
    SharedPreferences preferences;
    private FragmentManager manager;

    public notificationAdapter(Context xontext, FragmentManager childFragmentManager) {
        mContext = xontext;
        notiData = new ArrayList<>();
        preferences = mContext.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        this.manager = childFragmentManager;
    }

    public void setData(ArrayList<NotiData> d) {
        this.notiData = d;
    }

    @NonNull
    @Override
    public notificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleListNotiBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_list_noti, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull notificationAdapter.ViewHolder holder, int position) {
        NotiData data = notiData.get(position);
        holder.binding.setNotiData(data);
        holder.binding.executePendingBindings();

        if(data.getStatus().equals("1")){
            holder.binding.containerNotif.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        if (preferences.getBoolean("isDoc", false)) {
            if (Integer.parseInt(data.getIcon()) == 1) {
                holder.binding.clickableArea.setOnClickListener(v -> {
                    if (!data.getStatus().equals("1")) {
                        AppointmentConfirmationDialogFragment dialogFragment = new AppointmentConfirmationDialogFragment(data, position, notiData);
                        dialogFragment.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AlertDialogLowRadius);
                        dialogFragment.show(manager, "AppointmentConfirmation");
//                        displayConfirmationDialog(data, position);
                    }
                    else
                        holder.binding.containerNotif.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                });
                holder.binding.imageView2.setBackground(AppCompatResources.getDrawable(mContext, R.drawable.download));
            }
        } else{
            holder.binding.imageView2.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return notiData.size();
    }

    public final void displayConfirmationDialog(NotiData NotificationData, int position) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext, R.style.AlertDialog);
        builder.setMessage(NotificationData.getData());
        builder.setTitle("Confirmation");
        builder.setPositiveButton("Accept Appointment", (dialog, which) -> {
            service.acceptReq(NotificationData, mContext);
            notiData.get(position).setStatus("1"); // for settting status to seen
                                                   // need to add api in future
        });
        builder.setNegativeButton("Reject Appointment", (dialog, which) -> {
            service.rejectRequest(NotificationData, mContext);
            notiData.get(position).setStatus("1");
        });
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().getWindowStyle();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SingleListNotiBinding binding;

        public ViewHolder(@NonNull SingleListNotiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
