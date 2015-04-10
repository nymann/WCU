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
    String vareNavn = "";
    boolean verifiticeret = false;
    int counter = 0;
    double weightOnScale;
    double tareAmmount;

    // colors
    Color bgColor = new Color(0x005f6b);
    Color textColor = new Color(0xb8b9b9);
    Color menuColor = new Color(0x024b55);
    Color menuFontColor = new Color(0x70c0c0);
    Color sidebarColor = new Color(0x015762);


    // header buttons
    JButton beginProcedure = new JButton();
    JButton openLog = new JButton();
    JButton openStore = new JButton();


    public GUI() {
        customFont();
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

        customFont();
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
                writeToLog(user + " clicked begin procedure!", "log");
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
                writeToLog(user + " clicked Open Log.txt", "log");
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
                writeToLog(user + " clicked on open store", "log");
                openFile("res/store.txt");
            });


        } catch (IOException e) {
            System.out.println("GUI, failed to initialize! This should never happen.");
        }
    }

    private void menu() {
        Border noBorder = BorderFactory.createEmptyBorder();

        connect.setBounds(25, 143, 70, 14);
        try {
            BufferedImage commandsBtn = ImageIO.read(new File("res/img/connect.png"));
            connect.setIcon(new ImageIcon(commandsBtn));
        } catch (IOException e) {
            System.out.println("GUI, failed to initialize! This should never happen.");
        }
        connect.setBorder(noBorder);
        connect.setBackground(new Color(0x024b55));
        connect.setToolTipText("connect!");
        connect.addActionListener(actionEvent -> {
            writeToLog(user + " clicked on connect", "log");
        });

        restart.setBounds(371, 143, 57, 14);
        try {
            BufferedImage restartBtn = ImageIO.read(new File("res/img/restart.png"));
            restart.setIcon(new ImageIcon(restartBtn));
        } catch (IOException e) {
            System.out.println("GUI, failed to initialize! This should never happen.");
        }

        restart.setBorder(noBorder);
        restart.setBackground(new Color(0x024b55));
        restart.setToolTipText("Click to restart the procedure!");
        restart.addActionListener(actionEvent -> {
            writeToLog(user + " clicked restart.", "log");
            try {
                Runtime.getRuntime().exec("java -jar WCU.jar");
            } catch (IOException e) {
                System.out.println("GUI, failed to initialize! This should never happen.");
            }
            System.exit(1);
        });

        disconnect.setBounds(694, 143, 80, 14);
        BufferedImage disconnectBtn = null;
        try {
            disconnectBtn = ImageIO.read(new File("res/img/disconnect.png"));
        } catch (IOException e) {
            System.out.println("GUI, failed to initialize! This should never happen.");
        }
        disconnect.setIcon(new ImageIcon(disconnectBtn));
        disconnect.setBorder(noBorder);
        disconnect.setBackground(new Color(0x024b55));
        disconnect.setToolTipText("Click to disconnect from the weight!");
        disconnect.addActionListener(actionEvent -> {
            writeToLog(user + " clicked on disconnect", "log");
            try {
                Runtime.getRuntime().exec("java -jar WCU.jar");
            } catch (IOException e) {
                System.out.println("GUI, failed to initialize! This should never happen.");
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
            //displayWeight.setText("Display Weight");
            displayWeight.setBorder(noBorder);
            displayWeight.setBackground(new Color(0x005f6b));
            //displayWeight.setForeground(textColor);
            displayWeight.setToolTipText("text");
            displayWeight.setOpaque(false);
            displayWeight.addActionListener(actionEvent -> {
                sendMessage("S\r\n");
                writeToLog(user + " clicked display weight", "log");
            });

            tare.setBounds(384, 208, 32, 14);
            BufferedImage tareBtn = ImageIO.read(new File("res/img/tare.png"));
            tare.setIcon(new ImageIcon(tareBtn));
            tare.setBorder(noBorder);
            tare.setBackground(new Color(0x005f6b));
            tare.setToolTipText("text");
            tare.addActionListener(actionEvent -> {
                sendMessage("T\r\n");
                writeToLog(user + " clicked tare", "log");
            });

            textAreaTA.setBounds(37, 273, 200, 30);
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
                writeToLog(user + " clicked zero scale", "log");
            });

            grossControl.setVisible(false);
            grossControl.setBounds(29, 385, 109, 14);
            BufferedImage grossControlBtn = ImageIO.read(new File("res/img/grossControl.png"));
            grossControl.setIcon(new ImageIcon(grossControlBtn));
            grossControl.setBorder(noBorder);
            grossControl.setBackground(new Color(0x005f6b));
            grossControl.setToolTipText("text");
            grossControl.addActionListener(actionEvent -> {
                writeToLog(user + " clicked gross control", "log");
            });

            addItem.setVisible(false);
            addItem.setBounds(368, 385, 64, 14);
            BufferedImage addItemBtn = ImageIO.read(new File("res/img/addItem.png"));
            addItem.setIcon(new ImageIcon(addItemBtn));
            addItem.setBorder(noBorder);
            addItem.setBackground(new Color(0x005f6b));
            addItem.setToolTipText("text");
            addItem.addActionListener(actionEvent -> {
                writeToLog(user + " clicked add item.", "log");

            });

            setScaleOptions.setVisible(false);
            setScaleOptions.setBounds(629, 385, 140, 14);
            BufferedImage setScaleOptionsBtn = ImageIO.read(new File("res/img/setScaleOptions.png"));
            setScaleOptions.setIcon(new ImageIcon(setScaleOptionsBtn));
            setScaleOptions.setBorder(noBorder);
            setScaleOptions.setBackground(new Color(0x005f6b));
            setScaleOptions.setToolTipText("set scale options");
            setScaleOptions.addActionListener(actionEvent -> {
                writeToLog(user + " clicked set scale options", "log");
            });

            displayText.setBounds(366, 564, 95, 14);
            BufferedImage displayTextBtn = ImageIO.read(new File("res/img/displayText.png"));
            displayText.setIcon(new ImageIcon(displayTextBtn));
            displayText.setBorder(noBorder);
            displayText.setBackground(new Color(0x005f6b));
            displayText.setToolTipText("text");
            displayText.addActionListener(actionEvent -> {
                sendMessage("D " + '"' + jTextFieldDT.getText() + '"' + "\r\n");
                writeToLog(user + " clicked dispay text", "log");
            });

            jTextFieldDT.setBounds(26, 557, 321, 29);
            jTextFieldDT.setBackground(new Color(0x034b55));
            jTextFieldDT.setForeground(Color.white);
            jTextFieldDT.setCaretColor(Color.orange);
            jTextFieldDT.setBorder(noBorder);
            jTextFieldDT.addActionListener(actionEvent -> {
                sendMessage("D " + '"' + jTextFieldDT.getText() + '"' + "\r\n");
                writeToLog(user + " clicked diplay text", "log");
            });

            textAreaDW.setBounds(36, 230, 200, 30);
            textAreaDW.setBackground(new Color(0x005f6b));
            textAreaDW.setEditable(false);
            textAreaDW.setForeground(Color.white);
            textAreaDW.setText("");

            connectedAs.setBounds(600, 564, 200, 14);
            connectedAs.setBackground(new Color(0x005f6b));
            connectedAs.setForeground(new Color(0x70c0c0));

        } catch (IOException e) {
            System.out.println("GUI, failed to initialize! This should never happen.");
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

        int input = JOptionPane.showConfirmDialog(null, userPanel, "Login" ,JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

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
                writeToLog(user + " logged in.", "log");
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
           System.out.println("Couldn't send message to Scale");
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
                    tareAmmount = Double.parseDouble(onlyDigits(receivedFromServer));
                    textAreaDW.setText("0.000 kg");
                    weightOnScale = 0;
                    break;

                case "S ":
                    textAreaDW.setText(receivedFromServer.replaceAll("S", "").trim());
                    weightOnScale = Double.parseDouble(onlyDigits(receivedFromServer));
                    break;

                case "DW":
                    jTextFieldDT.setText("");
                    break;

                case "Z ":
                    textAreaDW.setText("0.000 kg");
                    textAreaTA.setText(("0.000 kg"));
                    weightOnScale = 0;
                    tareAmmount = 0;
                    break;

                case "D ":
                    jTextFieldDT.setText("");
                    break;

                default:
                    writeToLog("Command not found:" + trimmedResponse, "log");
                    System.out.println(trimmedResponse);
            }
        } catch (IOException e) {
           System.out.println("No response from server!");
            try {
                wait(500);
            } catch (InterruptedException e1) {
                System.out.println("Tried to wait for server response, it failed.");
            }
        }

        return receivedFromServer;
    }

    private void writeToLog(String m, String n) {
        File log = new File("res/" + n +".txt");

        if (!log.exists()) {
            try {
                log.createNewFile();
            } catch (IOException e) {
                System.out.println("The log filed didn't exist, but we failed to create it!");
            }
        }

        try {
            FileWriter fw = new FileWriter("res/" + n + ".txt", true);
            fw.write(LocalDateTime.now() + ", " + m + "\r\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Tried to create the file writer, it failed. Possible error, couldn't get the local time.");
        }
    }

    public void openFile(String path) {
        ProcessBuilder pb = new ProcessBuilder("notepad.exe", path);
        try {
            pb.start();
        } catch (IOException e) {
            System.out.println("Couldn't open Notepad.exe, user is probably using a different OS than Windows");
        }
    }

    public void beginProcedure() {
        switch (counter) {
            case 0:
                vareNummer = JOptionPane.showInputDialog("Insert item number");
                int vareNrSomINT = Integer.parseInt(vareNummer);
                System.out.println(vareNrSomINT);
                if ((!vareNummer.equals("")) && vareNrSomINT < 19) {
                    counter++;
                }
                else {
                    String m = "The inserted item number " + vareNrSomINT +", is not in the Store catalog, click on Open Store.txt to see the catalog!";
                    JOptionPane.showMessageDialog(null, m, "ERROR", JOptionPane.OK_OPTION);
                    counter = -1;
                }
                break;

            case 1:
                fileLookUp("store");
                if (!verifiticeret) {
                    String toBeConfirmed = "You are " + user + ", and you have chosen product nr. " + vareNummer + " which is " + vareNavn;
                    int i = JOptionPane.showConfirmDialog(null, toBeConfirmed, "WCU", JOptionPane.YES_NO_OPTION);
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
                sendMessage("Z\r\n");
                sendMessage("S\r\n");
                String container = "When the container is on the scale, press OK.";
                int i = JOptionPane.showConfirmDialog(null, container, "WCU", JOptionPane.OK_CANCEL_OPTION);
                if (i == 2) {
                    counter = 0;
                }
                else if (i == 0) {
                    counter++;
                    sendMessage("T\r\n");
                }
                break;

            case 3:
                String message = "When the item you want to weight is in the scale, press OK";
                int x = JOptionPane.showConfirmDialog(null, message, "WCU", JOptionPane.OK_CANCEL_OPTION);
                if (x == 2) {
                    counter = 0;
                }
                else if (x == 0) {
                    counter++;
                    sendMessage("S\r\n");
                    //sendMessage("T\r\n");
                }
                break;

            case 4:
                if (weightOnScale != 0.0) {
                    System.out.print("Net weight: ");
                    System.out.println(weightOnScale);
                    counter++;
                }
                break;

            case 5:
                System.out.print("Gross weight: ");
                System.out.println(weightOnScale + tareAmmount);
                String grossWeight = String.valueOf(weightOnScale + tareAmmount);
                grossWeight = grossWeight + " KG";
                int i1 = JOptionPane.showConfirmDialog(null, grossWeight, "Gross Weight", JOptionPane.YES_NO_OPTION);
                if (i1 == 0) {
                    counter++;
                }
                else {
                    counter = 2;
                }
                break;

            case 6:
                grossWeight = String.valueOf(weightOnScale + tareAmmount);
                String toLog = user + " finished a weight session, the item was: " + vareNummer + ", " + vareNavn + ", the gross weight was: " + grossWeight + " kg, and the net weight was: " + weightOnScale + " kg.";
                writeToLog(toLog, "storeLog");
                counter++;
                break;

            case 7:
                System.out.println("Succes, brugeren kan nu starte forfra");
                counter = -1;
                break;
        }
    }

    public void customFont() {
        Font basicFont = null;
        try {
            basicFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/basictitlefont.ttf"));
        } catch (FontFormatException e) {
            System.out.println("Couldn't format the cust font basictitlefont.");
        } catch (IOException e) {
            System.out.println("Couldn't create the custom font, is it in the res folder?");
        }
        Font basic32 = basicFont.deriveFont(32f);
        Font basic14 = basicFont.deriveFont(14f);

        textAreaTA.setFont(basic32);
        textAreaDW.setFont(basic32);
    }

    public String onlyDigits(String str) {
        str = str.replaceAll("[^0-9.]+", "");
        return str;
    }

    public void fileLookUp(String filename) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("res/" + filename + ".txt"));

            System.out.println("Looking up item name corresponding to item nr. " + vareNummer + " in " + filename +".txt");
            String str;

            while ((str = bufferedReader.readLine()) != null) {
                if (str.substring(0, str.indexOf(",")).equals(vareNummer)) {
                    vareNavn = str;
                    //System.out.println(vareNavn);
                }
            }

            bufferedReader.close();

            // This is so stupid, but it's the only way it seems to run without errors ???????
            vareNavn = vareNavn.substring(vareNavn.indexOf(","));
            vareNavn = vareNavn.replaceAll(",", "");
            vareNavn = vareNavn.trim();

        } catch (FileNotFoundException e) {
            System.out.println("We couldn't find the .txt file, are you sure you have a 'res' folder?");
        } catch (IOException e) {
            System.out.println("Failed to lookup the file, we couldn't create the reader.");
        }
    }

}
