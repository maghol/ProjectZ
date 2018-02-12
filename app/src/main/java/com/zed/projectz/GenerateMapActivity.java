package com.zed.projectz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GenerateMapActivity extends AppCompatActivity {

    private MapHelper mapHelper;
    private SessionData sessionData = DataHolder.getInstance().getData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_map);
        mapHelper = new MapHelper();
        initialize();
    }

    private void initialize() {
        sessionData = DataHolder.getInstance().getData();
        if (sessionData.Map == null || !sessionData.Map.Selected) {
            mapHelper.generateMap(getAssets(), getApplicationContext());
            displayMap();
        }
    }

    public void generateNewMap(View view) {
        mapHelper.generateMap(getAssets(), getApplicationContext());
        displayMap();
    }

    public void selectMap(View view) {
        sessionData.Map.Selected = true;
        finish();
    }

    private void displayMap() {
        ViewGroup mapContainer = this.findViewById(R.id.mapConstraintLayout);
        mapContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        View inflatedMainLayout = inflater.inflate(R.layout.main, null, false);
        inflatedMainLayout.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mapContainer.addView(inflatedMainLayout);
    }
}
