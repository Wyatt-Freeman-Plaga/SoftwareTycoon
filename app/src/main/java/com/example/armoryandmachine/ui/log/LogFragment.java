package com.example.armoryandmachine.ui.log;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.armoryandmachine.MainViewModel;
import com.example.armoryandmachine.R;
import com.example.armoryandmachine.RecyclerViewAdapterLog;
import com.example.armoryandmachine.RecyclerViewAdapterResearch;
import com.example.armoryandmachine.ui.jobs.JobsViewModel;

import java.util.ArrayList;

public class LogFragment extends Fragment {

    private LogViewModel logViewModel;
    private ArrayList<String> mText = new ArrayList<>();
    private ArrayList<String> mEntry = new ArrayList<>();
    public MainViewModel mainViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        logViewModel =
                ViewModelProviders.of(this).get(LogViewModel.class);
        View root = inflater.inflate(R.layout.fragment_log, container, false);
        //final TextView textView = root.findViewById(R.id.text_research);
        logViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        initImageBitmaps();
        initRecyclerView(root);
        return root;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        mainViewModel = new ViewModelProvider(super.getActivity()).get(MainViewModel.class);
    }

    private void initImageBitmaps(){
        mEntry.add("Entry 0");
        mText.add("Your first day trying to found a new video game startup. There's plenty of office space to expand into, but for now, it's just you and your computer.");

        /*if(mainViewModel.frontendUnlocked == true) {
            mResource.add("Frontend");
        }

        if(mainViewModel.designUnlocked == true) {
            mResource.add("Design");
        }

        if(mainViewModel.salesUnlocked == true) {
            mResource.add("Sales");
        }*/
    }

    private void initRecyclerView(View root){
        RecyclerView recyclerView =  root.findViewById(R.id.logRecyclerView);
        if(mainViewModel.logAdapter == null) {
            RecyclerViewAdapterLog adapter = new RecyclerViewAdapterLog(super.getContext(), mText, mEntry, this);
            recyclerView.setAdapter(adapter);
            System.out.println("New adapter made.");
        } else {
            recyclerView.setAdapter(mainViewModel.logAdapter);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(super.getContext()));
    }
}