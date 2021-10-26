import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 *
 * @author alexs
 */

public class LanguageStudyGUI extends JFrame implements ActionListener {
    private int width;
    private int height;
    private int num; //keeping track of the time, look at TimerGUI
    private String correctAnswer;
    private String instructions; //string will hold instructions until I change it
    private JLabel instructionsLabel; 
    private JLabel wordLabel;
    private JTextField textField;
    private JButton okButton;
    private Timer questionTimer;
    private Timer afterQuestionTimer;
    private String[] englishWord;
    private String[] germanWord;
    private Random random;
        
    public LanguageStudyGUI() { 
        super();
                
        random = new Random();
        
        this.setVisible(true);

        width = 450; //450
        height = 150; //150

        //Set a default size
        this.setSize(width, height);

        //Set the title of the frame
        setTitle("LanguageGUI");

        //Layout
        setLayout(new GridBagLayout());
        setResizable(false);
        
        //GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        
        //Create a JLabel for the Instructions
        instructions = "Type the translation into the field below.";
        instructionsLabel = new JLabel(instructions);

        //Set the label's position on the grid
        gbc.gridx = 1; //1,0
        gbc.gridy = 0;

        //Add to GUI
        add(instructionsLabel, gbc);//adding the label and it's position constraints

        //OK Button
        okButton = new JButton("OK");
        gbc.gridx = 2; //2,2
        gbc.gridy = 2;
        add(okButton, gbc);
        
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkAnswer(); //call the checkAnswer method to check if our answer is correct when the ok button is clicked
            }
        });
        
        //Text field
        textField = new JTextField(15); //num is param for length of textfield
        gbc.gridx = 1; //1,2
        gbc.gridy = 2;
        
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkAnswer(); //call the checkAnswer method to check if our answer is correct when the enter key is pressed
            }
        });
        add(textField, gbc);
        
        //arraylist is = to engWord and germWord. engWord = new ArrayList
        englishWord = new String[25]; 
        germanWord = new String[25];
        
        //try catch to scan thru English file and German
        try{
            Scanner engScan = new Scanner(new File("english.txt"));
            for (int i = 0; i < englishWord.length; i++) {
                //at each index do scan.nextLine()
                englishWord[i] = engScan.nextLine();
            }
        } catch (FileNotFoundException e) {         
            e.printStackTrace();
        }
        
        try{
            Scanner gerScan = new Scanner(new File("german.txt"));
            for (int i = 0; i < germanWord.length; i++) {
                //at each index do scan.nextLine()
                germanWord[i] = gerScan.nextLine();
            }
        } catch (FileNotFoundException e) {         
            e.printStackTrace();
        }    
        
        //Create a JLabel for the word
        gbc.gridx = 0;
        gbc.gridy = 2;
        //set label = to word
        wordLabel = new JLabel(""); //calling wordChoice method below
        add(wordLabel, gbc);//adding the label and it's position constraints
        
        questionTimer = new Timer(5000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkAnswer(); //call the checkAnswer method to check if our answer is correct if the user hasn't entered a guess after the 5 seconds have elapsed
            }
        });
     
        afterQuestionTimer = new Timer(3000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset(); //call the reset method after the 3 seconds between the answer and the next word (and 5 second timer restart)
            }
        }); 
        reset(); //setting up for the initial display of the screen. Starting the word timer at 5 seconds. 
    }
    
    private void reset(){
        wordLabel.setText(wordChoice()); //puts the next word in
        textField.setEditable(true);
        textField.setText(""); //setting the textfield to be empty
        instructionsLabel.setText(instructions);
        afterQuestionTimer.stop();
        questionTimer.start(); 
    }
    
    public void setResultLabel(String userAnswer, String matchingWord){
        //check language correct ans is in (if word displayed is Germ, find Eng word at same index) 
        if(userAnswer.compareToIgnoreCase(matchingWord) != 0){ //then the ans is incorrect
            System.out.println(("Incorrect! Answer: " + matchingWord));
            instructionsLabel.setText("Incorrect! Answer: " + matchingWord);
        }
        else{
            System.out.println("Correct!");
            instructionsLabel.setText("Correct!");
        }
    }
    
    public String wordChoice(){ 
        //Decide which lang and what word I wanna use
        int languageChoice = random.nextInt(2); //rng to decide b/t Eng or Ger
        int wordChoice = random.nextInt(25); //rng to decide which word in the textfile
        String word;
        
        if (languageChoice == 0) {
            //do german words
            word = germanWord[wordChoice];
        } else {
            //do english words
            word = englishWord[wordChoice];
        }
        return word;
    }
    
    private void checkAnswer(){
    //check if the word is correct or incorrect by clicking ok button or by pressing enter
        //get the answer and whatever I'm comparing it to
        String userAnswer = textField.getText();
        String labelText = wordLabel.getText();
        String matchingWord;
        int correctAnsIndex;

        if (Arrays.asList(englishWord).contains(labelText)){ //english array contains correct ans, that means its eng word, which means user ans should be German 
            //check the index of Eng word 
            correctAnsIndex = Arrays.asList(englishWord).indexOf(labelText);
            matchingWord = germanWord[correctAnsIndex];

            //check language correct ans is in (if word displayed is Eng, find Germ word at same index) 
            setResultLabel(userAnswer, matchingWord);
        }

        //repeat for German words
        if (Arrays.asList(germanWord).contains(labelText)){ //english array contains correct ans, that means its eng word, which means user ans should be German 
            //check the index of Eng word 
            correctAnsIndex = Arrays.asList(germanWord).indexOf(labelText);
            matchingWord = englishWord[correctAnsIndex];

            //check language correct ans is in (if word displayed is Germ, find Eng word at same index) 
            setResultLabel(userAnswer, matchingWord);
        }
        questionTimer.stop();
        textField.setEditable(false);
        afterQuestionTimer.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}   
