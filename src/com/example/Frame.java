package com.example;

/**
 * フレームを表すクラス.
 */
public class Frame {

    private RollResult firstRollResult;

    private RollResult secondRollResult;

    private RollResult thirdRollResult;

    private Frame nextFrame;

    private int index = 1;

    public void firstRoll(RollResult result) {
        if (result == null) {
            throw new IllegalArgumentException("引数にnullは設定できません");
        }
        this.firstRollResult = result;
    }

    public void secondRoll(RollResult result) {
        if (result == null) {
            throw new IllegalArgumentException("引数にnullは設定できません");
        }
        this.secondRollResult = result;
    }

    public void thirdRoll(RollResult result) {
        if (result == null) {
            throw new IllegalArgumentException("引数にnullは設定できません");
        }
        this.thirdRollResult = result;
    }

    public int score() {
        if (firstRollResult == RollResult.STRIKE) {
            return add_(this.index, this);
        }
        if (secondRollResult == null) {
            throw new UnsupportedOperationException("2投目を投げていないときは結果が出せません");
        }
        if (secondRollResult == RollResult.SPARE) {
            if (index == 10) {
                return 10 + thirdRollResult.toInt();
            }
            return 10 + nextFrame.firstRollResult.toInt();
        }
        return add();
    }

    private int add() {
        if (index == 10 && thirdRollResult == RollResult.SPARE) {
            return 20;
        }
        return firstRollResult.toInt() + (secondRollResult == null ? 0 : secondRollResult.toInt())
                + (thirdRollResult == null ? 0: thirdRollResult.toInt());
    }

    private static int add_(int currentIndex, Frame frame) {
        if (frame.nextFrame == null) {
            return frame.add();
        }
        if (frame.index > currentIndex + 1) {
            return frame.add();
        }
        if (frame.firstRollResult != RollResult.STRIKE && frame.secondRollResult != RollResult.SPARE) {
            return frame.add();
        }
        return frame.add() + add_(currentIndex ,frame.nextFrame);
    }

    public boolean isScoreGettable() {

        if (firstRollResult == RollResult.STRIKE) {
            if (index == 10) {
                if (secondRollResult == null || thirdRollResult == null) {
                    return false;
                }

            } else {
                if (nextFrame == null) {
                    return false;
                }
                if (nextFrame.firstRollResult != RollResult.STRIKE) {
                    if (nextFrame.firstRollResult == null || nextFrame.secondRollResult == null) {
                        return false;
                    }
                }
            }
        } else {
            if (secondRollResult == null) {
                return false;
            }
        }
        if (secondRollResult == RollResult.SPARE) {
            if (index == 10) {
                if (thirdRollResult == null) {
                    return false;
                }
            } else {
                if (nextFrame == null) {
                    return false;
                }
                if (nextFrame.firstRollResult == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public Frame getNextFrame() {
        return nextFrame;
    }

    public void setNextFrame(Frame nextFrame) {
        this.nextFrame = nextFrame;
        this.nextFrame.index = this.index + 1;
    }

    void setIndex(int index) {
        this.index = index;
    }
}
