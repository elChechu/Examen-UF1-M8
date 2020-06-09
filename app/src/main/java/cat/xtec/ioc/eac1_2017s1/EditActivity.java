package cat.xtec.ioc.eac1_2017s1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnOk, btnKo;
    EditText carrer;
    EditText CP;
    EditText poblacio;
    EditText telf;
    EditText web;

    /**
     * Inicialitzem els widgets i listeners, s'executa un cop al crear l'activitat
     * @param savedInstanceState Bundle amb informació per assegurar la persistència de dades
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_opc);

        carrer = (EditText) findViewById(R.id.carrer);
        CP = (EditText) findViewById(R.id.CP);
        poblacio = (EditText) findViewById(R.id.poblacio);
        telf = (EditText) findViewById(R.id.telf);
        web = (EditText) findViewById(R.id.web);

        // Inicialitzem els botons i afegim els listeners
        btnOk = (Button) findViewById(R.id.btnOk);
        btnKo = (Button) findViewById(R.id.btnKo);
        btnOk.setOnClickListener(this);
        btnKo.setOnClickListener(this);

        //Obtenim el "bundle" amb la informació extra de l'"intent"
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            carrer.setText(extras.getString("carrer"));
            CP.setText(extras.getString("CP"));
            poblacio.setText(extras.getString("poblacio"));
            telf.setText(extras.getString("telf"));
            web.setText(extras.getString("web"));
        }
    }

    /**
     * Gestionem el clic de les vistes
     * @param view Vista a la que hem fet clic
     */
    @Override
    public void onClick(View view) {

        // Si ha fet clic en OK
        if(view.getId() == R.id.btnOk) {

            // Creem l'intent amb les dades a enviar
            Intent data = new Intent();
            data.putExtra("carrer", carrer.getText().toString());
            data.putExtra("CP", CP.getText().toString());
            data.putExtra("poblacio", poblacio.getText().toString());
            data.putExtra("telf", telf.getText().toString());
            data.putExtra("web", web.getText().toString());

            // Afegim un resultat OK i tanquem
            setResult(RESULT_OK, data);
            finish();

        } else {

            // Si ha fet clic en cancel·lar, retornem CANCELED a l'activitat 1 i tanquem
            setResult(RESULT_CANCELED);
            finish();

        }
    }
}