package br.edu.ednilsonrossi.permissoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_PERMISSION = 99;


    String permissoes[] = new String[]{
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CALL_PHONE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //verificarPermissoes();
    }

    /*
    Esse método é uma maneira não adequada de solicitar permissões. A sugestão é solictar a permissão
    quando o usuário demanda do recurso do aplicativo. Usando esse formato de solicitação o usuário
    pode não entender o motivo da solicitação e não fornecer as permissões que o app necessita.
     */
    private void verificarPermissoes() {
        List<String> requerer = new ArrayList<>();

        for (String permissao : permissoes) {
            if (ContextCompat.checkSelfPermission(this, permissao) != PackageManager.PERMISSION_GRANTED) {
                requerer.add(permissao);
            }
        }

        if (!requerer.isEmpty()) {
            ActivityCompat.requestPermissions(this, requerer.toArray(new String[requerer.size()]), REQUEST_PERMISSION);
        }
    }




    private void tirarFoto() {
        Toast.makeText(this, "Diga Xis!!!", Toast.LENGTH_SHORT).show();
    }

    public void usarCamera(View v) {

        final Activity activity = this;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            tirarFoto();

        } else {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

                new AlertDialog.Builder(this)
                        .setMessage("Para que o aplicativo consiga tirar as fotos que você deseja é necessário fornecer a permissão de acesso, caso contrário o recurso será desabilitado. Caso marque a opção 'Não perguntar novamente' essa funcionalidade só será habilitada nas configurações do aplicativo do Android")
                        .setPositiveButton("Fornecer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION);
                            }
                        })
                        .setNegativeButton("Agora não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.CAMERA
                        },
                        REQUEST_PERMISSION);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION) {
            for (int i = 0; i < permissions.length; i++) {

                if (permissions[i].equalsIgnoreCase(Manifest.permission.CAMERA) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    tirarFoto();
                }

            }
        }
    }
}