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
c                   | Console    | Console class reference variable
skintone            | Color      | Holds the color values of skintone
red                 | Color      | Holds the color values of red
purple              | Color      | Holds the color values of purple
orange              | Color      | Holds the color values of orange
green               | Color      | Holds the color values of green
background          | Color      | Holds the color values of the background

*/
import hsa.Console;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class SplashScreen extends Thread
{

    private Console c;

    // Colors:
    Color skintone = new Color (249, 217, 198);
    Color red = new Color (205, 50, 54);
    Color purple = new Color (183, 131, 207);
    Color orange = new Color (230, 145, 56);
    Color green = new Color (50, 150, 100);
    Color background = new Color (34, 26, 113);

    public void background ()
    {
	c.setColor (background);
	c.fillRect (0, 0, 800, 540);

	c.setColor (Color.white); //ground
	c.fillRect (0, 480, 800, 200);

	for (int i = 0 ; i < 30 ; i++)
	{ //moon
	    c.drawArc (20 + i, 20, 100 - i, 100, 100, 180);
	}

	//clouds
	c.fillOval (200, -10, 100, 100);
	c.fillOval (230, -5, 100, 100);
	c.fillOval (350, -10, 100, 100);
	c.fillOval (130, -50, 100, 100);
	c.fillOval (170, 15, 100, 100);
	c.fillOval (270, 30, 100, 100);
	c.fillOval (240, 20, 100, 100);
	c.fillOval (290, -5, 100, 100);
	c.fillOval (340, 25, 100, 100);
	c.fillOval (430, -45, 100, 100);
	c.fillOval (410, 25, 100, 100);
	c.fillOval (440, 15, 100, 100);

    }


    /*
    *walkingMethod:This method contains all of the walking animation and graphics
    *
    *Structures:
    *while       - is used to run the walking animation untill it reaches a certian point.
     *
    *trycatch    - catches the exceptions produced by Thread.sleep();
     *
    *if          - checks if the different y and x values reach certian point and control the movement,
     *                                 and when the animation stops
     *
    *synchronized - is used to sync the colors to limit flashing animations
    *
    *Variables:
    *Name:     | Type: | Description
    *----------------------------------------------------------
    *i         | int   | impletmenting variable to count number of times while loop has run
    *m         | int   | the offset value assigned to the image to start off screen for the animation
     * x           | int   | the animation increasing variable that controls the whole animation
     * n           | int   | the x value for the movement of leg1
     * o           | int   | the x value for the movement of leg2
     * k           | int   | the y value for the movement in a sine wave that affects leg1
     * u           | int   | the y value for the movement in a sine wave that affects leg2
    *back1     | boolean | determines if the values n should decrement instead of increment to mimick more real life movement of walking for leg1
     * back2       | boolean | determines if the values o should decrement instead of increment to mimick more real life movement of walking for leg2
    */
    public void walking ()
    {
	int i = 0;
	int m = 200;
	int x = 0;
	int n = 0;
	int o = 0;

	boolean back1 = false;
	boolean back2 = false;
	int k = 0;
	int u = 0;
	try
	{
	    while (true)
	    { //animation loop
		i++;

		if (i < 450)
		{ // if i is less than 450, increment x (animate the person)
		    x++;
		}
		else if (i == 450)
		{ //if i ==450, stop the animation
		    x = 450;
		    break;
		}

		if (n == 35)
		{ //if n reaches 35, move the leg1 back
		    back1 = true;
		}
		else if (n == 0)
		{ //if n ==0, move leg1 forward
		    back1 = false;
		}

		if (back1 == true)
		{ //decrement n, when back1==true, and  set the y value(k) to 0, so the leg stays on one y plane
		    n = 33 - (i % 35);
		    k = 0;
		}
		else if (back1 == false)
		{ //increase n, and move the y value (k) along a sine curve
		    n++;
		    k = ((int) (10 * Math.sin ((n / 7) - 29)));

		}

		synchronized (c)
		{
		    // leg1
		    c.setColor (Color.gray);
		    int[] a = {100 + x - m, 125 + x - m, 115 + x - m + n, 95 + x - m + n};
		    int[] b = {330 + k, 330 + k, 460 + k, 460 + k};
		    c.fillPolygon (a, b, 4);
		}

		synchronized (c)
		{
		    // foot1
		    c.setColor (orange);
		    c.fillArc (127 - 35 + x - m + n, 450 + k, 25, 30, 0, 180);
		}
		synchronized (c)
		{
		    // neck
		    c.setColor (skintone);
		    int[] j = {112 - 15 + x - m, 142 - 15 + x - m, 132 - 15 + x - m, 107 - 15 + x - m};
		    c.setColor (skintone);
		    int[] l = {175, 175, 237, 237};
		    c.fillPolygon (j, l, 4);

		    // head
		    c.setColor (skintone);
		    c.fillOval (100 - 15 + x - m, 150, 70, 70);
		}
		synchronized (c)
		{
		    // eyes
		    c.setColor (Color.black);
		    c.fillOval (143 - 10 + x - m, 183, 10, 10);
		    c.drawArc (142 - 10 + x - m, 179, 20, 18, 70, 90);

		    // mouth
		    c.setColor (Color.black);
		    c.drawArc (140 - 10 + x - m, 205, 30, 20, 70, 60);
		}

		if (i > 35)
		{ //if i is greater than 35, then animate leg2
		    if (o == 35)
		    { //if o (x value of leg2) becomes 35, move the leg backwards
			back2 = true;
		    }
		    else if (o == 0)
		    { //if o is 0, send the leg forwards
			back2 = false;
		    }

		    if (back2 == true)
		    { //decrease o, and keep u(y value of leg2 ) on a consistant y plane
			o = 33 - (i % 35);
			u = 0;
		    }
		    else if (back2 == false)
		    { //increase o and move u along a sine curve
			o++;
			u = ((int) (10 * Math.sin ((o / 5) - 29)));

		    }
		    synchronized (c)
		    {
			//leg2
			c.setColor (Color.gray);
			int[] y = {115 - 20 + x - m, 140 - 20 + x - m, 145 - 30 + x - m + o, 120 - 30 + x - m + o};
			int[] z = {330 + u, 330 + u, 460 + u, 460 + u};
			c.fillPolygon (y, z, 4);
		    }
		    synchronized (c)
		    {
			//foot2
			c.setColor (orange);
			c.fillArc (92 + x - m + o, 455 + u, 25, 30, 0, 180);
		    }

		}
		synchronized (c)
		{
		    // body
		    c.setColor (red);
		    c.fillRoundRect (75 + x - m, 235, 65, 120, 10, 10);
		}

		// arm
		synchronized (c)
		{
		    //outline
		    c.setColor (Color.black);
		    c.fillRect (109 - 10 + x - m, 250, 25, 66);
		}
		synchronized (c)
		{
		    //sleeve
		    c.setColor (red);
		    c.fillRect (110 - 10 + x - m, 250, 23, 65);
		}
		synchronized (c)
		{
		    //hand
		    c.setColor (skintone);
		    c.fillArc (106 - 10 + x - m, 300, 30, 30, 180, 180);
		}

		Thread.sleep (20);

		synchronized (c)
		{
		    c.setColor (background);

		    // head
		    c.fillOval (100 - 15 + x - m, 150, 70, 70);
		    // body
		    c.fillRoundRect (75 + x - m, 235, 67, 120, 10, 10);

		    // neck
		    int[] j = {112 - 15 + x - m, 142 - 15 + x - m, 132 - 15 + x - m, 107 - 15 + x - m};
		    int[] l = {175, 175, 237, 237};
		    c.fillPolygon (j, l, 4);

		    // leg1
		    int[] a = {100 + x - m, 125 + x - m, 115 + x - m + n, 95 + x - m + n};
		    int[] b = {330 + k, 330 + k, 460 + k, 460 + k};
		    c.fillPolygon (a, b, 4);

		    // foot
		    c.fillArc (127 - 35 + x - m + n, 450 + k, 25, 30, 0, 180);

		    if (x > 35)
		    {
			//leg2
			int[] y = {115 - 20 + x - m, 140 - 20 + x - m, 145 - 30 + x - m + o, 120 - 30 + x - m + o};
			int[] z = {330 + u, 330 + u, 460 + u, 460 + u};
			c.fillPolygon (y, z, 4);
			//foot
			c.fillArc (92 + x - m + o, 455 + u, 25, 30, 0, 180);

		    }

		}
	    }

	}
	catch (Exception a)
	{
	}

	if (x == 450)
	{ //stop the animation and just draw the character
	    // legs
	    //leg1
	    c.setColor (Color.gray);
	    int[] a = {100 + x - m, 125 + x - m, 115 + x - m + n, 95 + x - m + n};
	    int[] b = {330 + k, 330 + k, 460 + k, 460 + k};
	    c.fillPolygon (a, b, 4);
	    //leg2
	    c.setColor (Color.gray);
	    int[] y = {115 - 20 + x - m, 140 - 20 + x - m, 145 - 30 + x - m + o, 120 - 30 + x - m + o};
	    int[] z = {330 + u, 330 + u, 460 + u, 460 + u};
	    c.fillPolygon (y, z, 4);

	    // feet
	    c.setColor (orange);
	    c.fillArc (127 - 35 + x - m + n, 450 + k, 25, 30, 0, 180);

	    c.setColor (orange);
	    c.fillArc (92 + x - m + o, 455 + u, 25, 30, 0, 180);

	    // neck
	    c.setColor (skintone);
	    int[] j = {112 - 15 + x - m, 142 - 15 + x - m, 132 - 15 + x - m, 107 - 15 +
		x - m};
	    int[] l = {175, 175, 237, 237};
	    c.fillPolygon (j, l, 4);

	    // head
	    c.setColor (skintone);
	    c.fillOval (100 - 15 + x - m, 150, 70, 70);

	    // eyes
	    c.setColor (Color.black);
	    c.fillOval (143 - 10 + x - m, 183, 10, 10);
	    c.drawArc (142 - 10 + x - m, 179, 20, 18, 70, 90);

	    // mouth
	    c.setColor (Color.black);
	    c.drawArc (140 - 10 + x - m, 205, 30, 20, 70, 60);

	    // body
	    c.setColor (red);
	    c.fillRoundRect (75 + x - m, 235, 65, 120, 10, 10);

	    // arm
	    //outine
	    c.setColor (Color.black);
	    c.fillRect (109 - 10 + x - m, 250, 25, 66);
	    //sleeve
	    c.setColor (red);
	    c.fillRect (110 - 10 + x - m, 250, 23, 65);
	    //hand
	    c.setColor (skintone);
	    c.fillArc (106 - 10 + x - m, 300, 30, 30, 180, 180);

	    //display title
	    c.setFont (new Font ("Consolas", Font.PLAIN, 50));
	    c.setColor (Color.black);
	    c.drawString ("Hangman", 200, 50);
	    c.setFont (new Font ("Rockwell Nova", Font.PLAIN, 15));
	    c.setColor (Color.red);
	    c.drawString ("Holiday Edition", 300, 80);

	    //draw tree
	    c.setColor (Color.green); //leaves
	    int e[] = {470, 490, 480, 490, 450, 460, 450};
	    int f[] = {20, 50, 50, 70, 70, 50, 50};
	    c.fillPolygon (e, f, 7);

	    c.setColor (Color.red); //trunk
	    c.fillRect (465, 70, 10, 30);

	    // Menu Image and opitons
	    try
	    {
		BufferedImage menuBackground = ImageIO.read (new File ("Screen 1_ Splashscreen.png"));
		c.drawImage (menuBackground, 150, 120, null);
	    }
	    catch (IOException p)
	    {
	    }
	}
	c.drawString ("Press any key to continue", 250, 510);
	c.getChar (); //continue program

    }


    /*
    *SplashScreenConstructor:This method initializes the console
    */

    public SplashScreen (Console con)
    {
	c = con;
    }


    /*
    *runMethod:This program runs all of the methods in the SplashScreen class
    */
    public void run ()
    {
	background ();
	walking ();
    }
}
