package com.fiap.rumenigue.paiflogistica.api;

import com.fiap.rumenigue.paiflogistica.model.Endereco;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by nigue on 15/09/2017.
 */

public interface CEPApi {

    @GET("cep/{codigo}")
    Call<Endereco> getEndereco(@Path("codigo") String codigo);
}
