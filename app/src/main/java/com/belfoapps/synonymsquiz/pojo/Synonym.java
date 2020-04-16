package com.belfoapps.synonymsquiz.pojo;

import java.util.Objects;

public class Synonym {
    private String word;
    private String synonym;

    public Synonym() {
    }

    public Synonym(String word, String synonym) {
        this.word = word;
        this.synonym = synonym;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Synonym synonym1 = (Synonym) o;
        return Objects.equals(word, synonym1.word) &&
                Objects.equals(synonym, synonym1.synonym);
    }
}
