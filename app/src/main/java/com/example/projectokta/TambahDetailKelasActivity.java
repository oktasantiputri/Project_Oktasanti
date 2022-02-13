package com.example.projectokta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

import java.util.HashMap;

public class TambahDetailKelasActivity extends AppCompatActivity implements View.OnClickListener {
    TextView judul_tambah_dtkelas;
    EditText tambah_idkls_dtkelas, tambah_idpst_dtkelas;
    Button btn_simpan_dtkelas, btn_lihat_dtkelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_detail_kelas);

        judul_tambah_dtkelas = findViewById(R.id.judul_tambah_dtkelas);
        tambah_idkls_dtkelas = findViewById(R.id.tambah_idkls_dtkelas);
        tambah_idpst_dtkelas = findViewById(R.id.tambah_idpst_dtkelas);
        btn_simpan_dtkelas = findViewById(R.id.btn_simpan_dtkelas);
        btn_lihat_dtkelas= findViewById(R.id.btn_lihat_dtkelas);

        btn_simpan_dtkelas.setOnClickListener(this);
        btn_lihat_dtkelas.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view == btn_lihat_dtkelas)
        {
            startActivity(new Intent(TambahDetailKelasActivity.this, MainActivity.class));
        }
        else if(view == btn_simpan_dtkelas)
        {
            if(tambah_idkls_dtkelas.getText().toString().length() == 0)
            {
                tambah_idkls_dtkelas.setError("Masukan ID Kelas!");
            }
            else if(tambah_idpst_dtkelas.getText().toString().length() == 0)
            {
                tambah_idpst_dtkelas.setError("Masukan ID Peserta!");
            }
            else
            {
                String id_kls = String.valueOf(tambah_idkls_dtkelas.getText());
                String id_pst = String.valueOf(tambah_idpst_dtkelas.getText());

                AlertDialog.Builder builder = new AlertDialog.Builder(TambahDetailKelasActivity.this);
                builder.setTitle("Data Detail Kelas");
                builder.setMessage("ID Kelas: " + id_kls +
                        "\nID Peserta: " + id_pst);
                builder.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_info));
                builder.setCancelable(false);
                builder.setNegativeButton("Cancel",null);
                builder.setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    { simpanDetailKelas(); }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

                Toast.makeText(TambahDetailKelasActivity.this, "Data Berhasil Ditambahkan!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void simpanDetailKelas()
    {
        final String id_kls = tambah_idkls_dtkelas.getText().toString().trim();
        final String id_pst = tambah_idpst_dtkelas.getText().toString().trim();

        class SimpanDtKelas extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahDetailKelasActivity.this, "Menambah Data",
                        "Harap tunggu", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_DTKLS_IDKLS, id_kls);
                params.put(Konfigurasi.KEY_DTKLS_IDPST, id_pst);
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendPostRequest(Konfigurasi.URL_ADD_DTKELAS, params);
                return hasil;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                clearText();

            }
        }
        SimpanDtKelas simpanDtKelas = new SimpanDtKelas();
        simpanDtKelas.execute();
    }

    private void clearText()
    {
        tambah_idkls_dtkelas.setText("");
        tambah_idpst_dtkelas.setText("");
        tambah_idkls_dtkelas.requestFocus();
    }
}