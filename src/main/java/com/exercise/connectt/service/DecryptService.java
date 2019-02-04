package com.exercise.connectt.service;

import com.exercise.connectt.pojo.Body;
import com.exercise.connectt.pojo.Crypt;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class DecryptService {

    private final List<Body> bookPages;
    private final Map<Integer, Crypt> encryptation;
    private static final String[] ALPHABET = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n",
            "o","p","q","r","s","t","u","v","w","x","y","z"};

    public DecryptService(List<Body> bookPages, Map<Integer, Crypt> encryptations) {
        this.bookPages = Objects.requireNonNull(bookPages);
        this.encryptation = Objects.requireNonNull(encryptations);
    }

    public List<Body> decryptBook() {
        return bookPages.stream()
                .map(body -> decryptBody(body))
                .collect(toList());
    }

    private Body decryptBody(Body body) {
        int id = body.getId();
        int rotation = findRotation(id);

        if(rotation == -1) {
            return body;
        } else {
            int rotate = 26 - findRotation(id);
            return new Body(body.getId(), decryptText(body.getData(), rotate));
        }
    }

    private Integer findRotation(int id){
        Optional<Crypt> foundCrypt = encryptation.values().stream()
                .filter(value -> {
                    return value.getStart() <= id && value.getStart()+value.getLength() >= id;
                })
                .findFirst();

        if(!foundCrypt.isPresent()) {
            return -1;
        } else {
            return foundCrypt.get().getRotate();
        }
    }

    private String decryptText(String inputText, int rotation) {
        StringBuilder decryptedText = new StringBuilder();
        char[] inputTextArray = inputText.toCharArray();

        for(Character character: inputTextArray){
            decryptedText.append(decryptCharacter(character, rotation));
        }

        return decryptedText.toString();
    }

    private Character decryptCharacter(Character character, int rotation) {
        if (!Character.isAlphabetic(character)) {
            return character;
        }

        Integer rotatedCharacterPosition;
        Character rotatedCharacter = character;

        for (int i = 0; i < 26 && rotatedCharacter.equals(character); i++) {
            if(character.toString().equalsIgnoreCase(ALPHABET[i])) {
                rotatedCharacterPosition = (i+rotation)%26;
                String decryptedCharacterAsString = ALPHABET[rotatedCharacterPosition];

                rotatedCharacter = decryptedCharacterAsString.charAt(0);

                if(!character.equals(ALPHABET[i].charAt(0))){
                    rotatedCharacter = decryptedCharacterAsString.toUpperCase().charAt(0);
                }
            }
        }

        return rotatedCharacter;
    }
}