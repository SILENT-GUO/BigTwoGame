import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
/**
 * This is BigTwoGUI class which paint the GUI of Big Two Game.
 * @author Guo Zebin
 */
public class BigTwoGUI implements CardGameUI {

    //private instance variables:
    private BigTwo game;//a Big Two card game associates with this GUI.
    private boolean[] selected;//a boolean array indicating which cards are being selected.
    private int activePlayer;//an integer specifying the index of the active player.
    private JFrame frame;//the main window of the application.
    private JPanel bottom;
    private JPanel chatbox;
    private JMenuBar menuBar;
    private JPanel bigTwoPanel;//a panel for showing the cards of each player and the cards played on the table.
    private JButton playButton;// a “Play” button for the active player to play the selected cards.
    private JLabel msgLabel;
    private JButton passButton;//a “Pass” button for the active player to pass his/her turn to the next player.
    private JTextArea msgArea;// a text area for showing the current game status as well as end of game messages.
    private JTextArea chatArea;//a text area for showing chat messages sent by the players.
    private JTextField chatInput;//a text field for players to input chat messages.
    private final static int MAX_CARD_NUM = 13; // max. no. of cards each player holds
    private Image[] avatars;
    private Image[][] cards;
    private Image Image10086;
    private static String GAME_TITLE = "BIG TWO GAME";

    /**
     * the public constructor for BigTwoGUI class
     * @param game is an BigTwo object.
     */
    public BigTwoGUI(BigTwo game) {
        this.game = game;
        selected = new boolean[13];
        setActivePlayer(game.getCurrentPlayerIdx());
        SetImages();
        initializeFrame();
    }

    /**
     * this method set the initial GUI frame;
     */
    private void initializeFrame() {
        frame = new JFrame();
        frame.setTitle(GAME_TITLE);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatbox = initializeChatBox();
        bottom = initializeBottom();
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        JMenu menuGame = new JMenu("Game");
        JMenu menuMessage = new JMenu("Message");
        menuBar.add(menuGame);
        menuBar.add(menuMessage);
        JMenuItem restart = new JMenuItem("Restart");
        JMenuItem quit = new JMenuItem("Quit");
        JMenuItem clearChatArea = new JMenuItem("Clear ChatArea");
        clearChatArea.addActionListener(new clearChatAreaListener());
        menuGame.add(restart);
        menuGame.add(quit);
        menuMessage.add(clearChatArea);
        restart.addActionListener(new RestartMenuItemListener());
        quit.addActionListener(new QuitMenuItemListener());
        bigTwoPanel = new BigTwoPanel();
        bigTwoPanel.setLayout(new BoxLayout(bigTwoPanel, BoxLayout.Y_AXIS));
        frame.add(bigTwoPanel, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);
        frame.add(chatbox, BorderLayout.EAST);
        frame.setJMenuBar(menuBar);
        frame.setPreferredSize(new Dimension(1450, 900));
        frame.setVisible(true);
        frame.pack();
        frame.repaint();
    }

    /**
     * this is the initialization of bottom part, containing playButton, passButton, magLabel and chatInput, used in initializeFrame() method.
     * @return bottoms of type Jpanel
     */
    private JPanel initializeBottom() {
        JPanel bottoms = new JPanel();
        playButton = new JButton("Play");
        playButton.addActionListener(new PlayButtonListener());
        passButton = new JButton("Pass");
        passButton.addActionListener(new PassButtonListener());
        chatInput = new JTextField(15);
        chatInput.addKeyListener(new ChatInputListener());
        msgLabel = new JLabel("Message:");
        bottoms.setLayout(new BoxLayout(bottoms, BoxLayout.X_AXIS));
        bottoms.add(Box.createHorizontalStrut(200));
        bottoms.add(playButton);
        bottoms.add(Box.createHorizontalStrut(30));
        bottoms.add(passButton);
        bottoms.add(Box.createHorizontalStrut(600));
        bottoms.add(msgLabel);
        bottoms.add(Box.createHorizontalStrut(5));
        bottoms.add(chatInput);
        return bottoms;
    }

    /**
     * this is the initialization of chatBox part, used in initializeFrame() method.
     * @return bottoms of type Jpanel
     */
    private JPanel initializeChatBox() {
        JPanel chatbox = new JPanel();
        chatbox.setLayout(new BoxLayout(chatbox, BoxLayout.Y_AXIS));
        msgArea = new JTextArea();
        msgArea.setEditable(false);
        msgArea.setLineWrap(true);
        chatArea = new JTextArea();
        chatArea.setLineWrap(true);

        JScrollPane scroll1 = new JScrollPane(msgArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll1.setPreferredSize(new Dimension(500, 0));
        DefaultCaret caret = (DefaultCaret)msgArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        chatbox.add(scroll1);
        JScrollPane scroll2 = new JScrollPane(chatArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll2.setPreferredSize(new Dimension(500, 0));
        DefaultCaret caret2 = (DefaultCaret)chatArea.getCaret();
        caret2.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        chatbox.add(scroll2);
        return chatbox;
    }

    /**
     * this method save all the images from files.
     */
    private void SetImages() {
        final String CWD = System.getProperty("user.dir");
        avatars = new Image[4];
        avatars[0] = new ImageIcon(CWD + "/src/avatars/direnjie.jpeg").getImage();
        avatars[1] = new ImageIcon(CWD + "/src/avatars/gongsunli.jpeg").getImage();
        avatars[2] = new ImageIcon(CWD + "/src/avatars/makeboluo.jpeg").getImage();
        avatars[3] = new ImageIcon(CWD + "/src/avatars/sunshangxiang.jpeg").getImage();
        Image10086 = new ImageIcon(CWD + "/src/cards/10086.png").getImage();
        cards = new Image[13][4];
        for (int i = 0; i < 13; ++i) {
            for (int j = 0; j < 4; ++j) {
                cards[i][j] = new ImageIcon(CWD + "/src/cards/" + i + j + ".png").getImage();
            }
        }

    }

    // public method

    /**
     * this method retrieves the active player
     * @param activePlayer an int value representing the index of the active player
     */
    @Override
    public void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * this method repaints GUI
     */
    @Override
    public void repaint() {
        resetSelected();
        frame.repaint();
    }

    /**
     * this method print given String to msgArea.
     * @param msg the string to be printed to the message area of the card game user
     */
    @Override
    public void printMsg(String msg) {
        msgArea.append(msg);
        msgArea.setCaretPosition(msgArea.getDocument().getLength());
    }

    /**
     * this method retrieves the selected cards array
     * @return the selected cards array
     */
    public int[] getSelected() {
        int numOfSelected = 0;
        int[] SelectedCardsIndex;
        for (int i = 0; i < selected.length; ++i) {
            if (selected[i]) {
                numOfSelected++;
            }
        }
        if (numOfSelected == 0) {
            return null;
        } else {
            SelectedCardsIndex = new int[numOfSelected];
            int j = 0;
            for (int i = 0; i < selected.length; ++i) {
                if (selected[i]) {
                    SelectedCardsIndex[j] = i;
                    j++;
                }
            }
        }
        return SelectedCardsIndex;
    }

    /**
     * this method sets all the card to not selected status
     */
    public void resetSelected() {
        for (int i = 0; i < selected.length; ++i) {
            selected[i] = false;
        }
    }

    /**
     * this method clear the msgArea
     */
    @Override
    public void clearMsgArea() {
        msgArea.setText(null);
    }

    /**
     * a method for resetting the GUI
     */
    @Override
    public void reset() {
        this.resetSelected();
        this.clearMsgArea();
        this.enable();
    }

    /**
     * a method for enabling user interactions with the GUI
     */
    @Override
    public void enable() {
        playButton.setEnabled(true);
        passButton.setEnabled(true);
        bigTwoPanel.setEnabled(true);
    }

    /**
     * a method for disabling user interactions with the GUI
     */
    @Override
    public void disable() {
        playButton.setEnabled(false);
        passButton.setEnabled(false);
        bigTwoPanel.setEnabled(false);
    }

    /**
     * a method for prompting the active player to select cards
     */
    @Override
    public void promptActivePlayer() {
        printMsg(game.getPlayerList().get(activePlayer).getName() + "'s turn: \n");
        int[] cardIdx = getSelected();
        resetSelected();
    }

    /**
     * the class that contains cards, avatars, lines and names on the left of the frame.
     */

    class BigTwoPanel extends JPanel implements MouseListener {

        private double strXCoor = (frame.getWidth() == 0)?10 : 10*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
        private double strYCoor = (frame.getWidth() == 0)?30 : 30*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
        private double shownStrXCoor = (frame.getWidth() == 0)?10 : 10* ((double)(frame.getWidth() * frame.getHeight())/(1450*900));
        private double shownStrYCoor = (frame.getWidth() == 0)?580 : 580 * ((double)(frame.getWidth() * frame.getHeight())/(1450*900));
        private double avaXCoor = (frame.getWidth() == 0)?5 : 5*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
        private double avaYCoor = (frame.getWidth() == 0)?40 : 40*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
        private double lineYCoor = (frame.getWidth() == 0)?140 : 140*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
        private double lineLength = (frame.getWidth() == 0)?2000 : 2000*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
        private double avawidth = (frame.getWidth() == 0)?110 : 110*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
        private double avaheight = (frame.getWidth() == 0)?80 : 80*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
        private double cardXCoor = (frame.getWidth() == 0)?120 : 120*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
        private double cardYCoor = (frame.getWidth() == 0)?15 : 15*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
        private double cardAddingSelected = (frame.getWidth() == 0)?8 : 8*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
        private double cardLength = (frame.getWidth() == 0)?80 : 80*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
        private double cardLengthOverlapped = (frame.getWidth() == 0)?30 : 30*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
        private double cardheight = (frame.getWidth() == 0)?120 : 120*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
        private double shownCardXCoor = (frame.getWidth() == 0)?10 : 10*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
        private double shownCardYCoor = (frame.getWidth() == 0)?590 : 590*((double)(frame.getWidth() * frame.getHeight())/(1450*900));

        /**
         * the public constructor of BigTwoPanel class
         */
        public BigTwoPanel() {
            this.setPreferredSize(new Dimension((frame.getWidth() == 0)?900 : 900*frame.getWidth() * frame.getHeight()/(1450*900), (frame.getWidth() == 0)?200 : 200*frame.getWidth() * frame.getHeight()/(1450*900)));
            this.addMouseListener(this);
        }

        /**
         * the method that paint the bigTwoPanel when constructed.
         * @param g the canva tool
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            strXCoor = (frame.getWidth() == 0)?10 : 10*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
            strYCoor = (frame.getWidth() == 0)?30 : 30*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
            shownStrXCoor = (frame.getWidth() == 0)?10 : 10 * ((double)(frame.getWidth() * frame.getHeight())/(1450*900));
            shownStrYCoor = (frame.getWidth() == 0)?580 : 580 * ((double)(frame.getWidth() * frame.getHeight())/(1450*900));
            avaXCoor = (frame.getWidth() == 0)?5 : 5*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
            avaYCoor = (frame.getWidth() == 0)?40 : 40*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
            lineYCoor = (frame.getWidth() == 0)?140 : 140*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
            lineLength = (frame.getWidth() == 0)?2000 : 2000*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
            avawidth = (frame.getWidth() == 0)?110 : 110*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
            avaheight = (frame.getWidth() == 0)?80 : 80*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
            cardXCoor = (frame.getWidth() == 0)?120 : 120*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
            cardYCoor = (frame.getWidth() == 0)?15 : 15*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
            cardAddingSelected = (frame.getWidth() == 0)?8 : 8*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
            cardLength = (frame.getWidth() == 0)?80 : 80*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
            cardLengthOverlapped = (frame.getWidth() == 0)?30 : 30*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
            cardheight = (frame.getWidth() == 0)?120 : 120*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
            shownCardXCoor = (frame.getWidth() == 0)?10 : 10*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
            shownCardYCoor = (frame.getWidth() == 0)?590 : 590*((double)(frame.getWidth() * frame.getHeight())/(1450*900));
            g.setColor(Color.WHITE);
            g.setFont(new Font("Times New Roman", Font.BOLD, (int)((frame.getWidth() == 0)?20 : 20*((double)(frame.getWidth() * frame.getHeight())/(1450*900)))));
            this.setBackground(Color.GREEN.darker());
            int numOfPlayers = 0;
            while (numOfPlayers < 4) {

                if (numOfPlayers == activePlayer) {
                    g.setColor(Color.GREEN.brighter());
                    g.drawString("You", (int)strXCoor, (int)(strYCoor + lineYCoor * numOfPlayers));
                    g.drawImage(avatars[numOfPlayers], (int)avaXCoor, (int)(avaYCoor + lineYCoor * numOfPlayers), (int)avawidth, (int)avaheight, this);
                    g.setColor(Color.BLACK);
                    g.drawLine(0, (int)lineYCoor * (numOfPlayers + 1), (int)lineLength, (int)lineYCoor * (numOfPlayers + 1));
                    //now draw cards;
                    for (int i = 0; i < game.getPlayerList().get(numOfPlayers).getNumOfCards(); ++i) {
                        int suit = game.getPlayerList().get(numOfPlayers).getCardsInHand().getCard(i).getSuit();
                        int rank = game.getPlayerList().get(numOfPlayers).getCardsInHand().getCard(i).getRank();
                        if (selected[i])
                            g.drawImage(cards[rank][suit], (int)(cardXCoor + cardLengthOverlapped * i), (int)(cardYCoor + lineYCoor * numOfPlayers - cardAddingSelected), (int)cardLength, (int)cardheight, this);
                        else
                            g.drawImage(cards[rank][suit], (int)(cardXCoor + cardLengthOverlapped * i), (int)(cardYCoor + lineYCoor * numOfPlayers), (int)cardLength, (int)cardheight, this);
                    }
                } else {
                    g.setColor(Color.BLACK);
                    g.drawString("Player " + numOfPlayers, (int)strXCoor, (int)(strYCoor + lineYCoor * numOfPlayers));
                    g.drawImage(avatars[numOfPlayers], (int)avaXCoor, (int)(avaYCoor + lineYCoor * numOfPlayers), (int)avawidth, (int)avaheight, this);
                    g.drawLine(0, (int)(lineYCoor * (numOfPlayers + 1)), (int)lineLength, (int)(lineYCoor * (numOfPlayers + 1)));
                    //now draw cards;
                    for (int i = 0; i < game.getPlayerList().get(numOfPlayers).getNumOfCards(); ++i) {
                        g.drawImage(Image10086, (int)(cardXCoor + cardLengthOverlapped * i), (int)(cardYCoor + lineYCoor * numOfPlayers), (int)cardLength, (int)cardheight, this);
                    }
                }
                numOfPlayers++;
            }

            if (!game.getHandsOnTable().isEmpty()) {
                Hand LastHand = game.getHandsOnTable().get(game.getHandsOnTable().size() - 1);
                g.drawString("Played by" + LastHand.getPlayer().getName(), (int)shownStrXCoor, (int)shownStrYCoor);
                int sizeOfLastHand = LastHand.size();
                for (int i = 0; i < sizeOfLastHand; ++i) {
                    int suit = LastHand.getCard(i).getSuit();
                    int rank = LastHand.getCard(i).getRank();
                    g.drawImage(cards[rank][suit], (int)(shownCardXCoor + cardLengthOverlapped * i), (int)shownCardYCoor, (int)cardLength, (int)cardheight, this);
                }
            }
            repaint();
        }

        /**
         * not used
         * @param e
         */
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        /**
         * not used
         * @param e
         */
        @Override
        public void mousePressed(MouseEvent e) {

        }

        /**
         * when mouse released, select or unselect cards on GUI
         * @param e
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            int numOfCards = game.getPlayerList().get(activePlayer).getNumOfCards();
            //the last card.
            if (e.getX() >= cardXCoor + cardLengthOverlapped * (numOfCards - 1) && e.getX() <= cardXCoor + cardLengthOverlapped * (numOfCards - 1) + cardLength) {
                if (selected[numOfCards - 1] && e.getY() >= (cardYCoor + lineYCoor * activePlayer - cardAddingSelected) && e.getY() <= (cardYCoor + lineYCoor * activePlayer + cardheight - cardAddingSelected)) {
                    selected[numOfCards - 1] = false;
                } else if (!selected[numOfCards - 1] && e.getY() >= (cardYCoor + lineYCoor * activePlayer) && e.getY() <= (cardYCoor + lineYCoor * activePlayer + cardheight)) {
                    selected[numOfCards - 1] = true;
                }
            }
            //other case;
            for (int i = 0; i < numOfCards - 1; ++i) {
                if (e.getX() >= cardXCoor + cardLengthOverlapped * i && e.getX() <= cardXCoor + cardLengthOverlapped * (i + 1)) {
                    if (selected[i] && e.getY() >= cardYCoor + lineYCoor * activePlayer - cardAddingSelected && e.getY() <= (cardYCoor + lineYCoor * activePlayer + cardheight - cardAddingSelected)) {
                        selected[i] = false;
                    } else if (!selected[i] && e.getY() >= (cardYCoor + lineYCoor * activePlayer) && e.getY() <= (cardYCoor + lineYCoor * activePlayer + cardheight)) {
                        selected[i] = true;
                    }
                }else if( i < numOfCards - 2 && getX() >= cardXCoor + cardLengthOverlapped * (i+1) && e.getX() <= cardXCoor + cardLengthOverlapped * (i + 2) && !selected[i] && selected[i+1] && e.getY() >= cardYCoor + lineYCoor * activePlayer + cardheight - cardAddingSelected &&  e.getY() <= cardYCoor + lineYCoor * activePlayer + cardheight ){
                    selected[i] = true;
                }else if(i < numOfCards - 2 && e.getX() >= cardXCoor + cardLengthOverlapped * (i+1) && e.getX() <= cardXCoor + cardLengthOverlapped * i + cardLength && selected[i] && !selected[i + 1] && e.getY() >= cardYCoor + lineYCoor * activePlayer - cardAddingSelected && e.getY() <= cardYCoor + lineYCoor * activePlayer){
                    selected[i] = false;
                }
            }
        }

        /**
         * not used
         * @param e
         */
        @Override
        public void mouseEntered(MouseEvent e) {

        }

        /**
         * not used
         * @param e
         */
        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * class that defines the motion of frame when play button is pressed
     */
    class PlayButtonListener implements ActionListener {
        /**
         * It actions by calling makemove
         * @param e the event that play button is pressed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (getSelected() == null) {
                printMsg("Please choose one card to play, or press Pass button to pass.\n");
            } else {
                game.makeMove(activePlayer, getSelected());
            }
            repaint();

            if(game.endOfGame()){
                disable();
            }
        }
    }

    /**
     * an inner class that transport the message in chatInput field to chatArea area
     */
    class ChatInputListener implements KeyListener {
        /**
         *not used
         * @param e
         */

        @Override
        public void keyTyped(KeyEvent e) {

        }

        /**
         * when enter key is pressed, deliver the message
         * @param e
         */
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                String Input = chatInput.getText();
                chatArea.append("Player " +game.getCurrentPlayerIdx()+ ": " + Input + "\n");
                chatInput.setText(null);
            }
        }

        /**
         * not used
         * @param e
         */
        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    /**
     *the inner class that defines the motion of panel when pass button is pressed
     */
    class PassButtonListener implements ActionListener {
        /**
         * the actions by calling the makemove.
         * @param e
         */
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (getSelected() == null) {
                game.makeMove(activePlayer, getSelected());
            } else {
                printMsg("Please don't have cards selected if you want to pass, or press Play button instead.\n");
            }
            repaint();
        }
    }

    /**
     * clicking the “Restart” menu item, restart the game.
     */
    class RestartMenuItemListener implements ActionListener {
        /**
         * it actions by reset all the contents.
         * @param e
         */
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            BigTwoDeck deck = new BigTwoDeck();
            deck.initialize();
            deck.shuffle();
            game.start(deck);
            reset();
        }
    }

    /**
     * clicking the “Restart” menu item, quit the game.
     */
    class QuitMenuItemListener implements ActionListener {
        /**
         * it actions by quiting the program.
         * @param e
         */
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            System.exit(0);
        }
    }

    /**
     * clicking the "clearChatArea" menu item, clear the chatArea area.
     */
    class clearChatAreaListener implements  ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            chatArea.setText(null);
        }
    }
}


