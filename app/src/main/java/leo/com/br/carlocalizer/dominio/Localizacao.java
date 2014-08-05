package leo.com.br.carlocalizer.dominio;

import android.location.Location;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Localizacao {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
    public static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();

    private Long id;
    private Double latitude;
    private Double longitude;
    private Date data;
    private boolean atual;

    public Localizacao() {
        super();
    }

    public Localizacao(Long id, Double latitude, Double longitude, Date data, boolean atual) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.data = data;
        this.atual = atual;
    }

    public Localizacao(Location location) {
        this(null, location.getLatitude(), location.getLongitude(), new Date(), true);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isAtual() {
        return atual;
    }

    public void setAtual(boolean atual) {
        this.atual = atual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Localizacao that = (Localizacao) o;

        if (atual != that.atual) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null)
            return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (atual ? 1 : 0);
        return result;
    }

    public String getDateFormated() {
        return DATE_FORMAT.format(getData());
    }

    public String getLatitudeLongitude() {
        StringBuilder builder = new StringBuilder();

        builder.append("Lat: ").append(NUMBER_FORMAT.format(getLatitude())).append(" Long: ").append(NUMBER_FORMAT.format(getLongitude()));

        return builder.toString();
    }
}
