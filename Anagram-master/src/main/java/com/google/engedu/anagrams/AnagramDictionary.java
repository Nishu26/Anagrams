/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.HashSet;
import java.util.Arrays;
import android.util.Log;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private int wordLength = DEFAULT_WORD_LENGTH;
    private Random random = new Random();
    private HashSet<String> wordSet;
    private ArrayList<String> wordList;
    private HashMap<Integer, ArrayList<String>> sizeToWords;
    private HashMap<String, ArrayList<String>> lettersToWord;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        wordSet = new HashSet<>();
        wordList = new ArrayList<>();
        lettersToWord = new HashMap<>();
        sizeToWords = new HashMap<>();
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);
            //ArrayList<String> arraylist = new ArrayList<>();
            String key = alphabeticalOrder(word);

            if(lettersToWord.containsKey(key))
            {
                ArrayList<String> arrayList = lettersToWord.get(key);
                arrayList.add(word);
                lettersToWord.put(key,arrayList);
                //lettersToWord.get(key).add(word);


                //anagrams.add(word);

                // Map anagrams back to sortedWord
                //lettersToWord.put(sortedWord, anagrams);
            }
            else
            {
                ArrayList<String> arrayList = new ArrayList<String>();

                // Add current word
                // anagrams.add(word);

                // Map anagrams to sortedWord
                //lettersToWord.put(sortedWord, anagrams);
                arrayList.add(word);
                lettersToWord.put(key,arrayList);
                //sizeToWords.get(l).add(word);
            }
            int l = word.length();
            if(sizeToWords.containsKey(l))
            {
                ArrayList<String> words = sizeToWords.get(l);
                words.add(word);
                sizeToWords.put(l, words);
            }
            else
            {
                ArrayList<String> words = new ArrayList<String>();
                words.add(word);
                sizeToWords.put(l, words);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {

        boolean check = true;
        if(wordSet.contains(word) && !word.contains(base)) {
            return true;
        }
        else
        {
            return false;
        }

    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        targetWord = alphabeticalOrder(targetWord);
                for (int i = 0; i < wordList.size(); i++) {
                    String word = wordList.get(i);
                    if (word.length() == targetWord.length()) {
                        String s = alphabeticalOrder(word);
                        if (targetWord.equals(s)) {
                            result.add(word);
                        }
                    }
                }
                return result;
            }


    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
       // ArrayList<String> resultant;
        ArrayList<String> result = new ArrayList<String>();
        //String k;
        for (char i = 'a'; i <= 'z'; i++) {
            String newWord = word + i;
            String snw = alphabeticalOrder(newWord);

            String b = "false";
            if (lettersToWord.containsKey(snw)) {
                b = "true";
            }
            ArrayList<String> arraylist;
            if (lettersToWord.containsKey(snw)) {
                arraylist = lettersToWord.get(snw);
            } else {
                arraylist = new ArrayList<String>();
            }
            for (int x = 0; x < arraylist.size(); x++) {
                String wordToAdd = arraylist.get(x);
                if (isGoodWord(wordToAdd, word)) {
                    result.add(wordToAdd);
                }
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {

        ArrayList<String> t = sizeToWords.get(wordLength);

        if(wordLength < MAX_WORD_LENGTH) wordLength++;

        while (true) {
            String w = t.get(random.nextInt(t.size()));
            if(getAnagramsWithOneMoreLetter(w).size() >= MIN_NUM_ANAGRAMS) return w;
        }

    }

    public String alphabeticalOrder(String word){
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
