package kr.edcan.alime.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import kr.edcan.alime.R;
import kr.edcan.alime.databinding.ActivityRegisterBinding;
import kr.edcan.alime.models.User;
import kr.edcan.alime.utils.AlimeUtils;
import kr.edcan.alime.utils.NetworkHelper;
import kr.edcan.alime.utils.NetworkInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    NetworkInterface service;
    Call<User> registerUser;
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        service = NetworkHelper.getNetworkInstance();
        setDefault();
    }

    private void setDefault() {
        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(), R.layout.spinner_item,
                AlimeUtils.getType());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.registerSpinner.setAdapter(adapter);
        binding.registerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser = service.userRegister(
                        binding.registerId.getText().toString().trim(),
                        binding.registerPassword.getText().toString().trim(),
                        binding.registerName.getText().toString().trim(),
                        false,
                        binding.registerSpinner.getSelectedItemPosition()
                );
                registerUser.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        switch (response.code()) {
                            case 200:
                                Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                finish();
                                break;
                            case 409:
                                Toast.makeText(RegisterActivity.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("asdf", t.getMessage());
                        Toast.makeText(RegisterActivity.this, "서버와의 연결에 문제가 있습니다.\n이 문제가 지속될 시, 서비스 관리자에게 문의해주세요.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
