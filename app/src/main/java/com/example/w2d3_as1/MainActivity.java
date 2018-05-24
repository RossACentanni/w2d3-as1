package com.example.w2d3_as1;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements EntryFragment.ListButtonListener {

    FragmentManager fragmentManager;
    EntryFragment entryFrag;
    UserListFragment listFrag;

    public static final String FILENAME = "userList.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get fragment manager and set up entryFrag.
        fragmentManager = getSupportFragmentManager();
        entryFrag = new EntryFragment();

        //Set container to render Entry Fragment.
        fragmentManager.beginTransaction()
                .replace(R.id.container, entryFrag)
                .commit();

    }

    public void switchToList(){
        listFrag = new UserListFragment();

        fragmentManager.beginTransaction()
                    .replace(R.id.container, listFrag)
                    .commit();
    }
}
