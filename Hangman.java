//December 14th, 2019
//Aava Sapkota
//Ms.Krasteva
//Hangman ISP
/*
Program Description: Welcome to Hangmam, Holiday Edition! This program is a fun single player word guessing game. When
the program begins, users will see a short animation starting the game and displaying the title. From the splash screen
animation, users are then led to a main menu with 5 options: instructions, highscores, level 1, level 2, and exit. Instructions
explains the game and it's hidden components; highscores displays the top 10 highscores achieved, also allowing the user
to clear the scores); level 1 plays level 1, and level 2 allows the user to play the game; and exit, closes the program.
After each game, depending on wheter the player wins or loses, a screen will pop up. If it is a win, the screen will ask the user
for their name, and display their score. Then the player can continue to the next level, or return to main menu. The first level
has a hint, and the second does not, however, there is a cheat method. There is a secret timer that is used to calculate score.
---------------------------------------------------------------------------------------------------------------------------------
Global Variable Dictionary:
Name                |    Type    | Description
---------------------------------------------------------------------------------------------------------------------------------
c                   |   Console  | The reference variable for the Console class
----------------------------------------------------------------------------
Control Flow
----------------------------------------------------------------------------
choice              | String     | main menu user input variable
win                 | boolean    | Determines if user has won a level or lost
screen                      | String     | Records the screen that the user is on (Level 1 or Level 2). Used for Screen processing
option              | String     | Used in the Win or lose screen to determine further control flow
-------------------------------------------------------------------------------
Game Processing
--------------------------------------------------------------------------------
letter                      | char       | records the user's letter guesses in the the game
hangManBodyParts1   | String [][]| holds the hangman body parts for level 1
hangManBodyParts2   | String [][]| holds the hangmand body parts for level 2
wordSet             | String[]   | holds the row associated with each level including the display format and hint if in level 1
word                | word       | the word the user has to guess in each level
reset               | boolean    | Determines the variable rest in level 2, if user decides to use the cheat fuction
hangmanCounter      | int        | counts the number of times a user enters the wrong character. Is used to help display the hangman body parts in order
deadWords           | String[]   | used to hold all the words alread used in the game, to avoid repetition
displayWord         | String[]   | used to hold the character displayed on the screen when they are guessed, or to display blanks
counter             | int        | counts number of times a letter is guessed correctly
userWord            | String     | holds the user's guessed word
hint                | String     | holds the mystery word's hint in level 1
deadLetters         | String     | records letters already used
guess                           | int        | number of incorrect guesses
---------------------------------------------------------------------------------------
Scoring and user info:
---------------------------------------------------------------------------------------
userName            | String     | Records the user's username that is associated with the score
startTime1          | long       | records the starting time for level 1
endTime1            | long       | records the end time for level 1
startTime2          | long       | records the starting time for level 2
endTime1            | long       | records the starting time for level 2
score               | long       | records the user's score for each level
highscores          | String [][]| holds the top 10 highscores in the game
scores              | long[]     | stores the scores achieved durring the game. Used to sort the highscores
-------------------------------------------------------------------------------------------
Colors and Fonts
-------------------------------------------------------------------------------------------
header              | Color      | holds the color values for the headers in the program
textBackground      | Color      | holds the color values for the text backgrounds in the program
background          | Color      | holds the color values for the backgrounds in the program
title               | Font       | holds the font for the titles in the program
options             | Font       | holds the font for the options for control flow
userOption          | Font       | holds the font for the user options for control flow
scoreF              | Font       | holds the font for the score display

*/

import hsa.Console;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.*;

public class Hangman
{

    Console c;

    // Screen control flow
    String choice = "";
    boolean win = false;
    String screen;
    String option = "";

    // Level processing and other components
    char letter;
    String[] [] hangManBodyParts1 = {{"head.png", "-80", "-60"}, {"body.png", "-80", "-60"},
	    {"sholders.png", "-80", "-60"}, {"left arm.png", "-80", "-60"}, {"right arm.png", "-80", "-60"},
	    {"left leg.png", "-80", "-100"}, {"right leg.png", "-80", "-100"}, {"scarf LVL1.png", "-80", "-60"}};
    String[] [] hangManBodyParts2 = {{"head.png", "-80", "-60"}, {"body.png", "-80", "-60"},
	    {"sholders.png", "-80", "-60"}, {"left arm.png", "-80", "-60"}, {"right arm.png", "-80", "-60"},
	    {"left leg.png", "-80", "-100"}, {"right leg.png", "-80", "-100"}, {"Hat LVL2.png", "-80", "-60"}};
    String[] wordSet;
    String word;
    boolean reset = true;
    int hangmanCounter = 0;
    int[] deadWords = new int [50];
    String[] displayWord;
    int counter = 0;
    String userWord = "";
    String hint;
    String deadLetters;
    int guess;
    int numOfAlphaValues = 0;

    // Scoring
    String userName = "";
    long startTime1 = 0;
    long startTime2 = 0;
    long endTime1 = 0;
    long endTime2 = 0;
    long score = 0;
    String[] [] highscores = new String [11] [3];
    long[] scores = new long [11];

    // Colors:
    Color header = new Color (182, 215, 168);
    Color textBackground = new Color (202, 249, 174);
    Color background = new Color (109, 158, 235);

    // Fonts
    Font title = new Font ("Consolas", Font.PLAIN, 50);
    Font options = new Font ("Comic Sans MS", Font.PLAIN, 10);
    Font userOption = new Font ("Consolas", Font.PLAIN, 16);
    Font scoreF = new Font ("Consolas", Font.PLAIN, 20);

    /*
     * Hangman Constructor: This is the Console constructor method. It creates the
     * Console.
     */
    public Hangman ()
    {
	c = new Console (27, 90, "Holiday Hangman Game");
    }


    /*
     * splashScreen Method: The splashScreen method calls in the splashscreen
     * animation in the SplashScreen class
     */
    public void splashScreen ()
    {
	SplashScreen a = new SplashScreen (c);
	a.run ();

    }


    /*
     * menu Method: This method is the main menu method. It is the first screen the
     * user see where they have to input an option to control the control flow.
     *
     * Structures:
     * while        - runs through user input until correct user input is given
     * try catch    - catches any errors that can crash the program and instead returns
     *                a message if incorrect input is entered. Used to catch
     *                IllegalArgumentExceptions, and IO exceptions when importing images.
     * if           - checks if the input is acceptable (one of the choices displayed)
     *
     */
    public void menu ()
    {
	c.clear ();
	option = "";
	startTime1 = 0;
	endTime1 = 0;
	startTime2 = 0;
	endTime2 = 0;
	userName = "";

	while (true)
	{
	    try
	    {

		// Menu Image and opitons
		try
		{ // imports image
		    BufferedImage menuBackground = ImageIO.read (new File ("Screen 2_ Menu.png"));
		    c.drawImage (menuBackground, -10, -10, null);
		}
		catch (IOException e)
		{
		}
		c.setFont (userOption);
		c.setColor (Color.black);
		c.drawString ("Enter your choice: ", 250, 515);
		c.setCursor (26, 54);
		c.print ("\t\t");
		c.setCursor (26, 54);
		choice = c.readLine ();
		if (!choice.equals ("a") && !choice.equals ("b") && !choice.equals ("c") && !choice.equals ("d")  // check if
			// input
			// is
			// one
			// of
			// the
			// options
			&& !choice.equals ("e") && !choice.equals ("A") && !choice.equals ("B") && !choice.equals ("C")
			&& !choice.equals ("D") && !choice.equals ("E"))
		{
		    throw new IllegalArgumentException ();
		}
		break;

	    }
	    catch (IllegalArgumentException e)
	    {
		JOptionPane.showMessageDialog (null, "Invalid Entry! Enter a valid choice.", "Error",
			JOptionPane.ERROR_MESSAGE); // Error Trap

	    }
	}

    }


    /*
     * instructions Method: This program displays the instructions for how to play
     * the game.
     *
     * Structures:
     * try catch - catches any errors that can crash the program and
     *             instead returns a message if incorrect input is entered.
     *             Used to catch IOexceptions when importing images.
     *
     */
    public void instructions ()
    {
	c.clear ();
	try
	{
	    BufferedImage Instructions = ImageIO.read (new File ("Screen 3_ Instructions.png"));
	    c.drawImage (Instructions, -10, -10, null);
	}
	catch (IOException e)
	{
	}
	c.getChar ();
    }


    /*
     * highscores Method: This method displays the highscores of the games, and
     * allows the user to clear the highscores.
     *
     * Structures:
     * while          - runs through user input until correct user input is
     *                  given, in this case, the option to clear the board (c)
     *                  or to leave the screen(l);
     * try catch      - catches any errors that can crash the program and instead
     *                  returns a message if incorrect input is entered. Used to catch
     *                  IllegalArgumentExceptions, and IO exceptions when improting images.
     * if             - checks if the input is acceptable (one of the choices displayed) for - is
     *                  used to display the highscores in a table
     *
     * Variables:
     * Name:         | Type:   | Description
     * ----------------------------------------------------------
     * a             | char    | user input to determine control flow
     * i             | int     | for loop varible to run through highscores array and store it
     *
     */
    public void highscores ()
    {
	c.clear ();
	try
	{
	    BufferedImage Highscores = ImageIO.read (new File ("Screen 4_ HighScores.png"));
	    c.drawImage (Highscores, -10, -10, null);
	}
	catch (IOException e)
	{
	}

	try
	{
	    BufferedReader r = new BufferedReader (new FileReader ("Highscores.txt"));
	    for (int i = 0 ; i < 10 ; i++)
	    {
		String[] holder = r.readLine ().split ("-");
		c.drawString (holder [0], 110, 180 + (i * 30));
		c.drawString (holder [1], 330, 180 + (i * 30));
		c.drawString (holder [2], 550, 180 + (i * 30));
	    }
	    r.close ();
	}
	catch (IOException a)
	{
	}

	while (true)
	{
	    try
	    {
		char a = c.getChar ();

		if (a == 'c')
		{ // check to clear
		    for (int i = 0 ; i < 10 ; i++)
		    { // clear highscores, by filling in empty values
			highscores [i] [0] = "0";
			highscores [i] [1] = "empty";
			highscores [i] [2] = "empty";
		    }
		    highscoresProcessing (); // writes to the highscores file
		}
		else if (a != 'l')
		{ // check to leave
		    throw new IllegalArgumentException ();
		}
		break;

	    }
	    catch (IllegalArgumentException a)
	    {
		JOptionPane.showMessageDialog (null, "Invalid Entry! Enter one of the two choices displayed.", "Error",
			JOptionPane.ERROR_MESSAGE); // Error Trap

	    }
	}

    }


    /*
     * hint Method: This method displays the highscores of the games, and allows the
     * user to clear the highscores.
     *
     * Structures:
     * try catch      - catches any errors that can crash the program and
     *                  instead returns a message if an error is returned. Used to catch IO
     *                  exceptions when improting images.
     * if             - checks the number of letters entered into each row, and if it fills
     *                                      a row send the next line in the display.
     * for            - is used to display the highscores in a table
     *
     * Variables:
     *
     * Name: | Type: | Description
     * ----------------------------------------------------------
     * x     | int   | this variable is used to place each letter in the hint in each row
     * y     | int   | this variable is used to control the rows in the hint display
     * i     | int   | for loop varible to run through highscores array and store it
     *
     */
    public void hint ()
    {
	wordSet = wordGenerator (fileToArray ("Level1WordBank.txt")); // set word and hint for level1
	int x = 0;
	int y = 0;
	try
	{ // get image
	    BufferedImage Hint = ImageIO.read (new File ("Hint .png"));
	    c.drawImage (Hint, -10, -10, null);
	}
	catch (IOException e)
	{
	}
	c.setColor (Color.black);
	for (int i = 0 ; i < wordSet [1].length () ; i++)
	{ // run through the hint
	    if (i % 47 == 0)
	    { // count number of characte/rs printed
		y += 20;
		x = 0;

	    }
	    else
	    {
		x = (i % 46) * 10;
	    }
	    c.drawString ((wordSet [1].charAt (i)) + "", 130 + x, 200 + y); // print character

	}

	c.getChar ();
    }


    /*
     * lvl1 Method: This method holds all of the processing and grpahics for Level 1
     * of the hangman game.
     *
     * Structures:
     * while         - is used to run the game until the user loses. If the user
     *                 wins, the loop will break and lead the user to the winner screen.
     * try catch     - catches any errors that can crash the program and instead returns a message
     *                 if an error is returned. Used to catch IO exceptions when improting images,
     *                 and to catch illeglArgumentExceptions.
     * if            - checks if the letter guessed is in the mystery word, and any other conditions
     *                 that result in a win, or loss (if a letter guessed is incorrect, then it
     *                 displays a body part onto the screen).
     * for          - is used to initailize numOfAlphaValues to help determine if
     *                the word has been guessed, to display the correctly guessed letters, and to
     *                go through displayWord and place the correct letters in it's indexes.
     *
     * Variables:
     * Name:       | Type: | Description
     * ----------------------------------------------------------
     * i           | int   | for loop varible to run through highscores array and store it
     * guessingBox | Color | The color of the word guessing box
     *
     *
     */
    public void lvl1 ()
    {
	c.clear ();
	startTime1 = System.currentTimeMillis (); // starts timer
	screen = "Level1"; // sets screen
	letter = ' '; // resets letter value
	counter = 0; // resets counter for number of correct words
	word = wordSet [0].toLowerCase (); // sets word to all lowercase
	guess = 0; // reset guesses
	displayWord = wordSet [2].split (" "); // get the word display
	numOfAlphaValues = 0; // reset number of alpha values in a word
	win = false; // reset win status
	for (int i = 0 ; i < word.length () ; i++)
	{ // go through word and count the number of alphabetic values
	    if (word.charAt (i) != ' ')
	    {
		numOfAlphaValues++;
	    }
	}
	deadLetters = ""; // reset deadLetters
	Color guessingBox = new Color (147, 196, 125);
	background = new Color (159, 197, 232);

	try
	{ // import image
	    BufferedImage Level1 = ImageIO.read (new File ("Screen 5_ Level 1.png"));
	    c.drawImage (Level1, -10, -10, null);
	}
	catch (IOException e)
	{
	}

	while (guess != 8)
	{ // run until number of incorrect guesses is 8
	    try
	    {

		c.setColor (Color.black);
		c.setFont (new Font ("Arial", Font.PLAIN, 17));
		for (int i = 0 ; i < displayWord.length ; i++)
		{ // display words onto the screen
		    c.drawString (displayWord [i] + "  ", 330 + i * 15, 150);
		}

		if (counter == numOfAlphaValues)
		{ // check if all alpha characters entered make up the hidden word
		    win = true;
		    break;
		}

		letter = c.getChar ();
		if (letter == '!')
		{ // guess word
		    c.setColor (background);
		    c.fillRect (383 - 10, 396 - 10, 254, 34);
		    c.setColor (Color.black);
		    c.fillRect (368 - 10, 391, 244 + 25, 34);
		    c.fillRect (348 - 10, 428 + 10, 289 + 20, 34);
		    c.setColor (guessingBox);
		    c.fillRect (370 - 10, 393, 240 + 25, 30);
		    c.fillRect (350 - 10, 430 + 10, 285 + 20, 30);
		    c.setColor (Color.black);
		    c.drawString ("Enter your guess below", 390, 415);
		    c.setCursor (23, 45);
		    c.print ("");
		    userWord = c.readLine ().toLowerCase (); // get userInput
		    c.setColor (background);
		    c.fillRect (368 - 10, 391, 244 + 25, 34);
		    c.setColor (Color.black);
		    c.fillRect (383 - 10, 396 - 10, 254, 34);
		    c.setColor (guessingBox);
		    c.fillRect (385 - 10, 398 - 10, 250, 30);
		    c.setColor (Color.black);
		    c.drawString ("Enter ! to guess the word", 395 - 10, 420 - 10);
		    c.setColor (Color.white);
		    c.fillRect (348 - 10, 428 + 10, 289 + 20, 34);

		}
		else if ((letter < 65 || (letter > 90 && letter < 97) || letter > 122))
		{ // check if input is valid
		    throw new IllegalArgumentException ();
		}
		if (deadLetters.indexOf (letter) >= 0)
		{ // check if letter has alread been entered
		    throw new IllegalArgumentException ();
		}

		c.setColor (background);
		if (letter >= 97 && letter <= 103)
		{ // cover the letter once it has been entered
		    c.fillRect (345 + 44 * (letter - 97), 225, 35, 30);
		}
		else if (letter >= 104 && letter <= 110)
		{
		    c.fillRect (345 + 43 * (letter - 104), 266, 35, 30);
		}
		else if (letter >= 111 && letter <= 117)
		{
		    c.fillRect (345 + 44 * (letter - 111), 310, 35, 30);
		}
		else if (letter >= 118 && letter <= 122)
		{
		    c.fillRect (365 + 44 * (letter - 118), 350, 35, 30);
		}

		if (userWord.equals (word))
		{ // check if word entered is the hidden word
		    win = true;
		    break;
		}

		if (word.indexOf (letter) >= 0)
		{ // check if letter inputed is apart of word
		    for (int i = 0 ; i < word.length () ; i++)
		    { // run through word length to find letter
			if (word.charAt (i) == letter || word.charAt (i) == (letter - 32))
			{ // check if the
			    displayWord [i] = (letter + " ").toUpperCase (); // add letter to display array
			    deadLetters += letter + ""; // add letter added to deadletters
			    counter++; // add to counter
			}
		    }
		}
		else
		{
		    if (letter != '!') // check to see if charcter is '!'
			deadLetters += letter + "";
		    try
		    { // get image of Hangman part
			BufferedImage Hangman = ImageIO.read (new File (hangManBodyParts1 [guess] [0]));
			c.drawImage (Hangman, Integer.parseInt (hangManBodyParts1 [guess] [1]),
				Integer.parseInt (hangManBodyParts1 [guess] [2]), null);

		    }
		    catch (IOException e)
		    {
		    }
		    guess++; // add to incorrect guesses
		}
	    }
	    catch (IllegalArgumentException e)
	    { // catch exceptions
		JOptionPane.showMessageDialog (null, "Invalid Entry! Enter one of the letters displayed on the screen.",
			"Error", JOptionPane.ERROR_MESSAGE); // Error Trap
	    }

	}
	c.setColor (Color.black);
	c.fillRect (290, 450, 230, 40);
	c.setColor (background);
	c.drawString ("Press any letter to continue", 300, 480);
	endTime1 = (System.currentTimeMillis () - startTime1) / 1000; // endTimer
	c.getChar (); // exit game

    }


    /*
     * lvl1 Method: This method holds all of the processing and grpahics for Level 2
     * of the hangman game.
     *
     * Structures:
     * while     - is used to run the game until the user loses. If the user
     *             wins, the loop will break and lead the user to the winner screen.
     *
     * try catch - catches any errors that can crash the program and instead returns a message
     *             if an error is returned. Used to catch IO exceptions when improting images,
     *             and to catch illeglArgumentExceptions.
     *
     * if        - checks if the letter guessed is in the mystery word, and any other
     *             conditions that result in a win, or loss (if a letter guessed is
     *             incorrect, then it displays a body part onto the screen). Also check
     *             for whether or not to restart the game if the cheatfuction is used.
     *
     * for       - is used to initailize numOfAlphaValues to help determine if the word
     *             has been guessed, to display the correctly guessed letters, and to go
     *             through displayWord and place the correct letters in it's indexes. It
     *             is also used to display the correct number of hangman parts.
     *
     * Variables:
     * Name:       | Type: | Description
     * ----------------------------------------------------------
     * i           | int   | for loop varible used throughout the program to run through a specific length
     * guessingBox | Color | The color of the word guessing box
     *
     *
     */

    public void lvl2 ()
    {
	c.clear (); // clears previous screen

	if (reset == true)
	{ // checks if all the level data should be reset
	    counter = 0;
	    startTime2 = System.currentTimeMillis (); // starts timer

	    numOfAlphaValues = 0;
	    screen = "Level2";
	    letter = ' ';
	    wordSet = wordGenerator (fileToArray ("Level2WordBank.txt")); // gets new word, and word display
	    word = wordSet [0].toLowerCase (); // sets word
	    guess = 0;
	    win = false;
	    deadLetters = "";
	    hangmanCounter = 0;
	    displayWord = wordSet [1].split (" "); // sets word display array

	}

	Color guessingBox = new Color (234, 153, 153);
	background = new Color (147, 196, 125);

	for (int i = 0 ; i < word.length () ; i++)
	{ // gets number of alphabetic character from word
	    if (word.charAt (i) != ' ')
	    {
		numOfAlphaValues++;
	    }
	}

	try
	{ // import background image
	    BufferedImage Level2 = ImageIO.read (new File ("Screen 6_ Level 2.png"));
	    c.drawImage (Level2, -10, -10, null);
	}
	catch (IOException e)
	{
	}

	while (guess != 8)
	{ // runs untill 8 wrong guesses are made
	    try
	    {
		c.setColor (Color.black);
		c.setFont (new Font ("Arial", Font.PLAIN, 17));
		for (int i = 0 ; i < displayWord.length ; i++)
		{ // displays word onto screen
		    c.drawString (displayWord [i] + "  ", 330 + i * 15, 150);
		}

		for (int i = 0 ; i < hangmanCounter ; i++)
		{ // displays all the hangman parts that are nessesary
		    try
		    { // imports the images
			BufferedImage Hangman = ImageIO.read (new File (hangManBodyParts2 [i] [0]));
			c.drawImage (Hangman, Integer.parseInt (hangManBodyParts2 [i] [1]),
				Integer.parseInt (hangManBodyParts2 [i] [2]), null);

		    }
		    catch (IOException e)
		    {
		    }
		}

		if (counter == numOfAlphaValues)
		{ // check if word entered is the hidden word
		    win = true;
		    reset = true;
		    break;
		}

		letter = c.getChar (); // get letter from user

		if (letter == '!')
		{ // checks if user wants to guess word
		    c.setColor (background);
		    c.fillRect (383 - 10, 396 - 10, 254, 34);
		    c.setColor (Color.black);
		    c.fillRect (370, 388, 234, 34);
		    c.fillRect (348 - 10, 428, 289 + 10, 30);
		    c.setColor (guessingBox);
		    c.fillRect (372, 392, 230, 28);
		    c.fillRect (350 - 10, 430, 285 + 10, 26);
		    c.setColor (Color.black);
		    c.drawString ("Enter your guess below", 390, 415);
		    c.setCursor (23, 45);
		    c.print ("");
		    userWord = c.readLine ().toLowerCase (); // get user word
		    c.setColor (background);
		    c.fillRect (370, 388, 234, 39);
		    c.fillRect (348 - 10, 428, 289 + 10, 40);
		    c.setColor (Color.black);
		    c.fillRect (383 - 10, 396 - 10, 254, 34);
		    c.setColor (guessingBox);
		    c.fillRect (385 - 10, 398 - 10, 250, 30);
		    c.setColor (Color.black);
		    c.drawString ("Enter ! to guess the word", 395 - 10, 420 - 10);

		}
		else if (letter == '$')
		{ // check if cheat fuction is activated
		    reset = false; // set reset to false, so level data is not reset
		    break;
		}
		else if ((letter < 65 || (letter > 90 && letter < 97) || letter > 122))
		{ // check if input is valid
		    throw new IllegalArgumentException ();
		}

		if (deadLetters.indexOf (letter) >= 0)
		{ // check if letter has already been guessed
		    throw new IllegalArgumentException ();
		}

		c.setColor (background);
		if (letter >= 97 && letter <= 103)
		{ // cover letters which have already been guessed
		    c.fillRect (342 + 43 * (letter - 97), 223, 35, 30);
		}
		else if (letter >= 104 && letter <= 110)
		{
		    c.fillRect (342 + 43 * (letter - 104), 264, 35, 30);
		}
		else if (letter >= 111 && letter <= 117)
		{
		    c.fillRect (342 + 43 * (letter - 111), 305, 35, 30);
		}
		else if (letter >= 118 && letter <= 122)
		{
		    c.fillRect (362 + 42 * (letter - 118), 347, 35, 30);
		}

		if (userWord.equals (word))
		{ // check if word entered is the hidden word
		    win = true;
		    reset = true;
		    break;
		}

		if (word.indexOf (letter) >= 0)
		{ // check if letter inputed is apart of word
		    for (int i = 0 ; i < word.length () ; i++)
		    {
			if (word.charAt (i) == letter || word.charAt (i) == letter - 32)
			{
			    displayWord [i] = (letter + " ").toUpperCase ();
			    deadLetters += letter + "";
			    counter++;
			}
		    }
		}
		else
		{
		    if (letter != '!') // make sure user did not enter ! which means they wanted to guess a word
			deadLetters += letter + "";
		    hangmanCounter++;
		    guess++;
		}

	    }
	    catch (IllegalArgumentException e)
	    {
		JOptionPane.showMessageDialog (null, "Invalid Entry! Enter a valid choice.", "Error",
			JOptionPane.ERROR_MESSAGE); // Error Trap
	    }
	}
	if (reset == true)
	{ // only end time if reset is equal to true
	    endTime2 = (System.currentTimeMillis () - startTime2) / 1000;
	}
	c.setColor (Color.black);
	c.fillRect (290, 450, 230, 40);
	c.setColor (background);
	c.drawString ("Press any letter to continue", 300, 480);
	c.getChar (); // leave level

    }


    /*
     * cheat Method: This method displays the graphics and processing for the cheat method
     *
     * Structures:
     * try catch - catches any errors that can crash the program and instead returns a message
     *             if an error is returned. Used to catch IO exceptions when improting images.
     *
     *
     */

    public void cheat ()
    {
	c.clear ();
	try
	{ //import background image
	    BufferedImage cheat = ImageIO.read (new File ("Cheat.png"));
	    c.drawImage (cheat, -10, -10, null);
	}
	catch (IOException e)
	{
	}
	c.setColor (Color.black);
	c.setFont (new Font ("Calibri", Font.PLAIN, 30));
	c.drawString (word.toUpperCase (), 160, 250); //display word
	c.getChar ();
    }


    /*
     * exit Method: This method diplays the end card and exits the game
     *
     * Structures:
     * try catch - catches any errors that can crash the program and instead returns a message
     *             if an error is returned. Used to catch IO exceptions when improting images.
     *
     */

    public void exit ()
    {
	c.clear ();
	try
	{ //import image
	    BufferedImage exitCard = ImageIO.read (new File ("Screen 7_ Exit.png"));
	    c.drawImage (exitCard, -10, -10, null);
	}
	catch (IOException e)
	{
	}

	c.getChar ();

	c.close (); //close program
    }


    /*
     * private fileToArray return Method: This method takes in a file and converts it into a 1D array
     *
     * Structures:
     * try catch - catches any errors that can crash the program such as IOException for the Buffered Reader
     *
     * for       - initailizes the file array with each line in the file. (Each line is one index in the array)
     *
     * Variables:
     * Name:       | Type:    | Description
     * ----------------------------------------------------------
     * file        | String[] | holds the contents of the file in it's indicies
     * fileName    | String   | Holds the name of the file, the program needs to read from
     * reader      | BufferedReader | the reference variable from the BufferedReader class, used to read the file
     *
     *
     */

    private String[] fileToArray (String fileName)
    {
	String[] file = new String [51]; //make new array to hold all the lines in the file
	try
	{
	    // try opening the file
	    BufferedReader reader = new BufferedReader (new FileReader (fileName)); //open reader and specify file to read from

	    // loop through the file
	    for (int i = 0 ; i < 50 ; i++)
	    {
		file [i] = reader.readLine (); //assign each line to file[i]
	    }
	    reader.close (); // close file

	}
	catch (IOException e)   //catch any file related errors
	{
	}

	return file; //return the file array

    }


    /*
     * private wordGenerator return Method: This method takes an array and returns a random row in the array.
     *
     * Structures:
     * while       - gets a random number, by checking if the index selected has been selected before.
     *
     * Variables:
     * Name:       | Type:          | Description
     * ----------------------------------------------------------
     * index        | int      | the varible holding a random number representing a random row
     * dictionary   | String[] | the array that stores all the lines form a file
     * arry         | String[] | the arry returned from spliting the line row from the dictionary array
     *
     */

    private String[] wordGenerator (String[] dictionary)
    {
	int index = (int) (51 * Math.random ()); //generates random index
	while (deadWords [index] == 1)
	{ //checks if index has been selected before
	    index = (int) (51 * Math.random ()); //generates random index
	}

	deadWords [index] = 1; //assigns the value at deadWords[index]=1, so it is not chosen again

	String arry[] = dictionary [index].split ("-"); //split the random row from dictionary, and put it into another 1D array
	return arry; //return arry
    }


    /*
     * highscoresProcessing return Method: This method writes the highscores array into the highscores file.
     *
     * Structures:
     * try catch - catches any errors that can crash the program, like the IOExecption that the PrintWriter returns
     *
     * if        - checks to see if the last line is being written, so it does not create a null row when being read.
     *
     * for       - runs through the top 10 rows of the highscores array and prints them onto the file
     *
     * Variables:
     * Name:  | Type:          | Description
     * ----------------------------------------------------------
     * writer | PrintWriter    | the reference variable for the PrintWriter class, to write contents onto the files
     * i      | int            | the for loop variable which loops through the top 10 rows of the higscores array
     *
     */
    public void highscoresProcessing ()
    {
	PrintWriter writer;

	try
	{
	    writer = new PrintWriter (new FileWriter ("Highscores.txt"), true);
	    for (int i = 0 ; i < 10 ; i++)
	    {
		writer.print (highscores [i] [0] + "-" + highscores [i] [1] + "-" + highscores [i] [2]); // Store data in file
		if (i != 10)
		{
		    writer.println ();
		}

	    }
	    writer.close ();
	}
	catch (IOException e)
	{
	}
    }


    /*
     * winOrLose Method: This method determines what result screen to output(winner/loser), and holds all
     * of the processing involved (recording highscores, calculating score, getting userName).
     *
     * Structures:
     * while     - is used when getting inputs, to run until the correct input has been given (for userName && options to continue).
     *
     * try catch - catches any errors that can crash the program and instead returns a message
     *             if an error is returned. Used to catch IO exceptions when improting images,
     *             and to catch illeglArgumentExceptions.
     *
     * if        - checks status of win and displays screen accordingly, and is in error
     *                         traps to check if input is valid. It is also in the highscores sorting process
     *                         to find the corresponding index in a sorted long scores array for the user score.
     *
     * for       - used to determine location of the user's score in a sorted array, and also to
     *
     * Variables:
     * Name:       | Type: | Description
     * ----------------------------------------------------------
     * i           | int   | for loop varible used throughout the program to run through a specific length
     *
     *
     */

    public void winOrLose ()
    {
	if (win == true && screen.equals ("Level1"))
	{ //check if win is true, and it is for level 1
	    win = false; //reset win
	    try
	    { //import background image
		BufferedImage winOrLose = ImageIO.read (new File ("WinnerLvl1.png"));
		c.drawImage (winOrLose, -10, -10, null);
	    }
	    catch (IOException e)
	    {
	    }

	    score = (int) 100000 - (endTime1 * 10) - (guess * 20); //calcualte score
	    c.setColor (Color.black);
	    c.setFont (scoreF);
	    c.drawString (score + "", 400, 220); //display score

	    while (true)
	    { //get username
		try
		{
		    c.setCursor (8, 50);
		    c.println ();
		    c.setCursor (8, 50);
		    userName = c.readLine ();
		    if (userName.length () > 12)
		    { //check length of userName
			throw new IllegalArgumentException ();
		    }
		    break;
		}
		catch (IllegalArgumentException a)
		{
		    JOptionPane.showMessageDialog (null, "Your username is too long, try a shorter one.", "Error",
			    JOptionPane.ERROR_MESSAGE); // Error Trap
		}
	    }

	    while (true)
	    { //get control flow options
		try
		{
		    c.setCursor (24, 60);
		    option = c.readLine (); //get option
		    if (!option.equals ("a") && !option.equals ("A") && !option.equals ("b") && !option.equals ("B"))
		    { //check if option is valid
			throw new IllegalArgumentException ();
		    }
		    if (option.equals ("a") || option.equals ("A"))
		    { // check if user wants to go to next level
			choice = "d";
		    }
		    break;
		}
		catch (IllegalArgumentException e)
		{
		    JOptionPane.showMessageDialog (null, "Invalid Entry! Enter a valid choice.", "Error",
			    JOptionPane.ERROR_MESSAGE); // Error Trap
		}
	    }

	    //sort highscores
	    scores [0] = score; //put score in holder index
	    Arrays.sort (scores); //sort array
	    int index = 0;
	    for (int i = 0 ; i < scores.length ; i++)
	    { //find index of score in the new sorted array scores
		if (scores [i] == score)
		{
		    index = 10 - i; //set the index to how far the score is from the back of the array
		    break;
		}
	    }

	    for (int i = 9 ; i > index ; i--)
	    { //move all the highscore values down to make a slot for the new score
		highscores [i + 1] [0] = highscores [i] [0]; // score
		highscores [i + 1] [1] = highscores [i] [1]; // name
		highscores [i + 1] [2] = highscores [i] [2]; // level
	    }

	    //put new elements into the new slot
	    highscores [index] [1] = userName;
	    highscores [index] [0] = score + "";
	    highscores [index] [2] = "Level1";

	    highscoresProcessing (); //print onto the highscores file

	}
	else if (win == true && screen.equals ("Level2"))
	{ //check if user won, and if they completed level 2
	    win = false; //rest win
	    try
	    { //import background image
		BufferedImage winOrLose = ImageIO.read (new File ("WinnerLvl2.png"));
		c.drawImage (winOrLose, -10, -10, null);
	    }
	    catch (IOException e)
	    {
	    }
	    score = (int) 100000 - (endTime2 * 10) - (hangmanCounter * 20); //calculate score
	    c.setColor (Color.black);
	    c.setFont (scoreF);
	    c.drawString (score + "", 400, 220); //display score

	    if (userName.equals (""))
	    { //check if there is no userName
		while (true)
		{ //get userName
		    try
		    {

			c.setCursor (8, 50);
			userName = c.readLine ();
			if (userName.length () > 12)
			{ //check Name
			    c.setCursor (8, 50);
			    c.print ("\t\t\t");
			    throw new IllegalArgumentException ();
			}

			break;
		    }
		    catch (IllegalArgumentException a)
		    {
			JOptionPane.showMessageDialog (null, "Your username is too long, try a shorter one.", "Error",
				JOptionPane.ERROR_MESSAGE); // Error Trap
		    }
		}
	    }
	    else
	    {
		c.setColor (Color.black);
		c.drawString (userName, 400, 160); //display userName
	    }
	    while (true)
	    { //get next control flow option variable value
		try
		{
		    c.setCursor (24, 60);
		    option = c.readLine (); //get option
		    if (!option.equals ("a") && !option.equals ("A") && !option.equals ("b") && !option.equals ("B"))
		    { //check if option is valid
			throw new IllegalArgumentException ();
		    }

		    if (option.equals ("a"))
		    { //check if user wants to play again
			choice = "c";
		    }

		    break;
		}
		catch (IllegalArgumentException e)
		{
		    JOptionPane.showMessageDialog (null, "Invalid Entry! Enter a valid choice.", "Error",
			    JOptionPane.ERROR_MESSAGE); // Error Trap
		}
	    }

	    reset = true; //set reset to true

	    //sort highscores
	    scores [0] = score; //put score in holder index
	    Arrays.sort (scores); //sort array
	    int index = 0;
	    for (int i = 0 ; i < scores.length ; i++)
	    { //find index of score in the new sorted array scores
		if (scores [i] == score)
		{
		    index = 10 - i; //set the index to how far the score is from the back of the array
		    break;
		}
	    }

	    for (int i = 9 ; i > index ; i--)
	    { //move all the highscore values down to make a slot for the new score
		highscores [i + 1] [0] = highscores [i] [0]; // score
		highscores [i + 1] [1] = highscores [i] [1]; // name
		highscores [i + 1] [2] = highscores [i] [2]; // level
	    }

	    //put new elements into the new slot
	    highscores [index] [1] = userName;
	    highscores [index] [0] = score + "";
	    highscores [index] [2] = "Level2";

	    highscoresProcessing (); //print onto the highscores file

	}
	else if (win == false)
	{ //check if user lost
	    try
	    { //import background image
		BufferedImage winOrLose = ImageIO.read (new File ("Loser.png"));
		c.drawImage (winOrLose, -10, -10, null);
	    }
	    catch (IOException e)
	    {
	    }

	    while (true)
	    { //get option
		try
		{
		    c.setCursor (21, 60);
		    option = c.readLine (); //get option
		    if (!option.equals ("a") && !option.equals ("A") && !option.equals ("b") && !option.equals ("B"))
		    { //check if opiton is valid
			throw new IllegalArgumentException ();
		    }

		    break;
		}
		catch (IllegalArgumentException e)
		{
		    JOptionPane.showMessageDialog (null, "Invalid Entry! Enter a valid choice.", "Error",
			    JOptionPane.ERROR_MESSAGE); // Error Trap
		}
	    }
	    reset = true; //set reset to true

	}

    }


    /*
     * Main Method: This is the main method which executes all the code. It controls the control flow
     * througout the program.
     *
     * Structures:
     * while     - is used to continuously run the program until the user decides to quit. Also
     *                 used for cheat, to continuously run level 2, when the cheat function is used.
     *
     * try catch - catches any errors that can crash. Used for the Buffered reader when storing
     *                              the file values in the highscores array.
     *
     * if        - checks the value of choice and option to control overall control flow of program.
     *
     * for       - used to initialize deadWords at the very beginning of the program. Also used to
     *                         put the highscores file content into the highscores array.
     *
     * Variables:
     * Name:       | Type:   | Description
     * ----------------------------------------------------------
     * i           | int     | for loop varible used throughout the program to run through a specific length
     * x           | int     | for loop varible used throughout the program to run through a specific length
     * o           | Hangman | the reference variable for the Hangman class
     * holder      | String[]| the array used to hold each line, before the line gets split into each index of the highscores array
     *
     */

    public static void main (String[] args)
    {
	Hangman o = new Hangman ();
	for (int x = 0 ; x < o.deadWords.length ; x++)
	{ //initialize deadWords[]
	    o.deadWords [x] = -1;
	}

	try
	{ //read and store contents of Highscores.txt into the highscores array and the scores array
	    BufferedReader r = new BufferedReader (new FileReader ("Highscores.txt"));
	    for (int i = 0 ; i < 10 ; i++)
	    {
		String[] holder = r.readLine ().split ("-");
		o.scores [10 - i] = Long.parseLong (holder [0]);
		for (int x = 0 ; x < 3 ; x++)
		{
		    o.highscores [i] [x] = holder [x];
		}
	    }
	    r.close ();
	}
	catch (IOException a)
	{
	}
	o.splashScreen ();
	while (!o.choice.equals ("e") && !o.choice.equals ("E"))
	{ //continue to run as long as choice does not equal e(exit)
	    o.menu ();

	    if (o.choice.equals ("a") || o.choice.equals ("A"))
	    { //run instrucitons if choice equals a
		o.instructions ();
	    }
	    else if (o.choice.equals ("b") || o.choice.equals ("B"))
	    { //run highscores if choice equals b
		o.highscores ();
	    }
	    else if (o.choice.equals ("c") || o.choice.equals ("C") || o.choice.equals ("d") || o.choice.equals ("D"))
	    {
		while (!o.option.equals ("b") && !o.option.equals ("B"))
		{ //while user does not want to return to main menu
		    if (o.choice.equals ("c") || o.choice.equals ("C"))
		    { //if choice equals c, run the level 1 methods
			o.hint ();
			o.lvl1 ();
			o.winOrLose ();
		    }
		    else if (o.choice.equals ("d") || o.choice.equals ("D"))
		    { //if choice equals d, run the level 2 methods
			o.lvl2 ();
			while (o.reset == false && o.win == false)
			{ //if reset and win are both false (meaning the cheat option has been selected) run cheat() and lvl2
			    o.cheat ();
			    o.lvl2 ();
			}
			o.winOrLose ();
		    }
		}
	    }
	}
	o.exit (); //exit program
    }
}
