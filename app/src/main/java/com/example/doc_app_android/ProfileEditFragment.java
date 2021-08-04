package com.example.doc_app_android;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.doc_app_android.DoctorHomeFragments.CheckupDetailsPatient;
import com.example.doc_app_android.DoctorHomeFragments.PatientHistoryFragment;
import com.example.doc_app_android.DoctorHomeFragments.ShareFragment;
import com.example.doc_app_android.data_model.ProfileData;
import com.example.doc_app_android.databinding.FragmentProfileBinding;
import com.example.doc_app_android.databinding.FragmentProfileEditBinding;
import com.example.doc_app_android.databinding.ProfileDialogBinding;
import com.example.doc_app_android.databinding.ValidationDialogBinding;
import com.example.doc_app_android.view_model.ProfileViewModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentProfileEditBinding binding;
    private SharedPreferences preferences;
    private boolean isFromPatientHistory;
    public static final int PERMISSION_REQUEST_CODE = 100;
    public static final int CAMERA_CAPTURE_CODE = 101;
    public static final int GALLERY_IMAGE_CODE = 102;
    private ProfileDialogBinding profileDialogBinding;
    private Dialog dialog2;
    private Dialog dialog;
    private Bitmap picture;
    private String name;
    private String email;
    private String phoneNumber;
    private String speciality;
    private String id;
    private ProfileViewModel profileViewModel;
    private ProfileData sendProfileData;
//    private ProfileData re
    private String encodedImageString;
    private ProfileData receivedProfileData;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileEditFragment newInstance(String param1, String param2) {
        ProfileEditFragment fragment = new ProfileEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        //((AppCompatActivity) getActivity()).getSupportActionBar().show();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_profile_edit, container, false);
        binding.setLifecycleOwner(this);
        preferences = requireContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        isFromPatientHistory = preferences.getBoolean("isFromPatientHistory", false);
        id = preferences.getString("id", "");



                profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        binding.btnEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(getActivity());
                ValidationDialogBinding binding1 = ValidationDialogBinding.inflate(LayoutInflater.from(getContext()));
                binding1.getRoot().setBackgroundResource(android.R.color.transparent);
                binding1.buttonOk
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.hide();
                            }
                        });
                dialog.setCancelable(false);
                dialog.setContentView(binding1.getRoot());
                Window window1 = dialog.getWindow();
                window1.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                name = Objects.requireNonNull(binding.profileName.getText()).toString();
                email = Objects.requireNonNull(binding.profileEmail.getText()).toString();
                phoneNumber = Objects.requireNonNull(binding.profilePhoneNumber.getText()).toString();
                speciality = Objects.requireNonNull(binding.profileSpeciality.getText()).toString();


                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(email) && TextUtils.isEmpty(phoneNumber)) {
                    binding1.messageTextview.setText("Please enter Name, Email and Phone Number");
                    dialog.show();

                } else if (TextUtils.isEmpty(name) && TextUtils.isEmpty(email)) {
                    binding1.messageTextview.setText("Please enter Name and Email.");
                    dialog.show();


                } else if (TextUtils.isEmpty(email) && TextUtils.isEmpty(phoneNumber)) {
                    binding1.messageTextview.setText("Please enter Email and Phone Number.");
                    dialog.show();


                } else if (TextUtils.isEmpty(name) && TextUtils.isEmpty(phoneNumber)) {
                    binding1.messageTextview.setText("Please enter Name and Phone Number.");
                    dialog.show();

                } else if (TextUtils.isEmpty(name)) {
                    binding1.messageTextview.setText("Please enter Name.");
                    dialog.show();

                } else if (TextUtils.isEmpty(email)) {
                    binding1.messageTextview.setText("Please enter Email.");
                    dialog.show();

                } else if (TextUtils.isEmpty(phoneNumber)) {
                    binding1.messageTextview.setText("Please enter Phone Number.");
                    dialog.show();

                } else if (picture == null) {
                    binding1.messageTextview.setText("Please Select an Image.");
                    dialog.show();

                } else {

                    encodedImageString = encodeBitmapImage(picture);
                    Log.e("debugging", "Encoded Image String: " + encodedImageString);

                    sendProfileData = new ProfileData(Integer.parseInt(id), speciality, email, name, phoneNumber, encodedImageString, picture);
                    //profileEditService.setProfileEditedObject(sendProfileData, requireContext());

                    profileViewModel.getEditedProfileDetails(sendProfileData, requireContext());
                    requireActivity().getSupportFragmentManager().popBackStack();
                    requireActivity().getSupportFragmentManager().popBackStack();


//
//                    loadingDialogBinding.updateProgress.setVisibility(View.INVISIBLE);
//                    dialog1.hide();
//                    binding.doctorProfileEdit.setVisibility(View.GONE);
//                    binding.doctorProfileSave.setVisibility(View.VISIBLE);
//                    getFragmentManager().beginTransaction().remove(ProfileFragment.this).commit();




                }
            }
        });


        binding.profileImageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkForPermission()) {

                    profileDialogBinding = ProfileDialogBinding.inflate(LayoutInflater.from(getContext()));


                    dialog2 = new Dialog(getActivity());
                    //profileDialogBinding.getRoot().setBackgroundResource(android.R.color.transparent);
                    dialog2.setContentView(profileDialogBinding.getRoot());
                    dialog2.show();
                    Window window = dialog2.getWindow();
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    profileDialogBinding.openCamera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            try {
                                startActivityForResult(openCameraIntent, CAMERA_CAPTURE_CODE);
                                dialog2.hide();
                                ;
                            } catch (ActivityNotFoundException e) {
                                // display error state to the user
                                Toast.makeText(getContext(), "Unable to Click Image, please try again!",
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                    profileDialogBinding.openGallery.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(openGalleryIntent, GALLERY_IMAGE_CODE);
                            dialog2.hide();

                        }
                    });


                } else {
                    Toast.makeText(getContext(), "Permissions Denied", Toast.LENGTH_SHORT).show();
                }

            }
        });


        binding.profileBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requireActivity().getSupportFragmentManager().popBackStack();
//                if (isFromPatientHistory) {
//                    requireActivity().getSupportFragmentManager().popBackStack();
////                    requireActivity().getSupportFragmentManager().beginTransaction().remove(ProfileEditFragment.this).commit();
////                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome_container, new PatientHistoryFragment()).commit();
//                } else {
//                    requireActivity().getSupportFragmentManager().popBackStack();
////                    requireActivity().getSupportFragmentManager().beginTransaction().remove(ProfileEditFragment.this).commit();
////                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome_container, new CheckupDetailsPatient()).commit();
//                }

            }
        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private boolean checkForPermission() {

        ArrayList<String> Permission = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Permission.add(Manifest.permission.CAMERA);
        }

        if (!Permission.isEmpty()) {
            String[] permissions = Permission.toArray(new String[0]);
            ActivityCompat.requestPermissions(requireActivity(), permissions, PERMISSION_REQUEST_CODE);
            return false;
        } else
            return true;


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //dialog.dismiss();


        switch (requestCode) {

            case CAMERA_CAPTURE_CODE:
                if (resultCode == -1 && data != null) {

                    picture = (Bitmap) data.getExtras().get("data");
                    Log.d("debugging", "Bitmap value: " + picture);
                    Log.d("debugging", "" + picture.getHeight());
                    Log.d("debugging", "" + picture.getWidth());
                    binding.profileImageEdit.setImageBitmap(picture);
                } else {
                    Toast.makeText(getContext(), "No Picture Clicked", Toast.LENGTH_SHORT).show();
                }
                break;


            case GALLERY_IMAGE_CODE:
                if (resultCode == -1 && data != null) {
                    Uri imageUri = data.getData();
                    try {
                        InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
                        picture = BitmapFactory.decodeStream(inputStream);
                        binding.profileImageEdit.setImageBitmap(picture);
                        Log.d("debugging", "Selected Picture");
                        Log.d("debugging", "Bitmap value: " + picture);
                        Log.d("debugging", "" + picture.getHeight());
                        Log.d("debugging", "" + picture.getWidth());

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getContext(), "No Picture Selected", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                Toast.makeText(getContext(), "No Picture Selected or Clicked", Toast.LENGTH_SHORT).show();
        }


    }

    private String encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();
        encodedImageString = android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
        return encodedImageString;
    }
}