package leo.com.br.carlocalizer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
