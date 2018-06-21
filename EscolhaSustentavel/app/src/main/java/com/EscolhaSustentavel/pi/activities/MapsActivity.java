package com.EscolhaSustentavel.pi.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.EscolhaSustentavel.pi.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends AppCompatActivity
        implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {
    private static final String TAG = MapsActivity.class.getSimpleName();
    //private GoogleMap mMap;
    private CameraPosition mCameraPosition;
    private String Categoria;

    private Marker mPerth;
    private Marker mSydney;
    private Marker mBrisbane;

    private GoogleMap mMap;

    private GoogleApiClient mGoogleApiClient;

    //Caso o usuário não dê a permissão de acessar GPS, essa localização padrão irá aparecer
    private final LatLng mDefaultLocation = new LatLng(-20.1969222, -40.25504790000002);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;


    //Localização geografica de onde o aparelho está localizado no momento.
    private Location mLastKnownLocation;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";


    private static final LatLng PERTH = new LatLng(-31.952854, 115.857342);
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Categoria = bundle.getString("categoria");


        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        setContentView(R.layout.activity_maps);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    /**
     * Constrói o mapa após a conexão com o Google Play Services
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Tratamento de falha de conexão com o Google Play Services
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.d(TAG, "Falha na conexão com o Google Play Services: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    /**
     * Tratamento de suspensão de conexão com o Google Play Services
     */
    @Override
    public void onConnectionSuspended(int cause) {
        Log.d(TAG, "Conexão com Google Play Services perdida");
    }

    /**
     * Manipula o mapa quando o mesmo está disponível
     * Esse callback é chamado quando o mapa está pronto pra ser usado
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        if(Categoria.equals("Metal")){

            // Set a listener for marker click.
            mMap.setOnMarkerClickListener(this);

            updateLocationUI();

            // Pega a posição atual no mapa e os marcadores
            getDeviceLocation();
            //getAllMarkers();
            mMap.addMarker(new MarkerOptions()
                    .title("Neto Supermercados")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(new LatLng(-20.1413291, -40.244629499999974)));

            mMap.addMarker(new MarkerOptions()
                    .title("Extrabom Supermercados")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(new LatLng(-20.1348281, -40.24962549999998)));

            mMap.addMarker(new MarkerOptions()
                    .title("Epa Supermercados")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(new LatLng(-20.1926399, -40.2678095)));

            mMap.addMarker(new MarkerOptions()
                    .title("São José Supermercado")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(new LatLng(-20.236437, -40.276589)));

            mMap.addMarker(new MarkerOptions()
                    .title("Rede Show Supermercado")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(new LatLng(-20.236437, -40.276589)));

            mMap.addMarker(new MarkerOptions()
                    .title("Rede Show Supermercado")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(new LatLng(-20.156054, -40.271042)));




        }

        if(Categoria.equals("Vidro")){

            // Set a listener for marker click.
            mMap.setOnMarkerClickListener(this);

            updateLocationUI();

            // Pega a posição atual no mapa e os marcadores
            getDeviceLocation();
            //getAllMarkers();
            mMap.addMarker(new MarkerOptions()
                    .title("Epa Supermercados")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(new LatLng( -20.194284, -40.253311
                    )));

            mMap.addMarker(new MarkerOptions()
                    .title("Ok Supermercados")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(new LatLng( -20.195311, -40.263176
                    )));

            mMap.addMarker(new MarkerOptions()
                    .title("Carone Supermercados")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(new LatLng( -20.192653  , -40.265172
                    )));

            mMap.addMarker(new MarkerOptions()
                    .title("Rede Show Supermercado")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(new LatLng(-20.156054, -40.271042)));

            mMap.addMarker(new MarkerOptions()
                    .title("MegaLar")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(new LatLng( -20.197778   , -40.257382
                    )));


            mMap.addMarker(new MarkerOptions()
                    .title("Supermercado Coutinho")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(new LatLng( -20.201692   , -40.274267
                    )));




        }

        if(Categoria.equals("Plástico")){

            // Set a listener for marker click.
            mMap.setOnMarkerClickListener(this);

            updateLocationUI();

            // Pega a posição atual no mapa e os marcadores
            getDeviceLocation();
            //getAllMarkers();

            mMap.addMarker(new MarkerOptions()
                    .title("MegaLar")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(new LatLng( -20.197778   , -40.257382
                    )));

            mMap.addMarker(new MarkerOptions()
                    .title("Supermercado Canguru")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(new LatLng(  -20.213686   , -40.238133
                    )));

            mMap.addMarker(new MarkerOptions()
                    .title("DimDom Supermercados")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(new LatLng( -20.198429  , -40.255933
                    )));

            mMap.addMarker(new MarkerOptions()
                    .title("Lojas Americanas")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(new LatLng( -20.222069   , -40.253065
                    )));

            mMap.addMarker(new MarkerOptions()
                    .title("Lojas Americanas")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(new LatLng(  -20.240037  , -40.274972
                    )));

            mMap.addMarker(new MarkerOptions()
                    .title("Dadalto")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(new LatLng( -20.194918   , -40.251394

                    )));




        }

    }

    public void getAllMarkers(GoogleMap map) {

        mMap = map;

        // Add some markers to the map, and add a data object to each marker.
        mPerth = mMap.addMarker(new MarkerOptions()
                .position(PERTH)
                .title("Perth"));
        mPerth.setTag(0);

        mSydney = mMap.addMarker(new MarkerOptions()
                .position(SYDNEY)
                .title("Sydney"));
        mSydney.setTag(0);

        mBrisbane = mMap.addMarker(new MarkerOptions()
                .position(BRISBANE)
                .title("Brisbane"));
        mBrisbane.setTag(0);

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

    //}


    /**
     * Pega a posição atual e posiciona a camera
     */
    private void getDeviceLocation() {
        /*
         * Faz o pedido de permissão de localização para pegar a localização do
         * aparelho. O resultado do pedido de permissão é tratado por um callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        /*
         * Pega a melhor e mais recente localização do aparelho, o que pode ser null, mas é raro, só em
         * casos onde a localização não está disponível.
         */
        if (mLocationPermissionGranted) {
            mLastKnownLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
        }

        // Seta a posição da camera para onde está a localização do aparelho
        if (mCameraPosition != null) {
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
        } else if (mLastKnownLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
        } else {
            Log.d(TAG, "Localização atual não permitida, usando localização padrão");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    /**
     * Trata o resultado do pedido de permissão de acesso ao GPS.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // Se o pedido é cancelado, o array sempre será vazio.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mLastKnownLocation = null;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
}
/**
 * Pega a posição atual e posiciona a camera
 */




