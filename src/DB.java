import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class DB {

    private static final String url = "jdbc:mysql://localhost:3306/test";
    private static final String user = "root";
    private static final String password = "pass";

    JFrame jf = new JFrame("Test");
    JPanel jp = new JPanel();

    JTextField id;
    JTextField name;
    JTextField author;

    JButton add = new JButton("Добавить");
    JButton update = new JButton("Обновить");
    JButton delete = new JButton("Удалить");
    JButton admin = new JButton("Админ");

    JLabel jname = new JLabel("Имя");
    JLabel jid = new JLabel("Номер");
    JLabel jauthor = new JLabel("Автор");


    public DB() {

        DefaultTableModel tablee = new DefaultTableModel();
        JTable jt = new JTable();

        tablee.addColumn("id");
        tablee.addColumn("name");
        tablee.addColumn("author");

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            String query = "Select id, name, author from books";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                tablee.addRow(new Object[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)

                });
            }

            jt.setFillsViewportHeight(true);
            JScrollPane jps = new JScrollPane(jt);
            jps.setBounds(500, 100, 450, 200);
            jt.setModel(tablee);

            jp.add(jps);

            conn.close();
            stmt.close();
            rs.close();

        } catch (Exception er) {
            System.err.println(er);

        }

        jp.setLayout(null);

        id = new JTextField(10);
        name = new JTextField(10);
        author = new JTextField(10);

        jid.setBounds(100, 110, 200, 25);
        id.setBounds(100, 130, 200, 25);
        jname.setBounds(100, 150, 200, 25);
        name.setBounds(100, 170, 200, 25);
        jauthor.setBounds(100, 190, 200, 25);
        author.setBounds(100, 210, 200, 25);

        add.setBounds(100, 250, 100, 25);
        update.setBounds(220, 250, 100, 25);
        delete.setBounds(340, 250, 100, 25);
        admin.setBounds(100, 270, 100, 25);

        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    Connection con = DriverManager.getConnection(url, user, password);
                    String sql = "INSERT INTO books (name, author) VALUES (?, ?)";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, name.getText());
                    pst.setString(2, author.getText());
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Succsesfully");

                    con.close();
                    pst.close();


                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);

                }
            }
        });

        jt.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable jt = (JTable) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2 && jt.getSelectedRow() != -1) { // обработка
                    try {


                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Connection con = DriverManager.getConnection(url, user, password);
                    String sql = "DELETE FROM books WHERE id=?";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, id.getText());
                  //  pst.setString(2, author.getText());
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Successfully");
                    con.close();
                    pst.close();

                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex);

                }
            }
        });

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    Connection con = DriverManager.getConnection(url, user, password);
                    String sql = "UPDATE books SET name=?, author=? WHERE id=?";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(3, id.getText());
                    pst.setString(1, name.getText());
                    pst.setString(2, author.getText());
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Succsesfully");

                    con.close();
                    pst.close();

                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex);

                }
            }
        });

        admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Admin();
            }
        });

        jp.add(id);
        jp.add(name);
        jp.add(author);
        jp.add(jid);
        jp.add(jname);
        jp.add(jauthor);
        jp.add(add);
        jp.add(update);
        jp.add(delete);
        jp.add(admin);

        jf.setSize(1000, 500);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(jp);
    }
    public static void main(String[] args) {
        new DB();
    }
}
