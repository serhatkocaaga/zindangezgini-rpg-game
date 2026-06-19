package model.oyun;

public class Ork implements Dusman {
    private String dusmanIsmi;
    private int tabanHp;
    private int tabanHasar;

    public Ork(int oyuncuSeviyesi) {
        this.dusmanIsmi = "Ork Savaşçısı (Seviye " + oyuncuSeviyesi + ")";
        this.tabanHp = 150 * oyuncuSeviyesi;
        this.tabanHasar = 30 * oyuncuSeviyesi;
    }

    @Override
    public void saldir() {
        System.out.println(dusmanIsmi + " devasa baltasını savurdu! (" + tabanHasar + " Hasar)");
    }

    @Override
    public String getDusmanIsmi() { return dusmanIsmi; }

    @Override
    public int getTabanHp() { return tabanHp; }

    @Override
    public int getTabanHasar() { return tabanHasar; }
}