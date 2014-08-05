package leo.com.br.carlocalizer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import leo.com.br.carlocalizer.database.DataBaseHelper;
import leo.com.br.carlocalizer.dominio.Localizacao;

public class Historico extends Activity {

    private DataBaseHelper dataBaseHelper;

    public Historico() {
        super();

        this.dataBaseHelper = new DataBaseHelper(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_historico);

        final List<Localizacao> lista = dataBaseHelper.buscarTodasAsLocalizacoes();

        HistoricoAdapter adapter = new HistoricoAdapter(lista, this);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

}
