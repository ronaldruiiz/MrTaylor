package aplicacion.android.ronal.serietaylor.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;




import aplicacion.android.ronal.serietaylor.R;


public class CalculadoraActivity extends AppCompatActivity {

    private EditText etSerie;
    private int tipo;
    private String query;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        query ="";

        etSerie = (EditText) findViewById(R.id.etSerie);
        bundle = getIntent().getExtras();

        if(bundle.getInt("tipo")==1) {

            etSerie.setInputType(InputType.TYPE_CLASS_PHONE);
        }

    }

    public void executeResults(View view) {

        query ="";


        if(bundle.getInt("tipo")==2) {
            query = "serie(";
        }
        query+= etSerie.getText().toString();

        Intent intent = new Intent(this,ResultsActivity.class);
        intent.putExtra("query",query);

        startActivity(intent);


    }

}