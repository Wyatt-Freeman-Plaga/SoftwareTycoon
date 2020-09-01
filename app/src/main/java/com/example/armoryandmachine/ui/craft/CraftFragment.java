package com.example.armoryandmachine.ui.craft;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.armoryandmachine.MainViewModel;
import com.example.armoryandmachine.R;
import com.example.armoryandmachine.RecyclerViewAdapterCraft;

import java.util.ArrayList;

public class CraftFragment extends Fragment {

    public CraftViewModel craftViewModel;
    public MainViewModel mainViewModel;
    private TextView workers;
    private TextView workerCap;
    private TextView heatCount;
    private ProgressBar mProgressBar;
    private Handler mHandler = new Handler();
    private int mProgressStatus;
    private int numWorkers = 0;
    private View root;

    private ArrayList<Integer> mImage = new ArrayList<>();
    private ArrayList<String> mResource = new ArrayList<>();
    private ArrayList<Integer> mResourceColor = new ArrayList<>();

    private ArrayList<String> mFirstTool = new ArrayList<>();
    private ArrayList<String> mSecondTool = new ArrayList<>();
    private ArrayList<String> mThirdTool = new ArrayList<>();

    private ProgressBar heatProgressBar;
    private ProgressBar tankProgressBar;
    private ProgressBar kilnProgressBar;
    private ProgressBar fluidProgressBar;

    private RecyclerView recyclerView;
    public boolean isRunning = true;
    public double uniqueID;
    public boolean paused = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        craftViewModel =
                new ViewModelProvider(this).get(CraftViewModel.class);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        mainViewModel = new ViewModelProvider(super.getActivity()).get(MainViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_craft, container, false);
        //final TextView textView = root.findViewById(R.id.text_craft);
        craftViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        initImageBitmaps();
        uniqueID = Math.random();
        recyclerView =  root.findViewById(R.id.recycler_view);
        initRecyclerView(root);
        this.root = root;
        return root;
    }

    @Override
    public void onPause() {
        this.paused = true;
        super.onPause();
    }

    @Override
    public void onResume() {
        this.paused = false;
        super.onResume();
    }

    public void updateWorkerCap(int position, TextView textToChange, Context context) {
        mainViewModel.increaseMaxWorkers(position, context);
        updateWorkers(position, textToChange, context);
    }

    private void initImageBitmaps(){
        mResource.add("Backend");
        mImage.add(R.drawable.backend);
        mResourceColor.add(getResources().getColor(R.color.red));
        mFirstTool.add("Code Cleanup");
        mSecondTool.add("Debugging");
        mThirdTool.add("Custom Engine");

        mainViewModel.loadData();

        if(mainViewModel.frontendUnlocked == true) {
            mResource.add("Frontend");
            mImage.add(R.drawable.frontend);
            mResourceColor.add(getResources().getColor(R.color.yellow));
            mFirstTool.add("Front-End 1");
            mSecondTool.add("Front-End 2");
            mThirdTool.add("Front-End 3");
        }

        if(mainViewModel.designUnlocked == true) {
            mResource.add("Design");
            mImage.add(R.drawable.design);
            mResourceColor.add(getResources().getColor(R.color.blue));
            mFirstTool.add("Custom Images");
            mSecondTool.add("Custom Sound");
            mThirdTool.add("3D Modeling");
        }

        if(mainViewModel.salesUnlocked == true) {
            mResource.add("Sales");
            mImage.add(R.drawable.sales);
            mResourceColor.add(getResources().getColor(R.color.purple));
            mFirstTool.add("Micro-\nTransactions");
            mSecondTool.add("Ad\nCampaign");
            mThirdTool.add("Celebrity Sponsorship");
        }
    }

    private void initRecyclerView(View root){
        if(mainViewModel.craftAdapter == null) {
            RecyclerViewAdapterCraft adapter = new RecyclerViewAdapterCraft(super.getContext(), mResource, mFirstTool, mSecondTool, mThirdTool, mResourceColor, mImage,this);
            recyclerView.setAdapter(adapter);
            System.out.println("New adapter made.");
        } else {
            recyclerView.setAdapter(mainViewModel.craftAdapter);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(super.getContext()));
    }

    public void addWorkerRequest(int position, TextView textToChange, Context context) {
        mainViewModel.addWorker(position, context);
        updateWorkers(position, textToChange, context);
    }

    public void takeWorkerRequest(int position, TextView textToChange, Context context) {
        mainViewModel.takeWorker(position, context);
        updateWorkers(position, textToChange, context);
    }

    public void updateWorkers(int position, TextView textToChange, Context context) {
        textToChange.setText(mainViewModel.getWorkersChar(position, context) + "/" + mainViewModel.getWorkerCapChar(position, context));
    }
}