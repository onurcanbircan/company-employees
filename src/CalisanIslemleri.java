import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CalisanIslemleri
{

    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    
    public ArrayList<Calisan> calisanlariGetir()
    {
        
        ArrayList<Calisan> cikti = new ArrayList<Calisan>();
        
        try
        {
            stmt = conn.createStatement();
        } 
        
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        String sql = "Select * From calisanlar";
        
        try
        {
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next())
            {
                int id = rs.getInt("id");
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String departman = rs.getString("departman");
                String maas = rs.getString("maas"); // Maas verisini int olarak alıyoruz
                
                cikti.add(new Calisan(id, ad, soyad, departman, maas)); // Doğru sıralama ile ekliyoruz
            }
            return cikti;
        }
        
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public void calisanSil(int id)
    {
    	
    	String sql = "Delete from calisanlar where id =?";
    	
    	try
    	{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			pstmt.executeUpdate();
		}
    	
    	catch (SQLException e)
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public void calisanGuncelle(int id, String yeni_ad, String yeni_soyad, String yeni_departman, String yeni_maas)
    {
    	String sql = "Update calisanlar Set ad =?, soyad =?, departman =?, maas =? where id =?";
    	
    	try
    	{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, yeni_ad);
			pstmt.setString(2, yeni_soyad);
			pstmt.setString(3, yeni_departman);
			pstmt.setString(4, yeni_maas);
			pstmt.setInt(5, id);
			
			pstmt.executeUpdate();
		} 
    	
    	catch (SQLException e)
    	{
			e.printStackTrace();
		}
    	
    }
    
    public void calisanEkle(String ad, String soyad, String departman, String maas)
    {
    	String sql = "Insert Into calisanlar (ad, soyad, departman, maas) VALUES(?, ?, ?, ?)";
    	
    	try
    	{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ad);
            pstmt.setString(2, soyad);
            pstmt.setString(3, departman);
            pstmt.setString(4, maas);
            
            pstmt.executeUpdate();
		}
    	
    	catch (SQLException e)
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public boolean girisYap(String kullanici_adi, String parola)
    {
        String sql = "Select * From adminler where username =? AND password =?";
        
        try
        {
            if (conn != null)
            {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, kullanici_adi);
                pstmt.setString(2, parola);
                
                ResultSet rs = pstmt.executeQuery();
                boolean result = rs.next();
                rs.close(); // ResultSet'i kapat
                return result;
            }
            
            else
            {
                System.out.println("Bağlantı kurulamadı.");
                return false;
            }
        }
        
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    public CalisanIslemleri()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(Database.URL, Database.USER, Database.PASSWORD);
            System.out.println("Bağlantı başarılı...");
        }
        
        catch (ClassNotFoundException e)
        {
            System.out.println("JDBC Sürücüsü bulunamadı.");
            e.printStackTrace();
        }
        
        catch (SQLException e)
        {
            System.out.println("Veri tabanına bağlanırken hata oluştu.");
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        CalisanIslemleri islemler = new CalisanIslemleri();
    }
}
