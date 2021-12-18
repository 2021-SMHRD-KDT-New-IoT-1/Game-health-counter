package com.bang.project;


import java.util.Date;

public class ChatVO {
    String q_name;
    int q_cnt;
    String q_check;
    int q_exp;
    Date reg_date;

    public ChatVO(String q_name, int q_cnt, String q_check, int q_exp, Date reg_date) {
        this.q_name = q_name;
        this.q_cnt = q_cnt;
        this.q_check = q_check;
        this.q_exp = q_exp;
        this.reg_date = reg_date;
    }

    public String getQ_name() {
        return q_name;
    }

    public void setQ_name(String q_name) {
        this.q_name = q_name;
    }

    public int getQ_cnt() {
        return q_cnt;
    }

    public void setQ_cnt(int q_cnt) {
        this.q_cnt = q_cnt;
    }

    public String getQ_check() {
        return q_check;
    }

    public void setQ_check(String q_check) {
        this.q_check = q_check;
    }

    public int getQ_exp() {
        return q_exp;
    }

    public void setQ_exp(int q_exp) {
        this.q_exp = q_exp;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    //    private String q_date;
//    private int q_check1;
//    private int q_check2;
//    private int q_check3;
//    private String q_text1;
//    private String q_text2;
//    private String q_text3;
//    private String q_cal1;
//    private String q_cal2;
//    private String q_cal3;
//
//    public ChatVO(String q_date, String q_text1, int q_check1, String q_cal1, String q_text2, int q_check2, String q_cal2, String q_text3, int q_check3, String q_cal3) {
//        this.q_date = q_date;
//        this.q_check1 = q_check1;
//        this.q_check2 = q_check2;
//        this.q_check3 = q_check3;
//        this.q_text1 = q_text1;
//        this.q_text2 = q_text2;
//        this.q_text3 = q_text3;
//        this.q_cal1 = q_cal1;
//        this.q_cal2 = q_cal2;
//        this.q_cal3 = q_cal3;
//    }
//
//    public String getQ_date() {
//        return q_date;
//    }
//
//    public void setQ_date(String q_date) {
//        this.q_date = q_date;
//    }
//
//    public int getQ_check1() {
//        return q_check1;
//    }
//
//    public void setQ_check1(int q_check1) {
//        this.q_check1 = q_check1;
//    }
//
//    public int getQ_check2() {
//        return q_check2;
//    }
//
//    public void setQ_check2(int q_check2) {
//        this.q_check2 = q_check2;
//    }
//
//    public int getQ_check3() {
//        return q_check3;
//    }
//
//    public void setQ_check3(int q_check3) {
//        this.q_check3 = q_check3;
//    }
//
//    public String getQ_text1() {
//        return q_text1;
//    }
//
//    public void setQ_text1(String q_text1) {
//        this.q_text1 = q_text1;
//    }
//
//    public String getQ_text2() {
//        return q_text2;
//    }
//
//    public void setQ_text2(String q_text2) {
//        this.q_text2 = q_text2;
//    }
//
//    public String getQ_text3() {
//        return q_text3;
//    }
//
//    public void setQ_text3(String q_text3) {
//        this.q_text3 = q_text3;
//    }
//
//    public String getQ_cal1() {
//        return q_cal1;
//    }
//
//    public void setQ_cal1(String q_cal1) {
//        this.q_cal1 = q_cal1;
//    }
//
//    public String getQ_cal2() {
//        return q_cal2;
//    }
//
//    public void setQ_cal2(String q_cal2) {
//        this.q_cal2 = q_cal2;
//    }
//
//    public String getQ_cal3() {
//        return q_cal3;
//    }
//
//    public void setQ_cal3(String q_cal3) {
//        this.q_cal3 = q_cal3;
//    }
}
