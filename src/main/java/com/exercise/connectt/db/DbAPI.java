package com.exercise.connectt.db;

import com.exercise.connectt.pojo.Body;
import com.exercise.connectt.pojo.Crypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbAPI {
    private Connection connection = null;
    private static final String database = "puzzle.db";
    private static final String urlSQLite = "jdbc:sqlite:C:\\workspace\\companies\\connectt\\resources\\"+database;

    private static final String BODY_ID = "id";
    private static final String BODY_DATA = "data";
    private static final String BODY_TABLE_NAME = "b0dy";

    private static final String CRYPT_START = "start";
    private static final String CRYPT_LENGTH = "length";
    private static final String CRYPT_ROTATE = "rotate";
    private static final String CRYPT_TABLE_NAME = "crypt";

    private static final String SELECT_SENTENCE = "SELECT * FROM ";


    public void connect(){
        try {
            connection = DriverManager.getConnection(urlSQLite);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void disconnect(){
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Body> getEncryptedBooks(){
        final List<Body> bookPages = new ArrayList<>();

        try {
            final Statement statement = connection.createStatement();
            StringBuilder query = new StringBuilder(SELECT_SENTENCE + BODY_TABLE_NAME);

            final ResultSet rs = statement.executeQuery(query.toString());
            while (rs.next()){
                bookPages.add(new Body(rs.getInt(BODY_ID), rs.getString(BODY_DATA)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookPages;
    }

    public Map<Integer, Crypt> getEncryptations(){
        final Map<Integer, Crypt> encryptations = new HashMap<>();

        try {
            final Statement statement = connection.createStatement();

            final ResultSet rs = statement.executeQuery(SELECT_SENTENCE + CRYPT_TABLE_NAME);
            while (rs.next()){
                final int id = rs.getInt(CRYPT_START);
                final Crypt crypt = new Crypt(rs.getInt(CRYPT_START), rs.getInt(CRYPT_LENGTH),
                        rs.getInt(CRYPT_ROTATE));

                encryptations.put(id, crypt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return encryptations;
    }
}
