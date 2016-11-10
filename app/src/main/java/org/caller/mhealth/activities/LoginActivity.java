package org.caller.mhealth.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.caller.mhealth.MainActivity;
import org.caller.mhealth.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


public class LoginActivity extends AppCompatActivity  {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.btnLogin)
    Button mLogin;
    @BindView(R.id.et_account)
    EditText loginName;
    @BindView(R.id.et_password)
    EditText loginPassword;
    @BindView(R.id.btn_register)
    Button btnregist;
    ProgressDialog mProgressDialog;
    @BindView(R.id.send_email)
    TextView mSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initToolbar();
    }


    @OnClick(R.id.btnLogin)
    public void login() {
        loginToServer();
    }

    @OnClick(R.id.send_email)
    public void sendEmail(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final EditText mt=new EditText(this);
        builder.setView(mt)
                .setTitle("邮箱验证")
                .setNegativeButton("send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String s = mt.getText().toString();
                        if(s.isEmpty())
                            Toast.makeText(LoginActivity.this, "邮箱不为空", Toast.LENGTH_SHORT).show();
                        else {
                            BmobUser.requestEmailVerify(s, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(LoginActivity.this, "请求验证邮件成功，请到" + s + "邮箱中进行激活。", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }).show();

    }

    private void loginToServer() {
        final String s = loginName.getText().toString();
        final String s1 = loginPassword.getText().toString();
        if (!isEmpty(s) && !isEmpty(s1)) {
            showProgresDialog();

            BmobUser bu2 = new BmobUser();
            bu2.setUsername(s);
            bu2.setPassword(s1);
            bu2.login(new SaveListener<BmobUser>() {

                @Override
                public void done(BmobUser bmobUser, BmobException e) {
                    if (e == null) {
                        mProgressDialog.dismiss();
                        if (bmobUser.getEmailVerified() == false) {
                            Toast.makeText(LoginActivity.this, "邮箱还没验证", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    } else {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else Toast.makeText(LoginActivity.this, "账号密码不为空", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_register)
    public void regist() {
        Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
        startActivity(intent);
    }


    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowTitleEnabled(false);
    }


    public boolean isEmpty(String s) {
        if (s == null) return true;
        else if (s.equals("")) return true;
        else return false;
    }

    public void showProgresDialog() {
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("正在加载·············");
        mProgressDialog.show();
    }

}
