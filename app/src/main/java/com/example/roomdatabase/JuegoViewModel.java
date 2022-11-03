package com.example.roomdatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class JuegoViewModel extends AndroidViewModel {

    JuegoRepositorio juegoRepositorio;

    MutableLiveData<List<Juego>> listJuegoMutableLiveData = new MutableLiveData<>();

    public JuegoViewModel(@NonNull Application application) {
        super(application);

        juegoRepositorio = new JuegoRepositorio();

        listJuegoMutableLiveData.setValue(juegoRepositorio.obtener());
    }

    MutableLiveData<List<Juego>> obtener(){
        return listJuegoMutableLiveData;
    }

    void insertar(Juego juego){
        juegoRepositorio.insertar(juego, new JuegoRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Juego> juegos) {
                listJuegoMutableLiveData.setValue(juegos);
            }
        });
    }

    void eliminar(Juego juego){
        juegoRepositorio.eliminar(juego, new JuegoRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Juego> juegos) {
                listJuegoMutableLiveData.setValue(juegos);
            }
        });
    }

    void actualizar(Juego juego, float valoracion){
        juegoRepositorio.actualizar(juego, valoracion, new JuegoRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Juego> juegos) {
                listJuegoMutableLiveData.setValue(juegos);
            }
        });
    }
}
