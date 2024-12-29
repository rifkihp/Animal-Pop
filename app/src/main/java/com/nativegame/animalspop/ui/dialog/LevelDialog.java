package com.nativegame.animalspop.ui.dialog;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nativegame.animalspop.MainActivity;
import com.nativegame.animalspop.R;
import com.nativegame.animalspop.ui.TransitionEffect;
import com.nativegame.animalspop.ui.UIEffect;
import com.nativegame.animalspop.level.MyLevel;
import com.nativegame.animalspop.sound.MySoundEvent;
import com.nativegame.nattyengine.ui.GameActivity;
import com.nativegame.nattyengine.ui.GameDialog;

/**
 * Created by Oscar Liang on 2022/09/18
 */

public class LevelDialog extends GameDialog implements View.OnClickListener,
        TransitionEffect.OnTransitionListener {

    private final TransitionEffect mTransitionEffect;

    public LevelDialog(GameActivity activity, int level) {
        super(activity);
        setContentView(R.layout.dialog_level);
        setRootLayoutId(R.layout.dialog_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);
        mTransitionEffect = new TransitionEffect(activity);
        mTransitionEffect.setListener(this);
        init((MyLevel) activity.getLevelManager().getLevel(level));
    }

    private void init(MyLevel mLevel) {
        // Init level text
        TextView txtLevel = (TextView) findViewById(R.id.txt_level);
        txtLevel.setText(mParent.getResources().getString(R.string.txt_level, mLevel.mLevel));

        // Init level target text
        TextView txtTarget = (TextView) findViewById(R.id.txt_target);
        switch (mLevel.mLevelType) {
            case POP_BUBBLE:
                txtTarget.setText(mParent.getResources().getString(R.string.txt_level_type_pop));
                break;
            case COLLECT_ITEM:
                txtTarget.setText(mParent.getResources().getString(R.string.txt_level_type_collect));
                break;
        }

        // Init level star
        ImageView imgStar = (ImageView) findViewById(R.id.image_star);
        int star = ((MainActivity) mParent).getDatabaseHelper().getLevelStar(mLevel.mLevel);
        if (star != -1) {
            switch (star) {
                case 1:
                    imgStar.setImageResource(R.drawable.star_set_01);
                    break;
                case 2:
                    imgStar.setImageResource(R.drawable.star_set_02);
                    break;
                case 3:
                    imgStar.setImageResource(R.drawable.star_set_03);
                    break;
            }
        }

        // Init button
        ImageButton btnPlay = (ImageButton) findViewById(R.id.btn_play);
        ImageButton btnCancel = (ImageButton) findViewById(R.id.btn_cancel);
        btnPlay.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        UIEffect.createButtonEffect(btnPlay);
        UIEffect.createButtonEffect(btnCancel);

        // Init player image
        ImageView imagePlayer = (ImageView) findViewById(R.id.image_fox);
        imagePlayer.setImageResource(mLevel.mLevelType.getAnimalDrawableId());

        //Init pop effect
        UIEffect.createPopUpEffect(imagePlayer);
        UIEffect.createPopUpEffect(findViewById(R.id.image_fox_bg), 2);
        UIEffect.createPopUpEffect(txtTarget, 3);
        UIEffect.createPopUpEffect(btnPlay, 4);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_play) {
            mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            navigateToGame();
        } else if (id == R.id.btn_cancel) {
            mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            dismiss();
        }
    }

    @Override
    protected void onShow() {
        mParent.getSoundManager().playSound(MySoundEvent.SWEEP_IN);
    }

    @Override
    public void onTransition() {
        // Dismiss the dialog and navigate to fragment
        dismiss();
        startGame();
    }

    public void navigateToGame() {
        mTransitionEffect.show();
    }

    public void startGame() {
        // Override this method to start the game
    }

}
