package com.techware.clickkart.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.LinearLayout;

import com.techware.clickkart.R;
import com.techware.clickkart.config.TypefaceCache;


/**
 * Created by Jemsheer K D on 22 December, 2016.
 * Package com.wakilishwa.dialogs
 * Project Wakilishwa
 */

public class SelectPhotoVideoDialog {

    private final Activity mContext;
    private Typeface typeface;
    private SelectPhotoVideoDialogActionListener selectPhotoVideoDialogActionListener;
    private Dialog dialogSelectDialog;

    public SelectPhotoVideoDialog(Activity mContext) {
        this.mContext = mContext;

        try {
            typeface = TypefaceCache.getInstance().getTypeface(mContext.getApplicationContext(), "Roboto-Regular.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSelectPhotoVideoDialog();
    }

    public void show() {
        dialogSelectDialog.show();
    }

    private void setSelectPhotoVideoDialog() {

        dialogSelectDialog = new Dialog(mContext, R.style.ThemeDialogCustom_NoBackground);
        dialogSelectDialog.setContentView(R.layout.dialog_select_photo_video);
        dialogSelectDialog.setTitle(R.string.title_select_photo_or_video);

        LinearLayout llPhoto = (LinearLayout) dialogSelectDialog.findViewById(R.id.ll_dialog_select_photo_video_photo);
        LinearLayout llVideo = (LinearLayout) dialogSelectDialog.findViewById(R.id.ll_dialog_select_photo_video_cam);

        llPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                selectPhotoVideoDialogActionListener.onSelectPhotoClick();
                dialogSelectDialog.dismiss();
            }
        });
        llVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                selectPhotoVideoDialogActionListener.onSelectVideoClick();
                dialogSelectDialog.dismiss();
            }
        });
    }

    public interface SelectPhotoVideoDialogActionListener {

        void onSelectPhotoClick();

        void onSelectVideoClick();

        void onSnackBarShow(String message);

        void onSwipeRefreshChange(boolean isSwipeRefreshing);
    }

    public SelectPhotoVideoDialogActionListener getSelectPhotoVideoDialogActionListener() {
        return selectPhotoVideoDialogActionListener;
    }


    public void setSelectPhotoVideoDialogActionListener(SelectPhotoVideoDialogActionListener selectPhotoVideoDialogActionListener) {
        this.selectPhotoVideoDialogActionListener = selectPhotoVideoDialogActionListener;
    }
}
