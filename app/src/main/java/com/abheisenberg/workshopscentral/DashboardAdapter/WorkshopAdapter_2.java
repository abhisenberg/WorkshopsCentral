package com.abheisenberg.workshopscentral.DashboardAdapter;

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
import com.abheisenberg.workshopscentral.WorkshopDataStructure.Workshop;

import java.util.ArrayList;

public class WorkshopAdapter_2 extends RecyclerView.Adapter<WorkshopAdapter_2.WorkshopViewHolder> {

    private static final String TAG = "ArticleAdapter";

    private Context context;
    private ArrayList<Workshop> wsList;
    UserSharedPref pref;

    public WorkshopAdapter_2(Context context, ArrayList<Workshop> wsList){
        this.context = context;
        this.wsList = wsList;
        this.pref = new UserSharedPref(context);;
    }

    public void showWS(){
        DBHandler dbHandler = new DBHandler(context);
        this.wsList = dbHandler.getAllWs();
        Log.d(TAG, "updateArticles, new size: "+wsList.size());
        notifyDataSetChanged();
    }

    @Override
    public WorkshopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.workshop_item_dashboard, parent, false);
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

    }

    @Override
    public int getItemCount() {
        return wsList.size();
    }

    class WorkshopViewHolder extends RecyclerView.ViewHolder {
        TextView tv_wsTitle, tv_wsVenue, tv_wsFees, tv_wsDate;

        public WorkshopViewHolder(View itemView) {
            super(itemView);
            tv_wsTitle = (TextView) itemView.findViewById(R.id.tv_wsTitle);
            tv_wsDate = (TextView) itemView.findViewById(R.id.tv_wsDate);
            tv_wsFees = (TextView) itemView.findViewById(R.id.tv_wsFees);
            tv_wsVenue = (TextView) itemView.findViewById(R.id.tv_wsVenue);
        }
    }

}
