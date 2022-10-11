/*
Project: ICS3U
Package: graphics
Class: SimpleGame
Programmer: Shaan Banday.
Teacher: Mr. Elliott.
Date Created: 14th October, 2020.
Program Description: A dodging game, programmed in Java. The aim of the game is to get the character to the finish line, without touching the bad guys.
The user can change the colour of the background to any random colour. The user can also change their character to basketball players LeBron James, Micheal
Jordan, and Kobe Bryant The user can move their character using the UP, DOWN, LEFT, and RIGHT arrow keys.
right arrow keys.
*/
package graphics;  //name of package

//import all these methods for graphics
import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;  

public class SimpleGame extends JFrame implements ActionListener, KeyListener //class implements an Action listener for buttons, and a Key listener for movement 
{  //start of class
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7694980414474595922L;
	//Declare graphical objects
    JPanel mainPanel, rightPanel, centerPanel; //declare a main panel, which holds everything, and two smaller panels. One is for the game, and one is for the buttons         
    LayoutManager mainLayout, rightLayout, centerLayout; //create two layouts, one for everything, and one just for the right panel   
    JButton lebron, mj, kobe, randomB, pressPlay, instructions, playAgain, backToStart;; //create 6 buttons, each which change the colour of the character
    JLabel menuLabel; //the JLabel for the menu title
    Color backgroundColor = new Color(203, 255, 250);  //initial colour of the game background (can be changed)
    Color lightPink = new Color(255, 204, 203); //colour of the button background
    
    //create dynamic objects
    Image character; //image of the user controlled character
    Image cursorImage = Toolkit.getDefaultToolkit().getImage("pictures/ballCursor.jpg"); //image of the cursor
    Cursor ballCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point (0,0), "ballCursor"); //set the image of the cursor to ballCursor, and name the cursor appropriately
    
    //declare variables
    int shapeX;  //x location of character
    int shapeY;  //y location of character  
    int badGuyX; //x location of bad guy
    boolean lose = false; //check if game is over, if false, game is still running. If true, user loses.
    boolean win = false; //check if user won, if false, game is still running, if true, user wins.
    boolean start = false; //check if the game has started
    boolean inst = false; //check if the user has selected the instructions button
    
    //declare constants
    final int WIDTH = 60; //width of character
    final int HEIGHT = 60; //height of character
    final int CHANGE = 200; //amount of change for bad guys
    
    public SimpleGame() //create graphical method for all the panels and buttons
    {  
        super("NBA Dodge 'n Dash"); //name of window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //end the program if the close button is hit, otherwise program goes forever
        this.setSize(1300, 850);  //set the size of the window in pixels. 1300 is the width, and 850 is the height  
        
        //create the main panel
        mainPanel = (JPanel) this.getContentPane();  //mainPanel is now in control of all the contents of the JFrame 
        mainLayout = new BorderLayout(); //create a Border Layout called mainLayout
        mainPanel.setLayout(mainLayout); //assign this Border Layout to main panel, the border layout is split into north, south, east, west, and center
        mainPanel.setCursor(ballCursor); //assign ballCursor as the cursor to replace the default OS cursor.
        
        //create the right panel
        rightPanel = new JPanel();  //create a new right panel
        rightLayout = new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS); //create a box layout called rightLayout
        rightPanel.setLayout(rightLayout);  //assign this Box layout to the right panel
        rightPanel.setBackground(lightPink); //set the background of the panel as light pink (r: 255, g: 204, b: 203) 
        rightPanel.setSize(100,850); //set the size of the right panel in pixels. 100 is the width, and 850 is the height (same as JFrame)
        rightPanel.setVisible(false); //set the right panel to non visible, it will become visible again, when the game starts
        
        //create the center panel
        centerPanel = new JPanel(); //create a new center panel
        centerPanel.setLayout(null);  //center panel has no layout (null) 
        centerPanel.setBackground(backgroundColor); //set the background of the panel as baby blue (r: 203, g: 255, b: 250)
        
        //let the key listener affect the panel
        addKeyListener(this); //add the key listener
        setFocusable(true); //make it the focal point if it is used
        setFocusTraversalKeysEnabled(false); //do not focus traversal keys (shift, tab, ctrl, fn, alt, windows key)
        
        //starting parameters of character
        shapeX = 1000; //set starting x position
        shapeY = 450; //set starting y position
        badGuyX = 115;
        character = Toolkit.getDefaultToolkit().getImage("pictures/lebron.jpg"); //set the starting image to LeBron James. Pressing a button can change the image variable.
        
        createBL(); //create all the buttons and labels, using the createBL() method
        
        //add right and center panels to the main panel
        mainPanel.add(rightPanel, BorderLayout.EAST);  //add right panel to 'east' (right)
        mainPanel.add(centerPanel, BorderLayout.CENTER); //add center panel to the center
        this.setResizable(false);  //make it so the window can't be resized
        this.setVisible(true); //make it so the everything is visible  
        this.repaint(); //invoke paint method, and update screen 
    }  
    public void createBL() //void method to create buttons and labels, while also adding them to the screen
    {
    	//Declare objects
    	Font menuFont = new Font("Helvetica", Font.BOLD, 100); //set font of the title to Helvetica bold, size: 100
    	Font buttonFont = new Font("Helvetica", Font.BOLD, 25); //set the font for both menu buttons to Helvetica bold, size: 30
    	
    	//Main Menu JLabel
    	menuLabel = new JLabel("NBA Dodge 'n Dash"); //create the label, and display the title of the game
    	menuLabel.setFont(menuFont); //set the label of the menu to the created font
    	menuLabel.setForeground(Color.ORANGE); //set the colour of the label to orange
    	menuLabel.setBounds(175, 100, 1200, 120); //set the position, and size of the JLabel, since it is being added to the center panel, which has a null layout.
    	centerPanel.add(menuLabel); //add the JLabel menuLabel to the center panel
    	
    	//Main menu, "Press to Play" button
    	pressPlay = new JButton("Press to Play the Game"); //create a pressPlay button, that starts the game when pressed
    	pressPlay.setBounds(440, 250, 400, 200); //set the position, and size of the button, since it is being added to the center panel, which has a null layout.
    	pressPlay.setFont(buttonFont); //set the font of the button, to the previously created font
    	pressPlay.setForeground(Color.YELLOW); //set the color of the font to yellow
    	pressPlay.setBackground(Color.RED);  //set the color of the button background as red
    	pressPlay.setBorder(BorderFactory.createBevelBorder(0, Color.YELLOW, Color.YELLOW));
    	pressPlay.addActionListener(this); //add an action listener, without an action listener, the button will do nothing 
    	pressPlay.setFocusable(false); //if the button is no longer clicked, take the focus off of it
    	centerPanel.add(pressPlay); //add this button to the center panel
    	
    	//Main menu, "Instructions" button
    	instructions = new JButton("Press to Read the Instructions");
    	instructions.setBounds(440, 460, 400, 200); //set the position (x = 440, y = 460), and size of the button (w = 400, h = 200)
    	instructions.setFont(buttonFont); //set the font of the button, to the previously created font
    	instructions.setForeground(Color.YELLOW); //set the color of the font to yellow
    	instructions.setBackground(Color.RED);  //set the color of the button background as red
    	instructions.setBorder(BorderFactory.createBevelBorder(0, Color.YELLOW, Color.YELLOW));
    	instructions.addActionListener(this); //add an action listener so the button actually does something when it is pressed   
    	instructions.setFocusable(false); //if the button is released, remove the focus
    	centerPanel.add(instructions); //add the instructions button the the center panel
    	
    	//Play again button
    	playAgain = new JButton("Press to Play Again");
    	playAgain.setBounds(375, 100, 400, 200); //set the position (x = 400, y = 460), and size of the button (w = 400, h = 200)
    	playAgain.setFont(buttonFont); //set the font of the button, to the previously created font
    	playAgain.setForeground(Color.YELLOW); //set the color of the font to yellow
    	playAgain.setBackground(Color.RED);  //set the color of the button background as red
    	playAgain.setBorder(BorderFactory.createBevelBorder(0, Color.YELLOW, Color.YELLOW));
    	playAgain.addActionListener(this); //add an action listener so the button actually does something when it is pressed   
    	playAgain.setFocusable(false); //if the button is released, remove the focus
    	centerPanel.add(playAgain); //add the playAgain button the the center panel
    	playAgain.setVisible(false); //set the playAgain button as invisible for now
    	
    	backToStart = new JButton("Press to Go Back");
    	backToStart.setBounds(440, 125, 400, 200); //set the position (x = 400, y = 460), and size of the button (w = 400, h = 200)
    	backToStart.setFont(buttonFont); //set the font of the button, to the previously created font
    	backToStart.setForeground(Color.YELLOW); //set the color of the font to yellow
    	backToStart.setBackground(Color.RED);  //set the color of the button background as red
    	backToStart.setBorder(BorderFactory.createBevelBorder(0, Color.YELLOW, Color.YELLOW));
    	backToStart.addActionListener(this); //add an action listener so the button actually does something when it is pressed   
    	backToStart.setFocusable(false); //if the button is released, remove the focus
    	centerPanel.add(backToStart); //add the playAgain button the the center panel
    	backToStart.setVisible(false); //set the playAgain button as invisible for now
    	
        //Create the right panel buttons
        lebron = new JButton("Change to LeBron"); //create button for changing the character to LeBron James
        mj = new JButton("Change to MJ"); //create button for changing the character to Micheal Jordan
        kobe = new JButton("Change to Kobe"); //create button for changing the character to Kobe Bryant
        randomB = new JButton("Change Background");  //create button for changing colour of the background to something random
        
        //set the colour of the button font to yellow
        lebron.setForeground(Color.YELLOW);
        mj.setForeground(Color.YELLOW);
        kobe.setForeground(Color.YELLOW);
        randomB.setForeground(Color.YELLOW);
        
        //set the colour of the button to red
        lebron.setBackground(Color.RED);
        mj.setBackground(Color.RED);
        kobe.setBackground(Color.RED);
        randomB.setBackground(Color.RED);
       
        //set the border of all the buttons to yellow
        lebron.setBorder(BorderFactory.createBevelBorder(0, Color.YELLOW, Color.YELLOW));
        mj.setBorder(BorderFactory.createBevelBorder(0, Color.YELLOW, Color.YELLOW));
        kobe.setBorder(BorderFactory.createBevelBorder(0, Color.YELLOW, Color.YELLOW));
        randomB.setBorder(BorderFactory.createBevelBorder(0, Color.YELLOW, Color.YELLOW));
        
        //add action listener to all buttons, so the action listener method is invoked when each button is pressed
        lebron.addActionListener(this); 
        mj.addActionListener(this);   
        kobe.addActionListener(this);   
        randomB.addActionListener(this);    
        
        //after button is pressed, take focus off buttons
        lebron.setFocusable(false);   
        mj.setFocusable(false);      
        kobe.setFocusable(false);     
        randomB.setFocusable(false);   
        
        //add buttons the the right panel
        rightPanel.add(lebron);
        rightPanel.add(mj);
        rightPanel.add(kobe);
        rightPanel.add(randomB);  
    } //end of createBL() method
    public void actionPerformed(ActionEvent e) //create void action listener method, to be invoked when a button is pressed
    {  
    	//declare variables
        String buttonName = e.getActionCommand(); //string variable that replicates the button name
        int a = (int) (Math.random()*255);  //generate random number for r value of the random colour
        int b = (int) (Math.random()*255);  //generate random number for g value of the random colour
        int c = (int) (Math.random()*255);  //generate random number for b value of the random colour
        
        //switch statements
        switch (buttonName) //a switch statement based on what button is pressed
        { 
        case "Press to Go Back":
        	backToStart.setVisible(false); //make the button invisible
        	centerPanel.setBackground(backgroundColor);
        	win = false; //set win to false
        	lose = false; //set lose to false
        	start = false;
        	inst = false;
        case "Press to Play Again": //if user wants to play again
        	shapeX = 1000; //reset x-value of character
        	shapeY = 450; //reset y-value of character
        	playAgain.setVisible(false); //make the button invisible
        	character = Toolkit.getDefaultToolkit().getImage("pictures/lebron.jpg"); //set picture back to default 
        	backgroundColor = new Color(203, 255, 250); //set background back to default
        	centerPanel.setBackground(backgroundColor);
        	win = false; //set win to false
        	lose = false; //set lose to false
        	break;
        case "Press to Play the Game": //if user wants to start playing
        	pressPlay.setVisible(false); //make the button invisible
        	start = true; //set start to true, so game can start
        	break;
        case "Press to Read the Instructions": //if user wants to read the instructions
        	instructions.setVisible(false); //make the button invisible
        	inst = true; //set inst to true, so user can read instructions
        	break;
        case "Change to MJ": //if Micheal Jordan is selected
        	character = Toolkit.getDefaultToolkit().getImage("pictures/mj.jpg"); //change the picture to mj
        	break; 
        case "Change to LeBron": //if LeBron James is selected
        	character = Toolkit.getDefaultToolkit().getImage("pictures/lebron.jpg"); //change the picture to lbj
        	break;
        case "Change to Kobe": //if Kobe Bryant is selected
        	character = Toolkit.getDefaultToolkit().getImage("pictures/kobe.jpg"); //change the picture to kb
        	break;
        case "Change Background": //if change background is selected
        	backgroundColor = new Color(a, b, c); //set backgroundColour to the three random RGB values previously declared
            centerPanel.setBackground(backgroundColor); //change the center panels background to that colour
            break; 
        }  //end of switch statement
        repaint();  //invoke paint method, and update screen 
    }  //end of action listener
    public void keyPressed(KeyEvent e)  //create void key listener method, to be invoked if any button is pressed
    {  
    	//declare constants
    	final int MOVEMENT = 5; //this is how much the character moves
    	
    	//declare variable
        int pressedCode = e.getKeyCode(); //integer variable to store the key code since up, down, left, and right arrows do not have an ASCII code
        
        //switch statements
        switch (pressedCode) //switch statement based on what key is pressed 
        { 
        case KeyEvent.VK_UP: //if up is pressed
            shapeY -= MOVEMENT; //decrease y value of character
            break; 
        case KeyEvent.VK_DOWN: //if down is pressed
            shapeY += MOVEMENT; //increase y value of character
            break; 
        case KeyEvent.VK_LEFT: //if left is pressed
            shapeX -= MOVEMENT; //decrease x value of character
            break; 
        case KeyEvent.VK_RIGHT: //if right is pressed
            shapeX += MOVEMENT; //increase x value of character
            break; 
        case KeyEvent.VK_W: //if W is pressed
            shapeY -= MOVEMENT; //decrease y value of character
            break; 
        case KeyEvent.VK_S: //if S is pressed
            shapeY += MOVEMENT; //increase y value of character
            break; 
        case KeyEvent.VK_A: //if A is pressed
            shapeX -= MOVEMENT; //decrease x value of character
            break; 
        case KeyEvent.VK_D: //if D is pressed
            shapeX += MOVEMENT; //increase x value of character
            break;
        }  //end of switch statement
        repaint(); //invoke paint method, and update panel
        
        //set boolean variables if game has been won or lost
        win = gameWon(shapeX, shapeY); //invoke gameWon(int x, int y) method to check if player has won
        repaint(); //invoke paint method, and update panel
        lose = gameOver(shapeX, shapeY); //invoke gameOver(int x, int y) method, to check if player has lost
        repaint(); //invoke paint method, and update panel
 
    }  //end of key pressed method
    public void keyReleased(KeyEvent e) { //void key released method 
    	//method is not used, so there is no code
    } 
    public void keyTyped(KeyEvent e) { //void key typed method 
    	//method is not used, so there is no code
    } 
    public boolean gameOver(int x, int y) //create a boolean method to check if player has lost
    {        
    	//declare variables
    	boolean isGameOver = false; //set isGameOver to false for now
    	int leftX = 60, rightX = 200, upY = 45, downY = 200; //starting bad guy position
    	
    	//decisions
	    if (((x >= leftX) && (x <= rightX)) && ((y >= upY) && (y <= downY))) //if character touches the first bad guy
	    {
	    	isGameOver = true; //game is over
	    	repaint(); //update panel
	    }
	    else if (((x >= (leftX + CHANGE)) && (x <= (rightX + CHANGE))) && ((y >= (upY + CHANGE)) && (y <= (downY+ CHANGE)))) //if character touches the second bad guy
	    {
	    	isGameOver = true; //game is over
	    	repaint(); //update panel
	    }
	    else if (((x >= (leftX + CHANGE*2)) && (x <= (rightX + CHANGE*2))) && ((y >= (upY + CHANGE*2)) && (y <= (downY+ CHANGE*2)))) //if character touches the third bad guy
	    {
	    	isGameOver = true; //game is over
	    	repaint(); //update panel
	    }
	    else if (((x >= (leftX + CHANGE*3)) && (x <= (rightX + CHANGE*3))) && ((y >= (upY + CHANGE*3)) && (y <= (downY+ CHANGE*3)))) //if character touches the fourth bad guy
	    {
	    	isGameOver = true; //game is over
	    	repaint(); //update panel
	    }
	    else //if it stays in boundaries, and does not touch any bad guy
	    {
	    	y += 0; //do not change y value of character
	    	x += 0; //do not change x value of character
	    } 
	    return isGameOver; //return a boolean value if the game has been lost
    }
    public boolean gameWon(int x, int y) //create a boolean method to check if player has won
    {
    	//declare variables
    	boolean isGameWon = false; //set isGameWon to false for now
    	
    	//decisions
    	if ((x >= 1085) || (x <= 90))  //if character is outside the horizontal borders of the game
        {  
             if (x >= 1085) //if it veers too far right
             {  
                 shapeX -= 50; //send it back 50 pixels left
                 isGameWon = false; //keep game going
                 repaint(); //update it
             }  
             else if (x <= 90) //if the shape gets to the finish line 
             {  
            	 isGameWon = true; //game is not over
                 repaint();  //update panel
             }  
             else   //if shape does not go outside boundaries
             {  
                 shapeX += 0; //do not bounce shape back
             }  
        }  
        else if ((y >= 850) || (y <= 25))  //if character is outside the vertical borders of the game
        {  
             if (y >= 850)  //if it veers too far down
             {  
                 shapeY -= 50;  //send it back 50 pixels up
                 isGameWon = false; //player has not won yet
                 repaint(); //update panel
             }  
             else if (y <= 25) //if it veers too far up
             {  
            	 shapeY += 50; //send it back 50 pixels down
                 isGameWon = false; //player has not won yet
                 repaint(); //update panel
             }  
             else //if it stays within the boundaries
             {  
            	 shapeY += 0;  //do not change character position
             }  
        } 
        else //if it stays in boundaries, and does not touch any bad guy
        {
        	shapeY += 0; //do not change y value of character
        	shapeX += 0; //do not change x value of character
        } 
    	return isGameWon; //return the boolean value, if the game has been won
    }
    public void pause(int a) //void pause method that stops the program for "a" milliseconds
    {
    	try //try to execute the block of code below
    	{ 
    		Thread.sleep(a); //sleep and stop the program for "a" seconds
    	}
    	catch (Exception e) { //handle the errors found
    	}
    } 
    public void paint(Graphics g) //create void paint method to paint
    {  
    	//declare objects
    	Font firstFont = new Font("Helvetica", Font.BOLD, 100); //set font to Bold 100 TNR  
    	
    	//declare constants
    	final int BODY_W = 90, BODY_H = 80; //width and height of the body of the bad guy
    	final int EYE_WH = 15; //width and height of the eye of the bad guy, one constant mens it will be a circle
    	
    	//start painting
        super.paint(g); //enable panel to be painted
        g.setColor(Color.BLACK); //set default colour to black
        if (start) //if the start 
        {
        	menuLabel.setVisible(false);
        	instructions.setVisible(false);
        	rightPanel.setVisible(true);
	        if (!lose) //if game is not over
	        {
	        	if (!win) //if game has not been won
	        	{
	        		//Draw finish line
	        		Image myPic = Toolkit.getDefaultToolkit().getImage("pictures/checkerboardPattern.jpg"); //import picture for finish line
		        	g.drawImage(myPic, -1200, 10, this); //draw finish line 
		        	
		        	//Draw character
		        	g.drawImage(character, shapeX, shapeY, this); //set the image, and the position of the character
		        	
		        	//draw title
		        	Font titleFont = new Font("Times Roman", Font.BOLD, 36); //set font for title
		        	g.setColor(Color.DARK_GRAY); //change colour of font as dark grey
		        	g.setFont(titleFont); //set the font to titleFont
		        	g.drawString("Get to the Finish Line!", 425, 75); //draw the title

		        	//draw bad guys
		        	for (int i = badGuyX; i < 1000; i += CHANGE) //loop to draw multiple bad guys, starting at 115
		        	{
		        		//draw body
		        		g.setColor(Color.BLACK); //set body colour to black
		        		g.drawRect(i, i + 10, BODY_W, BODY_H); //draw body with those dimensions 
		        		g.fillRect(i, i + 10, BODY_W, BODY_H); //fill body with those dimensions
		        		
		        		//draw eyes
		        		g.setColor(Color.RED); //set eye colour to red
		        		int j = i + 20; //starting position of eyes it 20 pixels to the left of the body
		        		g.fillOval(j, j + 5, EYE_WH, EYE_WH); //draw eye with those dimensions, and a constant height and width for uniform shape (circle)
		        		g.fillOval(j + 37, j + 5, EYE_WH, EYE_WH); //fill eye with those dimensions
		        		
		        		//draw eyebrows
		        		int[] x1Points = {j, j + 5, j+20, j + 15};  //create an array for the 4 x-values of the eyebrow
		        		int[] y1Points = {j-5, j-5, j, j};  //create an array for the 4 y-values of the eyebrow
		        		g.drawPolygon(x1Points, y1Points, 4); //draw polygon, with 4 vertices
		        		g.fillPolygon(x1Points, y1Points, 4); //fill polygon, with 4 vertices
		        		int[] x2Points = {j + 50, j + 55, j + 40, j + 35}; //create an array for the 4 x-values of the second eyebrow
		        		g.drawPolygon(x2Points, y1Points, 4); //draw polygon with 4 vertices. NOTE: since the second eyebrow does not vertically change, the first array can be reused
		        		g.fillPolygon(x2Points, y1Points, 4); //fill polygon
		        		
		        		//draw mouth
		        		g.fillOval(j - 5, j + 30, 72, 30); //draw mouth with those vertices
		        	} //end of loop
	        	}
		        else //if game has been won
		        {
		            g.setFont(firstFont); //set to first font
		            g.drawString("YOU WIN!", 250, 500); //draw a string at this position
		            pause(850); //pause for 850 milliseconds (0.85 seconds)
		            playAgain.setVisible(true); //set the playAgain button to visible
		        }
	        }
	        else //if game is over
	        {
	        	g.setFont(firstFont); //set to first font
	        	g.drawString("GAME OVER!", 250, 500); //draw a string at this position
	        	pause(850); //pause for 850 milliseconds (0.85 seconds)
	            playAgain.setVisible(true); //set the playAgain button to visible
	        }
        }
        else if ((!start) && (inst)) //if user selects instructions, and not start
        {
        	pressPlay.setVisible(false); //make the press play button invisible
        	menuLabel.setVisible(false); //make the menu label invisible
        	backToStart.setVisible(true);
        	g.setFont(firstFont); //set the font to firstFont
        	g.drawString("INSTRUCTIONS", 275, 110); //draw a string at this position
        	Font instFont = new Font("Helvetica", Font.BOLD, 36); //set font to Bold 100 TNR
        	g.setFont(instFont); //set the font to the newly created instFont
        	g.drawString("Use the UP, LEFT, RIGHT, and DOWN arrow keys or WASD keys", 100, 510); //display message
        	g.drawString("to move around or press any button at the side to change the character.", 40, 550); //display message
        	g.drawString("Your goal is to get to the other side of the screen, where the finish line is,", 32, 590); //display message
        	g.drawString("while not touching the bad guys. If you touch them, the game is over.", 62, 630); //display message
        }
        else //If the user does not select either button
        {
        	menuLabel.setVisible(true); //Keep the title visible
        	instructions.setVisible(true); //Keep the instructions button visible
        	pressPlay.setVisible(true); //Keep the press play button visible
        	rightPanel.setVisible(false); //Keep the right panel invisible
        }
    } //end of paint method
} //end of class