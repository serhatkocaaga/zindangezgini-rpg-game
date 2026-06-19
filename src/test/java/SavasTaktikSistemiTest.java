import model.karakter.Savasci;
import model.karakter.Buyucu;
import model.strateji.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavasTaktikSistemiTest {

    @Test
    public void testStrategyVePolymorphismIleDinamikHasar() {
        System.out.println(">>> STRATEGY KALIBI VE POLYMORPHISM: TAKTİKSEL SAVAŞ TESTİ BAŞLIYOR <<<\n");


        System.out.println("--- SAVAŞÇI TEST EDİLİYOR (Fiziksel Güç: 20) ---");
        Savasci savasci = new Savasci("Serhat", 20);


        savasci.setStrateji(new HizliSaldiri());
        int savasciHizliHasar = savasci.taktikselSaldiriYap();
        assertEquals(20, savasciHizliHasar, "Savaşçı hızlı saldırıda 20 hasar vermelidir.");


        savasci.setStrateji(new AgirSaldiri());
        int savasciAgirHasar = savasci.taktikselSaldiriYap();
        assertEquals(30, savasciAgirHasar, "Savaşçı ağır saldırıda 30 hasar (1.5x) vermelidir.");



        System.out.println("\n--- BÜYÜCÜ TEST EDİLİYOR (Zeka Puanı: 30) ---");

        Buyucu buyucu = new Buyucu("Mete", 30, 100);


        buyucu.setStrateji(new SavunmaPozisyonu());
        int buyucuSavunmaHasari = buyucu.taktikselSaldiriYap();
        assertEquals(6, buyucuSavunmaHasari, "Büyücü savunma pozisyonunda 6 hasar (0.2x) vermelidir.");


        buyucu.setStrateji(new AgirSaldiri());
        int buyucuAgirHasar = buyucu.taktikselSaldiriYap();
        assertEquals(45, buyucuAgirHasar, "Büyücü ağır saldırıda 45 hasar (1.5x) vermelidir.");

        System.out.println("\n>>> TEST BAŞARIYLA TAMAMLANDI <<<");

    }
}