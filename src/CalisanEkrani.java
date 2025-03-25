import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class CalisanEkrani extends JDialog {

    DefaultTableModel model;
    CalisanIslemleri islemler = new CalisanIslemleri();
    
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTable tableCalisan;
    private JTextField tfAramaCubugu;
    private JTextField tfAd;
    private JTextField tfSoyad;
    private JTextField tfDepartman;
    private JTextField tfMaas;

    public void dinamikAra(String ara)
    {
    	
    	TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
    	
    	tableCalisan.setRowSorter(tr);
    	
    	tr.setRowFilter(RowFilter.regexFilter(ara));
    }
    
    public void calisanGoruntule()
    {
        model.setRowCount(0);
        
        ArrayList<Calisan> calisanlar = new ArrayList<Calisan>();
        calisanlar = islemler.calisanlariGetir();
        
        if(calisanlar != null && !calisanlar.isEmpty())
        {
            for(Calisan calisan:calisanlar)
            {
                Object[] eklenecek = {calisan.getId(), calisan.getAd(), calisan.getSoyad(), calisan.getDepartman(), calisan.getMaas()};
                model.addRow(eklenecek);
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            CalisanEkrani dialog = new CalisanEkrani();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CalisanEkrani() {
        setBounds(100, 100, 804, 553);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        tableCalisan = new JTable();
        tableCalisan.setModel(new DefaultTableModel(
            new Object[][] {
            },
            new String[] {
                "ID", "Ad", "Soyad", "Departman", "Maas"
            }
        ) {
            boolean[] columnEditables = new boolean[] {
                false, false, false, false, false
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tableCalisan);
        scrollPane.setBounds(10, 188, 768, 282);
        contentPanel.add(scrollPane);

        tableCalisan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                int selectedRow = tableCalisan.getSelectedRow();
                if (selectedRow != -1) { // Seçili satır olup olmadığını kontrol ediyoruz.
                    int modelRow = tableCalisan.convertRowIndexToModel(selectedRow); // Görünen satır indeksini model indeksine dönüştürüyoruz.

                    tfAd.setText(model.getValueAt(modelRow, 1).toString());
                    tfSoyad.setText(model.getValueAt(modelRow, 2).toString());
                    tfDepartman.setText(model.getValueAt(modelRow, 3).toString());
                    tfMaas.setText(model.getValueAt(modelRow, 4).toString());
                }
            }
        });
        scrollPane.setBounds(10, 175, 768, 295);
        contentPanel.add(scrollPane);
        
        tfAramaCubugu = new JTextField();
        tfAramaCubugu.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent e)
        	{
        		
        		String ara = tfAramaCubugu.getText();
        		dinamikAra(ara);
        		
        	}
        });
        tfAramaCubugu.setBounds(10, 11, 768, 20);
        contentPanel.add(tfAramaCubugu);
        tfAramaCubugu.setColumns(10);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(10, 34, 768, 7);
        contentPanel.add(separator);
        
        JLabel lblDepartman = new JLabel("Departman: ");
        lblDepartman.setBounds(10, 101, 71, 14);
        contentPanel.add(lblDepartman);
        
        JLabel lblAd = new JLabel("Ad: ");
        lblAd.setBounds(10, 52, 71, 14);
        contentPanel.add(lblAd);
        
        JLabel lblSoyad = new JLabel("Soyad: ");
        lblSoyad.setBounds(10, 77, 71, 14);
        contentPanel.add(lblSoyad);
        
        JLabel lblMaas = new JLabel("Maaş: ");
        lblMaas.setBounds(10, 126, 71, 14);
        contentPanel.add(lblMaas);
        
        tfAd = new JTextField();
        tfAd.setBounds(91, 49, 150, 20);
        contentPanel.add(tfAd);
        tfAd.setColumns(10);
        
        tfSoyad = new JTextField();
        tfSoyad.setColumns(10);
        tfSoyad.setBounds(91, 74, 150, 20);
        contentPanel.add(tfSoyad);
        
        tfDepartman = new JTextField();
        tfDepartman.setColumns(10);
        tfDepartman.setBounds(91, 98, 150, 20);
        contentPanel.add(tfDepartman);
        
        tfMaas = new JTextField();
        tfMaas.setColumns(10);
        tfMaas.setBounds(91, 123, 150, 20);
        contentPanel.add(tfMaas);
        
        JLabel lblMessage = new JLabel("");
        lblMessage.setForeground(new Color(255, 0, 0));
        lblMessage.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblMessage.setBounds(10, 150, 231, 20);
        contentPanel.add(lblMessage);
        
        JButton btnCalisanEkle = new JButton("Yeni Çalişan Ekle");
        btnCalisanEkle.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e)
        	{
        		
        		lblMessage.setText("");
        		String ad = tfAd.getText();
        		String soyad = tfSoyad.getText();
        		String departman = tfDepartman.getText();
        		String maas = tfMaas.getText();
        		
        		islemler.calisanEkle(ad, soyad, departman, maas);
        		calisanGoruntule();
        		lblMessage.setText("Yeni çalışan başarıyla eklendi..!");
        	}
        });
        btnCalisanEkle.setBounds(251, 48, 150, 23);
        contentPanel.add(btnCalisanEkle);
        
        JButton btnGuncelle = new JButton("Çalışan Güncelle");
        btnGuncelle.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e)
        	{
        		
        		String ad = tfAd.getText();
        		String soyad = tfSoyad.getText();
        		String departman = tfDepartman.getText();
        		String maas = tfMaas.getText();
        		
        		int selectedRow = tableCalisan.getSelectedRow();
        		
        		if(selectedRow == -1)
        		{
        			
        			if(model.getRowCount() == 0)
        			{
        				
                		lblMessage.setText("Çalışanlar tablosu şuanda boş...!");
        				
        			}
        			
        			else
        			{
        				
                		lblMessage.setText("Lütfen güncellenecek bir çalışan seçiniz..!");

        				
        			}
        			
        		}
        		
        		else
        		{
        			
        			int id = (int) model.getValueAt(selectedRow, 0);
        			islemler.calisanGuncelle(id, ad, soyad, departman, maas);
        			calisanGoruntule();
            		lblMessage.setText("Çalışan başarıyla güncellendi.!");
        		}
        		
        	}
        });
        btnGuncelle.setBounds(251, 73, 150, 23);
        contentPanel.add(btnGuncelle);
        
        JButton btnSil = new JButton("Çalışan Sil");
        btnSil.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e)
        	{
        		lblMessage.setText("");
        		
        		int selectedRow = tableCalisan.getSelectedRow();        		
        		if(selectedRow == -1)
        		{
        			
        			if(model.getRowCount() == 0)
        			{
        				
                		lblMessage.setText("Çalışanlar tablosu şuanda boş...!");
        				
        			}
        			
        			else
        			{
        				
                		lblMessage.setText("Lütfen silinecek bir çalışan seçiniz..!");

        				
        			}
        			
        		}
        		
        		else
        		{
        			
        			int id = (int) model.getValueAt(selectedRow, 0);
        			islemler.calisanSil(id);
        			calisanGoruntule();
            		lblMessage.setText("Çalışan başarıyla silindi.!");
        		}
        		
        	}
        });
        btnSil.setBounds(251, 97, 150, 23);
        contentPanel.add(btnSil);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
        
        model = (DefaultTableModel) tableCalisan.getModel();
        calisanGoruntule();
    }
}
