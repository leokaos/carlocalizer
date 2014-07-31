package leo.com.br.carlocalizer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Main extends FragmentActivity {

    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        setContentView(R.layout.activity_main);

        setUpMapIfNeeded();

        verifyGpsOn();

        initDataBase();
    }

    private void initDataBase() {
        dataBaseHelper = new DataBaseHelper(this);
    }

    private void verifyGpsOn() {

        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (provider == null || !provider.contains(LocationManager.GPS_PROVIDER)) {
            showMessage("GPS Desabilitado!");
        } else {
            configGpsListener();
        }
    }

    private void configGpsListener() {

        LocationListener mLocationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                setCurrentPosition(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                showMessage("GPS Desabilitado!");
            }
        };


        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30l, 1f, mLocationListener);
    }

    private void setCurrentPosition(Location location) {

        mMap.clear();

        final LatLng position = new LatLng(location.getLatitude(), location.getLongitude());

        mMap.addMarker(new MarkerOptions().position(position).title("Posição Atual"));

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(position, 17.0f)));

        dataBaseHelper.addLocalizacao(new Localizacao(location));

    }

    private void showMessage(String msg) {
        final AlertDialog alert = new AlertDialog.Builder(this).create();

        alert.setTitle("Atenção");
        alert.setMessage(msg);
        alert.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                alert.hide();
            }
        });

        alert.setIcon(null);
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {

        if (mMap == null) {

            MapsInitializer.initialize(this);

            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_action_bar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.historico:
                Intent intent = new Intent(Main.this, Historico.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
        }

        return true;
    }
}