package com.example.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ExtraWorks extends Observable {
    private List<ExtraWork> extraWorkList = new ArrayList<>();

    public ExtraWorks() {}

    public List<ExtraWork> getExtraWorkList() {
        return extraWorkList;
    }

    public void setExtraWorkList(List<ExtraWork> extraWorkList) {
        this.extraWorkList = extraWorkList;
        setChanged();
        notifyObservers();
    }
}