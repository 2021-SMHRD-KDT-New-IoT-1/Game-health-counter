package com.bang.project;

public class RankVO {
    private String m_nickname;
    private int c_level;
    private int total_exp;

    public RankVO(String m_nickname, int c_level, int total_exp) {
        super();
        this.m_nickname = m_nickname;
        this.c_level = c_level;
        this.total_exp = total_exp;
    }

    public String getM_nickname() {
        return m_nickname;
    }

    public void setM_nickname(String m_nickname) {
        this.m_nickname = m_nickname;
    }

    public int getC_level() {
        return c_level;
    }

    public void setC_level(int c_level) {
        this.c_level = c_level;
    }

    public int getTotal_exp() {
        return total_exp;
    }

    public void setTotal_exp(int total_exp) {
        this.total_exp = total_exp;
    }
}
