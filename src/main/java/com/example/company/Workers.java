package com.example.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Workers extends Observable {
    private List<Worker> workerList = new ArrayList<>();
    private Worker currentWorker;

    public Workers() {}

    public List<Worker> getWorkerList() {
        return workerList;
    }

    public void setWorkerList(List<Worker> workerList) {
        this.workerList = workerList;
        setChanged();
        notifyObservers();
    }

    public Worker getCurrentWorker() {
        return currentWorker;
    }

    public void setCurrentWorker(Worker worker) {
        this.currentWorker = worker;
        setChanged();
        notifyObservers();
    }
}