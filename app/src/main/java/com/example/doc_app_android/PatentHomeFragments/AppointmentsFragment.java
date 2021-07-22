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
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.Adapter.NotesRVAdapter;
import com.example.doc_app_android.R;
import com.example.doc_app_android.databinding.FragmentAppointmentsBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.threeten.bp.LocalDate;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class AppointmentsFragment extends Fragment {


    public AppointmentsFragment() {
        // Required empty public constructor
    }
    public static AppointmentsFragment newInstance() {
        AppointmentsFragment fragment = new AppointmentsFragment();
        return fragment;
    }

    NotesRVAdapter adapter = new NotesRVAdapter(getContext());
    FragmentAppointmentsBinding binding;
    private ArrayList<String> message ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_appointments,null,false);

        binding.calendarView.setSelectedDate(LocalDate.now());
        binding.btnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.CLayoutNote.getVisibility()==View.VISIBLE){
                    binding.CLayoutNote.setVisibility(View.GONE);
                }else{
                    binding.CLayoutNote.setVisibility(View.VISIBLE);
//                    binding.scrollView.fullScroll(View.FOCUS_DOWN);
//                    binding.scrollView.smoothScrollTo(0,1000);
                    binding.scrollView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.scrollView.fullScroll(View.FOCUS_DOWN);
                        }
                    },100);
                }

            }
        });
        binding.btnRemain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE,"Add Reminder");
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,"");
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,"");
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
                if (s.toString().equals("")){
                    Log.e("TAG", "onCreateView: disabled" );
                    binding.btnSave.setEnabled(false);
                }else{
                    Log.e("TAG", "onCreateView: enabled" );
                    binding.btnSave.setEnabled(true);
                }
            }
        });

        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT){
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
                if(binding.textViewNote.getText().toString().equals("")){
                    Toast.makeText(getContext(), "--->>>>"+binding.textViewNote.getText().toString().length(), Toast.LENGTH_SHORT).show();
                }else if(binding.textViewNote.getText().toString().length()!=0){
                    saveData();
                    adapter.setdata(message);
                    adapter.notifyDataSetChanged();
                    binding.textViewNote.getText().clear();
                }
                else{
                    adapter.notifyDataSetChanged();
                    binding.textViewNote.getText().clear();
                }
            }
        });
        return binding.getRoot();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(message);
        editor.putString("msg",json);
        editor.apply();
    }
    private void loadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SHARED_PREFS",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("msg",null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        message = gson.fromJson(json,type);
        if(message==null){
            message = new ArrayList<>();
        }
    }

}