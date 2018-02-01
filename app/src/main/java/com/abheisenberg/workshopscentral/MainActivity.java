package com.abheisenberg.workshopscentral;

import android.app.Fragment;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.abheisenberg.workshopscentral.SQLDatabaseWorkshops.DBHandler;
import com.abheisenberg.workshopscentral.UserSharedPreferences.UserSharedPref;
import com.abheisenberg.workshopscentral.WorkshopDataStructure.Workshop;

public class MainActivity extends AppCompatActivity implements
        WelcomeFragment.OnFragmentInteractionListener, RegisterFragment.OnFragmentInteractionListener,
        SigninFragment.OnFragmentInteractionListener, WorkshopsListFragment.OnFragmentInteractionListener,
            DashboardFragment.OnFragmentInteractionListener {

    private int BACK_TO_EXIT = 0;

    private UserSharedPref sharedPref;

    /*
    Hardcoded data for the database.
     */

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

        sharedPref = new UserSharedPref(this);

        /*
        If the user is logged-in, then display the dashboard activity.
        If the user is not logged in then just display the welcome screen to register/login.
         */

        if(sharedPref.isLoggedIn()){
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment, new DashboardFragment()).commit();
        } else  {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment, new WelcomeFragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {

        /*
        If the fragment stack is empty then display the welcome screen once, and if the back button is pressed,
        then exit the app.
         */

        if(getFragmentManager().getBackStackEntryCount() == 0 && BACK_TO_EXIT != 1){
            BACK_TO_EXIT++;

            Toast.makeText(this, "Press 'Back' once more to exit!", Toast.LENGTH_SHORT).show();

            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                    .replace(R.id.fragment, new DashboardFragment())
                    .commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
