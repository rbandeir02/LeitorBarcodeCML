package br.mil.eb.cml;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class ValidaCombustivelActivity extends AppCompatActivity {
    Button btn;
    final Activity activity= this;
    TextView txtPlaca;
    TextView txtDataHora;
    TextView txtCota;
    TextView txtQtdAut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validacomb2);
        btn = (Button) findViewById(R.id.btnLerCodBarras);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("SCAN");
                intentIntegrator.setCameraId(0);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        try {

            txtPlaca =  (TextView) findViewById(R.id.txtViatura);
            txtDataHora =  (TextView) findViewById(R.id.txtData);
            txtCota =  (TextView) findViewById(R.id.txtCota);
            txtQtdAut =  (TextView) findViewById(R.id.txtQtdAut);

            if (intentResult != null) {
                if (intentResult.getContents() != null) {
                    carregarDados(intentResult.getContents().toString());
                } else {
                    alert("Scan cancelado");
                    txtPlaca.setText("Viatura: ");
                    txtDataHora.setText("Data e Hora: ");
                    txtCota.setText("Cota: ");
                    txtQtdAut.setText("Quantidade Autorizada: ");
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }catch (Exception ex)
        {
            alert("Erro: " + ex.getMessage());
            txtPlaca.setText("Viatura: ");
            txtDataHora.setText("Data e Hora: ");
            txtCota.setText("Cota: ");
            txtQtdAut.setText("Quantidade Autorizada: ");
        }

    }

    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    private void carregarDados (String msg){

        String [] dados = msg.split("&");

        txtPlaca.setText("Viatura: " + dados[0].substring(dados[0].indexOf("=") + 1, dados[0].length()));
        txtDataHora.setText("Data e Hora: "+ dados[1].substring(dados[1].indexOf("=") + 1, dados[1].length()));
        txtCota.setText("Cota: " + dados[3].substring(dados[3].indexOf("=") + 1, dados[3].length()));
        txtQtdAut.setText("Quantidade Autorizada: " + dados[5].substring(dados[5].indexOf("=") + 1, dados[5].length()));

    }

    public void QuitApp(View view) {
        ValidaCombustivelActivity.this.finish();
        System.exit(0);
    }

}
