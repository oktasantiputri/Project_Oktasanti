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

public class TambahInstrukturActivity extends AppCompatActivity implements View.OnClickListener {
    TextView judul_tambah_Instruktur;
    EditText txt_tambah_nama_instruktur, txt_tambah_email_instruktur, txt_tambah_hp_instruktur;
    Button btn_simpan_instruktur, btn_lihat_instruktur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_instruktur);

        judul_tambah_Instruktur = findViewById(R.id.judul_tambah_instruktur);
        txt_tambah_nama_instruktur = findViewById(R.id.txt_tambah_nama_instruktur);
        txt_tambah_email_instruktur = findViewById(R.id.txt_tambah_email_instruktur);
        txt_tambah_hp_instruktur = findViewById(R.id.txt_tambah_hp_instruktur);
        btn_simpan_instruktur = findViewById(R.id.btn_simpan_instruktur);
        btn_lihat_instruktur = findViewById(R.id.btn_lihat_instruktur);

        btn_simpan_instruktur.setOnClickListener(this);
        btn_lihat_instruktur.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view == btn_simpan_instruktur)
        {
            if(txt_tambah_nama_instruktur.getText().toString().length() == 0)
            {
                txt_tambah_nama_instruktur.setError("Masukan Nama Instruktur!");
            }
            else if(txt_tambah_email_instruktur.getText().toString().length() == 0)
            {
                txt_tambah_email_instruktur.setError("Mauskan E-mail Instruktur!");
            }
            else if(txt_tambah_hp_instruktur.getText().toString().length() == 0)
            {
                txt_tambah_hp_instruktur.setError("Masukan Nomor HP Instruktur!");
            }
            else
            {
                String nama_ins = String.valueOf(txt_tambah_nama_instruktur.getText());
                String email_ins = String.valueOf(txt_tambah_email_instruktur.getText());
                String hp_ins = String.valueOf(txt_tambah_hp_instruktur.getText());

                AlertDialog.Builder builder = new AlertDialog.Builder(TambahInstrukturActivity.this);
                builder.setTitle("Data Instruktur");
                builder.setMessage("Nama Instruktur: " + nama_ins +
                        "\nE-mail Instruktur: " + email_ins +
                        "\nNo.HP Instruktur: " + hp_ins);
                builder.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_info));
                builder.setCancelable(false);
                builder.setNegativeButton("Cancel",null);
                builder.setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    { simpanDataInstruktur(); }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

                Toast.makeText(TambahInstrukturActivity.this, "Data Berhasil Ditambahkan!",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else if (view == btn_lihat_instruktur)
        {
            startActivity(new Intent(TambahInstrukturActivity.this,
                    MainActivity.class));
        }
    }

    private void simpanDataInstruktur()
    {
        final String nama_ins = txt_tambah_nama_instruktur.getText().toString().trim();
        final String email_ins = txt_tambah_email_instruktur.getText().toString().trim();
        final String hp_ins = txt_tambah_hp_instruktur.getText().toString().trim();

        class SimpanDataInstruktur extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahInstrukturActivity.this, "Memasukan Data",
                        "Harap Tunggu",false,false);
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_INS_NAMA,nama_ins);
                params.put(Konfigurasi.KEY_INS_EMAIL,email_ins);
                params.put(Konfigurasi.KEY_INS_HP,hp_ins);
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendPostRequest(Konfigurasi.URL_ADD_INSTRUKTUR, params);
                return hasil;
            }

            @Override
            protected void onPostExecute(String message)
            {
                super.onPostExecute(message);
                loading.dismiss();
                clearTextIns();
            }
        }
        SimpanDataInstruktur simpanDataInstruktur = new SimpanDataInstruktur();
        simpanDataInstruktur.execute();
    }

    private void clearTextIns()
    {
        txt_tambah_nama_instruktur.setText("");
        txt_tambah_email_instruktur.setText("");
        txt_tambah_hp_instruktur.setText("");
        txt_tambah_nama_instruktur.requestFocus();
    }
}