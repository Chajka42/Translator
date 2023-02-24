package org.chajka;

import io.swagger.client.ApiException;

import io.swagger.client.api.DefaultApi;

import javax.swing.*;

import static org.chajka.TranslationWindow.*;

public class Main {

    public static void main(String[] args) throws ApiException {

        JFrame frame = new JFrame("Translator");
        TranslationWindow screenSaver = new TranslationWindow();
        frame.add(screenSaver);
        frame.setSize(NUM_COLUMNS * COLUMN_WIDTH, NUM_ROWS * ROW_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        new Thread(screenSaver).start();

    //   DefaultApi apiInstance = new DefaultApi();

//        String q = "Example of smith"; // String |
//        String tl = "ru"; // String |
//        String client = "Enter your example"; // String |
//        String sl = "en"; // String |
//        try {
//            String result = apiInstance.translateATGet(q, tl, client, sl);
//            System.out.println(result);
//
//        } catch (ApiException e) {
//            System.err.println("Exception when calling DefaultApi#translateATGet");
//            e.printStackTrace();
//        }



    }
}

