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
    private Button bt_seeallws, bt_signout;
    private TextView tv_signin, tv_register;
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

        tv_register = (TextView) rootView.findViewById(R.id.tv_register);
        bt_seeallws = (Button) rootView.findViewById(R.id.bt_showList);
        bt_signout = (Button) rootView.findViewById(R.id.bt_signout);
        tv_signin = (TextView) rootView.findViewById(R.id.tv_signin);
        ll_register = (LinearLayout) rootView.findViewById(R.id.registerthings);
        ll_signin = (LinearLayout) rootView.findViewById(R.id.signinthings);

        updateUI(pref.isLoggedIn());

        fTrans = getFragmentManager().beginTransaction();

        bt_seeallws.setOnClickListener(this);
        bt_signout.setOnClickListener(this);
        tv_signin.setOnClickListener(this);
        tv_register.setOnClickListener(this);

        return rootView;
    }

    public void updateUI(boolean isloggedin){
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

        Fragment nextFrag = null;

        switch (id){
            case R.id.bt_showList:
                nextFrag = new WorkshopsListFragment();
                break;

            case R.id.tv_register:
                nextFrag = new RegisterFragment();
                break;

            case R.id.tv_signin:
                nextFrag = new SigninFragment();
                break;

            case R.id.bt_signout:
                if(pref.isLoggedIn()){
                    pref.signout();
                    Toast.makeText(getActivity(), "Signed out!", Toast.LENGTH_SHORT).show();
                    updateUI(pref.isLoggedIn());
                }
        }

        fTrans.replace(R.id.fragment, nextFrag);
        fTrans.addToBackStack(null);
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
