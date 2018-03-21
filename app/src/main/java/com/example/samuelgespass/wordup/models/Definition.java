package com.example.samuelgespass.wordup.models;

import org.parceler.Parcel;

@Parcel
public class Definition {
    String word;
    String partOfSpeech;
    String attributionText;
    String definitionText;

    public Definition() {
    }

    public Definition(String word, String partOfSpeech, String attributionText, String definitionText) {
        this.word = word;
        this.partOfSpeech = partOfSpeech;
        this.attributionText = attributionText;
        this.definitionText = definitionText;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }

    public String getDefinitionText() {
        return definitionText;
    }

    public void setDefinitionText(String definitionText) {
        this.definitionText = definitionText;
    }
}
