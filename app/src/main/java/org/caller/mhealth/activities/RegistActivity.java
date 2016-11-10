package org.caller.mhealth.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.caller.mhealth.R;
import org.caller.mhealth.entitys.MyUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class RegistActivity extends AppCompatActivity {

    @BindView(R.id.input_name)
    EditText registName;
    @BindView(R.id.input_password)
    EditText registPassword;
    @BindView(R.id.input_email)
    EditText registEmail;
    @BindView(R.id.btn_signup)
    Button mBtnRegist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
    }

    public boolean isEmpty(String s) {
        if (s == null) return true;
        else if (s.equals("")) return true;
        else return false;
    }

    @OnClick(R.id.btn_signup)
    public void regist() {
        final String name = registName.getText().toString();
        final String password = registPassword.getText().toString();
        final String email = registEmail.getText().toString();
        if (!isEmpty(name) && !isEmpty(password) && !isEmpty(email)) {
            final MyUser user = new MyUser();
            user.setUsername(name);
            user.setPassword(password);
            user.setEmail(email);

            user.signUp(new SaveListener<MyUser>() {
                @Override
                public void done(MyUser s, BmobException e) {

                    if (e == null) {
                        Toast.makeText(RegistActivity.this, "注册成功" + s.toString(), Toast.LENGTH_SHORT).show();
                        final String em = email;
                        BmobUser.requestEmailVerify(email, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {

                                if (e == null) {
                                    Toast.makeText(RegistActivity.this, "请求验证邮件成功，请到" + email + "邮箱中进行激活。", Toast.LENGTH_SHORT).show();;
                                } else {
                                    Toast.makeText(RegistActivity.this, "失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(RegistActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else
            Toast.makeText(RegistActivity.this, "存在空值", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
