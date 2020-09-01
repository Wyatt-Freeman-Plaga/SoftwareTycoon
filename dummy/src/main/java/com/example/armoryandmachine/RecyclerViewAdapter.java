package com.example.armoryandmachine;

import android.content.Context;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHodler> {

    private Context mContext;
    //private ArrayList<String> mImage = new ArrayList<>();
    private ArrayList<String> mResource = new ArrayList<>();

    public RecyclerViewAdapter(Context context, ArrayList<String> resource) {
        mContext = context;
        //this.mImage = mImage;
        mResource = resource;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHodler holder = new ViewHodler(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        holder.resource.setText(mResource.get(position));
    }

    @Override
    public int getItemCount() {
        return mResource.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder{

        FrameLayout frameLayout;
        Button plusButton;
        Button minusButton;
        View imageBackground;
        ImageView image;
        TextView workerNum;
        TextView slash;
        TextView workerCap;
        ConstraintLayout constraintLayout;
        View progressBarBackground;
        ProgressBar progressBar;
        Button disableButton;
        TextView resource;
        RelativeLayout parentLayout;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.frameBackground);
            plusButton = itemView.findViewById(R.id.plus);
            minusButton = itemView.findViewById(R.id.minus);
            imageBackground = itemView.findViewById(R.id.black);
            image = itemView.findViewById(R.id.wrench);
            workerNum = itemView.findViewById(R.id.workerNum);
            slash = itemView.findViewById(R.id.workerNumDash);
            workerCap = itemView.findViewById(R.id.workerNumTotal);
            constraintLayout = itemView.findViewById(R.id.constraint);
            progressBarBackground = itemView.findViewById(R.id.blackBar);
            progressBar = itemView.findViewById(R.id.heatBar);
            disableButton = itemView.findViewById(R.id.disableAutomation);
            resource = itemView.findViewById(R.id.resource);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
