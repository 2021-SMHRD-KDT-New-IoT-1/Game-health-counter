package com.bang.project;


import java.util.Date;

public class QuestVO {
    String q_name;
    int q_cnt;
    String q_check;
    int q_exp;
    Date reg_date;

    public QuestVO(String q_name, int q_cnt, String q_check, int q_exp, Date reg_date) {
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
}
