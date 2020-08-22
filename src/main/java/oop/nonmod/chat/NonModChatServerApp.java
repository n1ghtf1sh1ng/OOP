package oop.nonmod.chat;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

public class NonModChatServerApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(NonModChatServerApp::new);
    }

    private JFrame frame;
    private NonModChatServer server;

    private JTextField hostField;
    private JSpinner portField;

    private StartAction startAction;
    private JButton startButton;
    private JLabel clockLabel;

    private JList<NonModChatUser> usersList;
    private JTextArea messageText;

    public NonModChatServerApp() {
        init();
        addNetworkInterfaceMessages();
    }


    private void init() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        frame = new JFrame("Chat Server");
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        initToolPane(pane);
        initCenterPane(pane);
        frame.add(pane);
        frame.pack();
        frame.addWindowListener(new StopHandler());
        frame.setVisible(true);
    }

    private void initToolPane(JPanel pane) {
        JPanel toolPane = new JPanel();
        toolPane.setLayout(new BoxLayout(toolPane, BoxLayout.Y_AXIS));
        toolPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        initToolServerPane(toolPane);
        initToolStartPane(toolPane);
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

    private void initToolStartPane(JPanel toolPane) {
        clockLabel = new JLabel("0");
        clockLabel.setPreferredSize(new Dimension(200, 14));
        startAction = new StartAction();
        startButton = new JButton(startAction);
        JPanel userPane = new JPanel();
        userPane.setLayout(new BoxLayout(userPane, BoxLayout.X_AXIS));
        userPane.add(new JLabel("Clock:"));
        userPane.add(clockLabel);
        userPane.add(Box.createHorizontalGlue());
        userPane.add(startButton);
        toolPane.add(userPane);
    }

    private void initCenterPane(JPanel pane) {
        JSplitPane centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        initCenterUsersPane(centerSplit);
        initCenterMessagePane(centerSplit);
        pane.add(centerSplit, BorderLayout.CENTER);
    }

    private void initCenterUsersPane(JSplitPane centerSplit) {
        usersList = new JList<>();
        usersList.setCellRenderer(new UserListCellRenderer());

        JScrollPane usersScroll = new JScrollPane(usersList);
        centerSplit.add(usersScroll);
    }

    private void initCenterMessagePane(JSplitPane centerSplit) {
        messageText = new JTextArea(30, 80);
        messageText.setEditable(false);

        JScrollPane messageScroll = new JScrollPane(messageText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        centerSplit.add(messageScroll);
    }

    private void addNetworkInterfaceMessages() {
        try {
            Enumeration<NetworkInterface> ifs = NetworkInterface.getNetworkInterfaces();
            while (ifs.hasMoreElements()) {
                NetworkInterface ni = ifs.nextElement();
                Enumeration<InetAddress> ads = ni.getInetAddresses();
                if (ads.hasMoreElements()) {
                    addMessage("Network-Interface: " + ni.getDisplayName());

                    while (ads.hasMoreElements()) {
                        InetAddress a = ads.nextElement();
                        if (a instanceof Inet4Address) {
                            addMessage("    " + a.getHostName() + ": " + a.getHostAddress());
                        }
                    }
                }
            }
            addMessage("For host-name, you can use one of above addresses.");
        } catch (Exception ex) {
            addMessage(String.format("network-interfaces error: %s", ex));
        }
    }

    class StartAction extends AbstractAction {
        public StartAction() {
            setToStart();
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            start();
        }

        public void setToStart() {
            putValue(NAME, "Start");
        }

        public void setToStop() {
            putValue(NAME, "Stop");
        }
    }

    public void start() {
        if (server == null) {
            try {
                String host = hostField.getText().trim();
                int port = ((Number) portField.getValue()).intValue();
                server = new NonModChatServer(this);
                server.startServer(host, port);
                startAction.setToStop();
            } catch (Exception ex) {
                server = null;
                addMessage(String.format("server failure: %s", ex));
                ex.printStackTrace();
            }
        } else {
            try {
                server.stop();
                server = null;
                startAction.setToStart();
            } catch (Exception ex) {
                addMessage(String.format("stop failure: %s", ex));
            }
        }
    }

    class UserListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof NonModChatUser) {
                NonModChatUser user = (NonModChatUser) value;
                setText(user.name + ": " + user.id);
            }
            return this;
        }
    }

    class StopHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            exit();
        }
    }

    public void exit() {
        if (server != null) {
            server.stop();
        }
        System.exit(0);
    }

    /////// methods for updating server info.

    public void addMessage(String str) {
        SwingUtilities.invokeLater(() -> {
            messageText.append(str + "\n");
            if (clockLabel != null && server != null) {
                synchronized (server) {
                    clockLabel.setText(Long.toString(server.serverClock));
                }
            }
        });
    }

    public void updateUsers(List<NonModChatUser> users) {
        SwingUtilities.invokeLater(() -> {
            usersList.setListData(new Vector<>(users));
        });
    }
}
