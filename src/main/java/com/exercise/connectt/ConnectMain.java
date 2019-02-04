package com.exercise.connectt;

import com.exercise.connectt.db.DbAPI;
import com.exercise.connectt.pojo.Body;
import com.exercise.connectt.pojo.Crypt;
import com.exercise.connectt.service.DecryptService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConnectMain {
    public static void main(String[] args) {
        DbAPI dbAPI = new DbAPI();

        dbAPI.connect();

        final List<Body> bookPages = dbAPI.getEncryptedBooks();
        final Map<Integer, Crypt> encryptations = dbAPI.getEncryptations();

        DecryptService decryptService = new DecryptService(bookPages, encryptations);

        List<Body> decrypt = decryptService.decryptBook();

        String decryptedBook = decrypt.stream()
                .map(Body::getData)
                .collect(Collectors.joining());

        System.out.println("Decripted Book: "+decryptedBook);

        dbAPI.disconnect();
    }
}
