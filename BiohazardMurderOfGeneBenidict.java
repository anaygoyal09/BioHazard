//Code link: https://github.com/anaygoyal09/BioHazard
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
	public BiohazardMurderOfGeneBenidictHolder()
	{
		setBackground(Color.BLACK);
		CardLayout cards = new CardLayout();
		setLayout(cards);

		firstCard title = new firstCard(this, cards);
		secondCard report = new secondCard(this, cards);
		thirdCard question1 = new thirdCard(this, cards);

		add(title, "title");
		add(report, "report");
		add(question1, "question1");
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

					float finalAlpha = startTransparency * pulseTransparency;
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, finalAlpha));

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

			if(x >= startX && x <= (startX + targetW) && y >= startY && y <= (startY + targetH)) //move the button a little to look like a click
			{
				clickingStart = true;
				startX += clickOffset;
				startY += clickOffset;
				repaint();
			}
		}

		public void mouseReleased(MouseEvent e)
		{
			startX -= clickOffset; //reset button position
			startY -= clickOffset;
			clickingStart = false;
			repaint();
		}

		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= startX && x <= (startX + targetW) && y >= startY && y <= (startY + targetH)) //show next card when clicked
				cards.show(panelCards, "report");

			if(x >= 0 && x <= 10 && y >= 0 && y <= 10)
				cards.show(panelCards, "report");

		}

		public void mouseExited(MouseEvent e)
		{
			hoveringStart = false;
			clickingStart = false;
			scale = 1.0; // Reset to default scale (optional)
			repaint();
		}

		public void mouseEntered(MouseEvent e) {}
		public void mouseDragged(MouseEvent e) {}
	}

	class secondCard extends JPanel
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;
		BufferedImage fullImage = null;

		public secondCard(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			setLayout(new BorderLayout());

			panelCards = panelCardsIn;
			cards = cardsIn;

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
					cards.show(panelCards, "question1");
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

	class thirdCard extends JPanel
	{
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;

		public thirdCard(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			panelCards = panelCardsIn;
			cards = cardsIn;


		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
		}
	}
}
