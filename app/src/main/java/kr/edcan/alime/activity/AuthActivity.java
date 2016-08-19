package kr.edcan.alime.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.tool.DataBindingBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import kr.edcan.alime.R;
import kr.edcan.alime.databinding.ActivityAuthBinding;
import kr.edcan.alime.models.User;
import kr.edcan.alime.utils.DataManager;
import kr.edcan.alime.utils.NetworkHelper;
import kr.edcan.alime.utils.NetworkInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {

    Call<User> userLogin;
    NetworkInterface service;
    ActivityAuthBinding binding;
    DataManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth);
        setDefault();
    }

    private void setDefault() {
        manager = new DataManager();
        manager.initializeManager(this);
        service = NetworkHelper.getNetworkInstance();
        binding.loginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin = service.userLogin(binding.loginId.getText().toString().trim(),
                        binding.loginPassword.getText().toString().trim());
                userLogin.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        switch (response.code()){
                            case 200:
                                manager.saveUser(response.body());
                                finish();
                                break;
                            case 400:
                                Toast.makeText(AuthActivity.this, "아이디 혹은 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                                break;
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("asdf", t.getMessage());
                        Toast.makeText(AuthActivity.this, "서버와의 연결에 문제가 있습니다.\n이 문제가 지속될 시, 서비스 관리자에게 문의해주세요.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        binding.loginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }
}
