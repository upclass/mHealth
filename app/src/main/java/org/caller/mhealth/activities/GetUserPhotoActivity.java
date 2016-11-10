package org.caller.mhealth.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TResult;

import org.caller.mhealth.MainApplication;
import org.caller.mhealth.R;
import org.caller.mhealth.entitys.MyUser;
import org.caller.mhealth.tools.CustomHelper;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class GetUserPhotoActivity extends TakePhotoActivity {
    private CustomHelper customHelper;
    Intent mIntent = new Intent();
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_photo);
        showProgresDialog();
        Intent intent = getIntent();
        boolean isTake = intent.getBooleanExtra("isTake", true);
        customHelper = new CustomHelper();
        customHelper.onClick(isTake, getTakePhoto());
        setResult(1, mIntent);
    }


    @Override
    public void takeCancel() {
        super.takeCancel();
        mIntent.putExtra("result", "取消了操作");
        finish();

    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        mIntent.putExtra("result", "出错");
        finish();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        mIntent.putExtra("result", "选择成功");
        final MyUser loginUser = ((MainApplication) getApplication()).getLoginUser();

        String path = result.getImage().getPath();
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    final String url = bmobFile.getFileUrl();
                    MyUser user = ((MainApplication) getApplication()).getLoginUser();
                    user.setPhoto(url);
                    user.update(user.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Log.i("bmob", "更新成功");
                            } else {
                                Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });
                    mIntent.putExtra("photo", loginUser.getPhoto());
                    finish();
                } else {
                    Toast.makeText(GetUserPhotoActivity.this, "上传文件成失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void showProgresDialog() {
        mProgressDialog = new ProgressDialog(GetUserPhotoActivity.this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("正在加载·············");
        mProgressDialog.show();
    }
}



