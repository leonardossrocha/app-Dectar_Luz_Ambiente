package com.rocha.dectarluzambiente;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private TextView lightStatus;
    private TextView lightValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightStatus = findViewById(R.id.light_status);
        lightValue = findViewById(R.id.light_value);

        // Inicializando o SensorManager e o Sensor de Luz
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null) {
            Toast.makeText(this, "Sensor de luz não disponível!", Toast.LENGTH_SHORT).show();
            finish(); // Fecha o app se o sensor não estiver presente
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registrando o listener para o sensor de luz
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Parando o listener quando o app estiver em pausa
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float light = event.values[0]; // Valor da luminosidade em lux

        // Atualizando os valores na interface
        lightValue.setText("Luminosidade: " + light + " lx");

        // Alterando a cor de fundo e status com base no valor da luz
        if (light < 10) {
            lightStatus.setText("Ambiente Escuro");
            lightStatus.setBackgroundColor(0xFF444444); // Cinza
            lightStatus.setTextColor(0xFFFFFFFF); // Branco
        } else {
            lightStatus.setText("Ambiente Claro");
            lightStatus.setBackgroundColor(0xFFFFFFFF); // Branco
            lightStatus.setTextColor(0xFF000000); // Preto
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Não necessário para este projeto
    }
}


//import android.os.Bundle;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//}