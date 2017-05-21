package com.example.administrator.itsdemo.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.administrator.itsdemo.R;

/**
 * Created by Administrator on 2017/5/14.
 */

public class LucencyDialogFragment  extends Fragment{

    private Button btn_dialog;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lucency_dialog,container,false);

        btn_dialog = (Button) view.findViewById(R.id.lucency_dialog);
        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialog.setNeutralButton("忽略", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = alertDialog.create();

                Window window = dialog.getWindow();

                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.alpha = 0.5f;
                window.setAttributes(attributes);

                dialog.show();
            }
        });
        return view;
    }
}
