package com.abheisenberg.workshopscentral;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.abheisenberg.workshopscentral.DashboardAdapter.WorkshopAdapter_2;
import com.abheisenberg.workshopscentral.SQLDatabaseWorkshops.DBHandler;
import com.abheisenberg.workshopscentral.UserSharedPreferences.UserSharedPref;
import com.abheisenberg.workshopscentral.WorkshopDataStructure.Workshop;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DashboardFragment extends Fragment {

    private RecyclerView rv_list_dash;
    private View rootView;
    private UserSharedPref pref;
    private Button bt_dashback;
    private DBHandler dbHandler;
    private OnFragmentInteractionListener mListener;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dbHandler = new DBHandler(getActivity());
        bt_dashback = (Button) rootView.findViewById(R.id.dash_back);
        rv_list_dash = (RecyclerView) rootView.findViewById(R.id.DashboardRV);
        pref = new UserSharedPref(getActivity());

        bt_dashback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                        .replace(R.id.fragment, new WelcomeFragment())
                        .commit();
            }
        });

        /*
        If the user is not logged in, just display an error message to login
         */

        if(!pref.isLoggedIn()) {
            Toast.makeText(getActivity(), "Please Login to view Dashboard!", Toast.LENGTH_SHORT).show();
            return rootView;
        }

        /*
        If the user is logged in, fetch the list of applied courses and display the list on the recycler view.
        And the initialize the views.
         */

        ArrayList<Integer> applied = pref.getUserApplied(pref.getEmail());
        if(applied.size() == 0)
            Toast.makeText(getActivity(), "Not applied to any Workshop yet!", Toast.LENGTH_SHORT).show();
        else {
            ArrayList<Workshop> allws = new ArrayList<>();
            for(int i=0; i<applied.size(); i++){
                allws.add(dbHandler.getWSfromID(applied.get(i)));
            }

            WorkshopAdapter_2 adapter = new WorkshopAdapter_2( getActivity(),allws);
            rv_list_dash.setLayoutManager(new LinearLayoutManager(getActivity()));
            rv_list_dash.setAdapter(adapter);
        }

        Log.d(TAG, "Fragments before this: "+getFragmentManager().getBackStackEntryCount());

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
