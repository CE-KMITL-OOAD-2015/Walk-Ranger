package com.example.nattachai.walkingranger;

/**
 * Created by Vincent on 19/11/2558.
 */
public class Reward {
    private String rewardID;
    private String rewardName;
    private String description;
    private int point;
    private String rewardCode;

    public Reward() {
    }

    public String getRewardID() {

        return rewardID;
    }

    public void setRewardID(String rewardID) {
        this.rewardID = rewardID;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getRewardCode() {
        return rewardCode;
    }

    public void setRewardCode(String rewardCode) {
        this.rewardCode = rewardCode;
    }
}
