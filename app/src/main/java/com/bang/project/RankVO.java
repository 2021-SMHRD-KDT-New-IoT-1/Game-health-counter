package com.bang.project;

public class RankVO {
    private int r_logo;
    private String r_lv;
    private String r_nick;
    private String r_exp;

    public RankVO(){}

    public RankVO(int r_logo, String r_lv, String r_nick, String r_exp) {
        this.r_logo = r_logo;
        this.r_lv = r_lv;
        this.r_nick = r_nick;
        this.r_exp = r_exp;
    }

    public int getR_logo() {
        return r_logo;
    }

    public void setR_logo(int r_logo) {
        this.r_logo = r_logo;
    }

    public String getR_lv() {
        return r_lv;
    }

    public void setR_lv(String r_lv) {
        this.r_lv = r_lv;
    }

    public String getR_nick() {
        return r_nick;
    }

    public void setR_nick(String r_nick) {
        this.r_nick = r_nick;
    }

    public String getR_exp() {
        return r_exp;
    }

    public void setR_exp(String r_exp) {
        this.r_exp = r_exp;
    }
}
