package com.example.doc_app_android.DoctorHomeFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.doc_app_android.Adapter.AppointWithAdapter;
import com.example.doc_app_android.Dialogs.docListFragment;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.AppointmentData;
import com.example.doc_app_android.databinding.FragmentAppointmentV2Binding;
import com.example.doc_app_android.view_model.FragApmntViewModel;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.threeten.bp.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppointmentV2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentV2 extends Fragment { // implements CalendarAdapter.OnItemListener {


    private FragApmntViewModel viewModel;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    private Event event;
    private ArrayList<Event> eventList = new ArrayList<>();
    private FragmentAppointmentV2Binding binding;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean fromDrawerAppointment = false;
    private boolean fromCheckupDetailsAppointment = false;
    private boolean fromPatientInfoAppointment = false;
    private ArrayList<Event> nameList = new ArrayList<>();
    private AppointWithAdapter adapter;
    private CharSequence SelectedDate = LocalDate.now().toString();
    private Date today1;
    private ArrayList<Event> listOfEvents;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AppointmentV2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AppointmentV2.
     */
    // TODO: Rename and change types and number of parameters
    public static AppointmentV2 newInstance() {
        AppointmentV2 fragment = new AppointmentV2();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointment_v2, container, false);
        binding.compactCalendarView.setUseThreeLetterAbbreviation(false);
        viewModel = new ViewModelProvider(requireActivity()).get(FragApmntViewModel.class);
        //binding.appointmentV2Progress.setVisibility(View.VISIBLE);
        setUpSharedPreferences();
//        nameList.add("DR.SUMIT YADAV");
//        nameList.add("DR.SAMARTH SINGH");

        SimpleDateFormat todayDateForRCV = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss", Locale.US);
        String today = todayDateForRCV.format(new Date());


        try {
            today1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        listOfEvents = (ArrayList<Event>) binding.compactCalendarView.getEvents(today1);
        adapter = new AppointWithAdapter(listOfEvents, requireContext());
        binding.appointmentList.setAdapter(adapter);


        SimpleDateFormat sdf2 = new SimpleDateFormat("MMMM-yyyy", Locale.US);
        String date2 = sdf2.format(new Date());
        binding.monthYearTV.setText(date2);
        String note = preferences.getString("appointment_note", "");
        binding.editNote.setText(note);


        Bundle bundle = getArguments();
        if (bundle != null) {
            fromDrawerAppointment = bundle.getBoolean("fromDrawerAppointment", false);
            fromPatientInfoAppointment = bundle.getBoolean("fromPatientInfoAppointment", false);
        }

        if (fromDrawerAppointment) {
            binding.askForAppointment.setVisibility(View.GONE);
            binding.addReminder.setVisibility(View.GONE);
            binding.addANote.setVisibility(View.GONE);
        }

        if (fromPatientInfoAppointment) {
            binding.separator1.setVisibility(View.GONE);
            binding.appointmentList.setVisibility(View.GONE);
            binding.askForAppointment.setVisibility(View.GONE);
            binding.addReminder.setVisibility(View.GONE);
            binding.addANote.setVisibility(View.GONE);
        }


        viewModel.getApmntData(binding.appointmentV2Progress).observe(getViewLifecycleOwner(), new Observer<ArrayList<AppointmentData>>() {
            @Override
            public void onChanged(ArrayList<AppointmentData> data) {
                binding.compactCalendarView.removeAllEvents();


                for (int i = 0; i < data.size(); i++) {
                    LocalDateTime localDateTime = LocalDateTime.parse(data.get(i).getDate() + " 00:00:00",
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    long millis = localDateTime
                            .atZone(ZoneId.systemDefault())
                            .toInstant().toEpochMilli();

                    event = new Event(Color.BLUE, millis, data.get(i).getName());
                    eventList.add(event);

                }
                binding.compactCalendarView.addEvents(eventList);
                listOfEvents = (ArrayList<Event>) binding.compactCalendarView.getEvents(today1);
                adapter.setData(listOfEvents);
                adapter.notifyDataSetChanged();
                

            }
        });


        binding.compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

//                ArrayList<Event> listOfEvents = (ArrayList<Event>) binding.compactCalendarView.getEvents(dateClicked);
//                adapter = new AppointWithAdapter(listOfEvents, requireContext());
//                binding.appointmentList.setAdapter(adapter);

                listOfEvents = (ArrayList<Event>) binding.compactCalendarView.getEvents(dateClicked);
                adapter.setData(listOfEvents);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                binding.monthYearTV.setText(dateFormat.format(firstDayOfNewMonth));
            }
        });

        binding.prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.compactCalendarView.scrollLeft();
            }
        });

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.compactCalendarView.scrollRight();
            }
        });

        binding.addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, "Add Reminder");
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, "");
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, "");
                startActivity(intent);
            }
        });

        binding.addANote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.addANote.setVisibility(View.GONE);
                binding.hiddenNoteSaveContainer.setVisibility(View.VISIBLE);
                binding.appointmentSaveButton.setVisibility(View.VISIBLE);
            }
        });

        binding.appointmentSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = Objects.requireNonNull(binding.editNote.getText()).toString();
                if (TextUtils.isEmpty(note)) {
                    Toast.makeText(requireContext(), "Nothing added in the Note", Toast.LENGTH_SHORT).show();
                } else {
                    editor = preferences.edit();
                    editor.putString("appointment_note", note);
                    editor.apply();
                }

                binding.hiddenNoteSaveContainer.setVisibility(View.GONE);
                binding.appointmentSaveButton.setVisibility(View.GONE);
                binding.addANote.setVisibility(View.VISIBLE);


            }
        });


        binding.askForAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        //binding.appointmentV2Progress.setVisibility(View.GONE);


        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    void setUpSharedPreferences() {
        preferences = requireContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);

    }


    void openDialog() {
        {
            docListFragment docDialog = new docListFragment(SelectedDate);
            docDialog.setDialog(docDialog);
            docDialog.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AlertDialogLowRadius);
            docDialog.show(getChildFragmentManager(), "docList");
        }
    }


}