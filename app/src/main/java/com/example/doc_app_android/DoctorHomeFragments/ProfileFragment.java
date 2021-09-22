package com.example.doc_app_android.DoctorHomeFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.ProfileData;
import com.example.doc_app_android.databinding.FragmentProfileBinding;
import com.example.doc_app_android.databinding.ProfileDialogBinding;
import com.example.doc_app_android.utils.Globals;
import com.example.doc_app_android.view_model.ProfileViewModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ProfileViewModel profileViewModel;
    private ProfileData receivedProfileData;
    private ProfileData sendProfileData;

    private Dialog dialog;
    private Dialog dialog2;
    public static final int PERMISSION_REQUEST_CODE = 100;
    public static final int CAMERA_CAPTURE_CODE = 101;
    public static final int GALLERY_IMAGE_CODE = 102;
    private ProfileDialogBinding profileDialogBinding;
    private FragmentProfileBinding binding;
    private Bitmap picture;
    private String encodedImageString;
    private String name;
    private String email;
    private String phoneNumber;
    private String speciality;
    private CircleImageView nav_profile;
    private TextView nav_name;
    private SharedPreferences preferences;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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

    @Override
    public void onResume() {
        super.onResume();
        // This particular line will hide the default toolbar of the Home Activity when fragment gets opened.
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }


    @Override
    public void onStop() {
        super.onStop();
        // This particular line will show the default toolbar of the Home Activity on Home Page when fragment gets closed.
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setLifecycleOwner(this);

        preferences = requireContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);


        binding.profileProgress.setVisibility(View.VISIBLE);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        profileViewModel.getProfileDetails().observe(getViewLifecycleOwner(), new Observer<ProfileData>() {
            @Override
            public void onChanged(ProfileData profileData) {
                receivedProfileData = profileData;

                if (!TextUtils.isEmpty(profileData.getName()) && !TextUtils.isEmpty(profileData.getEmail()) && !TextUtils.isEmpty(profileData.getPhoneNumber())) {

                    binding.doctorNameText.setText("Dr." + profileData.getName());
                    binding.emailAddressText.setText(profileData.getEmail());
                    binding.contactText.setText(profileData.getPhoneNumber());
                } else {
                    binding.doctorNameText.setText("Sam Dawson");
                    binding.emailAddressText.setText("example@gmail.com");
                    binding.contactText.setText("9999999999");
                }

                if (!preferences.getBoolean("isDoc", false)) {
                    binding.doctorNameText.setText(profileData.getName());

                    binding.speciality.setText("CASE OF: ORTHOLOGIST");
                    speciality = "ORTHOLOGIST";
                }
                Log.d("Testing", "Received Image: " + "https://maivrikdoc.herokuapp.com/api" + profileData.getImage());

                Picasso.get()
                        .load("https://maivrikdoc.herokuapp.com/api" + profileData.getImage())
                        .placeholder(R.drawable.doctor_profile_image)
                        .into(binding.doctorProfileImageSave);

                binding.profileProgress.setVisibility(View.GONE);


            }
        });


        binding.profileBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();

            }
        });

        binding.savedSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requireActivity().getSupportFragmentManager().popBackStack();


            }
        });


        binding.savedEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileEditFragment profileEditFragment = new ProfileEditFragment();
                Bundle bundle = new Bundle();
                bundle.putString("PROFILE-FRAGMENT-NAME", (String) binding.doctorNameText.getText());
                bundle.putString("PROFILE-FRAGMENT-EMAIL", (String) binding.emailAddressText.getText());
                bundle.putString("PROFILE-FRAGMENT-PHONE-NUMBER", (String) binding.contactText.getText());
                if (!preferences.getBoolean("isDoc", false)) {
                    bundle.putString("PROFILE-FRAGMENT-SPECIALITY", speciality);
                } else {
                    bundle.putString("PROFILE-FRAGMENT-SPECIALITY", (String) binding.speciality.getText());
                }
                bundle.putString("PROFILE-FRAGMENT-PROFILE-IMAGE", receivedProfileData.getImage());
                profileEditFragment.setArguments(bundle);


                requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragmentHome_container, profileEditFragment).setReorderingAllowed(true).addToBackStack("profileedit").commit();

            }
        });

        final SharedPreferences sp = requireContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        boolean isDoc = sp.getBoolean("isDoc", false);
        String dorP_id = sp.getString("doctor_id", "");
        String patient_id = sp.getString("id", "");

        binding.swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (isDoc) {
                    Log.d("Testing", "Url: " + Globals.profileDoctor + dorP_id);
                    profileViewModel.onRefresh(Globals.profileDoctor + dorP_id, true);

                } else {
                    Log.d("Testing", "Url: " + Globals.profileDoctor + patient_id);
                    profileViewModel.onRefresh(Globals.profilePatient + patient_id, false);

                }
                binding.swipeToRefresh.setRefreshing(false);

            }
        });


        return binding.getRoot();
    }


}