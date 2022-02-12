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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MateriFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private FloatingActionButton btn_tambah_materi;
    private ListView listViewMateri;
    private String JSON_STRING;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_materi, container, false);

        btn_tambah_materi = view.findViewById(R.id.btn_tambah_materi);
        listViewMateri = view.findViewById(R.id.listViewMateri);
        listViewMateri.setOnItemClickListener(this);
        btn_tambah_materi.setOnClickListener(this);

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
                String hasil = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_MATERI);
                return hasil;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("Data JSON: ", JSON_STRING);

                displayDataMateri();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDataMateri()
    {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try
        {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_MAT_ARRAY);
            Log.d("DATA_JSON: ", JSON_STRING);

            for (int i = 0; i < result.length(); i++)
            {
                JSONObject object = result.getJSONObject(i);
                String id_mat = object.getString(Konfigurasi.TAG_JSON_ID_MAT);
                String nama_mat = object.getString(Konfigurasi.TAG_JSON_NAMA_MAT);

                HashMap<String, String> instruktur = new HashMap<>();
                instruktur.put(Konfigurasi.TAG_JSON_ID_MAT, id_mat);
                instruktur.put(Konfigurasi.TAG_JSON_NAMA_MAT, nama_mat);
                list.add(instruktur);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        ListAdapter adapterMat = new SimpleAdapter(getActivity(), list, R.layout.list_detail_materi,
                new String[]{Konfigurasi.TAG_JSON_NAMA_MAT},
                new int[]{R.id.txt_dis_nama_materi});
                listViewMateri.setAdapter(adapterMat);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(view.getContext(), TambahMateriActivity.class));

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = new Intent(getActivity(), DetailMateriActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String matId = map.get(Konfigurasi.TAG_JSON_ID_MAT).toString();
        intent.putExtra(Konfigurasi.MAT_ID, matId);
        startActivity(intent);
    }
}