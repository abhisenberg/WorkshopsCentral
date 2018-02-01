package com.abheisenberg.workshopscentral;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.abheisenberg.workshopscentral.SQLDatabaseWorkshops.DBHandler;
import com.abheisenberg.workshopscentral.WorkshopDataStructure.Workshop;
import com.abheisenberg.workshopscentral.WorkshopDataStructure.WorkshopAdapter;

import java.io.File;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkshopsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class WorkshopsListFragment extends Fragment implements WorkshopAdapter.LoginFirst{

    private OnFragmentInteractionListener mListener;
    public static final String TAG = "listfrag";
    public WorkshopsListFragment() {
        // Required empty public constructor
    }

    private View rootView;
    private Button bt_apply;
    private FragmentTransaction fTrans;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_workshops_list, container, false);

        fTrans = getFragmentManager().beginTransaction();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_wslist);
        WorkshopAdapter adapter = new WorkshopAdapter(getActivity(), new ArrayList<Workshop>(), this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

       adapter.showWS();

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

    @Override
    public void loginBeforeApply() {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment, new SigninFragment())
                .addToBackStack(null)
                .commit();
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
