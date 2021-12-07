package com.example.doc_app_android.actvities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.doc_app_android.Dialogs.docListFragment;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.ProblemsList;
import com.example.doc_app_android.data_model.RegistrationData;
import com.example.doc_app_android.databinding.ActivitySignNextBinding;
import com.example.doc_app_android.utils.PreferencesName;
import com.example.doc_app_android.view_model.RegistrationViewModel;

import java.util.ArrayList;

public class SignNextActivity extends AppCompatActivity {

    private ActivitySignNextBinding binding;
    private String userName, email, password, confirmPassword;
    private RegistrationData registrationData;
    private RegistrationData userEnteredRegistrationData;
    private RegistrationViewModel registrationViewModel;
    private String name, contact, age, gender, specialistOf, consultTo, state, address;
    private boolean isDoctorRegistration = false, fromDataLoaderFragment = false;
    private static final String[] genderOptions = new String[]{"Male", "Female", "Others"};
    private static String[] problems = new String[]{"Problem"};
    private docListFragment docListFragment;
    private SharedPreferences preferences;
    private String userId, doctorId, problem;
    private int problem_id;
    private boolean other = false;


    @Override
    public void onBackPressed() {
        if (binding.regContainer1.getVisibility() == View.VISIBLE) {
            binding.regContainer1.setVisibility(View.GONE);
            binding.regContainer.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_next);
        binding.setLifecycleOwner(this);

        binding.backToSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.editProblem.setVisibility(View.GONE);
                binding.backToSpinner.setVisibility(View.GONE);
                binding.problemSpinner.setVisibility(View.VISIBLE);
                other = false;
            }
        });

        registrationViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        isDoctorRegistration = getIntent().getBooleanExtra("doctorRegistration", false);
        fromDataLoaderFragment = getIntent().getBooleanExtra("FROM_DATA_LOADER_FRAGMENT", false);
        preferences = getSharedPreferences(PreferencesName.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);

        ArrayAdapter<String> genderChoiceSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, genderOptions);
        binding.genderSpinner.setAdapter(genderChoiceSpinnerAdapter);


        ArrayAdapter<String> problemChoiceSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, problems);
        binding.problemSpinner.setAdapter(problemChoiceSpinnerAdapter);

        if (isDoctorRegistration) {
            binding.editStatusDoctor.setVisibility(View.GONE);
            binding.problemSpinner.setVisibility(View.GONE);
            binding.editStatusDoctor.setText(R.string.specialistOf);
        } else {
            binding.editStatusDoctor.setText(R.string.consultTo);
        }

        registrationViewModel.get_problem_list(this).observe(this, new Observer<ArrayList<ProblemsList>>() {
            @Override
            public void onChanged(ArrayList<ProblemsList> problemsLists) {

                problems = new String[problemsLists.size() + 2];
                problems[0] = "Problem";
                problems[problemsLists.size() + 1] = "Other";

                for (int i = 0; i < problemsLists.size(); i++) {
                    problems[Integer.parseInt(problemsLists.get(i).getId())] = problemsLists.get(i).getProblem();
                }

                ArrayAdapter<String> problemChoiceSpinnerAdapterSecond = new ArrayAdapter<String>(SignNextActivity.this, android.R.layout.simple_list_item_1, problems);
                binding.problemSpinner.setAdapter(problemChoiceSpinnerAdapterSecond);


            }
        });


        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                userName = String.valueOf(binding.editUsername.getText());
                email = String.valueOf(binding.editEmail.getText());
                password = String.valueOf(binding.createPass.getText());
                confirmPassword = String.valueOf(binding.confirmPass.getText());
                if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {
                    if (password.equals(confirmPassword)) {
                        registrationData = new RegistrationData(userName, email, password);

                        registrationViewModel.registerUser(SignNextActivity.this, registrationData, binding.regContainer, binding.regContainer1, isDoctorRegistration, fromDataLoaderFragment);

                    } else {
                        Toast.makeText(SignNextActivity.this, "Password must be same in both columns.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(confirmPassword)) {
                        Toast.makeText(SignNextActivity.this, "Enter details.", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(SignNextActivity.this, "Please enter all the information carefully.", Toast.LENGTH_SHORT).show();

                    }

                }
//                binding.regContainer.setVisibility(View.GONE);
//                binding.regContainer1.setVisibility(View.VISIBLE);


            }
        });


        binding.tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Second Page functions implementations starts from here.
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = String.valueOf(binding.editName.getText());
                contact = String.valueOf(binding.editContact.getText());
                state = String.valueOf(binding.editState.getText());
                address = String.valueOf(binding.editAddress.getText());
                age = String.valueOf(binding.ageField.getText());

                preferences = getSharedPreferences(PreferencesName.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
                userId = preferences.getString(PreferencesName.RECEIVED_REGISTRATION_ID, "");
                doctorId = preferences.getString(PreferencesName.SELECTED_DOCTOR_ID, "");
                if (other) {
                    problem = binding.editProblem.getText().toString();
                }


                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(contact) && !TextUtils.isEmpty(state) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(age)) {
                    if (!isDoctorRegistration) {
                        userEnteredRegistrationData = new RegistrationData(name, contact, state, address, age, gender);
                        if (other) {
//                            registrationViewModel.returnedProblem(problem, SignNextActivity.this).observe(SignNextActivity.this, new Observer<ProblemsList>() {
//                                @Override
//                                public void onChanged(ProblemsList problemsList) {
//                                    if (problemsList != null) {
//                                        Log.d("PROBLEM", "onChanged: " + problemsList);
//                                        problem_id = Integer.parseInt(problemsList.getId());
//                                        registrationViewModel.addPatientToDatabase(SignNextActivity.this, userEnteredRegistrationData, userId, doctorId, SignNextActivity.this, fromDataLoaderFragment, problem_id);
//                                    }
//                                }
//                            });
                            registrationViewModel.AddProblem(problem, SignNextActivity.this, userEnteredRegistrationData, userId, doctorId, SignNextActivity.this, fromDataLoaderFragment, 1);
                        } else {
                            registrationViewModel.addPatientToDatabase(SignNextActivity.this, userEnteredRegistrationData, userId, doctorId, SignNextActivity.this, fromDataLoaderFragment, problem_id);
                        }

                    } else {
                        userEnteredRegistrationData = new RegistrationData(name, contact, state, address, age, gender);
                        registrationViewModel.sendRegisteredUserInformation(SignNextActivity.this, userEnteredRegistrationData, userId, SignNextActivity.this, fromDataLoaderFragment);
                    }
                } else {
                    Toast.makeText(SignNextActivity.this, "Please enter all the information carefully.", Toast.LENGTH_SHORT).show();

                }


            }
        });

        binding.problemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                problem = problems[position];
                problem_id = position;
                if (problem.equals("Problem")) {
                    Toast.makeText(SignNextActivity.this, "Please select a problem.", Toast.LENGTH_SHORT).show();
                }

                if (problem.equals("Other")) {
                    binding.problemSpinner.setVisibility(View.GONE);
                    binding.editProblem.setVisibility(View.VISIBLE);
                    binding.backToSpinner.setVisibility(View.VISIBLE);
                    other = true;
                    problem = "Problem";
                    Toast.makeText(SignNextActivity.this, "Please enter your problem.", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                gender = genderOptions[position];
//                Toast.makeText(SignNextActivity.this, "Your have selected: " + genderOptions[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(SignNextActivity.this, "Please a select your respective gender.", Toast.LENGTH_SHORT).show();

            }
        });

        binding.editStatusDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDoctorRegistration) {
                    docListFragment = new docListFragment(true, binding.editStatusDoctor, fromDataLoaderFragment);
                    docListFragment.setDialog(docListFragment);
                    docListFragment.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AlertDialog);
                    docListFragment.show(getSupportFragmentManager(), "registration");
                }
            }
        });


        // Focus Change Listeners
        binding.editName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                binding.editName.setHint(hasFocus ? "" : "Name");
            }
        });

        binding.editContact.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                binding.editContact.setHint(hasFocus ? "" : "Contact");
            }
        });

        binding.editState.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                binding.editState.setHint(hasFocus ? "" : "State");
            }
        });

        binding.editAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                binding.editAddress.setHint(hasFocus ? "" : "Address");
            }
        });

        binding.ageField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                binding.ageField.setHint(hasFocus ? "" : "Age");
            }
        });

        binding.editUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                binding.editUsername.setHint(hasFocus ? "" : "UserName");
            }
        });

        binding.editEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                binding.editEmail.setHint(hasFocus ? "" : "Email");
            }
        });

        binding.createPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                binding.createPass.setHint(hasFocus ? "" : "Create Password");
            }
        });

        binding.confirmPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                binding.confirmPass.setHint(hasFocus ? "" : "Confirm Password");
            }
        });


    }

}