package org.jiangtao.utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import org.jiangtao.lifetime.IndexActivity;
import org.jiangtao.lifetime.R;

import static android.support.v4.app.ActivityCompat.startActivityForResult;


/**
 * Created by erdaye on 2015/11/24.
 */
public class Popupwindow extends PopupWindow {

    private Button pupBtnTakePhoto,pupBtnRichScan,pupBtnWriteDynamic,pupBtnWriteNote,pupBtnCancel;
    private View mMenuView;

    public Popupwindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popupwindow, null);
        pupBtnTakePhoto = (Button) mMenuView.findViewById(R.id.pup_btn_takephoto);
        pupBtnRichScan = (Button) mMenuView.findViewById(R.id.pup_btn_richscan);
        pupBtnWriteDynamic= (Button) mMenuView.findViewById(R.id.pup_btn_writedynamic);
        pupBtnWriteNote= (Button) mMenuView.findViewById(R.id.pup_btn_writenote);
//        pupBtnCancel= (Button) mMenuView.findViewById(R.id.pup_btn_cancel);
        //取消按钮
//        pupBtnCancel.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                //销毁弹出框
//                dismiss();
//            }
//        });

        //设置按钮监听
        pupBtnTakePhoto.setOnClickListener(itemsOnClick);
        pupBtnRichScan.setOnClickListener(itemsOnClick);
        pupBtnWriteDynamic.setOnClickListener(itemsOnClick);
        pupBtnWriteNote.setOnClickListener(itemsOnClick);
        //设置PopupWindow的View
        this.setContentView(mMenuView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置PopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.mypopwindow_anim_style);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }


}
