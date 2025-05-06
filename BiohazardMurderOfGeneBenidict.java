//https://github.com/anaygoyal09/BioHazard
//Arsh Abhinkar, Anay Goyal, Period 5
//BiohazardMurderOfGeneBenidict.java
//4/12/25
/* 
Main Class: BiohazardMurderOfGeneBenidict

	1. Main Method:
	2. Create an instance of BiohazardMurderOfGeneBenidict.
	3. Call createFrame().
	4. createFrame():
	5. Create a JFrame with title "Biohazard: The Murder of Gene Benidict".
	6. Set size, close operation, and other properties.
	7. Add an instance of BiohazardMurderOfGeneBenidictHolder to the frame.
	8. Make the frame visible.


Class: BiohazardMurderOfGeneBenidictHolder

	1. Constructor:
	2. Set background color and layout to CardLayout.
	3. Create instances of various cards (firstCard, secondCard, clue1, etc.).
	4. Add these cards to the layout with unique identifiers.
	5. Initialize boolean variables for tracking clues.


Class: firstCard

	1. Constructor:
	2. Add mouse listeners for interaction.
	3. Initialize animations and images (e.g., police lights, title image, start button).
	4. Start hover animation and black screen transition.
	5. blackScreen():
	6. Start timers for black screen and title fade-in.
	7. retrieveImage():
	8. Load images for the title and start button.
	9. startHoverAnimation():
	10. Start a timer to animate the hover effect on the start button.
	11. paintComponent():
	12. Draw the background, title, and start button with transparency and animations.
	13. Mouse Events:
	14. Handle hover, click, and release events for the start button.
	15. Navigate to the next card when the start button is clicked.


Class: secondCard

	1. Constructor:
	2. Load and scale the forensics report image.
	3. Add a button to navigate to the clue board.
	4. Add hover and pulse animations for the button.

week 2: 
Class: thirdCard

	1. Constructor:
	2. Add mouse listeners for interaction.
	3. Load images for the murder board and start clip.
	4. Start a timer for transitioning from the clip to the murder board.
	5. paintComponent():
	6. Draw the murder board and highlight clues when hovered or clicked.
	7. Mouse Events:
	8. Handle hover and click events for clues.
	9. Navigate to the corresponding clue card when a clue is clicked.


Classes: clue1, clue2, ..., clue7

	1. Constructor:
	2. Add a button to navigate back to the forensics report.
	3. Add a button to grade the answer and mark the clue as correct.


Class: forensicsReport

	1. Constructor:
	2. Load and display the forensics report image.
	3. Add a button to navigate back to the last clue.
 */
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import java.awt.GridLayout;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;	
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JSlider;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class BiohazardMurderOfGeneBenidict
{
	public static void main(String[] args)
	{
		BiohazardMurderOfGeneBenidict bmogb = new BiohazardMurderOfGeneBenidict();
		bmogb.createFrame();
	}

	public void createFrame()
	{
		JFrame frame = new JFrame("Biohazard: The Murder of Gene Benidict");
		frame.setSize(1300, 800);				
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		BiohazardMurderOfGeneBenidictHolder bmogbh = new BiohazardMurderOfGeneBenidictHolder();
		frame.getContentPane().add(bmogbh);
		frame.setVisible(true);
	}
}

class BiohazardMurderOfGeneBenidictHolder extends JPanel
{
	boolean startClip;
	String goingBacktoClue;
	protected boolean correct1, correct2, correct3, correct4, correct5, correct6, correct7;

	public BiohazardMurderOfGeneBenidictHolder()
	{
		setBackground(Color.BLACK);
		CardLayout cards = new CardLayout();
		setLayout(cards);

		firstCard title = new firstCard(this, cards);
		secondCard report = new secondCard(this, cards);
		thirdCard question1 = new thirdCard(this, cards);
		clue1 c1 = new clue1(this, cards);
		clue2 c2 = new clue2(this,cards);
		clue3 c3 = new clue3(this,cards);
		clue4 c4 = new clue4(this,cards);
		clue5 c5 = new clue5(this,cards);
		clue6 c6 = new clue6(this,cards);
		clue7 c7 = new clue7(this,cards);
		forensicsReport report2 = new forensicsReport(this, cards);
		correct1 = correct2 = correct3 = correct4 = correct5 = correct6 = correct7 = false;




		add(title, "title");
		add(report, "report");
		add(question1, "clueBoard");
		add(c1, "clue1");
		add(c2, "clue2");
		add(c3, "clue3");
		add(c4, "clue4");
		add(c5, "clue5");
		add(c6, "clue6");
		add(c7, "clue7");
		add(report2, "forensicsReport");

		startClip = false;
	}

	class firstCard extends JPanel implements MouseListener, MouseMotionListener
	{
		Image policeLights, titleImage, startButton;
		Timer black, showTitle, fadeTimer, checkTime, hoverTimer;
		boolean changeBlack, showImage, startReady, entered, hoveringStart, clickingStart, pulseOut;
		float titleTransparency, startTransparency, pulseTransparency;
		int startX, startY, startW, startH, targetW, targetH, clickOffset;
		double scale, minScale, maxScale, hoverScale, clickScale, breathingSpeed, snapSpeed;
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;

		public firstCard(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			addMouseListener(this);
			addMouseMotionListener(this);
			panelCards = panelCardsIn;
			cards = cardsIn;
			hoveringStart = clickingStart = pulseOut = false;
			pulseOut = true;
			clickOffset = 5; //how much to move the button when pressed
			scale = 1.0;
			minScale = 0.95;
			maxScale = 1.05;
			hoverScale = 1.1;
			clickScale = 0.9;
			breathingSpeed = 0.003;
			snapSpeed = 0.02;
			pulseTransparency = 1.0f;  //for breathing brightness
			startX = 525;
			startY = 660;
			startW = targetW = 250;
			startH = targetH = 75;
			ImageIcon storeGif = new ImageIcon("policeLightsAni.gif");        
			policeLights = storeGif.getImage();
			startHoverAnimation();
			retrieveImage();
			blackScreen();
		}

		public void blackScreen()
		{
			timerBlackHandler tbh = new timerBlackHandler();
			showTitleTimerHandler stth = new showTitleTimerHandler();
			checkTimeTimerHandler ctth = new checkTimeTimerHandler();
			changeBlack = showImage = startReady = entered = false;
			titleTransparency = startTransparency = 0f;
			black = new Timer(3500, tbh);
			showTitle = new Timer(2000, stth);
			checkTime = new Timer(3000, ctth);
			black.start();
		}

		public void retrieveImage()
		{
			try
			{
				titleImage = ImageIO.read(new File("titlePicture.jpg"));
				startButton = ImageIO.read(new File("startButtons.png"));
			}

			catch(IOException e)
			{
				System.err.println("\n\n\nERROR IN RETREIVING IMAGE\n\n\n");
				e.printStackTrace();
			}
		}

		public void startHoverAnimation()
		{
			hoverTimerHandler hth = new hoverTimerHandler();
			hoverTimer = new Timer(30, hth);
			hoverTimer.start();
		}

		class hoverTimerHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				double tolerance = 0.005;

				if(!hoveringStart && !clickingStart)
				{
					if(pulseOut)
					{
						scale += breathingSpeed;

						if(scale >= maxScale)
							pulseOut = false;
					}

					else
					{
						scale -= breathingSpeed;

						if(scale <= minScale)
							pulseOut = true;
					}

					float alphaRange = 0.3f; //how much darker it can go
					float scaleRange = (float)(maxScale - minScale);
					float normalized = (float)((scale - minScale) / scaleRange);
					pulseTransparency = 0.5f + (normalized * alphaRange);  //goes between 0.5 and 1.0 (50% - 100% transparency)
				}

				else if(hoveringStart && !clickingStart)
				{
					if(Math.abs(scale - hoverScale) > tolerance)
						scale += (hoverScale - scale) * 0.1;

					else
						scale = hoverScale;

					pulseTransparency = 1.0f;  //no transparency
				}

				else if (clickingStart)
				{
					if(Math.abs(scale - clickScale) > tolerance)
						scale += (clickScale - scale) * 0.1;

					else
						scale = clickScale;

					pulseTransparency = 1.0f; //no transparency
				}

				targetW = (int)(startW * scale);
				targetH = (int)(startH * scale);

				repaint();
			}
		}

		class checkTimeTimerHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				startReady = true;
				titleTransparency = 0f;
				fadeTimer.restart();
				startFadeIn();
				repaint();
			}
		}

		class timerBlackHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				changeBlack = true;
				repaint();
				black.stop();
				showTitle.start();
			}
		}

		class showTitleTimerHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				showImage = true;
				repaint();
				showTitle.stop();
				startFadeIn();
			}
		}

		class fadeTimerHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				if(!startReady)
				{
					titleTransparency += 0.05f;

					if(titleTransparency >= 1f)
					{
						titleTransparency = 1f;
						fadeTimer.stop();
						startReady = true;
						startFadeIn();

					}
				} 

				else
				{
					startTransparency += 0.05f;

					if(startTransparency >= 1f)
					{
						startTransparency = 1f;
						fadeTimer.stop();
					}
				}
				repaint();
			}
		}

		public void startFadeIn()
		{
			fadeTimerHandler fth = new fadeTimerHandler();
			fadeTimer = new Timer(150, fth);
			fadeTimer.start();
		}

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			g.drawImage(policeLights, -100, 0, 1500, 1075, this);

			if(changeBlack)
				g.fillRect(0, 0, 1300, 800);

			if(showImage && changeBlack)
			{
				Graphics2D g2d = (Graphics2D)(g);

				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, titleTransparency));
				g2d.drawImage(titleImage, 250, 40, 800, 600, this);

				if(startReady)
				{
					int drawX = startX - ((targetW - startW) / 2);
					int drawY = startY - ((targetH - startH) / 2);

					float pulse = startTransparency * pulseTransparency;
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, pulse));

					int imgHeight = startButton.getHeight(this) / 2; //half height
					int imgWidth = startButton.getWidth(this);
					int srcY1 = 0; //show top image when idle or hovering
					int srcY2 = imgHeight;

					if(clickingStart) //show bottom image when clicking
					{
						srcY1 = imgHeight;
						srcY2 = startButton.getHeight(this);
					}

					g2d.drawImage(startButton, drawX, drawY, (drawX + targetW), drawY + targetH, 0, srcY1, imgWidth, srcY2, this);

					if(clickingStart)
					{
						g2d.setColor(new Color(0, 0, 0, 80));
						g2d.fillRect(drawX, drawY, targetW, targetH);
					}
				}
			}
		}

		public void mouseMoved(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= startX && x <= (startX + targetW) && y >= startY && y <= (startY + targetH))
				hoveringStart = true;

			else
				hoveringStart = false;
		}

		public void mousePressed(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= startX && x <= (startX + targetW) && y >= startY && y <= (startY + targetH)) // Only move if clicking the start button
			{
				clickingStart = true;
				startX += clickOffset;
				startY += clickOffset;
				repaint();
			}
		}

		public void mouseReleased(MouseEvent e)
		{
			if (clickingStart) {
				startX -= clickOffset; //reset button position
				startY -= clickOffset;
				clickingStart = false;
				repaint();
			}
		}

		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= startX && x <= (startX + targetW) && y >= startY && y <= (startY + targetH) && startReady && showImage && changeBlack) //show next card when clicked
				cards.show(panelCards, "report");

			if(x >= 0 && x <= 10 && y >= 0 && y <= 10)
				cards.show(panelCards, "clueBoard");

		}

		public void mouseExited(MouseEvent e)
		{
			hoveringStart = false;
			clickingStart = false;
			scale = 1.0;
			repaint();
		}

		public void mouseEntered(MouseEvent e) {}
		public void mouseDragged(MouseEvent e) {}
	}

	class secondCard extends JPanel
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;
		thirdCard clip;
		BufferedImage fullImage = null;

		public secondCard(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			setLayout(new BorderLayout());

			panelCards = panelCardsIn;
			cards = cardsIn;
			clip = new thirdCard(panelCards, cards);

			// Load the original image for the forensics image
			ImageIcon originalIcon = new ImageIcon("coronersReport.png");
			int scrollPaneWidth = 1300;
			int scaledHeight = (originalIcon.getIconHeight() * scrollPaneWidth) / originalIcon.getIconWidth();
			Image scaledImage = originalIcon.getImage().getScaledInstance(scrollPaneWidth, scaledHeight, Image.SCALE_SMOOTH);
			ImageIcon scaledIcon = new ImageIcon(scaledImage);
			JLabel forensicsLabel = new JLabel(scaledIcon);

			// Load the full image for the button
			try
			{
				fullImage = ImageIO.read(new File("beginMurderButtons.png"));
			}

			catch (IOException e)
			{
				System.err.println("\n\n\nERROR IN RETRIEVING IMAGE\n\n\n");
				e.printStackTrace();
			}

			JLayeredPane layeredPane = new JLayeredPane();
			layeredPane.setPreferredSize(new Dimension(scrollPaneWidth, scaledHeight));

			// Add the forensics image to the layered pane
			forensicsLabel.setBounds(0, 0, scrollPaneWidth, scaledHeight);
			layeredPane.add(forensicsLabel, Integer.valueOf(0));

			int width = fullImage.getWidth();  //crop the top half of the button image
			int height = fullImage.getHeight() / 2;
			BufferedImage croppedImage = fullImage.getSubimage(0, 0, width, height);

			Image scaleImage = croppedImage.getScaledInstance(400, 65, Image.SCALE_SMOOTH);
			ImageIcon scaledReturnIcon = new ImageIcon(scaleImage);

			// Create the button with the cropped image
			JButton startGameButton = new JButton(scaledReturnIcon);
			int originalWidth = 400;
			int originalHeight = 65;
			int hoverWidth = 430;
			int hoverHeight = 75;

			class startGameButtonHandler implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					cards.show(panelCards, "clueBoard");
					startClip = true;
					clip.repaint();
				}
			}

			int buttonX = (scrollPaneWidth / 2) - (originalWidth / 2);
			int buttonY = scaledHeight - 125;
			startGameButton.setBounds(buttonX, buttonY, originalWidth, originalHeight);
			startGameButton.setBorderPainted(false);
			startGameButton.setContentAreaFilled(false);
			startGameButton.setFocusPainted(false);
			layeredPane.add(startGameButton, Integer.valueOf(1));
			startGameButtonHandler sgbh = new startGameButtonHandler();
			startGameButton.addActionListener(sgbh); //button Action to switch the card

			Timer pulseTimer = new Timer(100, null);
			int[] pulseDirection = {1}; //1 = grow, -1 = shrink
			int[] pulseSize = {0};

			class pulseTimerHandler implements ActionListener
			{
				public void actionPerformed(ActionEvent evt)
				{
					pulseSize[0] += pulseDirection[0];

					if(pulseSize[0] >= 10 || pulseSize[0] <= 0)
						pulseDirection[0] *= -1;

					int newW = originalWidth + pulseSize[0];
					int newH = originalHeight + pulseSize[0] / 2;

					Image scaledImg = croppedImage.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
					startGameButton.setIcon(new ImageIcon(scaledImg));
					int newX = buttonX - (newW - originalWidth) / 2;
					int newY = buttonY - (newH - originalHeight) / 2;
					startGameButton.setBounds(newX, newY, newW, newH);
				}
			}

			pulseTimerHandler pth = new pulseTimerHandler();
			pulseTimer.addActionListener(pth);
			pulseTimer.start();

			class buttonHandler implements MouseListener
			{
				Timer expandTimer, shrinkTimer;
				ImageIcon currentIcon = scaledReturnIcon;

				class hoverTimerHandler implements ActionListener
				{
					int currentWidth = startGameButton.getWidth();
					int currentHeight = startGameButton.getHeight();

					public void actionPerformed(ActionEvent evt) {
						if(currentWidth >= hoverWidth && currentHeight >= hoverHeight)
							expandTimer.stop();

						else
						{
							currentWidth += 3;
							currentHeight += 1;

							Image scaledImg = croppedImage.getScaledInstance(currentWidth, currentHeight, Image.SCALE_SMOOTH);
							currentIcon = new ImageIcon(scaledImg);
							startGameButton.setIcon(currentIcon);

							int newX = buttonX - ((currentWidth - originalWidth) / 2);
							int newY = buttonY - ((currentHeight - originalHeight) / 2);
							startGameButton.setBounds(newX, newY, currentWidth, currentHeight);
						}
					}
				}

				hoverTimerHandler hth = new hoverTimerHandler();

				public void mouseEntered(MouseEvent e)
				{
					pulseTimer.stop(); // stop pulsing when hovered

					if(shrinkTimer != null && shrinkTimer.isRunning())
						shrinkTimer.stop();

					expandTimer = new Timer(10, hth);
					expandTimer.start();
				}

				class shrinkTimerHandler implements ActionListener
				{
					int currentWidth = startGameButton.getWidth();
					int currentHeight = startGameButton.getHeight();

					public void actionPerformed(ActionEvent evt)
					{
						if(currentWidth <= originalWidth && currentHeight <= originalHeight)
						{
							shrinkTimer.stop();
							currentIcon = scaledReturnIcon;
							startGameButton.setIcon(currentIcon);
							startGameButton.setBounds(buttonX, buttonY, originalWidth, originalHeight);
							pulseTimer.start(); //resume pulse when exited
						}

						else
						{
							currentWidth -= 3;
							currentHeight -= 1;

							Image scaledImg = croppedImage.getScaledInstance(currentWidth, currentHeight, Image.SCALE_SMOOTH);
							currentIcon = new ImageIcon(scaledImg);
							startGameButton.setIcon(currentIcon);

							int newX = buttonX - (currentWidth - originalWidth) / 2;
							int newY = buttonY - (currentHeight - originalHeight) / 2;
							startGameButton.setBounds(newX, newY, currentWidth, currentHeight);
						}
					}
				}

				public void mouseExited(MouseEvent e)
				{
					if(expandTimer != null && expandTimer.isRunning())
						expandTimer.stop();

					shrinkTimerHandler sth = new shrinkTimerHandler();
					shrinkTimer = new Timer(10, sth);
					shrinkTimer.start();
				}

				public void mousePressed(MouseEvent e)
				{
					//crop to bottom half when pressed
					BufferedImage croppedBottomHalf = fullImage.getSubimage(0, (fullImage.getHeight() / 2) - 10, fullImage.getWidth(), (fullImage.getHeight() / 2) - 10);

					//show bottom half of image when pressed
					Image scaledBottomImage = croppedBottomHalf.getScaledInstance(400, 65, Image.SCALE_SMOOTH);
					BufferedImage bufferedBottomImage = new BufferedImage(400, 65, BufferedImage.TYPE_INT_ARGB);
					Graphics2D g2 = bufferedBottomImage.createGraphics();
					g2.drawImage(scaledBottomImage, 0, 0, null);

					RescaleOp op = new RescaleOp(0.6f, 0, null); //dark effect and add the darker image
					op.filter(bufferedBottomImage, bufferedBottomImage); 

					ImageIcon darkIcon = new ImageIcon(bufferedBottomImage);
					startGameButton.setIcon(darkIcon);

					int pressedWidth = startGameButton.getWidth(); //adjust position when pressed
					int pressedHeight = startGameButton.getHeight();
					int pressedX = startGameButton.getX() + 5;
					int pressedY = startGameButton.getY() + 2;
					startGameButton.setBounds(pressedX, pressedY, pressedWidth, pressedHeight);
				}

				public void mouseReleased(MouseEvent e)
				{
					startGameButton.setIcon(currentIcon); //restore image
					startGameButton.setBounds((startGameButton.getX() - 5), (startGameButton.getY() - 2), startGameButton.getWidth() + 10, startGameButton.getHeight() + 4);
				}

				public void mouseClicked(MouseEvent e) {}
			}

			buttonHandler bh = new buttonHandler(); //hover and press effect
			startGameButton.addMouseListener(bh);

			JScrollPane forensicsScroll = new JScrollPane(layeredPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			JScrollBar verticalBar = forensicsScroll.getVerticalScrollBar();
			forensicsScroll.setPreferredSize(new Dimension(scrollPaneWidth, 800));
			forensicsScroll.setBorder(null);

			verticalBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI()
			{
				Color thumbColor = new Color(110, 0, 0);
				Color trackColor = new Color(169, 169, 169);

				public void paintThumb(Graphics g, JComponent c, Rectangle r)
				{
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2.setColor(thumbColor);
					g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);
					g2.dispose();
				}

				public void paintTrack(Graphics g, JComponent c, Rectangle r)
				{
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setColor(trackColor);
					g2.fillRect(r.x, r.y, r.width, r.height);
				}

				public JButton createDecreaseButton(int orient)
				{
					return makeButton();
				}

				public JButton createIncreaseButton(int orient)
				{
					return makeButton();
				}

				public JButton makeButton()
				{
					JButton button = new JButton();
					button.setPreferredSize(new Dimension(0, 0));
					button.setMinimumSize(new Dimension(0, 0));
					button.setMaximumSize(new Dimension(0, 0));
					button.setOpaque(false);
					button.setContentAreaFilled(false);
					button.setBorderPainted(false);
					return button;
				}
			});

			add(forensicsScroll, BorderLayout.CENTER);
		}
	}

	class thirdCard extends JPanel implements MouseListener, MouseMotionListener
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;
		Image startGameClip, murderBoard;
		Timer blackScreenTimer, fadeTimer;
		boolean blackScreen, startClip;
		boolean[] clueHovered, cluePressed;
		float boardTransparency;

		public thirdCard(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			addMouseListener(this);
			setFocusable(true);
			addMouseMotionListener(this);
			panelCards = panelCardsIn;
			cards = cardsIn;
			blackScreen = false;
			startClip = true; //start with the clip
			boardTransparency = 0.0f;
			clueHovered = new boolean[8];
			cluePressed = new boolean[8];

			for(int i = 0; i < clueHovered.length; i++)
			{
				clueHovered[i] = false;
				cluePressed[i] = false;
			}

			blackScreenTimerHandler bsth = new blackScreenTimerHandler();
			blackScreenTimer = new Timer(8300, bsth); //when the black screen shows

			ImageIcon storeGif = new ImageIcon("startMurderIntroClip.gif");        
			startGameClip = storeGif.getImage();	


			retrieveImage();
		}

		public void retrieveImage()
		{
			try
			{
				murderBoard = ImageIO.read(new File("pressClues.png"));
			}
			catch(IOException e)
			{
				System.err.println("\n\n\nERROR IN RETRIEVING IMAGE\n\n\n");
				e.printStackTrace();
			}
		}

		class blackScreenTimerHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				blackScreen = true;
				startClip = false;
				blackScreenTimer.stop();
				repaint();

				helpFadeTimerHandler th = new helpFadeTimerHandler();
				Timer helpFadeTimer = new Timer(1000, th);
				helpFadeTimer.start();
			}
		}

		class helpFadeTimerHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				startFadeIn();
			}
		}

		class fadeTimerHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				boardTransparency += 0.05f;

				if (boardTransparency >= 1f)
				{
					boardTransparency = 1f;
					fadeTimer.stop();
				}
				repaint();
			}
		}

		public void startFadeIn()
		{
			fadeTimerHandler fth = new fadeTimerHandler();
			fadeTimer = new Timer(150, fth);
			fadeTimer.start();
		}

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)(g);
			int[] xShape4 = {159, 160, 166, 167, 173, 173, 217, 217, 224, 318, 318, 275, 247, 247, 223, 223, 180, 180};
			int[] yShape4 = {368, 411, 411, 430, 437, 517, 517, 529, 529, 490, 477, 361, 361, 354, 354, 361, 361, 368};
			int[] xShape7 = {87, 87, 80, 80, 73, 73, 66, 66, 88, 88, 138, 138, 182, 203, 203, 167, 167, 117, 117};
			int[] yShape7 = {533, 560, 560, 601, 601, 640, 640, 683, 683, 690, 690, 697, 696, 584, 547, 547, 540, 540, 533};

			if(startClip)
			{
				g.drawImage(startGameClip, 0, 0, 1310, 800, this);
				blackScreenTimer.start();
			}

			else if(blackScreen)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 1300, 800);
			}




			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, boardTransparency));
			g2d.drawImage(murderBoard, 0, -40, 1300, 820, this);


			g2d.setColor(new Color(0, 0, 0, 80));

			if(clueHovered[0]) //ADD && BLACKSCREEN LATER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! FOR MAKING IT BETTER
				g2d.fillRect(108, 189, 117, 147);



			else if(clueHovered[1])
				g2d.fillRect(563, 136, 122, 120);

			else if(clueHovered[2])
			{
				g2d.fillRect(908, 168, 101, 88);
				g2d.fillRect(1009, 142, 116, 116);
				g2d.fillRect(1000, 257, 110, 39);
			}

			else if(clueHovered[3])
				g2d.fillPolygon(xShape4, yShape4, 18);

			else if(clueHovered[4])
				g2d.fillPolygon(xShape7, yShape7, 19);


			else if(clueHovered[5])
			{
				int[] eightX = {209, 209, 217, 217, 239, 239,266, 266,275, 275,286, 408, 395, 391,391, 383,383, 371, 371, 362,362, 355, 355, 347, 347      ,333, 333,325,325,277, 277,      261, 261};
				int[] eightY = {560, 663, 663, 680, 680, 695, 695, 721,721, 744 ,780 ,740 ,721 ,721 ,701 ,701 ,688 ,688 ,662 ,662 ,633 ,633 ,620 ,620 ,568 ,568, 547, 547,562, 562,575, 575 ,560};
				g2d.fillPolygon(eightX,eightY ,33);
			}
			else if(clueHovered[6])
			{

				g2d.fillRect(980, 554, 152, 99);

			}


			//----------------------------------------------------------------------------

			g2d.setColor(new Color(100, 0, 0, 80));

			if(cluePressed[0])
			{
				g2d.fillRect(108, 189, 117, 147);

			}


			else if(cluePressed[1])
			{
				g2d.fillRect(563, 136, 122, 120);

			}

			else if(cluePressed[2])
			{
				g2d.fillRect(908, 168, 101, 88);
				g2d.fillRect(1009, 142, 116, 116);
				g2d.fillRect(1000, 257, 110, 39);


			}

			else if(cluePressed[3])
			{
				g2d.setColor(new Color(0, 100, 0, 80));
				g2d.fillPolygon(xShape4, yShape4, 18);

			}




			else if(cluePressed[4])
			{
				g2d.fillPolygon(xShape7, yShape7, 19);
				cards.show(panelCards, "clue5");
			}
			else if(cluePressed[5])
			{
				int[] eightX = {209, 209, 217, 217, 239, 239,266, 266,275, 275,286, 408, 395, 391,391, 383,383, 371, 371, 362,362, 355, 355, 347, 347,333, 333,325,325,277, 277,      261, 261};
				int[] eightY = {560, 663, 663, 680, 680, 695, 695, 721,721, 744 ,780 ,740 ,721 ,721 ,701 ,701 ,688 ,688 ,662 ,662 ,633 ,633 ,620 ,620 ,568 ,568, 547, 547,562, 562,575, 575 ,560};
				g2d.fillPolygon(eightX,eightY ,33);


			}
			else if(cluePressed[6])
			{
				g2d.fillRect(980, 554, 152, 99);

			}
			if(correct1)
			{
				g2d.setColor(new Color(0, 100, 0, 80));
				g2d.fillRect(108, 189, 117, 147);
			}
			if(correct2)
			{
				g2d.setColor(new Color(0, 100, 0, 80));
				g2d.fillRect(563, 136, 122, 120);
			}
			if(correct3)
			{
				g2d.setColor(new Color(0, 100, 0, 80));
				g2d.fillRect(908, 168, 101, 88);
				g2d.fillRect(1009, 142, 116, 116);
				g2d.fillRect(1000, 257, 110, 39);
			}
			if(correct4)
			{
				g2d.setColor(new Color(0, 100, 0, 80));
				g2d.fillPolygon(xShape4, yShape4, 18);
			}
			if(correct5)
			{
				g2d.setColor(new Color(0, 100, 0, 80));
				g2d.fillPolygon(xShape7, yShape7, 19);
			}
			if(correct6)
			{
				g2d.setColor(new Color(0, 100, 0, 80));
				int[] eightX = {209, 209, 217, 217, 239, 239,266, 266,275, 275,286, 408, 395, 391,391, 383,383, 371, 371, 362,362, 355, 355, 347, 347      ,333, 333,325,325,277, 277      ,261, 261};
				int[] eightY = {560, 663, 663, 680, 680, 695, 695,721 ,721 ,744 ,780 ,740 ,721 ,721 ,701 ,701 ,688 ,688 ,662 ,662 ,633 ,633 ,620 ,620 ,568 ,568 ,547 ,547 ,562 ,562 ,575 ,575 ,560};
				g2d.fillPolygon(eightX,eightY ,33);
			}
			if(correct7)
			{
				g2d.setColor(new Color(0, 100, 0, 80));
				g2d.fillRect(980,554 ,152 ,99);
			}
		}

		public void mouseEntered(MouseEvent e)
		{
			requestFocusInWindow();
		}

		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();

			if (x >= 115 && x <= 224 && y >= 189 && y <= 334) 
			{
				panelCards.goingBacktoClue = "clue1";
				cards.show(panelCards, "clue1");
				System.out.println("clue1");
			} 
			else if (x >= 563 && x <= 685 && y >= 137 && y <= 257) 
			{
				panelCards.goingBacktoClue = "clue2";
				cards.show(panelCards, "clue2");
				System.out.println("clue2");
			}
			else if (x >= 909 && x <= 1125 && y >= 142 && y <= 297) 
			{
				panelCards.goingBacktoClue = "clue3";
				cards.show(panelCards, "clue3");
				System.out.println("clue3");
			} else if (x >= 158 && x <= 316 && y >= 355 && y <= 525) 
			{
				panelCards.goingBacktoClue = "clue4";
				cards.show(panelCards, "clue4");
				System.out.println("clue4");

			} 
			else if (x >= 78 && y >= 540 && x <= 192 && y <= 693) 
			{
				panelCards.goingBacktoClue = "clue5";
				cards.show(panelCards, "clue5");
				System.out.println("clue5");
			} 
			else if (x >= 210 && y >= 565 && x <= 392 && y <= 759)
			{
				panelCards.goingBacktoClue = "clue6";
				cards.show(panelCards, "clue6");
				System.out.println("clue6");
			} 
			else if (x >= 981 && y >= 556 && x <= 1131 && y <= 655) 
			{
				panelCards.goingBacktoClue = "clue7";
				cards.show(panelCards, "clue7");
				System.out.println("clue7");
			}
		}

		public void mousePressed(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= 115 && x <= 224 && y >= 189 && y <= 334)
				cluePressed[0] = true;



			else if(x >= 563 && x <= 685 && y >= 137 && y <= 257)
				cluePressed[1] = true;

			else if(x >= 909 && x <= 1125 && y >= 142 && y <= 297)
				cluePressed[2] = true;

			else if(x >= 158 && x <= 316 && y >= 355 && y <= 525)
				cluePressed[3] = true;



			else if (x>=78 && y>=540 && x<= 192 && y <=693)
				cluePressed[4] = true;
			else if (x>=210 && y>=565 && x<= 392 && y <=759)
				cluePressed[5] = true;

			else if (x>=981 && y>=556 && x<= 1131 && y <=655)
				cluePressed[6] = true;

			repaint();
		}

		public void mouseReleased(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= 115 && x <= 224 && y >= 189 && y <= 334)
				cluePressed[0] = false;


			else if(x >= 563 && x <= 685 && y >= 137 && y <= 257)
				cluePressed[1] = false;

			else if(x >= 909 && x <= 1125 && y >= 142 && y <= 297)
				cluePressed[2] = false;

			else if(x >= 158 && x <= 316 && y >= 355 && y <= 525)
				cluePressed[3] = false;


			else if (x>=78 && y>=540 && x<= 192 && y <=693)
				cluePressed[4] = false;
			else if (x>=210 && y>=565 && x<= 392 && y <=759)
				cluePressed[5] = false;

			else if (x>=981 && y>=556 && x<= 1131 && y <=655)
				cluePressed[6] = false;


			repaint();
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseDragged(MouseEvent e) {
		}

		public void mouseMoved(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();


			if(x >= 115 && x <= 224 && y >= 189 && y <= 334)
				clueHovered[0] = true;

			else if(x >= 563 && x <= 685 && y >= 137 && y <= 257)
				clueHovered[1] = true;

			else if(x >= 909 && x <= 1125 && y >= 142 && y <= 297)
				clueHovered[2] = true;

			else if(x >= 158 && x <= 316 && y >= 355 && y <= 525)
				clueHovered[3] = true;

			else if (x>=78 && y>=540 && x<= 192 && y <=693)
				clueHovered[4] = true;
			else if (x>=210 && y>=565 && x<= 392 && y <=759)
				clueHovered[5] = true;

			else if (x>=981 && y>=556 && x<= 1131 && y <=655)
			{
				clueHovered[6] = true;


			}


			else
			{
				for(int i = 0; i < clueHovered.length; i++)
				{
					clueHovered[i] = false;
					cluePressed[i] = false;
				}
			}

			repaint();

		}


	}

	class clue1 extends JPanel
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;

		public clue1(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			System.out.println("clue1 created"); // Debug statement
			panelCards = panelCardsIn;
			cards = cardsIn;
			JButton backButton = new JButton("See Forensics Report");
			backButton.setBounds(10, 10, 100, 30);
			backButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
				{
					cards.show(panelCards, "forensicsReport");
				}
			});
			add(backButton);
			JButton gradeAnswerButton = new JButton("Grade Answer");

			gradeAnswerButton.setBounds(10, 50, 100, 30);
			gradeAnswerButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cards.show(panelCards, "clueBoard");
					correct1 = true; // Set the correct variable
					panelCards.repaint(); // Trigger repaint to update the UI
				}
			});

			add(gradeAnswerButton);

		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.BLACK);
			g.drawString("Clue 1", 10, 20); // Example text
			Image Grader= new ImageIcon("GradeAnswer.png").getImage();
			
			g.drawImage(Grader, 100, 1, 100, 100, this);
			
			
		}
	}
	class clue2 extends JPanel
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;

		public clue2(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			System.out.println("clue2 constructor called"); // Debug statement
			panelCards = panelCardsIn;
			cards = cardsIn;
			JButton backButton = new JButton("See Forensics Report");
			backButton.setBounds(10, 10, 100, 30);
			backButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					cards.show(panelCards, "forensicsReport");
					;
				}
			});
			add(backButton);
			JButton gradeAnswerButton = new JButton("Grade Answer");
			gradeAnswerButton.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) {
					cards.show(panelCards, "clueBoard");
					correct2 = true; // Set the correct variable
					panelCards.repaint(); // Trigger repaint to update the UI
				}
			});
			add(gradeAnswerButton);
		}
	}
	class clue3 extends JPanel
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;

		public clue3(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			System.out.println("clue3 constructor called"); // Debug statement
			panelCards = panelCardsIn;
			cards = cardsIn;
			JButton backButton = new JButton("See Forensics Report");
			backButton.setBounds(10, 10, 100, 30);
			backButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cards.show(panelCards, "forensicsReport");
				}
			});	
			add(backButton);
			JButton gradeAnswerButton = new JButton("Grade Answer");

			gradeAnswerButton.setBounds(10, 50, 100, 30);
			gradeAnswerButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cards.show(panelCards, "clueBoard");
					correct3 = true; // Set the correct variable
					System.out.println("correct3 set to true"); // Debug statement
					panelCards.repaint(); // Trigger repaint to update the UI
				}
			});

			add(gradeAnswerButton);


		}
	}
	class clue4 extends JPanel
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;

		public clue4(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			System.out.println("clue4 constructor called"); // Debug statement
			panelCards = panelCardsIn;
			cards = cardsIn;
			JButton backButton = new JButton("See Forensics Report");
			backButton.setBounds(10, 10, 100, 30);
			backButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cards.show(panelCards, "forensicsReport");
				}
			});
			add(backButton);
			JButton gradeAnswerButton = new JButton("Grade Answer");
			gradeAnswerButton.setBounds(10, 50, 100, 30);

			gradeAnswerButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
				{
					cards.show(panelCards, "clueBoard");
					correct4 = true;
				}
			});
			add(gradeAnswerButton);

		}
	}
	class clue5 extends JPanel
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;

		public clue5(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			System.out.println("clue5 constructor called"); // Debug statement
			panelCards = panelCardsIn;
			cards = cardsIn;
			JButton backButton = new JButton("See Forensics Report");
			backButton.setBounds(10, 10, 100, 30);
			backButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cards.show(panelCards, "forensicsReport");
				}
			});
			add(backButton);
			JButton gradeAnswerButton = new JButton("Grade Answer");
			gradeAnswerButton.setBounds(10, 50, 100, 30);
			gradeAnswerButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
				{
					cards.show(panelCards, "clueBoard");
					correct5 = true;
				}
			});
			add(gradeAnswerButton);
		}
	}
	class clue6 extends JPanel
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;

		public clue6(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			System.out.println("clue6 constructor called"); // Debug statement
			panelCards = panelCardsIn;
			cards = cardsIn;
			JButton backButton = new JButton("See Forensics Report");
			backButton.setBounds(10, 10, 100, 30);
			backButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cards.show(panelCards, "forensicsReport");
				}
			});
			add(backButton);
			JButton gradeAnswerButton = new JButton("Grade Answer");
			gradeAnswerButton.setBounds(10, 50, 100, 30);
			gradeAnswerButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
				{
					cards.show(panelCards, "clueBoard");
					correct6 = true;
				}
			});
			add(gradeAnswerButton);

		}
	}
	class clue7 extends JPanel
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;

		public clue7(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			System.out.println("clue7 constructor called"); // Debug statement
			panelCards = panelCardsIn;
			cards = cardsIn;
			JButton backButton = new JButton("See Forensics Report");
			backButton.setBounds(10, 10, 100, 30);
			backButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cards.show(panelCards, "forensicsReport");
				}
			});
			add(backButton);
			JButton gradeAnswerButton = new JButton("Grade Answer");
			gradeAnswerButton.setBounds(10, 50, 100, 30);
			System.out.println("gradeAnswerButton created"); // Debug statement
			gradeAnswerButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
				{
					cards.show(panelCards, "clueBoard");
					correct7 = true;
					System.out.println("correct7 set to true"); // Debug statement
				}
			});
			add(gradeAnswerButton);
		}
	}
	class forensicsReport extends JPanel
	{
		// This class is responsible for displaying the forensics report 
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;
		thirdCard clip;
		BufferedImage fullImage = null;

		public forensicsReport(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			setLayout(new BorderLayout());

			panelCards = panelCardsIn;
			cards = cardsIn;
			clip = new thirdCard(panelCards, cards);

			// Load the original image for the forensics image
			ImageIcon originalIcon = new ImageIcon("coronersReport.png");
			int scrollPaneWidth = 1300;
			int scaledHeight = (originalIcon.getIconHeight() * scrollPaneWidth) / originalIcon.getIconWidth();
			Image scaledImage = originalIcon.getImage().getScaledInstance(scrollPaneWidth, scaledHeight, Image.SCALE_SMOOTH);
			ImageIcon scaledIcon = new ImageIcon(scaledImage);
			JLabel forensicsLabel = new JLabel(scaledIcon);

			// Load the full image for the button
			try
			{
				fullImage = ImageIO.read(new File("beginMurderButtons.png"));
			}

			catch (IOException e)
			{
				System.err.println("\n\n\nERROR IN RETRIEVING IMAGE\n\n\n");
				e.printStackTrace();
			}

			JLayeredPane layeredPane = new JLayeredPane();
			layeredPane.setPreferredSize(new Dimension(scrollPaneWidth, scaledHeight));

			// Add the forensics image to the layered pane
			forensicsLabel.setBounds(0, 0, scrollPaneWidth, scaledHeight);
			layeredPane.add(forensicsLabel, Integer.valueOf(0));

			int width = fullImage.getWidth();  //crop the top half of the button image
			int height = fullImage.getHeight() / 2;
			BufferedImage croppedImage = fullImage.getSubimage(0, 0, width, height);

			Image scaleImage = croppedImage.getScaledInstance(400, 65, Image.SCALE_SMOOTH);
			ImageIcon scaledReturnIcon = new ImageIcon(scaleImage);

			// Create the button with the cropped image
			JButton startGameButton = new JButton(scaledReturnIcon);
			int originalWidth = 400;
			int originalHeight = 65;
			int hoverWidth = 430;
			int hoverHeight = 75;

			class startGameButtonHandler implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					cards.show(panelCards, panelCards.goingBacktoClue); // Navigate to the last clue
					startClip = true;
					clip.repaint();
				}
			}

			int buttonX = (scrollPaneWidth / 2) - (originalWidth / 2);
			int buttonY = scaledHeight - 125;
			startGameButton.setBounds(buttonX, buttonY, originalWidth, originalHeight);
			startGameButton.setBorderPainted(false);
			startGameButton.setContentAreaFilled(false);
			startGameButton.setFocusPainted(false);
			layeredPane.add(startGameButton, Integer.valueOf(1));
			startGameButtonHandler sgbh = new startGameButtonHandler();
			startGameButton.addActionListener(sgbh); //button Action to switch the card

			Timer pulseTimer = new Timer(100, null);
			int[] pulseDirection = {1}; //1 = grow, -1 = shrink
			int[] pulseSize = {0};

			class pulseTimerHandler implements ActionListener
			{
				public void actionPerformed(ActionEvent evt)
				{
					pulseSize[0] += pulseDirection[0];

					if(pulseSize[0] >= 10 || pulseSize[0] <= 0)
						pulseDirection[0] *= -1;

					int newW = originalWidth + pulseSize[0];
					int newH = originalHeight + pulseSize[0] / 2;

					Image scaledImg = croppedImage.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
					startGameButton.setIcon(new ImageIcon(scaledImg));
					int newX = buttonX - (newW - originalWidth) / 2;
					int newY = buttonY - (newH - originalHeight) / 2;
					startGameButton.setBounds(newX, newY, newW, newH);
				}
			}

			pulseTimerHandler pth = new pulseTimerHandler();
			pulseTimer.addActionListener(pth);
			pulseTimer.start();

			class buttonHandler implements MouseListener
			{
				Timer expandTimer, shrinkTimer;
				ImageIcon currentIcon = scaledReturnIcon;

				class hoverTimerHandler implements ActionListener
				{
					int currentWidth = startGameButton.getWidth();
					int currentHeight = startGameButton.getHeight();

					public void actionPerformed(ActionEvent evt) {
						if(currentWidth >= hoverWidth && currentHeight >= hoverHeight)
							expandTimer.stop();

						else
						{
							currentWidth += 3;
							currentHeight += 1;

							Image scaledImg = croppedImage.getScaledInstance(currentWidth, currentHeight, Image.SCALE_SMOOTH);
							currentIcon = new ImageIcon(scaledImg);
							startGameButton.setIcon(currentIcon);

							int newX = buttonX - ((currentWidth - originalWidth) / 2);
							int newY = buttonY - ((currentHeight - originalHeight) / 2);
							startGameButton.setBounds(newX, newY, currentWidth, currentHeight);
						}
					}
				}

				hoverTimerHandler hth = new hoverTimerHandler();

				public void mouseEntered(MouseEvent e)
				{
					pulseTimer.stop(); // stop pulsing when hovered

					if(shrinkTimer != null && shrinkTimer.isRunning())
						shrinkTimer.stop();

					expandTimer = new Timer(10, hth);
					expandTimer.start();
				}

				class shrinkTimerHandler implements ActionListener
				{
					int currentWidth = startGameButton.getWidth();
					int currentHeight = startGameButton.getHeight();

					public void actionPerformed(ActionEvent evt)
					{
						if(currentWidth <= originalWidth && currentHeight <= originalHeight)
						{
							shrinkTimer.stop();
							currentIcon = scaledReturnIcon;
							startGameButton.setIcon(currentIcon);
							startGameButton.setBounds(buttonX, buttonY, originalWidth, originalHeight);
							pulseTimer.start(); //resume pulse when exited
						}

						else
						{
							currentWidth -= 3;
							currentHeight -= 1;

							Image scaledImg = croppedImage.getScaledInstance(currentWidth, currentHeight, Image.SCALE_SMOOTH);
							currentIcon = new ImageIcon(scaledImg);
							startGameButton.setIcon(currentIcon);

							int newX = buttonX - (currentWidth - originalWidth) / 2;
							int newY = buttonY - (currentHeight - originalHeight) / 2;
							startGameButton.setBounds(newX, newY, currentWidth, currentHeight);
						}
					}
				}

				public void mouseExited(MouseEvent e)
				{
					if(expandTimer != null && expandTimer.isRunning())
						expandTimer.stop();

					shrinkTimerHandler sth = new shrinkTimerHandler();
					shrinkTimer = new Timer(10, sth);
					shrinkTimer.start();
				}

				public void mousePressed(MouseEvent e)
				{
					//crop to bottom half when pressed
					BufferedImage croppedBottomHalf = fullImage.getSubimage(0, (fullImage.getHeight() / 2) - 10, fullImage.getWidth(), (fullImage.getHeight() / 2) - 10);

					//show bottom half of image when pressed
					Image scaledBottomImage = croppedBottomHalf.getScaledInstance(400, 65, Image.SCALE_SMOOTH);
					BufferedImage bufferedBottomImage = new BufferedImage(400, 65, BufferedImage.TYPE_INT_ARGB);
					Graphics2D g2 = bufferedBottomImage.createGraphics();
					g2.drawImage(scaledBottomImage, 0, 0, null);

					RescaleOp op = new RescaleOp(0.6f, 0, null); //dark effect and add the darker image
					op.filter(bufferedBottomImage, bufferedBottomImage); 

					ImageIcon darkIcon = new ImageIcon(bufferedBottomImage);
					startGameButton.setIcon(darkIcon);

					int pressedWidth = startGameButton.getWidth(); //adjust position when pressed
					int pressedHeight = startGameButton.getHeight();
					int pressedX = startGameButton.getX() + 5;
					int pressedY = startGameButton.getY() + 2;
					startGameButton.setBounds(pressedX, pressedY, pressedWidth, pressedHeight);
				}

				public void mouseReleased(MouseEvent e)
				{
					startGameButton.setIcon(currentIcon); //restore image
					startGameButton.setBounds((startGameButton.getX() - 5), (startGameButton.getY() - 2), startGameButton.getWidth() + 10, startGameButton.getHeight() + 4);
				}

				public void mouseClicked(MouseEvent e) {}
			}

			buttonHandler bh = new buttonHandler(); //hover and press effect
			startGameButton.addMouseListener(bh);

			JScrollPane forensicsScroll = new JScrollPane(layeredPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			JScrollBar verticalBar = forensicsScroll.getVerticalScrollBar();
			forensicsScroll.setPreferredSize(new Dimension(scrollPaneWidth, 800));
			forensicsScroll.setBorder(null);

			verticalBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI()
			{
				Color thumbColor = new Color(110, 0, 0);
				Color trackColor = new Color(169, 169, 169);

				public void paintThumb(Graphics g, JComponent c, Rectangle r)
				{
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2.setColor(thumbColor);
					g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);
					g2.dispose();
				}

				public void paintTrack(Graphics g, JComponent c, Rectangle r)
				{
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setColor(trackColor);
					g2.fillRect(r.x, r.y, r.width, r.height);
				}



			});

			add(forensicsScroll, BorderLayout.CENTER);
			/*
			JButton backButton = new JButton("Back");
		       backButton.setBounds(10, 10, 100, 30);
		        backButton.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		                cards.show(panelCards, panelCards.goingBacktoClue); // Navigate to the last clue
		            }
		        });
		        layeredPane.add(backButton, Integer.valueOf(1)); // Add the button to the layered pane
			 */
		}
	}
}