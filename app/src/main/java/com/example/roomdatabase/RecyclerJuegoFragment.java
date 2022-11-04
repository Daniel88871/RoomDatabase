package com.example.roomdatabase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


import com.example.roomdatabase.databinding.FragmentRecyclerJuegosBinding;
import com.example.roomdatabase.databinding.ViewholderJuegoBinding;

import java.util.List;

public class RecyclerJuegoFragment extends Fragment {
    private FragmentRecyclerJuegosBinding binding;
    private JuegoViewModel juegoViewModel;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRecyclerJuegosBinding.inflate(inflater, container, false)).getRoot();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT  | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int posicion = viewHolder.getAdapterPosition();
                Juego juego = juegoAdapter.obtenerJuego(posicion);
                juegoViewModel.eliminar(juego);

            }
        }).attachToRecyclerView(binding.recyclerView);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        juegoViewModel = new ViewModelProvider(requireActivity()).get(JuegoViewModel.class);
        navController = Navigation.findNavController(view);

        // navegar a NuevoElemento cuando se hace click en el FloatingActionButton
        binding.irANuevoJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_recyclerJuegoFragment_to_nuevoJuegoFragment);
            }
        });

        // crear el Adaptador
        JuegoAdapter juegoAdapter = new JuegoAdapter();

        // asociar el Adaptador con el RecyclerView
        binding.recyclerView.setAdapter(juegoAdapter);

        // obtener el array de Elementos, y pasarselo al Adaptador
        juegoViewModel.obtener().observe(getViewLifecycleOwner(), new Observer<List<Juego>>() {
            @Override
            public void onChanged(List<Juego> juego) {
                juegoAdapter.establecerLista(juego);
            }
        });
    }

    class JuegoAdapter extends RecyclerView.Adapter<JuegoViewHolder> {

        public Juego obtenerJuego(int posicion){
            return juegos.get(posicion);
        }

        // referencia al Array que obtenemos del ViewModel
        List<Juego> juegos;

        // crear un nuevo ViewHolder
        @NonNull
        @Override
        public JuegoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new JuegoViewHolder(ViewholderJuegoBinding.inflate(getLayoutInflater(), parent, false));
        }

        // rellenar un ViewHolder en una posición del Recycler con los datos del elemento que
        // esté en esa misma posición en el Array
        @Override
        public void onBindViewHolder(@NonNull JuegoViewHolder holder, int position) {

            Juego juego = juegos.get(position);

            holder.binding.nombre.setText(juego.nombre);
            holder.binding.valoracion.setRating(juego.valoracion);

            holder.binding.valoracion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if(fromUser) {
                        juegoViewModel.actualizar(juego, rating);
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    juegoViewModel.seleccionar(juego);
                    navController.navigate(R.id.action_recyclerJuegoFragment_to_mostrarJuegoFragment);
                }
            });
        }

        // informar al Recycler de cuántos elementos habrá en la lista
        @Override
        public int getItemCount() {
            return juegos != null ? juegos.size() : 0;
        }

        // establecer la referencia a la lista, y notificar al Recycler para que se regenere
        public void establecerLista(List<Juego> juegos){
            this.juegos = juegos;
            notifyDataSetChanged();
        }
    }

    // Clase para inicializar el ViewBinding en los ViewHolder
    class JuegoViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderJuegoBinding binding;

        public JuegoViewHolder(ViewholderJuegoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}