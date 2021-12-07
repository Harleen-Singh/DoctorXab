package com.example.doc_app_android.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
        String time = data.getTime();
        String date = data.getDate();
        try {
            holder.binding.timeForNotification.setText(convertDateToBeautifulDate(time, date));

        } catch (ParseException e) {
            holder.binding.timeForNotification.setText("N/A");
            e.printStackTrace();
        }

        if (data.getStatus().equals("1")) {
            holder.binding.containerNotif.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

//        if (preferences.getBoolean("isDoc", false)) {
        if (Integer.parseInt(data.getIcon()) == 1) {
            holder.binding.clickableArea.setOnClickListener(v -> {
                if (!data.getStatus().equals("1")) {
                    AppointmentConfirmationDialogFragment dialogFragment = new AppointmentConfirmationDialogFragment(data, position, notiData);
                    dialogFragment.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AlertDialogLowRadius);
                    dialogFragment.show(manager, "AppointmentConfirmation");
//                        displayConfirmationDialog(data, position);
                } else
                    holder.binding.containerNotif.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            });
            holder.binding.imageView2.setBackground(AppCompatResources.getDrawable(mContext, R.drawable.download));
        } else if (Integer.parseInt(data.getIcon()) == 2) {
            holder.binding.imageView2.setBackground(AppCompatResources.getDrawable(mContext, R.drawable.ic_baseline_folder_shared_24));
        } else {
            holder.binding.imageView2.setBackground(AppCompatResources.getDrawable(mContext, R.drawable.ic_baseline_notifications_none_24));
        }
//        } else {
//            holder.binding.imageView2.setVisibility(View.GONE);
//        }
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

    private String convertDateToBeautifulDate(String time, String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
        date = date + "Z";

        Date beautifulDate = simpleDateFormat.parse(date.replaceAll("Z$", "+0000"));
        String beautifulDate1 = String.valueOf(beautifulDate);
        String d1 = beautifulDate1.substring(beautifulDate1.length() - 4);
        String d2 = beautifulDate1.substring(0, beautifulDate1.length() - 18);
        String finalDate = d2 + ", " + d1;

        Log.d("convertDate", "convertDateToBeautifulDate: " + beautifulDate1);
        Log.d("convertDate", "convertDateToBeautifulDate: " + time);
        Log.d("convertDate", "Year: " + d1);
        Log.d("convertDate", "Date: " + d2);

        return finalDate;

    }
}
