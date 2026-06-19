package uygulama;

import fabrika.DusmanFabrikasi;
import fabrika.ZindanDusmanFabrikasi;
import model.oyun.Dusman;
import model.karakter.Karakter;
import model.karakter.Savasci;
import model.karakter.Buyucu;
import model.esya.Esya;
import model.esya.TemelEsya;
import model.esya.AtesEfsunu;
import model.esya.ZehirEfsunu;
import model.strateji.HizliSaldiri;
import model.strateji.AgirSaldiri;
import model.strateji.SavunmaPozisyonu;
import veritabani.VeritabaniBaglantisi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class OyunArayuzu {
    private Scanner scanner;
    private Karakter oyuncu;
    private Esya oyuncuSilahi;
    private DusmanFabrikasi fabrika;
    private int aktifKullaniciId = -1;

    private int oyuncuGuncelHp = 100;

    public OyunArayuzu() {
        this.scanner = new Scanner(System.in);
        this.fabrika = new ZindanDusmanFabrikasi();
    }

    public void baslat() {
        System.out.println("=================================================");
        System.out.println("⚔️  ZİNDAN GEZGİNİ'NE HOŞ GELDİNİZ ⚔️");
        System.out.println("=================================================");

        Connection baglanti = VeritabaniBaglantisi.baglantiAl();
        if (baglanti != null) {
            System.out.println("[SİSTEM] PostgreSQL Veritabanı bağlantısı başarılı.\n");
        }

        kimlikDogrulamaMenusu();

        if (oyuncu == null) {
            System.out.println("[SİSTEM] Karakter yüklenemedi. Oyun kapatılıyor.");
            return;
        }

        boolean oyunaDevam = true;
        while (oyunaDevam) {
            System.out.println("\n--- ANA MENÜ ---");
            System.out.println("1. Zindana Gir (Savaş)");
            System.out.println("2. Demirciye Git (Eşya Geliştir)");
            System.out.println("3. Şifacıya Git (Can Doldur - 25 Altın)");
            System.out.println("4. Karakter Durumu");
            System.out.println("5. Oyundan Çık");
            System.out.print("Seçiminiz: ");

            String secim = scanner.nextLine();

            switch (secim) {
                case "1":
                    if (oyuncuGuncelHp <= 0) {
                        System.out.println("❌ Ölüler zindana giremez! Önce Şifacıya gidip dirilmen lazım.");
                    } else {
                        savasSistemi();
                    }
                    break;
                case "2":
                    demirciSistemi();
                    break;
                case "3":
                    sifaciSistemi();
                    break;
                case "4":
                    karakterDurumuGoster();
                    break;
                case "5":
                    System.out.println("Oyundan çıkılıyor... Başarılar Gezgin!");
                    oyunaDevam = false;
                    break;
                default:
                    System.out.println("Geçersiz seçim! Tekrar deneyin.");
            }
        }
        VeritabaniBaglantisi.baglantiyiKapat();
    }

    private void kimlikDogrulamaMenusu() {
        while (aktifKullaniciId == -1) {
            System.out.println("--- GİRİŞ KAPISI ---");
            System.out.println("1. Giriş Yap");
            System.out.println("2. Yeni Kayıt Ol");
            System.out.println("3. Kayıtlı Kullanıcıyı/Hesabı Sil");
            System.out.print("Seçiminiz: ");
            String secim = scanner.nextLine();

            if (secim.equals("1")) {
                kullaniciGirisYap();
            } else if (secim.equals("2")) {
                kullaniciKayitOl();
            } else if (secim.equals("3")) {
                kullaniciSil();
            } else {
                System.out.println("Geçersiz işlem.");
            }
        }
    }

    private void kullaniciGirisYap() {
        System.out.print("Kullanıcı Adı: ");
        String kAdi = scanner.nextLine();
        System.out.print("Şifre: ");
        String sifre = scanner.nextLine();

        Connection conn = VeritabaniBaglantisi.baglantiAl();
        String sql = "SELECT k.kullanici_id, ka.isim, ka.unvan, ka.toplam_xp, ka.guncel_hp FROM kullanici k " +
                "JOIN karakter ka ON k.kullanici_id = ka.kullanici_id WHERE k.kullanici_adi = ? AND k.sifre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kAdi);
            stmt.setString(2, sifre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.aktifKullaniciId = rs.getInt("kullanici_id");
                String isim = rs.getString("isim");
                String unvan = rs.getString("unvan");
                int xp = rs.getInt("toplam_xp");

                this.oyuncuGuncelHp = rs.getInt("guncel_hp");

                if ("Savaşçı".equals(unvan)) {
                    this.oyuncu = new Savasci(isim, 25);
                    this.oyuncuSilahi = new TemelEsya(1, "Demir Kılıç", "Silah", 20);
                } else {
                    this.oyuncu = new Buyucu(isim, 30, 100);
                    this.oyuncuSilahi = new TemelEsya(2, "Büyücü Asası", "Silah", 15);
                }

                this.oyuncu.setToplamXpPuani(xp);
                System.out.println("\n✅ Giriş başarılı! Hoş geldin " + unvan + " " + oyuncu.getIsim());
                System.out.println("❤️ Güncel Canın: " + this.oyuncuGuncelHp + " | Güncel Seviyen: " + oyuncu.getSeviye());
            } else {
                System.out.println("❌ Hatalı kullanıcı adı veya şifre!");
            }
        } catch (SQLException e) {
            System.out.println("Giriş yapılırken SQL hatası oluştu: " + e.getMessage());
        }
    }

    private void kullaniciKayitOl() {
        System.out.print("Yeni Kullanıcı Adı: ");
        String kAdi = scanner.nextLine();
        System.out.print("Yeni Şifre: ");
        String sifre = scanner.nextLine();
        System.out.print("Karakterinizin Adı: ");
        String karakterAdi = scanner.nextLine();

        System.out.println("Karakter Sınıfı Seçin:");
        System.out.println("1. Savaşçı (Fiziksel Güç Odaklı)");
        System.out.println("2. Büyücü (Zeka ve Mana Odaklı)");
        System.out.print("Seçiminiz: ");
        String sinifSecim = scanner.nextLine();

        String unvan = sinifSecim.equals("2") ? "Büyücü" : "Savaşçı";

        Connection conn = VeritabaniBaglantisi.baglantiAl();
        try {
            String sqlKullanici = "INSERT INTO kullanici (kullanici_adi, sifre, hesap_yasi) VALUES (?, ?, 21) RETURNING kullanici_id";
            PreparedStatement stmtK = conn.prepareStatement(sqlKullanici);
            stmtK.setString(1, kAdi);
            stmtK.setString(2, sifre);
            ResultSet rsK = stmtK.executeQuery();

            if (rsK.next()) {
                int yeniUid = rsK.getInt("kullanici_id");

                String sqlKarakter = "INSERT INTO karakter (kullanici_id, isim, unvan, guncel_hp, altin_miktari, toplam_xp) VALUES (?, ?, ?, 100, 100, 0)";
                PreparedStatement stmtKa = conn.prepareStatement(sqlKarakter);
                stmtKa.setInt(1, yeniUid);
                stmtKa.setString(2, karakterAdi);
                stmtKa.setString(3, unvan);
                stmtKa.executeUpdate();

                System.out.println("✅ Kayıt başarıyla tamamlandı! Başlangıç parası: 100 Altın. Şimdi giriş yapabilirsiniz.");
            }
        } catch (SQLException e) {
            System.out.println("Kayıt esnasında hata: " + e.getMessage());
        }
    }

    private void kullaniciSil() {
        System.out.println("\n--- HESAP SİLME PANELİ ---");
        System.out.print("Silinecek Kullanıcı Adı: ");
        String kAdi = scanner.nextLine();
        System.out.print("Şifre: ");
        String sifre = scanner.nextLine();

        System.out.print("⚠️ DİKKAT: Bu hesaba ait karakter, envanter ve tüm veriler silinecektir! Onaylıyor musunuz? (E/H): ");
        String onay = scanner.nextLine();

        if (!onay.equalsIgnoreCase("E")) {
            System.out.println("İşlem iptal edildi.");
            return;
        }

        Connection conn = VeritabaniBaglantisi.baglantiAl();
        String sqlSil = "DELETE FROM kullanici WHERE kullanici_adi = ? AND sifre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlSil)) {
            stmt.setString(1, kAdi);
            stmt.setString(2, sifre);
            int etkilenenSatir = stmt.executeUpdate();

            if (etkilenenSatir > 0) {
                System.out.println("✅ Hesap ve ilişkili tüm zindan verileri veritabanından başarıyla kazındı!");
            } else {
                System.out.println("❌ Hatalı kullanıcı adı veya şifre! Silme işlemi başarısız.");
            }
        } catch (SQLException e) {
            System.out.println("Hesap silinirken veritabanı hatası oluştu: " + e.getMessage());
        }
    }

    private void savasSistemi() {
        System.out.println("\n>>> Zindanın derinliklerine iniyorsun... <<<");

        String[] dusmanHavuzu = {"ork", "zombi"};
        String rastgeleDusmanTipi = dusmanHavuzu[(int) (Math.random() * dusmanHavuzu.length)];

        Dusman dusman = fabrika.dusmanOlustur(rastgeleDusmanTipi, oyuncu.getSeviye());
        System.out.println("Karşına tehlikeli bir " + dusman.getDusmanIsmi() + " çıktı! (HP: " + dusman.getTabanHp() + ")");

        int dusmanHp = dusman.getTabanHp();
        int raund = 1;

        while (dusmanHp > 0 && this.oyuncuGuncelHp > 0) {
            System.out.println("\n⚔️ --- RAUND " + raund + " --- ⚔️");


            String ekstraBar = "";
            if (oyuncu instanceof Buyucu) {
                ekstraBar = " | 💧 Mana: " + ((Buyucu) oyuncu).getManaPuani();
            } else if (oyuncu instanceof Savasci) {
                ekstraBar = " | 💢 Öfke: " + ((Savasci) oyuncu).getOfkeBari();
            }

            System.out.println("❤️ " + oyuncu.getIsim() + " HP: " + this.oyuncuGuncelHp + ekstraBar + " | 💀 " + dusman.getDusmanIsmi() + " HP: " + dusmanHp);
            System.out.println("Hangi taktiği kullanmak istersin?");
            System.out.println("1. Hızlı Saldırı (Normal Hasar - Garanti İsabet)");

            if (oyuncu instanceof Buyucu) {
                System.out.println("2. Ağır Saldırı (Büyü Patlaması) [Maliyet: 25 Mana - %30 Iskalama Riski]");
                System.out.println("3. Savunma Pozisyonu (Hasarı Azaltır ve +20 Mana Yeniler)");
            } else {
                System.out.println("2. Ağır Saldırı (1.5x Hasar) [Öfke 30+ ise Iskalama İptal Olur!]");
                System.out.println("3. Savunma Pozisyonu (Düşmandan az hasar alırsın)");
            }

            System.out.print("Taktik Seç: ");

            String taktikSecim = scanner.nextLine();
            boolean iskaGecti = false;
            boolean savunmaModu = false;


            if (oyuncu instanceof Buyucu && taktikSecim.equals("2")) {
                Buyucu buyucu = (Buyucu) oyuncu;
                if (buyucu.getManaPuani() >= 25) {
                    buyucu.setManaPuani(buyucu.getManaPuani() - 25);
                    System.out.println("✨ [BÜYÜ] 25 Mana harcayarak devasa bir enerji topladın!");
                } else {
                    System.out.println("💧 Yeterli Manan yok! Asanla zayıf bir fiziksel vuruş yapmak zorunda kaldın.");
                    taktikSecim = "1";
                }
            } else if (oyuncu instanceof Buyucu && taktikSecim.equals("3")) {
                Buyucu buyucu = (Buyucu) oyuncu;
                buyucu.setManaPuani(Math.min(buyucu.getManaPuani() + 20, 100));
                System.out.println("🧘 [ODAKLANMA] Savunmaya geçtin ve 20 Mana yeniledin.");
            }


            if (oyuncu instanceof Savasci && taktikSecim.equals("2")) {
                Savasci savasci = (Savasci) oyuncu;
                if (savasci.getOfkeBari() >= 30) {
                    savasci.setOfkeBari(savasci.getOfkeBari() - 30);
                    System.out.println("🔥 [ÖFKE PATLAMASI] 30 Öfke puanı serbest bırakıldı! Bu saldırı hedefini kesinlikle bulacak!");
                    iskaGecti = false;
                } else {
                    if (Math.random() < 0.30) {
                        iskaGecti = true;
                    }
                }
            } else if (!(oyuncu instanceof Savasci) && taktikSecim.equals("2")) {

                if (Math.random() < 0.30) {
                    iskaGecti = true;
                }
            }

            switch (taktikSecim) {
                case "1":
                    oyuncu.setStrateji(new HizliSaldiri());
                    break;
                case "2":
                    oyuncu.setStrateji(new AgirSaldiri());
                    break;
                case "3":
                    oyuncu.setStrateji(new SavunmaPozisyonu());
                    savunmaModu = true;
                    break;
                default:
                    System.out.println("Geçersiz taktik! Hızlı Saldırıya geçildi.");
                    oyuncu.setStrateji(new HizliSaldiri());
            }

            if (iskaGecti) {
                System.out.println("❌ [KRİTİK ISKA] Saldırın boşa gitti! Hedefi tutturamadın.");
            } else {
                int karakterVurus = oyuncu.taktikselSaldiriYap();
                int toplamVurulan = karakterVurus + oyuncuSilahi.getToplamGuc();
                dusmanHp -= toplamVurulan;
                System.out.println("💥 " + dusman.getDusmanIsmi() + " adlı yaratığa " + toplamVurulan + " hasar verdin!");
            }

            if (dusmanHp <= 0) {
                break;
            }

            int dusmanBaseHasar = (int) (Math.random() * 8) + 6;

            boolean dusmanKritik = Math.random() < 0.15;
            if (dusmanKritik) {
                dusmanBaseHasar = (int) (dusmanBaseHasar * 1.5);
            }

            if (savunmaModu) {
                dusmanBaseHasar = dusmanBaseHasar / 3;
                System.out.println("🛡️ Savunma pozisyonunda olduğun için düşman hasarını blokladın!");
            }

            this.oyuncuGuncelHp -= dusmanBaseHasar;

            if (dusmanKritik && !savunmaModu) {
                System.out.println("🩸 [KRİTİK DARBE] " + dusman.getDusmanIsmi() + " zayıf noktana vurdu! -> " + dusmanBaseHasar + " Hasar Aldın.");
            } else {
                System.out.println("👹 " + dusman.getDusmanIsmi() + " sana saldırdı! -> " + dusmanBaseHasar + " Hasar Aldın.");
            }


            if (oyuncu instanceof Savasci) {
                Savasci savasci = (Savasci) oyuncu;
                savasci.setOfkeBari(Math.min(savasci.getOfkeBari() + 15, 100)); // Max 100 öfke
                System.out.println("💢 " + oyuncu.getIsim() + " acı hissettikçe öfkeleniyor! (+15 Öfke)");
            }

            raund++;
        }

        Connection conn = VeritabaniBaglantisi.baglantiAl();
        String sqlCanKaydet = "UPDATE karakter SET guncel_hp = ? WHERE kullanici_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlCanKaydet)) {
            stmt.setInt(1, Math.max(this.oyuncuGuncelHp, 0));
            stmt.setInt(2, aktifKullaniciId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Can kaydedilirken hata oluştu: " + e.getMessage());
        }

        if (this.oyuncuGuncelHp > 0) {
            int kazanilanAltin = (int) (Math.random() * 30) + 20;

            System.out.println("\n🎉 Tebrikler! Savaşı kazandın.");
            System.out.println("💰 Zindandan +" + kazanilanAltin + " Altın topladın!");

            oyuncu.dusmaniYen(dusman);

            String sqlOdul = "UPDATE karakter SET altin_miktari = altin_miktari + ?, toplam_xp = ? WHERE kullanici_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlOdul)) {
                stmt.setInt(1, kazanilanAltin);
                stmt.setInt(2, oyuncu.getToplamXpPuani());
                stmt.setInt(3, aktifKullaniciId);
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Altın ödülü ve XP kaydedilirken SQL hatası: " + e.getMessage());
            }
        } else {
            System.out.println("\n💀 [YOU DIED] Zindanda can verdin. Köylüler seni baygın halde bulup kasabaya getirdi.");
        }
    }

    private void sifaciSistemi() {
        Connection conn = VeritabaniBaglantisi.baglantiAl();
        int mevcutAltin = 0;

        String sqlPara = "SELECT altin_miktari FROM karakter WHERE kullanici_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlPara)) {
            stmt.setInt(1, aktifKullaniciId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                mevcutAltin = rs.getInt("altin_miktari");
            }
        } catch (SQLException e) {
            System.out.println("Para sorgulanırken hata: " + e.getMessage());
        }

        System.out.println("\n>>> Köy Şifacısına Hoş Geldin! <<<");
        System.out.println("❤️ Mevcut Canın: " + this.oyuncuGuncelHp + "/100");
        System.out.println("💰 Mevcut Altınınız: " + mevcutAltin + " Altın");

        if (this.oyuncuGuncelHp >= 100) {
            System.out.println("Şifacı: \"Sapağlamsın be evladım, git biraz daha ork tokatla öyle gel.\"");
            return;
        }

        System.out.println("Şifacı: \"25 Altın karşılığında tüm yaralarını sarabilirim.\"");
        System.out.print("Kabul ediyor musun? (E/H): ");
        String cevap = scanner.nextLine();

        if (cevap.equalsIgnoreCase("E")) {
            if (mevcutAltin >= 25) {
                this.oyuncuGuncelHp = 100;


                if (oyuncu instanceof Buyucu) ((Buyucu) oyuncu).setManaPuani(100);
                if (oyuncu instanceof Savasci) ((Savasci) oyuncu).setOfkeBari(0);

                String sqlIyilestir = "UPDATE karakter SET altin_miktari = altin_miktari - 25, guncel_hp = 100 WHERE kullanici_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sqlIyilestir)) {
                    stmt.setInt(1, aktifKullaniciId);
                    stmt.executeUpdate();
                    System.out.println("✨ [İYİLEŞTİN] Şifacı iksirini içtin. Canın tamamen fullendi!");
                } catch (SQLException e) {
                    System.out.println("Şifa işlemi sırasında veritabanı hatası: " + e.getMessage());
                }
            } else {
                System.out.println("❌ Şifacı: \"Paran yoksa şifa da yok! Otacı değilim ben, ticaret yapıyorum.\"");
            }
        } else {
            System.out.println("İşlem iptal edildi.");
        }
    }

    private void demirciSistemi() {
        Connection conn = VeritabaniBaglantisi.baglantiAl();
        int mevcutAltin = 0;

        String sqlPara = "SELECT altin_miktari FROM karakter WHERE kullanici_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlPara)) {
            stmt.setInt(1, aktifKullaniciId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                mevcutAltin = rs.getInt("altin_miktari");
            }
        } catch (SQLException e) {
            System.out.println("Para sorgulanırken hata: " + e.getMessage());
        }

        System.out.println("\n>>> Zindan Demircisine Hoş Geldin! <<<");
        System.out.println("💰 Mevcut Altınınız: " + mevcutAltin + " Altın");
        System.out.println("Mevcut Silahın: " + oyuncuSilahi.getTamAd() + " | Güç: " + oyuncuSilahi.getToplamGuc());
        System.out.println("Silahına hangi efsunu basmak istersin?");
        System.out.println("1. Alevin Öfkesi (Ateş Efsunu +15 Güç) [Maliyet: 50 Altın]");
        System.out.println("2. Yılanın Dişi (Zehir Efsunu +10 Güç) [Maliyet: 30 Altın]");
        System.out.println("3. Vazgeç (Geri Dön)");
        System.out.print("Seçiminiz: ");

        String efsunSecim = scanner.nextLine();
        int maliyet = 0;
        Esya geciciSilah = oyuncuSilahi;

        if (efsunSecim.equals("1")) {
            maliyet = 50;
            geciciSilah = new AtesEfsunu(oyuncuSilahi);
        } else if (efsunSecim.equals("2")) {
            maliyet = 30;
            geciciSilah = new ZehirEfsunu(oyuncuSilahi);
        } else {
            System.out.println("İşlem iptal edildi.");
            return;
        }

        if (mevcutAltin >= maliyet) {
            String sqlParaGuncelle = "UPDATE karakter SET altin_miktari = altin_miktari - ? WHERE kullanici_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlParaGuncelle)) {
                stmt.setInt(1, maliyet);
                stmt.setInt(2, aktifKullaniciId);
                stmt.executeUpdate();

                oyuncuSilahi = geciciSilah;

                if (efsunSecim.equals("1")) {
                    System.out.println("🔥 Demirci cüzdanından 50 altın aldı ve silahını ateşte dövdü!");
                } else {
                    System.out.println("🐍 Demirci cüzdanından 30 altın aldı ve silahına zehir sürdü!");
                }
            } catch (SQLException e) {
                System.out.println("Para düşülürken hata oluştu: " + e.getMessage());
            }
        } else {
            System.out.println("❌ [YETERSİZ BAKİYE] Demirci: \"Fakirleri zindana almıyoruz aslanım, git önce altın kas!\"");
        }

        System.out.println("Yeni Silahın: " + oyuncuSilahi.getTamAd() + " | Yeni Güç: " + oyuncuSilahi.getToplamGuc());
    }

    private void karakterDurumuGoster() {
        int mevcutAltin = 0;
        Connection conn = VeritabaniBaglantisi.baglantiAl();
        String sqlPara = "SELECT altin_miktari FROM karakter WHERE kullanici_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlPara)) {
            stmt.setInt(1, aktifKullaniciId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                mevcutAltin = rs.getInt("altin_miktari");
            }
        } catch (SQLException e) {
            mevcutAltin = 0;
        }

        System.out.println("\n--- KARAKTER DURUMU ---");
        System.out.println("İsim: " + oyuncu.getTamIsim());
        System.out.println("Seviye: " + oyuncu.getSeviye() + " (XP: " + oyuncu.getToplamXpPuani() + ")");
        System.out.println("❤️ Güncel Can: " + this.oyuncuGuncelHp + "/100");


        if (oyuncu instanceof Buyucu) {
            System.out.println("💧 Mana Puanı: " + ((Buyucu) oyuncu).getManaPuani() + "/100");
        } else if (oyuncu instanceof Savasci) {
            System.out.println("💢 Öfke Barı: " + ((Savasci) oyuncu).getOfkeBari() + "/100");
        }

        System.out.println("💰 Altın Miktarı: " + mevcutAltin + " Altın");
        System.out.println("Sınıf Tipi Taban Hasarı: " + oyuncu.getTabanHasar());
        System.out.println("Kuşanılan Silah: " + oyuncuSilahi.getTamAd() + " (Güç: " + oyuncuSilahi.getToplamGuc() + ")");
        System.out.println("-----------------------");
    }
}