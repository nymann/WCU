package data;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Arrays;


/**
 * Created by Kristian on 07/04/2015.
 */
public class GUI {
    // CLIENT
    DataOutputStream outputStream;
    BufferedReader fromServer;
    Socket connection;

    String serverIP = "localhost";
    int portnumber = 4567;

    // GUI DELEN
    JFrame jFrame = new JFrame();
    JLayeredPane jLayeredPane = new JLayeredPane();
    JPanel background = new JPanel();

    // header
    JLabel logo = new JLabel();


    // display weight button + text area
    JButton displayWeight = new JButton();
    JTextArea textAreaDW = new JTextArea();

    // connected as
    JLabel connectedAs = new JLabel();

    // Tare
    JButton tare = new JButton();
    JTextArea textAreaTA = new JTextArea();

    // Zero scale
    JButton zeroScale = new JButton();

    // Gross control
    JButton grossControl = new JButton();

    // Add item
    JButton addItem = new JButton();

    // Set Scale Options (RM20)
    JButton setScaleOptions = new JButton();

    // Display Weight
    JTextField jTextFieldDT = new JTextField();
    JButton displayText = new JButton();

    // Connected user
    JLabel connectedUser = new JLabel();

    // menu
    JPanel menu = new JPanel();
    JButton connect = new JButton();
    JButton restart = new JButton();
    JButton disconnect = new JButton();

    // global variables
    final int width = 800;
    final int height = 600;

    String user;

    String vareNummer = "";
    boolean verifiticeret = false;
    int counter = 0;

    // font for Display Text.
    Font font = new Font("Arial", Font.PLAIN, 12);

    // header buttons
    JButton beginProcedure = new JButton();
    JButton openLog = new JButton();
    JButton openStore = new JButton();


    public GUI() {
        jFrame.setTitle("Weighting Console Unit - GROUP AWESOME");
        jFrame.setPreferredSize(new Dimension(width, height + 20));
        jFrame.setLayout(new BorderLayout());
        jFrame.add(jLayeredPane, BorderLayout.CENTER);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);

        jLayeredPane.setBounds(0, 0, width, height);

        background.setBackground(new Color(0x005f6b));
        background.setBounds(0, 0, width, height);
        background.setOpaque(true);

        menu.setBackground(new Color(0x024b55));
        menu.setBounds(0, 120, width, 60);
        menu.setOpaque(true);

        header();
        menu();
        body();

        // test


        jLayeredPane.add(background, 0, 0);
        jLayeredPane.add(menu, 1, 0);
        jLayeredPane.add(logo, 1, 0);
        jLayeredPane.add(connect, 2, 0);
        jLayeredPane.add(restart, 2, 0);
        jLayeredPane.add(disconnect, 2, 0);
        jLayeredPane.add(addItem, 2, 0);
        jLayeredPane.add(displayText, 2, 0);
        jLayeredPane.add(displayWeight, 2, 0);
        jLayeredPane.add(grossControl, 2, 0);
        jLayeredPane.add(openLog, 2, 0);
        jLayeredPane.add(openStore, 2, 0);
        jLayeredPane.add(setScaleOptions, 2, 0);
        jLayeredPane.add(tare, 2, 0);
        jLayeredPane.add(textAreaTA, 2, 0);
        jLayeredPane.add(zeroScale, 2, 0);
        jLayeredPane.add(jTextFieldDT, 2, 0);
        jLayeredPane.add(textAreaDW, 2, 0);
        jLayeredPane.add(connectedAs, 2, 0);
        jLayeredPane.add(beginProcedure, 2, 0);

        jFrame.pack();
        jFrame.setVisible(false);

        login();


    }

    private void header() {
        Border noBorder = BorderFactory.createEmptyBorder();
        try {
            logo.setBounds(6, 6, 128, 104);
            BufferedImage logoImg = ImageIO.read(new File("res/img/logo.png"));
            logo.setIcon(new ImageIcon(logoImg));
            logo.setBorder(noBorder);
            logo.setOpaque(false);

            beginProcedure.setBounds(243, 12, 315, 84);
            BufferedImage beginProcedureImg = ImageIO.read(new File("res/img/beginProcedure.png"));
            beginProcedure.setIcon(new ImageIcon(beginProcedureImg));
            beginProcedure.setBorder(noBorder);
            beginProcedure.setBackground(new Color(0x005f6b));
            beginProcedure.setOpaque(false);
            beginProcedure.addActionListener(actionEvent -> {
                writeToLog(user + " clicked begin procedure!");
                counter = 0;
                while (counter >= 0) {
                    beginProcedure();
                }
            });


            openLog.setBounds(675, 16, 95, 14);
            BufferedImage openLogBtn = ImageIO.read(new File("res/img/openLog.png"));
            openLog.setIcon(new ImageIcon(openLogBtn));
            openLog.setBorder(noBorder);
            openLog.setBackground(new Color(0x024b55));
            openLog.setToolTipText("text");
            openLog.setOpaque(false);
            openLog.addActionListener(actionEvent -> {
                writeToLog(user + " clicked Open Log.txt");
                openFile("res/log.txt");
            });


            openStore.setBounds(658, 36, 112, 14);
            BufferedImage openStoreBtn = ImageIO.read(new File("res/img/openStore.png"));
            openStore.setIcon(new ImageIcon(openStoreBtn));
            openStore.setBorder(noBorder);
            openStore.setBackground(new Color(0x024b55));
            openStore.setToolTipText("text");
            openStore.setOpaque(false);
            openStore.addActionListener(actionEvent -> {
                writeToLog(user + " clicked on open store");
                openFile("res/store.txt");
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void menu() {
        Border noBorder = BorderFactory.createEmptyBorder();

        connect.setBounds(25, 143, 70, 14);
        try {
            BufferedImage commandsBtn = ImageIO.read(new File("res/img/connect.png"));
            connect.setIcon(new ImageIcon(commandsBtn));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connect.setBorder(noBorder);
        connect.setBackground(new Color(0x024b55));
        connect.setToolTipText("connect!");
        connect.addActionListener(actionEvent -> {
            writeToLog(user + " clicked on connect");
        });

        restart.setBounds(371, 143, 57, 14);
        try {
            BufferedImage restartBtn = ImageIO.read(new File("res/img/restart.png"));
            restart.setIcon(new ImageIcon(restartBtn));
        } catch (IOException e) {
            e.printStackTrace();
        }

        restart.setBorder(noBorder);
        restart.setBackground(new Color(0x024b55));
        restart.setToolTipText("Click to restart the procedure!");
        restart.addActionListener(actionEvent -> {
            writeToLog(user + " clicked restart.");
            try {
                Runtime.getRuntime().exec("java -jar WCU.jar");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(1);
        });

        disconnect.setBounds(694, 143, 80, 14);
        BufferedImage disconnectBtn = null;
        try {
            disconnectBtn = ImageIO.read(new File("res/img/disconnect.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        disconnect.setIcon(new ImageIcon(disconnectBtn));
        disconnect.setBorder(noBorder);
        disconnect.setBackground(new Color(0x024b55));
        disconnect.setToolTipText("Click to disconnect from the weight!");
        disconnect.addActionListener(actionEvent -> {
            writeToLog(user + " clicked on disconnect");
            try {
                Runtime.getRuntime().exec("java -jar WCU.jar");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(1);
        });
    }

    private void body() {
        Border noBorder = BorderFactory.createEmptyBorder();
        try {
            displayWeight.setBounds(28, 208, 113, 14);
            BufferedImage displayWeightBtn = ImageIO.read(new File("res/img/displayWeight.png"));
            displayWeight.setIcon(new ImageIcon(displayWeightBtn));
            displayWeight.setBorder(noBorder);
            displayWeight.setBackground(new Color(0x005f6b));
            displayWeight.setToolTipText("text");
            displayWeight.addActionListener(actionEvent -> {
                sendMessage("S\r\n");
                writeToLog(user + " clicked display weight");
            });

            tare.setBounds(384, 208, 32, 14);
            BufferedImage tareBtn = ImageIO.read(new File("res/img/tare.png"));
            tare.setIcon(new ImageIcon(tareBtn));
            tare.setBorder(noBorder);
            tare.setBackground(new Color(0x005f6b));
            tare.setToolTipText("text");
            tare.addActionListener(actionEvent -> {
                sendMessage("T\r\n");
                writeToLog(user + " clicked tare");
            });

            textAreaTA.setBounds(370, 234, 150, 14);
            textAreaTA.setFont(font);
            textAreaTA.setForeground(Color.white);
            textAreaTA.setBackground(new Color(0x005f6b));
            textAreaTA.setText("");


            zeroScale.setBounds(684, 208, 84, 14);
            BufferedImage zeroScaleBtn = ImageIO.read(new File("res/img/zeroScale.png"));
            zeroScale.setIcon(new ImageIcon(zeroScaleBtn));
            zeroScale.setBorder(noBorder);
            zeroScale.setBackground(new Color(0x005f6b));
            zeroScale.setToolTipText("text");
            zeroScale.addActionListener(actionEvent -> {
                sendMessage("Z\r\n");
                writeToLog(user + " clicked zero scale");
            });


            grossControl.setBounds(29, 385, 109, 14);
            BufferedImage grossControlBtn = ImageIO.read(new File("res/img/grossControl.png"));
            grossControl.setIcon(new ImageIcon(grossControlBtn));
            grossControl.setBorder(noBorder);
            grossControl.setBackground(new Color(0x005f6b));
            grossControl.setToolTipText("text");
            grossControl.addActionListener(actionEvent -> {
                writeToLog(user + " clicked gross control");
            });


            addItem.setBounds(368, 385, 64, 14);
            BufferedImage addItemBtn = ImageIO.read(new File("res/img/addItem.png"));
            addItem.setIcon(new ImageIcon(addItemBtn));
            addItem.setBorder(noBorder);
            addItem.setBackground(new Color(0x005f6b));
            addItem.setToolTipText("text");
            addItem.addActionListener(actionEvent -> {
                writeToLog(user + " clicked add item.");
            });

            setScaleOptions.setBounds(629, 385, 140, 14);
            BufferedImage setScaleOptionsBtn = ImageIO.read(new File("res/img/setScaleOptions.png"));
            setScaleOptions.setIcon(new ImageIcon(setScaleOptionsBtn));
            setScaleOptions.setBorder(noBorder);
            setScaleOptions.setBackground(new Color(0x005f6b));
            setScaleOptions.setToolTipText("set scale options");
            setScaleOptions.addActionListener(actionEvent -> {
                writeToLog(user + " clicked set scale options");
            });

            displayText.setBounds(366, 564, 95, 14);
            BufferedImage displayTextBtn = ImageIO.read(new File("res/img/displayText.png"));
            displayText.setIcon(new ImageIcon(displayTextBtn));
            displayText.setBorder(noBorder);
            displayText.setBackground(new Color(0x005f6b));
            displayText.setToolTipText("text");
            displayText.addActionListener(actionEvent -> {
                sendMessage("D " + '"' + jTextFieldDT.getText() + '"' + "\r\n");
                writeToLog(user + " clicked dispay text");
            });

            jTextFieldDT.setBounds(26, 557, 321, 29);
            jTextFieldDT.setBackground(new Color(0x034b55));
            jTextFieldDT.setForeground(Color.white);
            jTextFieldDT.setCaretColor(Color.orange);
            jTextFieldDT.setBorder(noBorder);
            jTextFieldDT.addActionListener(actionEvent -> {
                sendMessage("D " + '"' + jTextFieldDT.getText() + '"' + "\r\n");
                writeToLog(user + " clicked diplay text");
            });

            textAreaDW.setBounds(28, 234, 113, 14);
            textAreaDW.setFont(font);
            textAreaDW.setBackground(new Color(0x005f6b));
            textAreaDW.setEditable(false);
            textAreaDW.setForeground(Color.white);
            textAreaDW.setText("");

            connectedAs.setBounds(600, 564, 200, 14);
            connectedAs.setFont(font);
            connectedAs.setBackground(new Color(0x005f6b));
            connectedAs.setForeground(new Color(0x70c0c0));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login() {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new GridLayout(2,2));
        JLabel usernameLbl = new JLabel("Username:");
        JLabel passwordLbl = new JLabel("Password:");
        JTextField username = new JTextField();
        JPasswordField passwordFld = new JPasswordField();
        userPanel.add(usernameLbl);
        userPanel.add(username);
        userPanel.add(passwordLbl);
        userPanel.add(passwordFld);

        int input = JOptionPane.showConfirmDialog(null, userPanel, "Enter your password:" ,JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        char[] correctPassword = {'1', '2', '3', '4'};

        if (input == 0) {
            System.out.println("\nUsername entered was: " + username.getText());
            user = username.getText();
            connectedAs.setText("Connected as: " + user);
            char[] enteredPassword = passwordFld.getPassword();
            System.out.println("\nPassword entered was: " + String.valueOf(enteredPassword));

            if (Arrays.equals(correctPassword, enteredPassword)) {
                System.out.println("\nThe password entered is correct!");
                jFrame.setVisible(true);
                writeToLog(user + " logged in.");
                startRunning();
            }

            else {
                System.out.println("\nCall security!");
            }

            Arrays.fill(enteredPassword, '0');
        }

        else {
            System.out.println("\nUser didn't login.");
        }
    }

    public void startRunning() {
        System.out.println("Connecting to " + serverIP + ", on port nr. " + portnumber + "....");
        connectToScale();
        System.out.println("Connected.");
        setupStreams();
        while(true) {
            whileConnected();
        }
    }


    private void connectToScale() {
        try {
            connection = new Socket(serverIP, portnumber);
        } catch (IOException e) {
            System.out.println("Couldn't connect to server");
        }

    }

    private void setupStreams() {
        try {
            outputStream = new DataOutputStream(connection.getOutputStream());
            fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            System.out.println("Streams are set.");
        } catch (IOException e) {
            System.out.println("Couldn't setup the input or output streams.");
        }
    }

    private void closeStreams() {

        try {
            outputStream.flush();
            outputStream.close();

            fromServer.close();
        } catch (IOException e) {
            System.out.println("Failed to close streams properly.");
        }
    }

    public void sendMessage(String message) {
        try {
            outputStream.writeBytes(message);
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ableToType(boolean tof) {
        SwingUtilities.invokeLater(() -> jTextFieldDT.setEditable(tof));
    }

    private String whileConnected() {
        ableToType(true);
        String receivedFromServer = null;
        String trimmedResponse = null;

        if (connection.isClosed()) {
            return "Serveren er ikke online.";
        }

        try {
            receivedFromServer = fromServer.readLine();
            trimmedResponse = receivedFromServer.substring(0, 2);

            switch (trimmedResponse) {
                case "T ":
                    textAreaTA.setText(receivedFromServer.replaceAll("T", "").replaceAll("S", "").trim());
                    textAreaDW.setText("0.000 kg");
                    break;

                case "S ":
                    textAreaDW.setText(receivedFromServer.replaceAll("S", "").trim());
                    break;

                case "DW":
                    jTextFieldDT.setText("");
                    break;

                case "Z ":
                    textAreaDW.setText("0.000 kg");
                    textAreaTA.setText(("0.000 kg"));
                    break;

                case "D ":
                    jTextFieldDT.setText("");
                    break;

                default:
                    writeToLog("Command not found:" + trimmedResponse);
                    System.out.println(trimmedResponse);
            }
        } catch (IOException e) {
           System.out.println("Vi kunne ikke f� svar fra serveren");
            try {
                wait(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }

        return receivedFromServer;
    }

    private void writeToLog(String m) {
        File log = new File("res/log.txt");

        if (!log.exists()) {
            try {
                log.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter fw = new FileWriter("res/log.txt", true);
            fw.write(LocalDateTime.now() + ", " + m + "\r\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openFile(String path) {
        ProcessBuilder pb = new ProcessBuilder("notepad.exe", path);
        try {
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void beginProcedure() {
        switch (counter) {
            case 0:
                vareNummer = JOptionPane.showInputDialog("Indtast varenummer");
                counter++;
                break;

            case 1:
                if (!verifiticeret) {
                    String toBeConfirmed = "You are " + user + ", and you have chosen product nr. " + vareNummer;
                    int i = JOptionPane.showConfirmDialog(null, toBeConfirmed, "Hmm", JOptionPane.YES_NO_OPTION);
                    System.out.println(i);
                    if (i == 0) {
                        counter ++;
                    }
                    else {
                        counter = -1;
                    }
                }
                break;

            case 2:
                System.out.println("Brugeren skal nu Tar�rer");
                counter++;
                break;

            case 3:
                System.out.println("P�fyld vare");
                counter++;
                break;

            case 4:
                System.out.println("Afm�l nettov�gt");
                counter++;
                break;

            case 5:
                System.out.println("Foretag bruttokontrol");
                counter++;
                break;

            case 6:
                System.out.println("Afskriv forbrugt r�vare i database og indf�r afvejningen i loggen");
                counter++;
                break;

            case 7:
                System.out.println("Succes, brugeren kan nu starte forfra");
                counter = 0;
                break;
        }
    }
}