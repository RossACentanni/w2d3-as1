package com.example.w2d3_as1;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


/**
 * A simple {@link Fragment} subclass.
 */
public class EntryFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = EntryFragment.class.getSimpleName()+"_TAG";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ListButtonListener listener;

    private EditText nameET;
    private EditText emailET;
    private EditText ageET;

    private Button pictureBTN;
    private Button saveBTN;
    private Button listBTN;

    public EntryFragment() {
        // Required empty public constructor
    }

    //We'll attach a listener here for communication with MainActivity.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (ListButtonListener) getActivity();
        }
        catch (ClassCastException e){
            Log.d(TAG, "onAttach: Implement ListButtonListener in MainActivity you goober");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Bind all the UI elements.
        nameET = view.findViewById(R.id.nameET);
        emailET = view.findViewById(R.id.emailET);
        ageET = view.findViewById(R.id.ageET);

        pictureBTN = view.findViewById(R.id.pictureBTN);
        saveBTN = view.findViewById(R.id.saveBTN);
        listBTN = view.findViewById(R.id.listBTN);

        pictureBTN.setOnClickListener(this);
        saveBTN.setOnClickListener(this);
        listBTN.setOnClickListener(this);

    }

    //Interface for telling MainActivity to switch between fragments.
    public interface ListButtonListener{
        public void switchToList();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.pictureBTN:
                takePicture();
                break;
            case R.id.saveBTN:
                Person person = readInput();
                writeToFile(person);
                break;
            case R.id.listBTN:
                listener.switchToList();
                break;
        }
    }

    //Handles opening the camera.
    private void takePicture(){
        PackageManager packMan = getContext().getPackageManager();
        boolean hasCamera = packMan.hasSystemFeature(PackageManager.FEATURE_CAMERA);
        if(!hasCamera){
            Toast.makeText(getContext(), "You don't have a camera!", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if((takePictureIntent.resolveActivity(packMan)) != null){
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    //Fires off when returning from camera.
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE){
            Toast.makeText(getContext(), "I threw your photo in the trash.", Toast.LENGTH_SHORT).show();
        }
    }

    //Reads the info from EditText fields and puts them in a Person wrapper.
    private Person readInput(){
        return new Person(nameET.getText().toString(),
                          emailET.getText().toString(),
                          Integer.parseInt(ageET.getText().toString()));
    }

    private void writeToFile(Person person){
        String toWrite = person.toString();
        try{
            OutputStreamWriter osw = new OutputStreamWriter(getContext().openFileOutput(
                    MainActivity.FILENAME, Context.MODE_APPEND));
            osw.write(toWrite);
            osw.close();
        }
        catch(IOException e) {
            Log.e(TAG, "writeToFile: File write failed.");
        }
    }

}
