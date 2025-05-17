//https://github.com/anaygoyal09/BioHazard
//Arsh Abhinkar, Anay Goyal
//BiohazardMurderOfGeneBenidict.java
//4/12/25

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
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
	boolean correct1, correct2, correct3, correct4, correct5, correct6, correct7;
	boolean startClip, darkenScreenPanel3, showClueVid;
	boolean[] questionsAnswered;
	boolean wrong1, wrong2, wrong3, wrong4, wrong5, wrong6, wrong7;
	String file, goingBacktoClue;
	Font font;

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
		fifthCard secondClueBoard = new fifthCard(this, cards);
		question1 question1 = new question1(this, cards);
		clue1 clue1 = new clue1(this, cards);
		question2 question2 = new question2(this, cards);
		question3 question3 = new question3(this, cards);
		question4 question4 = new question4(this, cards);
		question5 question5 = new question5(this, cards);
		question7 question7 = new question7(this, cards);
		chooseMurderer chooseMurderer = new chooseMurderer(this, cards);

		add(title, "title");
		add(report, "report");
		add(practiceQuestion, "practiceQuestion");
		add(clueBoard, "clueBoard");
		add(secondClueBoard, "secondClueBoard");
		add(question1, "question1");
		add(clue1, "clue1");
		add(question2, "question2");
		add(question3, "question3");
		add(question4, "question4");
		add(question5, "question5");
		add(question7, "question7");
		add(chooseMurderer, "chooseMurderer");

		showClueVid = true;
		startClip = darkenScreenPanel3 = correct1 = correct2 = correct3 = correct4 = correct5 = correct6 = correct7 = false;
		questionsAnswered = new boolean[7];

		for(int i = 0; i < questionsAnswered.length; i++)
		{
			questionsAnswered[i] = false;
		}
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
				cards.show(panelCards, "question2");
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

			if(x >= 20 && x <= 70 && y >= 700 && y <= 750 && showTutorial[1] == false)
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
			{
				showClueVid = true;
				cards.show(panelCards, "clueBoard");
			}
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

			if(!showClueVid)
				g.drawImage(murderBoard, 0, -40, 1300, 820, this);

			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, boardTransparency));
			g2d.drawImage(murderBoard, 0, -40, 1300, 820, this);

			g2d.setColor(new Color(0, 0, 0, 80));

			if(clueHovered[0]) //ADD && BLACKSCREEN LATER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! FOR MAKING IT BETTER
			{
				g2d.setColor(new Color(0, 0, 0, 80));
				g2d.fillRect(108, 189, 117, 147);
			}

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
			//		System.out.println(correct1 + " " + correct2 + " " + correct3 + " " + correct4 + " " + correct5 + " " + correct6 + " " + correct7);
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

		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= 115 && x <= 224 && y >= 189 && y <= 334 && !correct1) 
			{
				//	if(questionsAnswered[0] = false)
				cards.show(panelCards, "question1");

				//		else if(questionsAnswered[1] = false)
				//		cards.show(panelCards, "question2");

				//	else if(questionsAnswered[2] = false)

			} 

			else if (x >= 563 && x <= 685 && y >= 137 && y <= 257&& !correct2) 
			{
				cards.show(panelCards, "question1");
			}

			else if (x >= 909 && x <= 1125 && y >= 142 && y <= 297&& !correct3) 
			{
				cards.show(panelCards, "question1");
			}

			else if (x >= 158 && x <= 316 && y >= 355 && y <= 525&& !correct4) 
			{
				cards.show(panelCards, "question1");
			} 

			else if (x >= 78 && y >= 540 && x <= 192 && y <= 693&& !correct5) 
			{
				cards.show(panelCards, "question1");
			}

			else if (x >= 210 && y >= 565 && x <= 392 && y <= 759&& !correct6)
			{
				cards.show(panelCards, "question1");
			} 

			else if (x >= 981 && y >= 556 && x <= 1131 && y <= 655 && !correct7) 
			{
				cards.show(panelCards, "question1");
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
			{
				clueHovered[0] = true;
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			else if(x >= 563 && x <= 685 && y >= 137 && y <= 257)
			{
				clueHovered[1] = true;
				setCursor(new Cursor(Cursor.HAND_CURSOR));

			}

			else if(x >= 909 && x <= 1125 && y >= 142 && y <= 297)
			{
				clueHovered[2] = true;
				setCursor(new Cursor(Cursor.HAND_CURSOR));

			}
			else if(x >= 158 && x <= 316 && y >= 355 && y <= 525)
			{
				clueHovered[3] = true;
				setCursor(new Cursor(Cursor.HAND_CURSOR));

			}

			else if (x>=78 && y>=540 && x<= 192 && y <=693)
			{
				clueHovered[4] = true;
				setCursor(new Cursor(Cursor.HAND_CURSOR));

			}
			else if (x>=210 && y>=565 && x<= 392 && y <=759)
			{
				clueHovered[5] = true;
				setCursor(new Cursor(Cursor.HAND_CURSOR));

			}

			else if (x>=981 && y>=556 && x<= 1131 && y <=655)
			{
				clueHovered[6] = true;
				setCursor(new Cursor(Cursor.HAND_CURSOR));


			}


			else
			{
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				for(int i = 0; i < clueHovered.length; i++)
				{
					clueHovered[i] = false;
					cluePressed[i] = false;
				}
			}

			repaint();

		}
	}

	class fifthCard extends JPanel implements MouseListener, MouseMotionListener
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;
		Image startGameClip, murderBoard;
		Timer blackScreenTimer, fadeTimer;
		boolean blackScreen, startClip;
		boolean[] clueHovered, cluePressed;
		float boardTransparency;

		public fifthCard(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
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

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)(g);
			int[] xShape4 = {159, 160, 166, 167, 173, 173, 217, 217, 224, 318, 318, 275, 247, 247, 223, 223, 180, 180};
			int[] yShape4 = {368, 411, 411, 430, 437, 517, 517, 529, 529, 490, 477, 361, 361, 354, 354, 361, 361, 368};
			int[] xShape7 = {87, 87, 80, 80, 73, 73, 66, 66, 88, 88, 138, 138, 182, 203, 203, 167, 167, 117, 117};
			int[] yShape7 = {533, 560, 560, 601, 601, 640, 640, 683, 683, 690, 690, 697, 696, 584, 547, 547, 540, 540, 533};

			g.drawImage(murderBoard, 0, -40, 1300, 820, this);


			g2d.setColor(new Color(0, 0, 0, 80));

			if(clueHovered[0]) //ADD && BLACKSCREEN LATER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! FOR MAKING IT BETTER
			{
				g2d.setColor(new Color(0, 0, 0, 80));
				g2d.fillRect(108, 189, 117, 147);
			}

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
			//		System.out.println(correct1 + " " + correct2 + " " + correct3 + " " + correct4 + " " + correct5 + " " + correct6 + " " + correct7);
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

		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			int num = 0;

			if(questionsAnswered[6])
				num = 7;

			else if(questionsAnswered[5])
				num = 6;

			else if(questionsAnswered[4])
				num = 5;

			else if(questionsAnswered[3])
				num = 4;

			else if(questionsAnswered[2])
				num = 3;

			else if(questionsAnswered[1])
				num = 2;

			if(x >= 115 && x <= 224 && y >= 189 && y <= 334 && !correct1) 
			{
				//	if(questionsAnswered[0] = false)
				System.out.println("\n\n\n\n!!!!!!! " + num + " !!!!!!!!!!!");

				cards.show(panelCards, "question" + num);

				//		else if(questionsAnswered[1] = false)
				//		cards.show(panelCards, "question2");

				//	else if(questionsAnswered[2] = false)

			} 

			else if (x >= 563 && x <= 685 && y >= 137 && y <= 257&& !correct2) 
			{
				cards.show(panelCards, "question" + num);
			}

			else if (x >= 909 && x <= 1125 && y >= 142 && y <= 297&& !correct3) 
			{
				cards.show(panelCards, "question" + num);
			}

			else if (x >= 158 && x <= 316 && y >= 355 && y <= 525&& !correct4) 
			{
				cards.show(panelCards, "question" + num);
			} 

			else if (x >= 78 && y >= 540 && x <= 192 && y <= 693&&!correct5) 
			{
				cards.show(panelCards, "question" + num);
			}

			else if (x >= 210 && y >= 565 && x <= 392 && y <= 759&& !correct6)
			{
				cards.show(panelCards, "question" + num);
			} 

			else if (x >= 981 && y >= 556 && x <= 1131 && y <= 655&&!correct7) 
			{
				cards.show(panelCards, "question" + num);
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
			{
				clueHovered[0] = true;
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			else if(x >= 563 && x <= 685 && y >= 137 && y <= 257)
			{
				clueHovered[1] = true;
				setCursor(new Cursor(Cursor.HAND_CURSOR));

			}

			else if(x >= 909 && x <= 1125 && y >= 142 && y <= 297)
			{
				clueHovered[2] = true;
				setCursor(new Cursor(Cursor.HAND_CURSOR));

			}
			else if(x >= 158 && x <= 316 && y >= 355 && y <= 525)
			{
				clueHovered[3] = true;
				setCursor(new Cursor(Cursor.HAND_CURSOR));

			}

			else if (x>=78 && y>=540 && x<= 192 && y <=693)
			{
				clueHovered[4] = true;
				setCursor(new Cursor(Cursor.HAND_CURSOR));

			}
			else if (x>=210 && y>=565 && x<= 392 && y <=759)
			{
				clueHovered[5] = true;
				setCursor(new Cursor(Cursor.HAND_CURSOR));

			}

			else if (x>=981 && y>=556 && x<= 1131 && y <=655)
			{
				clueHovered[6] = true;
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}


			else
			{
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				for(int i = 0; i < clueHovered.length; i++)
				{
					clueHovered[i] = false;
					cluePressed[i] = false;
				}
			}

			repaint();

		}


	}

	class question1 extends JPanel implements MouseListener
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		centerPanel cp;
		CardLayout cards;
		Image background, questions, options, boxes, arrows, answerImage;
		String question1Text, questionChoice, questionsName, optionsName, boxesName, response;
		Timer waitTimer, lowerTimer;
		boolean drawBox;
		int randNum, repeats, xCord, yCord, answer;
		JFrame frame;

		public question1(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			cp = new centerPanel();
			panelCards = panelCardsIn;
			cards = cardsIn;

			setupLayout();
			addMouseListener(this);

			repeats = 0;
			xCord = 500;
			yCord = 140;
			drawBox = true;

			waitTimerHandler wth = new waitTimerHandler();
			lowerTimerHandler lth = new lowerTimerHandler();
			waitTimer = new Timer(5000, wth); //CHANGE TO 4000
			waitTimer.setRepeats(false);
			lowerTimer = new Timer(150, lth); //CHANGE TO 150

			question1Text = file.substring(file.indexOf("Question 1"), (file.indexOf("-----")));
			randNum = (int)(Math.random() * 5) + 1;
			questionChoice = question1Text.substring(question1Text.indexOf("Choice " + randNum), question1Text.indexOf("-", question1Text.indexOf("Choice " + randNum)));
			answer = Integer.parseInt(questionChoice.substring(questionChoice.length() - 2, questionChoice.length() - 1));
			questionsName = question1Text.substring(question1Text.indexOf("Questions: ") + 11, (question1Text.indexOf("-", question1Text.indexOf("Questions: "))) - 1);
			optionsName = question1Text.substring(question1Text.indexOf("Options: ") + 9, (question1Text.indexOf("-", question1Text.indexOf("Options: "))) - 1);
			boxesName = (question1Text.substring(question1Text.indexOf("Boxes: ") + 7, question1Text.length())).trim();

			retreiveImage();
			makeFont();
		}

		public void retreiveImage()
		{
			try
			{
				background = ImageIO.read(new File("question1BG.png"));
				questions = ImageIO.read(new File(questionsName));
				options = ImageIO.read(new File(optionsName));
				boxes = ImageIO.read(new File(boxesName));
				arrows = ImageIO.read(new File("arrows.png"));
				answerImage = ImageIO.read(new File("answers.png"));
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
				{
					lowerTimer.stop();
					drawBox = false;
				}

				setFocusable(true);
				cp.repaint();
			}
		}

		public void makeFont()
		{
			try
			{
				font = Font.createFont(Font.TRUETYPE_FONT, new File("VT323-Regular.ttf"));
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(font);
			}
			catch (FontFormatException | IOException e)
			{
				System.err.println("Error loading font: " + e.getMessage());
				e.printStackTrace();
			}
		} 

		class centerPanel extends JPanel
		{
			int questionHeight, questionWidth, optionHeight, optionWidth, arrowsHeight, arrowsWidth;

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

				arrowsHeight = arrows.getHeight(this);
				arrowsWidth = arrows.getWidth(this) / 2;

				if(background != null)
					g.drawImage(background, -20, 0, 1050, 870, this);

				g.drawImage(questions, 100, 30, 920, 100, 0, questionHeight * (randNum - 1), questionWidth, questionHeight * randNum, this);
				g.drawImage(arrows, 100, 350, 180, 440, 0, 0, arrowsWidth, arrowsHeight, this);
				g.drawImage(arrows, 840, 350, 920, 440, arrowsWidth, 0, arrowsWidth * 2, arrowsHeight, this);

				g.setColor(Color.RED);

				if(drawBox)
				{
					g.fillRect(xCord, yCord, 20, 20);
				}

				if(!drawBox)
				{
					g.setColor(new Color(0, 153, 0));
					g.fillRect(135 + (answer - 1) * 240, 680, 30, 70);

					g.setColor(new Color(153, 0, 0));

					for(int i = 0; i < 4; i++)
					{
						if(i != (answer - 1))
							g.fillRect(135 + i * 240, 680, 33, 70);
					}

					if(xCord >= 90 + (240 * (answer - 1)) && xCord <= (90 + (240 * (answer - 1))) + 130)
						response = "Correct!";

					else
						response = "Incorrect";

					makeFrame();

					questionsAnswered[0] = true;
					frame.setVisible(true);
				}

				if(repeats == 0)
					waitTimer.start();

				repeats++;

				g.drawImage(options, 100, 130, 920, 290, 0, optionHeight * (randNum - 1), optionWidth, optionHeight * randNum, this);
				g.drawImage(boxes, 70, 620, 900, 150, this);

				/*	g.setFont(font.deriveFont(30f)); --------------------------------------------------------------------------------------------------------------------------
				g.setColor(Color.WHITE);			 --------------------------------------------------------------------------------------------------------------------------
				g.drawString("Hello", 10, 10);*///	 --------------------------------------------------------------------------------------------------------------------------
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

		public void makeFrame()
		{
			frame = new JFrame(response);
			frame.setSize(300, 200);				
			frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			answerFrameHandler afh = new answerFrameHandler();
			frame.getContentPane().add(afh);
			frame.setLocationRelativeTo(null);
			frame.setVisible(false);	
		}

		class answerFrameHandler extends JPanel
		{
			public answerFrameHandler()
			{
				setBackground(Color.WHITE);
				setLayout(null);

				JLabel title = new JLabel(response);
				JButton returnToClue;
				title.setFont(new Font("Monospaced", Font.PLAIN, 25));
				title.setBounds(130, 20, 150, 40);
				title.setForeground(Color.BLACK);
				title.setVisible(true);
				add(title);

				if(response.equals("Correct!"))
					returnToClue = new JButton("View clue");

				else 
					returnToClue = new JButton("Return to clue page");

				returnToClue.setFont(new Font("Monospaced", Font.PLAIN, 10));
				returnToClue.setBounds(120, 60, 160, 70);
				returnToClue.setForeground(Color.BLACK);
				returnToClue.setVisible(true);
				buttonHandler ibh = new buttonHandler();
				returnToClue.addActionListener(ibh);
				add(returnToClue);
			}

			class buttonHandler implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{	
					if(response.equals("Incorrect"))
					{
						cards.show(panelCards, "secondClueBoard");
						showClueVid = false;
					}

					else
						cards.show(panelCards, "clue1");

					questionsAnswered[0] = true;
					frame.dispose();
				}
			}

			public void paintComponent(Graphics g)
			{
				int answerWidth = answerImage.getWidth(this) / 2;
				int answerHeight = answerImage.getHeight(this);

				if(response.equals("Correct!"))
				{
					g.drawImage(answerImage, 20, 20, 100, 130, 0, 0, answerWidth, answerHeight, this);
					correct1 = true;
				}

				else if(response.equals("Incorrect"))
					g.drawImage(answerImage, 17, 20, 100, 130, answerWidth, 0, answerWidth * 2, answerHeight, this);
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

		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= 100 && x <= 180 && y >= 350 && y <= 440)
				xCord -= 15;

			if(x >= 840 && x <= 920 && y >= 350 && y <= 440)
				xCord += 15;

			repaint();
		}

		public void mousePressed(MouseEvent e)
		{

		}

		public void mouseReleased(MouseEvent e)
		{

		}

		public void mouseEntered(MouseEvent e)
		{

		}

		public void mouseExited(MouseEvent e)
		{

		}
	}

	class clue1 extends JPanel implements MouseListener
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;

		Image clueClip, clueBG;
		boolean drawImage;
		Timer stopClipTimer;
		int repeats;

		public clue1(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			panelCards = panelCardsIn;
			cards = cardsIn;
			addMouseListener(this);
			retreiveImage();
			ImageIcon storeGif = new ImageIcon("clue1.gif");   
			stopClipTimerHandler scth = new stopClipTimerHandler();
			clueClip = storeGif.getImage();
			stopClipTimer = new Timer(15000, scth);
			drawImage = false;
			repeats = 0;
		}

		public void retreiveImage()
		{
			try
			{
				clueBG = ImageIO.read(new File("clueBG.png"));
			}

			catch(IOException e)
			{
				System.err.println("\n\n\nERROR IN RETRIEVING IMAGE\n\n\n");
				e.printStackTrace();
			}
		}

		class stopClipTimerHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				drawImage = true;
				stopClipTimer.stop();
				repaint();
			}
		}

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);

			if(repeats == 0)
				stopClipTimer.start();

			repeats++;

			if(drawImage)
				g.drawImage(clueBG, 0, 0, 1300, 800, this);

			else
				g.drawImage(clueClip, 0, 0, 1300, 800, this);

			g.setColor(Color.WHITE);
			g.drawLine(1240, 10, 1290, 60);
			g.drawLine(1290, 10, 1240, 60);

		}

		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= 1260 && x <= 1300 && y >= 0 && y <= 50)
			{
				cards.show(panelCards, "secondClueBoard");
				showClueVid = false;
			}
		}

		public void mousePressed(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}
	}

	class question2 extends JPanel implements MouseListener, MouseMotionListener
	{
		String answer, response;
		protected boolean displayQ = false;
		String question = "";
		protected boolean DrawQ = true;
		protected Scanner input;
		protected boolean darkenButton;
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;
		Image background, answerImage;
		JFrame frame;
		
		public question2(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			panelCards = panelCardsIn;
			cards = cardsIn;
			retreiveImage();


			setupLayout();
			repaint();
			addMouseListener(this);
			addMouseMotionListener(this);
			requestFocusInWindow();
			setFocusable(true);
			makeFont();
		}


		public void retreiveImage()
		{
			try
			{
				background = ImageIO.read(new File("question1BG.png"));
				answerImage = ImageIO.read(new File("answers.png"));
			}
			
			catch(IOException e)
			{
				System.err.println("\n\n\nERROR IN RETRIEVING IMAGE\n\n\n");
				e.printStackTrace();
			}
		}

		public void makeFont()
		{
			try
			{
				font = Font.createFont(Font.TRUETYPE_FONT, new File("VT323-Regular.ttf"));
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(font);
			}
			catch (FontFormatException | IOException e)
			{
				System.err.println("Error loading font: " + e.getMessage());
				e.printStackTrace();
			}
		}

		class centerPanel extends JPanel
		{
			String ans1, ans2, ans3, ans4;
			Image questionBase;
			
			public centerPanel()
			{
				DrawQuestion();
				repaint();
			}
			
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)(g);
				if(background != null)
				{

					g.drawImage(background, -20, 0, 1050, 870, this);
				}
				// Draw the "Grade Answer" image
				Image grader = new ImageIcon("GradeAnswer.png").getImage();
				g.drawImage(grader, 375, 700, 300, 50, this);

				Image hint = new ImageIcon("forensicButton.png").getImage();
				g.drawImage(hint, 50, 695, 50, 60, this);
				
				if (darkenButton)
				{
					g.setColor(new Color(0, 0, 0, 80));
					g.fillRect(375, 700, 300, 50);
				}
				
				questionBase = new ImageIcon("questionBase.png").getImage();
				g.drawImage(questionBase, 50, -85, 920, 860, this);
				g.setFont(font.deriveFont(15f));
				g.setColor(Color.WHITE);


				if(displayQ)
				{
					g.setFont(font.deriveFont(30f));
					g.setColor(Color.WHITE);

					int maxWidth = 700; // Maximum width for a single line
					int x = 175; // Starting x-coordinate
					int y = 90; // Starting y-coordinate
					int lineHeight = g.getFontMetrics().getHeight(); // Line height

					String[] words = question.split(" ");
					StringBuilder line = new StringBuilder();

					for(String word : words)
					{
						String testLine = line + word + " ";
						int lineWidth = g.getFontMetrics().stringWidth(testLine);

						if(lineWidth > maxWidth)
						{
							g.drawString(line.toString(), x, y);
							line = new StringBuilder(word + " ");
							y += lineHeight;
						}
						else
							line.append(word).append(" ");
					}

					// Draw the last line
					if (line.length() > 0) {
						g.drawString(line.toString(), x, y);
					}
				}

			}

			public void DrawQuestion()
			{
				int questionVariant;


				File inFile = new File("questions.txt");
				try
				{
					input = new Scanner(inFile);
				}
				catch(FileNotFoundException e)
				{
					System.err.printf("\n\n\nERROR: Cannot find/open file %s.\n\n\n","questions.txt");
					System.exit(1);
				}

				if(DrawQ)
				{
					while (input.hasNext())
					{
						question = input.next().trim(); // Trim to remove extra spaces


						if (question.equalsIgnoreCase("Question2:")) 
						{
							System.out.println("Question2 found");
							break; 
						}
					}
				}
				
				DrawQ = false;
				//choose between 1-5

				questionVariant =(int)(Math.random() * 5) + 1;
				System.out.println(questionVariant);
				switch(questionVariant)
				{
				case 1:
					while (input.hasNext())
					{
						question = input.next().trim(); // Trim to remove extra spaces

						if (question.equalsIgnoreCase("Sub1:")) 
						{
							System.out.println("Sub1 found");
							break; 
						}
					}


					break;
					
				case 2:
					while (input.hasNext())
					{
						question = input.next().trim(); // Trim to remove extra spaces

						if (question.equalsIgnoreCase("Sub2:")) 
						{
							System.out.println("Sub1 found");
							break; 
						}
					}

					break;
					
				case 3:
					while (input.hasNext())
					{
						question = input.next().trim(); // Trim to remove extra spaces

						if (question.equalsIgnoreCase("Sub3:")) 
						{
							System.out.println("Sub1 found");
							break; 
						}
					}

					break;
					
				case 4:
					while (input.hasNext())
					{
						question = input.next().trim(); // Trim to remove extra spaces

						if (question.equalsIgnoreCase("Sub4:")) 
						{
							System.out.println("Sub1 found");
							break; 
						}
					}


					break;
					
				case 5:
					while (input.hasNext())
					{
						question = input.next().trim(); // Trim to remove extra spaces

						if (question.equalsIgnoreCase("Sub5:")) 
						{
							System.out.println("Sub1 found");
							break; 
						}
					}


					break;
				default:
					System.out.println("Invalid question variant.");
				}
				
				displayQ = true;
				question = input.nextLine();
				ans1 = input.nextLine();
				ans2 = input.nextLine();
				ans3 = input.nextLine();
				ans4 = input.nextLine();
				//create radio buttons
				JPanel radioPanel = new JPanel();
				radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS)); 
				radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS)); // Vertical alignment
				radioPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left
				radioPanel.setBorder(BorderFactory.createEmptyBorder(300, 180, 20, 50)); // Add padding

				// Set layout to BoxLayout for vertical alignment
				radioPanel.setBounds(150, 300, 800, 200); // Set bounds for the panel

				radioPanel.setBackground(new Color(0, 0, 0, 0)); // Set transparent background
				// Create the radio buttons
				JRadioButton button1 = new JRadioButton(ans1);
				JRadioButton button2 = new JRadioButton(ans2);
				JRadioButton button3 = new JRadioButton(ans3);
				JRadioButton button4 = new JRadioButton(ans4);

				// Add the radio buttons to a ButtonGroup
				ButtonGroup group = new ButtonGroup();

				group.add(button1);
				group.add(button2);
				group.add(button3);
				group.add(button4);

				// Add the radio buttons to the panel
				radioPanel.add(button1);
				radioPanel.add(button2);
				radioPanel.add(button3);
				radioPanel.add(button4);

				// Customize the appearance of the radio buttons
				button1.setOpaque(false);
				button2.setOpaque(false);
				button3.setOpaque(false);
				button4.setOpaque(false);
				button1.setBackground(null);
				button2.setBackground(null);
				button3.setBackground(null);
				button4.setBackground(null);

				try
				{
					font = Font.createFont(Font.TRUETYPE_FONT, new File("VT323-Regular.ttf"));
					GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
					ge.registerFont(font);
				}
				catch (FontFormatException | IOException e)
				{
					System.err.println("Error loading font: " + e.getMessage());
					e.printStackTrace();
				}
				// Add the panel to your main container

				// Set font for the radio buttons
				Font customFont = font.deriveFont(30f);
				button1.setFont(customFont);
				button2.setFont(customFont);
				button3.setFont(customFont);
				button4.setFont(customFont);
				button1.setOpaque(false);
				button2.setOpaque(false);
				button3.setOpaque(false);
				button4.setOpaque(false);

				button1.setForeground(Color.WHITE);
				button2.setForeground(Color.WHITE);
				button3.setForeground(Color.WHITE);

				button4.setForeground(Color.WHITE);
				answer = input.nextLine();
				button1.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						System.out.println(answer);
						System.out.println(answer.equals("1"));
						System.out.println("Button 1 clicked");
						if (button1.isSelected())
						{


							if(answer.equals("1"))
							{
								panelCards.correct2 = true;
								panelCards.wrong2 = false;
							}
							else
							{
								panelCards.wrong2 = true;
								panelCards.correct2 = false;
							}

						} 
						//System.out.println(correct1 + " " + correct2 + " " + correct3 + " " + correct4 + " " + correct5 + " " + correct6 + " " + correct7);

					}
				});
				
				button2.addActionListener(new ActionListener()
				{

					public void actionPerformed(ActionEvent e) 
					{

						System.out.println(answer.equals("2"));
						System.out.println(answer);

						System.out.println("Button 2 clicked");
						if (button2.isSelected())
						{

							if(answer.equals("2"))
							{
								panelCards.correct2 = true;
								panelCards.wrong2 = false;
							}
							else
							{
								panelCards.wrong2 = true;
								panelCards.correct2 = false;
							}
						} 
						//System.out.println(correct1 + " " + correct2 + " " + correct3 + " " + correct4 + " " + correct5 + " " + correct6 + " " + correct7);

					}
				});
				
				button3.addActionListener(new ActionListener() 
				{

					public void actionPerformed(ActionEvent e) 
					{
						System.out.println(answer);
						System.out.println(answer.equals("3"));
						System.out.println("Button 3 clicked");
						if (button3.isSelected())
						{

							if(answer.equals("3"))
							{
								panelCards.correct2 = true;
								panelCards.wrong2 = false;
							}
							else
							{
								panelCards.wrong2 = true;
								panelCards.correct2 = false;
							}
						} 
						//System.out.println(correct1 + " " + correct2 + " " + correct3 + " " + correct4 + " " + correct5 + " " + correct6 + " " + correct7);

					}
				});

				button4.addActionListener(new ActionListener() 
				{

					public void actionPerformed(ActionEvent e) 
					{
						System.out.println(answer);
						System.out.println("Button 4 clicked");
						System.out.println(answer.equals("4"));
						if (button4.isSelected())
						{


							if(answer.equals("4"))
							{

								panelCards.correct2 = true;
								panelCards.wrong2 = false;

							}
							else
							{
								panelCards.wrong2 = true;
								panelCards.correct2 = false;
							}
							//	System.out.println(correct1 + " " + correct2 + " " + correct3 + " " + correct4 + " " + correct5 + " " + correct6 + " " + correct7);

						} 
					}
				});

				makeFrame();
				add(radioPanel);
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
		
		public void makeFrame()
		{			
			if(correct2)
				response = "Correct!";
			
			else if(!correct2)
				response = "Incorrect";
			
			frame = new JFrame(response);
			frame.setSize(300, 200);				
			frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			answerFrameHandler afh = new answerFrameHandler();
			frame.getContentPane().add(afh);
			frame.setLocationRelativeTo(null);
			frame.setVisible(false);	
		}
		
		class answerFrameHandler extends JPanel
		{
			public answerFrameHandler()
			{
				setBackground(Color.WHITE);
				setLayout(null);

				JLabel title = new JLabel(response);
				JButton returnToClue;
				title.setFont(new Font("Monospaced", Font.PLAIN, 25));
				title.setBounds(130, 20, 150, 40);
				title.setForeground(Color.BLACK);
				title.setVisible(true);
				add(title);

				if(response.equals("Correct!"))
					returnToClue = new JButton("View clue");

				else 
					returnToClue = new JButton("Return to clue page");

				returnToClue.setFont(new Font("Monospaced", Font.PLAIN, 10));
				returnToClue.setBounds(120, 60, 160, 70);
				returnToClue.setForeground(Color.BLACK);
				returnToClue.setVisible(true);
				buttonHandler ibh = new buttonHandler();
				returnToClue.addActionListener(ibh);
				add(returnToClue);
			}

			class buttonHandler implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{	
					if(response.equals("Incorrect"))
					{
						cards.show(panelCards, "secondClueBoard");
						showClueVid = false;
					}

					else
						cards.show(panelCards, "clue1");

					questionsAnswered[0] = true;
					frame.dispose();
				}
			}

			public void paintComponent(Graphics g)
			{
				int answerWidth = answerImage.getWidth(this) / 2;
				int answerHeight = answerImage.getHeight(this);

				if(response.equals("Correct!"))
				{
					g.drawImage(answerImage, 20, 20, 100, 130, 0, 0, answerWidth, answerHeight, this);
					correct1 = true;
				}

				else if(response.equals("Incorrect"))
					g.drawImage(answerImage, 17, 20, 100, 130, answerWidth, 0, answerWidth * 2, answerHeight, this);
			}
		}

		centerPanel cp = new centerPanel();
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


		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();
			System.out.println("Mouse clicked at: " + x + ", " + y);
			// Check if the click is within the bounds of the "Grade Answer" image
			if(x >= 375 && x <= 675 && y >= 700 && y <= 750) //---------------------------------------------------------------------------------------------------------------
			{
				frame.setVisible(true);
				
				panelCards.repaint();
			}
			else if(x >= 28 && x <= 66 && y >= 695 && y <= 755) 
			{
				cards.show(panelCards, "forensicsReport");
			}
		}
		public void mouseDragged(MouseEvent e) {}
		public void mouseMoved(MouseEvent e) 
		{
			int x = e.getX();
			int y = e.getY();
			//System.out.println("Mouse moved at: " + x + ", " + y);
			// Check if the mouse is within the bounds of the "Grade Answer" image
			if (x >= 345 && x <= 650 && y >= 700 && y <= 750) 
			{
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				darkenButton = true;
				repaint();
			} 
			
			else 
			{
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				darkenButton = false;
				repaint();
			}
		}
		
		public void mousePressed(MouseEvent e) 
		{

			int x = e.getX();
			int y = e.getY();
			
			if (x >= 375 && x <= 675 && y >= 700 && y <= 750) 
			{
				darkenButton = true;
				repaint();
			}
		}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e)
		{
		}
		public void mouseExited(MouseEvent e) 
		{
		}

	}

	class question3 extends JPanel implements MouseListener, MouseMotionListener
	{
		int x, y;
		boolean drawCircle;
		Image questionBase, diaphragm;
		protected boolean darkenButton;
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;
		Image background;
		public question3(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			panelCards = panelCardsIn;
			cards = cardsIn;
			retreiveImage();


			setupLayout();
			repaint();
			addMouseListener(this);
			addMouseMotionListener(this); 	
		}

		public void retreiveImage()
		{

			try
			{
				background = ImageIO.read(new File("question1BG.png"));
			}
			catch(IOException e)
			{
				System.err.println("\n\n\nERROR IN RETRIEVING IMAGE\n\n\n");
				e.printStackTrace();
			}
			try
			{
				diaphragm = ImageIO.read(new File("diaphragm.png"));

			}
			catch(IOException e)
			{
				System.err.println("\n\n\nERROR IN RETRIEVING IMAGE\n\n\n");
				e.printStackTrace();
			}
		}



		class centerPanel extends JPanel
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)(g);
				if(background != null)
				{

					g.drawImage(background, -20, 0, 1050, 870, this);
				}
				// Draw the "Grade Answer" image
				Image grader = new ImageIcon("GradeAnswer.png").getImage();
				g.drawImage(grader, 375, 700, 300, 50, this);

				Image hint = new ImageIcon("forensicButton.png").getImage();
				g.drawImage(hint, 50, 695, 50, 60, this);
				if (darkenButton)
				{
					g.setColor(new Color(0, 0, 0, 80));
					g.fillRect(375, 700, 300, 50);
				}
				questionBase = new ImageIcon("questionBase.png").getImage();
				g.drawImage(questionBase, 50, -85, 920, 750, this);
				g.drawImage(diaphragm, 325, 190, 400, 500, this);
				g.setFont(font.deriveFont(25f));
				g.setColor(Color.BLACK);

				g.drawString("The \"DIAPHRAGM REPORT\" describes the diaphragm's role in breathing.", 175, 130);
				g.drawString(" Click on the image below representing the diaphragm.", 175, 175);
				if(drawCircle)
				{
					g.setColor(Color.RED);
					g.fillOval(x+30, y, 5, 5);

				}	

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

		centerPanel cp = new centerPanel();
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


		public void mouseClicked(MouseEvent e)
		{
			x = e.getX();
			y = e.getY();
			if(x>=392 && y>=508 && x<= 601 && y<616)
			{
				panelCards.correct1 = true;
				System.out.println("Correct Answer");



			}
			else if (x >= 375 && x <= 675 && y >= 700 && y <= 750) 
			{
				System.out.println("Grade Answer button clicked");
				cards.show(panelCards, "clueBoard");
				drawCircle = true;
				panelCards.repaint(); // Trigger repaint to update the UI
			}
			else if (x >= 50 && x <= 100 && y >= 695 && y <= 755) 
			{
				cards.show(panelCards, "forensicsReport");
			}
			else
			{
				panelCards.correct1 = false;

			}
			drawCircle = true;

			System.out.println(correct1 + " " + correct2 + " " + correct3 + " " + correct4 + " " + correct5 + " " + correct6 + " " + correct7);
			repaint();
		}
		public void mousePressed(MouseEvent e) {}		
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e)
		{
			int x1 = e.getX();
			int y1 = e.getY();
			if(x1>=375 && x1<=675 && y1>=700 && y1<=750)
			{

				darkenButton = true;
				repaint();

			}
		}
		public void mouseExited(MouseEvent e) {}


		public void mouseDragged(MouseEvent e) 
		{


		}


		public void mouseMoved(MouseEvent e)
		{

			int x2 = e.getX();
			int y2= e.getY();


			// Check if the mouse is within the bounds of the "Grade Answer" image
			if (x2 >= 345 && x2 <= 650 && y2 >= 700 && y2 <= 750) 
			{
				System.out.println("Mouse over Grade Answer button");
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				darkenButton = true;
				repaint();
			} 
			else 
			{
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				darkenButton = false;
				repaint();
			}

		}

	}

	class question4 extends JPanel implements MouseListener
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		centerPanel cp;
		CardLayout cards;
		Image background, questionPaper, optionPaper, boxes, arrows, answerImage;
		String questionText, questionChoice, question, questionPaperName, optionPaperName, boxesName, response, options;
		String[] indOptions;
		Timer waitTimer, lowerTimer;
		boolean drawBox;
		int randNum, repeats, xCord, yCord, answer;
		JFrame frame;

		public question4(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			cp = new centerPanel();
			panelCards = panelCardsIn;
			cards = cardsIn;

			setupLayout();
			addMouseListener(this);

			repeats = 0;
			xCord = 500;
			yCord = 150;
			drawBox = true;

			waitTimerHandler wth = new waitTimerHandler();
			lowerTimerHandler lth = new lowerTimerHandler();
			waitTimer = new Timer(6000, wth); //CHANGE TO 4000
			waitTimer.setRepeats(false);
			lowerTimer = new Timer(160, lth); //CHANGE TO 150
			indOptions = new String[4];

			questionText = file.substring(file.indexOf("Question 4"), file.indexOf("-----", file.indexOf("Question 4")));
			randNum = (int)(Math.random() * 5) + 1;
			questionChoice = questionText.substring(questionText.indexOf("Choice " + randNum), questionText.indexOf("-", questionText.indexOf("Choice " + randNum)));
			question = questionChoice.substring(questionChoice.indexOf(":") + 2, questionChoice.indexOf("?") + 1);
			options = questionChoice.substring(questionChoice.indexOf("1."), questionChoice.length());
			questionPaperName = questionText.substring(questionText.indexOf("Questions: ") + 11, questionText.indexOf("-", questionText.indexOf("Questions: ")) - 1);
			optionPaperName = questionText.substring(questionText.indexOf("Options: ") + 9, questionText.indexOf("-", questionText.indexOf("Options: ")) - 1);
			boxesName = questionText.substring(questionText.indexOf("Boxes: ") + 7, questionText.length() - 1);
			answer = Integer.parseInt(questionChoice.substring(questionChoice.indexOf("?") + 2, questionChoice.indexOf("?") + 3));		

			for(int i = 0; i < indOptions.length; i++)
			{	
				if(i == 3)
					indOptions[i] = options.substring(options.indexOf(i + 1 + "."));

				else
					indOptions[i] = options.substring(options.indexOf(i + 1 + "."), options.indexOf(i + 2 + "."));
			}

			/*		question1Text = file.substring(file.indexOf("Question 4"), (file.indexOf("-----", file.indexOf("Question 4"))));
			randNum = (int)(Math.random() * 5) + 1;
			questionChoice = question1Text.substring(question1Text.indexOf("Choice " + randNum), question1Text.indexOf("-", question1Text.indexOf("Choice " + randNum)));
			answer = Integer.parseInt(questionChoice.substring(questionChoice.length() - 2, questionChoice.length() - 1));
			questionsName = question1Text.substring(question1Text.indexOf("Questions: ") + 11, (question1Text.indexOf("-", question1Text.indexOf("Questions: "))) - 1);
			System.out.println(questionsName);
			optionsName = question1Text.substring(question1Text.indexOf("Options: ") + 9, (question1Text.indexOf("-", question1Text.indexOf("Options: "))) - 1);
			boxesName = (question1Text.substring(question1Text.indexOf("Boxes: ") + 7, question1Text.length())).trim();*/

			retreiveImage();
			makeFont();
		}

		public void retreiveImage()
		{
			try
			{
				background = ImageIO.read(new File("question1BG.png"));
				questionPaper = ImageIO.read(new File(questionPaperName));
				optionPaper = ImageIO.read(new File(optionPaperName));
				boxes = ImageIO.read(new File(boxesName));
				arrows = ImageIO.read(new File("arrows.png"));
				answerImage = ImageIO.read(new File("answers.png"));
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
				{
					lowerTimer.stop();
					drawBox = false;
				}

				setFocusable(true);
				cp.repaint();
			}
		}

		public void makeFont()
		{
			try
			{
				font = Font.createFont(Font.TRUETYPE_FONT, new File("VT323-Regular.ttf"));
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(font);
			}
			catch (FontFormatException | IOException e)
			{
				System.err.println("Error loading font: " + e.getMessage());
				e.printStackTrace();
			}
		} 

		class centerPanel extends JPanel
		{
			int questionHeight, questionWidth, optionHeight, optionWidth, arrowsHeight, arrowsWidth;

			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)(g);

				requestFocusInWindow();
				setFocusable(true);

				arrowsHeight = arrows.getHeight(this);
				arrowsWidth = arrows.getWidth(this) / 2;

				if(background != null)
					g.drawImage(background, -20, 0, 1050, 870, this);

				g.drawImage(questionPaper, 60, 35, 900, 80, this);
				g.drawImage(arrows, 100, 350, 180, 440, 0, 0, arrowsWidth, arrowsHeight, this);
				g.drawImage(arrows, 840, 350, 920, 440, arrowsWidth, 0, arrowsWidth * 2, arrowsHeight, this);

				g.setColor(Color.RED);

				if(drawBox)
				{
					g.fillRect(xCord, yCord, 20, 20);
				}

				if(!drawBox)
				{
					g.setColor(new Color(0, 153, 0));
					g.fillRect(135 + (answer - 1) * 240, 680, 30, 70);

					g.setColor(new Color(153, 0, 0));

					for(int i = 0; i < 4; i++)
					{
						if(i != (answer - 1))
							g.fillRect(135 + i * 240, 680, 33, 70);
					}

					if(xCord >= 90 + (240 * (answer - 1)) && xCord <= (90 + (240 * (answer - 1))) + 130)
						response = "Correct!";

					else
						response = "Incorrect";

					makeFrame();

					frame.setVisible(true);
				}

				if(repeats == 0)
					waitTimer.start();

				repeats++;

				g.drawImage(optionPaper, 60, 140, 900, 200, this);
				g.drawImage(boxes, 70, 620, 900, 150, this);

				g.setFont(font.deriveFont(45f));
				g.setColor(Color.BLACK);		
				g.drawString(question, 150, 87);

				g.setFont(font.deriveFont(36f));
				g.setColor(Color.BLACK);		

				for(int i = 0; i < indOptions.length; i++)
					g.drawString(indOptions[i], 170, 180  + (i * 45));
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

		public void makeFrame()
		{
			frame = new JFrame(response);
			frame.setSize(300, 200);				
			frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			answerFrameHandler afh = new answerFrameHandler();
			frame.getContentPane().add(afh);
			frame.setLocationRelativeTo(null);
			frame.setVisible(false);	
		}

		class answerFrameHandler extends JPanel
		{
			public answerFrameHandler()
			{
				setBackground(Color.WHITE);
				setLayout(null);

				JLabel title = new JLabel(response);
				JButton returnToClue;
				title.setFont(font.deriveFont(40f));
				title.setBounds(130, 20, 150, 40);
				title.setForeground(Color.BLACK);
				title.setVisible(true);
				add(title);

				if(response.equals("Correct!"))
				{
					returnToClue = new JButton("View clue");
					returnToClue.setFont(font.deriveFont(20f));
				}

				else
				{
					returnToClue = new JButton("Return to clue page");
					returnToClue.setFont(font.deriveFont(15f));
				}

				returnToClue.setBounds(120, 60, 140, 50);
				returnToClue.setForeground(Color.BLACK);
				returnToClue.setBackground(Color.WHITE);
				returnToClue.setVisible(true);
				buttonHandler ibh = new buttonHandler();
				returnToClue.addActionListener(ibh);
				add(returnToClue);
			}

			class buttonHandler implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{	
					if(response.equals("Incorrect"))
					{
						cards.show(panelCards, "secondClueBoard");
						showClueVid = false;
					}

					else
						cards.show(panelCards, "clue1");

					frame.dispose();
				}
			}

			public void paintComponent(Graphics g)
			{
				int answerWidth = answerImage.getWidth(this) / 2;
				int answerHeight = answerImage.getHeight(this);

				if(response.equals("Correct!"))
				{
					g.drawImage(answerImage, 20, 20, 100, 130, 0, 0, answerWidth, answerHeight, this);
					correct1 = true;
				}

				else if(response.equals("Incorrect"))
					g.drawImage(answerImage, 17, 20, 100, 130, answerWidth, 0, answerWidth * 2, answerHeight, this);
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

		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= 100 && x <= 180 && y >= 350 && y <= 440)
				xCord -= 15;

			if(x >= 840 && x <= 920 && y >= 350 && y <= 440)
				xCord += 15;

			repaint();
		}

		public void mousePressed(MouseEvent e)
		{

		}

		public void mouseReleased(MouseEvent e)
		{

		}

		public void mouseEntered(MouseEvent e)
		{

		}

		public void mouseExited(MouseEvent e)
		{

		}
	}

	class question5 extends JPanel implements MouseListener, MouseMotionListener
	{

		String answer;
		protected boolean displayQ = false;
		String question ="";
		protected boolean DrawQ = true;
		protected Scanner input;

		protected boolean darkenButton;
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;
		Image background;

		public question5(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			panelCards = panelCardsIn;
			cards = cardsIn;
			retreiveImage();


			setupLayout();
			repaint();
			addMouseListener(this);
			addMouseMotionListener(this);
			requestFocusInWindow();
			setFocusable(true);
			makeFont();
		}

		public void retreiveImage()
		{
			try
			{
				background = ImageIO.read(new File("question1BG.png"));
			}
			catch(IOException e)
			{
				System.err.println("\n\n\nERROR IN RETRIEVING IMAGE\n\n\n");
				e.printStackTrace();
			}
		}

		public void makeFont()
		{
			try
			{
				font = Font.createFont(Font.TRUETYPE_FONT, new File("VT323-Regular.ttf"));
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(font);
			}
			catch (FontFormatException | IOException e)
			{
				System.err.println("Error loading font: " + e.getMessage());
				e.printStackTrace();
			}
		}

		class centerPanel extends JPanel
		{
			String ans1, ans2, ans3, ans4;
			Image questionBase;
			public centerPanel()
			{
				DrawQuestion();
				repaint();
			}
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)(g);
				if(background != null)
				{

					g.drawImage(background, -20, 0, 1050, 870, this);
				}
				// Draw the "Grade Answer" image
				Image grader = new ImageIcon("GradeAnswer.png").getImage();
				g.drawImage(grader, 375, 700, 300, 50, this);

				Image hint = new ImageIcon("forensicButton.png").getImage();
				g.drawImage(hint, 50, 695, 50, 60, this);
				if (darkenButton)
				{
					g.setColor(new Color(0, 0, 0, 80));
					g.fillRect(375, 700, 300, 50);
				}
				questionBase = new ImageIcon("questionBase.png").getImage();
				g.drawImage(questionBase, 50, -85, 920, 860, this);
				g.setFont(font.deriveFont(15f));
				g.setColor(Color.WHITE);


				if (displayQ) {
					g.setFont(font.deriveFont(30f));
					g.setColor(Color.WHITE);

					int maxWidth = 700; // Maximum width for a single line
					int x = 175; // Starting x-coordinate
					int y = 90; // Starting y-coordinate
					int lineHeight = g.getFontMetrics().getHeight(); // Line height

					String[] words = question.split(" ");
					StringBuilder line = new StringBuilder();

					for (String word : words) {
						String testLine = line + word + " ";
						int lineWidth = g.getFontMetrics().stringWidth(testLine);

						if (lineWidth > maxWidth) {
							g.drawString(line.toString(), x, y);
							line = new StringBuilder(word + " ");
							y += lineHeight;
						} else {
							line.append(word).append(" ");
						}
					}

					// Draw the last line
					if (line.length() > 0) {
						g.drawString(line.toString(), x, y);
					}
				}

			}

			public void DrawQuestion()
			{
				int questionVariant;


				File inFile = new File("questions.txt");
				try
				{
					input = new Scanner(inFile);
				}
				catch(FileNotFoundException e)
				{
					System.err.printf("\n\n\nERROR: Cannot find/open file %s.\n\n\n","questions.txt");
					System.exit(1);
				}

				if(DrawQ)
				{
					while (input.hasNext())
					{
						question = input.next().trim(); // Trim to remove extra spaces


						if (question.equalsIgnoreCase("Question3:")) 
							break; 
					}
				}
				DrawQ = false;
				//choose between 1-5

				questionVariant =(int) (Math.random()*5)+1;
				System.out.println(questionVariant);
				switch(questionVariant)
				{
				case 1:
					while (input.hasNext())
					{
						question = input.next().trim(); // Trim to remove extra spaces
						System.out.println("Question: " + question); // Debugging output

						if (question.equalsIgnoreCase("Sub1:")) 
						{
							System.out.println("Sub1 found");
							break; 
						}
					}


					break;
				case 2:
					while (input.hasNext())
					{
						question = input.next().trim(); // Trim to remove extra spaces
						System.out.println("Question: " + question); // Debugging output

						if (question.equalsIgnoreCase("Sub2:")) 
						{
							System.out.println("Sub1 found");
							break; 
						}
					}

					break;
				case 3:
					while (input.hasNext())
					{
						question = input.next().trim(); // Trim to remove extra spaces
						System.out.println("Question: " + question); // Debugging output

						if (question.equalsIgnoreCase("Sub3:")) 
						{
							System.out.println("Sub1 found");
							break; 
						}
					}


					break;
				case 4:
					while (input.hasNext())
					{
						question = input.next().trim(); // Trim to remove extra spaces
						System.out.println("Question: " + question); // Debugging output

						if (question.equalsIgnoreCase("Sub4:")) 
						{
							System.out.println("Sub1 found");
							break; 
						}
					}


					break;
				case 5:
					while (input.hasNext())
					{
						question = input.next().trim(); // Trim to remove extra spaces
						System.out.println("Question: " + question); // Debugging output

						if (question.equalsIgnoreCase("Sub5:")) 
						{
							System.out.println("Sub1 found");
							break; 
						}
					}


					break;
				default:
					System.out.println("Invalid question variant.");
				}
				displayQ = true;
				question = input.nextLine();
				ans1 = input.nextLine();
				ans2 = input.nextLine();
				ans3 = input.nextLine();
				ans4 = input.nextLine();
				//create radio buttons
				JPanel radioPanel = new JPanel();
				radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS)); 
				radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS)); // Vertical alignment
				radioPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left
				radioPanel.setBorder(BorderFactory.createEmptyBorder(300, 180, 20, 50)); // Add padding

				// Set layout to BoxLayout for vertical alignment
				radioPanel.setBounds(150, 300, 800, 200); // Set bounds for the panel

				radioPanel.setBackground(new Color(0, 0, 0, 0)); // Set transparent background
				// Create the radio buttons
				JRadioButton button1 = new JRadioButton(ans1);
				JRadioButton button2 = new JRadioButton(ans2);
				JRadioButton button3 = new JRadioButton(ans3);
				JRadioButton button4 = new JRadioButton(ans4);

				// Add the radio buttons to a ButtonGroup
				ButtonGroup group = new ButtonGroup();

				group.add(button1);
				group.add(button2);
				group.add(button3);
				group.add(button4);

				// Add the radio buttons to the panel
				radioPanel.add(button1);
				radioPanel.add(button2);
				radioPanel.add(button3);
				radioPanel.add(button4);

				// Customize the appearance of the radio buttons
				button1.setOpaque(false);
				button2.setOpaque(false);
				button3.setOpaque(false);
				button4.setOpaque(false);
				button1.setBackground(null);
				button2.setBackground(null);
				button3.setBackground(null);
				button4.setBackground(null);

				try
				{
					font = Font.createFont(Font.TRUETYPE_FONT, new File("VT323-Regular.ttf"));
					GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
					ge.registerFont(font);
				}
				catch (FontFormatException | IOException e)
				{
					System.err.println("Error loading font: " + e.getMessage());
					e.printStackTrace();
				}
				// Add the panel to your main container

				// Set font for the radio buttons
				Font customFont = font.deriveFont(30f);
				button1.setFont(customFont);
				button2.setFont(customFont);
				button3.setFont(customFont);
				button4.setFont(customFont);
				button1.setOpaque(false);
				button2.setOpaque(false);
				button3.setOpaque(false);
				button4.setOpaque(false);

				button1.setForeground(Color.WHITE);
				button2.setForeground(Color.WHITE);
				button3.setForeground(Color.WHITE);

				button4.setForeground(Color.WHITE);
				answer = input.nextLine();
				button1.addActionListener(new ActionListener() 
				{


					public void actionPerformed(ActionEvent e) 
					{
						System.out.println(answer);
						System.out.println(answer.equals("1"));
						System.out.println("Button 1 clicked");
						if (button1.isSelected())
						{


							if(answer.equals("1"))
							{
								correct2 = true;
								panelCards.wrong2 = false;
							}
							else
							{
								panelCards.wrong2 = true;
								correct2 = false;
							}

						} 
						//System.out.println(correct1 + " " + correct2 + " " + correct3 + " " + correct4 + " " + correct5 + " " + correct6 + " " + correct7);

					}
				});
				button2.addActionListener(new ActionListener()
				{

					public void actionPerformed(ActionEvent e) 
					{

						System.out.println(answer.equals("2"));
						System.out.println(answer);

						System.out.println("Button 2 clicked");
						if (button2.isSelected())
						{

							if(answer.equals("2"))
							{
								correct2 = true;
								panelCards.wrong2 = false;
							}
							else
							{
								panelCards.wrong2 = true;
								correct2 = false;
							}
						} 
						//System.out.println(correct1 + " " + correct2 + " " + correct3 + " " + correct4 + " " + correct5 + " " + correct6 + " " + correct7);

					}
				});
				button3.addActionListener(new ActionListener() 
				{

					public void actionPerformed(ActionEvent e) 
					{
						System.out.println(answer);
						System.out.println(answer.equals("3"));
						System.out.println("Button 3 clicked");
						if (button3.isSelected())
						{

							if(answer.equals("3"))
							{
								correct2 = true;
								panelCards.wrong2 = false;
							}
							else
							{
								panelCards.wrong2 = true;
								correct2 = false;
							}
						} 
						//System.out.println(correct1 + " " + correct2 + " " + correct3 + " " + correct4 + " " + correct5 + " " + correct6 + " " + correct7);

					}
				});

				button4.addActionListener(new ActionListener() 
				{

					public void actionPerformed(ActionEvent e) 
					{
						System.out.println(answer);
						System.out.println("Button 4 clicked");
						System.out.println(answer.equals("4"));
						if (button4.isSelected())
						{


							if(answer.equals("4"))
							{

								correct2 = true;
								panelCards.wrong2 = false;

							}
							
							else
							{
								panelCards.wrong2 = true;
								correct2 = false;
							}
							//	System.out.println(correct1 + " " + correct2 + " " + correct3 + " " + correct4 + " " + correct5 + " " + correct6 + " " + correct7);

						} 
					}
				});

				add(radioPanel);



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

		centerPanel cp = new centerPanel();
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


		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();
			System.out.println("Mouse clicked at: " + x + ", " + y);
			// Check if the click is within the bounds of the "Grade Answer" image
			if (x >= 375 && x <= 675 && y >= 700 && y <= 750) 
			{
				System.out.println("Grade Answer button clicked");
				cards.show(panelCards, "clueBoard");

				panelCards.repaint(); // Trigger repaint to update the UI
			}
			else if (x >= 28 && x <= 66 && y >= 695 && y <= 755) 
			{
				cards.show(panelCards, "forensicsReport");
			}


		}
		public void mouseDragged(MouseEvent e) {}
		public void mouseMoved(MouseEvent e) 
		{
			int x = e.getX();
			int y = e.getY();
			//System.out.println("Mouse moved at: " + x + ", " + y);
			// Check if the mouse is within the bounds of the "Grade Answer" image
			if (x >= 345 && x <= 650 && y >= 700 && y <= 750) 
			{
				System.out.println("Mouse over Grade Answer button");
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				darkenButton = true;
				repaint();
			} 
			else 
			{
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				darkenButton = false;
				repaint();
			}
		}
		public void mousePressed(MouseEvent e) 
		{

			int x = e.getX();
			int y = e.getY();
			System.out.println("Mouse pressed at: " + x + ", " + y);
			// Check if the click is within the bounds of the "Grade Answer" image
			if (x >= 375 && x <= 675 && y >= 700 && y <= 750) 
			{
				System.out.println("Grade Answer button pressed");
				darkenButton = true;
				repaint();
			}
		}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e)
		{
		}
		public void mouseExited(MouseEvent e) 
		{
		}
	}

	class question6 extends JPanel implements MouseListener, MouseMotionListener
	{
		boolean dragging1, dragging2, dragging3, dragging4;
		int x1, x2, x3, x4;
		int y1, y2, y3, y4;


		JLabel label1 , label2, label3, label4;
		JLabel top1, top2, top3, top4;
		protected boolean darkenButton;
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;
		Image background;

		public question6(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			panelCards = panelCardsIn;
			cards = cardsIn;
			retreiveImage();

			labels();
			setupLayout();
			repaint();
			addMouseListener(this);
			addMouseMotionListener(this);
		}

		public void retreiveImage()
		{
			try
			{
				background = ImageIO.read(new File("question1BG.png"));
			}
			catch(IOException e)
			{
				System.err.println("\n\n\nERROR IN RETRIEVING IMAGE\n\n\n");
				e.printStackTrace();
			}
		}

		class centerPanel extends JPanel
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)(g);
				if(background != null)
				{

					g.drawImage(background, -20, 0, 1050, 870, this);
				}
				// Draw the "Grade Answer" image
				Image grader = new ImageIcon("GradeAnswer.png").getImage();
				g.drawImage(grader, 375, 700, 300, 50, this);

				Image hint = new ImageIcon("forensicButton.png").getImage();
				g.drawImage(hint, 50, 695, 50, 60, this);
				if (darkenButton)
				{
					g.setColor(new Color(0, 0, 0, 80));
					g.fillRect(375, 700, 300, 50);
				}
				g.setColor(Color.WHITE);
				g.fillRect(100, 200, 100, 100);
				g.fillRect(300, 200, 100, 100);
				g.fillRect(500, 200, 100, 100);
				g.fillRect(700, 200, 100, 100);

				g.setColor(Color.WHITE);
				g.fillRect(100, 500, 100, 100);
				g.fillRect(300, 500, 100, 100);
				g.fillRect(500, 500, 100, 100);
				g.fillRect(700, 500, 100, 100);
				g.setColor(Color.BLACK);
				g.setFont(font.deriveFont(10f));

				//g.drawString("Drag into correct order", 110, 550);
				//g.drawString("Drag into correct order", 310, 550);
				//g.drawString("Drag into correct order", 510, 550);
				//	g.drawString("Drag into correct order", 710, 550);

				if(dragging1)
				{
					g.setColor(Color.RED);
					g.drawRect(x1, y1, 100, 100);
				}
				else if(dragging2)
				{
					g.setColor(Color.RED);
					g.drawRect(x2, y2, 100, 100);
				}
				else if(dragging3)
				{
					g.setColor(Color.RED);
					g.drawRect(x3, y3, 100, 100);
				}
				else if(dragging4)
				{
					g.setColor(Color.RED);
					g.drawRect(x4, y4, 100, 100);
				}


				g.setFont(font.deriveFont(30f));
				g.setColor(Color.WHITE);

				int maxWidth = 700; // Maximum width for a single line
				int x = 175; // Starting x-coordinate
				int y = 90; // Starting y-coordinate
				int lineHeight = g.getFontMetrics().getHeight(); // Line height

				String[] words = "The \"DIGESTIVE REPORT\" outlines the path food takes. Arrange the following digestive organs in the order food passes through them:".split(" ");
				StringBuilder line = new StringBuilder();

				for (String word : words) {
					String testLine = line + word + " ";
					int lineWidth = g.getFontMetrics().stringWidth(testLine);

					if (lineWidth > maxWidth) {
						g.drawString(line.toString(), x, y);
						line = new StringBuilder(word + " ");
						y += lineHeight;
					} else {
						line.append(word).append(" ");
					}
				}

				// Draw the last line
				if (line.length() > 0) {
					g.drawString(line.toString(), x, y);
				}


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

		centerPanel cp = new centerPanel();
		cluePanelWithBackground cluePan = new cluePanelWithBackground("cluesPanelBG.png");
		public void labels()
		{

			label1 = new JLabel("Stomach");
			label1.setBounds(100, 500, 100, 100);
			label2 = new JLabel("Small Intestine");
			label2.setBounds(300, 500, 100, 100);
			label3 = new JLabel("Esophagus");
			label3.setBounds(500, 500, 100, 100);
			label4 = new JLabel("Large Intestine");
			label4.setBounds(700, 500, 100, 100);
			label1.setFont(font.deriveFont(10f));
			label2.setFont(font.deriveFont(10f));
			label3.setFont(font.deriveFont(10f));
			label4.setFont(font.deriveFont(10f));
			label1.setText("1");	
			label2.setText("2");
			label3.setText("3");
			label4.setText("4");
			label1.setForeground(Color.BLACK);
			label2.setForeground(Color.BLACK);
			label3.setForeground(Color.BLACK);
			label4.setForeground(Color.BLACK);
			label1.setBackground(Color.WHITE);
			label2.setBackground(Color.WHITE);
			label3.setBackground(Color.WHITE);
			label4.setBackground(Color.WHITE);
			top1 = new JLabel("Stomach");
			top1.setBounds(100, 100, 100, 100);
			top2 = new JLabel("Small Intestine");
			top2.setBounds(300, 100, 100, 100);
			top3 = new JLabel("Esophagus");
			top3.setBounds(500, 100, 100, 100);
			top4 = new JLabel("Large Intestine");
			top4.setBounds(700, 100, 100, 100);
			top1.setBounds(100, 200, 100, 100);
			top2.setBounds(300, 200, 100, 100);
			top3.setBounds(500, 200, 100, 100);
			top4.setBounds(700, 200, 100, 100);

			top1.setFont(font.deriveFont(10f));
			top2.setFont(font.deriveFont(10f));
			top3.setFont(font.deriveFont(10f));
			top4.setFont(font.deriveFont(10f));
			top1.setText("Stomach");
			top2.setText("Small Intestine");
			top3.setText("Esophagus");
			top4.setText("Large Intestine");

			top1.setForeground(Color.BLACK);
			top2.setForeground(Color.BLACK);
			top3.setForeground(Color.BLACK);
			top4.setForeground(Color.BLACK);


			add(top1);
			add(top2);
			add(top3);
			add(top4);

			add(label1);
			add(label2);
			add(label3);
			add(label4);
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


		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();


			// Check if the click is within the bounds of the "Grade Answer" image
			if (x >= 375 && x <= 675 && y >= 700 && y <= 750) 
			{
				System.out.println("Grade Answer button clicked");
				cards.show(panelCards, "clueBoard");
				if(label1.getText().equalsIgnoreCase("Esophagus") && label2.getText().equalsIgnoreCase("Stomach") && label3.getText().equalsIgnoreCase("Small Intestine") && label4.getText().equalsIgnoreCase("Large Intestine"))
				{
					panelCards.correct7 = true; // Set the correct variable
					panelCards.wrong7 = false;
					panelCards.repaint(); // Trigger repaint to update the UI
				}
				else
				{
					panelCards.wrong7 = true;
					panelCards.correct7 = false;
				}

				panelCards.repaint(); // Trigger repaint to update the UI

			}
			else if (x >= 50 && x <= 100 && y >= 695 && y <= 755) 
			{
				cards.show(panelCards, "forensicsReport");
			}

		}
		public void mousePressed(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();
			System.out.println("Mouse pressed at: " + x + ", " + y);
			// Check if the click is within the bounds of the "Grade Answer" image

			if (x >= 375 && x <= 675 && y >= 700 && y <= 750) 
			{
				System.out.println("Grade Answer button pressed");
				darkenButton = true;
				repaint();


			}
			else if(x>100&&x<200&&y>200&&y<300)
			{	
				System.out.println("Mouse pressed in first square");
				dragging1 = true;

			}
			else if(x>300&&x<400&&y>200&&y<300)
			{
				System.out.println("Mouse pressed in second square");
				dragging2 = true;

			}
			else if(x>500&&x<600&&y>200&&y<300)
			{
				System.out.println("Mouse pressed in third square");
				dragging3 = true;

			}
			else if(x>700&&x<800&&y>200&&y<300)
			{
				System.out.println("Mouse pressed in fourth square");
				dragging4 = true;


			}
			System.out.println(dragging1);
			System.out.println(dragging2);
			System.out.println(dragging3);
			System.out.println(dragging4);



		}		
		public void mouseReleased(MouseEvent e) 
		{

			int x = e.getX();
			int y = e.getY();
			if(x>100&&x<200&&y>500&&y<600)
			{
				System.out.println("Mouse released in first square");

				if(dragging1)
				{
					label1.setText("Stomach");


				}
				else if(dragging2)
				{
					label1.setText("Small Intestine");
				}
				else if(dragging3)
				{
					label1.setText("Esophagus");
				}
				else if(dragging4)
				{
					label1.setText("Large Intestine");

				}


			}
			else if(x>300&&x<400&&y>500&&y<600)
			{
				System.out.println("Mouse released in second square");

				if(dragging1)
				{
					label2.setText("Stomach");


				}
				else if(dragging2)
				{
					label2.setText("Small Intestine");
				}
				else if(dragging3)
				{
					label2.setText("Esophagus");
				}
				else if(dragging4)
				{
					label2.setText("Large Intestine");

				}


			}
			else if(x>500&&x<600&&y>500&&y<600)
			{
				System.out.println("Mouse released in third square");
				x3 = 500;
				y3 = 500;

				if(dragging1)
				{
					label3.setText("Stomach");


				}
				else if(dragging2)
				{
					label3.setText("Small Intestine");
				}
				else if(dragging3)
				{
					label3.setText("Esophagus");
				}
				else if(dragging4)
				{
					label3.setText("Large Intestine");

				}

			}
			else if(x>700&&x<800&&y>500&&y<600)
			{
				System.out.println("Mouse released in fourth square");
				x4 = 700;
				y4 = 500;

				if(dragging1)
				{
					label4.setText("Stomach");


				}
				else if(dragging2)
				{
					label4.setText("Small Intestine");
				}
				else if(dragging3)
				{
					label4.setText("Esophagus");
				}
				else if(dragging4)
				{
					label4.setText("Large Intestine");

				}


			}
			dragging1 = false;
			dragging2 = false;
			dragging3 = false;
			dragging4 = false;
			repaint();
		}
		public void mouseEntered(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();
			if(x>=375 && x<=675 && y>=700 && y<=750)
			{

				darkenButton = true;
				repaint();

			}
		}
		public void mouseExited(MouseEvent e) {}


		public void mouseDragged(MouseEvent e) 
		{
			int x = e.getX();
			int y = e.getY();

			System.out.println("Mouse dragged at: " + x + ", " + y);
			if(dragging1)
			{
				x1 = x;
				y1 = y;

			}
			else if(dragging2)
			{
				x2 = x;
				y2 = y;

			}
			else if(dragging3)
			{
				x3 = x;
				y3 = y;

			}
			else if(dragging4)
			{
				x4 = x;
				y4 = y;

			}

			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) 
		{

		}


	}

	class question7 extends JPanel implements MouseListener
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		centerPanel cp;
		CardLayout cards;
		Image background, questionPaper, optionPaper, boxes, arrows, answerImage;
		String questionText, questionChoice, question, questionPaperName, optionPaperName, boxesName, response, options;
		String[] indOptions;
		Timer waitTimer, lowerTimer;
		boolean drawBox;
		int randNum, repeats, xCord, yCord, answer;
		JFrame frame;

		public question7(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			cp = new centerPanel();
			panelCards = panelCardsIn;
			cards = cardsIn;

			setupLayout();
			addMouseListener(this);

			repeats = 0;
			xCord = 500;
			yCord = 150;
			drawBox = true;

			waitTimerHandler wth = new waitTimerHandler();
			lowerTimerHandler lth = new lowerTimerHandler();
			waitTimer = new Timer(6000, wth); //CHANGE TO 4000
			waitTimer.setRepeats(false);
			lowerTimer = new Timer(160, lth); //CHANGE TO 150
			indOptions = new String[4];

			questionText = file.substring(file.indexOf("Question 7"), file.indexOf("-----", file.indexOf("Question 7")));
			randNum = (int)(Math.random() * 5) + 1;
			questionChoice = questionText.substring(questionText.indexOf("Choice " + randNum), questionText.indexOf("-", questionText.indexOf("Choice " + randNum)));


			question = questionChoice.substring(0, questionChoice.indexOf("?") + 1);
			question = question.substring(8, question.length());
			System.out.println(question.indexOf(":") + 1);



			options = questionChoice.substring(questionChoice.indexOf("1."), questionChoice.length());
			questionPaperName = questionText.substring(questionText.indexOf("Questions: ") + 11, questionText.indexOf("-", questionText.indexOf("Questions: ")) - 1);
			optionPaperName = questionText.substring(questionText.indexOf("Options: ") + 9, questionText.indexOf("-", questionText.indexOf("Options: ")) - 1);
			boxesName = questionText.substring(questionText.indexOf("Boxes: ") + 7, questionText.length() - 1);

			answer = Integer.parseInt(questionChoice.substring(questionChoice.indexOf("?") + 2, questionChoice.indexOf("?") + 3));	

			for(int i = 0; i < indOptions.length; i++)
			{	
				if(i == 3)
					indOptions[i] = options.substring(options.indexOf(i + 1 + "."));

				else
					indOptions[i] = options.substring(options.indexOf(i + 1 + "."), options.indexOf(i + 2 + "."));
			}

			/*		question1Text = file.substring(file.indexOf("Question 4"), (file.indexOf("-----", file.indexOf("Question 4"))));
			randNum = (int)(Math.random() * 5) + 1;
			questionChoice = question1Text.substring(question1Text.indexOf("Choice " + randNum), question1Text.indexOf("-", question1Text.indexOf("Choice " + randNum)));
			answer = Integer.parseInt(questionChoice.substring(questionChoice.length() - 2, questionChoice.length() - 1));
			questionsName = question1Text.substring(question1Text.indexOf("Questions: ") + 11, (question1Text.indexOf("-", question1Text.indexOf("Questions: "))) - 1);
			System.out.println(questionsName);
			optionsName = question1Text.substring(question1Text.indexOf("Options: ") + 9, (question1Text.indexOf("-", question1Text.indexOf("Options: "))) - 1);
			boxesName = (question1Text.substring(question1Text.indexOf("Boxes: ") + 7, question1Text.length())).trim();*/

			retreiveImage();
			makeFont();
		}

		public void retreiveImage()
		{
			try
			{
				background = ImageIO.read(new File("question1BG.png"));
				questionPaper = ImageIO.read(new File(questionPaperName));
				optionPaper = ImageIO.read(new File(optionPaperName));
				boxes = ImageIO.read(new File(boxesName));
				arrows = ImageIO.read(new File("arrows.png"));
				answerImage = ImageIO.read(new File("answers.png"));
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
				{
					lowerTimer.stop();
					drawBox = false;
				}

				setFocusable(true);
				cp.repaint();
			}
		}

		public void makeFont()
		{
			try
			{
				font = Font.createFont(Font.TRUETYPE_FONT, new File("VT323-Regular.ttf"));
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(font);
			}
			catch (FontFormatException | IOException e)
			{
				System.err.println("Error loading font: " + e.getMessage());
				e.printStackTrace();
			}
		} 

		class centerPanel extends JPanel
		{
			int questionHeight, questionWidth, optionHeight, optionWidth, arrowsHeight, arrowsWidth;

			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)(g);

				requestFocusInWindow();
				setFocusable(true);

				arrowsHeight = arrows.getHeight(this);
				arrowsWidth = arrows.getWidth(this) / 2;

				if(background != null)
					g.drawImage(background, -20, 0, 1050, 870, this);

				g.drawImage(questionPaper, 60, 35, 900, 80, this);
				g.drawImage(arrows, 100, 350, 180, 440, 0, 0, arrowsWidth, arrowsHeight, this);
				g.drawImage(arrows, 840, 350, 920, 440, arrowsWidth, 0, arrowsWidth * 2, arrowsHeight, this);

				g.setColor(Color.RED);

				if(drawBox)
				{
					g.fillRect(xCord, yCord, 20, 20);
				}

				if(!drawBox)
				{
					g.setColor(new Color(0, 153, 0));
					g.fillRect(135 + (answer - 1) * 240, 680, 30, 70);

					g.setColor(new Color(153, 0, 0));

					for(int i = 0; i < 4; i++)
					{
						if(i != (answer - 1))
							g.fillRect(135 + i * 240, 680, 33, 70);
					}

					if(xCord >= 90 + (240 * (answer - 1)) && xCord <= (90 + (240 * (answer - 1))) + 130)
						response = "Correct!";

					else
						response = "Incorrect";

					makeFrame();

					frame.setVisible(true);
				}

				if(repeats == 0)
					waitTimer.start();

				repeats++;

				g.drawImage(optionPaper, 60, 140, 900, 200, this);
				g.drawImage(boxes, 70, 620, 900, 150, this);

				g.setFont(font.deriveFont(45f));
				g.setColor(Color.BLACK);		
				g.drawString(question, 150, 87);

				g.setFont(font.deriveFont(36f));
				g.setColor(Color.BLACK);		

				for(int i = 0; i < indOptions.length; i++)
					g.drawString(indOptions[i], 170, 180  + (i * 45));
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

		public void makeFrame()
		{
			frame = new JFrame(response);
			frame.setSize(300, 200);				
			frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			answerFrameHandler afh = new answerFrameHandler();
			frame.getContentPane().add(afh);
			frame.setLocationRelativeTo(null);
			frame.setVisible(false);	
		}

		class answerFrameHandler extends JPanel
		{
			public answerFrameHandler()
			{
				setBackground(Color.WHITE);
				setLayout(null);

				JLabel title = new JLabel(response);
				JButton returnToClue;
				title.setFont(font.deriveFont(40f));
				title.setBounds(130, 20, 150, 40);
				title.setForeground(Color.BLACK);
				title.setVisible(true);
				add(title);

				if(response.equals("Correct!"))
				{
					returnToClue = new JButton("View clue");
					returnToClue.setFont(font.deriveFont(20f));
				}

				else
				{
					returnToClue = new JButton("Return to clue page");
					returnToClue.setFont(font.deriveFont(15f));
				}

				returnToClue.setBounds(120, 60, 140, 50);
				returnToClue.setForeground(Color.BLACK);
				returnToClue.setBackground(Color.WHITE);
				returnToClue.setVisible(true);
				buttonHandler ibh = new buttonHandler();
				returnToClue.addActionListener(ibh);
				add(returnToClue);
			}

			class buttonHandler implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{	
					if(response.equals("Incorrect"))
					{
						cards.show(panelCards, "secondClueBoard");
						showClueVid = false;
					}

					else
						cards.show(panelCards, "clue1");

					frame.dispose();
				}
			}

			public void paintComponent(Graphics g)
			{
				int answerWidth = answerImage.getWidth(this) / 2;
				int answerHeight = answerImage.getHeight(this);

				if(response.equals("Correct!"))
				{
					g.drawImage(answerImage, 20, 20, 100, 130, 0, 0, answerWidth, answerHeight, this);
					correct1 = true;
				}

				else if(response.equals("Incorrect"))
					g.drawImage(answerImage, 17, 20, 100, 130, answerWidth, 0, answerWidth * 2, answerHeight, this);
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

		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= 100 && x <= 180 && y >= 350 && y <= 440)
				xCord -= 15;

			if(x >= 840 && x <= 920 && y >= 350 && y <= 440)
				xCord += 15;

			repaint();
		}

		public void mousePressed(MouseEvent e)
		{

		}

		public void mouseReleased(MouseEvent e)
		{

		}

		public void mouseEntered(MouseEvent e)
		{

		}

		public void mouseExited(MouseEvent e)
		{

		}
	}

	class chooseMurderer extends JPanel
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;
		Image background;

		public chooseMurderer(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			setupLayout();
		}

		class centerPanel extends JPanel
		{
			Image backgroundImage, suspects;

			public centerPanel(String imageName)
			{
				setLayout(null);
				try
				{
					backgroundImage = ImageIO.read(new File(imageName));
					suspects = ImageIO.read(new File("criminals.png"));
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

				int susWidth = suspects.getWidth(this);
				int susHeight = suspects.getHeight(this) / 7;

				if(backgroundImage != null)
					g.drawImage(backgroundImage, 0, 0, 954, getHeight(), this);

				g2d.setColor(new Color(0, 0, 0, 80));
				g2d.fillRect(0, 0, 954, getHeight());

				for(int i = 0; i < 7; i++)
					g.drawImage(suspects, 230, 40 + (195 * i), 180 + susWidth, susHeight * (i + 1), 0, susHeight * i, susWidth, susHeight * (i + 1) - 2, this)
					;			}
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

		centerPanel cp = new centerPanel("chooseMurderBG.png");
		cluePanelWithBackground cluePan = new cluePanelWithBackground("cluesPanelBG.png");

		public void setupLayout()
		{
			setLayout(new BorderLayout());
			JLayeredPane layeredPane = new JLayeredPane();
			layeredPane.setPreferredSize(new Dimension(1250, 800));
			cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
			cp.setOpaque(false);
			cp.setPreferredSize(new Dimension(1040, 1400));
			cluePan.setLayout(new BoxLayout(cluePan, BoxLayout.Y_AXIS));
			cluePan.setOpaque(false);
			cluePan.setPreferredSize(new Dimension(260, 1000));

			JScrollPane scrollPane = new JScrollPane(cluePan, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			JScrollBar clueScrollBar = scrollPane.getVerticalScrollBar();

			JScrollPane mainScrollPane = new JScrollPane(cp, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			JScrollBar mainClueScrollBar = mainScrollPane.getVerticalScrollBar();

			mainClueScrollBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI()
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

			mainScrollPane.setBounds(0, 0, 970, 800);
			mainScrollPane.setOpaque(false);
			mainScrollPane.getViewport().setOpaque(false);
			mainScrollPane.setBorder(null);

			layeredPane.add(mainScrollPane, Integer.valueOf(1));
			layeredPane.add(scrollPane, Integer.valueOf(1));
			add(layeredPane, BorderLayout.CENTER);

			String[] suspects = new String[7];
			suspects[0] = "Suspect 1";
			suspects[1] = "Suspect 2";
			suspects[2] = "Suspect 3";
			suspects[3] = "Suspect 4";
			suspects[4] = "Suspect 5";
			suspects[5] = "Suspect 6";
			suspects[6] = "Suspect 7";

			ButtonGroup murdererGroup = new ButtonGroup();

			for(int i = 0; i < suspects.length; i++)
			{
				JRadioButton rb = new JRadioButton(suspects[i]);
				rb.setAlignmentX(Component.LEFT_ALIGNMENT);
				rb.setOpaque(false);
				rb.setForeground(Color.WHITE);
				rb.setFont(font.deriveFont(30f));

				murdererGroup.add(rb);
				cp.add(Box.createVerticalStrut(20));   // spacing above
				cp.add(Box.createHorizontalStrut(20));
				cp.add(rb);
				cp.add(Box.createVerticalStrut(120));   // spacing below
				cp.add(Box.createHorizontalStrut(20));
			}

		}
	}
}