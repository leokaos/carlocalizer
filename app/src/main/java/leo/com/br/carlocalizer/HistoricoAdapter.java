package leo.com.br.carlocalizer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

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

        View row;

        row = inflater.inflate(R.layout.custom_list_view, parent, false);
        TextView title, detail;
        ImageView i1;
        title = (TextView) row.findViewById(R.id.title);
        detail = (TextView) row.findViewById(R.id.detail);
        i1 = (ImageView) row.findViewById(R.id.img);

        title.setText(listaLocalizacao.get(position).getDateFormated());
        detail.setText(listaLocalizacao.get(position).getLatitudeLongitude());

        return (row);
    }
}
