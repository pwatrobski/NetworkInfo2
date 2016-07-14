package com.example.ptw.networkinfo;


/**
 * Created by ptw on 2016-07-14.
 */
public class Bandwidth {
    int bps;
    double bps_db;
    int power;
    String unit;

    public Bandwidth(int bps) {
        this.bps = bps;
        this.bps_db = (double) bps;

        this.power = 0;
        while (this.bps_db >= 1024) {
            this.bps_db = this.bps_db / 1024;
            this.power++;
        }
        switch (this.power) {
            case (0):
                this.unit = "bps";
                break;
            case (1):
                this.unit = "Kbps";
                break;
            case (2):
                this.unit = "Mbps";
                break;
            case (3):
                this.unit = "Gbps";
                break;
            case (4):
                this.unit = "Tbps";
                break;
            default:
                this.unit = "(?)bps";
                break;
        }
    }

    public void setBW(int bps) {
        this.bps = bps;
        this.bps_db = (double) bps;
        this.power = 0;

        while (this.bps_db >= 1024) {
            this.bps_db = this.bps_db / 1024;
            this.power++;
        }

        switch (this.power) {
            case (0):
                this.unit = "bps";
                break;
            case (1):
                this.unit = "Kbps";
                break;
            case (2):
                this.unit = "Mbps";
                break;
            case (3):
                this.unit = "Gbps";
                break;
            case (4):
                this.unit = "Tbps";
                break;
            default:
                this.unit = "(?)bps";
                break;
        }
    }

    public int getBps() {
        return bps;
    }

    public double getBps_db() {
        return bps_db;
    }

    public int getPower() {
        return power;
    }

    public String getUnit() {
        return unit;
    }
}
