package leo.com.br.carlocalizer.converter;

import android.database.Cursor;

import java.text.ParseException;
import java.util.Date;

import leo.com.br.carlocalizer.dominio.Localizacao;

import static leo.com.br.carlocalizer.dominio.Localizacao.DATE_FORMAT;

public class LocalizacaoExtrator implements Extrator<Localizacao> {

    public static final String COLUNA_ID = "id";
    public static final String COLUNA_DATA = "data";
    public static final String COLUNA_ATUAL = "atual";
    public static final String COLUNA_LATITUDE = "latitude";
    public static final String COLUNA_LONGITUDE = "longitude";

    public LocalizacaoExtrator() {
        super();
    }


    @Override
    public Localizacao extrair(Cursor cursor) {

        Localizacao localizacao = new Localizacao();

        localizacao.setId(cursor.getLong(cursor.getColumnIndex(COLUNA_ID)));
        localizacao.setData(extractDate(cursor.getString(cursor.getColumnIndex(COLUNA_DATA))));
        localizacao.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUNA_LATITUDE)));
        localizacao.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUNA_LONGITUDE)));
        localizacao.setAtual(extractBoolean(cursor.getInt(cursor.getColumnIndex(COLUNA_ATUAL))));

        return localizacao;
    }

    protected Date extractDate(String data) {
        try {
            return DATE_FORMAT.parse(data);
        } catch (ParseException e) {
            return null;
        }
    }

    protected boolean extractBoolean(Integer value) {
        if (value == 0) {
            return false;
        } else {
            return true;
        }
    }
}
