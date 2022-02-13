package com.example.projectokta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailKelasActivity extends AppCompatActivity implements View.OnClickListener {
    EditText txt_det_id_kls, txt_det_mulai_kelas, txt_det_akhir_kelas,
    txt_det_id_ins_kls, txt_det_id_mat_kls;
    TextView judul_det_kelas;
    Button btn_update_kls, btn_del_kls;
    String id_kls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kelas);

        judul_det_kelas = findViewById(R.id.judul_detail_kelas);
        txt_det_id_kls = findViewById(R.id.txt_det_id_kls);
        txt_det_mulai_kelas = findViewById(R.id.txt_det_mulai_kls);
        txt_det_akhir_kelas = findViewById(R.id.txt_det_akhir_kelas);
        txt_det_id_ins_kls = findViewById(R.id.txt_det_id_ins_kls);
        txt_det_id_mat_kls = findViewById(R.id.txt_det_id_mat_kls);
        btn_update_kls = findViewById(R.id.btn_update_kls);
        btn_del_kls = findViewById(R.id.btn_del_kls);

        Intent terimaIntent = getIntent();
        id_kls = terimaIntent.getStringExtra(Konfigurasi.KLS_ID);
        txt_det_id_kls.setText(id_kls);

        btn_update_kls.setOnClickListener(this);
        btn_del_kls.setOnClickListener(this);

        getJSON();
    }

    private void getJSON()
    {
        class GetJSON extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasActivity.this, "Memuat Data",
                        "Harap Menunggu", false, false);
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL_KELAS, id_kls);
                return hasil;
            }

            @Override
            protected void onPostExecute(String message)
            {
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailKelas(message);

            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailKelas(String json)
    {
        try
        {
            JSONObject jsonObject =new JSONObject(json);
            JSONArray result =jsonObject.getJSONArray(Konfigurasi.TAG_JSON_KLS_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String tgl_mulai_kls =object.getString(Konfigurasi.TAG_JSON_MULAI_KLS);
            String tgl_akhir_kls = object.getString(Konfigurasi.TAG_JSON_AKHIR_KLS);
            String id_ins = object.getString(Konfigurasi.TAG_JSON_ID_INS_KLS);
            String id_mat = object.getString(Konfigurasi.TAG_JSON_ID_MAT_KLS);

            txt_det_mulai_kelas.setText(tgl_mulai_kls);
            txt_det_akhir_kelas.setText(tgl_akhir_kls);
            txt_det_id_ins_kls.setText(id_ins);
            txt_det_id_mat_kls.setText(id_mat);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) 
    {
        if(view == btn_update_kls)
        {
            updateKelas();
        }
        else if(view == btn_del_kls)
        {
            confirmDeleteKelas();
        }
    }

    private void confirmDeleteKelas()
    {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("Menghapus Data");
        builder.setMessage("Apakah anda yakin menghapus data?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            { deleteDataKelas(); }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataKelas()
    {
        class DeleteDataKelas extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasActivity.this, "Menghapus Data",
                        "Harap menunggu...",false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendGetResponse(Konfigurasi.URL_DELETE_KELAS,id_kls);
                return hasil;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                startActivity(new Intent(DetailKelasActivity.this, MainActivity.class));
            }
        }
        DeleteDataKelas deleteDataKelas = new DeleteDataKelas();
        deleteDataKelas.execute();
    }

    private void updateKelas()
    {
        final String tgl_mulai_kls = txt_det_mulai_kelas.getText().toString().trim();
        final String tgl_akhir_kls = txt_det_akhir_kelas.getText().toString().trim();
        final String id_ins = txt_det_id_ins_kls.getText().toString().trim();
        final String id_mat = txt_det_id_mat_kls.getText().toString().trim();

        class UpdateDataKelas extends AsyncTask <Void,Void,String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasActivity.this, "Memperbaharui Data",
                        "Harap tunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_KLS_ID, id_kls);
                params.put(Konfigurasi.KEY_KLS_MULAI, tgl_mulai_kls);
                params.put(Konfigurasi.KEY_KLS_AKHIR, tgl_akhir_kls);
                params.put(Konfigurasi.KEY_ID_INS_KLS, id_ins);
                params.put(Konfigurasi.KEY_ID_MAT_KLS, id_mat);

                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendPostRequest(Konfigurasi.URL_UPDATE_KELAS,params);
                return hasil;

            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                startActivity(new Intent(DetailKelasActivity.this, MainActivity.class));
            }
        }
        UpdateDataKelas updateDataKelas = new UpdateDataKelas();
        updateDataKelas.execute();
    }
}