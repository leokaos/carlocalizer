package leo.com.br.carlocalizer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import leo.com.br.carlocalizer.dominio.Localizacao;

public class HistoricoAdapter extends BaseAdapter {

    private List<Localizacao> listaLocalizacao;
    private LayoutInflater inflater;

    public HistoricoAdapter(List<Localizacao> listaLocalizacao, Activity parentActivity) {
        super();

        this.listaLocalizacao = listaLocalizacao;
        this.inflater = parentActivity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return listaLocalizacao.size();
    }

    @Override
    public Object getItem(int position) {
        return listaLocalizacao.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaLocalizacao.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Localizacao localizacao = listaLocalizacao.get(position);

        View row;

        row = inflater.inflate(R.layout.custom_list_view, parent, false);
        TextView title, detail;

        title = (TextView) row.findViewById(R.id.title);
        detail = (TextView) row.findViewById(R.id.detail);

        String texto = localizacao.getDateFormated();

        if (localizacao.isAtual()) {
            texto += " (Atual)";
        }

        title.setText(texto);
        detail.setText(localizacao.getLatitudeLongitude());

        return (row);
    }
}
