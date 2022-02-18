package vn.edu.tnut.trungtien.baitaplon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    // Khai báo các buttton
    private Button btnGioithieu;
    private Button btnDoctruyen;
    private Button btnGetsoluong;
    private Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Lấy id của các button
        btnGioithieu = (Button) findViewById(R.id.btn_gioithieu);
        btnDoctruyen = (Button) findViewById(R.id.btn_truyen);
        btnGetsoluong = (Button) findViewById(R.id.btn_soluong);
        btnPost = (Button) findViewById(R.id.btn_post);

        // Đăng ký sự kiện click cho button giới thiệu
        btnGioithieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GioiThieu.class);
                startActivity(intent);
            }
        });

        // Đăng ký sự kiện click cho button đọc truyện
        btnDoctruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TruyenTranh.class);
                startActivity(intent);
            }
        });
        // Đăng ký sự kiện click cho button số lượng sinh viên trên tms
        btnGetsoluong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GetSoLuong.class);
                startActivity(intent);
            }
        });
        // Đăng ký sự kiện ckick cho button post
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Post.class);
                startActivity(intent);
            }
        });
    }

}