package veritabani;
import java.io.FileInputStream; // Bunu ekle
import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class VeritabaniBaglantisi {
    private static Properties props = new Properties();
    private static Connection baglanti = null;

    static {
        try {
            // getResourceAsStream yerine FileInputStream kullanıyoruz
            // Bu, projenin en dışındaki dosyayı okur
            InputStream input = new FileInputStream("db.properties");
            props.load(input);
            input.close();
        } catch (Exception e) {
            System.out.println("[HATA] db.properties dosyası bulunamadı! Lütfen projenin ana klasörüne ekleyin.");
            System.out.println("Detay: " + e.getMessage());
        }
    }

    // ... geri kalan kodların (URL, KULLANICI_ADI, SIFRE tanımlamaları) aynı kalabilir
    private static final String URL = props.getProperty("db.url");
    private static final String KULLANICI_ADI = props.getProperty("db.user");
    private static final String SIFRE = props.getProperty("db.pass");

    public static Connection baglantiAl() {
        try {
            if (baglanti == null || baglanti.isClosed()) {
                Class.forName("org.postgresql.Driver");
                // Eğer db.properties yüklenemediyse SIFRE null döner, burası kontrol edilebilir
                baglanti = DriverManager.getConnection(URL, KULLANICI_ADI, SIFRE);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("[HATA] PostgreSQL JDBC Driver bulunamadı!");
        } catch (SQLException e) {
            System.out.println("[HATA] Veritabanına bağlanılamadı: " + e.getMessage());
        }

        return baglanti;
    }

    public static void baglantiyiKapat() {
        try {
            if (baglanti != null && !baglanti.isClosed()) {
                baglanti.close();
                System.out.println("[SİSTEM] Veritabanı bağlantısı güvenle kapatıldı.");
            }
        } catch (SQLException e) {
            System.out.println("Bağlantı kapatılırken bir hata oluştu: " + e.getMessage());
        }
    }
}