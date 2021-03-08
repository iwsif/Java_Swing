
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.Pipe;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.ScrollPaneLayout.UIResource;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;

import java.io.*;
import java.awt.event.*;
import java.awt.*;
import java.util.regex.*;



public class Finder {
    
    static JFrame frame = new JFrame();
    static JLabel label = null;
    static JPanel panel = new JPanel();
    static UIManager manager = new UIManager();
    static JButton button = new JButton();
    static JTextArea textarea = new JTextArea();
    static JOptionPane option = new JOptionPane();
    static AudioInputStream stream = null;
    static Clip clip = null;
    static String address = null;
    static Pattern pattern = null;
    static Matcher match = null;
    static JPasswordField root_pass = new JPasswordField();
    public static void main(String[] args) throws IOException,UnsupportedLookAndFeelException{

        ImageIcon icon  = new ImageIcon("/home/joseph/Desktop/giphy.gif");

        manager.put("Panel.background",Color.lightGray);
        manager.put("OptionPane.background",Color.LIGHT_GRAY);
        manager.put("OptionPane.minimumSize",new Dimension(300,300));
        Border border2 = new LineBorder(Color.BLACK);
        manager.put("OptionPane.border",border2);
        manager.put("Panel.background",Color.BLACK);
        manager.put("Button.background",Color.BLACK);
        manager.put("Button.foreground",Color.GREEN);
        manager.put("Button.font",new Font("Serif",Font.PLAIN,17));                
        manager.put("OptionPane.messageForeground",Color.GREEN);
        manager.put("OptionPane.messageFont",new Font("Serif",Font.PLAIN,17));
        
        option.setAlignmentX(option.CENTER_ALIGNMENT);

        root_pass.setBackground(Color.black);
        root_pass.setForeground(Color.GREEN);
        root_pass.setVisible(true);
        root_pass.setFont(new Font("Serif",Font.PLAIN,18));
        

        frame.setSize(600,600);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        panel.setSize(600,600);
        panel.setVisible(true);
        panel.setBackground(new ColorUIResource(0,0,0));

        label = new JLabel(icon);
        label.setVisible(true);

        Border border = new LineBorder(Color.white);
        button.setText("Start");
        button.setForeground(Color.green);
        button.setBackground(Color.black);
        button.setVisible(true);
        button.setFont(new Font("Serif",Font.PLAIN,18));
        button.setBounds(220,400,130,20);
        button.setBorder(border);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File file = new File("moby.wav");
                try 
                {
                    stream  = AudioSystem.getAudioInputStream(file);
                    clip = AudioSystem.getClip();
                    clip.open(stream);
                    clip.loop(0);
                    TimeUnit.SECONDS.sleep(2);
                    String user_input = textarea.getText();
                    if (!user_input.equals(""))
                    {
                        String  add = find(user_input);
                        if (!user_input.contains("https://"))
                        {
                            user_input = "https://" + user_input;
                        }
                        
                        option.showConfirmDialog(frame,"Enable TOR proxies?","",option.YES_NO_OPTION,option.PLAIN_MESSAGE);
                        if (option.YES_OPTION == 0 && option.NO_OPTION != 1) 
                        {
                            option.showConfirmDialog(frame,root_pass,"Type your root pass", option.OK_CANCEL_OPTION,option.PLAIN_MESSAGE);
                           
                            if (option.OK_OPTION != 0)
                            {
                                option.showMessageDialog(frame,"Exiting","",option.PLAIN_MESSAGE);
                                TimeUnit.SECONDS.sleep(1);
                                System.exit(0);
                            }
                            else
                            {
                                Runtime process = Runtime.getRuntime();
                    
                                String password = new String(root_pass.getPassword());

                                String[] commands = {
                                    "sh",
                                    "-c",
                                    "echo " + "'" + password + "'" + "| sudo -S systemctl start tor.service"
                                };
                                Process ps = process.exec(commands);

                                if (!ps.isAlive())
                                {
                                    if (ps.exitValue() != 0)
                                    {
                                        System.out.println("Error");
                                    }
                                }
                                System.setProperty("socksProxyHost", "127.0.0.1");
                                System.setProperty("socksProxyPort", "9150");
                                
                            }
                        }

                        TimeUnit.SECONDS.sleep(2);                        


                        String  host_details = get_protocol(user_input);
                        
                        user_input = user_input.split("/")[1];
                        String open_ports = scan_ports(user_input);

                        if (!add.equals("Unknown host") && !add.equals("") )
                        {   
                            option.showMessageDialog(frame,add + "\n" + "Protocol:" + host_details + "\n" + open_ports,"Results",option.PLAIN_MESSAGE);
                        }
                        else if (add.equals("Unknown host"))
                        {
                            option.showMessageDialog(frame,"Unknown host!!","Host not found!!",option.PLAIN_MESSAGE);
                        }
                        else
                        {
                            option.showMessageDialog(frame,"Error","Error",option.PLAIN_MESSAGE); 
                        } 
            
                    }
                    else 
                    {
                        option.showMessageDialog(frame,"Wrong value!!","Error",option.PLAIN_MESSAGE);
                    }
                    if (option.CLOSED_OPTION == -1)
                    {
                        clip.stop();
                    }
                }
                catch(Exception exp)
                {
                    System.out.println(exp);
                }
            }
        });

        textarea.setVisible(true);
        textarea.setFont(new Font("Serif",Font.PLAIN,20));
        textarea.setBounds(160,300,250,27);
        textarea.setBackground(Color.BLACK);
        textarea.setForeground(Color.green);
        textarea.setBorder(border);

        frame.add(button);
        frame.add(textarea);
        frame.add(panel);
        panel.add(label);
        frame.repaint();
    }

    public static String find(String user_input){

        String regex = "[0123456789]{1,3}.[0123456789]{1,3}.[0123456789]{1,3}.[0123456789]{1,3}";
        pattern = Pattern.compile(regex);
        match = pattern.matcher(user_input);
        Boolean result = match.matches();
        try 
        {    
            if(result)
            {
                InetAddress ad = InetAddress.getByName(user_input);
                address = ad.getCanonicalHostName();
            }
            if(!result)
            {
                address = InetAddress.getByName(user_input).toString();
            }
        
        }
        catch(UnknownHostException e)
        {
            address = "Unknown host";
        }
        catch(Exception e) 
        {
            address = "";
        }
        return address;
    }
    public static String get_protocol(String host) {
        String result = null;
        try 
        {
            URL host_url = new URL(host);
            if (host_url != null)
            {
                result = host_url.getProtocol();
            }

        }
        catch(Exception e)
        {
            result = "";
        }
        return result;
    }

    public static String scan_ports(String host) throws SocketException,IOException,UnknownHostException{
        Socket socket = null;
        InetAddress address = InetAddress.getByName(host);
        String result = "";
        String closed_ports = null;
        Integer index = 0;

        for (index=0;index<65535;index++)    
        {    
            try
            {    
                socket = new Socket(address.getHostAddress(),index);
                result = result + "Port " + index.toString() + " is OPEN!!\n";
            }
            catch(SocketTimeoutException e)
            {
                closed_ports = index.toString();
            }
            catch(Exception e)
            {
                closed_ports = index.toString();
            }
        }
        return result;
    }

    
}

