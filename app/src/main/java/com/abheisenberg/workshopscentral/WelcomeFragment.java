package com.abheisenberg.workshopscentral;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abheisenberg.workshopscentral.UserSharedPreferences.UserSharedPref;

import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WelcomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class WelcomeFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    private View rootView;
    private Button bt_seeallws, bt_signout, bt_signinW, bt_registerW;
    private LinearLayout ll_signin, ll_register;
    private FragmentTransaction fTrans;
    private UserSharedPref pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_welcome_screen, container, false);

        pref = new UserSharedPref(getActivity());

        if(rootView == null){
            Log.d("welcome ", "rootview is null ");
        }

        bt_registerW = (Button) rootView.findViewById(R.id.bt_registerW);
        bt_seeallws = (Button) rootView.findViewById(R.id.bt_showList);
        bt_signout = (Button) rootView.findViewById(R.id.bt_signoutW);
        bt_signinW = (Button) rootView.findViewById(R.id.bt_signinW);
        ll_register = (LinearLayout) rootView.findViewById(R.id.registerthings);
        ll_signin = (LinearLayout) rootView.findViewById(R.id.signinthings);

        updateUI(pref.isLoggedIn());

        fTrans = getFragmentManager().beginTransaction();

        bt_seeallws.setOnClickListener(this);
        bt_signout.setOnClickListener(this);
        bt_registerW.setOnClickListener(this);
        bt_signinW.setOnClickListener(this);

        Log.d(TAG, "Fragments before this: "+getFragmentManager().getBackStackEntryCount());

        return rootView;
    }

    public void updateUI(boolean isloggedin){
        /*
        Check if the user is logged in, if he is, then dont display the login button and if the user is not login,
        display the login and register button.
         */

        if(isloggedin){
            ll_register.setVisibility(View.GONE);
            ll_signin.setVisibility(View.GONE);
            bt_signout.setVisibility(View.VISIBLE);
        } else {
            ll_signin.setVisibility(View.VISIBLE);
            ll_register.setVisibility(View.VISIBLE);
            bt_signout.setVisibility(View.GONE);
        }
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
    public void onClick(View view) {
        int id = view.getId();

        fTrans = getFragmentManager().beginTransaction();
        Fragment nextFrag = null;

        switch (id){
            case R.id.bt_showList:
                nextFrag = new WorkshopsListFragment();
                break;

            case R.id.bt_registerW:
                nextFrag = new RegisterFragment();
                break;

            case R.id.bt_signinW:
                nextFrag = new SigninFragment();
                break;

            case R.id.bt_signoutW:
                nextFrag = this;
                if(pref.isLoggedIn()){
                    pref.signout();
                    Toast.makeText(getActivity(), "Signed out!", Toast.LENGTH_SHORT).show();
                    updateUI(pref.isLoggedIn());
                }
        }

        fTrans.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
        fTrans.replace(R.id.fragment, nextFrag);
        fTrans.commit();
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
