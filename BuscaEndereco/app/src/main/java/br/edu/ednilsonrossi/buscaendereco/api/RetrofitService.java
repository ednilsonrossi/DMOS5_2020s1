package br.edu.ednilsonrossi.buscaendereco.api;

import br.edu.ednilsonrossi.buscaendereco.model.Endereco;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {

    @GET("{endereco}")
    Call<Endereco> getDados(@Path("endereco") String cep);
}
