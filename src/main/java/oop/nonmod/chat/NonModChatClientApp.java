package oop.nonmod.chat;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NonModChatClientApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(NonModChatClientApp::new);
    }


    private JFrame frame;
    private JTextField hostField;
    private JSpinner portField;

    private JTextField userField;
    private JButton connectButton;
    private ConnectAction connectAction;

    private JTextArea messageText;
    private JTextField postField;

    private Timer retrieveTimer;

    private NonModChatClient client;


    public NonModChatClientApp() {
        init();
    }

    private void init() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        frame = new JFrame("Chat Client");

        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        initToolPane(pane);
        initMessagePane(pane);
        initPostPane(pane);
        frame.add(pane);
        frame.pack();

        frame.addWindowListener(new DisconnectHandler());
        frame.setVisible(true);

        initTimer();
    }

    private void initToolPane(JPanel pane) {
        JPanel toolPane = new JPanel();
        toolPane.setLayout(new BoxLayout(toolPane, BoxLayout.Y_AXIS));
        toolPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        initToolServerPane(toolPane);
        initToolUserPane(toolPane);
        pane.add(toolPane, BorderLayout.NORTH);
    }

    private void initToolServerPane(JPanel toolPane) {
        hostField = new JTextField(20);
        hostField.setText("localhost");
        portField = new JSpinner(new SpinnerNumberModel(8080, 0, 65545, 1));
        JPanel serverPane = new JPanel();
        serverPane.setLayout(new BoxLayout(serverPane, BoxLayout.X_AXIS));
        serverPane.add(new JLabel("Host:"));
        serverPane.add(hostField);
        serverPane.add(Box.createRigidArea(new Dimension(10, 10)));
        serverPane.add(new JLabel("Port:"));
        serverPane.add(portField);
        toolPane.add(serverPane);
    }

    private void initToolUserPane(JPanel toolPane) {
        userField = new JTextField(15);
        userField.setText(System.getProperty("user.name", "anonymous"));
        connectAction = new ConnectAction();
        connectButton = new JButton(connectAction);
        JPanel userPane = new JPanel();
        userPane.setLayout(new BoxLayout(userPane, BoxLayout.X_AXIS));
        userPane.add(new JLabel("User Name:"));
        userPane.add(userField);
        userPane.add(Box.createRigidArea(new Dimension(10, 10)));
        userPane.add(connectButton);
        toolPane.add(userPane);
    }

    private void initMessagePane(JPanel pane) {
        messageText = new JTextArea(30, 80);
        messageText.setEditable(false);
        JScrollPane messageScroll = new JScrollPane(messageText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        pane.add(messageScroll, BorderLayout.CENTER);
    }

    private void initPostPane(JPanel pane) {
        postField = new JTextField(20);
        postField.addActionListener(new PostHandler());
        postField.setEnabled(false);

        JPanel postPane = new JPanel();
        postPane.setLayout(new BoxLayout(postPane, BoxLayout.X_AXIS));
        postPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        postPane.add(new JLabel("Post:"));
        postPane.add(postField);

        pane.add(postPane, BorderLayout.SOUTH);
    }

    private void initTimer() {
        retrieveTimer = new Timer(10_000, new RetrieveHandler());
        retrieveTimer.setRepeats(true);
        retrieveTimer.start();
    }

    class ConnectAction extends AbstractAction {
        ConnectAction() {
            setToConnect();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            connect(); //call the below connect()
        }

        public void setToConnect() {
            putValue(Action.NAME, "Connect");
        }

        public void setToDisconnect() {
            putValue(Action.NAME, "Disconnect");
        }
    }

    public void connect() {
        if (client == null) {
            try {
                String host = hostField.getText().trim();
                int port = ((Number) portField.getValue()).intValue();
                String user = userField.getText().trim();
                client = new NonModChatClient(host, port);

                log("connect: url=%s, user=%s", client.getServerUrl(""), user);
                boolean ret = client.login(user);
                if (ret) {
                    log("connected: id=%s", client.getId());
                    connectAction.setToDisconnect();
                    postField.setEnabled(true);
                    postField.requestFocus();
                } else {
                    log("connection failure");
                    client = null;
                }
            } catch (Exception ex) {
                client = null;
                log("connection error: %s", ex);
                ex.printStackTrace();
            }
        } else {
            disconnect();
        }
    }

    public void disconnect() {
        if (client != null) {
            try {
                log("disconnect: url=%s, user=%s, id=%s",
                        client.getServerUrl(""),
                        client.getName(),
                        client.getId());
                client.leave();
                client = null;
                connectAction.setToConnect();
                postField.setEnabled(false);
            } catch (Exception ex) {
                log("disconnect error: %s", ex);
                ex.printStackTrace();
            }
        }
    }

    public void log(String format, Object... args) {
        String msg = String.format(format, args);
        System.err.println(msg);
        messageText.append(msg + "\n");
    }

    class DisconnectHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            exit();
        }
    }

    public void exit() {
        disconnect();
        retrieveTimer.stop();
        System.exit(0);
    }

    class PostHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            post();
        }
    }

    public void post() {
        try {
            if (client != null) {
                String msg = postField.getText();
                NonModChatMessage m = client.post(msg);
                logMessage(m);
                postField.setText("");
            }
        } catch (Exception ex) {
            log("post error: %s", ex);
            ex.printStackTrace();
        }
    }

    public void logMessage(NonModChatMessage message) {
        log("[%s] %s: %s",
                message.time.format(NonModChatServer.timeFormatter),
                message.user.name,
                message.message);
    }

    class RetrieveHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            retrieve();
        }
    }

    public void retrieve() {
        if (client != null) {
            try {
                java.util.List<NonModChatMessage> ms = client.retrieveMessages();
                System.err.println("ret " + ms.size());
                for (int i = 0; i < ms.size(); ++i) {
                    NonModChatMessage m = ms.get(i);
                    logMessage(m);
                }
            } catch (Exception ex) {
                log("retrieve error: %s", ex);
            }
        }
    }
}
