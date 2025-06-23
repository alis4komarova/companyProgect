package com.example.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Participations extends Observable {
    private List<Participation> participationList = new ArrayList<>();

    public Participations() {}

    public List<Participation> getParticipationList() {
        return participationList;
    }

    public void setParticipationList(List<Participation> participationList) {
        this.participationList = participationList;
        setChanged();
        notifyObservers();
    }
}