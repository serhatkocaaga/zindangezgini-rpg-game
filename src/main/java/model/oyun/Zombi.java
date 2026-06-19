package model.oyun;

public class Zombi implements Dusman {
    private String dusmanIsmi;
    private int tabanHp;
    private int tabanHasar;

    public Zombi(int oyuncuSeviyesi) {
        this.dusmanIsmi = "Zombi (Seviye " + oyuncuSeviyesi + ")";
        this.tabanHp = 50 * oyuncuSeviyesi;
        this.tabanHasar = 10 * oyuncuSeviyesi;
    }

    @Override
    public void saldir() {
        System.out.println(dusmanIsmi + " hırlayarak üzerinize atıldı! (" + tabanHasar + " Hasar)");
    }

    @Override
    public String getDusmanIsmi() { return dusmanIsmi; }

    @Override
    public int getTabanHp() { return tabanHp; }

    @Override
    public int getTabanHasar() { return tabanHasar; }
}