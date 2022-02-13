package com.example.projectokta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class KelasFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    FloatingActionButton btn_tambah_kelas;
    ListView listViewKelas;
    String JSON_STRING;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_kelas, container, false);

        listViewKelas = view.findViewById(R.id.listViewKelas);
        btn_tambah_kelas = view.findViewById(R.id.btn_tambah_kelas);

        listViewKelas.setOnItemClickListener(this);
        btn_tambah_kelas.setOnClickListener(this);
        
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
                loading = ProgressDialog.show(getActivity(), "Mengambil Data",
                        "Harap tunggu", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_KELAS);
                return hasil;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("Data JSON: ", JSON_STRING);
                displayDataKelas();

            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDataKelas()
    {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try
        {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_KLS_ARRAY);
            Log.d("DATA_JSON: ", JSON_STRING);

            for (int i = 0; i < result.length(); i++)
            {
                JSONObject object = result.getJSONObject(i);
                String id_kls = object.getString(Konfigurasi.TAG_JSON_ID_KLS);
                String tgl_mulai_kls = object.getString(Konfigurasi.TAG_JSON_MULAI_KLS);
                String tgl_akhir_kls = object.getString(Konfigurasi.TAG_JSON_AKHIR_KLS);
                String id_ins = object.getString(Konfigurasi.TAG_JSON_ID_INS_KLS);
                String id_mat = object.getString(Konfigurasi.TAG_JSON_ID_MAT_KLS);

                HashMap<String, String> kelas = new HashMap<>();
                kelas.put(Konfigurasi.TAG_JSON_ID_KLS, id_kls);
                kelas.put(Konfigurasi.TAG_JSON_MULAI_KLS, tgl_mulai_kls);
                kelas.put(Konfigurasi.TAG_JSON_AKHIR_KLS, tgl_akhir_kls);
                kelas.put(Konfigurasi.TAG_JSON_ID_INS_KLS, id_ins);
                kelas.put(Konfigurasi.TAG_JSON_ID_MAT_KLS, id_mat);
                list.add(kelas);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        ListAdapter adapterKls = new SimpleAdapter(getActivity(), list, R.layout.list_detail_kelas,
                new String[]{Konfigurasi.TAG_JSON_ID_KLS, Konfigurasi.TAG_JSON_MULAI_KLS,
                        Konfigurasi.TAG_JSON_AKHIR_KLS},
                new int[]{R.id.txt_dis_id_kls, R.id.txt_dis_mulai_kls, R.id.txt_dis_akhir_kls});
        listViewKelas.setAdapter(adapterKls);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = new Intent(getActivity(), DetailKelasActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String klsId = map.get(Konfigurasi.TAG_JSON_ID_KLS).toString();
        intent.putExtra(Konfigurasi.KLS_ID, klsId);
        startActivity(intent);
    }

    @Override
    public void onClick(View view)
    {
        startActivity(new Intent(view.getContext(), TambahKelasActivity.class));
    }
}