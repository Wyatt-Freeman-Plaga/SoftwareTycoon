package com.example.armoryandmachine.ui.jobs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.example.armoryandmachine.JobActivity;
import com.example.armoryandmachine.MainViewModel;
import com.example.armoryandmachine.R;
import com.example.armoryandmachine.RecyclerViewAdapterJobs;
import com.example.armoryandmachine.RecyclerViewAdapterResearch;
import com.example.armoryandmachine.ui.research.ResearchViewModel;

import java.util.ArrayList;

public class JobsFragment extends Fragment {

    private JobsViewModel jobsViewModel;
    private ArrayList<String> mResource = new ArrayList<>();
    private ArrayList<Integer> mResistanceColor = new ArrayList<>();
    private ArrayList<String> mResistance = new ArrayList<>();
    private ArrayList<Integer> mVulnerabilityColor = new ArrayList<>();
    private ArrayList<String> mVulnerability = new ArrayList<>();
    public MainViewModel mainViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        jobsViewModel =
                ViewModelProviders.of(this).get(JobsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_jobs, container, false);
        //final TextView textView = root.findViewById(R.id.text_research);
        jobsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
        mResource.add("Mobile App Consult");
        mResistance.add("None");
        mResistanceColor.add(Color.BLACK);
        mVulnerability.add("Front-End");
        mVulnerabilityColor.add(getResources().getColor(R.color.yellow));

        //mainViewModel.loadData();

        if(jobsViewModel.highestUnlocked >= 1) {
            mResource.add("Small Game Mod");
            mResistance.add("None");
            mResistanceColor.add(Color.BLACK);
            mVulnerability.add("Back-End");
            mVulnerabilityColor.add(getResources().getColor(R.color.red));
        }

        if(jobsViewModel.highestUnlocked >= 2) {
            mResource.add("Indie Game Consult");
            mResistance.add("Design");
            mResistanceColor.add(getResources().getColor(R.color.blue));
            mVulnerability.add("Back-End");
            mVulnerabilityColor.add(getResources().getColor(R.color.red));
        }

        if(jobsViewModel.highestUnlocked >= 3) {
            mResource.add("Mobile App Co-Creator");
            mResistance.add("Back-End");
            mResistanceColor.add(getResources().getColor(R.color.red));
            mVulnerability.add("Front-End");
            mVulnerabilityColor.add(getResources().getColor(R.color.yellow));
        }

        if(jobsViewModel.highestUnlocked >= 4) {
            mResource.add("Basic Video Game Consult");
            mResistance.add("Sales");
            mResistanceColor.add(getResources().getColor(R.color.purple));
            mVulnerability.add("Design");
            mVulnerabilityColor.add(getResources().getColor(R.color.blue));
        }

        if(jobsViewModel.highestUnlocked >= 5) {
            mResource.add("Indie Game Co-Creator");
            mResistance.add("Design");
            mResistanceColor.add(getResources().getColor(R.color.blue));
            mVulnerability.add("Back-End");
            mVulnerabilityColor.add(getResources().getColor(R.color.red));
        }



        /*case 2: return "";
        case 3: return "";
        case 4: return "";
        case 5: return "";
        case 6: return "Medium Game Mod";
        case 7: return "Solo mobile App";
        case 8: return "Polished Video Game Consult";
        case 9: return "Large Mod";
        case 10: return "AAA Video Game Consult";
        case 11: return "Basic Video Game Co-Creator";
        case 12: return "Solo Indie Game";
        case 13: return "Polished Video Game Co-Creator";
        case 14: return "Solo Basic Video Game";
        case 15: return "AAA Video Game Co-Creator";
        case 16: return "Solo Polished Video Game";
        case 17: return "Solo AAA Video Game";
        case 18: return "Solo Industry Defining Game";*/

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
        RecyclerView recyclerView =  root.findViewById(R.id.jobs);
        if(mainViewModel.jobsAdapter == null) {
            RecyclerViewAdapterJobs adapter = new RecyclerViewAdapterJobs(super.getContext(), mResource, mResistanceColor, mResistance, mVulnerabilityColor, mVulnerability, this);
            recyclerView.setAdapter(adapter);
            System.out.println("New adapter made.");
        } else {
            recyclerView.setAdapter(mainViewModel.jobsAdapter);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(super.getContext()));
    }

    public void startJobsActivity(int position) {
        Intent intent = new Intent(getActivity(), JobActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}