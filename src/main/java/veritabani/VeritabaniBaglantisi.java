package veritabani;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class VeritabaniBaglantisi {

    // PostgreSQL sunucu bilgileri
    private static final String URL = "jdbc:postgresql://localhost:5432/zindangezgini_db";
    private static final String KULLANICI_ADI = "postgres";
    private static final String SIFRE = "1231233100serhatX";

    // Arayüzle paylaşacağımız ortak bağlantı nesnesi
    private static Connection baglanti = null;

    // Arayüzün ve testlerin çağrı yaptığı metot
    public static Connection baglantiAl() {
        try {
            // Bağlantı yoksa veya herhangi bir sebeple kapanmışsa yeniden aç
            if (baglanti == null || baglanti.isClosed()) {
                Class.forName("org.postgresql.Driver");
                baglanti = DriverManager.getConnection(URL, KULLANICI_ADI, SIFRE);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("[HATA] PostgreSQL JDBC Driver bulunamadı!");
        } catch (SQLException e) {
            System.out.println("[HATA] Veritabanına bağlanılamadı: " + e.getMessage());
        }

        return baglanti;
    }

    // OYUN ARAYÜZÜNÜN ÇALIŞMASI İÇİN EKSİK OLAN METOT:
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