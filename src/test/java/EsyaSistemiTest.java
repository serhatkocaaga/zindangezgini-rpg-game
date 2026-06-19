import model.esya.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EsyaSistemiTest {

    @Test
    public void testDecoratorIleDinamikEfsunlama() {
        System.out.println(">>> DİNAMİK EŞYA GÜÇLENDİRME TESTİ BAŞLIYOR (DECORATOR - INTERFACE TABANLI) <<<\n");


        Esya kilic = new TemelEsya(1, "Demir Kılıç", "Silah", 20);

        System.out.println("1. AŞAMA: Sandıktan normal bir eşya çıktı.");
        System.out.println("   [ENVANTER] Eşya: " + kilic.getTamAd() + " | Hasar: " + kilic.getToplamGuc());

        assertEquals(20, kilic.getToplamGuc());
        assertEquals("Demir Kılıç", kilic.getTamAd());

        System.out.println("\n2. AŞAMA: Kılıca 'Ateş Efsunu' basılıyor... Artık ATEŞLİ bir kılıcımız var!");
        kilic = new AtesEfsunu(kilic);

        System.out.println("   [ENVANTER] Yeni Eşya: " + kilic.getTamAd() + " | Yeni Hasar: " + kilic.getToplamGuc());

        assertEquals(35, kilic.getToplamGuc());
        assertEquals("Demir Kılıç [+Alevin Öfkesi (Ateş)]", kilic.getTamAd());

        System.out.println("\n3. AŞAMA: Kılıca 'Zehir Efsunu' basılıyor... Artık hem ATEŞLİ hem ZEHİRLİ bir kılıcımız var!");
        kilic = new ZehirEfsunu(kilic);

        System.out.println("   [ENVANTER] Yeni Eşya: " + kilic.getTamAd() + " | Yeni Hasar: " + kilic.getToplamGuc());

        assertEquals(45, kilic.getToplamGuc());
        assertEquals("Demir Kılıç [+Alevin Öfkesi (Ateş)] [+Yılanın Dişi (Zehir)]", kilic.getTamAd());

        System.out.println("\n>>> TEST BAŞARIYLA TAMAMLANDI: Dinamik güçlendirme (Decorator) kusursuz çalışıyor! <<<");
    }
}