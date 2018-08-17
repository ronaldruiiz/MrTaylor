package aplicacion.android.ronal.serietaylor.views;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import aplicacion.android.ronal.serietaylor.R;

public class HomeActivity extends AppCompatActivity {
    public final int CALCULADORA_NOMAL=1;
    public final int CALCULADORA_SERIE=2;

    FloatingActionButton fbInfo;
    ImageView imgSerie;
    ImageView imgNormal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imgSerie = (ImageView)findViewById(R.id.imgSerie);
        imgNormal = (ImageView)findViewById(R.id.imgNormal);
        fbInfo = (FloatingActionButton)findViewById(R.id.fbInfo);

        Picasso.get().load(R.drawable.cal_serie).fit().into(imgSerie);
        Picasso.get().load(R.drawable.cal_normal).fit().into(imgNormal);

        fbInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, InfoActivity.class);

                startActivity(intent);
            }
        });




    }

    public void intent(View view) {
        Intent intent=new Intent(this,CalculadoraActivity.class);;

        switch(view.getId())
        {
            case R.id.calNormal:

                    intent.putExtra("tipo",CALCULADORA_NOMAL);

                    startActivity(intent);
                break;
                case R.id.calSerie:
                    intent = new Intent(this,CalculadoraActivity.class);

                    intent.putExtra("tipo",CALCULADORA_SERIE);
                startActivity(intent);
                break;
        }

    }
}
