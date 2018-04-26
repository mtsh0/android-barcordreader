package jp.ac.hal.nhs50391.aaa1120;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.samples.vision.barcodereader.R;

public class Main extends AppCompatActivity {


    public String order_no;
    public Integer dvendor_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//Main.javaとMainActivity.javaのどちらに書けば良い?
        // uriに「hoge-scheme://hoge-host?id=hogehogehoge」が入る
        //    Uri uri = activity.getIntent().getData();
//        Uri uri = Uri.parse("hoge-scheme://153.126.195.96");
        // パラメータで指定したhogehogehogeが取得できる
//        String order_no = uri.getQueryParameter("order_no");

//        Spinner spinner = (Spinner) findViewById(R.id.spinner6);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dvendorList, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
    }



}
