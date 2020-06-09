package cat.xtec.ioc.eac1_2017s1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton[] buttonsNom;
    ImageButton[] buttons;
    Button btnEnvia;
    Button maps;
    TextView[] textViews;
    EditText editNom;
    LinearLayout[] layouts;
    TextView nom;

    /**
     * Inicialitzem els widgets i listeners, s'executa un cop al crear l'activitat
     * @param savedInstanceState Bundle amb informació per assegurar la persistència de dades
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialitzem els botons 3, 4, 5 i 6
        buttons = new ImageButton[]{
                (ImageButton) findViewById(R.id.edit),
                (ImageButton) findViewById(R.id.delete),
                (ImageButton) findViewById(R.id.phone),
                (ImageButton) findViewById(R.id.viewWeb)
        };

        //Inicialitzem els botons 1 i 2
        buttonsNom = new ImageButton[]{
                (ImageButton) findViewById(R.id.nomOK),
                (ImageButton) findViewById(R.id.nomKO)
        };

        // Assignem els listener als botons
        for (ImageButton edit : buttons)
            edit.setOnClickListener(this);

        for (ImageButton edit : buttonsNom)
            edit.setOnClickListener(this);

        // Inicialitzem i afegim el listener del botó d'enviar
        btnEnvia = (Button) findViewById(R.id.btnEnvia);
        btnEnvia.setOnClickListener(this);


        // Inicialitzem i afegim el listener del botó d'enviar
        maps = (Button) findViewById(R.id.map);
        maps.setOnClickListener(this);



        // Inicialitzem els textviews
        textViews = new TextView[]{
                (TextView) findViewById(R.id.carrer),
                (TextView) findViewById(R.id.CP),
                (TextView) findViewById(R.id.poblacio),
                (TextView) findViewById(R.id.telf),
                (TextView) findViewById(R.id.web),
        };

        // Per últim, l'EditText i el label del nom
        editNom = (EditText) findViewById(R.id.editNom);
        nom = (TextView) findViewById(R.id.nom);

        //Inicialitzem els layouts per tal de jugar amb les visibilitat dels elements
        layouts = new LinearLayout[]{
                (LinearLayout) findViewById(R.id.layout1),
                (LinearLayout) findViewById(R.id.layout2)
        };

//        Button maps = (Button) findViewById(R.id.map);
//        maps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("boton de gogole maps pulsad");
//                openGoogleMaps();
//            }
//        });


        //Establim la visualització dels elements a l'estat inicial
        inici();
    }

    //Visualització dels elements a l'estat inicial
    private void inici() {

        for (LinearLayout l : layouts) {
            l.setVisibility(View.INVISIBLE);
        }

        btnEnvia.setVisibility(View.INVISIBLE);

        for (ImageButton b : buttonsNom) {
            b.setVisibility(View.VISIBLE);
        }

        editNom.setVisibility(View.VISIBLE);

        editNom.setText("");
        nom.setText(R.string.nom);

        for(TextView t : textViews){
            t.setText("");
        }
    }

    //Visualització dels elements al pitjar el botó 1
    private void addNom() {

        for (ImageButton b : buttonsNom) {
            b.setVisibility(View.INVISIBLE);
        }
        nom.setText(editNom.getText().toString());
        editNom.setVisibility(View.INVISIBLE);
        for (LinearLayout l : layouts) {
            l.setVisibility(View.VISIBLE);
        }
        btnEnvia.setVisibility(View.VISIBLE);

    }

    //Accions del botó 3 (editar)
    private void edit() {

        //Omplim un "intent" amb les dades a passar a l'altre activitat
        Intent intent = new Intent(this, EditActivity.class);

        //Creem un "bundle" per a afegir dades extra a l'"intent"
        Bundle extras = new Bundle();

        //Afegim les dades si hi ha alguna informació
        if(textViews[0].length()>0) extras.putString("carrer", textViews[0].getText().toString());
        if(textViews[1].length()>0) extras.putString("CP", textViews[1].getText().toString());
        if(textViews[2].length()>0) extras.putString("poblacio", textViews[2].getText().toString());
        if(textViews[3].length()>0) extras.putString("telf", textViews[3].getText().toString());
        if(textViews[4].length()>0) extras.putString("web", textViews[4].getText().toString());

        //Afegim el "bundle" amb informació extra a l'"intent"
        intent.putExtras(extras);

        //Cridem l'activitat
        startActivityForResult(intent, 0);

    }

    /**
     * Recollim la informació de retorn
     * @param requestCode Codi amb el que s'ha llançat l'activitat
     * @param resultCode Codi de retorn, OK o CANCELED
     * @param data Intent amb els extras, la informació que hem definit a l'activitat 2.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // Recollim la informació que se'ns passa des de l'activitat 2
            String[] strings = new String[]{
                data.getExtras().getString("carrer"),
                data.getExtras().getString("CP"),
                data.getExtras().getString("poblacio"),
                data.getExtras().getString("telf"),
                data.getExtras().getString("web")
            };

            //Omplim els TextViews amb la informació rebuda
            for(int i = 0; i < textViews.length; i++)
                textViews[i].setText(strings[i]);

        }
    }

    //Accions del botó 5 (telèfon)
    private void phone() {

        //Si s'ha informat el telèfon, obrirà l'aplicació del mòbil. Si no, mostrarà un missatge informatiu
        if(textViews[3].getText().toString().length()>0) {
            //Omplim un "intent" amb les dades a passar a l'altre activitat
            Intent intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:" + textViews[3].getText().toString()));
            //Cridem l'activitat
            startActivity(intent);
        }else Toast.makeText(this, R.string.telfNoOk, Toast.LENGTH_LONG).show();
    }

    //Accions del botó 6 (visualitzar web)
    @SuppressLint("SetTextI18n")
    private void web(){

        // TODO Cal codificar aquest mètode que visualitzi en el telefon la web de l'usuari
        // Si l'usuari no te web introduïda ha d'aparèixer un Toast amb el missatge
        // "No hi ha plana web disponible". Si la plana web introduïda no disposa dels
        // Caràcters http:// cal afegir-los automàticament.

        Context context = getApplicationContext();
        CharSequence text = "No hi ha plana web disponible";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);

        TextView web = (TextView) findViewById(R.id.web);
        String url = (String) web.getText();
        int sizeurl =  url.length();

        // el navegador el movil me falla y no me abre nada, ni manualmente, pero en teoria debe ir bien
        if (sizeurl>0) {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {

                web.setText("http://" + url);
                url = "http://" + url;

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        }else
        {
//            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
            toast.show();
        }
    }


    private void openGoogleMaps(){

        System.out.println("metodo google maps");
        Intent maps = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=cerdanyola"));
        startActivity(maps);

    }
    private  void checkFields(){
        //TODO Si tots els camps estan plens (no es comprova que la URL sigui correcta)
        //S'emmagatzemen les dades en un fitxer de preferències i es mostra un Toast
        //amb el Missatge. "Dades Enviades". Si hi ha algun cap buit,
        //es mostra un Toast amb el missatge "Dades incompletes"

        // get all childs
        TextView web = (TextView) findViewById(R.id.web);
        TextView carrer = (TextView) findViewById(R.id.carrer);
        TextView cp = (TextView) findViewById(R.id.CP);
        TextView poblacio = (TextView) findViewById(R.id.poblacio);
        TextView telefon = (TextView) findViewById(R.id.telf);


        // comporobar que los datos estan rellenados
        if (web.getText().length()>0 && carrer.getText().length() > 0 && cp.getText().length() >0 && telefon.getText().length() >0 && poblacio.getText().length() > 0){

            SharedPreferences prefs =
                    getSharedPreferences("ficheroconfiguracion",
                            Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("cp", cp.getText().toString());
            editor.putString("poblacio", poblacio.getText().toString());
            editor.putString("web", web.getText().toString());
            editor.putString("telefon", telefon.getText().toString());
            editor.putString("carrer", carrer.getText().toString());
            editor.commit();

            Toast.makeText(this, "Dades Enviades", Toast.LENGTH_LONG).show();


        }else {
            if (carrer.getText().length() <= 0){
                Toast.makeText(this, "Introdueix el nom", Toast.LENGTH_LONG).show();
            } if (cp.getText().length() <= 0){
                Toast.makeText(this, "Introdueix el codi postal", Toast.LENGTH_LONG).show();
            } if (poblacio.getText().length()<=0){
                Toast.makeText(this, "Introdueix la poblacio", Toast.LENGTH_LONG).show();
            } if (telefon.getText().length()<=0){
                Toast.makeText(this, "Introdueix el telefon", Toast.LENGTH_LONG).show();
            } if (web.getText().length()<=0){
                Toast.makeText(this, "Introdueix la web", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Falten totes les dades", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Gestionem els clic dels botons
     * @param view Vista que s'ha clicat
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {

        // Depenent del view al que fem clic fem una acció o una altra

        switch (view.getId()) {
            case R.id.nomOK:
                if(editNom.getText().toString().length()>0)
                    addNom();
                else Toast.makeText(this, "Heu d'escriure quelcom!", Toast.LENGTH_LONG).show();
                break;

            case R.id.nomKO:
                if(editNom.getText().toString().length()<0) {
                    Toast.makeText(this, "Heu d'introduir un nombre", Toast.LENGTH_LONG).show();
                }else {
                    editNom.setText("");
                }
                break;

            case R.id.edit:
                edit();
                break;
            case R.id.delete:
                inici();
                break;
            case R.id.phone:
                phone();
                break;
            case R.id.viewWeb:
                web();
                break;
            case R.id.btnEnvia:
                checkFields();
                break;

            case R.id.map:
                System.out.println("cse googel maps");
                openGoogleMaps();
                break;


            default:
                break;
        }
    }
}