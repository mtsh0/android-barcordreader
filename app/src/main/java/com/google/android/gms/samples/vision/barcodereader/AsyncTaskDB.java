package com.google.android.gms.samples.vision.barcodereader;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by t41r on 2018/02/05.
 */

public class AsyncTaskDB extends AsyncTask<String , Void, Void> {

    private Activity activity;


    private Integer dvendor_id;
    private String best;
    private String order_no;

    public AsyncTaskDB(Activity act){
//        activity = act;
//        dvendor_id = null;
//        best ="";
//        order_no = "";

    }


    protected Void doInBackground(String... params){


        java.sql.Connection con =null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://faaabbbbcccc.biz/sk31_saishuu_development", "root", "lolo1212");

            stmt = con.createStatement();

//            String strSQL = "";
//            strSQL += "update orders set dvendor_id =" + dvendor_id + ", d_number ='" + best + "' where order_no = " + order_no + "'";

            rs = stmt.executeQuery(params[0]);

        }catch (ClassNotFoundException e){
            Log.e("mysql", "ドライバがない");
        }catch (SQLException e){
            Log.e("mysql", "SQL異常" + e.getMessage());
        }finally {
            try{
                if (rs != null){
                    rs.close();
                }

                if (stmt != null){
                    stmt.close();
                }

                if (con != null){
                    con.close();
                }


            }catch (SQLException e){
                Log.e("mysql", "closeでエラー");
            }
        }


        return null;
    }
}
