import javax.security.auth.login.LoginException;
import javax.swing.*;

import java.awt.event.*;
import java.awt.im.InputContext;
import java.awt.*;

public class Login implements ActionListener{
    static JFrame frame = new JFrame("Login Window");
    static    JLabel username = new JLabel();
    static   JLabel password = new JLabel();
    static    JPanel panel = new JPanel();
    static   JTextField username_box = new JTextField();
    static    JPasswordField password_box = new JPasswordField();
    static    JButton button = new JButton("Login");
     static   String real_username = "joseph";
     static   String real_password = "kiriakopoulos";

    public static void main(String[] args)  {
        Login login = new Login();        

        frame.setSize(600,225);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        panel.setSize(200,200);
        panel.setVisible(true);
        panel.setLayout(null);
    
        frame.add(login.panel);

        username.setBounds(55, 15, 100, 100);
        username.setText("Username:");
        username.setVisible(true);

        password.setBounds(55,60,100,100);
        password.setText("Password:");
        password.setVisible(true);

        username_box.setBounds(225,55,140,25);
        username_box.setVisible(true);

        password_box.setBounds(225,95,140,25);
        password_box.setVisible(true);

        panel.add(username);
        panel.add(password);
        panel.add(username_box);
        panel.add(password_box);
        
        
        button.setBounds(252,150,80,25);
        button.setVisible(true);
        button.addActionListener(new Login());
        panel.add(button);
        
        
    } 
    public void actionPerformed(ActionEvent e) {
        String user_name = username_box.getText();
        String user_password = password_box.getText();

        if (user_name.equals(real_username) && user_password.equals(real_password))
        {
            JOptionPane.showMessageDialog(null,"Success!!");
        }
        else if (!user_name.equals(real_username) && user_password.equals(real_password))
        {
            JOptionPane.showMessageDialog(null,"Username is incorrect!!");
        }
        else if (user_name.equals(real_username) && !user_password.equals(real_password))
        {
            JOptionPane.showMessageDialog(null,"Password is incorrect!!");
        }
        else if (user_name.equals("") && user_password.equals(""))
        {
            JOptionPane.showMessageDialog(null,"Wrong values!!");
        } 
        else
        {
            JOptionPane.showMessageDialog(null,"Incorrect username and password!!");
        }      
    } 
}