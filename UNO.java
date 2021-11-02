/******************************************************************************

Sinry Dong
ICS3U
June 19 2020
This program is a modified game of UNO executed through the console, JoptionPanes, and 
Java Swing. The game is played between the user and the computer. Both players start with 
a set of 7 cards and the goal of the game is to be the first to play all cards. The computer
and user take turns playing or drawing cards until one player wins. The final score of the
user is written to a file and read to display a score board. 

*******************************************************************************/
import java.util.Scanner;
import java.util.Random;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File; 
import java.io.IOException; 
import java.io.FileNotFoundException;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.FileWriter;
import javax.swing.*;
import javax.swing.border.Border;


public class UNO {
	
	//Static variable to keep track of the previous card played after each player goes, can be accessed by all methods
	static int baseCard; 
	
	/**
	 * Displays the title, rules, and card distribution of the game
	 * pre: No syntax errors
	 * post: text printed to a JOptionPane using HTML format
	 */
	public static void displayIntro() {
		String instructions = "UnoInstructions.txt";
		File file = new File(instructions); 
		 //Creates new instructions file if the file does not already exist
		if(file.exists()) { 
		} else {
			try {
				file.createNewFile(); 
				Writer writer = null;
				try {
					//Append mode switched on 
					writer = new FileWriter(instructions, true); 
				} catch (IOException e) {
					e.printStackTrace(); 
				}
				PrintWriter output = new PrintWriter(writer); 
				//Prints html instructions to new text file
				output.println("<html>"); 
				output.println("<h1 style=\"color: #5e9ca0; text-align: center;\"><span style=\"color: #008080;\">Welcome to the game of UNO!</span></h1>");
				output.println("<h3 style=\"color: #5e9ca0; text-align: left;\"><span style=\"color: #000000;\">Players: 2&nbsp;</span>&nbsp;</h3>");
				output.println("<p><strong><span style=\"color: #008080;\">RULES:</span> Each player will be dealt 7 cards.</strong><strong> Players will take&nbsp;</strong><strong>turns playing one card at a time.&nbsp;</strong></p>");
				output.println("<p><strong>The card played must be the same colour (same tens place) or the same&nbsp;</strong><strong>number </strong></p>");
				output.println("<p><strong>(same ones place) as the previous card laid down. If a player has </strong><strong>no cards that can be played,</strong></p>");
				output.println("<p><strong>they must draw a card from the pile. A player&nbsp;</strong><strong>can have a maximum of 20 cards in their hand. </strong></p>");
				output.println("<p><strong>Beyond 20, </strong><strong>they are not&nbsp;</strong><strong>required to draw. The first player to play all their cards wins. </strong></p>");
				output.println("<p>&nbsp;</p>");
				output.println("<p>To <strong>PLAY</strong> a card: Type the number of the card into the console and press ENTER</p>");
				output.println("<p>To <strong>DRAW</strong> a card: Type the word \"draw\" into the console and press ENTER</p>");
				output.println("<p>&nbsp;</p>");
				output.println("<p><strong><span style=\"color: #008080;\">CARDS:</span></strong></p>");
				output.println("<table style=\"width: 549px; border-color: black; margin-left: auto; margin-right: auto;\">");
				output.println("<tbody>");
				output.println("<tr>");
				output.println("<td style=\"width: 126px;\"><span style=\"color: #ff0000;\"><strong>RED</strong></span></td>");
				output.println("<td style=\"width: 135px;\"><span style=\"color: #ffcc00;\"><strong>YELLOW</strong></span></td>");
				output.println("<td style=\"width: 131px;\"><span style=\"color: #008000;\"><strong>GREEN</strong></span></td>");
				output.println("<td style=\"width: 129px;\"><span style=\"color: #0000ff;\"><strong>BLUE</strong></span></td>");
				output.println("</tr>");
				output.println("<tr>");
				output.println("<td style=\"width: 126px;\"><strong>1-9</strong></td>");
				output.println("<td style=\"width: 135px;\"><strong>11-19</strong></td>");
				output.println("<td style=\"width: 131px;\"><strong>21-29</strong></td>");
				output.println("<td style=\"width: 129px;\"><strong>31-39</strong></td>");
				output.println("</tr>");
				output.println("</tbody>");
				output.println("</table>");
				output.println("<p><span style=\"color: #ff6600;\">&nbsp;</span></p>");
				output.println("<p><span style=\"color: #ff6600;\"><strong>NOTE:&nbsp;</strong><span style=\"color: #000000;\">Game played in the console</span></span></p>"); 
				output.println("</html>"); 
				output.close();
			} catch (IOException e) {
				System.out.println("File could not be created:"); 
				System.err.println("IOException: "+e.getMessage()); 
			}
		}
		
		//New scanner object created
		Scanner sc = null; 
		try {
			sc = new Scanner(file); 
		} catch (FileNotFoundException e){
		}
		//Reads lines from instructions text file and adds onto the string intro
		String intro = ""; 
		while(sc.hasNextLine()) {
			intro += sc.nextLine(); 
		}
		//Intro rules printed in a JOptionPane
		JOptionPane.showMessageDialog(null, intro, "UNO", 3); 
	}
	
	/**
	 * Deals random cards to the user
	 * pre: packages imported, no syntax or logic errors
	 * post: Returns array of integers as the cards for the user
	 */
	public static int[] dealUserCards() {
		Random r = new Random(); 
		//Declares array and allocates space for a maximum of 20 cards
		int[] userCards = new int[20]; 
		//Generates a random number and assigns to the first 7 elements
		for(int i=0; i<7; i++) {
			int cardNumber; 
			//Assigns random number (1-39) to a card
			do {
				cardNumber = r.nextInt(39-1+1)+1;
				userCards[i] = cardNumber;
			//Re-assigns value is the number is 10,20,30 (invalid values) 
			} while (cardNumber==10||cardNumber==20||cardNumber==30); 
		}
		//Returns array with its element values
		return userCards; 
	}
	
	/**
	 * Deals random cards to the computer
	 * pre: packages imported, no syntax or logic errors
	 * post: Returns array of integers as the cards for the computer
	 */
	public static int[] dealComputerCards() {
		Random r = new Random();  
		//Declares array and allocates space for a maximum of 20 cards
		int[] computerCards = new int[20];
		//Generates a random number and assigns to the first 7 elements
		for(int i=0; i<7; i++) {
			int cardNumber; 
			//Assigns random number to a card
			do {
				cardNumber = r.nextInt(39-1+1)+1;
				computerCards[i] = cardNumber;
			//Re-assigns value is the number is 10,20,30 (invalid values) 
			} while (cardNumber==10||cardNumber==20||cardNumber==30); 
		}
		//Returns array with its element values
		return computerCards; 
	}
	
	
	/**
	 * Computer's turn is executed. Computer's card numbers are displayed and the computer either plays 
	 * or draws a card depending on which move is possible 
	 * pre: index of computerCards[]>0, baseCard(static int)!=0,10,20,30
	 * post: Computer's card is played or a card is drawn. Uno is displayed if the computer
	 * only has one card. basecard value is reassigned if a card is played. Returns altered
	 * array (deck of cards). 
	 */
	public static int[] computerPlays(int[] computerCards) {
		Random r = new Random();
		int counter = 0; 
		System.out.println("COMPUTER'S TURN"); 
		//Loop looks through computer's card set to find a card that can be played (Note: empty cards = 0 )
		for(int i=0; i<20; i++) {
			//Card can be played if the tens place or ones place is the same as the base card
			if(computerCards[i]!=0 && ((computerCards[i]/10)==(baseCard/10)||(computerCards[i]%10)==(baseCard%10))) {
				String colour=""; 
				//Determines colour of the card by finding the tens place through integer division
				if((computerCards[i]/10) == 0) {
					colour = "Red"; 
				} else if ((computerCards[i]/10)==1) {
					colour = "Yellow"; 
				} else if ((computerCards[i]/10)==2) {
					colour = "Green"; 
				} else if ((computerCards[i]/10)==3) {
					colour = "Blue"; 
				}
				//Prints out card played and its colour
				System.out.format("%-3s%-6s%3s%n", "___","______","___");
				System.out.format("%-3s%-6s%3s%n", "|","","|");
				System.out.format("%-3s%-6s%3s%n", "|","","|");
				System.out.format("%-3s%-6s%3s%n", "|",colour,"|");
				System.out.format("%-3s%-6s%3s%n", "|",computerCards[i],"|");
				System.out.format("%-3s%-6s%3s%n", "|","","|");
				System.out.format("%-3s%-6s%3s%n", "|__","______","__|");
				//baseCard value changed to the card just played
				baseCard=computerCards[i];
				//Card played is removed from computer's set
				computerCards[i] = 0; 
				//Exits loop once the card is played
				break;
			}
			counter++; //counter increments by one every time a card cannot be played
		}
		
		//If no cards in the deck can be played, the computer draws a card
		if(counter==20) {
			//counter records the empty spaces in the computer's card deck
			int spaceCounter=0; 
			for(int i=0; i<20; i++) {
				//An empty space is found in the set
				if(computerCards[i]==0) {
					int cardNumber; 
					//Assigns random number to a card
					do {
						cardNumber = r.nextInt(39-1+1)+1;
						computerCards[i] = cardNumber;
					//Re-assigns value if the number is 10,20,30 (invalid values) 
					} while (cardNumber==10||cardNumber==20||cardNumber==30);
					System.out.println("COMPUTER DRAWS 1 CARD"); 
					break; //loop exited so only one card is added
				}
				spaceCounter++; //Counter increments by one each time a card is detected
			}
			//If no empty spaces are in the deck, the computer does not draw a card
			if(spaceCounter==20) {
				System.out.println("COMPUTER'S DECK IS FULL: NO CARDS HAVE BEEN ADDED"); 
			}
		}
		
		counter = 0; //counter reset to 0
		//Looks through card set and determines the number of cards the player has after the round
		for(int i=0; i<20; i++) {
			if(computerCards[i] != 0) {
				counter++; //counter increments by one when a card exists
			}
		}
		//If only one card exists in the deck, "UNO" is displayed to the screen
		if(counter==1) {
			System.out.println("COMPUTER: UNO"); 
		}
		//Displays number of cards in computer's deck
		System.out.println("COMPUTER'S DECK: "+counter+ " CARDS"); 
		System.out.println(); 
		//Returns new deck after cards have been played or added
		return computerCards; 
	}
	
	
	/**
	 * Player's turn is executed. Player's card numbers are displayed and the player
	 * can choose to either draw a card or play a card. 
	 * pre: index of userCards[]>0, username!=null, baseCard(static int)!=0,10,20,30
	 * post: Player's card is played or player's card is drawn. Uno is displayed if the user
	 * only has one card. basecard value is reassigned if a card is played. Returns altered
	 * array (deck of cards). 
	 */
	public static int[] userPlays(int[] userCards, String username) {
		Scanner input = new Scanner(System.in); 
		Random r = new Random();
		System.out.println("YOUR CARDS:"); 
		//Displays all card numbers the player has
		for(int i=0; i<20; i++) {
			//Ignores empty cards (i.e. elements initialized to 0)
			if(userCards[i] != 0) {
				System.out.print(userCards[i]+ "   "); 
			}
		}
		System.out.println(); //Moves insertion point to next line
		//Keeps track of whether the user plays or draws a card; default: plays card
		boolean choice= true; 
		//counter used to determine the number of cards in the set
		int counter=0; 
		int card = 0;  
		String draw;  
		do {
			//reset to 0 every repetition
			card=0; 
			counter=0; 
			System.out.println("Play Card: "); 
			//Determines which data type has been entered by the user 
			if(input.hasNextInt()) { //If integer is entered
				card = input.nextInt(); 
				String colour=""; 
				//Determines colour of the card by finding the tens place through integer division
				if((card/10) == 0) {
					colour = "Red"; 
				} else if ((card/10)==1) {
					colour = "Yellow"; 
				} else if ((card/10)==2) {
					colour = "Green"; 
				} else if ((card/10)==3) {
					colour = "Blue"; 
				}
				//Looks for the card number entered in the player's set
				for(int i=0; i<20; i++) {
					//Card played and displayed if it exists in the set and is valid with the previous card
					if(userCards[i]==card) {
						if((card/10)==(baseCard/10) || (card%10)==(baseCard%10)) {
							System.out.format("%-3s%-6s%3s%n", "___","______","___");
							System.out.format("%-3s%-6s%3s%n", "|","","|");
							System.out.format("%-3s%-6s%3s%n", "|","","|");
							System.out.format("%-3s%-6s%3s%n", "|",colour,"|");
							System.out.format("%-3s%-6s%3s%n", "|",card,"|");
							System.out.format("%-3s%-6s%3s%n", "|","","|");
							System.out.format("%-3s%-6s%3s%n", "|__","______","__|");
							//Card played is removed from player's set
							userCards[i] = 0; 
							//baseCard value reassigned to the card just played
							baseCard=card;
							//Exits loop if a card can be played
							break; 
						}
					}
					counter++; //counter increments by one every time a card cannot be played
				}
				
			} else if (input.hasNext()) { //if a one-word string is entered
				draw = input.next(); 
				//If the user enters the word "draw", a card is added to the player's set
				if(draw.equalsIgnoreCase("draw")) {
					//changed to false when user draws a card
					choice = false;
					for(int i=0; i<20; i++) {
						//An empty space is found in the set
						if(userCards[i]==0) {
							int cardNumber; 
							//Assigns random number to a card
							do {
								cardNumber = r.nextInt(39-1+1)+1;
								userCards[i] = cardNumber;
							//Re-assigns value is the number is 10,20,30 (invalid values) 
							} while (cardNumber==10||cardNumber==20||cardNumber==30); 
							break; //loop exited so only one card is added
						}
						counter++; //counter incremented by one for each repetition
					}
					//If the counter reaches 20 (no empty spaces in set), no cards will be added
					if(counter==20) {
						System.out.println("YOUR DECK IS FULL: NO CARDS HAVE BEEN DRAWN"); 
					}
				}
			}
		//Player must play a different card if the tens and ones place is different, or if they do not have the card entered
		} while (((card/10)!=(baseCard/10) && (card%10)!=(baseCard%10) && choice==true) || (counter==20 && choice==true));
		
		
		counter = 0; //counter reset to 0
		//Looks through card set and determines the number of cards the player has after the round
		for(int i=0; i<20; i++) {
			if(userCards[i] != 0) {
				counter++; //counter increments by one when a card exists
			}
		}
		//If only one card exists in the deck, "UNO" is displayed to the screen
		if(counter==1) {
			System.out.println(username+": UNO"); 
		}
		System.out.println(); 
		//Returns new deck after cards have been played or added
		return userCards; 
	}
	
	
	/*
	 * Final score portion is executed. Scores are written to a file, read from 
	 * the file and displayed in a score board through a GUI
	 * pre: boolean winner==true||false, username initiated, required packages and classes imported
	 * post: Uno.txt created (if not already), file written to, file read, message dialog boxes
	 * executed, JFrame displayed
	 */
	public static void getScore(String username, boolean winner) {
		Scanner input = new Scanner(System.in); 
		//Read/Write to file score board
		String scoreBoard = "Uno.txt";
		File file = new File(scoreBoard); 
		 //Creates new file if the file does not already exist
		if(file.exists()) { 
		} else {
			try {
				file.createNewFile(); 
			} catch (IOException e) {
				System.out.println("File could not be created:"); 
				System.err.println("IOException: "+e.getMessage()); 
			}
		}
		
		Writer writer = null;
		try {
			//Append mode switched on 
			writer = new FileWriter(scoreBoard, true); 
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		 
		//Prompts user for date using a dialog box
		String date = JOptionPane.showInputDialog(null, "Enter Date:", "UNO", JOptionPane.PLAIN_MESSAGE);
		//Changes date to "date" if the user clicks cancel
		if(date==null) {
			date="DATE"; 
		}
		
		PrintWriter output = new PrintWriter(writer); 
		//Writes to text file
		if(winner==true) { 
			//If user won (boolean is true) 
			output.println(date+":   "+username+":   WIN");
			output.close();
		} else {
			//If user lost (boolean is false) 
			output.println(date+":   "+username+":   LOSE");
			output.close();
		} 
		
		//GUI SCOREBOARD with JAVA SWING
		//JFrame created 
		JFrame jframe = new JFrame("UNO"); 
		jframe.setSize(540,400); 
		//JFrame closes when x is pressed
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		//JFrame set to appear on top of tab in the middle of the screen
		jframe.setLocationRelativeTo(null);
		jframe.setAlwaysOnTop(true); 
		 
		//JPanel created, colour selected, added to jframe
		JPanel panel = new JPanel(); 
		panel.setBackground(Color.WHITE); 
		jframe.add(panel); 
		
		//Box layout created, vertical layout
		BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxlayout);
		
		//Scrollable panel created
		JScrollPane scroll = new JScrollPane();
		//Only vertical scroll bar set
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//Scrollpane added to jframe and centered
		scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
		scroll.setViewportView(panel);
		jframe.add(scroll); 
		
		//JLabel for title created
		JLabel title = new JLabel("UNO: SCOREBOARD");
		//JLabel aligned, font selected, added to JPanel
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setFont(new Font("Verdana",1,20));
		panel.add(title);
		
		//New Scanner object created
		Scanner sc = null; 
		try {
			sc = new Scanner(file); 
		} catch (FileNotFoundException e){
		}
		String scores="";  
		//Reads lines from text file and prints out the score history
		while(sc.hasNextLine()) {
			scores = sc.nextLine(); 
			//New JLabel created, content read from text file
			JLabel players = new JLabel(scores, SwingConstants.CENTER);
			//JLabel centered, font selected, dimensions set, border created, added to JPanel
			players.setAlignmentX(Component.CENTER_ALIGNMENT);
			players.setFont(new Font("Verdana",1,14));
			players.setPreferredSize(new Dimension(540, 24));
			players.setMinimumSize(new Dimension(540, 24));
			players.setMaximumSize(new Dimension(540, 24));
			Border border = BorderFactory.createLineBorder(Color.ORANGE, 1); 
			players.setBorder(border);
			panel.add(players);
		}
		
		//JFrame set to visible
		jframe.setVisible(true);
	}
	

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in); 
		Random r = new Random(); 
		
		//Calls displayIntro method to print rules to the screen
		displayIntro(); 
		
		//Prompts user for his/her name 
		String name = JOptionPane.showInputDialog(null, "Enter Name:", "UNO", JOptionPane.PLAIN_MESSAGE); 
		//Assigns default name if the user clicks "cancel" 
		if(name==null) {
			name="player"; 
		}
		//Name converted to a constant with all upper case letters
		final String USERNAME = name.toUpperCase(); 
		
		//User and computer cards (array) declared. Methods called to obtain array values
		int[] userCards = new int[20];
		userCards = dealUserCards();
		int[] computerCards = new int[20]; 
		computerCards = dealComputerCards(); 	
		
		//Game officially starts here (PRINTS TO CONSOLE) 
		System.out.println("Let's begin the game!"); 
		//First base card generated randomly 
		do {
			baseCard = r.nextInt(39-1+1)+1;
		//Re-assigns value if the number is 10,20,30 (invalid values) 
		} while (baseCard==10||baseCard==20||baseCard==30);
		String baseColour=""; 
		//Determines colour of the card by finding the tens place through integer division
		if((baseCard/10) == 0) {
			baseColour = "Red"; 
		} else if ((baseCard/10)==1) {
			baseColour = "Yellow"; 
		} else if ((baseCard/10)==2) {
			baseColour = "Green"; 
		} else if ((baseCard/10)==3) {
			baseColour = "Blue"; 
		}
		//Prints out base card in a card format; 3 columns
		System.out.format("%-3s%-6s%3s%n", "___","______","___");
		System.out.format("%-3s%-6s%3s%n", "|","","|");
		System.out.format("%-3s%-6s%3s%n", "|","","|");
		System.out.format("%-3s%-6s%3s%n", "|",baseColour,"|");
		System.out.format("%-3s%-6s%3s%n", "|",baseCard,"|");
		System.out.format("%-3s%-6s%3s%n", "|","","|");
		System.out.format("%-3s%-6s%3s%n", "|__","______","__|");
		
		//ROUNDS officially begin
		//Counters keeps track of number of cards
		int computerSpaces; 
		int userSpaces; 
		//Tracks who wins
		boolean winner = true; 
		//Sets up dialog box to appear on top of current window
		final JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		//Do-while loop allows the user and computer to play one round after each other
		do {
			//userPlays method called, altered deck of cards returned
			userCards=userPlays(userCards, USERNAME);
			//Cards counter reset to 0 each time
			userSpaces=0;
			//Determines the number of cards in the deck 
			for(int i=0; i<20; i++) {
				if(userCards[i]!=0) {
					userSpaces++; //increments by 1 for every non-empty space
				}
			}
			//if no cards are in the user's deck, the user wins
			if(userSpaces==0) {
				//Dialogue box appears
				JOptionPane.showMessageDialog(dialog, USERNAME +" WINS", "UNO", JOptionPane.PLAIN_MESSAGE);
				break; //loop exited, game over
			} 
			
			//computerPlays method called, altered deck of cards returned
			computerCards=computerPlays(computerCards);
			//Cards counter reset to 0 each time
			computerSpaces=0; 
			//Determines number of cards in the deck
			for(int i=0; i<20; i++) {
				if(computerCards[i]!=0) {
					computerSpaces++; //increments by 1 for every non-empty space
				}
			}
			//If no cards are in the computer's deck, the computer wins
			if(computerSpaces==0) {
				//Dialogue box appears
				JOptionPane.showMessageDialog(dialog, "COMPUTER WINS", "UNO", JOptionPane.PLAIN_MESSAGE);
				//Boolean changed to false if the computer wins
				winner = false; 
				break; //loop exited, game over
			} 
		} while(computerSpaces!=0 && userSpaces!=0); //loop repeats while both players still have cards
		
		//Calls getScore method
		getScore(USERNAME, winner); 
		
	}

}


