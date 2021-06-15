//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
//
//public class Test extends JPanel {
//
//    private static final String url = "jdbc:mysql://localhost:3306/test";
//    private static final String user = "root";
//    private static final String password = "pass";
//
//    public Test() {
//
//        DefaultTableModel table = new DefaultTableModel();
//        JTable jt = new JTable();
//
//        table.addColumn("id");
//        table.addColumn("name");
//        table.addColumn("author");
//
//        try {
//            Connection conn = DriverManager.getConnection(url, user, password);
//            Statement stmt = conn.createStatement();
//            String query = "Select id, name, author from books";
//            ResultSet rs = stmt.executeQuery(query);
//
//            while (rs.next()) {
//                table.addRow(new Object[]{
//                        rs.getString(1),
//                        rs.getString(2),
//                        rs.getString(3)
//
//                });
//            }
//
//            jt.setPreferredScrollableViewportSize(new Dimension(450, 100));
//        //    jt.setPreferredSize(new Dimension(100,100));
//         //   jt.setLocation(200,600);
//            jt.setFillsViewportHeight(true);
//           // jt.setBounds(100, 200, 450, 200);
//
//            JScrollPane jps = new JScrollPane(jt);
//            add(jps);
//            jt.setModel(table);
//        } catch (Exception er) {
//            System.err.println(er);
//
//        }
//
//    }
//
//    public static void main(String[] args) {
//        JFrame jf = new JFrame("Test");
//        Test t = new Test();
//        jf.setSize(1000, 500);
//        jf.setVisible(true);
//        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jf.add(t);
//    }
//}
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
public class Test {
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
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String psw = "sd";
        String my = in.next();
        if (getHash(psw).equals(getHash(my))) {
            System.out.println("Пароли совпали");
            System.out.println(getHash(psw));
            System.out.println(getHash(my));
        } else {
            System.out.println("Пароли не совпали");
            System.out.println(getHash(psw));
            System.out.println(getHash(my));
        }
    }
}
