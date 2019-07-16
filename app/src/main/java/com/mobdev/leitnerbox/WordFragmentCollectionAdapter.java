package com.mobdev.leitnerbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class WordFragmentCollectionAdapter extends FragmentStatePagerAdapter implements Serializable {

    private LeitnerCardList chosenCardsList;
    private ArrayList<Mark> wordsMark;
    private int boxNumber;

    public WordFragmentCollectionAdapter(FragmentManager fm, int boxNumber) {
        super(fm);
        this.boxNumber = boxNumber;

        chosenCardsList = new LeitnerCardList(boxNumber);
        wordsMark = new ArrayList<>();
        int size = chosenCardsList.getReadyCardNum();

        for (int i = 0; i < size; i++) {
            wordsMark.add(Mark.NOTHING);
        }

    }


    public LeitnerCardList getChosenCardsList() {
        return chosenCardsList;
    }

    @Override
    public Fragment getItem(int position) {
        WordFragment wordFragment = new WordFragment();
        Bundle bundle = new Bundle();

        LeitnerCardList.LeitnerCard cardAtPosition = chosenCardsList.getCardAtPosition(position);
        bundle.putInt("POSITION", position);
        bundle.putInt("COUNT", getCount());
        bundle.putString("WORD", cardAtPosition.getWord());
        bundle.putString("MEANING", cardAtPosition.getMeaning());
        bundle.putString("RELEASEDTIMESTR", "" + cardAtPosition.getReleaseTime());
        bundle.putInt("BOXNUMBER", boxNumber);

        wordFragment.setAdapter(this);
        wordFragment.setArguments(bundle);

        return wordFragment;
    }

    @Override
    public int getCount() {
        return chosenCardsList.getReadyCardNum();
    }

    public ArrayList<Mark> getWordsMark() {
        return this.wordsMark;
    }

    public void setWordsMarkAtPosition(int position, Mark mark) {
        this.wordsMark.set(position - 1, mark);
    }

    public enum Mark {
        NOTHING,
        KNOWN,
        NOT_KNOWN,
        DELETED
    }


}
