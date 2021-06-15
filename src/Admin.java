import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Admin {

    private static String url="jdbc:mysql://localhost:3306/test";
    private static String user="root";
    private static String pass="pass";

    public JFrame frame;

    JLabel name = new JLabel("Имя");
    JLabel pas = new JLabel("Пароль");
    JLabel status = new JLabel("Роль");

    JTextField nickName;
    JPasswordField password;
    JTextField stat;

    JButton add = new JButton("Добавить");
    JButton delete = new JButton("Удалить");

    public Admin(){
        JFrame frame = new JFrame("Админ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        DefaultTableModel dtm = new DefaultTableModel();
        JTable table = new JTable();

        dtm.addColumn("Name");
        dtm.addColumn("Pass");
        dtm.addColumn("Status");

        nickName = new JTextField(10);
        password = new JPasswordField(10);
        stat = new JTextField(10);


        name.setBounds(100,100, 100, 25);
        nickName.setBounds(100,130, 200, 25);
        pas.setBounds(100,160, 100, 25);
        password.setBounds(100,190, 200, 25);
        status.setBounds(100,220, 100, 25);
        stat.setBounds(100,250, 200, 25);


        add.setBounds(100,290, 200, 25);
        delete.setBounds(100,320, 200, 25);


        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String hashpas = password.getText();
                String hash = getHash(hashpas);
                try {
                    Connection con = DriverManager.getConnection(url, user, pass);
                    String sql = "INSERT INTO autentification (name, hash, status) VALUES (?,?,?)";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, nickName.getText());
                    pst.setString(2, hash);
                    pst.setString(3, stat.getText());

                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Success");

                }catch (Exception ex){
                    System.err.println(ex);
                }
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = DriverManager.getConnection(url, user, pass);
                    String query = "DELETE FROM autentification WHERE name=?";
                    PreparedStatement prt = con.prepareStatement(query);

                    prt.setString(1, nickName.getText());
                    prt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Success");
                    table.repaint();

                }catch (Exception ex){
                    System.err.println(ex);
                }
            }
        });

        try{
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement st = con.createStatement();
            String sql = "SELECT name, hash, status FROM autentification";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                dtm.addRow(new Object[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)
                });
            }
            table.setFillsViewportHeight(true);
            JScrollPane jsp = new JScrollPane(table);
            jsp.setBounds(500,100, 400,300);
            table.setModel(dtm);
            panel.add(jsp);

            con.close();
            st.close();
            rs.close();

        }catch (Exception ex){
            System.err.println(ex);
        }


        panel.add(name);
        panel.add(nickName);
        panel.add(pas);
        panel.add(password);
        panel.add(stat);
        panel.add(status);
        panel.add(add);
        panel.add(delete);
        frame.add(panel);


    }

    public static String getHash(String plaintext) {
        try {
            MessageDigest m;
            m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashtext = bigInt.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args){

        new Admin();
    }
}