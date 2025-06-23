package com.example.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class TypeWorks extends Observable {
    private List<TypeWork> typeWorkList = new ArrayList<>();

    public TypeWorks() {}

    public List<TypeWork> getTypeWorkList() {
        return typeWorkList;
    }

    public void setTypeWorkList(List<TypeWork> typeWorkList) {
        this.typeWorkList = typeWorkList;
        setChanged();
        notifyObservers();
    }
}