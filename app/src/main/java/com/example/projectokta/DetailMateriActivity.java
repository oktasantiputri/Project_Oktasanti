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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailMateriActivity extends AppCompatActivity implements View.OnClickListener {
    TextView judul_det_materi;
    EditText txt_det_id_materi, txt_det_nama_materi;
    Button btn_update_materi, btn_del_materi;
    String id_mat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_materi);

        judul_det_materi = findViewById(R.id.judul_det_materi);
        txt_det_id_materi = findViewById(R.id.txt_det_id_materi);
        txt_det_nama_materi = findViewById(R.id.txt_det_nama_materi);
        btn_update_materi = findViewById(R.id.btn_update_materi);
        btn_del_materi = findViewById(R.id.btn_del_materi);

        Intent terimaIntentMat = getIntent();
        id_mat = terimaIntentMat.getStringExtra(Konfigurasi.MAT_ID);
        txt_det_id_materi.setText(id_mat);


        btn_update_materi.setOnClickListener(this);
        btn_del_materi.setOnClickListener(this);

        getJSON();
    }

    private void getJSON()
    {
        class GetJSON extends AsyncTask<Void,Void, String>
        {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailMateriActivity.this, "Memuat Data",
                        "Harap menunggu", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL_MATERI, id_mat);
                return hasil;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailMateri(message);


            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailMateri(String json)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_MAT_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String nama_mat = object.getString(Konfigurasi.TAG_JSON_NAMA_MAT);
            txt_det_nama_materi.setText(nama_mat);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view)
    {
        if(view == btn_update_materi)
        {
            updateMateri();
        }
        else if(view == btn_del_materi)
        {
            confirmDeleteMateri();
        }
    }

    private void confirmDeleteMateri()
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
            { deleteMateri(); }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteMateri()
    {
        class DeleteMateri extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailMateriActivity.this, "Menghapus Data",
                        "Harap tunggu",false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendGetResponse(Konfigurasi.URL_DELETE_MATERI, id_mat);
                return hasil;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                startActivity(new Intent(DetailMateriActivity.this,MainActivity.class));
            }
        }
        DeleteMateri deleteMateri = new DeleteMateri();
        deleteMateri.execute();
    }

    private void updateMateri()
    {
        final String nama_mat = txt_det_nama_materi.getText().toString().trim();

        class UpdateMateri extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailMateriActivity.this, "Memperbaharui Data",
                        "Harap tunggu",false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_MAT_ID, id_mat);
                params.put(Konfigurasi.KEY_MAT_NAMA, nama_mat);
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendPostRequest(Konfigurasi.URL_UPDATE_MATERI, params);
                return hasil;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                startActivity(new Intent(DetailMateriActivity.this, MainActivity.class));
            }
        }
        UpdateMateri updateMateri = new UpdateMateri();
        updateMateri.execute();
    }
}