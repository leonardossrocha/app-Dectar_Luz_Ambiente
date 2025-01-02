package com.rocha.dectarluzambiente;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

// Classe principal da aplicação, que implementa SensorEventListener para lidar com eventos de sensores
public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager; // Gerencia os sensores do dispositivo
    private Sensor lightSensor;         // Representa o sensor de luz
    private TextView lightStatus;       // Exibe o status do ambiente (claro ou escuro)
    private TextView lightValue;        // Exibe o valor de luminosidade em lux

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Define o layout da atividade

        // Inicializando os componentes da interface
        lightStatus = findViewById(R.id.light_status); // Exibe se o ambiente está claro ou escuro
        lightValue = findViewById(R.id.light_value);   // Exibe o valor atual de luminosidade

        // Inicializando o gerenciador de sensores e selecionando o sensor de luz
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        // Verifica se o sensor de luz está disponível no dispositivo
        if (lightSensor == null) {
            // Exibe uma mensagem e encerra o aplicativo caso o sensor não esteja presente
            Toast.makeText(this, "Sensor de luz não disponível!", Toast.LENGTH_SHORT).show();
            finish(); // Encerra a atividade
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registra o listener para começar a receber eventos do sensor de luz
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remove o listener quando o app entra em pausa para economizar recursos
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Obtém o valor atual do sensor de luz (em lux)
        float light = event.values[0];

        // Atualiza o valor na interface para o usuário
        lightValue.setText("Luminosidade: " + light + " lx");

        // Altera o status e a cor de fundo com base no valor da luminosidade
        if (light < 10) {
            // Ambiente escuro: define texto e cor de fundo apropriados
            lightStatus.setText("Ambiente Escuro");
            lightStatus.setBackgroundColor(0xFF444444); // Cinza
            lightStatus.setTextColor(0xFFFFFFFF);       // Branco
        } else {
            // Ambiente claro: define texto e cor de fundo apropriados
            lightStatus.setText("Ambiente Claro");
            lightStatus.setBackgroundColor(0xFFFFFFFF); // Branco
            lightStatus.setTextColor(0xFF000000);       // Preto
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Método implementado, mas não utilizado neste projeto, pois precisão não é relevante aqui
    }
}
