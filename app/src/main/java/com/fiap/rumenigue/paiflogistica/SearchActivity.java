package com.fiap.rumenigue.paiflogistica;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fiap.rumenigue.paiflogistica.api.CEPApi;
import com.fiap.rumenigue.paiflogistica.model.Endereco;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    EditText etCep;
    Button btnSearch;
    TextView tvTipo, tvLogradouro, tvBairro, tvCidade, tvEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etCep = (EditText)findViewById(R.id.etCep);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        tvTipo = (TextView)findViewById(R.id.tvTipo);
        tvLogradouro = (TextView)findViewById(R.id.tvLogradouro);
        tvBairro = (TextView)findViewById(R.id.tvBairro);
        tvCidade = (TextView)findViewById(R.id.tvCidade);
        tvEstado = (TextView)findViewById(R.id.tvEstado);
    }

    private Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl("http://correiosapi.apphb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void buscar(View view) {
        CEPApi api = getRetrofit().create(CEPApi.class);

        api.getEndereco(etCep.getText().toString())
                .enqueue(new Callback<Endereco>() {
                    @Override
                    public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                        if (response.body() != null){
                            Endereco res = response.body();

                            tvTipo.setText(getResources().getString(R.string.tipo_logradouro) + res.getTipoDeLogradouro());
                            tvLogradouro.setText(getResources().getString(R.string.logradouro) + res.getLogradouro());
                            tvBairro.setText(getResources().getString(R.string.bairro) + res.getBairro());
                            tvCidade.setText(getResources().getString(R.string.cidade) + res.getCidade());
                            tvEstado.setText(getResources().getString(R.string.estado) + res.getEstado());
                        }
                    }

                    @Override
                    public void onFailure(Call<Endereco> call, Throwable t) {
                        Toast.makeText(SearchActivity.this, R.string.search_fail,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
