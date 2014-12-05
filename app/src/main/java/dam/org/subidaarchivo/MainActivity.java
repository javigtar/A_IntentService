package dam.org.subidaarchivo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends Activity {

    Button subirArchivo;
    ProgressBar barraProgreso;
    TextView mensaje;
    ProgresoDeSubida progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Barra de progreso de la subida
        barraProgreso = (ProgressBar)findViewById(R.id.progressBar);
        barraProgreso.setMax(100);

        //Mensaque me muestra en pantalla e indicará cuando la subida ha finalziado
        mensaje = (TextView)findViewById(R.id.tvMensaje);

        subirArchivo = (Button)findViewById(R.id.bSubirArchivo);
        subirArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent msgSubirArchivo = new Intent(MainActivity.this, SubirArchivo.class);
                startService(msgSubirArchivo);
            }
        });

        //Tipo de mensaje que tendrá que tratar la aplicación para enterarse del progreso de la subida
        IntentFilter filter = new IntentFilter();
        filter.addAction(SubirArchivo.ACTION_PROGRESO);
        filter.addAction(SubirArchivo.ACTION_FIN);
        progreso = new ProgresoDeSubida();
        //Registramos el BroadcastReceiver a nuestra aplicación y lo asociamos a los mensajes del progreso
        registerReceiver(progreso, filter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Cuando salgamos de la aplicacion quitará el registro de nuestra aplicación de nuestro
        //BroadcastReceiver para que no se quede en memoria
        unregisterReceiver(progreso);
    }

    //Clase que gestionará los mensajes que produzca la clase SubirArchivo
    public class ProgresoDeSubida extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //Aquí entrará cuando la clase SubirArchivo envie mensajes de tipo ACTION_PROGRESO
            if(intent.getAction().equals(SubirArchivo.ACTION_PROGRESO)){
                int progreso = intent.getIntExtra("progreso", 0);
                barraProgreso.setProgress(progreso);

            }else{
                //Aquí entrará cuando la clase SubirArchivo envie mensajes de tipo ACTION_FIN
                if(intent.getAction().equals((SubirArchivo.ACTION_FIN))){
                    mensaje.setText("Subida finalizada");
                }
            }
        }
    }

}
