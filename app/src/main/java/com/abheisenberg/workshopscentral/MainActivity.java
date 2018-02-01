package com.abheisenberg.workshopscentral;

import android.app.Fragment;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.abheisenberg.workshopscentral.SQLDatabaseWorkshops.DBHandler;
import com.abheisenberg.workshopscentral.WorkshopDataStructure.Workshop;

public class MainActivity extends AppCompatActivity implements
        WelcomeFragment.OnFragmentInteractionListener, RegisterFragment.OnFragmentInteractionListener,
        SigninFragment.OnFragmentInteractionListener, WorkshopsListFragment.OnFragmentInteractionListener {

    private Workshop[] workshopsData =
            {
                    new Workshop(0, "Android Workshop", "NSIT College", "10th Feb, 2018", 2500),
                    new Workshop(1, "Robotics Hands-on", "BVP, Mayur Vihar", "13th Feb, 2018", 3000),
                    new Workshop(2, "Web Dev Workshop", "DTU, Rohini", "4th March, 2018", 1000),
                    new Workshop(3, "Network Security Workshop", "Pratap Nagar", "20th March, 2018", 1800),
                    new Workshop(4, "Drone Build Workshop", "NSIT College", "23rd March, 2018", 2500),
                    new Workshop(5, "Python Basics", "MAIT College", "10th April, 2018", 1600)
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHandler dbHandler = new DBHandler(this);
        dbHandler.addWs(workshopsData);

        Fragment main = new WelcomeFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment, main)
                .commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
