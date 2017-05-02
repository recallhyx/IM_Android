package com.cst.im.UI;

import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cst.im.NetWork.ComService;
import com.cst.im.R;
import com.cst.im.UI.main.MainActivity;
import com.cst.im.presenter.ILoginPresenter;
import com.cst.im.presenter.LoginPresenterCompl;
import com.cst.im.presenter.Status;
import com.cst.im.view.ILoginView;

public class LoginActivity extends AppCompatActivity implements ILoginView,View.OnClickListener,View.OnFocusChangeListener{
    //显示动画
    final TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
    private EditText editUser;
    private EditText editPwd;
    private Button btnLogin;
    private ImageView topPicture ;
    private ImageView showPassword;

    private ImageView ivLoginByQQ;
    private ImageView ivLoginByWechat;
    private ImageView ivLoginByWeibo;
    private TextView tvForgetPassword;
    private LinearLayout activityMain ;
    private TextInputLayout tilUsername;
    private TextInputLayout tilPassword;

    // 注册按钮
    Button btnRegister;

    //登录业务逻辑
    ILoginPresenter loginPresenter;

    ComService comService;
    ServiceConnection serviceConn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cst.im.R.layout.activity_login);

        initView();

        //init login presenter
        loginPresenter = new LoginPresenterCompl(this);
        mShowAction.setDuration(500);//动画运行时间

        btnLogin.setOnClickListener(this); // 登录按钮
        btnRegister.setOnClickListener(this); // 注册按钮
        editPwd.setOnFocusChangeListener(this); // 输入密码时图标消失
        onEditTip();

//网络接口测试部分
////////////////////////////////////////////////////////////////////
        /*
        serviceConn =new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                comService = ((ComService.MyBind)service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(new Intent(this, ComService.class), serviceConn, BIND_AUTO_CREATE);
        */
        //启动服务
        Intent startIntent = new Intent(this, ComService.class);
        startService(startIntent);//记得最后结束
        // TODO: 2017/4/30 service还未写结束，可以写一个Service管理类,现在会有个bug,就是关闭不了service
//////////////////////////////////////////////////////////////////////////////

    }

    // 初始化控件
    private void initView(){
        //find Control
        topPicture = (ImageView) findViewById(R.id.top_picture);
        editUser = (EditText) findViewById(R.id.username);
        editPwd = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.login_button);
        ivLoginByQQ = (ImageView) findViewById(R.id.qq_login);
        ivLoginByWechat = (ImageView) findViewById(R.id.wechat_login);
        ivLoginByWeibo = (ImageView) findViewById(R.id.weibo_login);
        activityMain = (LinearLayout) findViewById(R.id.activity_main);
        tilUsername = (TextInputLayout) findViewById(R.id.usernameWrapper);
        tilPassword = (TextInputLayout) findViewById(R.id.passwordWrapper);
        btnRegister = (Button) findViewById(R.id.register_action);
    }

    //处理登录事件的UI提示
    @Override
    public void onLoginResult(int rslCode, String rslMsg){

        if (rslCode==0){
            //页面跳转
            Intent it = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(it);
            LoginActivity.this.finish();
            Toast.makeText(this,rslMsg, Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"Login Fail, code = " + rslCode,Toast.LENGTH_SHORT).show();
    }

    //网络错误提示
    @Override
    public void onNetworkError() {

    }
    //输入时的提醒
    @Override
    public void onEditTip() {
        editUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Drawable drawable = getResources().getDrawable(R.drawable.login_warning);
                drawable.setBounds(0,0,56,56);
                switch (loginPresenter.judgeUsername(editUser.getText().toString())){
                    case Status.Login.USERNAME_INVALID:
                        editUser.setError("=.=",drawable);
                        break;
                    case Status.Login.USERNAME_PHONE:
                        editUser.setError("手机号",drawable);
                        break;
                    case Status.Login.USERNAME_EMAIL:
                        editUser.setError("邮箱",drawable);
                        break;
                    case Status.Login.USERNAME_ACCOUNT:
                        editUser.setError("用户名",drawable);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Drawable drawable = getResources().getDrawable(R.drawable.login_warning);
                drawable.setBounds(0,0,56,56);
                if(loginPresenter.judgePassword(editPwd.getText().toString())){
                    editPwd.setError("√",drawable);
                }
                else {
                    editPwd.setError("x",drawable);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.login_button:
                loginPresenter.doLogin(editUser.getText().toString(),editPwd.getText().toString());
                break;
            case R.id.register_action:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            topPicture.setVisibility(View.GONE);
        }
        else{
            topPicture.startAnimation(mShowAction);
            topPicture.setVisibility(View.VISIBLE);
        }
    }



}
