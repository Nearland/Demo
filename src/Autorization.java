import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Autorization {

    public JFrame Frame;

    PreparedStatement prepst;
    ResultSet res;
    Connection conn, connect;

    private static final String url = "jdbc:mysql://localhost:3306/test";
    private static final String user = "root";
    private static final String password = "pass";

    public Autorization() {

        Frame = new JFrame("Authorization");
        Frame.setLocationRelativeTo(null);// в центре экрана
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setSize(460, 150);
        Frame.dispose(); // отключает кнопки
        // Frame.setUndecorated(true); // делает не активными кнопки
        Frame.setResizable(false); // запрещает растягивать форму

        JPanel panel = new JPanel();
        panel.setLayout(null);


        JButton log;
        JButton ext;

        log = new JButton("Войти");
        log.setBounds(50, 70, 150, 33); // кординаты кнопки
        log.setBorderPainted(false); // убирает обводку кнопки
        log.setFocusPainted(false); // убирает рамку текста кнопки

        ext = new JButton("Выйти");
        ext.setBounds(250, 70, 150, 33); // кординаты кнопки
        ext.setBorderPainted(false); // убирает обводку кнопки
        ext.setFocusPainted(false); // убирает рамку текста кнопки


        JTextField login;
        JPasswordField pass;

        login = new JTextField(10);
        pass = new JPasswordField(10);


        JLabel l;
        JLabel p;

        l = new JLabel("Логин:");
        p = new JLabel("Пароль:");


        l.setBounds(50, 20, 80, 25);
        login.setBounds(100, 20, 100, 25);

        p.setBounds(240, 20, 80, 25);
        pass.setBounds(300, 20, 100, 25);

        panel.add(l);
        panel.add(login);
        panel.add(p);
        panel.add(pass);
        panel.add(log);
        panel.add(ext);
        Frame.add(panel);

        log.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evnt) {

                try {
                    conn = DriverManager.getConnection(url, user, password);
                    connect = DriverManager.getConnection(url, user, password);
                    prepst = conn.prepareStatement("SELECT * FROM autentification WHERE name=? AND hash=? AND status='moder'");
                    prepst = connect.prepareStatement("SELECT * FROM autentification WHERE name=? AND hash=? AND status='admin'");
                    String lll = login.getText();
                    String ppp = pass.getText();
                    prepst.setString(1, lll);
                    prepst.setString(2, ppp);
                    String hash = getHash(ppp);

                    res = prepst.executeQuery();

                    if ((test(conn, lll, hash)) == true) {

                        new DB();

                        JOptionPane.showMessageDialog(null, "Добро пожаловать модератор " + lll + "!");

                        Frame.dispose();
                    } else if ((test1(connect, lll, hash)) == true) {

                        new DB();

                        JOptionPane.showMessageDialog(null, "Добро пожаловать админ " + lll + "!");

                        Frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Неверный пароль или логин");
                    }

                    res.close();
                    prepst.close();
                    conn.close();

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

        });

        ext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evnt) {
                Frame.dispose();
            }
        });
        Frame.setVisible(true);
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

    boolean test1(Connection conect, String lll, String hash) throws SQLException {

        int rez = 0;
        PreparedStatement st = conect
                .prepareStatement("SELECT COUNT(*) FROM autentification WHERE name=? AND hash=? AND status='admin'");
        st.setString(1, lll);
        st.setString(2, hash);
        ResultSet res = st.executeQuery();
        while (res.next()) {
            rez = res.getInt(1);
        }
        st.close();
        return rez == 1;
    }

    boolean test(Connection con, String lll, String ppp) throws SQLException {
        int rez = 0;
        PreparedStatement st = con
                .prepareStatement("SELECT COUNT(*) FROM autentification WHERE name=? AND hash=? AND status='moder'");
        st.setString(1, lll);
        st.setString(2, ppp);
        ResultSet res = st.executeQuery();
        while (res.next()) {
            rez = res.getInt(1);
        }
        st.close();
        return rez == 1;
    }

    public static void main(String[] args){
        new Autorization();
    }

}
