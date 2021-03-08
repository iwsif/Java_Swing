
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.metal.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import jdk.jshell.JShell;

import java.security.MessageDigest;
import java.security.MessageDigestSpi;



public class Hasher {

    static JFrame frame = new JFrame();
    static Font font = new Font("Italic",Font.BOLD,21);
    static Font input_font = new Font("Italic",Font.ITALIC,17);
    static JPanel panel = new JPanel();
    static JLabel password = new JLabel();
    static JTextField text = new JTextField();
    static JButton button = new JButton();
    static JPasswordField password_to_hash = new JPasswordField();
    static MessageDigest hash_password = null;
    static JOptionPane option = new JOptionPane();
    
    public static void main(String[] args) {
    
        NimbusLookAndFeel nimbus = new NimbusLookAndFeel();

        try
        {
            UIManager.setLookAndFeel(nimbus);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }


        frame.setVisible(true);
        frame.setSize(450, 450);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setTitle("Sec Pass Tool");

        panel.setSize(450, 450);
        panel.setVisible(true);
        panel.setLayout(null);
        panel.setBackground(Color.DARK_GRAY);


        password.setBounds(20, 100, 200, 200);
        password.setVisible(true);
        password.setText("Password");
        password.setForeground(Color.BLACK);
        password.setFont(font);

        password_to_hash.setVisible(true);
        password_to_hash.setFont(input_font);
        password_to_hash.setForeground(Color.BLACK);
        password_to_hash.setBounds(155, 185, 220, 30);
        
        button.setVisible(true);
        button.setBounds(145,295,130,30);
        button.setForeground(Color.black);
        button.setFont(new Font("Italic",Font.BOLD,17));
        button.setText("Hash");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String to_hash = password_to_hash.getText();
                if (to_hash.equals("") == false) 
                {
                        String pass = secure_password(to_hash);
                        
                        option.showMessageDialog(frame, pass);
                }
                else 
                {
                    option.showMessageDialog(frame, "Write a password to hash !!");
                }
                option.setVisible(true);
            }
        });

        panel.add(password);
        panel.add(password_to_hash);
        panel.add(button);
        frame.add(panel);
        
    }  
    public static String secure_password(String plain_password) {
        String string = null;

        if (plain_password.equals("") == false) {
            try 
            {
                hash_password = MessageDigest.getInstance("SHA");
                byte[] pass = hash_password.digest(plain_password.getBytes());
                java.util.List <String> list  = new ArrayList <String>();
                
                for (int i=0;i<pass.length;i++)
                {
                    list.add(String.format("%02x",pass[i]));
                }
                StringBuilder builder = new StringBuilder();

                for (int i=0; i<list.size();i++)
                {
                    builder.append(list.get(i));
                }

                string = builder.toString();
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        return string;
    } 
}

