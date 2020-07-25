import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Frame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ctr{
    public static void main(String args[]){
        
        new login();
     
    }
}

class login extends JFrame{

    private JLabel login = new JLabel("Login");
    private JLabel user = new JLabel("Username");
    private JLabel pass = new JLabel("Password");
    private JTextField username = new JTextField(10);
    private JPasswordField password = new JPasswordField(10);
    private JButton submit = new JButton("Login");
    private JButton register = new JButton("Register");
    private JButton fpass = new JButton("Forgot");
    private JButton change = new JButton("Change");

    private JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private JPanel top = new JPanel();
    private JPanel bot = new JPanel();

    private Font fontl = new Font("Arial",Font.PLAIN,50);
    private Font fontup = new Font("Arial",Font.PLAIN,16);

    Connection conn=null;
    PreparedStatement stmt= null;
    ResultSet rs = null;

    public static String Name,Lastname,Username;
    public static boolean Authorized = false;

    public login(){
        super("RESTAURANT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        
        split.setDividerSize(0);
        split.setResizeWeight(0.7);
        split.setEnabled(false);

        top.setBackground(Color.darkGray);
        top.setLayout(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.insets = new Insets(-10,120,60,0);

        bot.setBackground(Color.darkGray);
        bot.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(0,20,0,20);
        
        add(split);
        
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.gridwidth = 2;
        login.setFont(fontl);
        login.setForeground(Color.white);
        top.add(login,gbc1);
        
        gbc1.gridwidth = 1;
        gbc1.insets = new Insets(10,50,40,50);
        gbc1.gridx = 0;
        gbc1.gridy = 1;
        user.setFont(fontup);
        user.setForeground(Color.white);
        top.add(user,gbc1);

        gbc1.gridx = 1;
        gbc1.gridy = 1;
        top.add(username,gbc1);

        gbc1.gridx = 0;
        gbc1.gridy = 2;
        pass.setFont(fontup);
        pass.setForeground(Color.white);
        top.add(pass,gbc1);
 
        gbc1.gridx = 1;
        gbc1.gridy = 2;
        top.add(password,gbc1);

        gbc1.gridwidth = 2;
        gbc1.gridx = 0;
        gbc1.gridy = 3;
        submit.setMargin(new Insets(10,40,10,40));
        gbc1.insets = new Insets(0,130,0,140);
        top.add(submit,gbc1);

        register.setMargin(new Insets(10,30,10,30));
        bot.add(register,gbc2);
        change.setMargin(new Insets(10,40,10,40));
        bot.add(change,gbc2);
        fpass.setMargin(new Insets(10,40,10,40));
        bot.add(fpass,gbc2);

        split.setTopComponent(top);
        split.setBottomComponent(bot);

        Loginm lmanager = new Loginm();
        submit.addActionListener(lmanager);
        register.addActionListener(lmanager);
        fpass.addActionListener(lmanager);
        change.addActionListener(lmanager);

        }

        private class Loginm implements ActionListener{
            boolean in=false;
            public void actionPerformed(ActionEvent event){
                switch(event.getActionCommand()) {
                    case "Register":
                        new register();
                      break;
                    case "Login":
                    try{
                        Class.forName("com.mysql.jdbc.Driver");
                        conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
                        stmt=conn.prepareStatement("SELECT Pass,Username,Name,Lastname,Authorized FROM personel WHERE Username ='"+username.getText()+"'");
                        rs=stmt.executeQuery();
                        if(rs.next()){
                        if(rs.getString("Pass").equals(String.valueOf(password.getPassword()))){
                            Name=rs.getString("Name");
                            Lastname=rs.getString("Lastname");
                            Username=rs.getString("Username");
                            Authorized = rs.getBoolean("Authorized");
                                new tables();
                                dispose();
                                in = true;
                                }                          
                            }
                            if(in == false){
                            throw new Exception("");
                            }                   
                        }catch(Exception e){
        
                            JOptionPane.showMessageDialog(null,"Invalid","LOGIN",JOptionPane.PLAIN_MESSAGE);
                        }
                      break;
                    case "Forgot":
                        new forgot();
                      break;
                    case "Change":
                        new change();
                      break;

                    default:
                      break;
                  }
            }
        }
    }

    class change extends JFrame{
        private JLabel change = new JLabel("Change");
        private JLabel id = new JLabel("ID Num");
        private JLabel user = new JLabel("Username");
        private JLabel pass = new JLabel("Password");

        private JTextField idn = new JTextField(8);
        private JTextField usern = new JTextField(8);
        private JPasswordField passn = new JPasswordField(8);

        private JButton userb = new JButton("Change Username");
        private JButton passb = new JButton("Change Password");

        private JPanel pant = new JPanel();
        private JPanel panb = new JPanel();

        private JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        private Font f = new Font("Arial",Font.PLAIN,20);
        private Font fo = new Font("Arial",Font.PLAIN,14);

        Connection conn=null;
        PreparedStatement stmt= null;
        ResultSet rs = null;

        public change(){
        super("CHANGE");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300,300);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        split.setDividerSize(0);
        split.setResizeWeight(0.30);
        split.setEnabled(false);
        split.setBorder(null);

        pant.setBackground(Color.darkGray);
        pant.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0,50,20,50);

        add(split);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        change.setFont(f);
        change.setForeground(Color.white);
        pant.add(change,gbc);
        gbc.gridwidth = 1;
        
        gbc.insets = new Insets(0,10,0,10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        id.setFont(fo);
        id.setForeground(Color.white);
        pant.add(id,gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        pant.add(idn,gbc);

        panb.setBackground(Color.darkGray);
        panb.setLayout(new GridBagLayout());
        GridBagConstraints gbcb = new GridBagConstraints();
        gbcb.fill = GridBagConstraints.HORIZONTAL;
        gbcb.insets = new Insets(0,30,20,20);

        gbcb.gridx = 0;
        gbcb.gridy = 0;
        user.setFont(fo);
        user.setForeground(Color.white);
        panb.add(user,gbcb);

        gbcb.gridx = 1;
        gbcb.gridy = 0;
        pass.setFont(fo);
        pass.setForeground(Color.white);
        panb.add(pass,gbcb);

        gbcb.insets = new Insets(0,10,30,10);

        gbcb.gridx = 0;
        gbcb.gridy = 1;
        panb.add(usern,gbcb);

        gbcb.gridx = 1;
        gbcb.gridy = 1;
        panb.add(passn,gbcb);

        gbcb.insets = new Insets(0,10,0,10);

        gbcb.gridx = 0;
        gbcb.gridy = 2;
        userb.setMargin(new Insets(0,0,0,0));
        panb.add(userb,gbcb);

        gbcb.gridx = 1;
        gbcb.gridy = 2;
        passb.setMargin(new Insets(0,0,0,0));
        panb.add(passb,gbcb);

        split.setTopComponent(pant);
        split.setBottomComponent(panb);
        split.setBorder(null);

    Change cmanager = new Change();
        userb.addActionListener(cmanager);
        passb.addActionListener(cmanager);
    }

    private class Change implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if(event.getActionCommand() == "Change Username"){
                if(JOptionPane.showConfirmDialog(null,"Are You Sure You Want To Change Your Username","CHANGE USERNAME",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                    try{
                        if(usern.getText().isEmpty()){
                            throw new Exception("");
                        }
                    Class.forName("com.mysql.jdbc.Driver");
                    conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
                    stmt=conn.prepareStatement("UPDATE personel SET Username = '"+usern.getText()+"' WHERE Id ='"+idn.getText()+"'");
                    stmt.executeUpdate();
                    stmt=conn.prepareStatement("SELECT Username FROM personel WHERE Id ='"+idn.getText()+"'");
                    rs=stmt.executeQuery();
                        
                        if(rs.next()){
                            dispose();
                            JOptionPane.showMessageDialog(null,"Successfully Changed","CHANGE USERNAME",JOptionPane.PLAIN_MESSAGE);
                        }else{
                            throw new Exception("");
                            }
                   
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Invalid","CHANGE USERNAME",JOptionPane.PLAIN_MESSAGE);
                    }
            }
            }else{
                if(JOptionPane.showConfirmDialog(null,"Are You Sure You Want To Change Your Password","CHANGE PASSWORD",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                    try{
                        if(passn.getPassword().length == 0){
                            throw new Exception("");
                        }
                    Class.forName("com.mysql.jdbc.Driver");
                    conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
                    stmt=conn.prepareStatement("UPDATE personel SET Pass = '"+String.valueOf(passn.getPassword())+"' WHERE Id ='"+idn.getText()+"'");
                    stmt.executeUpdate();
                    stmt=conn.prepareStatement("SELECT Username FROM personel WHERE Id ='"+idn.getText()+"'");
                    rs=stmt.executeQuery();
                     
                        if(rs.next()){
                            dispose();
                            JOptionPane.showMessageDialog(null,"Successfully Changed","CHANGE PASSWORD",JOptionPane.PLAIN_MESSAGE);
                        }else{
                            throw new Exception("");
                            }                   
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Invalid","CHANGE Password",JOptionPane.PLAIN_MESSAGE);
                    }
                
                }
            }
        }
    }
}

    class forgot extends JFrame{

        private JLabel ff = new JLabel("Forgot");
        private JLabel idnum = new JLabel("ID Num");
        private JTextField idnumber = new JTextField(8);
        private JButton submiti = new JButton("Submit");

        private JPanel pan = new JPanel();

        private Font f = new Font("Arial",Font.PLAIN,20);
        private Font fo = new Font("Arial",Font.PLAIN,14);

        Connection conn=null;
        PreparedStatement stmt= null;
        ResultSet rs = null;

        public forgot(){
        super("FORGOT");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(230,180);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        pan.setBackground(Color.darkGray);
        pan.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        add(pan);

        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 0;
        ff.setForeground(Color.white);
        ff.setFont(f);
        pan.add(ff,gbc);

        gbc.insets = new Insets(20,5,0,5);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        idnum.setFont(fo);
        idnum.setForeground(Color.white);
        pan.add(idnum,gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        pan.add(idnumber,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        submiti.setMargin(new Insets(5,5,5,5));
        pan.add(submiti,gbc);

        Pas pmanager = new Pas();
        submiti.addActionListener(pmanager);

        }

        private class Pas implements ActionListener{
            public void actionPerformed(ActionEvent event){
                try{
                Class.forName("com.mysql.jdbc.Driver");
                conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
                stmt=conn.prepareStatement("SELECT Username,Pass FROM personel WHERE Id ='"+idnumber.getText()+"'");
                rs=stmt.executeQuery();
                    if(rs.next()){
                        dispose();
                        JOptionPane.showMessageDialog(null,"Username: "+rs.getString("Username")+"                   Password: "+rs.getString("Pass")+" ","FORGOT",JOptionPane.PLAIN_MESSAGE);
                    }else{
                        throw new Exception("");
                        }                   
                }catch(Exception e){

                    JOptionPane.showMessageDialog(null,"Invalid","FORGOT",JOptionPane.PLAIN_MESSAGE);
                }
                }
            }
        }


    class register extends JFrame{

        private JLabel register = new JLabel("Register");
        private JLabel idnum = new JLabel("ID Num");
        private JTextField id = new JTextField(8);
        private JLabel name = new JLabel("Name");
        private JTextField n = new JTextField(8);
        private JLabel lastname = new JLabel("Lastname");
        private JTextField l = new JTextField(8);
        private JLabel username = new JLabel("Username");
        private JTextField u = new JTextField(8);
        private JLabel password = new JLabel("Password");
        private JPasswordField pas = new JPasswordField(8);
        private JLabel phonenum = new JLabel("Phone Num");
        private JTextField p = new JTextField(8);
        private JButton submit = new JButton("Submit");
        private JLabel authorizedidnum = new JLabel("Authorized P. ID Num");
        private JTextField a = new JTextField(8);
        private JLabel isauthorized = new JLabel("Is Authorized");
        private JCheckBox ia = new JCheckBox();

        private JPanel pan = new JPanel();

        private Font f = new Font("Arial",Font.PLAIN,20);
        private Font fo = new Font("Arial",Font.PLAIN,14);

        Connection conn=null;
        PreparedStatement stmt= null;
        ResultSet rs = null;

        boolean c = false;

        public register(){
            super("REGISTER");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(320,390);
            setVisible(true);
            setResizable(false);
            setLocationRelativeTo(null);

            add(pan);

            pan.setBackground(Color.darkGray);
            pan.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0,-65,10,0);

            gbc.gridwidth = 2;
            gbc.gridx = 1;
            gbc.gridy = 0;
            register.setFont(f);
            register.setForeground(Color.white);
            pan.add(register,gbc);

            gbc.insets = new Insets(10,10,0,10);

            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = 1;
            idnum.setFont(fo);
            idnum.setForeground(Color.white);
            pan.add(idnum,gbc);

            gbc.gridx = 2;
            gbc.gridy = 1;
            pan.add(id,gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            name.setFont(fo);
            name.setForeground(Color.white);
            pan.add(name,gbc);

            gbc.gridx = 2;
            gbc.gridy = 2;
            pan.add(n,gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            lastname.setFont(fo);
            lastname.setForeground(Color.white);
            pan.add(lastname,gbc);

            gbc.gridx = 2;
            gbc.gridy = 3;
            pan.add(l,gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            phonenum.setFont(fo);
            phonenum.setForeground(Color.white);
            pan.add(phonenum,gbc);

            gbc.gridx = 2;
            gbc.gridy = 4;
            pan.add(p,gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            username.setFont(fo);
            username.setForeground(Color.white);
            pan.add(username,gbc);

            gbc.gridx = 2;
            gbc.gridy = 5;
            pan.add(u,gbc);

            gbc.gridx = 0;
            gbc.gridy = 6;
            password.setFont(fo);
            password.setForeground(Color.white);
            pan.add(password,gbc);

            gbc.gridx = 2;
            gbc.gridy = 6;
            pan.add(pas,gbc);

            gbc.gridx = 1;
            gbc.gridy = 7;
            gbc.insets = new Insets(10,-60,10,0);
            submit.setMargin(new Insets(5,5,5,5));
            pan.add(submit,gbc);

            gbc.insets = new Insets(10,10,0,10);
            gbc.gridx = 0;
            gbc.gridy = 8;
            authorizedidnum.setFont(fo);
            authorizedidnum.setForeground(Color.white);
            pan.add(authorizedidnum,gbc);

            gbc.gridx = 2;
            gbc.gridy = 8;
            pan.add(a,gbc);

            gbc.gridx = 0;
            gbc.gridy = 9;
            isauthorized.setFont(fo);
            isauthorized.setForeground(Color.white);
            pan.add(isauthorized,gbc);

            gbc.gridx = 2;
            gbc.gridy = 9;
            gbc.fill=5;
            ia.setBackground(Color.darkGray);
            pan.add(ia,gbc);


        Reg rmanager = new Reg();
        submit.addActionListener(rmanager);

        }

        private class Reg implements ActionListener{
            public void actionPerformed(ActionEvent event){
                 try{
                    if(id.getText().isEmpty() || n.getText().isEmpty() || l.getText().isEmpty() || u.getText().isEmpty() || pas.getPassword().length == 0){
                        throw new Exception("");
                    }
                     Class.forName("com.mysql.jdbc.Driver");
                     conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");

                     stmt=conn.prepareStatement("SELECT Authorized FROM personel WHERE Id='"+a.getText()+"'");
                     rs = stmt.executeQuery();
                     if(rs.next()){
                        c=rs.getBoolean("Authorized");
                        if(c==false){
                            throw new Exception("");
                        }
                    }else{
                        throw new Exception("");
                        }

                     stmt=conn.prepareStatement("INSERT INTO personel(Id,Name,Lastname,Phone,Username,Pass,Authorized) VALUES('"+id.getText()+"','"+n.getText()+"','"+l.getText()+"','"+p.getText()+"','"+u.getText()+"','"+String.valueOf(pas.getPassword())+"',"+ia.isSelected()+")");
                     stmt.executeUpdate();
                     dispose();
                     JOptionPane.showMessageDialog(null,"Successfully Registered","REGISTER",JOptionPane.PLAIN_MESSAGE);
                     }catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Invalid","REGISTER",JOptionPane.PLAIN_MESSAGE);              
                    }
        }
    }
}


    class tables extends JFrame{

        Date d = new Date();
        SimpleDateFormat fmd = new SimpleDateFormat("dd-MM-yyyy");

        Date t = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("HH.mm.ss");

        public List<String> foods = new ArrayList<>();
        public List<String> drinks = new ArrayList<>();
        public List<String> misc = new ArrayList<>();

        private JLabel name = new JLabel(login.Name);
        private JLabel lastname = new JLabel(login.Lastname);
        private JLabel username = new JLabel(login.Username);
        private JButton t1 = new JButton("Table 1");
        private JButton t2 = new JButton("Table 2");
        private JButton t3 = new JButton("Table 3");
        private JButton t4 = new JButton("Table 4");
        private JButton t5 = new JButton("Table 5");
        private JButton t6 = new JButton("Table 6");
        private JButton t7 = new JButton("Table 7");
        private JButton t8 = new JButton("Table 8");
        private JButton t9 = new JButton("Table 9");
        private JLabel date = new JLabel(fmd.format(d));
        private JLabel time = new JLabel(fmt.format(t));
        private JButton logout = new JButton("Logout");
        private JButton menu = new JButton("Menu");

        private JButton admin = new JButton("Admin");

        private JSplitPane splittop = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        private JSplitPane splitbot = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        private JPanel top = new JPanel();
        private JPanel mid = new JPanel();
        private JPanel bot = new JPanel();

        private Font f = new Font("Arial",Font.PLAIN,14);

        public static String tablename;
        String n=null;
        String p=null;

        Connection conn=null;
        PreparedStatement stmt= null;
        ResultSet rs = null;

        public tables(){
            super("TABLES");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(500,500);
            setVisible(true);
            setResizable(false);
            setLocationRelativeTo(null);

            splittop.setDividerSize(0);
            splittop.setResizeWeight(0.14);
            splittop.setEnabled(false);

            splitbot.setDividerSize(0);
            splitbot.setResizeWeight(0.91);
            splitbot.setEnabled(false);

            top.setBackground(Color.darkGray);
            top.setLayout(new GridBagLayout());
            GridBagConstraints gbct = new GridBagConstraints();
            gbct.fill = GridBagConstraints.HORIZONTAL;
            gbct.insets = new Insets(-10,150,10,170);

            mid.setBackground(Color.darkGray);
            mid.setLayout(new GridLayout(3,3));

            bot.setBackground(Color.darkGray);
            bot.setLayout(new GridBagLayout());
            GridBagConstraints gbcb = new GridBagConstraints();
            gbcb.fill = GridBagConstraints.HORIZONTAL;
            
            add(splitbot);

            gbct.gridx = 0;
            gbct.gridy = 0;
            name.setFont(f);
            name.setForeground(Color.white);
            top.add(name,gbct);

            gbct.gridx = 1;
            gbct.gridy = 0;
            username.setFont(f);
            username.setForeground(Color.white);
            top.add(username,gbct);

            gbct.insets = new Insets(0,150,-10,150);
            gbct.gridx = 0;
            gbct.gridy = 1;
            lastname.setFont(f);
            lastname.setForeground(Color.white);
            top.add(lastname,gbct);

            if(login.Authorized == true){
                gbct.gridx = 1;
                gbct.gridy = 1;
                top.add(admin,gbct);
            }

            mid.add(t1);
            mid.add(t2);
            mid.add(t3);
            mid.add(t4);
            mid.add(t5);
            mid.add(t6);
            mid.add(t7);
            mid.add(t8);
            mid.add(t9);

            gbcb.insets = new Insets(0,130,10,140);

            gbcb.gridx = 0;
            gbcb.gridy = 0;
            date.setFont(f);
            date.setForeground(Color.white);
            bot.add(date,gbcb);

            gbcb.gridx = 1;
            gbcb.gridy = 0;
            logout.setMargin(new Insets(5,5,5,5));
            bot.add(menu,gbcb);

            gbcb.insets = new Insets(0,130,0,140);
            gbcb.gridx = 0;
            gbcb.gridy = 1;
            time.setFont(f);
            time.setForeground(Color.white);
            bot.add(time,gbcb);

            gbcb.gridx = 1;
            gbcb.gridy = 1;
            logout.setMargin(new Insets(2,5,2,5));
            bot.add(logout,gbcb);

            splittop.setTopComponent(top);
            splittop.setBottomComponent(mid);
            splittop.setBorder(null);

            splitbot.setTopComponent(splittop);
            splitbot.setBottomComponent(bot);
            splitbot.setBorder(null);
        
            Table tmanager = new Table();
            t1.addActionListener(tmanager);
            t2.addActionListener(tmanager);
            t3.addActionListener(tmanager);
            t4.addActionListener(tmanager);
            t5.addActionListener(tmanager);
            t6.addActionListener(tmanager);
            t7.addActionListener(tmanager);
            t8.addActionListener(tmanager);
            t9.addActionListener(tmanager);
            logout.addActionListener(tmanager);
            admin.addActionListener(tmanager);
            menu.addActionListener(tmanager);
        }

        public void listt(){
            List<String> combined = new ArrayList<String>();
            String[] ar;
            String pri = "";
            try{
        Class.forName("com.mysql.jdbc.Driver");
        conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
        stmt=conn.prepareStatement("SELECT * FROM menu ORDER BY Name ASC");
        rs=stmt.executeQuery();
        while(rs.next()){
            n=rs.getString("Name");
            p=rs.getString("Price");
            if(rs.getString("Category").equals("Food")){
            foods.add(n+": "+p+"\n");
            continue;
            }
            if(rs.getString("Category").equals("Drink")){
            drinks.add(n+": "+p+"\n");
            continue;
            }
            if(rs.getString("Category").equals("Misc")){
            misc.add(n+": "+p+"\n");
            continue;
            }
        }
        foods.add(0,"FOODS:\n");
        foods.add("\n");
        drinks.add(0,"DRINKS:\n");
        drinks.add("\n");
        misc.add(0,"MISC:\n");
        misc.add("\n");
        
        combined.addAll(foods);
        combined.addAll(drinks);
        combined.addAll(misc);

        ar = combined.toArray(new String[0]);
        
        for(int i = 0;i<ar.length;i++){
           pri = pri + ar[i];
        }

        JOptionPane.showMessageDialog(null,pri,"MENU",JOptionPane.PLAIN_MESSAGE);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Invalid","MENU",JOptionPane.PLAIN_MESSAGE);
        }     
        }

        private class Table implements ActionListener{
            public void actionPerformed(ActionEvent event){
                if (event.getActionCommand()=="Logout"){
                        for(int i = 0;i<getFrames().length;i++){
                            Frame[] a = getFrames();
                            a[i].dispose();
                        }
                    login.Name=null;
                    login.Lastname=null;
                    login.Username=null;
                    login.Authorized=false;
                    new login();
                }else if(event.getActionCommand()=="Admin"){
                    new authorized();
                }else if(event.getActionCommand()=="Menu"){
                    listt();
                }else{
                    tablename = event.getActionCommand();
                    new bill();

                }
            }
        }
    }


    class bill extends JFrame{
        
        private JLabel bil = new JLabel("Bill");

        private JButton add = new JButton("Add");
        private JButton pay = new JButton("Pay");
        private JButton remove = new JButton("Remove");
        private JButton all = new JButton("Select");

        private JSplitPane splittop = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        private JSplitPane splitbot = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        private JSplitPane splitmid = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        JTable table;
        Object[][] values;
        String[] sums = {"Items Left : ","Total : "};

        private JPanel pant = new JPanel();
        private JScrollPane panms;
        private JPanel panb = new JPanel();

        private Font f = new Font("Arial",Font.PLAIN,20);

        public static JFrame billframe = new JFrame();

        Connection conn=null;
        PreparedStatement stmt= null;
        ResultSet rs = null;

        int i = 0;
        int len = 0;
        int l = 0;

        List<TableCellEditor> editors = new ArrayList<TableCellEditor>();

        public bill(){
            super(tables.tablename.toUpperCase());
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(320,300);
            setVisible(true);
            setResizable(false);
            setLocationRelativeTo(null);

            billframe = this;

            splittop.setDividerSize(0);
            splittop.setResizeWeight(0.10);
            splittop.setEnabled(false);
            splittop.setBorder(null);

            splitmid.setDividerSize(0);
            splitmid.setResizeWeight(0.15);
            splitmid.setEnabled(false);
            splitmid.setBorder(null);

            splitbot.setDividerSize(0);
            splitbot.setResizeWeight(0.91);
            splitbot.setEnabled(false);
            splitbot.setBorder(null);

            add(splitbot);

            pant.setBackground(Color.darkGray);
            pant.setLayout(new GridBagLayout());
            GridBagConstraints gbct = new GridBagConstraints();
            gbct.fill = GridBagConstraints.HORIZONTAL;
            gbct.insets = new Insets(0,0,0,0);

            bil.setFont(f);
            bil.setForeground(Color.white);
            pant.add(bil,gbct);

            len = getlength();
            values = new Object[len][3];
            getbill(values);

            for(int k = 0 ;k < len;k++){
                String[] items = new String[(int)values[k][2]];
                for(int l = 0, num = (int)values[k][2]; num > 0 ;l++,num--){
                    items[l] = String.valueOf(num);
                }
            JComboBox<String> comboBox1 = new JComboBox<String>( items );
            DefaultCellEditor dce1 = new DefaultCellEditor( comboBox1 );
            editors.add( dce1 );
            }
    


            table = new JTable(){
                public TableCellEditor getCellEditor(int row, int column)
            {
                int modelColumn = convertColumnIndexToModel( column );

                if (modelColumn == 2)
                    return editors.get(row);
                else
                    return super.getCellEditor(row, column);
            }
            };
            DefaultTableModel model = new DefaultTableModel(){
                public Class<?> getColumnClass(int column){
                    switch(column){
                        case 0:
                        return String.class;
                        case 1:
                        return Integer.class;
                        case 2:
                        return JComboBox.class;
                        case 3:
                        return Boolean.class;

                        default:
                            return String.class;
                    }
                }
            };

            table.setModel(model);
            model.addColumn("Name");
            model.addColumn("Price (One)");
            model.addColumn("Choose Amt");
            model.addColumn("Select");

            for(int p=0;p<i;p++){
                model.addRow(new Object[0]);
                model.setValueAt(values[p][0], p, 0);
                model.setValueAt(values[p][1], p, 1);
                model.setValueAt(values[p][2], p, 2);
                model.setValueAt(false, p, 3);
            }

   
            table.setFillsViewportHeight(true);
            table.setBackground(Color.darkGray);
            table.setForeground(Color.white);
            table.getTableHeader().setReorderingAllowed(false);

            JTableHeader header = table.getTableHeader();
            header.setLayout(new GridBagLayout());
            GridBagConstraints g = new GridBagConstraints();
            g.fill = GridBagConstraints.HORIZONTAL;
            g.insets = new Insets(0,225,0,0);

            all.setMargin(new Insets(0,15,5,20));
            header.add(all,g);

            panms = new JScrollPane(table);

            panms.setLayout(new ScrollPaneLayout());
            panms.getViewport().setBackground(Color.darkGray);
            GridBagConstraints gbctab = new GridBagConstraints();
            gbctab.fill = GridBagConstraints.HORIZONTAL;
            gbctab.insets = new Insets(0,0,0,0);
            panms.setBorder(null);
            
            panb.setBackground(Color.darkGray);
            panb.setLayout(new GridBagLayout());
            GridBagConstraints gbcb = new GridBagConstraints();
            gbcb.fill = GridBagConstraints.HORIZONTAL;
            gbcb.insets = new Insets(0,15,0,15);
            
            panb.add(add,gbcb);
            panb.add(pay,gbcb);
            panb.add(remove,gbcb);

            splittop.setTopComponent(pant);
            splittop.setBottomComponent(panms);

            splitbot.setTopComponent(splittop);
            splitbot.setBottomComponent(panb);
            
            Bill bmanager = new Bill();
            all.addActionListener(bmanager);
            add.addActionListener(bmanager);
            pay.addActionListener(bmanager);
            remove.addActionListener(bmanager);
        }

        private int getlength(){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
                stmt=conn.prepareStatement("SELECT Food FROM orders WHERE Tablename = '"+tables.tablename+"' GROUP BY Food");
                rs=stmt.executeQuery();
                while(rs.next()){
                    len++;
                }
                }catch(Exception e){
                }
            return len;

        }    

        private void getbill(Object[][] values){
            i=0;
        try{
            Class.forName("com.mysql.jdbc.Driver");

            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
            stmt=conn.prepareStatement("SELECT orders.Food,COUNT(orders.Food) as Total,menu.Price FROM menu INNER JOIN orders ON orders.Food = menu.Name WHERE Tablename = '"+tables.tablename+"' Group BY Food");
            rs=stmt.executeQuery();
            while(rs.next()){
                values[i][0]=rs.getString("Food");
                values[i][1]=rs.getInt("Price");
                values[i][2]=rs.getInt("Total");
                i++;
            }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Invalid","BILL",JOptionPane.PLAIN_MESSAGE);
            }
        }

        private class Bill implements ActionListener{
            public void actionPerformed(ActionEvent event){
                int cont = 0;
                Boolean checked = false;
                switch (event.getActionCommand()){
                case "Select":
                cont =0;
                    for(int i = 0;i<table.getRowCount();i++){
                        if(Boolean.valueOf(table.getValueAt(i, 3).toString())==true){
                            cont++;
                        }
                    }
                    if(cont == len){
                        for(int i = 0;i<table.getRowCount();i++){
                            table.setValueAt(false, i, 3);
                        }
                    }else{
                        for(int i = 0;i<table.getRowCount();i++){
                            table.setValueAt(true, i, 3);
                        }
                    }
                    
                    
                break;
                case "Add":
                new add();
                break;
                case "Pay":
                if(JOptionPane.showConfirmDialog(null,"Are You Sure You Want To Pay For These Items","PAY",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                for(int i = 0;i<table.getRowCount();i++){
                    if(Boolean.valueOf(table.getValueAt(i, 3).toString())==true){
                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                           conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
                           stmt=conn.prepareStatement("DELETE FROM orders WHERE Food ='"+table.getValueAt(i, 0).toString()+"' LIMIT "+table.getValueAt(i, 2)+"");
                           stmt.executeUpdate();
                           billframe.dispose();
                           new bill();
                            }catch(Exception e){
                               JOptionPane.showMessageDialog(null,"Invalid","PAY",JOptionPane.PLAIN_MESSAGE);
                               l++;
                            }
                    }
                    }
                    if(l==0){
                        JOptionPane.showMessageDialog(null,"Successfully Payed For Selected Items" ,"PAY",JOptionPane.PLAIN_MESSAGE);
                        l++;
                }
        }
                break;
                case "Remove":
                if(JOptionPane.showConfirmDialog(null,"Are You Sure You Want To Remove The Selected Items","REMOVE",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                    for(int i = 0;i<table.getRowCount();i++){
                        if(Boolean.valueOf(table.getValueAt(i, 3).toString())==true){
                            try{
                                Class.forName("com.mysql.jdbc.Driver");
                               conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
                               stmt=conn.prepareStatement("DELETE FROM orders WHERE Food ='"+table.getValueAt(i, 0).toString()+"' LIMIT "+table.getValueAt(i, 2)+"");
                               stmt.executeUpdate();
                               billframe.dispose();
                               new bill();
                                }catch(Exception e){
                                   JOptionPane.showMessageDialog(null,"Invalid","REMOVE",JOptionPane.PLAIN_MESSAGE);
                                   l++;
                                }
                        }
                        }
                        if(l==0){
                            JOptionPane.showMessageDialog(null,"Successfully Removed Selected Items" ,"REMOVE",JOptionPane.PLAIN_MESSAGE);
                            l++;
                    }
            }
                break;

                default:
                break;
            }
        }
    }
}
    
class add extends JFrame{

    private JLabel add = new JLabel("Add");

    public List<String> names = new ArrayList<>();
    public List<String> ad = new ArrayList<>();
    private JComboBox menu;

    private String[] amounts = {"1","2","3","4","5"};
    private JComboBox menua = new JComboBox(amounts);

    private JButton addb = new JButton("Add");

    private JPanel pan = new JPanel();

    private Font f = new Font("Arial",Font.PLAIN,20);

    Connection conn=null;
    PreparedStatement stmt= null;
    ResultSet rs = null;

    String n=null;
    String p=null;

    public add(){
        super("ADD TO : " + tables.tablename.toUpperCase());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300,150);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        pan.setBackground(Color.darkGray);
        pan.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(-10,110,20,0);

        add(pan);

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=2;
        add.setFont(f);
        add.setForeground(Color.white);
        pan.add(add,gbc);
        gbc.gridwidth=1;

        listt();
        menu = new JComboBox(names.toArray());
        
        gbc.insets = new Insets(0,15,0,15);
        gbc.gridx=0;
        gbc.gridy=1;
        pan.add(menu,gbc);

        gbc.gridx=1;
        gbc.gridy=1;
        pan.add(menua,gbc);

        gbc.gridx=2;
        gbc.gridy=1;
        pan.add(addb,gbc);

        Adds amanager = new Adds();
        addb.addActionListener(amanager);
    }

    public void listt(){
        try{
    Class.forName("com.mysql.jdbc.Driver");
    conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
    stmt=conn.prepareStatement("SELECT Name,Price FROM menu ORDER BY Name ASC");
    rs=stmt.executeQuery();
    while(rs.next()){
        n = rs.getString("Name");
        p = rs.getString("Price");
        names.add(n+": "+p);
        ad.add(n);
    }
    }catch(Exception e){
        JOptionPane.showMessageDialog(null,"Invalid","ADD TO : " + tables.tablename.toUpperCase(),JOptionPane.PLAIN_MESSAGE);
    }     
    }

    private class Adds implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if(JOptionPane.showConfirmDialog(null,"Are You Sure You Want To Add The Selected Items","ADD",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){        
        Date t = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("HH.mm.ss");
                try{
                     Class.forName("com.mysql.jdbc.Driver");
                     conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
                     for(int i = 0 ; i< Integer.valueOf(menua.getSelectedIndex()+1);i++){
                     stmt=conn.prepareStatement("INSERT INTO orders(Tablename,Food,Ordertime) VALUES('"+tables.tablename+"','"+ad.get(menu.getSelectedIndex())+"','"+fmt.format(t)+"')");
                     stmt.executeUpdate();
                     }
                     dispose();
                     for(int i = 0;i<getFrames().length;i++){
                        Frame[] a = getFrames();
                        if(a[i] == bill.billframe){
                        a[i].dispose();
                        }
                    }
                        new bill();
                        new add();
                        JOptionPane.showMessageDialog(null,"Successfully Added Item To "+ tables.tablename,"ADD TO : " + tables.tablename.toUpperCase(),JOptionPane.PLAIN_MESSAGE);       
                     }catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Invalid","ADD TO : " + tables.tablename.toUpperCase(),JOptionPane.PLAIN_MESSAGE);              
                    }
            }
        }
    }
}

class authorized extends JFrame{
    private JLabel admin = new JLabel("Admin");

    private JButton delper = new JButton("Delete Personel");
    private JButton delac = new JButton("Delete Your Account");
    private JButton delmenu = new JButton("Delete Menu Item");
    private JButton chanp = new JButton("Change Item");
    private JButton addit = new JButton("Add Item");
    private JButton endday = new JButton("End Of Day");

    private JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    
    private JPanel top = new JPanel();
    private JPanel bot = new JPanel();

    private Font f = new Font("Arial",Font.PLAIN,20);

    Connection conn=null;
    PreparedStatement stmt= null;

    public authorized(){
        super("ADMIN");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300,200);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        split.setDividerSize(0);
        split.setResizeWeight(0.1);
        split.setEnabled(false);
        split.setBorder(null);

        add(split);

        top.setBackground(Color.darkGray);
        bot.setBackground(Color.red);

        top.setBackground(Color.darkGray);
        top.setLayout(new GridBagLayout());
        GridBagConstraints gbct = new GridBagConstraints();
        gbct.fill = GridBagConstraints.HORIZONTAL;
        gbct.insets = new Insets(10,0,-5,0);

        admin.setFont(f);
        admin.setForeground(Color.white);
        top.add(admin,gbct);

        bot.setBackground(Color.darkGray);
        bot.setLayout(new GridBagLayout());
        GridBagConstraints gbcb = new GridBagConstraints();
        gbcb.fill = GridBagConstraints.HORIZONTAL;
        gbcb.insets = new Insets(0,5,5,5);

        gbcb.gridx = 0;
        gbcb.gridy=0;
        bot.add(delper,gbcb);
        gbcb.gridx = 0;
        gbcb.gridy=1;
        bot.add(delac,gbcb);
        gbcb.gridx = 0;
        gbcb.gridy=2;
        bot.add(delmenu,gbcb);
        gbcb.gridx = 1;
        gbcb.gridy=0;
        bot.add(chanp,gbcb);
        gbcb.gridx = 1;
        gbcb.gridy=1;
        bot.add(addit,gbcb);
        gbcb.gridx = 1;
        gbcb.gridy=2;
        bot.add(endday,gbcb);

        split.setTopComponent(top);
        split.setBottomComponent(bot);
    
        Admin admanager = new Admin();
        delper.addActionListener(admanager);
        delac.addActionListener(admanager);
        delmenu.addActionListener(admanager);
        chanp.addActionListener(admanager);
        addit.addActionListener(admanager);
        endday.addActionListener(admanager);
    }

    private class Admin implements ActionListener{
        public void actionPerformed(ActionEvent event){
            switch(event.getActionCommand()){
                case "Delete Personel":
                    new deletep();
                break;
                case "Delete Your Account":
                if(JOptionPane.showConfirmDialog(null,"Are You Sure You Want To Delete This Account","DELETE YOUR ACCOUNT",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){

                 try{
                     Class.forName("com.mysql.jdbc.Driver");
                    conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
                    stmt=conn.prepareStatement("DELETE FROM personel WHERE Username ='"+login.Username+"'");
                    stmt.executeUpdate();
                    for(int i = 0;i<getFrames().length;i++){
                        Frame[] a = getFrames();
                        a[i].dispose();
                    }
                    new login();
                    JOptionPane.showMessageDialog(null,"Successfully Deleted Your Account","DELETE YOUR ACCOUNT",JOptionPane.PLAIN_MESSAGE);
                     }catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Invalid","DELETE YOUR ACCOUNT",JOptionPane.PLAIN_MESSAGE);  
                     }
                }
                break;
                case "Delete Menu Item":
                    new deletemi();
                break;
                case "Change Item":
                    new changei();
                break;
                case "Add Item":
                    new addi();
                break;
                case "End Of Day":
                JOptionPane.showConfirmDialog(null,"Are You Sure You Want The End Of Day Report","END OF DAY REPORT",JOptionPane.YES_NO_OPTION);
                break;

                default:
                break;
            }
        }
    }
}

class deletep extends JFrame{
    private JLabel del = new JLabel("Delete Personel");
    private JLabel name = new JLabel("Select Name");

    public List<String> names = new ArrayList<>();
    public List<String> usernames = new ArrayList<>();
    private JComboBox list;

    private JButton but = new JButton("Delete");

    private JPanel pan = new JPanel();

    private Font f = new Font("Arial",Font.PLAIN,20);
    private Font fo = new Font("Arial",Font.BOLD,14);

    String n = null;
    String l = null;

    Connection conn=null;
    PreparedStatement stmt= null;
    ResultSet rs = null;

    public deletep(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300,200);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        
        add(pan);
        
        pan.setBackground(Color.darkGray);
        pan.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,5,20,0);

        gbc.gridx = 0;
        gbc.gridy=0;
        del.setFont(f);
        del.setForeground(Color.white);
        pan.add(del,gbc);

        gbc.insets = new Insets(0,30,5,30);
        gbc.gridx = 0;
        gbc.gridy = 1;
        name.setFont(fo);
        name.setForeground(Color.white);
        pan.add(name,gbc);

        listt();
        list = new JComboBox(names.toArray());

        gbc.insets = new Insets(0,0,20,0);
        gbc.gridx = 0;
        gbc.gridy = 2;
        pan.add(list,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        pan.add(but,gbc);
    
        Deletep depmanager = new Deletep();
        but.addActionListener(depmanager);
    }

    public void listt(){
        try{
    Class.forName("com.mysql.jdbc.Driver");
    conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
    stmt=conn.prepareStatement("SELECT Name,Lastname,Username FROM personel ORDER BY Name ASC");
    rs=stmt.executeQuery();
    while(rs.next()){
        if(rs.getString("Username").equals(login.Username)){
            continue;
        }
        n = rs.getString("Name");
        l = rs.getString("Lastname");
        names.add(n+" "+l);
        usernames.add(rs.getString("Username"));
    }
    }catch(Exception e){
        JOptionPane.showMessageDialog(null,"Invalid","DELETE PERSONEL",JOptionPane.PLAIN_MESSAGE);
    }     
    }

    private class Deletep implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if(JOptionPane.showConfirmDialog(null,"Are You Sure You Want To Delete This Personel","DELETE PERSONEL",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                try{
                   Class.forName("com.mysql.jdbc.Driver");
                   conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
                   stmt=conn.prepareStatement("DELETE FROM personel WHERE Username ='"+usernames.get(list.getSelectedIndex())+"'");
                   stmt.executeUpdate();
                   dispose();
                   new deletep();
                   JOptionPane.showMessageDialog(null,"Successfully Deleted Personel","DELETE PERSONEL",JOptionPane.PLAIN_MESSAGE);
                    }catch(Exception e){
                       JOptionPane.showMessageDialog(null,"Invalid","DELETE PERSONEL",JOptionPane.PLAIN_MESSAGE);  
                    }
            }
        }
    }
}

class deletemi extends JFrame{
    private JLabel del = new JLabel("Delete Menu Item");
    private JLabel name = new JLabel("Select Item");

    public List<String> names = new ArrayList<>();
    private JComboBox list;

    private JButton but = new JButton("Delete");

    private JPanel pan = new JPanel();

    private Font f = new Font("Arial",Font.PLAIN,20);
    private Font fo = new Font("Arial",Font.BOLD,14);

    Connection conn=null;
    PreparedStatement stmt= null;
    ResultSet rs = null;

    public deletemi(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300,200);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        
        add(pan);
        
        pan.setBackground(Color.darkGray);
        pan.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,5,20,0);


        gbc.gridx = 0;
        gbc.gridy=0;
        del.setFont(f);
        del.setForeground(Color.white);
        pan.add(del,gbc);

        gbc.insets = new Insets(0,30,5,30);
        gbc.gridx = 0;
        gbc.gridy = 1;
        name.setFont(fo);
        name.setForeground(Color.white);
        pan.add(name,gbc);

        listt();
        list = new JComboBox(names.toArray());

        gbc.insets = new Insets(0,0,20,0);
        gbc.gridx = 0;
        gbc.gridy = 2;
        pan.add(list,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        pan.add(but,gbc);
    
        Deletemi demmanager = new Deletemi();
        but.addActionListener(demmanager);
    }

    public void listt(){
  
        try{
    Class.forName("com.mysql.jdbc.Driver");
    conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
    stmt=conn.prepareStatement("SELECT Name FROM menu ORDER BY Name ASC");
    rs=stmt.executeQuery();
    while(rs.next()){
        names.add(rs.getString("Name"));
    }
    }catch(Exception e){
        JOptionPane.showMessageDialog(null,"Invalid","DELETE MENU ITEM",JOptionPane.PLAIN_MESSAGE);
    }     
    }

    private class Deletemi implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if(JOptionPane.showConfirmDialog(null,"Are You Sure You Want To Delete This Menu Item","DELETE MENU ITEM",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                   conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
                   stmt=conn.prepareStatement("DELETE FROM menu WHERE Name ='"+list.getSelectedItem()+"'");
                   stmt.executeUpdate();
                   dispose();
                   new deletemi();
                   JOptionPane.showMessageDialog(null,"Successfully Deleted Menu Item","DELETE MENU ITEM",JOptionPane.PLAIN_MESSAGE); 
                    }catch(Exception e){
                       JOptionPane.showMessageDialog(null,"Invalid","DELETE MENU ITEM",JOptionPane.PLAIN_MESSAGE);  
                    }
            }
        }
}
}
class changei extends JFrame{
    private JLabel del = new JLabel("Change Menu Item");
    private JLabel name = new JLabel("Select Item");
    private JLabel cn = new JLabel("Change Name");
    private JLabel cp = new JLabel("Change Price");
    private JLabel cc = new JLabel("Change Category");

    public List<String> names = new ArrayList<>();
    private JComboBox list;
    
    private String[] cat = {"Food","Drink","Misc"};
    private JComboBox clist = new JComboBox(cat);

    private JButton butn = new JButton("Change Name");
    private JButton butp = new JButton("Change Price");
    private JButton butc = new JButton("Change Category");

    private JTextField cname = new JTextField(8);
    private JSpinner cprice = new JSpinner();

    private JPanel pant = new JPanel();
    private JPanel panb = new JPanel();
    private JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    private Font f = new Font("Arial",Font.PLAIN,20);
    private Font fo = new Font("Arial",Font.BOLD,14);

    Connection conn=null;
    PreparedStatement stmt= null;
    ResultSet rs = null;

    public changei(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450,300);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        split.setDividerSize(0);
        split.setResizeWeight(0.10);
        split.setEnabled(false);
        split.setBorder(null);

        add(split);
        
        pant.setBackground(Color.darkGray);
        pant.setLayout(new GridBagLayout());
        GridBagConstraints gbct = new GridBagConstraints();
        gbct.fill = GridBagConstraints.HORIZONTAL;
        gbct.insets = new Insets(5,5,20,0);

        gbct.gridx=0;
        gbct.gridy=0;
        del.setFont(f);
        del.setForeground(Color.white);
        pant.add(del,gbct);

        gbct.insets = new Insets(0,40,5,20);
        gbct.gridx=0;
        gbct.gridy=1;
        name.setFont(fo);
        name.setForeground(Color.white);
        pant.add(name,gbct);

        listt();
        list = new JComboBox(names.toArray());

        gbct.insets = new Insets(0,0,0,0);
        gbct.gridx=0;
        gbct.gridy=2;
        pant.add(list,gbct);

        panb.setBackground(Color.darkGray);
        panb.setLayout(new GridBagLayout());
        GridBagConstraints gbcb = new GridBagConstraints();
        gbcb.fill = GridBagConstraints.HORIZONTAL;
        gbcb.insets = new Insets(10,12,20,12);

        gbcb.gridx=0;
        gbcb.gridy=0;
        cn.setFont(fo);
        cn.setForeground(Color.white);
        panb.add(cn);
        gbcb.gridx=0;
        gbcb.gridy=1;
        panb.add(cname,gbcb);
        gbcb.gridx=1;
        gbcb.gridy=0;
        cp.setFont(fo);
        cp.setForeground(Color.white);
        panb.add(cp);
        gbcb.gridx=1;
        gbcb.gridy=1;
        panb.add(cprice,gbcb);
        gbcb.gridx=2;
        gbcb.gridy=0;
        cc.setFont(fo);
        cc.setForeground(Color.white);
        panb.add(cc);
        gbcb.gridx=2;
        gbcb.gridy=1;
        panb.add(clist,gbcb);
        gbcb.insets = new Insets(0,12,0,12);
        gbcb.gridx=0;
        gbcb.gridy=2;
        panb.add(butn,gbcb);
        gbcb.gridx=1;
        gbcb.gridy=2;
        panb.add(butp,gbcb);
        gbcb.gridx=2;
        gbcb.gridy=2;
        panb.add(butc,gbcb);

        split.setTopComponent(pant);
        split.setBottomComponent(panb);
        
        Changemi cmmanager = new Changemi();
        butn.addActionListener(cmmanager);
        butp.addActionListener(cmmanager);
        butc.addActionListener(cmmanager);
    }

    public void listt(){
  
        try{
    Class.forName("com.mysql.jdbc.Driver");
    conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
    stmt=conn.prepareStatement("SELECT Name FROM menu ORDER BY Name ASC");
    rs=stmt.executeQuery();
    while(rs.next()){
        names.add(rs.getString("Name"));
    }
    }catch(Exception e){
        JOptionPane.showMessageDialog(null,"Invalid","CHANGE MENU ITEM",JOptionPane.PLAIN_MESSAGE);
    }     
    }

    private class Changemi implements ActionListener{
        public void actionPerformed(ActionEvent event){
            switch(event.getActionCommand()){
            case "Change Name":
            if(JOptionPane.showConfirmDialog(null,"Are You Sure You Want To Change The Name","CHANGE MENU ITEM NAME",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                try{
                Class.forName("com.mysql.jdbc.Driver");
                conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
                stmt=conn.prepareStatement("UPDATE menu SET Name = '"+cname.getText()+"' WHERE Name ='"+list.getSelectedItem()+"'");
                stmt.executeUpdate();
                dispose();
                new changei();
                JOptionPane.showMessageDialog(null,"Successfully Changed Menu Item","CHANGE MENU ITEM",JOptionPane.PLAIN_MESSAGE);
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null,"Invalid","CHANGE MENU ITEM",JOptionPane.PLAIN_MESSAGE);
                }
            }
            break;
            case "Change Price":
            if(JOptionPane.showConfirmDialog(null,"Are You Sure You Want To Change The Price","CHANGE MENU ITEM PRICE",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
                    stmt=conn.prepareStatement("UPDATE menu SET Price = '"+(int)cprice.getValue()+"' WHERE Name ='"+list.getSelectedItem()+"'");
                    stmt.executeUpdate();
                    dispose();
                    new changei();
                    JOptionPane.showMessageDialog(null,"Successfully Changed Menu Item","CHANGE MENU ITEM",JOptionPane.PLAIN_MESSAGE);
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Invalid","CHANGE MENU ITEM",JOptionPane.PLAIN_MESSAGE);
                    }
                }
            break;
            case "Change Category":
            if(JOptionPane.showConfirmDialog(null,"Are You Sure You Want To Change The Category","CHANGE MENU ITEM CATEGORY",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
                    stmt=conn.prepareStatement("UPDATE menu SET Category = '"+clist.getSelectedItem()+"' WHERE Name ='"+list.getSelectedItem()+"'");
                    stmt.executeUpdate();
                    dispose();
                    new changei();
                    JOptionPane.showMessageDialog(null,"Successfully Changed Menu Item","CHANGE MENU ITEM",JOptionPane.PLAIN_MESSAGE);
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Invalid","CHANGE MENU ITEM",JOptionPane.PLAIN_MESSAGE);
                    }
            }
            break;

            default:
            break;
        }
    }
}
}

class addi extends JFrame{
    private JLabel del = new JLabel("Add Menu Item");
    private JLabel n = new JLabel("Name");
    private JLabel p = new JLabel("Price");
    private JLabel c = new JLabel("Category");
    
    private String[] cat = {"Food","Drink","Misc"};
    private JComboBox clist = new JComboBox(cat);

    private JButton but = new JButton("Add Item");

    private JTextField name = new JTextField(8);
    private JSpinner price = new JSpinner();

    private JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    private JPanel pan = new JPanel();
    private JPanel panb = new JPanel();

    private Font f = new Font("Arial",Font.PLAIN,20);
    private Font fo = new Font("Arial",Font.BOLD,14);

    Connection conn=null;
    PreparedStatement stmt= null;
    
    public addi(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300,250);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        split.setDividerSize(0);
        split.setResizeWeight(0.60);
        split.setEnabled(false);

        add(split);

        pan.setBackground(Color.darkGray);
        pan.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0,50,15,50);

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=2;
        del.setFont(f);
        del.setForeground(Color.white);
        pan.add(del,gbc);

        gbc.insets = new Insets(0,10,15,10);
        gbc.gridwidth=1;
        gbc.gridx=0;
        gbc.gridy=1;
        n.setFont(fo);
        n.setForeground(Color.white);
        pan.add(n,gbc);
        gbc.gridx=1;
        gbc.gridy=1;
        pan.add(name,gbc);
        gbc.gridx=0;
        gbc.gridy=2;
        p.setFont(fo);
        p.setForeground(Color.white);
        pan.add(p,gbc);
        gbc.gridx=1;
        gbc.gridy=2;
        pan.add(price,gbc);


        gbc.insets = new Insets(0,10,-10,10);
        gbc.gridx=0;
        gbc.gridy=3;
        c.setFont(fo);
        c.setForeground(Color.white);
        pan.add(c,gbc);
        gbc.gridx=1;
        gbc.gridy=3;
        pan.add(clist,gbc);
        

        panb.setBackground(Color.darkGray);
        panb.setLayout(new GridBagLayout());
        GridBagConstraints gbcb = new GridBagConstraints();
        gbcb.fill = GridBagConstraints.HORIZONTAL;
        gbcb.insets = new Insets(0,0,0,0);
        gbcb.gridx=0;
        gbcb.gridy=0;
        panb.add(but,gbcb);

        split.setTopComponent(pan);
        split.setBottomComponent(panb);
        
        Addmi ammanager = new Addmi();
        but.addActionListener(ammanager);
    }

    private class Addmi implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if(JOptionPane.showConfirmDialog(null,"Are You Sure You Want To Add This Item To The Menu","ADD MENU ITEM",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                try{
                    if(name.getText().isEmpty() || price.getValue().equals("") || (int) price.getValue() < 0){
                        throw new Exception("");
                    }
                     Class.forName("com.mysql.jdbc.Driver");
                     conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root" ,"");
                     stmt=conn.prepareStatement("INSERT INTO menu(Name,Price,Category) VALUES('"+name.getText()+"',"+price.getValue()+",'"+clist.getSelectedItem()+"')");
                     stmt.executeUpdate();
                     dispose();
                     new addi();
                     JOptionPane.showMessageDialog(null,"Successfully Added Menu Item","ADD MENU ITEM",JOptionPane.PLAIN_MESSAGE);
                     }catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Invalid","ADD MENU ITEM",JOptionPane.PLAIN_MESSAGE);              
                    }
            }
    }
}
}