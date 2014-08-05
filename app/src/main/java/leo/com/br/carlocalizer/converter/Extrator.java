package leo.com.br.carlocalizer.converter;

import android.database.Cursor;

public interface Extrator<T> {

    T extrair(Cursor cursor);
}
