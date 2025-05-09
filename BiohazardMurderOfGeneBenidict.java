//https://github.com/anaygoyal09/BioHazard
//Arsh Abhinkar, Anay Goyal
//BiohazardMurderOfGeneBenidict.java
//4/12/25

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
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
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
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
	boolean startClip, darkenScreenPanel3;
	String file;

	public BiohazardMurderOfGeneBenidictHolder()
	{
		setBackground(Color.BLACK);
		CardLayout cards = new CardLayout();
		setLayout(cards);
		file = "";
		readFile();

		firstCard title = new firstCard(this, cards);
		secondCard report = new secondCard(this, cards);
		thirdCard practiceQuestion = new thirdCard(this, cards);
		fourthCard clueBoard = new fourthCard(this, cards);
		question1 question1 = new question1(this, cards);

		add(title, "title");
		add(report, "report");
		add(practiceQuestion, "practiceQuestion");
		add(clueBoard, "clueBoard");
		add(question1, "question1");

		startClip = darkenScreenPanel3 = false;
	}

	public void readFile()
	{
		Scanner input = null;
		String inFileName = "questions.txt";

		File inFile = new File(inFileName); //D+I new file

		try
		{
			input = new Scanner(inFile); //initializing scanner
		}

		catch(FileNotFoundException e) //if scanner fails to initialize, print error message and exit program
		{
			System.err.printf("\nERROR: Cannot find/open file %s. ", inFileName);
			System.exit(1);
		}

		while(input.hasNextLine()) //store the entire text file in string file
			file += input.nextLine() + "\n";
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
		thirdCard darkScreen;

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
			darkScreen = new thirdCard(panelCards, cards);
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
			if(clickingStart)
			{
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
				cards.show(panelCards, "question1");
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
		thirdCard darkScreen;
		fourthCard clip;
		BufferedImage fullImage = null;

		public secondCard(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			setLayout(new BorderLayout());

			panelCards = panelCardsIn;
			cards = cardsIn;
			clip = new fourthCard(panelCards, cards);
			darkScreen = new thirdCard(panelCards, cards);

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
					cards.show(panelCards, "practiceQuestion");
					darkenScreenPanel3 = true;
					darkScreen.repaint();
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
		Image pqBackground, tutorialQuestions, forensicButton, tutorialText, tutorialHands;
		Timer darkScreenTimer, secondDarkTimer, fadeTimer;
		boolean showQuestions, showDarkScreen, hoveredOnForensic, clickedOnForensic, secondDarkScreen;
		boolean[] showTutorial, isSwapped = new boolean[] {false, false, false, false, false};
		float darkAlpha = 0f;
		int repeats = 0, activeIndex = -1;
		int[] currentFrames = {0, 2, 4, 6, 8};

		public thirdCard(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			panelCards = panelCardsIn;
			cards = cardsIn;
			showQuestions = showDarkScreen = hoveredOnForensic = clickedOnForensic = secondDarkScreen = false;
			darkScreenTimerHandler dsth = new darkScreenTimerHandler();
			secondDarkTimerHandler sdth = new secondDarkTimerHandler();
			secondDarkTimer = new Timer(3500, sdth);
			darkScreenTimer = new Timer(1000, dsth);
			darkScreenTimer.setRepeats(false);
			secondDarkTimer.setRepeats(false);  
			showTutorial = new boolean[4];

			for(int i = 0; i < showTutorial.length; i++)
				showTutorial[i] = false;

			addMouseListener(this);
			addMouseMotionListener(this);

			retrieveImage();
			setupLayout();
		}


		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();
			int drawWidth = tutorialQuestions.getWidth(null) / 3;
			int drawHeight = tutorialQuestions.getHeight(null) / 10;

			if(x >= 100 && x <= 190 + drawWidth && y >= 250 && y <= 180 + drawHeight)
				swapImage(0);

			else if(x >= 520 && x <= 610 + drawWidth && y >= 250 && y <= 180 + drawHeight)
				swapImage(1);

			else if(x >= 100 && x <= 190 + drawWidth && y >= 400 && y <= 330 + drawHeight)
				swapImage(2);

			else if(x >= 520 && x <= 610 + drawWidth && y >= 400 && y <= 330 + drawHeight)
				swapImage(3);

			else if(x >= 310 && x <= 400 + drawWidth && y >= 690 && y <= 620 + drawHeight)
			{
				if(activeIndex != -1)
					swapImage(4);
			}

			if(x >= 100 && x <= 497 && y >= 250 && y <= 313)
			{
				showTutorial[1] = false;
				showTutorial[2] = true;
				cp.repaint();
			}

			if(x >= 20 && x <= 70 && y >= 700 && y <= 750)
			{
				clickedOnForensic = true;
				cp.repaint();
			}
		}

		public void mouseMoved(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= 20 && x <= 70 && y >= 700 && y <= 750)
				hoveredOnForensic = true;

			else
				hoveredOnForensic = false;

			cp.repaint();
		}

		private void swapImage(int index)
		{
			if(currentFrames[index] % 2 == 1)
			{
				currentFrames[index]--; // Change from 1 to 0
				isSwapped[index] = false; //Mark as not swapped
			}

			else
			{
				// If it's in state 0, switch it to state 1
				currentFrames[index]++;
				isSwapped[index] = true; // Mark as swapped
			}

			// If another button is clicked, revert its state back to 0
			if(activeIndex != -1 && activeIndex != index)
			{
				if(currentFrames[activeIndex] % 2 == 1)
				{
					currentFrames[activeIndex]--; // Revert from 1 to 0
					isSwapped[activeIndex] = false; //Mark as not swapped
				}
			}

			activeIndex = index;
			cp.repaint(); //change paint component
		}



		class centerPanel extends JPanel
		{
			float[] buttonAlpha = new float[] {0f, 0f, 0f, 0f, 0f};

			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)(g);

				if(pqBackground != null)
					g.drawImage(pqBackground, -20, 0, 1050, 870, this);

				int drawWidth = tutorialQuestions.getWidth(this) / 3;
				int drawHeight = tutorialQuestions.getHeight(this) / 10;
				int handWidth = tutorialHands.getWidth(this);
				int handHeight = tutorialHands.getHeight(this) / 4;
				int textWidth = tutorialText.getWidth(this);
				int textHeight = tutorialText.getHeight(this) / 4;


				if(showDarkScreen)
				{
					g2d.setColor(new Color(0, 0, 0, (int)(darkAlpha * 255)));
					g2d.fillRect(0, 0, getWidth(), 27);
					g2d.fillRect(30, 28, 58, 76);
					g2d.fillRect(927, 28, 70, 76);
					g2d.fillRect(0, 104, 1300, 696);
				}

				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, buttonAlpha[0]));
				g2d.drawImage(tutorialQuestions, 100, 250, 190 + drawWidth, 180 + drawHeight,
						0, drawHeight * currentFrames[0], tutorialQuestions.getWidth(this), drawHeight * (currentFrames[0] + 1), this);

				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, buttonAlpha[1]));
				g2d.drawImage(tutorialQuestions, 520, 250, 610 + drawWidth, 180 + drawHeight,
						0, drawHeight * currentFrames[1], tutorialQuestions.getWidth(this), drawHeight * (currentFrames[1] + 1), this);

				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, buttonAlpha[2]));
				g2d.drawImage(tutorialQuestions, 100, 400, 190 + drawWidth, 330 + drawHeight,
						0, drawHeight * currentFrames[2], tutorialQuestions.getWidth(this), drawHeight * (currentFrames[2] + 1), this);

				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, buttonAlpha[3]));
				g2d.drawImage(tutorialQuestions, 520, 400, 610 + drawWidth, 330 + drawHeight,
						0, drawHeight * currentFrames[3], tutorialQuestions.getWidth(this), drawHeight * (currentFrames[3] + 1), this);

				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, buttonAlpha[4]));
				g2d.drawImage(tutorialQuestions, 310, 690, 400 + drawWidth, 620 + drawHeight,
						0, drawHeight * currentFrames[4], tutorialQuestions.getWidth(this), drawHeight * (currentFrames[4] + 1), this);

				g2d.drawImage(forensicButton, 50, 695, 50, 60, this);

				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

				if(secondDarkScreen)
				{
					g2d.setColor(new Color(0, 0, 0, 80));
					g2d.fillRect(0, 0, getWidth(), getHeight());
				}

				if(showTutorial[0])
				{
					g.drawImage(tutorialText, 570, 200, 610 + textWidth, 170 + textHeight, 0, 0, textWidth, handHeight - 100, this);
					g.drawImage(tutorialHands, 650, 300, 740 + handWidth, 390 + handHeight, 0, 0, handWidth, handHeight, this);
				}

				if(showTutorial[1])
				{
					g2d.setColor(new Color(160, 160, 160, 80));
					g2d.fillRect(100, 250, 397, 63);
					g.drawImage(tutorialText, 230, 150, 270 + textWidth, 140 + textHeight, 0, handHeight - 30, textWidth, (handHeight * 2) - 70, this);
					g.drawImage(tutorialHands, 300, 200, 320 + handWidth, 250 + handHeight, 0, handHeight, handWidth, (handHeight * 2) + 70, this);
				}

				if(showTutorial[2])
				{
					g.drawImage(tutorialHands, 0, 430, 20 + handWidth, 480 + handHeight, 0, (handHeight * 2) + 130, handWidth, (handHeight * 4), this);
					g.drawImage(tutorialText, 120, 640, 140 + textWidth, 690 + textHeight, 0, (handHeight * 2) - 60, textWidth, (handHeight * 3) - 70, this);
				}

				if(showTutorial[3])
				{
					g2d.setColor(new Color(160, 160, 160, 80));
					g2d.fillRect(310, 690, 397, 63);
					g.drawImage(tutorialHands, 250, 430, 270 + handWidth, 480 + handHeight, 0, (handHeight * 2) + 130, handWidth, (handHeight * 4), this);
					g.drawImage(tutorialText, 450, 600, 470 + textWidth, 650 + textHeight, 0, (handHeight * 3) - 90, textWidth, (handHeight * 4) - 70, this);
				}

				if(hoveredOnForensic)
				{
					g2d.setColor(new Color(30, 30, 30, 80));
					g2d.fillRect(50, 700, 50, 50);
				}

				if(clickedOnForensic)
				{
					g2d.setColor(new Color(0, 0, 0, 80));
					g2d.fillRect(50, 700, 50, 50);
				}
			}
		}

		centerPanel cp = new centerPanel();

		class cluePanelWithBackground extends JPanel implements MouseListener
		{
			private Image backgroundImage;

			public cluePanelWithBackground(String imagePath)
			{
				addMouseListener(this);

				try
				{
					backgroundImage = ImageIO.read(new File(imagePath));
				}

				catch (IOException e)
				{
					System.out.println("Failed to load background image for clue panel.");
					e.printStackTrace();
				}
			}

			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)(g);

				if(backgroundImage != null)
					g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

				if(showTutorial[1])
				{
					g2d.setColor(new Color(0, 0, 0, 80));
					g2d.fillRect(0,  0,  getWidth(),  getHeight());
				}
			}

			public void mouseClicked(MouseEvent e)
			{
				int x = e.getX();
				int y = e.getY();

				if(x >= 0 && x <= 314 && y >= 0 && y <= 800)
				{
					showTutorial[0] = false;
					showTutorial[1] = true;
					cp.repaint();
					repaint();
				}
			}

			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		}

		cluePanelWithBackground cluePan = new cluePanelWithBackground("cluesPanelBG.png");

		public void retrieveImage()
		{
			try
			{
				pqBackground = ImageIO.read(new File("Screenshot 2025-05-03 at 8.12.57 AM.png"));
				tutorialQuestions = ImageIO.read(new File("tutorialButtons.png"));
				forensicButton = ImageIO.read(new File("forensicButton.png"));
				tutorialText = ImageIO.read(new File("tutorialText.png"));
				tutorialHands = ImageIO.read(new File("tutorialHand.png"));
			}

			catch (IOException e)
			{
				System.err.println("\n\n\nERROR IN RETRIEVING IMAGE\n\n\n");
				e.printStackTrace();
			}
		}

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);

			if(darkenScreenPanel3 && repeats == 0)
			{
				darkScreenTimer.start();
				secondDarkTimer.start();
				repeats++;
			}
		}

		class darkScreenTimerHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				showDarkScreen = true;
				darkAlpha = 0f;

				fadeTimer = new Timer(50, new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						darkAlpha += 0.05f;
						if (darkAlpha >= 0.5f)
						{
							darkAlpha = 0.5f;
							fadeTimer.stop();
							startButtonFadeInSequence();
						}
						cp.repaint();
					}
				});

				fadeTimer.start();
			}
		}

		class secondDarkTimerHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				secondDarkScreen = true;
				showTutorial[0] = true;
				cp.repaint();
			}
		}

		Timer[] buttonFadeTimers = new Timer[5];

		public void startButtonFadeInSequence()
		{
			for (int i = 0; i < 5; i++)
			{
				final int index = i;
				buttonFadeTimers[i] = new Timer(i * 300, new ActionListener()
				{
					float alpha = 0f;
					Timer innerTimer = new Timer(50, null);

					public void actionPerformed(ActionEvent e)
					{
						innerTimer.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent ev)
							{
								alpha += 0.05f;

								if (alpha >= 1f)
								{
									alpha = 1f;
									innerTimer.stop();
								}

								cp.buttonAlpha[index] = alpha;
								cp.repaint();
							}
						});

						innerTimer.start();
						buttonFadeTimers[index].stop();
					}
				});

				buttonFadeTimers[i].start();
			}
		}

		public void setupLayout()
		{
			setLayout(new BorderLayout());

			JLayeredPane layeredPane = new JLayeredPane();
			layeredPane.setPreferredSize(new Dimension(1250, 800));

			cp.setBounds(-30, 0, 1000, 800);
			cp.setOpaque(false);

			cluePan.setLayout(new BoxLayout(cluePan, BoxLayout.Y_AXIS));
			cluePan.setOpaque(false);
			cluePan.setPreferredSize(new Dimension(260, 1000));

			JScrollPane scrollPane = new JScrollPane(cluePan, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			JScrollBar clueScrollBar = scrollPane.getVerticalScrollBar();

			clueScrollBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI()
			{
				Color thumbColor = new Color(182, 119, 62);
				Color trackColor = new Color(71, 35, 32);

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

			scrollPane.setBounds(970, 0, 330, 800);
			scrollPane.setOpaque(false);
			scrollPane.getViewport().setOpaque(false);
			scrollPane.setBorder(null);

			layeredPane.add(cp, Integer.valueOf(1));
			layeredPane.add(scrollPane, Integer.valueOf(1));

			add(layeredPane, BorderLayout.CENTER);
		}

		public void mouseDragged(MouseEvent e) {}

		public void mousePressed(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= 20 && x <= 70 && y >= 700 && y <= 750)
			{
				clickedOnForensic = true;
				cp.repaint();
			}

			else
			{
				clickedOnForensic = false;
				cp.repaint();
			}

			if(x >= 20 && x <= 70 && y >= 700 && y <= 750 && showTutorial[2])
			{
				showTutorial[2] = false;
				showTutorial[3] = true;
				cp.repaint();
			}

			if(x >= 310 && x <= 707 && y >= 690 && y <= 753 && showTutorial[3])
				cards.show(panelCards, "clueBoard");
		}

		public void mouseReleased(MouseEvent e) 
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= 20 && x <= 70 && y >= 700 && y <= 750)
			{
				clickedOnForensic = false;
				cp.repaint();
			}
		}

		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}

	class fourthCard extends JPanel implements MouseListener, MouseMotionListener
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;
		Image startGameClip, murderBoard;
		Timer blackScreenTimer, fadeTimer;
		boolean blackScreen, startClip;
		boolean[] clueHovered, cluePressed;
		float boardTransparency;

		public fourthCard(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			addMouseListener(this);
			setFocusable(true);
			addMouseMotionListener(this);
			panelCards = panelCardsIn;
			cards = cardsIn;
			blackScreen = false;
			startClip = true; //start with the clip
			boardTransparency = 0.0f;
			clueHovered = new boolean[12];
			cluePressed = new boolean[12];

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
			int[] fourX = {159, 160, 166, 167, 173, 173, 217, 217, 224, 318, 318, 275, 247, 247, 223, 223, 180, 180};
			int[] fourY = {368, 411, 411, 430, 437, 517, 517, 529, 529, 490, 477, 361, 361, 354, 354, 361, 361, 368};
			int[] sevenX = {87, 87, 80, 80, 73, 73, 66, 66, 88, 88, 138, 138, 182, 203, 203, 167, 167, 117, 117};
			int[] sevenY = {533, 560, 560, 601, 601, 640, 640, 683, 683, 690, 690, 697, 696, 584, 547, 547, 540, 540, 533};
			int[] eightX = {209, 209, 217, 217, 239, 239,266, 266,275, 275,286, 408, 395, 391,391, 383,383, 371, 371, 362,362, 355, 355, 347, 347, 261, 261};
			int[] eightY = {560, 663, 663, 680, 680, 695, 695, 721,721, 750,790, 750, 721, 721,701, 701, 688, 688, 662, 662, 633, 633, 620, 620, 580, 580, 560};
			int[] nineX = {453, 476, 477, 484, 484, 520, 520, 563, 563, 556, 556, 549, 549, 543, 541, 533, 535, 521, 418, 418, 426, 426, 433, 433, 433, 441, 441, 446, 446};
			int[] nineY = {586, 586, 591, 592, 600, 600, 608, 606, 626, 626, 660, 660, 694, 694, 727, 727, 761, 760, 733, 698, 698, 685, 685, 655, 628, 628, 606, 606, 586};
			int[] tenX = {749, 749,741,741, 770, 807,844,  844, 878 , 878, 800, 800};
			int[] tenY = {647,700 ,700, 722,733, 733 , 733,  740, 740,660, 660, 647};

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

			//       g.drawImage(murderBoard, 0, -40, 1300, 820, this);

			if(blackScreen || boardTransparency > 0f) //fade-in murder board
			{
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, boardTransparency));
				g2d.drawImage(murderBoard, 0, -40, 1300, 820, this);
			}

			g2d.setColor(new Color(0, 0, 0, 80));

			if(clueHovered[0]) //ADD && BLACKSCREEN LATER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! FOR MAKING IT BETTER
				g2d.fillRect(108, 189, 117, 147);

			else if(clueHovered[1])
				g2d.fillRect(410, 201, 117, 62);

			else if(clueHovered[2])
				g2d.fillRect(563, 136, 122, 120);

			else if(clueHovered[3])
			{
				g2d.fillRect(908, 168, 101, 88);
				g2d.fillRect(1009, 142, 116, 116);
				g2d.fillRect(1000, 257, 110, 39);
			}

			else if(clueHovered[4])
				g2d.fillPolygon(fourX, fourY, 18);

			else if(clueHovered[5])
				g2d.fillRect(512, 369, 101, 131);

			else if(clueHovered[6])
				g2d.fillRect(1073, 347, 137, 82);

			else if(clueHovered[7])
				g2d.fillPolygon(sevenX, sevenY, 19);

			else if(clueHovered[8])
				g2d.fillPolygon(eightX, eightY, 27);

			else if(clueHovered[9])
				g2d.fillPolygon(nineX, nineY, 29);

			else if(clueHovered[10])
				g2d.fillPolygon(tenX, tenY, 12);

			else if(clueHovered[11])
				g2d.fillRect(980, 554, 152, 99);

			//----------------------------------------------------------------------------

			g2d.setColor(new Color(100, 0, 0, 80));

			if(cluePressed[0])
				g2d.fillRect(108, 189, 117, 147);

			else if(cluePressed[1])
				g2d.fillRect(410, 201, 117, 62);

			else if(cluePressed[2])
				g2d.fillRect(563, 136, 122, 120);

			else if(cluePressed[3])
			{
				g2d.fillRect(908, 168, 101, 88);
				g2d.fillRect(1009, 142, 116, 116);
				g2d.fillRect(1000, 257, 110, 39);
			}

			else if(cluePressed[4])
			{
				g2d.setColor(new Color(0, 100, 0, 80));
				g2d.fillPolygon(fourX, fourY, 18);
			}

			else if(cluePressed[5])
				g2d.fillRect(512, 369, 101, 131);

			else if(cluePressed[6])
				g2d.fillRect(1073, 347, 137, 82);

			else if(cluePressed[7])
				g2d.fillPolygon(sevenX, sevenY, 19);
		}

		public void mouseEntered(MouseEvent e)
		{
			requestFocusInWindow();
		}

		public void mouseClicked(MouseEvent e) {}

		public void mousePressed(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= 115 && x <= 224 && y >= 189 && y <= 334)
				cluePressed[0] = true;

			else if(x >= 410 && x <= 527 && y >= 201 && y <= 263)
				cluePressed[1] = true;

			else if(x >= 563 && x <= 685 && y >= 137 && y <= 257)
				cluePressed[2] = true;

			else if(x >= 909 && x <= 1125 && y >= 142 && y <= 297)
				cluePressed[3] = true;

			else if(x >= 158 && x <= 316 && y >= 355 && y <= 525)
				cluePressed[4] = true;

			else if(x >= 513 && x <= 613 && y >= 369 && y <= 500)
				cluePressed[5] = true;

			repaint();
		}

		public void mouseReleased(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= 115 && x <= 224 && y >= 189 && y <= 334)
				cluePressed[0] = false;

			else if(x >= 410 && x <= 527 && y >= 201 && y <= 263)
				cluePressed[1] = false;

			else if(x >= 563 && x <= 685 && y >= 137 && y <= 257)
				cluePressed[2] = false;

			else if(x >= 909 && x <= 1125 && y >= 142 && y <= 297)
				cluePressed[3] = false;

			else if(x >= 158 && x <= 316 && y >= 355 && y <= 525)
			{
				cluePressed[4] = false;
				cards.show(panelCards, "question1");

			}

			else if(x >= 513 && x <= 613 && y >= 369 && y <= 500)
				cluePressed[5] = false;

			repaint();
		}

		public void mouseExited(MouseEvent e) {}
		public void mouseDragged(MouseEvent e) {}

		public void mouseMoved(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();
			//	System.out.println(x+ ","+ (y-2)); //remove later

			if(x >= 115 && x <= 224 && y >= 189 && y <= 334)
				clueHovered[0] = true;

			else if(x >= 410 && x <= 527 && y >= 201 && y <= 263)
				clueHovered[1] = true;

			else if(x >= 563 && x <= 685 && y >= 137 && y <= 257)
				clueHovered[2] = true;

			else if(x >= 909 && x <= 1125 && y >= 142 && y <= 297)
				clueHovered[3] = true;

			else if(x >= 158 && x <= 316 && y >= 355 && y <= 525)
				clueHovered[4] = true;

			else if(x >= 513 && x <= 613 && y >= 369 && y <= 500)
				clueHovered[5] = true;

			else if(x >= 1075 && y >= 351 && x <= 1210 && y <= 427)
				clueHovered[6] = true;

			else if (x >= 78 && y >= 540 && x <= 192 && y <= 693)
				clueHovered[7] = true;

			else if (x >= 210 && y >= 565 && x <= 392 && y <= 759)
				clueHovered[8] = true;

			else if (x >= 441 && y >= 592 && x <= 546 && y <= 759)
				clueHovered[9] = true;

			else if (x >= 753 && y >= 647 && x <= 875 && y <= 738)
				clueHovered[10] = true;

			else if (x >= 981 && y >= 556 && x <= 1131 && y <= 655)
				clueHovered[11] = true;

			else
			{
				for(int i = 0; i < clueHovered.length; i++)
					clueHovered[i] = false;
			}

			repaint();
		}
	}

	class question1 extends JPanel implements KeyListener
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		centerPanel cp;
		CardLayout cards;
		Image background, questions, options, boxes;
		String question1Text, questionChoice, questionsName, optionsName, boxesName;
		Timer waitTimer, lowerTimer;
		int randNum, repeats, xCord, yCord;

		public question1(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			cp = new centerPanel();

			setupLayout();
			addKeyListener(this);
			setFocusable(true);
			requestFocusInWindow();

			repeats = 0;
			xCord = 500;
			yCord = 140;

			waitTimerHandler wth = new waitTimerHandler();
			lowerTimerHandler lth = new lowerTimerHandler();
			waitTimer = new Timer(3000, wth);
			waitTimer.setRepeats(false);
			lowerTimer = new Timer(150, lth);

			question1Text = file.substring(file.indexOf("Question 1"), (file.indexOf("-----")));
			randNum = (int)(Math.random() * 5) + 1;
			questionChoice = question1Text.substring(question1Text.indexOf("Choice " + randNum), question1Text.indexOf("-", question1Text.indexOf("Choice " + randNum)));
			questionsName = question1Text.substring(question1Text.indexOf("Questions: ") + 11, (question1Text.indexOf("-", question1Text.indexOf("Questions: "))) - 1);
			optionsName = question1Text.substring(question1Text.indexOf("Options: ") + 9, (question1Text.indexOf("-", question1Text.indexOf("Options: "))) - 1);
			boxesName = (question1Text.substring(question1Text.indexOf("Boxes: ") + 7, question1Text.length())).trim();

			retreiveImage();
		}

		public void retreiveImage()
		{
			try
			{
				background = ImageIO.read(new File("question1BG.png"));
				questions = ImageIO.read(new File(questionsName));
				options = ImageIO.read(new File(optionsName));
				boxes = ImageIO.read(new File(boxesName));
			}

			catch(IOException e)
			{
				System.err.println("\n\n\nERROR IN RETRIEVING IMAGE\n\n\n");
				e.printStackTrace();
			}
		}


		class waitTimerHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				requestFocusInWindow();

				setFocusable(true);
				lowerTimer.start();
				waitTimer.stop();
			}
		}

		class lowerTimerHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				yCord += 10;
				requestFocusInWindow();
				
				if(yCord == 700)
					lowerTimer.stop();

				setFocusable(true);
				cp.repaint();
			}
		}

		class centerPanel extends JPanel
		{
			int questionHeight, questionWidth, optionHeight, optionWidth;

			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)(g);

				requestFocusInWindow();
				setFocusable(true);

				questionHeight = questions.getHeight(this) / 5;
				questionWidth = questions.getWidth(this);

				optionHeight = options.getHeight(this) / 5;
				optionWidth = options.getWidth(this);

				if(background != null)
					g.drawImage(background, -20, 0, 1050, 870, this);

				g.drawImage(questions, 100, 30, 920, 100, 0, questionHeight * (randNum - 1), questionWidth, questionHeight * randNum, this);

				g.setColor(Color.RED);
				g.fillRect(xCord, yCord, 20, 20);

				if(repeats == 0)
					waitTimer.start();

				repeats++;

				g.drawImage(options, 100, 130, 920, 290, 0, optionHeight * (randNum - 1), optionWidth, optionHeight * randNum, this);
				g.drawImage(boxes, 100, 620, 820, 150, this);		
			}
		}

		class cluePanelWithBackground extends JPanel
		{
			private Image backgroundImage;

			public cluePanelWithBackground(String imagePath)
			{
				try
				{
					backgroundImage = ImageIO.read(new File(imagePath));
				}

				catch (IOException e)
				{
					System.out.println("Failed to load background image for clue panel.");
					e.printStackTrace();
				}
			}

			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)(g);

				if(backgroundImage != null)
					g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		}

		cluePanelWithBackground cluePan = new cluePanelWithBackground("cluesPanelBG.png");

		public void setupLayout()
		{
			setLayout(new BorderLayout());

			JLayeredPane layeredPane = new JLayeredPane();
			layeredPane.setPreferredSize(new Dimension(1250, 800));

			cp.setBounds(-30, 0, 1000, 800);
			cp.setOpaque(false);

			cluePan.setLayout(new BoxLayout(cluePan, BoxLayout.Y_AXIS));
			cluePan.setOpaque(false);
			cluePan.setPreferredSize(new Dimension(260, 1000));

			JScrollPane scrollPane = new JScrollPane(cluePan, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			JScrollBar clueScrollBar = scrollPane.getVerticalScrollBar();

			clueScrollBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI()
			{
				Color thumbColor = new Color(182, 119, 62);
				Color trackColor = new Color(71, 35, 32);

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

			scrollPane.setBounds(970, 0, 330, 800);
			scrollPane.setOpaque(false);
			scrollPane.getViewport().setOpaque(false);
			scrollPane.setBorder(null);
			layeredPane.add(cp, Integer.valueOf(1));
			layeredPane.add(scrollPane, Integer.valueOf(1));
			add(layeredPane, BorderLayout.CENTER);
		}

		public void keyTyped(KeyEvent e) {
			System.out.println("helloooooo");

			int key = e.getKeyCode();
			if(key == KeyEvent.VK_LEFT)
				xCord -= 100;

			if(key == KeyEvent.VK_RIGHT)
				xCord += 100;

			cp.repaint();
		}

		public void keyPressed(KeyEvent e)
		{
			System.out.println("helloooooo");

			int key = e.getKeyCode();
			if(key == KeyEvent.VK_LEFT)
				xCord -= 100;

			if(key == KeyEvent.VK_RIGHT)
				xCord += 100;

			cp.repaint();
		}

		public void keyReleased(KeyEvent e) {
			System.out.println("helloooooo");

			int key = e.getKeyCode();
			if(key == KeyEvent.VK_LEFT)
				xCord -= 100;

			if(key == KeyEvent.VK_RIGHT)
				xCord += 100;

			cp.repaint();
		}
	}
}