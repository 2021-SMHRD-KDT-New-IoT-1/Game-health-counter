package com.bang.project;

public class RaidVO {

    private String raid_seq; // 레이드 시퀀스
    private String raid_kind; // 레이드 종류
    private String raid_name; // 레이드 이름
    private String raid_cnt; // 레이드 미션 횟수
    private String reg_date; // 레이드 기간 . 일단 3일로 치고. 시작날짜에다가 +3일하면 종료날짜.


    public RaidVO(String raid_seq, String raid_kind, String raid_name, String raid_cnt, String reg_date) {
        super();
        this.raid_seq = raid_seq;
        this.raid_kind = raid_kind;
        this.raid_name = raid_name;
        this.raid_cnt = raid_cnt;
        this.reg_date = reg_date;
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

    public String getReg_date() {
        return reg_date;
    }

}