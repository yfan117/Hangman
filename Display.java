/* Created by: Yulong Fan
 * 
 * Display.java
 * graphic user interface for this mini-game
 * 
 * required files:
 * HANG1.png 
 * HANG2.png
 * HANG3.png
 * HANG4.png
 * HANG5.png
 * HANG6.png
 * HANG7.png
 * HANG8.png
 * tryButton.png
 * reButton.png
 * dictionary.txt
 * Hangman.java
 * 
 * last edited: 02/23/2020 
 */

//package hangman;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;



public class Display extends JFrame{
	//this object is used to communicate between interface and main class
	Hangman game = new Hangman();
	
	//window size
	private int width = 1646;
	private int height = 1080;
	
	private static boolean isEnd = false;
	
	private Font font = new Font("SansSerif", Font.BOLD, 150);
	
	private JFrame frame = new JFrame ("Hang Man");
	private JLayeredPane panel ;		
	private JButton mainImage ;
	private JLabel txtBox ;
	private JLabel usedCharBox ;
	private final JLabel creatorName = new JLabel ("Created by: Yulong Fan");
	private JTextField inputBox ;
	
	public Display(String imageName, String maskedWord, String usedChar)
	{
		//initialize frame settings 
		isEnd = false;
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		//create default display
		createDisplay( imageName, maskedWord, usedChar);

	}
	
	public void setEnd(boolean status)
	{
		//determines when the re-try button appears
		isEnd = status;
	}
	

	public void createDisplay(String imageName, String maskedWord, String usedChar)
	{
		//JPanel and its components are all removed and recreated every time display updates
		panel = new JLayeredPane();		
		panel.setVisible(true);
		
		//imageName is .PNG String that determines which background image to display
		mainImage = new JButton(new ImageIcon(imageName));
		mainImage.setBounds(-5, -5, width, height);
		panel.add(mainImage);
				
		//maskedWord displays the correct letter guessed
		//if the round ends, maskedWord reveal the actual vocab
		txtBox = new JLabel(maskedWord);
		txtBox.setFont(font);
		txtBox.setHorizontalAlignment(JLabel.CENTER);
		txtBox.setBounds(600, 200, 1000, 200);
		txtBox.setForeground(Color.WHITE);
		panel.add(txtBox, new Integer(1));
		
		//creator signature
		creatorName.setBounds(1400, 950, 200, 10);
		creatorName.setForeground(Color.WHITE);
		panel.add(creatorName, new Integer(1));
		
		
		if(isEnd == false)
		{
			//display incorrectly guess letters
			//disappears when the round ends
			usedCharBox = new JLabel(usedChar);
			usedCharBox.setFont(font);
			usedCharBox.setHorizontalAlignment(JLabel.CENTER);
			usedCharBox.setBounds(600, 400, 1000, 200);
			usedCharBox.setForeground(Color.WHITE);
			panel.add(usedCharBox, new Integer(1));
			
		}
		
		//user input box
		//can enter multiple letters, but only the first one will be taken
		inputBox = new JTextField(1);
		inputBox.setFont(font);
		inputBox.setHorizontalAlignment(JTextField.CENTER);
		inputBox.setBounds(800, 669, 981-800, 226);
		panel.add(inputBox, new Integer(1));
		
		JButton tryButton = new JButton(new ImageIcon ("tryButton.png"));
		if (isEnd == false)
		{
			//only appears when the round has not end
			tryButton.setBounds(981, 669, 430, 226);
			panel.add(tryButton, new Integer(1));
		}
				
		JButton reButton = new JButton(new ImageIcon ("reButton.png"));
		if (isEnd == true)
		{			
			//only appears when the round ends
			reButton.setBounds(981, 669, 430, 226);
			panel.add(reButton, new Integer(1));
		}
		
		
		
		if (isEnd == false)
		{
				//only works when the round has not end
				tryButton.addActionListener(new ActionListener() {
		
					@Override
					public void actionPerformed(ActionEvent e) {
						
						//JPanel components are removed
						frame.remove(panel);
						frame.remove(mainImage);
						frame.remove(inputBox);
						frame.remove(txtBox);
						frame.remove(usedCharBox);
						
						String userInput = inputBox.getText();
						
						//if button is clicked without input
						//this class return 0, which will be ignored
						if(userInput.length()>0)
						{	
							System.out.println(userInput);
							char input = userInput.charAt(0);
						
							game.updateGame(input);
						}
						else
						{
							char temp ='0';
							game.updateGame(temp);
						}
					
					}

				 }
			 );
		}
		if (isEnd == true)
			{
				//only works when the round ends
				reButton.addActionListener(new ActionListener() {
	
					@Override
					public void actionPerformed(ActionEvent e) {
						
						//JPanel components are removed
						frame.remove(panel);
						frame.remove(mainImage);
						frame.remove(inputBox);
						frame.remove(txtBox);
						frame.remove(usedCharBox);
						
						//initiate restart process
						game.reStart();
					
				
					}
	
			
				}
						);
			
			}
		
		frame.add(panel);
		
		frame.validate();
		
	}
	

}
