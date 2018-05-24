package com.example.w2d3_as1;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserListFragment extends Fragment {

    private static final String TAG = UserListFragment.class.getSimpleName()+"_TAG";

    private TextView userListTV;
    private String userList;

    public UserListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Bind all the UI elements.
        userListTV = view.findViewById(R.id.userListTV);
        readFromFile();
    }

    private void readFromFile(){
        try{
            InputStream inputStream = getContext().openFileInput(MainActivity.FILENAME);

            if(inputStream != null){
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(isr);
                String readString = "";
                while( (readString = br.readLine()) != null){
                    userListTV.append(readString + "\n");
                }
                inputStream.close();
            }
        }
        catch(IOException e){
            Log.d(TAG, "readFromFile: Read from file failed!");
        }
    }
}