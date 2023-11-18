package com.vuphan.listform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.vuphan.listform.database.UserDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText edUserName, edBirthDay, edEmail;
    private Button btnAddUser;
    private RecyclerView rcvUser;
    private UserAdapter userAdapter;
    private List<User> mListUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        mListUser = new ArrayList<>();
        mListUser = UserDatabase.getInstance(this).userDAO().getListUsers();
        userAdapter = new UserAdapter();
        userAdapter.setData(mListUser);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvUser.setLayoutManager(linearLayoutManager);
        rcvUser.setAdapter(userAdapter);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }

    private void initUI() {
        edUserName = findViewById((R.id.ed_username));
        edBirthDay = findViewById((R.id.ed_birthday));
        edEmail = findViewById((R.id.ed_email));
        btnAddUser = findViewById((R.id.btn_add_user));
        rcvUser = findViewById((R.id.rcv_user));
    }

    private void addUser() {
        String userName = edUserName.getText().toString().trim();
        String birthDay = edBirthDay.getText().toString().trim();
        String email = edEmail.getText().toString().trim();

        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(birthDay) || TextUtils.isEmpty(email) ){
            return;
        }

        User user = new User(userName, birthDay, email);
        UserDatabase.getInstance(this).userDAO().insertUser(user);
        Toast.makeText(this, "Add user success!", Toast.LENGTH_SHORT).show();

        edUserName.setText("");
        edBirthDay.setText("");
        edEmail.setText("");

        hideSoftKeyboard();
        mListUser = UserDatabase.getInstance(this).userDAO().getListUsers();
        userAdapter.setData(mListUser);
    }

    public void hideSoftKeyboard(){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }
    }
}