package com.example.projectokta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class TambahMateriActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView judul_tambah_materi;
    private EditText tambah_nama_materi;
    Button btn_simpan_materi, btn_lihat_materi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_materi);

        judul_tambah_materi = findViewById(R.id.judul_tambah_materi);
        tambah_nama_materi = findViewById(R.id.tambah_nama_materi);
        btn_lihat_materi = findViewById(R.id.btn_lihat_materi);
        btn_simpan_materi = findViewById(R.id.btn_simpan_materi);

        btn_lihat_materi.setOnClickListener(this);
        btn_simpan_materi.setOnClickListener(this);



    }

    @Override
    public void onClick(View view)
    {
        if(view == btn_simpan_materi)
        {
            simpanMateri();
        }
        else if(view == btn_lihat_materi)
        {
            startActivity(new Intent(TambahMateriActivity.this, MainActivity.class));
        }
    }

    private void simpanMateri()
    {
        final String nama_materi = tambah_nama_materi.getText().toString().trim();

        class SimpanMateri extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahMateriActivity.this, "Memasukan Data",
                        "Harap Tunggu",false,false);
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_MAT_NAMA,nama_materi);
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendPostRequest(Konfigurasi.URL_ADD_MATERI, params);
                return hasil;
            }

            @Override
            protected void onPostExecute(String message)
            {
                super.onPostExecute(message);
                loading.dismiss();
                clearTextMat();
            }
        }
        SimpanMateri simpanMateri = new SimpanMateri();
        simpanMateri.execute();
    }

    private void clearTextMat()
    {
        tambah_nama_materi.setText("");
        tambah_nama_materi.requestFocus() ;
    }
}