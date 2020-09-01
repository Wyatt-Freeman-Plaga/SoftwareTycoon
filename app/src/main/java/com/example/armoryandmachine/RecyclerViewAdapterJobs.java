package com.example.armoryandmachine;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.armoryandmachine.ui.craft.CraftFragment;
import com.example.armoryandmachine.ui.jobs.JobsFragment;
import com.example.armoryandmachine.ui.research.ResearchFragment;

import java.util.ArrayList;

public class RecyclerViewAdapterJobs extends RecyclerView.Adapter<RecyclerViewAdapterJobs.ViewHodler> {

    private Context mContext;
    //private ArrayList<String> mImage = new ArrayList<>();
    private ArrayList<String> mResource = new ArrayList<>();
    private ArrayList<Integer> mResistanceColor = new ArrayList<>();
    private ArrayList<String> mResistanceType = new ArrayList<>();
    private ArrayList<Integer> mVulnerabilityColor = new ArrayList<>();
    private ArrayList<String> mVulnerabilityType = new ArrayList<>();
    private JobsFragment jobsFragment;
    private int numBound = 0;

    public RecyclerViewAdapterJobs(Context context, ArrayList<String> resource, ArrayList<Integer> resistanceColor, ArrayList<String> resistanceType, ArrayList<Integer> vulnerabilityColor, ArrayList<String> vulnerabilityType, JobsFragment jobsFragment) {
        mContext = context;
        mResource = resource;
        mResistanceColor = resistanceColor;
        mResistanceType = resistanceType;
        mVulnerabilityColor = vulnerabilityColor;
        mVulnerabilityType = vulnerabilityType;
        this.jobsFragment = jobsFragment;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_jobs, parent, false);
        ViewHodler holder = new ViewHodler(view);
        jobsFragment.mainViewModel.jobsAdapter = this;
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHodler holder, final int position) {
        Handler mHandler = new Handler();
        holder.jobTitle.setText(mResource.get(position));
        holder.resistanceType.setText(mResistanceType.get(position));
        holder.resistanceType.setTextColor(mResistanceColor.get(position));
        //holder.resistanceType.setTextColor(50055);
        holder.vulnerabilityType.setText(mVulnerabilityType.get(position));
        holder.vulnerabilityType.setTextColor(mVulnerabilityColor.get(position));


        holder.begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobsFragment.startJobsActivity(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResource.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder{

        Button begin;
        TextView jobTitle;
        TextView resistanceType;
        TextView vulnerabilityType;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            begin = itemView.findViewById(R.id.startButton);
            jobTitle = itemView.findViewById(R.id.jobTitle);
            resistanceType = itemView.findViewById(R.id.resistanceType);
            vulnerabilityType = itemView.findViewById(R.id.vulnerabilityType);
        }
    }
}
