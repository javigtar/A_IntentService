package dam.org.subidaarchivo;

import android.app.Activity;
import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    Button subirArchivo;
    ProgressBar barraProgreso;
    TextView mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barraProgreso = (ProgressBar)findViewById(R.id.progressBar);
        barraProgreso.setMax(100);

        mensaje = (TextView)findViewById(R.id.tvMensaje);

        subirArchivo = (Button)findViewById(R.id.bSubirArchivo);
        subirArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent msgSubirArchivo = new Intent(MainActivity.this, SubirArchivo.class);
                startService(msgSubirArchivo);
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(SubirArchivo.ACTION_PROGRESO);
        filter.addAction(SubirArchivo.ACTION_FIN);
        ProgresoDeSubida progreso = new ProgresoDeSubida();
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

    public class ProgresoDeSubida extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(SubirArchivo.ACTION_PROGRESO)){
                int progreso = intent.getIntExtra("progreso", 0);
                barraProgreso.setProgress(progreso);

            }else{
                if(intent.getAction().equals((SubirArchivo.ACTION_FIN))){
                    //Toast.makeText(MainActivity.this, "Subida Finalizada", Toast.LENGTH_SHORT).show();
                    mensaje.setText("Subida finalizada");
                }
            }
        }
    }

}
