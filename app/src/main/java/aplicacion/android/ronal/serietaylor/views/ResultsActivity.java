package aplicacion.android.ronal.serietaylor.views;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAImage;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;

import java.util.ArrayList;
import java.util.List;

import aplicacion.android.ronal.serietaylor.R;
import aplicacion.android.ronal.serietaylor.adapter.ResultsAdapter;
import aplicacion.android.ronal.serietaylor.data.*;

public class ResultsActivity extends AppCompatActivity {

    String query;
    ProgressBar pbResults;
    TextView txtAlert;
    int tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        txtAlert = (TextView) findViewById(R.id.txtAlert);

        pbResults = (ProgressBar)findViewById(R.id.pbResults);
        configResults();
        resultado();

    }

    public void resultado() {
        Task task = new Task();
        task.execute();
    }


    public void configResults(){
        Bundle results = getIntent().getExtras();

        if(results!=null) {
            query = results.getString("query");

            if(!query.contains("serie"))
            {
                tipo = 2;
            }
            else
            {
                tipo = 1;
            }
        }
    }

    public class Task extends AsyncTask<WAQueryResult,Void,WAQueryResult> {

        private List<Results> mResults = new ArrayList<Results>();

        private String appid = "APP_ID";//colocar el id que brinda la api de wolfram

        private WAQueryResult queryResult;

        private RecyclerView mRecyclerView;

        private RecyclerView.Adapter mAdapter;

        private RecyclerView.LayoutManager mLayout;

        protected void onPreExecute() {

        }

        @Override
        protected WAQueryResult doInBackground(WAQueryResult... urls) {
            String input = query;

            if(input.length()>7 || tipo ==2)
            {
                if(input.length()<3)
                {
                    return null;
                }

                input+=")";

                WAEngine engine = new WAEngine();
                engine.setAppID(appid);
                //engine.addFormat("plaintext");
                engine.addFormat("image");

                // Create the query.
                WAQuery query = engine.createQuery();
                query.setInput(input);
                queryResult = null;
                try {
                    queryResult = engine.performQuery(query);
                } catch (WAException e) {
                    return null;
                }
                return queryResult;
            }
            return null;
        }

        @Override
        protected void onPostExecute(WAQueryResult queryResult) {
            if(queryResult == null)
            {
                txtAlert.setText("Error");
                pbResults.setVisibility(View.INVISIBLE);
                txtAlert.setVisibility(View.VISIBLE);
                return;
            }
            else {
                if (queryResult.isError()) {
                    System.out.println("Query error");
                    System.out.println("  error code: " + queryResult.getErrorCode());
                    System.out.println("  error message: " + queryResult.getErrorMessage());

                } else if (!queryResult.isSuccess()) {
                    System.out.println("Query was not understood; no results available.");

                } else {

                    if(queryResult.getPods()[1].getTitle().contains("Series expansion ") || tipo ==2) {

                        for (WAPod pod : queryResult.getPods()) {
                            System.out.println("Numero Pod" + queryResult.getNumPods());
                            if (!pod.isError()) {
                                System.out.println("Numero Subpod" + pod.getNumSubpods());
                                for (WASubpod subpod : pod.getSubpods()) {
                                    for (Object element : subpod.getContents()) {
                                        //System.out.println("Esta es la URL: "+((WAImage) element).getURL());
                                        mResults.add(new Results(pod.getTitle(), ((WAImage) element).getURL()));
                                    }
                                }
                            }
                        }
                        configTartetas();
                    }
                    else
                    {
                        txtAlert.setText("Ingrese busqueda valida");
                        pbResults.setVisibility(View.INVISIBLE);
                        txtAlert.setVisibility(View.VISIBLE);
                    }
                }

            }

        }



        private void configTartetas() {

            if(mResults.size() == 0)
            {
                txtAlert.setVisibility(View.VISIBLE);
                pbResults.setVisibility(View.INVISIBLE);
            }
            else {
                mRecyclerView = (RecyclerView) ResultsActivity.this.findViewById(R.id.cdView);
                mLayout = new LinearLayoutManager(ResultsActivity.this);
                mAdapter = new ResultsAdapter(mResults, R.layout.item_results, new ResultsAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Results results, int position) {

                    }
                });
                mRecyclerView.setLayoutManager(mLayout);
                mRecyclerView.setAdapter(mAdapter);
                pbResults.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }


        }
    }
}
