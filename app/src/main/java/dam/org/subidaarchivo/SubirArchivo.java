package dam.org.subidaarchivo;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by JAVIER GARCIA TARIN on 05/12/2014.
 */
public class SubirArchivo extends IntentService {

    //Mensajes que producirá la clase para tratarlos con un BroadcastReceiver
    public static final String ACTION_PROGRESO = "PROGRESO";
    public static final String ACTION_FIN = "FIN";

    public SubirArchivo() {
        super("SubirArchivo");
    }

    //Código que ejecutará la tarea en segundo plano
    @Override
    protected void onHandleIntent(Intent intent) {

        for(int i =0 ; i <= 10 ; i++){

            SystemClock.sleep(1000);

            //Comunicamos el progreso
            Intent progreso = new Intent();
            progreso.setAction(ACTION_PROGRESO);
            progreso.putExtra("progreso", i * 10);
            //Cada vez que se repita el bucle enviará un mensaje broadcast con el intent creado
            //indicando que está en proceso la tarea
            sendBroadcast(progreso);
        }

        Intent fin = new Intent();
        fin.setAction(ACTION_FIN);
        //Cuando acabe la tarea mandará un mensaje broadcast con el intent creado indicando
        //que ha finalizado la tarea
        sendBroadcast(fin);

    }
}