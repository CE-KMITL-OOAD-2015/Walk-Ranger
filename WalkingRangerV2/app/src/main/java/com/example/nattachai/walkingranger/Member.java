package com.example.nattachai.walkingranger;

/**
 * Created by Windows8.1 on 6/11/2558.
 */
public class Member {

    String memberCode;
    String memberName;
    int stepCount;
    int weeklyStep;
    int monthlyStep;
    String fbCode;
    String groupCode;
    String role;
    int scavengerPoint;
    String groupName;

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public int getWeeklyStep() {
        return weeklyStep;
    }

    public void setWeeklyStep(int weeklyStep) {
        this.weeklyStep = weeklyStep;
    }

    public int getMonthlyStep() {
        return monthlyStep;
    }

    public void setMonthlyStep(int monthlyStep) {
        this.monthlyStep = monthlyStep;
    }

    public String getFbCode() {
        return fbCode;
    }

    public void setFbCode(String fbCode) {
        this.fbCode = fbCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getScavengerPoint() {
        return scavengerPoint;
    }

    public void setScavengerPoint(int scavengerPoint) {
        this.scavengerPoint = scavengerPoint;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public Member() {
    }
}
