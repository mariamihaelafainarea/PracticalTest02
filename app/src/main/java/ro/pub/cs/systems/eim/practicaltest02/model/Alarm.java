package ro.pub.cs.systems.eim.practicaltest02.model;

public class Alarm {
    private int ora;
    private int minut;
    private String ip_client;


    public int getOra() {
        return ora;
    }

    public void setOra(int ora) {
        this.ora = ora;
    }

    public int getMinut() {
        return minut;
    }

    public void setMinut(int minut) {
        this.minut = minut;
    }

    public String getIp_client() {
        return ip_client;
    }

    public void setIp_client(String ip_client) {
        this.ip_client = ip_client;
    }
}
