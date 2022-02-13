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

public class DetailDTKelasActivity extends AppCompatActivity implements View.OnClickListener {
    TextView judul_detail_dtkelas;
    EditText edit_id_detail_kelas, edit_idkls_dtkelas, edit_idpst_dtkelas;
    Button btn_update_dtkelas, btn_del_dtkelas;
    String id_detail_kelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dtkelas);

        judul_detail_dtkelas = findViewById(R.id.judul_detail_dtkelas);
        edit_id_detail_kelas = findViewById(R.id.edit_id_detail_kelas);
        edit_idkls_dtkelas = findViewById(R.id.edit_idkls_dtkelas);
        edit_idpst_dtkelas = findViewById(R.id.edit_idpst_dtkelas);
        btn_update_dtkelas = findViewById(R.id.btn_update_dtkelas);
        btn_del_dtkelas = findViewById(R.id.btn_del_dtkelas);

        btn_update_dtkelas.setOnClickListener(this);
        btn_del_dtkelas.setOnClickListener(this);

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
                loading = ProgressDialog.show(DetailDTKelasActivity.this, "Memuat Data",
                        "Harap Menunggu", false, false);
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL_DTKELAS, id_detail_kelas);
                return hasil;
            }

            @Override
            protected void onPostExecute(String message)
            {
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailDTKelas(message);

            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailDTKelas(String json)
    {
        try
        {
            JSONObject jsonObject =new JSONObject(json);
            JSONArray result =jsonObject.getJSONArray(Konfigurasi.TAG_JSON_DT_KLS_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String id_kls =object.getString(Konfigurasi.TAG_JSON_IDKLS_DTKLS);
            String id_pst = object.getString(Konfigurasi.TAG_JSON_IDPST_DTKLS);

            edit_idkls_dtkelas.setText(id_kls);
            edit_idpst_dtkelas.setText(id_pst);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view)
    {
        if(view == btn_update_dtkelas)
        {
            updateDetailKelas();
        }
        else if(view == btn_del_dtkelas)
        {
            confirmDeleteDetailKelas();
        }
    }

    private void confirmDeleteDetailKelas()
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
            { deleteDetailKelas(); }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDetailKelas()
    {
        class DeleteDetailKelas extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailDTKelasActivity.this, "Menghapus Data",
                        "Harap menunggu...",false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendGetResponse(Konfigurasi.URL_DELETE_INSTRUKTUR,id_detail_kelas);
                return hasil;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                startActivity(new Intent(DetailDTKelasActivity.this, MainActivity.class));
            }
        }
        DeleteDetailKelas deleteDetailKelas = new DeleteDetailKelas();
        deleteDetailKelas.execute();
    }

    private void updateDetailKelas()
    {
        final String id_kls = edit_idkls_dtkelas.getText().toString().trim();
        final String id_pst = edit_idpst_dtkelas.getText().toString().trim();

        class UpdateDataDetailKelas extends AsyncTask <Void,Void,String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailDTKelasActivity.this, "Memperbaharui Data",
                        "Harap tunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_DTKLS_IDKLS, id_kls);
                params.put(Konfigurasi.KEY_DTKLS_IDPST, id_pst);

                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendPostRequest(Konfigurasi.URL_UPDATE_DTKELAS,params);
                return hasil;

            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                startActivity(new Intent(DetailDTKelasActivity.this, MainActivity.class));
            }
        }
        UpdateDataDetailKelas updateDataDetailKelas = new UpdateDataDetailKelas();
        updateDataDetailKelas.execute();
    }
}
