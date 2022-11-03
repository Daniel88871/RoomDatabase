package com.example.roomdatabase;

import java.util.ArrayList;
import java.util.List;

public class JuegoRepositorio {

    List<Juego> cosas = new ArrayList<>();

    interface Callback {
        void cuandoFinalice(List<Juego> elementos);
    }

    JuegoRepositorio() {
        cosas.add(new Juego("Minions", " Los minions son unidades infinitas que se entrenan automaticamente y aparecen en batalla con el proposito de atacar unidades enemigas en sus lineas de batalla."));
        cosas.add(new Juego("Campeones", "Los campeones son guerreros reunidos de bastas regiones del universo que han venido a luchar en los Campos de la Justicia. Riot Games desarrollará nuevos campeones constantemente. Cada campeón está individualmente diseñado para tener habilidades específicas y formas de juego que el jugador deberá adaptarse. Un grupo usa la fuerza bruta y el poder, otro usa la precisión y la astucia, mientras que el último grupo usa la visión supernatural y la magia."));
        cosas.add(new Juego("Torretas", "Las torretas son un objetivo en League of Legends. Las torretas son estructuras que otorgan visión de la zona, ayudan a controlar territorio y hacen mucho daño si detectan a un enemigo cerca."));
        cosas.add(new Juego("Inhibidores", "Los inhibidores son construcciones que bloquean el entrenamiento de Super Súbditos enemigos en el mismo carril. Tienen 4000 puntos de vida y no tienen armadura ni resistencia mágica. Una vez que el inhibidor del equipo enemigo es destruido, el Nexo comenzará a liberar Super Súbditos."));
        cosas.add(new Juego("Nexo", "El nexo es el principal objetivo de League of Legends para ganar una partida. Tiene 5,500 de vida y 20 de regeneración de vida por segundo. Si el equipo enemigo rompe el nexo, se acaba la partida."));
        cosas.add(new Juego("Objetos", "Un item es un artefacto llevado por el Campeón para mejorar su performance en batalla. Los items pueden obtenerse sólo atraves del intem ''Shop'' ubicado en el vendedor de items, donde tienes cinco categorias, divididas por su efecto. Tambien pueden ser organizada en basicas, avanzadas y lejendarias. Un campeon puede llevar con sigo sólo 6 items a la vez, haciendo la seleccion de items una parte importante del logro de la victoria."));
    }

    List<Juego> obtener() {
        return cosas;
    }

    void insertar(Juego juego, Callback callback){
        cosas.add(juego);
        callback.cuandoFinalice(cosas);
    }

    void eliminar(Juego juego, Callback callback) {
        cosas.remove(juego);
        callback.cuandoFinalice(cosas);
    }

    void actualizar(Juego juego, float valoracion, Callback callback) {
        juego.valoracion = valoracion;
        callback.cuandoFinalice(cosas);
    }
}
