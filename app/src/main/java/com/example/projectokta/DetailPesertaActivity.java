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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailPesertaActivity extends AppCompatActivity implements View.OnClickListener {
    EditText txt_det_id_peserta, txt_det_nama_peserta, txt_det_email_peserta,
    txt_det_hp_peserta, txt_det_instansi_peserta;
    Button btn_update_peserta, btn_del_peserta;
    String id_pst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_peserta);
        
        txt_det_id_peserta = findViewById(R.id.txt_det_id_peserta);
        txt_det_nama_peserta = findViewById(R.id.txt_det_nama_peserta);
        txt_det_email_peserta = findViewById(R.id.txt_det_email_peserta);
        txt_det_hp_peserta = findViewById(R.id.txt_det_hp_peserta);
        txt_det_instansi_peserta = findViewById(R.id.txt_det_instansi_peserta);
        btn_update_peserta = findViewById(R.id.btn_update_peserta);
        btn_del_peserta = findViewById(R.id.btn_del_peserta);
        
        Intent terimaIntent = getIntent();
        id_pst = terimaIntent.getStringExtra(Konfigurasi.PST_ID);
        txt_det_id_peserta.setText(id_pst);
        
        btn_update_peserta.setOnClickListener(this);
        btn_del_peserta.setOnClickListener(this);
        
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
                loading = ProgressDialog.show(DetailPesertaActivity.this, "Memuat Data",
                        "Harap Menunggu", false, false);
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL_PESERTA, id_pst);
                return hasil;
            }

            @Override
            protected void onPostExecute(String message)
            {
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailData(message);
                
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailData(String json) 
    {
        try
        {
            JSONObject jsonObject =new JSONObject(json);
            JSONArray result =jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String nama_pst =object.getString(Konfigurasi.TAG_JSON_NAMA_PST);
            String email_pst = object.getString(Konfigurasi.TAG_JSON_EMAIL_PST);
            String hp_pst = object.getString(Konfigurasi.TAG_JSON_HP_PST);
            String ins_pst = object.getString(Konfigurasi.TAG_JSON_INS_PST);

            txt_det_nama_peserta.setText(nama_pst);
            txt_det_email_peserta.setText(email_pst);
            txt_det_hp_peserta.setText(hp_pst);
            txt_det_instansi_peserta.setText(ins_pst);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) 
    {
        if(view == btn_update_peserta)
        {
            updateDataPeserta();
        }
        else if(view == btn_del_peserta)
        {
            confirmDeletePeserta();
        }
    }

    private void confirmDeletePeserta()
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
            { deleteDataPeserta(); }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataPeserta()
    {
        class DeleteDataPeserta extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailPesertaActivity.this, "Menghapus Data",
                        "Harap menunggu...",false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendGetResponse(Konfigurasi.URL_DELETE_PESERTA,id_pst);
                return hasil;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                startActivity(new Intent(DetailPesertaActivity.this, MainActivity.class));
            }
        }
        DeleteDataPeserta deleteDataPeserta = new DeleteDataPeserta();
        deleteDataPeserta.execute();
    }

    private void updateDataPeserta()
    {
        final String nama_pst = txt_det_nama_peserta.getText().toString().trim();
        final String email_pst = txt_det_email_peserta.getText().toString().trim();
        final String hp_pst = txt_det_hp_peserta.getText().toString().trim();
        final String ins_pst = txt_det_instansi_peserta.getText().toString().trim();

        class UpdateDataPeserta extends AsyncTask <Void,Void,String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailPesertaActivity.this, "Memperbaharui Data",
                        "Harap tunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_PST_ID, id_pst);
                params.put(Konfigurasi.KEY_PST_NAMA, nama_pst);
                params.put(Konfigurasi.KEY_PST_EMAIL, email_pst);
                params.put(Konfigurasi.KEY_PST_HP, hp_pst);
                params.put(Konfigurasi.KEY_PST_INSTANSI, ins_pst);

                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendPostRequest(Konfigurasi.URL_UPDATE_PESERTA,params);
                return hasil;

            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                startActivity(new Intent(DetailPesertaActivity.this, MainActivity.class));
            }
        }
        UpdateDataPeserta updateDataPeserta = new UpdateDataPeserta();
        updateDataPeserta.execute();
    }
}