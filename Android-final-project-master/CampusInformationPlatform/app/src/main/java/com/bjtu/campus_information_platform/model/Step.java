package com.bjtu.campus_information_platform.model;

public class Step {
    private String nickname;
    private int steps;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "Step{" +
                "nickname='" + nickname + '\'' +
                ", steps=" + steps +
                '}';
    }
}
