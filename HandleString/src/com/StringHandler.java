package com;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class StringHandler implements Serializable {
    private String StringToUpper;
    private String StringToLower;
    private String StringToUpperLower;
    private int NumberOfWords;
    private  int NumberOfVowel;

    public StringHandler(String StringToUpper, String StringToLower, String StringToUpperLower, int NumberOfWords, int NumberOfVowel) {
        super();
        this.StringToUpper = StringToUpper;
        this.StringToLower = StringToLower;
        this.StringToUpperLower = StringToUpperLower;
        this.NumberOfWords = NumberOfWords;
        this.NumberOfVowel = NumberOfVowel;
    }

    public StringHandler() {
        super();
    }
    //Setters and Getters
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        StringToUpper = (String) ois.readObject();
        StringToLower = (String) ois.readObject();
        StringToUpperLower = (String) ois.readObject();
        NumberOfWords = ois.readInt();
        NumberOfVowel = ois.readInt();
    }

    private void writeObject(ObjectOutputStream oos) throws ClassNotFoundException, IOException {
        oos.writeObject(StringToUpper);
        oos.writeObject(StringToLower);
        oos.writeObject(StringToUpperLower);
        oos.writeInt(NumberOfWords);
        oos.writeInt(NumberOfVowel);
    }
    public String toString() {
        return "Uppercase: "+StringToUpper+"\n"+"LowerCase: "+StringToLower+"\n"+"UpperLower: "+StringToUpperLower+"\n"+
        "NumberOfWords: "+NumberOfWords+"\n"+"NumberOfVowels: "+NumberOfVowel+"\n";
    }
}
