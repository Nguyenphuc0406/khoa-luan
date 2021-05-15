package com.hust.medtech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private Button btnScanQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScanQR = findViewById(R.id.btn_scan_qr);
        btnScanQR.setOnClickListener(v -> {
            onScanCode();
                }
        );
    }

    public void onScanCode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                IntentIntegrator integrator = new IntentIntegrator(this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setCameraId(0); // Use a specific camera of the device
                integrator.setBarcodeImageEnabled(true);
                integrator.setPrompt("");
                integrator.setCaptureActivity(AnyOrientationCaptureActivity.class);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_CONTACTS)) {
                    Toast.makeText(this, "Not Permission ",
                            Toast.LENGTH_SHORT).show();
                } else {

// No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            100);
                }
            }
        } else {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setCameraId(0); // Use a specific camera of the device
            integrator.setBarcodeImageEnabled(true);
            integrator.setPrompt("");
            integrator.setCaptureActivity(AnyOrientationCaptureActivity.class);
            integrator.setOrientationLocked(false);
            integrator.initiateScan();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == IntentIntegrator.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//retrieve scan result
                IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

                if (scanningResult != null) {
                    if (scanningResult.getContents() != null) {
//we have a result
                        String codeContent = scanningResult.getContents();
                        if ("123".equals(codeContent)) {
                            startActivity(new Intent(this,FormRegisterActivity.class));

                        }else {
                            Toast.makeText(this, "QR wrong", Toast.LENGTH_SHORT).show();
                        }

//                        open form





                    } else {
                        Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100: {
// If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    onScanCode();
                } else {

                    Toast.makeText(this,"Deny camera",
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}