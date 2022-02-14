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

public class DtKelasFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    FloatingActionButton btn_tambah_dt_kelas;
    ListView listViewDetailKelas;
    String JSON_STRING;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_kelas, container, false);

        listViewDetailKelas = view.findViewById(R.id.listViewDetailKelas);
        btn_tambah_dt_kelas = view.findViewById(R.id.btn_tambah_dt_kelas);

        listViewDetailKelas.setOnItemClickListener(this);
        btn_tambah_dt_kelas.setOnClickListener(this);

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
                String hasil = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_DTKELAS);
                return hasil;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("Data JSON: ", JSON_STRING);
                displayDetailKelas();

            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailKelas()
    {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try
        {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_DT_KLS_ARRAY);
            Log.d("DATA_JSON: ", JSON_STRING);

            for (int i = 0; i < result.length(); i++)
            {
                JSONObject object = result.getJSONObject(i);
                String id_kls = object.getString(Konfigurasi.TAG_JSON_IDKLS_DTKLS);
                String jum_pst = object.getString(Konfigurasi.TAG_JSON_JML_PST_DTKLS);


                HashMap<String, String> dtkelas = new HashMap<>();
                dtkelas.put(Konfigurasi.TAG_JSON_IDKLS_DTKLS, id_kls);
                dtkelas.put(Konfigurasi.TAG_JSON_JML_PST_DTKLS, jum_pst);
                list.add(dtkelas);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        ListAdapter adapterDtKls = new SimpleAdapter(getActivity(), list, R.layout.list_detail_dtkelas,
                new String[]{Konfigurasi.TAG_JSON_IDKLS_DTKLS, Konfigurasi.TAG_JSON_JML_PST_DTKLS},
                new int[]{R.id.txt_dis_idkls_dtkls, R.id.txt_jml_pst_dtkls});
        listViewDetailKelas.setAdapter(adapterDtKls);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = new Intent(getActivity(), DetailDTKelasActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String dtklsId = map.get(Konfigurasi.TAG_JSON_ID_DTKLS).toString();
        intent.putExtra(Konfigurasi.DTKLS_ID, dtklsId);
        startActivity(intent);
    }

    @Override
    public void onClick(View view)
    {
        startActivity(new Intent(view.getContext(), TambahDetailKelasActivity.class));
    }
}