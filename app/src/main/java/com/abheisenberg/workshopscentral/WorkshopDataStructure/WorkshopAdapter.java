package com.abheisenberg.workshopscentral.WorkshopDataStructure;

/**
 * Created by abheisenberg on 31/1/18.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.abheisenberg.workshopscentral.R;
import com.abheisenberg.workshopscentral.SQLDatabaseWorkshops.DBHandler;
import com.abheisenberg.workshopscentral.UserSharedPreferences.UserSharedPref;

import java.util.ArrayList;

public class WorkshopAdapter extends RecyclerView.Adapter<WorkshopAdapter.WorkshopViewHolder> {

    public interface LoginFirst {
        void loginBeforeApply();
    }

    LoginFirst loginFirst;

    public void loginApply(LoginFirst loginFirst) {
        this.loginFirst = loginFirst;
    }

    private static final String TAG = "ArticleAdapter";

    private Context context;
    private ArrayList<Workshop> wsList;
    UserSharedPref pref;

    public WorkshopAdapter(Context context, ArrayList<Workshop> wsList, LoginFirst loginFirst){
        this.context = context;
        this.loginFirst = loginFirst;
        this.wsList = wsList;
        this.pref = new UserSharedPref(context);;
    }

    public void showWS(){
        DBHandler dbHandler = new DBHandler(context);
        this.wsList = (ArrayList<Workshop>) dbHandler.getAllWs();
//        Toast.makeText(context, "New size "+wsList.size(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "updateArticles, new size: "+wsList.size());
        notifyDataSetChanged();
    }

    @Override
    public WorkshopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.workshop_item, parent, false);
        return new WorkshopViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WorkshopViewHolder holder, int position) {
        final Workshop thisWs = wsList.get(position);

        Log.d(TAG, "onBindViewHolder: size: "+wsList.size());

        holder.tv_wsDate.setText(thisWs.getDATE());
        holder.tv_wsTitle.setText(thisWs.getTITLE());
        holder.tv_wsFees.setText("Rs. "+String.valueOf(thisWs.getFEES()));
        holder.tv_wsVenue.setText(thisWs.getVENUE());

        if(pref.isLoggedIn() && pref.alreadyApplied(thisWs.getTITLE())){
            holder.bt_apply.setText("Applied");
            holder.bt_apply.setBackgroundColor(Color.WHITE);
            holder.bt_apply.setTextColor(Color.BLACK);
        }

        holder.bt_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pref.isLoggedIn()){
                    pref.apply(thisWs.getTITLE());
                    Toast.makeText(context, "Successfully Applied!", Toast.LENGTH_SHORT).show();
                    holder.bt_apply.setText("Applied");
                    holder.bt_apply.setBackgroundColor(Color.WHITE);
                    holder.bt_apply.setTextColor(Color.BLACK);
                } else {
                    loginFirst.loginBeforeApply();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return wsList.size();
    }

    class WorkshopViewHolder extends RecyclerView.ViewHolder {
        TextView tv_wsTitle, tv_wsVenue, tv_wsFees, tv_wsDate;
        Button bt_apply;

        public WorkshopViewHolder(View itemView) {
            super(itemView);
            tv_wsTitle = (TextView) itemView.findViewById(R.id.tv_wsTitle);
            tv_wsDate = (TextView) itemView.findViewById(R.id.tv_wsDate);
            tv_wsFees = (TextView) itemView.findViewById(R.id.tv_wsFees);
            tv_wsVenue = (TextView) itemView.findViewById(R.id.tv_wsVenue);
            bt_apply = (Button) itemView.findViewById(R.id.bt_apply);
        }
    }

}
