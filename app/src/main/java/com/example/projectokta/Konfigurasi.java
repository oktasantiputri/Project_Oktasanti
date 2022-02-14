package com.example.projectokta;

public class Konfigurasi
{
    // url dimana web API berada
    //PESERTA
    public static final String URL_GET_ALL_PESERTA = "http://192.168.100.4/inixtraining/peserta/tr_datas_peserta.php";
    public static final String URL_GET_DETAIL_PESERTA = "http://192.168.100.4/inixtraining/peserta/tr_detail_peserta.php?id_pst=";
    public static final String URL_ADD_PESERTA = "http://192.168.100.4/inixtraining/peserta/tr_add_peserta.php";
    public static final String URL_UPDATE_PESERTA = "http://192.168.100.4/inixtraining/peserta/tr_update_peserta.php?id_pst=";
    public static final String URL_DELETE_PESERTA = "http://192.168.100.4/inixtraining/peserta/tr_delete_peserta.php?id_pst=";

    //INSTRUKTUR
    public static final String URL_GET_ALL_INSTRUKTUR = "http://192.168.100.4/inixtraining/instruktur/tr_datas_instruktur.php";
    public static final String URL_GET_DETAIL_INSTRUKTUR = "http://192.168.100.4/inixtraining/instruktur/tr_detail_instruktur.php?id_ins=";
    public static final String URL_ADD_INSTRUKTUR = "http://192.168.100.4/inixtraining/instruktur/tr_add_instruktur.php";
    public static final String URL_UPDATE_INSTRUKTUR = "http://192.168.100.4/inixtraining/instruktur/tr_update_instruktur.php?id_ins=";
    public static final String URL_DELETE_INSTRUKTUR = "http://192.168.100.4/inixtraining/instruktur/tr_delete_instruktur.php?id_ins=";

    //MATERI
    public static final String URL_GET_ALL_MATERI = "http://192.168.100.4/inixtraining/materi/tr_datas_materi.php";
    public static final String URL_GET_DETAIL_MATERI = "http://192.168.100.4/inixtraining/materi/tr_detail_materi.php?id_mat=";
    public static final String URL_ADD_MATERI = "http://192.168.100.4/inixtraining/materi/tr_add_materi.php";
    public static final String URL_UPDATE_MATERI = "http://192.168.100.4/inixtraining/materi/tr_update_materi.php?id_mat=";
    public static final String URL_DELETE_MATERI = "http://1192.168.100.4/inixtraining/materi/tr_delete_materi.php?id_mat=";

    //KELAS
    public static final String URL_GET_ALL_KELAS = "http://192.168.100.4/inixtraining/kelas/tr_datas_kelas.php";
    public static final String URL_GET_DETAIL_KELAS = "http://192.168.100.4/inixtraining/kelas/tr_detail_kelas.php?id_kls=";
    public static final String URL_ADD_KELAS = "http://192.168.100.4/inixtraining/kelas/tr_add_kelas.php";
    public static final String URL_UPDATE_KELAS = "http://192.168.100.4/inixtraining/kelas/tr_update_kelas.php?id_kls=";
    public static final String URL_DELETE_KELAS = "http://192.168.100.4/inixtraining/kelas/tr_delete_kelas.php?id_kls=";

    //DETAIL KELAS
    public static final String URL_GET_ALL_DTKELAS = "http://192.168.100.4/inixtraining/detail_kelas/tr_datas_detail_kelas.php";
    public static final String URL_GET_DETAIL_DTKELAS = "http://192.168.100.4/inixtraining/detail_kelas/tr_detail_detail_kelas.php?id_detail_kls=";
    public static final String URL_ADD_DTKELAS = "http://192.168.100.4/inixtraining/detail_kelas/tr_add_detail_kelas.php";
    public static final String URL_UPDATE_DTKELAS = "http://192.168.100.4/inixtraining/detail_kelas/tr_update_detail_kelas.php?id_detail_kls=";
    public static final String URL_DELETE_DTKELAS = "http://192.168.100.4/inixtraining/detail_kelas/tr_delete_detail_kelas.php?id_detail_kls=";

    // SEARCH DATA
    public static final String URL_SEARCH_PST = "http://192.168.100.4/inixtraining/search/search_info_pst.php?id_pst=";
    public static final String URL_SEARCH_INS = "http://192.168.100.4/inixtraining/search/search_info_ins.php?id_ins=";

    // key and value JSON yang muncul di browser
    //PESERTA
    public static final String KEY_PST_ID = "id_pst";
    public static final String KEY_PST_NAMA = "nama_pst";
    public static final String KEY_PST_EMAIL = "email_pst";
    public static final String KEY_PST_HP = "hp_pst";
    public static final String KEY_PST_INSTANSI = "instansi_pst";

    //INSTRUKTUR
    public static final String KEY_INS_ID = "id_ins";
    public static final String KEY_INS_NAMA = "nama_ins";
    public static final String KEY_INS_EMAIL = "email_ins";
    public static final String KEY_INS_HP = "hp_ins";

    //MATERI
    public static final String KEY_MAT_ID = "id_mat";
    public static final String KEY_MAT_NAMA = "nama_mat";

    //KELAS
    public static final String KEY_KLS_ID = "id_kls";
    public static final String KEY_KLS_MULAI = "tgl_mulai_kls";
    public static final String KEY_KLS_AKHIR = "tgl_akhir_kls";
    public static final String KEY_ID_INS_KLS = "id_ins";
    public static final String KEY_ID_MAT_KLS = "id_mat";

    //DETAIL KELAS
    public static final String KEY_DTKLS_ID = "id_detail_kelas";
    public static final String KEY_DTKLS_IDKLS = "id_kls";
    public static final String KEY_DTKLS_IDPST = "id_pst";


    // flag JSON
    //PESERTA
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JSON_ID_PST = "id_pst";
    public static final String TAG_JSON_NAMA_PST = "nama_pst";
    public static final String TAG_JSON_EMAIL_PST = "email_pst";
    public static final String TAG_JSON_HP_PST = "hp_pst";
    public static final String TAG_JSON_INS_PST = "instansi_pst";

    //INSTRUKTUR
    public static final String TAG_JSON_INS_ARRAY = "result";
    public static final String TAG_JSON_ID_INS = "id_ins";
    public static final String TAG_JSON_NAMA_INS = "nama_ins";
    public static final String TAG_JSON_EMAIL_INS = "email_ins";
    public static final String TAG_JSON_HP_INS = "hp_ins";

    //MATERI
    public static final String TAG_JSON_MAT_ARRAY = "result";
    public static final String TAG_JSON_ID_MAT = "id_mat";
    public static final String TAG_JSON_NAMA_MAT = "nama_mat";

    //KELAS
    public static final String TAG_JSON_KLS_ARRAY = "result";
    public static final String TAG_JSON_ID_KLS = "id_kls";
    public static final String TAG_JSON_MULAI_KLS = "tgl_mulai_kls";
    public static final String TAG_JSON_AKHIR_KLS = "tgl_akhir_kls";
    public static final String TAG_JSON_ID_INS_KLS = "id_ins";
    public static final String TAG_JSON_ID_MAT_KLS = "id_mat";

    //DETAIL KELAS
    public static final String TAG_JSON_DT_KLS_ARRAY = "result";
    public static final String TAG_JSON_ID_DTKLS = "id_detail_kls";
    public static final String TAG_JSON_IDKLS_DTKLS = "id_kls";
    public static final String TAG_JSON_IDPST_DTKLS = "id_pst";
    public static final String TAG_JSON_NAMAMAT_DTKLS = "nama_mat";
    public static final String TAG_JSON_NAMAPST_DTKLS = "nama_pst";
    public static final String TAG_JSON_JML_PST_DTKLS = "jum_pst";

    // variabel ID peserta
    public static final String PST_ID = "id_pst";
    public static final String INS_ID = "id_ins";
    public static final String MAT_ID = "id_mat";
    public static final String KLS_ID = "id_kls";
    public static final String DTKLS_ID = "id_detail_kls";
}
