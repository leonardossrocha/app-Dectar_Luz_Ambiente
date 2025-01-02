package com.rocha.dectarluzambiente;

import android.app.Activity; // Classe base para criar uma tela (atividade) no Android.
import android.hardware.Sensor; // Representa um sensor físico no dispositivo
import android.hardware.SensorEvent; // Contém os dados gerados por um sensor, como o tipo de sensor e os valores capturados
import android.hardware.SensorEventListener; // Interface que permite ouvir eventos gerados pelos sensores
import android.hardware.SensorManager; // Classe que gerencia os sensores do dispositivo.
import android.os.Bundle; // Classe que contém os dados passados para uma atividade no momento de sua criação ou recriação
import android.widget.TextView; // Componente de interface de usuário que exibe texto na tela.
import android.widget.Toast; // Classe usada para exibir mensagens breves ao usuário em forma de pop-up


// Classe principal da aplicação, que implementa SensorEventListener para lidar com eventos de sensores
// Utilização da interface SensorEventListener, para gerir notificações de sensores
// Veja mais em: https://developer.android.com/reference/android/hardware/SensorEventListener
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
