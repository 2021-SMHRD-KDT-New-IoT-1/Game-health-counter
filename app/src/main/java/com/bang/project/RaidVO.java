package com.bang.project;

import java.sql.Date;

public class RaidVO {

    private String raid_seq; // 레이드 시퀀스
    private String raid_kind; // 레이드 종류
    private String raid_name; // 레이드 이름
    private String raid_cnt; // 레이드 미션 횟수
    private Date reg_date;
    private String check; // 사용자가 참가중인지 아닌지


    public RaidVO(String raid_seq, String raid_kind, String raid_name, String raid_cnt, Date reg_date, String check) {
        super();
        this.raid_seq = raid_seq;
        this.raid_kind = raid_kind;
        this.raid_name = raid_name;
        this.raid_cnt = raid_cnt;
        this.reg_date = reg_date;
        this.check = check;
    }
    public String getRaid_seq() {
        return raid_seq;
    }

    public String getRaid_kind() {
        return raid_kind;
    }

    public String getRaid_name() {
        return raid_name;
    }

    public String getRaid_cnt() {
        return raid_cnt;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public String getCheck(){return check;}

    public void setCheck(String check){this.check = check;}

}