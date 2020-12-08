package com.example.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.music.MyService.MyBinder;
public class MainActivity extends AppCompatActivity {
    private MyService myService;
    private boolean isBound = false;
    private ServiceConnection connection;
    SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView btOn = (ImageView) findViewById(R.id.mo);
        final ImageView btOff = (ImageView) findViewById(R.id.off);
        final ImageView btFast = (ImageView) findViewById(R.id.toi);
        final ImageView btPlay = (ImageView) findViewById(R.id.play);
        final ImageView btTua=(ImageView) findViewById(R.id.tuilai);

        // Khởi tạo ServiceConnection
        connection = new ServiceConnection() {

            // Phương thức này được hệ thống gọi khi kết nối tới service bị lỗi
            @Override
            public void onServiceDisconnected(ComponentName name) {

                isBound = false;
            }

            // Phương thức này được hệ thống gọi khi kết nối tới service thành công
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyBinder binder = (MyBinder) service;
                myService = binder.getService(); // lấy đối tượng MyService
                isBound = true;
            }
        };

        // Khởi tạo intent
        final Intent intent =
                new Intent(MainActivity.this,
                        MyService.class);

        btOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Bắt đầu một service sủ dụng bind
                bindService(intent, connection,
                        Context.BIND_AUTO_CREATE);
                Toast.makeText(MainActivity.this,
                        "Mở bài hát", Toast.LENGTH_SHORT).show();
                // Đối thứ ba báo rằng Service sẽ tự động khởi tạo
            }
        });

        btOff.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Nếu Service đang hoạt động
                if (isBound) {
                    // Tắt Service
                    unbindService(connection);
                    isBound = false;
                    Toast.makeText(MainActivity.this,
                            "Tắt", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btFast.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // nếu service đang hoạt động
                if (isBound) {
                    // tua bài hát
                    myService.fastForward();
                    myService.fastStart();
                    Toast.makeText(MainActivity.this,
                            "Tua tới bài hát", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBound) {
                    // tua bài hát)
                    myService.fastStart();
                    Toast.makeText(MainActivity.this,
                            "Tua tới bài hát", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // nếu service đang hoạt động
                if (isBound) {
                    // tua bài hát
                  if(myService.isPlaying()){
                      myService.pause();
                      btPlay.setImageResource(R.drawable.play1);
                      Toast.makeText(MainActivity.this,
                              "Tạm dừng", Toast.LENGTH_SHORT).show();
                  }else{
                      myService.play();
                      btPlay.setImageResource(R.drawable.pause1);
                      Toast.makeText(MainActivity.this,
                              "Tiếp tục", Toast.LENGTH_SHORT).show();
                  }
                } else {
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btTua.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // nếu service đang hoạt động
                if (isBound) {
                    // tua bài hát
                    myService.fastForward();
                    myService.fastStart();
                    Toast.makeText(MainActivity.this,
                            "Tua lui bài hát", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.tuilai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBound) {
                    // tua bài hát)
                    myService.fastStart();
                    Toast.makeText(MainActivity.this,
                            "Tua lui bài hát", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}