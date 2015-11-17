package com.example.nattachai.walkingranger;

import java.util.ArrayList;

/**
 * Created by Windows8.1 on 6/11/2558.
 */
public class Group {
    String groupCode;
    String groupName;
    int avgSteps;
    int avgWeeklySteps;
    int avgMonthlySteps;
    Member leader = new Member();
    ArrayList<Member> memberlist = new ArrayList<>();

    public Group() {
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getAvgSteps() {
        return avgSteps;
    }

    public void setAvgSteps(int avgSteps) {
        this.avgSteps = avgSteps;
    }

    public int getAvgWeeklySteps() {
        return avgWeeklySteps;
    }

    public void setAvgWeeklySteps(int avgWeeklySteps) {
        this.avgWeeklySteps = avgWeeklySteps;
    }

    public int getAvgMonthlySteps() {
        return avgMonthlySteps;
    }

    public void setAvgMonthlySteps(int avgMonthlySteps) {
        this.avgMonthlySteps = avgMonthlySteps;
    }

    public Member getLeader() {
        return leader;
    }

    public void setLeader(Member leader) {
        this.leader = leader;
    }

    public ArrayList<Member> getMemberlist() {
        return memberlist;
    }

    public void setMemberlist(ArrayList<Member> memberList) {
        this.memberlist = memberList;
    }
}
