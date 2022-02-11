package com.example.projectokta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class PesertaFragment extends Fragment
        implements AdapterView.OnItemClickListener, View.OnClickListener {
    ListView list_peserta;
    FloatingActionButton btn_tambah_peserta;
    private String JSON_STRING;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_peserta, container, false);

         list_peserta = view.findViewById(R.id.listViewPeserta);
         btn_tambah_peserta = view.findViewById(R.id.btn_tambah_peserta);

        list_peserta.setOnItemClickListener(this);
        btn_tambah_peserta.setOnClickListener(this);

        getJSON();
        return view;
    }

    private void getJSON()
    {
        class GetJSON extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Memuat Data",
                        "Harap tunggu",false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_PESERTA);
                return hasil;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                Log.d("Data JSON: ", JSON_STRING);
                displayPeserta();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayPeserta()
    {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> listpst = new ArrayList<>();

        try
        {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result =jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            Log.d("Data JSON: ", JSON_STRING);

            for(int i = 0; i <result.length(); i++)
            {
                JSONObject objectpst =result.getJSONObject(i);
                String id_pst = objectpst.getString(Konfigurasi.TAG_JSON_ID_PST);
                String nama_pst = objectpst.getString(Konfigurasi.TAG_JSON_NAMA_PST);

                HashMap<String, String> peserta =new HashMap<>();
                peserta.put(Konfigurasi.TAG_JSON_ID_PST, id_pst);
                peserta.put(Konfigurasi.TAG_JSON_NAMA_PST, nama_pst);
                // ubah format json menjadi array list
                listpst.add(peserta);
            }
        }
        catch (Exception e)
        {e.printStackTrace();}

        ListAdapter listAdapterPeserta = new SimpleAdapter(getActivity(), listpst, R.layout.list_detail_peserta,
                new String[]{Konfigurasi.TAG_JSON_ID_PST, Konfigurasi.TAG_JSON_NAMA_PST},
                new int[]{R.id.txt_dis_id_peserta, R.id.txt_dis_nama_peserta});
        list_peserta.setAdapter(listAdapterPeserta);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = new Intent(getActivity(), DetailPesertaActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String pstId = map.get(Konfigurasi.TAG_JSON_ID_PST).toString();
        intent.putExtra(Konfigurasi.PST_ID, pstId);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(view.getContext(), TambahPesertaActivity.class));

    }
}