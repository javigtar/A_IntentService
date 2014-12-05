package dam.org.subidaarchivo;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by JAVIER GARCIA TARIN on 05/12/2014.
 */
public class SubirArchivo extends IntentService {

    public static final String ACTION_PROGRESO = "PROGRESO";
    public static final String ACTION_FIN = "FIN";


    public SubirArchivo() {
        super("SubirArchivo");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        for(int i =0 ; i <= 10 ; i++){

            SystemClock.sleep(1000);

            //Comunicamos el progreso
            Intent progreso = new Intent();
            progreso.setAction(ACTION_PROGRESO);
            progreso.putExtra("progreso", i * 10);
            sendBroadcast(progreso);
        }

        Intent fin = new Intent();
        fin.setAction(ACTION_FIN);
        sendBroadcast(fin);

    }
}