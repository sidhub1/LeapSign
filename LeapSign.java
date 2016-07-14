import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.leapmotion.leap.*;

/****
 * 
 * @author Team Explore The LeapSign Class contains the main method, which
 *         instantiates the controller, and adds a listener from the
 *         LeapSignListener class.
 *
 */
class LeapSign {

	private static JFrame jframe;
	private static JPanel jpanel;
	private static BufferedImage[] images;
	private static LeapSignListener listener;
	private static String[] toolNames;

	public static void main(String[] args) {
		listener = new LeapSignListener();
		int numOfTools = 6;
		images = new BufferedImage[numOfTools];
		toolNames = new String[numOfTools];
		try {
			images[0] = ImageIO.read(new File("src/bonecutter.jpg"));
			images[1] = ImageIO.read(new File("src/cannula.jpg"));
			images[2] = ImageIO.read(new File("src/forceps.jpg"));
			images[3] = ImageIO.read(new File("src/Scalpel.jpg"));
			images[4] = ImageIO.read(new File("src/scissors.jpg"));
			images[5] = ImageIO.read(new File("src/trocar.jpg"));
			
			toolNames[0] = "Bone Cutter";
			toolNames[1] = "Cannula";
			toolNames[2] = "Forceps";
			toolNames[3] = "Scalpel";
			toolNames[4] = "Scissors";
			toolNames[5] = "Trocar";
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		/***
		 * GUI with JFrame and JPanel
		 */
		jframe = new JFrame("Discover GUI");
		jpanel = new JPanel();
		

		java.awt.Color bg = java.awt.Color.cyan;
		jpanel.setBackground(bg);
		// Keep this process running until Enter is pressed
		setHomeScreen();

		Controller controller = new Controller();
		controller.addListener(listener);
		System.out.println("Press Enter to quit...");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String result = listener.getOption();
		System.out.println(result);
		controller.removeListener(listener);
	}

	private static void exploreClicked(LeapSignListener listener,
			JFrame jframe, BufferedImage[] images) {
		// TODO Auto-generated method stub

		//System.out.println("You clicked Explore!");
		String result = listener.getOption();
		try
		{
			Thread.sleep(750);
		}
		catch(InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
		//System.out.println("result: " + result);
		System.out.println("YOU CHOSE EXPLORE AND SELECTED THE TOOLS OPTION!");
		result = "TOOLS";
		int gridSize = 4;
		if (result != null) {
			if (result.equals("TOOLS")) {
				
				jframe.setVisible(false);
				JFrame jf = new JFrame("Explore GUI");
				jf.setVisible(true);
				jf.setSize(850, 400);
				jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jf.getContentPane().setLayout(new GridLayout(4, 2, 10, 10));
				
				
				int counter = 0;
				for (int i = 0; i < 2; i++) {
					for (int j = 0; j < 3; j++) {
						JLabel jlabel = new JLabel();
						jlabel.setIcon(new ImageIcon(images[counter]));
						jf.add(jlabel);
						counter++;
					}
				}
				
				JButton back = new JButton("BACK");
				back.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						setHomeScreen();
					}
					
				});
				jf.add(back);
				
				JButton setTool = new JButton("HOLD AND SET YOUR TOOL");
				setTool.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Select Tool");
						
						Integer tool = listener.getTool();
						while (tool == null){
							tool = listener.getTool();
						}
						try
						{
							Thread.sleep(750);
						}
						catch(InterruptedException ex)
						{
							Thread.currentThread().interrupt();
						}
						tool = 3;
						System.out.println("Tool: " + tool);
						JDialog toolDialog = new JDialog();
						
						toolDialog.setSize(300, 300);
						toolDialog.setTitle("You Selected a Tool");
						toolDialog.setVisible(true);
						JLabel toolText = new JLabel("				      "
								+ "     Tool Selected: " + toolNames[tool]);
						toolText.setSize(300, 200);
						toolDialog.add(toolText);
					}
					
				});
				jf.add(setTool);

			}

		}
	}
	
	public static void setHomeScreen() {
		jframe.setVisible(true);
        jframe.setSize(800, 400);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.getContentPane().setLayout(new GridLayout(3, 0));
        
        JButton jExplore = new JButton("Explore");
        jExplore.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				exploreClicked(listener, jframe, images);
			}

			
        });
        jframe.add(jExplore);
        jframe.add(new JLabel("Use the EXPLORE feature to practice on the human body."));
        JButton jCollaborate = new JButton("Collaborate");
        jCollaborate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("You clicked Collaborate!");
				
			}
        });
        jframe.add(jCollaborate);
        jframe.add(new JLabel("In the COLLABORATE feature, communicate with surgeons. "));
        JButton jCreate = new JButton("Create");
        jCreate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("You clicked Create!");
			}
        });
        jframe.add(jCreate);
        jframe.add(new JLabel("Add to our database of body parts and tools."));
	}

}

/***
 * 
 * @author Team Explore The LeapSignListener class handles all the movements of
 *         the user's hands.
 * 
 */
class LeapSignListener extends Listener {
	int i;
	private String sentence;
	private String option;
	private int tool;

	/***
	 * @param controller
	 *            The Method OnConnect prints a statement if the controller has
	 *            successfully connected.
	 */
	public void onConnect(Controller controller) {
		System.out.println("Connected");
	}

	public String getOption() {
		return option;
	}
	public int getTool(){
		return tool;
	}

	/**
	 * The method getSentence returns the sentence of Strings where all letters
	 * are stored
	 * 
	 * @return sentence
	 */
	public String getSentence() {
		return sentence;
	}

	/**
	 * @param controller
	 *            The onFrame method is called several times every second, and
	 *            here, all main code is written. This class makes a call to the
	 *            LetterChooser class, after assigning all fingers on the frame
	 *            to a local variable.
	 */
	public void onFrame(Controller controller) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Frame frame = controller.frame();
		HandList hands = frame.hands();
		Hand hand = hands.get(0);
		FingerList fingers = hand.fingers();
		Finger indexFinger = null;
		Finger thumbFinger = null;
		Finger middleFinger = null;
		Finger pinkyFinger = null;
		Finger ringFinger = null;
		for (Finger finger : fingers) {
			if ((finger.type() + "").equals("TYPE_INDEX")) {
				indexFinger = finger;
			}
			if ((finger.type() + "").equals("TYPE_THUMB")) {
				thumbFinger = finger;
			}
			if ((finger.type() + "").equals("TYPE_MIDDLE")) {
				middleFinger = finger;
			}
			if ((finger.type() + "").equals("TYPE_PINKY")) {
				pinkyFinger = finger;
			}
			if ((finger.type() + "").equals("TYPE_RING")) {
				ringFinger = finger;
			}
		}

		LetterChooser lc = new LetterChooser(thumbFinger, indexFinger,
				middleFinger, ringFinger, pinkyFinger);
		option = lc.selectOption();
		tool = lc.getTool();
		// System.out.println(option);
		/*
		 * if(i%4 ==0) sentence+=letter; System.out.println(sentence);
		 */
	}
}
