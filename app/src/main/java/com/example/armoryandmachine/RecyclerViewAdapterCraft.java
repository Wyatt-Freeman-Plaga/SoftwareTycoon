package com.example.armoryandmachine;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.armoryandmachine.ui.craft.CraftFragment;

import java.util.ArrayList;

public class RecyclerViewAdapterCraft extends RecyclerView.Adapter<RecyclerViewAdapterCraft.ViewHodler> {

    private Context mContext;
    //private ArrayList<String> mImage = new ArrayList<>();
    private ArrayList<String> mResource = new ArrayList<>();
    private ArrayList<Integer> mResourceColor = new ArrayList<>();
    private ArrayList<String> mTool1 = new ArrayList<>();
    private ArrayList<String> mTool2 = new ArrayList<>();
    private ArrayList<String> mTool3 = new ArrayList<>();
    public CraftFragment craftFragment;
    RecyclerViewAdapterCraft recyclerViewAdapter;
    private int numBound = 0;
    private int tracker = 0;
    private ArrayList<Integer> imageString = new ArrayList<>();

    public RecyclerViewAdapterCraft(Context context, ArrayList<String> resource, ArrayList<String> mTool1, ArrayList<String> mTool2, ArrayList<String> mTool3, ArrayList<Integer> resourceColor, ArrayList<Integer> imageString, CraftFragment craftFragment) {
        mContext = context;
        //this.mImage = mImage;
        mResource = resource;
        mResourceColor = resourceColor;
        this.mTool1 = mTool1;
        this.mTool2 = mTool2;
        this.mTool3 = mTool3;
        this.craftFragment = craftFragment;
        this.imageString = imageString;
        recyclerViewAdapter = this;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_craft, parent, false);
        ViewHodler holder = new ViewHodler(view);
        craftFragment.mainViewModel.craftAdapter = this;
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHodler holder, final int position) {
        holder.resourceTitle.setText(mResource.get(position));
        holder.resourceTitle.setTextColor(mResourceColor.get(position));
        holder.resource.setText(mResource.get(position));
        holder.tool1.setText(mTool1.get(position));
        holder.tool2.setText(mTool2.get(position));
        holder.tool3.setText(mTool3.get(position));
        craftFragment.updateWorkers(position, holder.workers, mContext);
        ProgressBar mProgressBar = holder.progressBar;
        craftFragment.updateWorkerCap(position, holder.workers, mContext);
        Handler mHandler = new Handler();
        double randomID = Math.random();
        final int bound = numBound;
        holder.image.setImageResource(imageString.get(position));
        switch(position) {
            case 0: //holder.resource.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                holder.imageBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red));
                break;
            case 1: //holder.resource.setTextColor(ContextCompat.getColor(mContext, R.color.yellow));
                holder.imageBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.yellow));
                break;
            case 2: //holder.resource.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
                holder.imageBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.blue));
                break;
            case 3: //holder.resource.setTextColor(ContextCompat.getColor(mContext, R.color.purple));
                holder.imageBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.purple));
                break;
            default: //holder.resource.setTextColor(ContextCompat.getColor(mContext, R.color.yellow));
        }
        System.out.println(holder.resource.getTextColors());
        numBound++;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(numBound - 4 <= bound){
                    tracker++;
                    if(tracker % 50 == 0) {
                        craftFragment.mainViewModel.saveData();
                    }
                    //System.out.println(bound + " bound ID.");
                    /*if(craftFragment.paused == true) {
                        System.out.println("sleep");
                        android.os.SystemClock.sleep(50);
                        continue;
                    }*/
                    //System.out.println("no sleep");
                    if(craftFragment.mainViewModel.tooHigh(position) == true) {
                        holder.progressBar.setProgress(craftFragment.mainViewModel.updateTotals(position, mContext));
                        new Handler(Looper.getMainLooper()).post(new Runnable(){
                            @Override
                            public void run() {
                                holder.resource.setText(mResource.get(position) + ": " + craftFragment.mainViewModel.getFullTotalChar(position) + "/" + craftFragment.mainViewModel.getMaxChar(position, mContext));
                                holder.progressBar.setIndeterminate(true);
                            }
                        });
                    } else {
                        holder.progressBar.setProgress(craftFragment.mainViewModel.updateTotals(position, mContext));
                        new Handler(Looper.getMainLooper()).post(new Runnable(){
                            @Override
                            public void run() {
                                holder.progressBar.setIndeterminate(false);
                                holder.resource.setText(mResource.get(position) + ": " + craftFragment.mainViewModel.getFullTotalChar(position) + "/" + craftFragment.mainViewModel.getMaxChar(position, mContext));
                            }
                        });
                    }
                    android.os.SystemClock.sleep(5);
                }
            }
        }).start();

        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                craftFragment.addWorkerRequest(position, holder.workers, mContext);
                craftFragment.mainViewModel.updateTotals(position, mContext);
            }
        });
        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                craftFragment.takeWorkerRequest(position, holder.workers, mContext);
                craftFragment.mainViewModel.updateTotals(position, mContext);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResource.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder{

        TextView resourceTitle;
        Button plusButton;
        Button minusButton;
        TextView tool1;
        TextView tool2;
        TextView tool3;
        View imageBackground;
        ImageView image;
        TextView workers;
        ConstraintLayout constraintLayout;
        View progressBarBackground;
        ProgressBar progressBar;
        Button disableButton;
        TextView resource;
        RelativeLayout parentLayout;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            resourceTitle = itemView.findViewById(R.id.resorceTitle);
            plusButton = itemView.findViewById(R.id.plus);
            minusButton = itemView.findViewById(R.id.minus);
            tool1 = itemView.findViewById(R.id.tool1);
            tool2 = itemView.findViewById(R.id.tool2);
            tool3 = itemView.findViewById(R.id.tool3);
            imageBackground = itemView.findViewById(R.id.black);
            image = itemView.findViewById(R.id.wrench);
            workers = itemView.findViewById(R.id.workers);
            constraintLayout = itemView.findViewById(R.id.constraint);
            progressBarBackground = itemView.findViewById(R.id.blackBar);
            progressBar = itemView.findViewById(R.id.progressBar);
            disableButton = itemView.findViewById(R.id.buyWorker);
            resource = itemView.findViewById(R.id.resource);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
