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

import java.util.ArrayList;

public class RecyclerViewAdapterLoadout extends RecyclerView.Adapter<RecyclerViewAdapterLoadout.ViewHodler> {

    private Context mContext;
    private ArrayList<String> toolNames = new ArrayList<>();
    private LoadoutActivity loadoutActivity;
    private TextView toolName;

    public RecyclerViewAdapterLoadout(Context context, ArrayList<String> toolNames, LoadoutActivity loadoutActivity) {
        mContext = context;
        this.toolNames = toolNames;
        this.loadoutActivity = loadoutActivity;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loadout, parent, false);
        ViewHodler holder = new ViewHodler(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHodler holder, final int position) {
        Handler mHandler = new Handler();
        holder.toolName.setText(toolNames.get(position));
        final RecyclerViewAdapterLoadout adapter = this;

        holder.addToLoadout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadoutActivity.loadoutViewModel.addTool((String) holder.toolName.getText());
                loadoutActivity.updateLoadout();
                toolNames.remove(holder.getAdapterPosition());
                adapter.notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return toolNames.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder{

        Button addToLoadout;
        TextView toolName;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            addToLoadout = itemView.findViewById(R.id.addMoveToLoadout);
            toolName = itemView.findViewById(R.id.moveName);
        }
    }
}
