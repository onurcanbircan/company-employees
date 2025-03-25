import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GirisEkrani extends JFrame {

    CalisanIslemleri islemler = new CalisanIslemleri();
    
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField tfKullaniciAdi;
    private JPasswordField pfParola;
    private JLabel lblMessage;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GirisEkrani frame = new GirisEkrani();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public GirisEkrani() {
        setTitle("Giriş Ekranı");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(400, 200, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblKullaniciAdi = new JLabel("Kullanıcı Adı: ");
        lblKullaniciAdi.setBounds(84, 91, 81, 14);
        contentPane.add(lblKullaniciAdi);
        
        JLabel lblParola = new JLabel("Parola: ");
        lblParola.setBounds(84, 113, 81, 14);
        contentPane.add(lblParola);
        
        tfKullaniciAdi = new JTextField();
        tfKullaniciAdi.setBounds(175, 88, 115, 20);
        contentPane.add(tfKullaniciAdi);
        tfKullaniciAdi.setColumns(10);
        
        pfParola = new JPasswordField();
        pfParola.setBounds(175, 110, 115, 20);
        contentPane.add(pfParola);
        
        lblMessage = new JLabel("");
        lblMessage.setForeground(new Color(255, 0, 0));
        lblMessage.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblMessage.setBounds(76, 145, 358, 35);
        contentPane.add(lblMessage);
        
        JButton btnGiris = new JButton("Giriş Yap");
        btnGiris.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                String kullanici_adi = tfKullaniciAdi.getText();
                String parola = new String(pfParola.getPassword());
                
                boolean girisbasarili = islemler.girisYap(kullanici_adi, parola);
                if(girisbasarili)
                {
                    CalisanEkrani calisanEkrani = new CalisanEkrani();
                    calisanEkrani.setVisible(true);
                    dispose(); // Mevcut pencereyi kapat
                }
                else
                {
                    lblMessage.setText("Girişi başarısız, lütfen tekrar deneyiniz!");
                }
            }
        });
        btnGiris.setBounds(195, 207, 89, 23);
        contentPane.add(btnGiris);
    }
}
