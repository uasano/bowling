package com.example;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertThat;

/**
 * フレームのテストケース.
 */
public class FrameTest {

    @Test
    public void 一フレーム目の一投目と二投目が0ピンだった時にスコアが0になる() throws Exception {

        Frame sut = createFrame(RollResult.ZERO, RollResult.ZERO);

        assertThat(sut.score(), is(0));
    }

    @Test
    public void 一フレーム目の一投目が1ピン二投目が0ピンだった時にスコアが1になる() throws Exception {

        Frame sut = createFrame(RollResult.ONE, RollResult.ZERO);
        assertThat(sut.score(), is(1));

    }

    @Test(expected = UnsupportedOperationException.class)
    public void 一フレーム目で二投目を投げていない時にスコアを取得するとUnsupportedOperationExceptionが発生すること() throws Exception {
        Frame sut = new Frame();

        sut.firstRoll(RollResult.ONE);
        // 2投目を行なっていない
        // sut.secondRoll(RollResult.ZERO);

        sut.score();
    }

    @Test
    public void 一フレーム目で二投目を投げていない時はスコアが取得できない状態である() throws Exception {
        Frame sut = new Frame();

        sut.firstRoll(RollResult.ONE);
        // 2投目を行なっていない
        // sut.secondRoll(RollResult.ZERO);
        assertThat(sut.isScoreGettable(), is(false));
    }

    @Test
    public void 一フレーム目でストライクを取った時にスコアが取得できない状態である() throws Exception {
        // なぜなら、2フレーム目が決まっていないから
        Frame sut = createStrikeFrame();

        assertThat(sut.isScoreGettable(), is(false));
    }

    @Test
    public void 一フレーム目でスペアをとった時にスコアが取得できない状態である() throws Exception {
        // なぜなら、2フレーム目が決まっていないから
        Frame sut = createFrame(RollResult.ZERO, RollResult.SPARE);

        assertThat(sut.isScoreGettable(), is(false));

    }

    @Test
    public void 一フレーム目でスペアを取って2フレーム目の一投目が0の時に1フレーム目のスコアが10になること() throws Exception {

        Frame sut = createFrame(RollResult.ZERO, RollResult.SPARE);

        Frame secondFrame = new Frame();
        secondFrame.firstRoll(RollResult.ZERO);

        sut.setNextFrame(secondFrame);

        assertThat(sut.isScoreGettable(), is(true));
        assertThat(sut.score(), is(10));

    }

    @Test
    public void 一フレーム目でストライクを取って2フレーム目の投球結果が0_0の時に１フレームめのスコアが10になること() throws Exception {

        Frame sut = createStrikeFrame();

        Frame secondFrame = createFrame(RollResult.ZERO, RollResult.ZERO);

        sut.setNextFrame(secondFrame);

        assertThat(sut.isScoreGettable(), is(true));
        assertThat(sut.score(), is(10));

    }

    @Test
    public void 一フレーム目でストライクを取って2フレーム目の投球結果が0_1の時に１フレームめのスコアが11になること() throws Exception {

        Frame sut = createStrikeFrame();

        Frame secondFrame = createFrame(RollResult.ZERO, RollResult.ONE);

        sut.setNextFrame(secondFrame);

        assertThat(sut.isScoreGettable(), is(true));
        assertThat(sut.score(), is(11));

    }

    @Test
    public void 一フレーム目と二フレーム目がストライクで3フレーム目の一投目が0の時に1フレーム目のスコアが20になること() throws Exception {

        Frame sut = createStrikeFrame();
        sut.setNextFrame(createStrikeFrame());

        sut.getNextFrame().setNextFrame(createFrame(RollResult.ZERO));

        assertThat(sut.isScoreGettable(), is(true));
        assertThat(sut.score(), is(20));
    }

    @Test
    public void 十フレーム目で一投目二投目がストライクの時スコアが取得できない状態である() throws Exception {

        Frame sut = createFrame(RollResult.STRIKE, RollResult.STRIKE);
        sut.setIndex(10);

        assertThat(sut.isScoreGettable(), is(false));
    }

    @Test
    public void 十フレーム目で1投目2投目3投目がストライクの時スコアが30になる() throws Exception {

        Frame sut = createFrame(RollResult.STRIKE, RollResult.STRIKE, RollResult.STRIKE);
        sut.setIndex(10);

        assertThat(sut.isScoreGettable(), is(true));
        assertThat(sut.score(), is(30));

    }

    @Test
    public void 十フレーム目の一投目が0ピン二投目が1ピンの時10フレーム目のスコアが1になる() throws Exception {

        Frame sut = createFrame(RollResult.ZERO, RollResult.ONE);
        sut.setIndex(10);

        assertThat(sut.isScoreGettable(), is(true));
        assertThat(sut.score(), is(1));

    }

    // TODO 10フレーム目でストライク・0・スペア


    @Test
    public void 十フレーム目の一投目がストライクで二投目が1ピｎで3投目がスペアの時スコアが20になる() throws Exception {

        Frame sut = createFrame(RollResult.STRIKE, RollResult.ONE, RollResult.SPARE);
        sut.setIndex(10);

        assertThat(sut.isScoreGettable(), is(true));
        assertThat(sut.score(), is(20));

    }

    private Frame createFrame(RollResult first) {
        Frame frame = new Frame();
        frame.firstRoll(first);

        return frame;
    }

    @Test
    public void 十フレーム目で1投目が0ピン2投目がスペア3投目が1ピンの時に10フレーム目のスコアが11になる() throws Exception {
        Frame sut = createFrame(RollResult.ZERO, RollResult.SPARE, RollResult.ONE);
        sut.setIndex(10);

        assertThat(sut.isScoreGettable(), is(true));
        assertThat(sut.score(), is(11));


    }
    // TODO 10フレーム目でスペアをとって3投目をまだ投げていないときにスコアが取得できない状態になること


    @Test
    public void 十フレーム目で1投目が0ピン二投目が1の時に10フレーム目のスコアが1になる() throws Exception {

        Frame sut = createFrame(RollResult.ZERO, RollResult.ONE);
        sut.setIndex(10);

        assertThat(sut.isScoreGettable(), is(true));
        assertThat(sut.score(), is(1));

    }

    @Test
    public void 例題のゲームのテスト() throws Exception {

        Frame sut = createFrame(RollResult.GUTTER, RollResult.FOUL);

        int totalScore = 0;

        Frame f = chain(sut, createStrikeFrame());
        f = chain(f, createFrame(RollResult.EIGHT, RollResult.SPARE));
        f = chain(f, createStrikeFrame());
        f = chain(f, createStrikeFrame());
        f = chain(f, createStrikeFrame());
        f = chain(f, createFrame(RollResult.FIVE, RollResult.THREE));
        f = chain(f, createFrame(RollResult.EIGHT, RollResult.SPARE));
        f = chain(f, createStrikeFrame());
        f = chain(f, createFrame(RollResult.STRIKE, RollResult.STRIKE, RollResult.STRIKE));



        assertThat(sut.isScoreGettable(), is(true));
        assertThat(total(sut), is(201));
    }

    private int total(Frame frame) {

        if (frame.getNextFrame() == null) {
            return frame.score();
        }
        return frame.score() + total(frame.getNextFrame());
    }

    private Frame chain(Frame current, Frame next) {
        current.setNextFrame(next);
        return next;
    }

    private Frame createStrikeFrame() {
        Frame frame = new Frame();
        frame.firstRoll(RollResult.STRIKE);
        return frame;
    }

    private Frame createFrame(RollResult first, RollResult second) {
        Frame frame = new Frame();
        frame.firstRoll(first);
        frame.secondRoll(second);
        return frame;
    }

    private Frame createFrame(RollResult first, RollResult second, RollResult third) {
        Frame frame = new Frame();
        frame.firstRoll(first);
        frame.secondRoll(second);
        frame.thirdRoll(third);
        return frame;
    }
}
