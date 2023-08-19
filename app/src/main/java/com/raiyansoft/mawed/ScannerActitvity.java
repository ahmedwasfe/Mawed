package com.raiyansoft.mawed;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.ui.activities.salon.SalonOrderSamaryActivity;
import com.raiyansoft.mawed.utils.Const;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.FileNotFoundException;
import java.io.InputStream;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerActitvity extends AppCompatActivity implements ZBarScannerView.ResultHandler {
    private static final String TAG = ScannerActitvity.class.getSimpleName();

    private static final int CODE_REQUEST_CAMERA_PERMISSION = 8;


    private ZBarScannerView zBarScannerView;
    private CodeScanner codeScanner;
    private CodeScannerView codeScannerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        zBarScannerView = new ZBarScannerView(this);
//        setContentView(zBarScannerView);
        initScanner();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CODE_REQUEST_CAMERA_PERMISSION);
        } else {
            codeScanner = new CodeScanner(this, codeScannerView);
            setupCodeScanner();
//            zBarScannerView.startCamera();
        }

        findViewById(R.id.iv_scan_image)
                .setOnClickListener(v -> {
                    Log.d(TAG, "onScanImageClick: ");
                    Intent intent = new Intent(/* Intent.ACTION_PICK */ Intent.ACTION_GET_CONTENT)
                            .setType("image/*");
                    someActivityResultLauncher.launch(intent);
                });

    }

    private void setupCodeScanner() {
        if (codeScanner == null)
            codeScanner = new CodeScanner(this, codeScannerView);

        codeScanner.setDecodeCallback(result -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    MainActivity._etInput.setText(result.getText() + "\n" + result.getBarcodeFormat());
                    MultiFormatWriter writer = new MultiFormatWriter();
                    try {
                        // TODO initialize bit materix
                        BitMatrix matrix = writer.encode(result.getText(), BarcodeFormat.QR_CODE, 350, 350);
                        // TODO initialize barcode encoder
                        BarcodeEncoder encoder = new BarcodeEncoder();
                        // TODO initialize bitmap
                        Bitmap bitmap = encoder.createBitmap(matrix);
                        // TODO set bitmap on imageview
//                        MainActivity._ivCode.setImageBitmap(bitmap);
                        // TODO initialize input manager
                        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        // TODO Hide soft keyboard
//                        manager.hideSoftInputFromWindow(MainActivity._etInput.getApplicationWindowToken(), 0);
//                        Toast.makeText(ScannerActitvity.this, result.getText(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ScannerActitvity.this, SalonOrderSamaryActivity.class)
                                .putExtra(Const.KEY_APPOINTMENT_ID, Integer.parseInt(result.getText())));
                    } catch (WriterException e) {
                        e.printStackTrace();
                        Log.e(TAG, "run: " + e.getMessage());
                    }catch (NoSuchMethodError suchMethodError){
                        Log.e(TAG, "run: " + suchMethodError.getMessage());
                    }
//                    onBackPressed();
                }
            });
        });

        codeScannerView.setOnClickListener(v -> codeScanner.startPreview());
    }

    private void initScanner() {


        codeScannerView = findViewById(R.id.scanner_view);

        zBarScannerView.setFlash(true);
        zBarScannerView.setAutoFocus(true);
    }

    @Override
    public void handleResult(Result rawResult) {
//        MainActivity._etInput.setText(rawResult.getContents());
        Log.d(TAG, "handleResult: " + rawResult.toString() + "\n" + rawResult.getBarcodeFormat().getName());
        onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (codeScanner != null)
            codeScanner.startPreview();
    }

    @Override
    public void onPause() {
        if (codeScanner != null)
            codeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_REQUEST_CAMERA_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                zBarScannerView.startCamera();
                setupCodeScanner();
        } else
            Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

//                    if (result.requestCode == CODE_REQUEST_STOARGE) {
                    if (result.getResultCode() == RESULT_OK) {
                        Uri qrCodeImage = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(qrCodeImage);
                            Bitmap qrCodeBitmap = BitmapFactory.decodeStream(inputStream);
                            Bitmap bMap = qrCodeBitmap;
                            String content = null;
                            int[] ints = new int[bMap.getWidth() * bMap.getHeight()];
                            bMap.getPixels(ints, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());
                            LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), ints);
                            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
                            Reader reader = new MultiFormatReader();
                            com.google.zxing.Result res = reader.decode(binaryBitmap);
                            content = res.getText();
//                    _etInput.setText(result);
//                    _ivCode.setImageBitmap(qrCodeBitmap);
                            startActivity(new Intent(ScannerActitvity.this, SalonOrderSamaryActivity.class)
                                    .putExtra(Const.KEY_APPOINTMENT_ID, Integer.parseInt(content)));
                            Toast.makeText(ScannerActitvity.this, "CODE: " + content, Toast.LENGTH_SHORT).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(ScannerActitvity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        } catch (FormatException e) {
                            e.printStackTrace();
                        } catch (ChecksumException e) {
                            e.printStackTrace();
                        } catch (NotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

    );
}
