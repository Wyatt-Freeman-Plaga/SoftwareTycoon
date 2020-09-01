package com.example.armoryandmachine.ui.craft;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.armoryandmachine.R;

public class CraftFragment extends Fragment implements View.OnClickListener {

    private CraftViewModel craftViewModel;
    private TextView workers;
    private TextView workerCap;
    private TextView heatCount;
    private ProgressBar mProgressBar;
    private Handler mHandler = new Handler();
    private int mProgressStatus;
    private int numWorkers = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        craftViewModel =
                ViewModelProviders.of(this).get(CraftViewModel.class);
        View root = inflater.inflate(R.layout.fragment_craft, container, false);
        final TextView textView = root.findViewById(R.id.text_craft);
        craftViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    public void updateTotalHeat() {
        //heatCount = (TextView) root.findViewById(R.id.heatCount);
        String stringHeat = Integer.toString(CraftViewModel.getTotalHeat());
        heatCount.setText(stringHeat);
    }


    @Override
    public void onClick(View v) {
        workers = (TextView) getView().findViewById(R.id.workerNum);
        workerCap = (TextView) getView().findViewById(R.id.workerNumTotal);
        switch(v.getId()) {
            case R.id.plus: {
                numWorkers=Integer.parseInt(workers.getText().toString());
                int numTotal=Integer.parseInt(workerCap.getText().toString());
                if(numWorkers<numTotal) {
                    numWorkers+=1;
                } else {
                    return;
                }
                String stringWorkers = Integer.toString(numWorkers);
                workers.setText(stringWorkers);

                //heatCount = getView().findViewById(R.id.heatCount);
                String stringHeat = Integer.toString(CraftViewModel.getTotalHeat());
                heatCount.setText(stringHeat);
                break;
            }
            case R.id.minus: {
                numWorkers=Integer.parseInt(workers.getText().toString());
                if(numWorkers>0) {
                    numWorkers-=1;
                } else {
                    return;
                }
                String stringWorkers = Integer.toString(numWorkers);
                workers.setText(stringWorkers);
                break;
            }
        }
    }
}