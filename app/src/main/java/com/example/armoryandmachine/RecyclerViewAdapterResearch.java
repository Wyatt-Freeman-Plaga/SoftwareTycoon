package com.example.armoryandmachine;

import android.content.Context;
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
import com.example.armoryandmachine.ui.research.ResearchFragment;

import java.util.ArrayList;

public class RecyclerViewAdapterResearch extends RecyclerView.Adapter<RecyclerViewAdapterResearch.ViewHodler> {

    private Context mContext;
    //private ArrayList<String> mImage = new ArrayList<>();
    private ArrayList<String> mResource = new ArrayList<>();
    private ResearchFragment researchFragment;
    private int numBound = 0;

    public RecyclerViewAdapterResearch(Context context, ArrayList<String> resource, ResearchFragment researchFragment) {
        mContext = context;
        mResource = resource;
        this.researchFragment = researchFragment;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_research, parent, false);
        ViewHodler holder = new ViewHodler(view);
        researchFragment.mainViewModel.researchAdapter = this;
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHodler holder, final int position) {
        Handler mHandler = new Handler();
        holder.jobSpeedNeeded.setText(researchFragment.mainViewModel.requiredForRate(position, mContext) + " " + mResource.get(position));
        holder.itemCapacityNeeded.setText(researchFragment.mainViewModel.requiredForItemCapacity(position, mContext) + " " + mResource.get(position));
        holder.workerCapacityNeeded.setText(researchFragment.mainViewModel.requiredForMaxWorkers(position, mContext) + " " + mResource.get(position));
        final int bound = numBound;
        numBound++;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(numBound - 4 <= bound){
                    new Handler(Looper.getMainLooper()).post(new Runnable(){
                        @Override
                        public void run() {
                            holder.resource.setText(mResource.get(position) + ": " + researchFragment.mainViewModel.getFullTotalChar(position));
                        }
                    });
                    android.os.SystemClock.sleep(500);
                }
            }
        }).start();

        holder.jobSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                researchFragment.mainViewModel.increaseRate(position, mContext);
                holder.jobSpeedNeeded.setText(researchFragment.mainViewModel.requiredForRate(position, mContext) + " " + mResource.get(position));
                holder.itemCapacityNeeded.setText(researchFragment.mainViewModel.requiredForItemCapacity(position, mContext) + " " + mResource.get(position));
                holder.workerCapacityNeeded.setText(researchFragment.mainViewModel.requiredForMaxWorkers(position, mContext) + " " + mResource.get(position));
            }
        });
        holder.workerCapacity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                researchFragment.mainViewModel.increaseMaxWorkers(position, mContext);
                holder.jobSpeedNeeded.setText(researchFragment.mainViewModel.requiredForRate(position, mContext) + " " + mResource.get(position));
                holder.itemCapacityNeeded.setText(researchFragment.mainViewModel.requiredForItemCapacity(position, mContext) + " " + mResource.get(position));
                holder.workerCapacityNeeded.setText(researchFragment.mainViewModel.requiredForMaxWorkers(position, mContext) + " " + mResource.get(position));
            }
        });
        holder.itemCapacity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                researchFragment.mainViewModel.increaseItemCapacity(position, mContext);
                holder.jobSpeedNeeded.setText(researchFragment.mainViewModel.requiredForRate(position, mContext) + " " + mResource.get(position));
                holder.itemCapacityNeeded.setText(researchFragment.mainViewModel.requiredForItemCapacity(position, mContext) + " " + mResource.get(position));
                holder.workerCapacityNeeded.setText(researchFragment.mainViewModel.requiredForMaxWorkers(position, mContext) + " " + mResource.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResource.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder{

        Button jobSpeed;
        Button itemCapacity;
        Button workerCapacity;
        TextView resource;
        TextView jobSpeedNeeded;
        TextView workerCapacityNeeded;
        TextView itemCapacityNeeded;
        Button techTree;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            resource = itemView.findViewById(R.id.resourceResearch);
            jobSpeed = itemView.findViewById(R.id.jobSpeed);
            itemCapacity = itemView.findViewById(R.id.itemCapacity);
            workerCapacity = itemView.findViewById(R.id.workerCapacity);
            jobSpeedNeeded = itemView.findViewById(R.id.jobSpeedNeeded);
            workerCapacityNeeded = itemView.findViewById(R.id.workerCapscityNeeded);
            itemCapacityNeeded = itemView.findViewById(R.id.itemCapacityNeeded);
            techTree = itemView.findViewById(R.id.techTree);
        }
    }
}
