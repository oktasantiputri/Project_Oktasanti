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
import android.widget.Toast;

import java.util.HashMap;

public class TambahPesertaActivity extends AppCompatActivity implements View.OnClickListener {
    EditText tambah_nama_peserta, tambah_email_peserta,tambah_hp_peserta, tambah_instansi_peserta;
    Button btn_simpan_peserta, btn_lihat_peserta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_peserta);

        tambah_nama_peserta = findViewById(R.id.tambah_nama_peserta);
        tambah_email_peserta = findViewById(R.id.tambah_email_peserta);
        tambah_hp_peserta = findViewById(R.id.tambah_hp_peserta);
        tambah_instansi_peserta = findViewById(R.id.tambah_instansi_peserta);
        btn_lihat_peserta = findViewById(R.id.btn_lihat_peserta);
        btn_simpan_peserta = findViewById(R.id.btn_simpan_peserta);

        btn_simpan_peserta.setOnClickListener(this);
        btn_lihat_peserta.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view == btn_lihat_peserta)
        {
            startActivity(new Intent(TambahPesertaActivity.this, MainActivity.class));
        }
        else if(view == btn_simpan_peserta)
        {
            if(tambah_nama_peserta.getText().toString().length() == 0)
            {
                tambah_nama_peserta.setError("Masukan Nama Peserta!");
            }
            else if(tambah_email_peserta.getText().toString().length() == 0)
            {
                tambah_email_peserta.setError("Mauskan E-mail Peserta!");
            }
            else if(tambah_hp_peserta.getText().toString().length() == 0)
            {
                tambah_hp_peserta.setError("Masukan Nomor HP Peserta!");
            }
            else if(tambah_instansi_peserta.getText().toString().length() == 0)
            {
                tambah_instansi_peserta.setError("Masukan Asal Instansi Peserta");
            }
            else
            {
                String nama_pst = String.valueOf(tambah_nama_peserta.getText());
                String email_pst = String.valueOf(tambah_email_peserta.getText());
                String hp_pst = String.valueOf(tambah_hp_peserta.getText());
                String instansi_pst = String.valueOf(tambah_instansi_peserta.getText());

                AlertDialog.Builder builder = new AlertDialog.Builder(TambahPesertaActivity.this);
                builder.setTitle("Data Peserta");
                builder.setMessage("Nama Peserta: " + nama_pst +
                        "\nE-mail Peserta: " + email_pst +
                        "\nNo.HP Peserta: " + hp_pst +
                        "\nAsal Instansi: " + instansi_pst);
                builder.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_info));
                builder.setCancelable(false);
                builder.setNegativeButton("Cancel",null);
                builder.setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    { simpanPeserta(); }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

                Toast.makeText(TambahPesertaActivity.this, "Data Berhasil Ditambahkan!",
                        Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void simpanPeserta()
    {
        final String nama_peserta = tambah_nama_peserta.getText().toString().trim();
        final String email_peserta = tambah_email_peserta.getText().toString().trim();
        final String hp_peserta = tambah_hp_peserta.getText().toString().trim();
        final String instansi_peserta = tambah_instansi_peserta.getText().toString().trim();

        class SimpanPeserta extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahPesertaActivity.this, "Menambah Data",
                        "Harap tunggu", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_PST_NAMA, nama_peserta);
                params.put(Konfigurasi.KEY_PST_EMAIL, email_peserta);
                params.put(Konfigurasi.KEY_PST_HP, hp_peserta);
                params.put(Konfigurasi.KEY_PST_INSTANSI,instansi_peserta);
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendPostRequest(Konfigurasi.URL_ADD_PESERTA, params);
                return hasil;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                clearText();

            }
        }
        SimpanPeserta simpanPeserta = new SimpanPeserta();
        simpanPeserta.execute();
    }

    private void clearText()
    {
        tambah_nama_peserta.setText("");
        tambah_email_peserta.setText("");
        tambah_hp_peserta.setText("");
        tambah_instansi_peserta.setText("");
        tambah_nama_peserta.requestFocus();
    }
}