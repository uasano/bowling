package com.example;

/**
 * 投球の結果.
 */
public enum RollResult {



    GUTTER(0),
    FOUL(0),
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    STRIKE(10),
    SPARE(null);

    private Integer value;

    private RollResult(Integer value) {
        this.value = value;
    }

    public int toInt() {
        if (this == SPARE) {
            throw new UnsupportedOperationException("スペアの時は数値に変換できません.");
        }
        return value;
    }

}
