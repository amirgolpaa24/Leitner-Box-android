package com.mobdev.leitnerbox;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class LeitnerCardList implements Serializable {

    public class LeitnerCard implements Serializable {

        private String word;
        private String meaning;
        private long releaseTime;
        public LeitnerCard(String word, String meaning, long releaseTime) {
            this.word = word;
            this.meaning = meaning;
            this.releaseTime = releaseTime;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }
        public String getMeaning() {
            return meaning;
        }
        public void setMeaning(String meaning) {
            this.meaning = meaning;
        }
        public long getReleaseTime() {
            return releaseTime;
        }
        public void setReleaseTime(long releaseTime) {
            this.releaseTime = releaseTime;
        }

    }
    private ArrayList<LeitnerCard> cards;
    private int count;
    private int boxNumber;




    public LeitnerCardList(int boxNumber) {
        this.boxNumber = boxNumber;
        this.cards = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(FileHelper.getBoxFile(boxNumber));
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String s = sb.toString();
        int st = 0, en = 0, i, j;
        String cardString, word, meaning;
        long releaseTime;
        while (s.length() > 4) {
            en = s.indexOf("}");
            cardString = s.substring(st, en);
            i = cardString.indexOf(":");
            j = cardString.lastIndexOf(":\n");
            releaseTime = Long.parseLong(cardString.substring(1, i));
            word = cardString.substring(i + 1, j);
            meaning = cardString.substring(j + 2);

            if(releaseTime <= ZonedDateTime.now().toInstant().toEpochMilli()) {
                this.cards.add(new LeitnerCard(word, meaning, releaseTime));
            }
            count++;
            if(en + 2 < s.length())
                s = s.substring(en + 2);
            else
                break;
        }


    }



    public LeitnerCardList(int boxNumber, boolean LimitReleasedTime) {
        this.boxNumber = boxNumber;
        this.cards = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(FileHelper.getBoxFile(boxNumber));
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String s = sb.toString();
        int st = 0, en = 0, i, j;
        String cardString, word, meaning;
        long releaseTime;
        while (s.length() > 4) {
            en = s.indexOf("}");
            cardString = s.substring(st, en);
            i = cardString.indexOf(":");
            j = cardString.lastIndexOf(":\n");

            releaseTime = Long.parseLong(cardString.substring(1, i));
            if(!LimitReleasedTime)
                releaseTime = 0;

            word = cardString.substring(i + 1, j);
            meaning = cardString.substring(j + 2);

            if(releaseTime <= ZonedDateTime.now().toInstant().toEpochMilli())
                this.cards.add(new LeitnerCard(word, meaning, releaseTime));

            count++;
            if(en + 2 < s.length())
                s = s.substring(en + 2);
            else
                break;
        }


    }



    private int getIndexOf(String word, String meaning, String releasedTimeStr) {
        long releasedTime = Long.parseLong(releasedTimeStr);
        for (int i = 0; i < cards.size(); i++) {
            LeitnerCard card = cards.get(i);
            if (card.word.equals(word) && card.meaning.equals(meaning) && card.releaseTime == releasedTime) {
                return i;
            }
        }
        return -1;
    }

    public void deleteCardFromFile(String word, String meaning, String releasedTimeStr) {
        String itemStr = "{" + releasedTimeStr + ":" + word + ":\n" + meaning + "}\n";

        StringBuilder sb = new StringBuilder();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(FileHelper.getBoxFile(boxNumber));
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String allFileStr = sb.toString();

        //Log.i("--------------------------------------------------tag1", allFileStr);
        allFileStr = allFileStr.replace(itemStr, "");
        //Log.i("--------------------------------------------------tag2", allFileStr);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(FileHelper.getBoxFile(boxNumber));
            fileOutputStream.write(allFileStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public LeitnerCard getCardAtPosition(int position) {
        return cards.get(position);
    }

    public int getReadyCardNum() {
        return cards.size();
    }

    public int getCount() {
        return this.count;
    }



}
















