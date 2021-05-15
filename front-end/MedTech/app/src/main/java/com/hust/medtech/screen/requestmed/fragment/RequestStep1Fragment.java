package com.hust.medtech.screen.requestmed.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hust.medtech.R;
import com.hust.medtech.base.AnyOrientationCaptureActivity;
import com.hust.medtech.base.BaseFragment;
import com.hust.medtech.databinding.FragmentRequestStep1Binding;

public class RequestStep1Fragment extends BaseFragment<FragmentRequestStep1Binding,RequestStep1Fragment> {

    public static RequestStep1Fragment newInstance() {
        
        Bundle args = new Bundle();
        
        RequestStep1Fragment fragment = new RequestStep1Fragment();
        fragment.setArguments(args);
        return fragment;
    }
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    @Override
    public int layoutId() {
        return R.layout.fragment_request_step1;
    }

    @Override
    public void initData() {
        mBinding.setPresenter(this);
    }

    public void onCancel(){
        getActivity().onBackPressed();
    }
    public void onScanCode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CAMERA);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setBarcodeImageEnabled(true);
                integrator.setPrompt("");
                integrator.setCaptureActivity(AnyOrientationCaptureActivity.class);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.READ_CONTACTS)) {
                    Toast.makeText(getActivity(),"dialog_input_serial_msg_req_permission",
                            Toast.LENGTH_SHORT).show();
                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }
        } else {
            IntentIntegrator integrator = new IntentIntegrator(getActivity());
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setCameraId(0);  // Use a specific camera of the device
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
                        if(codeContent.equals("BV_YHN") ||codeContent.equals("BV_BM") ){
                            gotoStep2();
                        }else {
                            Toast.makeText(getActivity(), "Mã bệnh viện không hợp lệ!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {
        }
    }

    private void gotoStep2(){
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fm_main, RequestStep2Fragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    onScanCode();
                } else {

                    Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();

                }
                return;
            }
        }
    }
}
