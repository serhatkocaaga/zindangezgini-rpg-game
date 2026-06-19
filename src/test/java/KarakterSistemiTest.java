import model.karakter.Buyucu;
import model.karakter.Savasci;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KarakterSistemiTest {

    @Test
    public void testSeviyeHesaplama() {
        System.out.println("TEST: Derived Attribute (Seviye) kontrol ediliyor...");

        Savasci savasci = new Savasci();

        savasci.setToplamXpPuani(350);

        assertEquals(4, savasci.getSeviye(), "Seviye hesaplama mantığında hata var!");
    }

    @Test
    public void testTamIsimBirlestirme() {
        System.out.println("TEST: Composite Attribute (Tam_Isim) kontrol ediliyor...");

        Buyucu buyucu = new Buyucu();
        buyucu.setUnvan("Büyücü");
        buyucu.setIsim("Serhat");


        assertEquals("Büyücü Serhat", buyucu.getTamIsim(), "İsim ve unvan doğru birleşmiyor!");
    }
}