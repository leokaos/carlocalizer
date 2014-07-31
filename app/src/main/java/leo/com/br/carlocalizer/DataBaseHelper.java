package leo.com.br.carlocalizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
        builder.append("longitude real");
        builder.append(");");

        db.execSQL(builder.toString());
    }

    public void open() {
        this.mDb = getWritableDatabase();
    }

    public void addLocalizacao(Localizacao localizacao) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("data", Localizacao.DATE_FORMAT.format(localizacao.getData()));

        contentValues.put("latitude", localizacao.getLatitude());
        contentValues.put("longitude", localizacao.getLongitude());

        database.insert("historico_localizacao", null, contentValues);

    }

    public List<Localizacao> buscarTodasAsLocalizacoes() {
        List<Localizacao> listaRetorno = new ArrayList<Localizacao>();

        final SQLiteDatabase dataBase = getReadableDatabase();

        Cursor cursor = dataBase.query("historico_localizacao", new String[]{"id", "data", "longitude", "latitude"}, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            while (cursor.moveToNext()) {

                Localizacao localizacao = new Localizacao();

                localizacao.setDataFromString(cursor.getString(cursor.getColumnIndex("data")));
                localizacao.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
                localizacao.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
                localizacao.setId(cursor.getLong(cursor.getColumnIndex("id")));

                listaRetorno.add(localizacao);
            }
        }

        return listaRetorno;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
