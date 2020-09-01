package com.example.armoryandmachine;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.armoryandmachine.ui.jobs.JobsFragment;
import com.example.armoryandmachine.ui.log.LogFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewAdapterLog extends RecyclerView.Adapter<RecyclerViewAdapterLog.ViewHodler> {

    private Context mContext;
    private ArrayList<String> mText;
    private ArrayList<String> mEntry;
    LogFragment logFragment;

    public RecyclerViewAdapterLog(Context context, ArrayList<String> text, ArrayList<String> entry, LogFragment logFragment) {
        mContext = context;
        mText = text;
        mEntry = entry;
        this.logFragment = logFragment;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_log, parent, false);
        ViewHodler holder = new ViewHodler(view);
        logFragment.mainViewModel.logAdapter = this;
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHodler holder, final int position) {
        Handler mHandler = new Handler();
        holder.mText.setText(mText.get(position));
    }

    @Override
    public int getItemCount() {
        return mText.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder{

        Button begin;
        TextView mEntry;
        TextView mText;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            begin = itemView.findViewById(R.id.startButton);
            mEntry = itemView.findViewById(R.id.logEntry);
            mText = itemView.findViewById(R.id.logEntryText);
        }
    }
}