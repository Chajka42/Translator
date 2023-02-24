package org.chajka;

import io.swagger.client.ApiException;
import io.swagger.client.api.DefaultApi;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;

public class TranslationWindow extends JPanel implements Runnable {

    private static final int FONT_SIZE = 24;
    public static final int NUM_COLUMNS = 64;
    public static final int NUM_ROWS = 24;
    public static final int COLUMN_WIDTH = 20;
    public static final int ROW_HEIGHT = 30;
    private static final int DELAY_MS = 10;

    private static final Random RANDOM = new Random();
    private static final char[] CHARACTERS = "\u0020\u0020\u0020\u30a1\u30a3\u30a5\u30a7\u30a9\u30ab\u30ad\u30af\u30b1\u30b3\u30b5\u30b7\u30b9\u30bb\u30bd\u30bf\u30c1\u30c4\u30c6\u30c8\u30ca\u30cb\u30cc\u30cd\u30ce\u30cf\u30d2\u30d5\u30d8\u30db\u30de\u30df\u30e0\u30e1\u30e2\u30e4\u30e6\u30e8\u30e9\u30ea\u30eb\u30ec\u30ed\u30ef\u30f2\u30f3\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020".toCharArray();

    private final char[][] matrix = new char[NUM_ROWS][NUM_COLUMNS];

    private final JComboBox<String> inComboBox;
    private final JComboBox<String> outComboBox;
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;

    public TranslationWindow() {

        setLayout(null); // set layout to null to manually position components

        JLabel captionLabel = new JLabel("> Translator >");
        captionLabel.setBounds(0, 20, NUM_COLUMNS * COLUMN_WIDTH, ROW_HEIGHT);
        captionLabel.setHorizontalAlignment(JLabel.CENTER);
        captionLabel.setForeground(Color.WHITE);
        captionLabel.setFont(new Font("Arial", Font.BOLD, 45));
        add(captionLabel);

        String[] languageCodes = {"en", "es", "fr", "de", "ja", "ko", "pt", "zh-CN", "zh-TW", "ru", "it", "nl", "pl", "sv", "da", "fi", "el", "hi", "id", "ms", "no", "sk", "sl", "tr", "uk", "vi", "af", "sw", "fil", "he"};
        String[] languageNames = {"English", "Spanish", "French", "German", "Japanese", "Korean", "Portuguese", "Chinese (Simplified)", "Chinese (Traditional)", "Russian", "Italian", "Dutch", "Polish", "Swedish", "Danish", "Finnish", "Greek", "Hindi", "Indonesian", "Malay", "Norwegian", "Slovak", "Slovenian", "Turkish", "Ukrainian", "Vietnamese", "Afrikaans", "Swahili", "Filipino", "Hebrew"};

        inComboBox = new JComboBox<>(languageNames);
        inComboBox.setBounds(350, 20,120, 40);
        inComboBox.setSelectedIndex(0);
        inComboBox.setBackground(Color.DARK_GRAY);
        inComboBox.setForeground(Color.WHITE);
        add(inComboBox);

        outComboBox = new JComboBox<>(languageNames);
        outComboBox.setBounds(810, 20,120, 40);
        outComboBox.setSelectedIndex(9);
        outComboBox.setBackground(Color.DARK_GRAY);
        outComboBox.setForeground(Color.WHITE);
        add(outComboBox);

        JButton startButton = new JButton("Go!");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setBounds(NUM_COLUMNS * COLUMN_WIDTH / 2 - 50, 6 * ROW_HEIGHT, 75, 75);
        startButton.setForeground(Color.WHITE); // set text color
        startButton.setBackground(Color.DARK_GRAY); // set background color
        startButton.addActionListener(e -> {
            // restart the thread with the new font and font size
            String inputText = inputTextArea.getText();
            // Implement the translation logic here
            String outputText = new StringBuilder(inputText).reverse().toString();


            Runnable translationTask = new Runnable() {
                @Override
                public void run() {
                    DefaultApi apiInstance = new DefaultApi();
                    String q = inputTextArea.getText(); // String |
                    String tl = languageCodes[outComboBox.getSelectedIndex()]; // String |
                    String client = "Enter your example"; // String |
                    String sl = languageCodes[inComboBox.getSelectedIndex()]; // String |
                    try {
                        String result = apiInstance.translateATGet(q, tl, client, sl);
                        System.out.println(result);
                        outputTextArea.setText(result.substring(2, result.length() - 2));
                    } catch (ApiException exception) {
                        System.err.println("Exception when calling DefaultApi#translateATGet");
                        exception.printStackTrace();
                    }
                }
            };

            Thread translationThread = new Thread(translationTask);
            translationThread.start();






        });
        add(startButton);


//        JLabel inputLabel = new JLabel("Enter your text here:");
//        inputLabel.setForeground(Color.WHITE);
//        inputLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
//        inputLabel.setBounds(50, 2 * ROW_HEIGHT - 10, 250 * 2, ROW_HEIGHT);
//        add(inputLabel);
// create input and output text areas
        inputTextArea = new JTextArea();
        inputTextArea.setBackground(Color.DARK_GRAY);
        inputTextArea.setForeground(new Color(255, 255, 255));
        inputTextArea.setFont(new Font("SansSerif", Font.PLAIN, 26));
        inputTextArea.setText("Enter your text to translate ...");
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        inputTextArea.setBounds(50, 3 * ROW_HEIGHT, 250 * 2, 10 * ROW_HEIGHT);
        add(inputTextArea);

        outputTextArea = new JTextArea();
        outputTextArea.setBackground(Color.DARK_GRAY);
        outputTextArea.setForeground(new Color(255, 255, 255));
        outputTextArea.setFont(new Font("SansSerif", Font.PLAIN, 26));
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setBounds(710, 3 * ROW_HEIGHT, 250 * 2, 10 * ROW_HEIGHT);
        add(outputTextArea);
// outputTextArea.setBounds(50, 7 * ROW_HEIGHT, 300, 10 * ROW_HEIGHT);
// outputTextArea.setEditable(false);
// add(outputTextArea);


        Font font = new Font("Monospaced", Font.BOLD, FONT_SIZE);
        setFont(font);
        setForeground(Color.GRAY);
        setBackground(Color.BLACK);

        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++) {
                matrix[i][j] = getRandomChar();
            }
        }
    }

    private char getRandomChar() {
        return CHARACTERS[RANDOM.nextInt(CHARACTERS.length)];
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < NUM_COLUMNS; i++) {
                // Determine whether this column should be shifted
                boolean shiftColumn = RANDOM.nextDouble() < 0.1;




                if (shiftColumn) {
                    // Смещение дождя из катаканы
                    for (int j = NUM_ROWS - 1; j > 0; j--) {
                        matrix[j][i] = matrix[j - 1][i];
                    }

//                    // generate one katakana symbol 70% and space 30%
//                    Random rand = new Random();
//                    int probability = rand.nextInt(100); // generate a random integer between 0 and 99
//                    if (probability < 30) {
//                        matrix[0][i] = '\u0020';
//                    } else {
//                        matrix[0][i] = getRandomChar();
//                    }

                    matrix[0][i] = getRandomChar();

                }
            }

            repaint();

            try {
                Thread.sleep(DELAY_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++) {
                g.drawString(Character.toString(matrix[i][j]), j * COLUMN_WIDTH, (i + 1) * ROW_HEIGHT);
            }
        }
    }

}




