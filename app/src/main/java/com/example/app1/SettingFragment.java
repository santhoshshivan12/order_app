package com.example.app1;

import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashSet;
import java.util.Set;

public class SettingFragment extends Fragment {

    private ImageView profileImageView;
    private EditText editUsername;
    private TextView editUserType;
    private Button uploadImageButton, logoutButton, saveButton, uploadSplashImageButton;
    private LinearLayout profileSection;
    private SharedPreferences sharedPreferences, sp;
    private ActivityResultLauncher<Intent> selectImageLauncher;
    private ActivityResultLauncher<Intent> selectMultipleImagesLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting2, container, false);

        // Initialize views
        profileSection = view.findViewById(R.id.profile_section);
        profileImageView = view.findViewById(R.id.profile_image);
        editUsername = view.findViewById(R.id.edit_username);
        editUserType = view.findViewById(R.id.edit_user_type);
        uploadImageButton = view.findViewById(R.id.upload_image_button);
        uploadSplashImageButton = view.findViewById(R.id.upload_splash_image_button);
        logoutButton = view.findViewById(R.id.logout_button);
        saveButton = view.findViewById(R.id.save_button);

        sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        sp = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        if (sp.getString("user", "").equals("user")) {
            profileSection.setVisibility(VISIBLE);
            // Load user data (username, user type, profile image)
            loadUserData();

            // Save button click listener to update user details
            saveButton.setOnClickListener(v -> saveUserDetails());

            // Profile image upload button
            uploadImageButton.setOnClickListener(v -> openImagePicker());
        }

        // Splash screen image upload button
        uploadSplashImageButton.setOnClickListener(v -> openMultipleImagePicker());

        // Logout button click listener
        logoutButton.setOnClickListener(v -> logout());

        // Set up image picker for profile image
        selectImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri imageUri = data.getData();
                            if (imageUri != null) { // Check if imageUri is not null
                                profileImageView.setImageURI(imageUri);
                                saveProfileImage(imageUri); // Save the profile image
                            } else {
                                Toast.makeText(getContext(), "Image selection cancelled.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        // Set up multiple image picker for splash screen images
        selectMultipleImagesLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            if (data.getClipData() != null) {
                                // Handle multiple image selection
                                int count = data.getClipData().getItemCount();
                                Set<String> splashImages = new HashSet<>();
                                for (int i = 0; i < count; i++) {
                                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                                    splashImages.add(imageUri.toString());
                                }
                                saveSplashImages(splashImages);  // Save splash images
                            } else if (data.getData() != null) {
                                // Handle single image selection
                                Uri imageUri = data.getData();
                                Set<String> splashImages = new HashSet<>();
                                splashImages.add(imageUri.toString());
                                saveSplashImages(splashImages);
                            } else {
                                Toast.makeText(getContext(), "Image selection cancelled.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        return view;
    }

    private void loadUserData() {
        // Load username and user type
        String username = sharedPreferences.getString("username", "");
        String userType = sp.getString("user", "");
        String profileImageUri = sharedPreferences.getString("profile_image", null);

        editUsername.setText(username);
        editUserType.setText(userType);

        // Load profile image if exists
        if (profileImageUri != null) {
            profileImageView.setImageURI(Uri.parse(profileImageUri));
        }
    }

    private void saveUserDetails() {
        String newUsername = editUsername.getText().toString().trim();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", newUsername);
        editor.putString("user_type", editUserType.getText().toString().trim());

        editor.apply();

        Toast.makeText(getContext(), "User details updated", Toast.LENGTH_SHORT).show();
    }

    private void openImagePicker() {
        // Open the gallery to select an image
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        selectImageLauncher.launch(intent);
    }

    private void openMultipleImagePicker() {
        // Open the gallery to select multiple images
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        selectMultipleImagesLauncher.launch(intent);
    }

    private void saveProfileImage(Uri imageUri) {
        // Save profile image URI to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("profile_image", imageUri.toString());
        editor.apply();
        Toast.makeText(getContext(), "Profile image updated", Toast.LENGTH_SHORT).show();
    }

    private void saveSplashImages(Set<String> splashImages) {
        // Save splash image URIs to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("splash_images", splashImages);
        editor.apply();
        Toast.makeText(getContext(), "Splash screen images updated", Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        // Clear user data from SharedPreferences and redirect to login screen
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(getActivity(), login.class);
        startActivity(intent);
        getActivity().finish();
    }
}
