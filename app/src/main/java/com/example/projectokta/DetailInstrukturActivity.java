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

public class DetailInstrukturActivity extends AppCompatActivity implements View.OnClickListener {
    TextView judul_detail_instruktur;
    EditText txt_det_id_ins, txt_det_nama_ins, txt_det_email_ins, txt_det_hp_ins;
    Button btn_update_ins, btn_del_ins;
    String id_ins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_instruktur);

        judul_detail_instruktur = findViewById(R.id.judul_det_instruktur);
        txt_det_id_ins = findViewById(R.id.txt_det_id_ins);
        txt_det_nama_ins = findViewById(R.id.txt_det_nama_ins);
        txt_det_email_ins = findViewById(R.id.txt_det_email_ins);
        txt_det_hp_ins = findViewById(R.id.txt_det_hp_ins);
        btn_update_ins = findViewById(R.id.btn_update_ins);
        btn_del_ins = findViewById(R.id.btn_del_ins);

        btn_update_ins.setOnClickListener(this);
        btn_del_ins.setOnClickListener(this);

        Intent terimaIntent = getIntent();
        id_ins = terimaIntent.getStringExtra(Konfigurasi.INS_ID);
        txt_det_id_ins.setText(id_ins);

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
                loading = ProgressDialog.show(DetailInstrukturActivity.this, "Memuat Data",
                        "Harap menunggu", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL_INSTRUKTUR, id_ins);
                return hasil;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                displayDataInstruktur(message);
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDataInstruktur(String json)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_INS_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String nama_ins = object.getString(Konfigurasi.TAG_JSON_NAMA_INS);
            String email_ins = object.getString(Konfigurasi.TAG_JSON_EMAIL_INS);
            String hp_ins = object.getString(Konfigurasi.TAG_JSON_HP_INS);

            txt_det_nama_ins.setText(nama_ins);
            txt_det_email_ins.setText(email_ins);
            txt_det_hp_ins.setText(hp_ins);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view)
    {
        if(view == btn_update_ins)
        {
            updateDataInstruktur();
        }
        else if(view == btn_del_ins)
        {
            confirmDeleteDataIntruktur();
        }
    }

    private void confirmDeleteDataIntruktur()
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
            { deleteDataInstruktur(); }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataInstruktur()
    {
        class DeleteDataInstruktur extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailInstrukturActivity.this, "Menghapus Data",
                        "Harap tunggu",false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendGetResponse(Konfigurasi.URL_DELETE_INSTRUKTUR, id_ins);
                return hasil;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                startActivity(new Intent(DetailInstrukturActivity.this,MainActivity.class));
            }
        }
        DeleteDataInstruktur deleteDataInstruktur = new DeleteDataInstruktur();
        deleteDataInstruktur.execute();
    }

    private void updateDataInstruktur()
    {
        final String nama_ins = txt_det_nama_ins.getText().toString().trim();
        final String email_ins = txt_det_email_ins.getText().toString().trim();
        final String hp_ins = txt_det_hp_ins.getText().toString().trim();

        class UpdateDatainstruktur extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailInstrukturActivity.this, "Memperbaharui Data",
                        "Harap tunggu",false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_INS_ID, id_ins);
                params.put(Konfigurasi.KEY_INS_NAMA, nama_ins);
                params.put(Konfigurasi.KEY_INS_EMAIL, email_ins);
                params.put(Konfigurasi.KEY_INS_HP, hp_ins);
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendPostRequest(Konfigurasi.URL_UPDATE_INSTRUKTUR, params);
                return hasil;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                startActivity(new Intent(DetailInstrukturActivity.this, MainActivity.class));
            }
        }
        UpdateDatainstruktur updateDatainstruktur = new UpdateDatainstruktur();
        updateDatainstruktur.execute();
    }
}