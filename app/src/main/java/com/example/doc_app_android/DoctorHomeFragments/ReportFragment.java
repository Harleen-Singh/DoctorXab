package com.example.doc_app_android.DoctorHomeFragments;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
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

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.doc_app_android.R;
import com.example.doc_app_android.databinding.FragmentReportBinding;
import com.example.doc_app_android.databinding.ProfileDialogBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int PERMISSION_REQUEST_CODE = 100;
    public static final int CAMERA_CAPTURE_CODE = 101;
    public static final int GALLERY_IMAGE_CODE = 102;
    private ProfileDialogBinding profileDialogBinding;
    private Dialog dialog2;
    private Bitmap picture;
    private FragmentReportBinding binding;
    private ContentValues values;
    private Uri imageUri;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        // This particular line will hide the default toolbar of the Home Activity when fragment gets opened.
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report, container, false);
        binding.setLifecycleOwner(this);
        // Inflate the layout for this fragment

        binding.takePictureRetakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = requireContext().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                try {
                    startActivityForResult(openCameraIntent, CAMERA_CAPTURE_CODE);

                } catch (ActivityNotFoundException e) {
                    // display error state to the user
                    Toast.makeText(getContext(), "Unable to Click Image, please try again!",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

        binding.takePictureContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinalReportFragment finalReportFragment = new FinalReportFragment();
                Bundle bundle = new Bundle();
                bundle.putString("image",encodeBitmapImage(picture));
                finalReportFragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(R.id.fragmentHome_container, finalReportFragment).addToBackStack("report").commit();

            }
        });



        binding.takePhotoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        binding.takePhotoAddPicture.setOnClickListener(new View.OnClickListener() {
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
                            values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE, "New Picture");
                            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                            imageUri = requireContext().getContentResolver().insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
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

                }
            }
        });

        binding.takePhotoAdjustment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.takePhotoCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Bitmap cropped = binding.takePhotoImageView.getCroppedImage(500, 500);
//                if (cropped != null)
//                    binding.takePhotoImageView.setImageBitmap(cropped);

            }
        });


        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //dialog.dismiss();


        switch (requestCode) {

            case CAMERA_CAPTURE_CODE:
                if (resultCode == -1 ) {

                    try {
                        picture = MediaStore.Images.Media.getBitmap(
                                requireContext().getContentResolver(), imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("debugging", "Bitmap value: " + picture);
                    Log.d("debugging", "" + picture.getHeight());
                    Log.d("debugging", "" + picture.getWidth());
                    binding.takePhotoImageView.setImageBitmap(picture);
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
                        binding.takePhotoImageView.setImageBitmap(picture);
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

    private String encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();
        return android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
    }
}