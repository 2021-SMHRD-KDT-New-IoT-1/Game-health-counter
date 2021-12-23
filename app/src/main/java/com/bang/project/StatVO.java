package com.bang.project;

public class StatVO {
    int push_cnt;
    int squart_cnt;
    int pull_cnt;
    int push_tcnt;
    int squart_tcnt;
    int pull_tcnt;

    public StatVO(int push_cnt, int squart_cnt, int pull_cnt, int push_tcnt, int squart_tcnt, int pull_tcnt) {
        super();
        this.push_cnt = push_cnt;
        this.squart_cnt = squart_cnt;
        this.pull_cnt = pull_cnt;
        this.push_tcnt = push_tcnt;
        this.squart_tcnt = squart_tcnt;
        this.pull_tcnt = pull_tcnt;
    }

    public int getPush_cnt() {
        return push_cnt;
    }

    public void setPush_cnt(int push_cnt) {
        this.push_cnt = push_cnt;
    }

    public int getSquart_cnt() {
        return squart_cnt;
    }

    public void setSquart_cnt(int squart_cnt) {
        this.squart_cnt = squart_cnt;
    }

    public int getPull_cnt() {
        return pull_cnt;
    }

    public void setPull_cnt(int pull_cnt) {
        this.pull_cnt = pull_cnt;
    }

    public int getPush_tcnt() {
        return push_tcnt;
    }

    public void setPush_tcnt(int push_tcnt) {
        this.push_tcnt = push_tcnt;
    }

    public int getSquart_tcnt() {
        return squart_tcnt;
    }

    public void setSquart_tcnt(int squart_tcnt) {
        this.squart_tcnt = squart_tcnt;
    }

    public int getPull_tcnt() {
        return pull_tcnt;
    }

    public void setPull_tcnt(int pull_tcnt) {
        this.pull_tcnt = pull_tcnt;
    }



}
