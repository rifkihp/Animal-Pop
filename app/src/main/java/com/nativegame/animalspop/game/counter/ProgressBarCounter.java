package com.nativegame.animalspop.game.counter;

import android.graphics.drawable.ClipDrawable;
import android.widget.ImageView;

import com.nativegame.animalspop.R;
import com.nativegame.animalspop.game.MyGameEvent;
import com.nativegame.animalspop.level.MyLevel;
import com.nativegame.animalspop.sound.MySoundEvent;
import com.nativegame.nattyengine.Game;
import com.nativegame.nattyengine.event.GameEvent;
import com.nativegame.nattyengine.entity.UIGameObject;

/**
 * Created by Oscar Liang on 2022/09/18
 */

public class ProgressBarCounter extends UIGameObject {

    private static final int POINTS_GAINED_PER_BUBBLE = 10;
    private static final int STAR1_THRESHOLD = 2800;
    private static final int STAR2_THRESHOLD = 7200;
    private static final int STAR3_THRESHOLD = 10000;

    private final MyLevel mLevel;
    private final ClipDrawable mProgressBar;
    private final ImageView mStar1;
    private final ImageView mStar2;
    private final ImageView mStar3;
    private final float mPointFactor;

    private int mPoints;
    private int mCurrentStar;

    public ProgressBarCounter(Game game) {
        super(game);
        mLevel = (MyLevel) game.getLevel();
        // Init the progress bar
        ImageView imageProgressBar = game.getGameActivity().findViewById(R.id.image_progress_bar);
        mProgressBar = (ClipDrawable) imageProgressBar.getDrawable();
        // Init the star
        mStar1 = (ImageView) game.getGameActivity().findViewById(R.id.image_star_01);
        mStar2 = (ImageView) game.getGameActivity().findViewById(R.id.image_star_02);
        mStar3 = (ImageView) game.getGameActivity().findViewById(R.id.image_star_03);
        // Init the factor base on moves
        mPointFactor = 10000 / (mLevel.mMove * 40f);   // Progress bar max is 10000
    }

    @Override
    public void onStart() {
        mPoints = 0;
        mCurrentStar = 0;
        drawUI();
    }

    private final Runnable mUpdateStarRunnable = new Runnable() {
        @Override
        public void run() {
            switch (mCurrentStar) {
                case 1:
                    mStar1.setImageResource(R.drawable.star);
                    break;
                case 2:
                    mStar2.setImageResource(R.drawable.star);
                    break;
                case 3:
                    mStar3.setImageResource(R.drawable.star);
                    break;
            }
        }
    };

    @Override
    public void onUpdate(long elapsedMillis) {
        if (mPoints >= STAR1_THRESHOLD && mCurrentStar < 1) {
            mLevel.mStar = 1;
            mCurrentStar = 1;
            mStar1.post(mUpdateStarRunnable);
            mGame.getSoundManager().playSound(MySoundEvent.ADD_STAR);
        } else if (mPoints >= STAR2_THRESHOLD && mCurrentStar < 2) {
            mLevel.mStar = 2;
            mCurrentStar = 2;
            mStar1.post(mUpdateStarRunnable);
            mGame.getSoundManager().playSound(MySoundEvent.ADD_STAR);
        } else if (mPoints >= STAR3_THRESHOLD && mCurrentStar < 3) {
            mLevel.mStar = 3;
            mCurrentStar = 3;
            mStar1.post(mUpdateStarRunnable);
            mGame.getSoundManager().playSound(MySoundEvent.ADD_STAR);
            removeFromGame();
        }
    }

    @Override
    protected void onDrawUI() {
        mProgressBar.setLevel(mPoints);
    }

    @Override
    public void onGameEvent(GameEvent gameEvents) {
        if (gameEvents == MyGameEvent.BUBBLE_POP) {
            mPoints += mPointFactor * POINTS_GAINED_PER_BUBBLE;
            drawUI();
        }
    }

}
