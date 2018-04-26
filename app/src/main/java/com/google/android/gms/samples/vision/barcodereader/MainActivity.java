/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.gms.samples.vision.barcodereader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * Main activity demonstrating how to pass extra parameters to an activity that
 * reads barcodes.
 */




public class MainActivity extends Activity implements View.OnClickListener {

//    Intent test = getIntent();
//Main.javaとMainActivity.javaのどちらに書けば良い?
//uriに「hoge-scheme://hoge-host?id=hogehogehoge」が入る
//    Uri uri = test.getData();

//    Uri uri = Uri.parse("hoge-scheme://153.126.195.96");
    Uri uri = getIntent().getData();
//パラメータで指定したhogehogehogeが取得できる
    public String order_no = uri.getQueryParameter("order_no");
    public Integer dvendor_id;



    // use a compound button so either checkbox or switch widgets work.
    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private TextView barcodeValue;

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";

    @Override
    //activity_main.xmlの画面描写のための変数宣言
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner6);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dvendorList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

//        dvendor_id = 0;
        Log.d("dvendor_idの値確認0", String.valueOf(dvendor_id));
//        Log.d("order_noの確認", uri.getQueryParameter("order_no"));
        //イベ
        // ントリスナー
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){

                dvendor_id = position;
                Log.d("dvendor_idの値確認1", String.valueOf(dvendor_id));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });


        statusMessage = (TextView)findViewById(R.id.status_message);
        barcodeValue = (TextView)findViewById(R.id.barcode_value);

        //初期値はfalse(activity_main.xmlのandroid:id="@+id/auto_focus")
        autoFocus = (CompoundButton) findViewById(R.id.auto_focus);

        //初期値はfalse(activity_main.xmlのandroid:id="@+id/use_flash")
        useFlash = (CompoundButton) findViewById(R.id.use_flash);

        findViewById(R.id.read_barcode).setOnClickListener(this);


    }




    /**
     * activity_main.xmlでボタンが押された時の各処理
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */

    //activity_main.xmlのボタン処理（各ボタンごと）
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_barcode) {
            Log.d("dvendor_idの値確認2", String.valueOf(dvendor_id));
            //（BarcodeCaptureActivity.javaへ移行する）launch barcode activity.
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            //オートフォーカス設定のボタン処理値をBarcodeCaptureActivity.javaへ移行する
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus.isChecked());
            //フラッシュ設定のボタン処理値をBarcodeCaptureActivity.javaへ移行する
            intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());
            //注文番号の値をBarcodeCaptureActivity.javaへ移行する
            intent.putExtra("order_no", order_no);
//            Log.d("order_noの確認", order_no);
            Log.d("dvendor_idの値確認3", String.valueOf(dvendor_id));
            //業者番号の値をBarcodeCaptureActivity.javaへ移行する
            intent.putExtra("dvendor_id", dvendor_id);
            Log.d("dvendor_idの値確認4", String.valueOf(dvendor_id));
            //
            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }

    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    /**
     *BarcodeCaptureActivity.javaで得た値を表示する
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    statusMessage.setText(R.string.barcode_success);
                    barcodeValue.setText(barcode.displayValue);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
