package com.example.converttool;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ConvertActivity extends AppCompatActivity implements TextWatcher, AdapterView.OnItemSelectedListener {
    private TextView txvResult;
    private Spinner spnUnits;
    private EditText edtInput;
    private String []arrUnits;
    private int convertType;
    private static final float [][] distanceRate = {
            {1, 1609.344f, 1760, 63360, 160934.4f},
            {0.000621f, 1, 1.093613f, 39.370079f, 100},
            {0.000568f, 0.9144f, 1, 36, 91.44f},
            {0.000016f, 0.0254f, 0.027778f, 1, 2.54f},
            {0.000006f, 0.01f, 0.010936f, 0.393701f, 1}
    };
    private static final float [][] weightRate = {
            {1, 10, 1000, 2204.622622f, 1000000},
            {0.1f, 1, 100, 220.462262f, 100000},
            {0.001f, 0.01f, 1, 2.204623f, 1000},
            {0.000454f, 0.004536f, 0.453592f, 1, 453.59237f},
            {0.000001f, 0.00001f, 0.002205f, 0.001f, 1}
    };
    private static final float [][] temperatureRate = {
            {1, 33.8f, 274.15f, 493.47f},
            {-17.222222f, 1, 255.927778f, 460.67f},
            {-272.15f, -457.87f, 1, 1.8f},
            {-272.594444f, -458.67f, 0.555556f, 1}
    };
    private static final float [][] timeRate = {
            {1, 7, 168, 10800, 604800},
            {0.142857f, 1, 24, 1440, 86400},
            {0.005952f, 0.041667f, 1, 60, 3600},
            {0.000099f, 0.000694f, 0.016667f, 1, 60},
            {0.000002f, 0.000012f, 0.000278f, 0.016667f, 1}
    };
    private int currentUnit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);

        convertType = getIntent().getExtras().getInt("convert_type");
        txvResult = (TextView) findViewById(R.id.lb_result);
        spnUnits = (Spinner) findViewById(R.id.spn_unit);
        edtInput = (EditText) findViewById(R.id.edt_input);

        if(convertType == MainActivity.TEMPERATURE_CONVERT) {
            arrUnits = getResources().getStringArray(R.array.temperature_units);
        }
        else if(convertType == MainActivity.TIME_CONVERT) {
            arrUnits = getResources().getStringArray(R.array.time_units);
        }
        else if(convertType == MainActivity.DISTANCE_CONVERT) {
            arrUnits = getResources().getStringArray(R.array.distance_units);
        }
        else {
            arrUnits = getResources().getStringArray(R.array.weight_units);
        }
        ArrayAdapter<CharSequence> adpater = ArrayAdapter.createFromResource(this, R.array.distance_units, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adpater = getUnitListForAdapter(convertType);
        adpater.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spnUnits.setAdapter(adpater);

        edtInput.addTextChangedListener(this);
        spnUnits.setOnItemSelectedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        convert(currentUnit, getConvertRate(convertType));
    }

    private void convert(int currentUnit, float[][] arrRate) {
        if(edtInput.getText().toString().trim().length() < 1) {
            txvResult.setText("");
            return;
        }
        float input = Float.parseFloat(edtInput.getText().toString());
        String result = "";

        for(int index = 0; index < arrUnits.length; index++) {
            if(index != currentUnit) {
                result += arrUnits[index] + ": ";
                result += Math.round(arrRate[currentUnit][index] * input * 1000) / 1000.0 + "\n";
            }
            txvResult.setText(result);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        currentUnit = position;
        convert(currentUnit, getConvertRate(convertType));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private float[][] getConvertRate(int convertType) {
        switch (convertType){
            case MainActivity.DISTANCE_CONVERT:
                return distanceRate;
            case MainActivity.TIME_CONVERT:
                return timeRate;
            case MainActivity.TEMPERATURE_CONVERT:
                return temperatureRate;
            case MainActivity.WEIGHT_CONVERT:
                return weightRate;
            default:
                return null;
        }
    }

    private ArrayAdapter<CharSequence> getUnitListForAdapter(int convertType) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.distance_units, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        ;
        switch (convertType){
            case MainActivity.DISTANCE_CONVERT:
                adapter = ArrayAdapter.createFromResource(this, R.array.distance_units, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                break;
            case MainActivity.TIME_CONVERT:
                adapter = ArrayAdapter.createFromResource(this, R.array.time_units, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                break;
            case MainActivity.TEMPERATURE_CONVERT:
                adapter = ArrayAdapter.createFromResource(this, R.array.temperature_units, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                break;
            case MainActivity.WEIGHT_CONVERT:
                adapter = ArrayAdapter.createFromResource(this, R.array.weight_units, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                break;
            default:
                break;
        }
        return adapter;
    }
}