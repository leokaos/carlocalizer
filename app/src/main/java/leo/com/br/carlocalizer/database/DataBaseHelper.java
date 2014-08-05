package leo.com.br.carlocalizer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import leo.com.br.carlocalizer.converter.LocalizacaoExtrator;
import leo.com.br.carlocalizer.dominio.Localizacao;

import static leo.com.br.carlocalizer.converter.LocalizacaoExtrator.COLUNA_ATUAL;
import static leo.com.br.carlocalizer.converter.LocalizacaoExtrator.COLUNA_DATA;
import static leo.com.br.carlocalizer.converter.LocalizacaoExtrator.COLUNA_LATITUDE;
import static leo.com.br.carlocalizer.converter.LocalizacaoExtrator.COLUNA_LONGITUDE;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME = "car_localizer";
    private static final Integer DATA_BASE_VERSION = 2;

    private SQLiteDatabase mDb;

    public DataBaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE").append(" ").append("historico_localizacao").append(" (");
        builder.append("id integer primary key AUTOINCREMENT NOT NULL,");
        builder.append("data text, ");
        builder.append("latitude real, ");
        builder.append("longitude real, ");
        builder.append("atual integer");
        builder.append(");");

        db.execSQL(builder.toString());
    }

    public void open() {
        this.mDb = getWritableDatabase();
    }

    public void addLocalizacao(Localizacao localizacao) {
        SQLiteDatabase database = getWritableDatabase();

        if (localizacao.isAtual()) {
            setPadrao();
        }

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUNA_DATA, Localizacao.DATE_FORMAT.format(localizacao.getData()));

        contentValues.put(COLUNA_LATITUDE, localizacao.getLatitude());
        contentValues.put(COLUNA_LONGITUDE, localizacao.getLongitude());
        contentValues.put(COLUNA_ATUAL, localizacao.isAtual());

        database.insert("historico_localizacao", null, contentValues);
    }

    private void setPadrao() {

        SQLiteDatabase sqlLite = getWritableDatabase();

        ContentValues content = new ContentValues();

        content.put(LocalizacaoExtrator.COLUNA_ATUAL, "0");

        sqlLite.update("historico_localizacao", content, null, null);
    }

    public List<Localizacao> buscarTodasAsLocalizacoes() {
        List<Localizacao> listaRetorno = new ArrayList<Localizacao>();

        final SQLiteDatabase dataBase = getReadableDatabase();

        Cursor cursor = dataBase.query("historico_localizacao", new String[]{"id", "data", "longitude", "latitude", "atual"}, null, null, null, null, null);

        LocalizacaoExtrator extrator = new LocalizacaoExtrator();

        if (cursor != null && cursor.moveToFirst()) {

            while (cursor.moveToNext()) {
                listaRetorno.add(extrator.extrair(cursor));
            }
        }

        return listaRetorno;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Localizacao getLocalizacaoAtual() {

        Localizacao localizacao = null;

        final SQLiteDatabase dataBase = getReadableDatabase();

        String query = "atual = ?";

        String[] argumentos = {"1"};

        Cursor cursor = dataBase.query("historico_localizacao", new String[]{"id", "data", "longitude", "latitude", "atual"}, query, argumentos, null, null, null);

        LocalizacaoExtrator extrator = new LocalizacaoExtrator();

        if (cursor != null && cursor.moveToFirst()) {
            localizacao = extrator.extrair(cursor);
        }

        return localizacao;
    }
}
