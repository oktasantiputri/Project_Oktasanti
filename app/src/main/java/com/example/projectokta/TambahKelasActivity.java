package com.example.projectokta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class TambahKelasActivity extends AppCompatActivity implements View.OnClickListener {
    TextView judul_tambah_kelas;
    EditText tambah_mulai_kls, tambah_akhir_kls, tambah_id_ins_kls, tambah_id_mat_kls;
    Button btn_simpan_kls, btn_lihat_kls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kelas);

        judul_tambah_kelas = findViewById(R.id.judul_tambah_Kelas);
        tambah_mulai_kls = findViewById(R.id.tambah_mulai_kls);
        tambah_akhir_kls = findViewById(R.id.tambah_akhir_kls);
        tambah_id_ins_kls = findViewById(R.id.tambah_id_ins_kls);
        tambah_id_mat_kls = findViewById(R.id.tambah_id_mat_kls);
        btn_simpan_kls = findViewById(R.id.btn_simpan_kls);
        btn_lihat_kls = findViewById(R.id.btn_lihat_kls);

        btn_simpan_kls.setOnClickListener(this);
        btn_lihat_kls.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        if(view == btn_lihat_kls)
        {
            startActivity(new Intent(TambahKelasActivity.this, MainActivity.class));
        }
        else if(view == btn_simpan_kls)
        {
            if(tambah_mulai_kls.getText().toString().length() == 0)
            {
                tambah_mulai_kls.setError("Masukan Tanggal Mulai Kelas!");
            }
            else if(tambah_akhir_kls.getText().toString().length() == 0)
            {
                tambah_akhir_kls.setError("Masukan Tanggal Akhir Kelas!");
            }
            else if(tambah_id_ins_kls.getText().toString().length() == 0)
            {
                tambah_id_ins_kls.setError("ID Instruktur diperlukan!");
            }
            else if(tambah_id_mat_kls.getText().toString().length() == 0)
            {
                tambah_id_mat_kls.setError("ID Materi diperlukan!");
            }
            else
            {
                String mulai_kls = String.valueOf(tambah_akhir_kls.getText());
                String akhir_kls = String.valueOf(tambah_akhir_kls.getText());
                String id_ins = String.valueOf(tambah_id_ins_kls.getText());
                String id_mat = String.valueOf(tambah_id_mat_kls.getText());

                AlertDialog.Builder builder = new AlertDialog.Builder(TambahKelasActivity.this);
                builder.setTitle("Data Kelas");
                builder.setMessage("Tanggal Mulai Kelas: " + mulai_kls +
                        "\nTanggal Akhir Kelas: " + akhir_kls +
                        "\nID Instruktur: " + id_ins +
                        "\nID MAteri: " + id_mat);
                builder.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_info));
                builder.setCancelable(false);
                builder.setNegativeButton("Cancel",null);
                builder.setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                { simpanKelas(); }
            });
                AlertDialog dialog = builder.create();
                dialog.show();

                Toast.makeText(TambahKelasActivity.this, "Data Berhasil Ditambahkan!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void simpanKelas()
    {
        final String tgl_mulai_kls = tambah_mulai_kls.getText().toString().trim();
        final String tgl_akhir_kls = tambah_akhir_kls.getText().toString().trim();
        final String id_ins = tambah_id_ins_kls.getText().toString().trim();
        final String id_mat = tambah_id_mat_kls.getText().toString().trim();

        class SimpanKelas extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahKelasActivity.this, "Menambah Data",
                        "Harap tunggu", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_KLS_MULAI, tgl_mulai_kls);
                params.put(Konfigurasi.KEY_KLS_AKHIR, tgl_akhir_kls);
                params.put(Konfigurasi.KEY_ID_MAT_KLS, id_mat);
                params.put(Konfigurasi.KEY_ID_INS_KLS,id_ins);
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendPostRequest(Konfigurasi.URL_ADD_KELAS, params);
                return hasil;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                clearText();

            }
        }
        SimpanKelas simpanKelas = new SimpanKelas();
        simpanKelas.execute();
    }

    private void clearText()
    {
        tambah_mulai_kls.setText("");
        tambah_akhir_kls.setText("");
        tambah_id_mat_kls.setText("");
        tambah_id_ins_kls.setText("");
        tambah_mulai_kls.requestFocus();
    }
}