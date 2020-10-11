package com.blueview.led.Util;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blueview.led.R;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.camera.CameraManager;

public class ScanQRCodeActivity extends AppCompatActivity {

    private TextView opxc;
    private CaptureManager capture;
    private DecoratedBarcodeView bv_barcode;
    private ImageView sm_back;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scan_qrcode);
        getSupportActionBar().hide();
        bv_barcode = (DecoratedBarcodeView) findViewById(R.id.bv_barcode);
        opxc=findViewById(R.id.sm_opxz);
        sm_back=findViewById(R.id.sm_back);
        capture = new CaptureManager(this, bv_barcode);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
        opxc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent innerIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");
                startActivityForResult(wrapperIntent, 1);
            }
        });
        sm_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return bv_barcode.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

}

