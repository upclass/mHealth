package org.caller.mhealth.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


import org.caller.mhealth.MainActivity;
import org.caller.mhealth.MainApplication;
import org.caller.mhealth.R;
import org.caller.mhealth.entitys.Comment;
import org.caller.mhealth.entitys.MyUser;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class PushCommentActivity extends AppCompatActivity {
    private EditText  ed_content;
    private ImageView usephotoImageView;
    private String mAddress;
    private Button mSubmit;
    private ImageView mAdd;
    private String mAddPhoto;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_comment);
        initView();
        String address = getIntent().getStringExtra("address");
        mAddress=address;
    }

    private void initView() {
        usephotoImageView = (ImageView) findViewById(R.id.avatarImg);
        ed_content = (EditText) findViewById(R.id.ed_content);
        mSubmit= (Button) findViewById(R.id.btn_submit);
        mAdd= (ImageView) findViewById(R.id.iv_menu);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(PushCommentActivity.this).inflate(R.layout.bottom_dialog, null);
                TextView tv_take = (TextView) view.findViewById(R.id.tv_take);
                TextView tv_select = (TextView) view.findViewById(R.id.tv_select);
                final Dialog mBottomSheetDialog = new Dialog(PushCommentActivity.this, R.style.MaterialDialogSheet);
                mBottomSheetDialog.setContentView(view);
                mBottomSheetDialog.setCancelable(true);
                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                mBottomSheetDialog.show();
                tv_take.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PushCommentActivity.this, GetUserPhotoActivity.class);
                        intent.putExtra("isTake", true);
                        startActivityForResult(intent, 998);
                        mBottomSheetDialog.dismiss();
                    }
                });

                tv_select.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PushCommentActivity.this, GetUserPhotoActivity.class);
                        intent.putExtra("isTake", false);
                        startActivityForResult(intent, 999);
                        mBottomSheetDialog.dismiss();
                    }
                });
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = new Comment();
                comment.setContent(ed_content.getText().toString());
                comment.setTime(SystemClock.currentThreadTimeMillis());
                comment.setName(comment.getName());
                String photo = ((MainApplication) getApplication()).getLoginUser().getPhoto();
                comment.setPhoto(photo);
                comment.setPhotoAdd(mAddPhoto);
                comment.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId,BmobException e) {
                        if(e==null){
                            Snackbar.make(mSubmit,"发布成功",Snackbar.LENGTH_LONG).show();
                            finish();
                        }else{
                            Snackbar.make(mSubmit,e.getMessage(),Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        String photo = ((MainApplication) getApplication()).getLoginUser().getPhoto();
        Picasso.with(this).load(photo).into(usephotoImageView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            String result = data.getStringExtra("result");
            Snackbar.make(mSubmit, result, Snackbar.LENGTH_SHORT)
                    .show();
            String path = data.getStringExtra("path");
            UploadPhoto(path);
        }
    }

    private void UploadPhoto(String path) {
//        mProgressDialog.show();
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    String url = bmobFile.getFileUrl();
                    mAddPhoto=url;
                    Snackbar.make(mSubmit,"上传图片成功",Snackbar.LENGTH_LONG).show();

                } else {
                    Snackbar.make(mSubmit,"上传图片失败",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

//    public void showProgresDialog() {
//        mProgressDialog = new ProgressDialog(PushCommentActivity.this);
//        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        mProgressDialog.setMessage("正在加载·············");
//        mProgressDialog.show();
//    }

}
