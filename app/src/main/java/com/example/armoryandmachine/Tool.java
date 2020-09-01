package com.example.armoryandmachine;

public class Tool {

    public boolean occupied = false;
    public boolean visible = false;
    public String name = "default";

    public Tool() {
        occupied = false;
        visible = true;
        name = "default";
    }

    public String getName() {
        return name;
    }
}
