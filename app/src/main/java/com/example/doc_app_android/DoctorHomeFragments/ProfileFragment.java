package com.example.doc_app_android.DoctorHomeFragments;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doc_app_android.ProfileEditFragment;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.ProfileData;
import com.example.doc_app_android.databinding.FragmentProfileBinding;
import com.example.doc_app_android.databinding.LoadingDialogBinding;
import com.example.doc_app_android.databinding.NavHeaderBinding;
import com.example.doc_app_android.databinding.ProfileDialogBinding;
import com.example.doc_app_android.databinding.ValidationDialogBinding;

import com.example.doc_app_android.view_model.ProfileViewModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

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

                    binding.doctorNameText.setText(profileData.getName());
                    binding.emailAddressText.setText(profileData.getEmail());
                    binding.contactText.setText(profileData.getPhoneNumber());
                } else {
                    binding.doctorNameText.setText("Sam Dawson");
                    binding.emailAddressText.setText("example@gmail.com");
                    binding.contactText.setText("9999999999");
                }

                if(!preferences.getBoolean("isDoc",false)){
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
                if(!preferences.getBoolean("isDoc",false)){
                    bundle.putString("PROFILE-FRAGMENT-SPECIALITY", speciality);
                } else{
                    bundle.putString("PROFILE-FRAGMENT-SPECIALITY", (String) binding.speciality.getText());
                }
                bundle.putString("PROFILE-FRAGMENT-PROFILE-IMAGE", receivedProfileData.getImage());
                profileEditFragment.setArguments(bundle);



                requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragmentHome_container, profileEditFragment).setReorderingAllowed(true).addToBackStack("profileedit").commit();

            }
        });

        



        return binding.getRoot();
    }










}