package com.example.doc_app_android.PatentHomeFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.Adapter.NotesRVAdapter;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.AppointmentData;
import com.example.doc_app_android.data_model.DocData;
import com.example.doc_app_android.data_model.DoctorListViewModel;
import com.example.doc_app_android.databinding.FragmentAppointmentsBinding;
import com.example.doc_app_android.services.DoctorListService;
import com.example.doc_app_android.view_model.FragApmntViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.jetbrains.annotations.NotNull;
import org.threeten.bp.LocalDate;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class AppointmentsFragment extends Fragment {

    private String TAG = "APPOINTMENT";

    public AppointmentsFragment() {
        // Required empty public constructor
    }

    public static AppointmentsFragment newInstance() {
        AppointmentsFragment fragment = new AppointmentsFragment();
        return fragment;
    }

    NotesRVAdapter adapter = new NotesRVAdapter(getContext());
    FragmentAppointmentsBinding binding;
    DoctorListService docListService = new DoctorListService();
    private ArrayList<String> message;
    private FragApmntViewModel viewModel;
    private DoctorListViewModel doctorListViewModel;
    private ArrayList<AppointmentData> appointmentData = new ArrayList<>();
    private SharedPreferences preferences;
    private boolean isPatientCalender;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointments, null, false);

        viewModel = new ViewModelProvider(requireActivity()).get(FragApmntViewModel.class);
        doctorListViewModel = new ViewModelProvider(requireActivity()).get(DoctorListViewModel.class);
        binding.progressbar.setVisibility(View.VISIBLE);
        binding.setLifecycleOwner(this);
        Map<String, String> docNames = new HashMap<>();

        preferences = getContext().getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        isPatientCalender = preferences.getBoolean("patientInfoCalendar", false);

        if(isPatientCalender){
            binding.calenderContainer.setVisibility(View.GONE);
            binding.editCalendar.setVisibility(View.VISIBLE);
        }

//        binding.editCalendar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.patient_info_frame, new AppointmentsFragment()).addToBackStack("name5").commit();
//                binding.editCalendar.setVisibility(View.GONE);
//                binding.editSave.setVisibility(View.VISIBLE);
//            }
//        });
//
//        binding.editSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getFragmentManager().beginTransaction().remove(AppointmentsFragment.this).commit();
//
//
//            }
//        });
        doctorListViewModel.getID_name_pair().observe(requireActivity(), new Observer<ArrayList<DocData>>() {
            @Override
            public void onChanged(ArrayList<DocData> docData) {
                Log.e(TAG, "onChanged: " + "GOT DOC LIST");
                for (int i = 0; i < docData.size(); i++) {
                    docNames.put(docData.get(i).getDocID(), docData.get(i).getName());
                }

            }
        });
        viewModel.getApmntData().observe(getViewLifecycleOwner(), new Observer<ArrayList<AppointmentData>>() {
            @Override
            public void onChanged(ArrayList<AppointmentData> appointmentData) {
                try {
                    Log.e(TAG, "onChanged: " + "GOT DATES");
                    addSpanToCalender(appointmentData);
                    changeDocName(appointmentData, docNames);
                    binding.progressbar.setVisibility(View.GONE);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        binding.calendarView.setSelectedDate(LocalDate.now());
        spanAdder(new Date(), "1");
        binding.btnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.CLayoutNote.getVisibility() == View.VISIBLE) {
                    binding.CLayoutNote.setVisibility(View.GONE);
                } else {
                    binding.CLayoutNote.setVisibility(View.VISIBLE);
                    binding.scrollView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.scrollView.fullScroll(View.FOCUS_DOWN);
                        }
                    }, 100);
                }

            }
        });
        binding.btnRemain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, "Add Reminder");
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, "");
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, "");
                startActivity(intent);

            }
        });

        binding.textViewNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    Log.e("TAG", "onCreateView: disabled");
                    binding.btnSave.setEnabled(false);
                } else {
                    Log.e("TAG", "onCreateView: enabled");
                    binding.btnSave.setEnabled(true);
                }
            }
        });

        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                message.remove(viewHolder.getBindingAdapterPosition());
                saveData();
                adapter.notifyDataSetChanged();
            }
        };


        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(binding.rVNote);


        binding.rVNote.setAdapter(adapter);
        loadData();
        adapter.setdata(message);
        adapter.notifyDataSetChanged();
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.add(binding.textViewNote.getText().toString());
                if (binding.textViewNote.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "--->>>>" + binding.textViewNote.getText().toString().length(), Toast.LENGTH_SHORT).show();
                } else if (binding.textViewNote.getText().toString().length() != 0) {
                    saveData();
                    adapter.setdata(message);
                    adapter.notifyDataSetChanged();
                    binding.textViewNote.getText().clear();
                } else {
                    adapter.notifyDataSetChanged();
                    binding.textViewNote.getText().clear();
                }
            }
        });

        return binding.getRoot();
    }

    private void changeDocName(ArrayList<AppointmentData> appointmentData, Map<String, String> docNames) {
        binding.calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull @NotNull MaterialCalendarView widget, @NonNull @NotNull CalendarDay date, boolean selected) {
                DecimalFormat formatter = new DecimalFormat("00");
                String selectedDay = formatter.format(date.getDay());
                String selectedMon = formatter.format(date.getMonth());
                String selectedYear = formatter.format(date.getYear());

                String selectedDate = selectedYear + "-" + selectedMon + "-" + selectedDay;
                for (int i = 0; i < appointmentData.size(); i++) {
                    Log.e(TAG, "onDateSelected: " + selectedDate);
                    Log.e(TAG, "onDateSelected: " + appointmentData.get(i).getDate());
                    if (appointmentData.get(i).getDate().equals(selectedDate)) {
                        binding.tvDoc.setText(docNames.get(appointmentData.get(i).getDoctor_id()));
//                        binding.tvDoc.setText(appointmentData.get(i).getDoctor_id());
                        break;
                    } else {
                        binding.tvDoc.setText("NO APPOINTMENT TODAY");
                    }
                }
            }
        });
    }

    private void addSpanToCalender(ArrayList<AppointmentData> appointmentData) throws ParseException {
        for (int i = 0; i < appointmentData.size(); i++) {
            String strDate = appointmentData.get(i).getDate();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = format.parse(strDate);
            spanAdder(date, "0");
        }
    }

    private void spanAdder(Date date, String flag) { //flag :0 means appointment dates;; 1 means today ;;
        binding.calendarView.addDecorators(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                DecimalFormat formatter = new DecimalFormat("00");
                int sDay = day.getDay();
                int sMonth = day.getMonth();
                int sYear = day.getYear();
                String sMon = formatter.format(sMonth);
                String sday = formatter.format(sDay);

                int pDay = date.getDate();
                int pMonth = date.getMonth() + 1;
                int pYear = date.getYear() + 1900;
                String pMon = formatter.format(pMonth);
                String pday = formatter.format(pDay);
                Date sDate = new Date(), pDate = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                try {
                    sDate = format.parse(sYear + "-" + sMon + "-" + sday);
                    pDate = format.parse(pYear + "-" + pMon + "-" + pday);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (pDate.equals(sDate)) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void decorate(DayViewFacade view) {
                if (flag.equals("0")) {
                    view.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.appointment_span));
                }
                if (flag.equals("1")) {
                    view.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.appointment_today_span));
                }
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(message);
        editor.putString("msg", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("msg", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        message = gson.fromJson(json, type);
        if (message == null) {
            message = new ArrayList<>();
        }
    }
}