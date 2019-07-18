package com.mobdev.leitnerbox;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Size;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mobdev.leitnerbox.WordFragmentCollectionAdapter.Mark;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class WordFragment extends Fragment {

    private TextView wordTextView, meaningTextView, cardNumberTextView;
    private Button knowButton, notKnowButton;
    private Button deleteButton;
    private FrameLayout frameLayout;

    private ViewGroup container;

    private ArrayList<Mark> wordsMark;
    int count, position, boxNumber, nextBoxNumber, previousBoxNumber;
    Mark mark;
    String word, meaning, releasedTimeStr;
    private LeitnerCardList chosenCardsList;
    private WordFragmentCollectionAdapter adapter;


    public void setAdapter(WordFragmentCollectionAdapter adapter) {
        this.adapter = adapter;
        this.wordsMark = adapter.getWordsMark();
        this.chosenCardsList = adapter.getChosenCardsList();
    }

    public WordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        this.container = container;

        View view = inflater.inflate(R.layout.fragment_word, container, false);
        wordTextView = view.findViewById(R.id.word_textView);
        meaningTextView = view.findViewById(R.id.meaning_textView);
        knowButton = view.findViewById(R.id.know_button);
        notKnowButton = view.findViewById(R.id.notKnow_button);
        cardNumberTextView = view.findViewById(R.id.cardNumber_textView);
        frameLayout = view.findViewById(R.id.wordsFragment_frameLayout);

        wordTextView.setText("There are no words ready to show.");
        if (getArguments() != null) {
            position = getArguments().getInt("POSITION");
            mark = wordsMark.get(position);
            count = getArguments().getInt("COUNT");
            word = getArguments().getString("WORD");
            meaning = getArguments().getString("MEANING");
            releasedTimeStr = getArguments().getString("RELEASEDTIMESTR");
            boxNumber = getArguments().getInt("BOXNUMBER");
            nextBoxNumber = boxNumber + 1;
            previousBoxNumber = boxNumber - 1;
            if(boxNumber == 1)
                previousBoxNumber = 1;
        }
        wordTextView.setText(word);


        if(mark == Mark.KNOWN) {
            wordTextView.setBackground(getResources().getDrawable(R.drawable.know_button_background, null));
        } else if(mark == Mark.NOT_KNOWN) {
            wordTextView.setBackground(getResources().getDrawable(R.drawable.notknow_button_background, null));
        } else if(mark == Mark.DELETED) {
            wordTextView.setBackgroundColor(Color.parseColor("#CFBAFF"));
            cardNumberTextView.setBackground(getResources().getDrawable(R.drawable.deleted_card_background, null));
            cardNumberTextView.setTextColor(Color.parseColor("#CF0808"));
            deleteButton.setVisibility(View.INVISIBLE);
            wordTextView.setText("");
            meaningTextView.setText("");
        }

        if(mark == Mark.DELETED) {
            cardNumberTextView.setText("word number " + ++position + " / " + count + " HAS BEEN DELETED!");
        } else {
            cardNumberTextView.setText("word number " + ++position + " / " + count);
        }


        knowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mark != Mark.NOTHING) {
                    return;
                }

                new FileHelper().addNewWordToBoxFile(word, meaning, nextBoxNumber, false);

                wordTextView.setBackground(getResources().getDrawable(R.drawable.know_button_background, null));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) { }

                chosenCardsList.deleteCardFromFile(word, meaning, releasedTimeStr);
                adapter.setWordsMarkAtPosition(position, Mark.KNOWN);
                mark = Mark.KNOWN;
                turnPage(container);
            }
        });
        notKnowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mark != Mark.NOTHING) {
                    return;
                }

                new FileHelper().addNewWordToBoxFile(word, meaning, previousBoxNumber, true);

                wordTextView.setBackground(getResources().getDrawable(R.drawable.notknow_button_background, null));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) { }

                chosenCardsList.deleteCardFromFile(word, meaning, releasedTimeStr);
                adapter.setWordsMarkAtPosition(position, Mark.NOT_KNOWN);
                mark = Mark.NOT_KNOWN;
                turnPage(container);
            }
        });


        meaningTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mark == Mark.DELETED) {
                    return;
                }

                meaningTextView.setText(meaning);
                meaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            }
        });
        deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mark != Mark.NOTHING) {
                    return;
                }

                openDeletingWordDialog();
            }
        });


        return view;
    }



    private void openDeletingWordDialog() {
        DeletingWordDialog deletingWordDialog = new DeletingWordDialog();
        deletingWordDialog.setWordFragment(this);
        deletingWordDialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "deleting word dialog");
    }



    public void applyDeleting() {
        chosenCardsList.deleteCardFromFile(word, meaning, releasedTimeStr);
        adapter.setWordsMarkAtPosition(position, WordFragmentCollectionAdapter.Mark.DELETED);
        mark = WordFragmentCollectionAdapter.Mark.DELETED;

        cardNumberTextView.setText("word number " + ++position + " / " + count + " HAS BEEN DELETED!");
        cardNumberTextView.setTextColor(Color.parseColor("#CF0808"));
        cardNumberTextView.setBackground(getResources().getDrawable(R.drawable.deleted_card_background, null));
        wordTextView.setText("");
        meaningTextView.setText("");
        wordTextView.setBackgroundColor(Color.parseColor("#CFBAFF"));
        deleteButton.setVisibility(View.INVISIBLE);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) { }

        turnPage(container);
    }


    private void turnPage(ViewGroup container) {
        ViewPager pager = ((ViewPager) container);
        pager.setCurrentItem(pager.getCurrentItem() + 1);
        if (pager.getCurrentItem() >= adapter.getCount()) {
            wordTextView.setText("");
            meaningTextView.setText("");

            wordTextView.setVisibility(View.INVISIBLE);
            meaningTextView.setVisibility(View.INVISIBLE);

            cardNumberTextView.setText(getString(R.string.noMoreWordsReadyMessage));
            cardNumberTextView.setBackgroundColor(getResources().getColor(R.color.colorPurpleLight, null));
            cardNumberTextView.setTextColor(getResources().getColor(R.color.colorPurpleDark, null));

            //Toast.makeText(getContext(), "There are no more words ready to learn in this box", Toast.LENGTH_SHORT).show();
        }
    }




}

















