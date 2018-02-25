package com.duolingo.duostories.view.contents;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duolingo.duostories.MyApplication;
import com.duolingo.duostories.R;
import com.duolingo.duostories.model.utils.ImageUtils;
import com.duolingo.duostories.presenter.adapter.CursosAdapter;

import java.util.Locale;

import br.agr.terras.materialdroid.CircularImageView;

/**
 * Created by leo on 25/02/18.
 */

public class ContentUsuario {
    private LinearLayout content;
    private TextView tvNome;
    private TextView tvEmail;
    private TextView tvId;
    private TextView tvBio;
    private TextView tvXp;
    private TextView tvLingots;
    private TextView tvOfensiva;
    private TextView tvLocal;
    private CircularImageView ivFoto;
    private RecyclerView rvCursos;

    public ContentUsuario(View rootView){
        content = rootView.findViewById(R.id.llContentUsuario);
        tvNome = rootView.findViewById(R.id.tvNomeAmigo);
        tvEmail = rootView.findViewById(R.id.tvEmailAmigo);
        tvId = rootView.findViewById(R.id.tvIdAmigo);
        tvBio = rootView.findViewById(R.id.tvBioAmigo);
        tvXp = rootView.findViewById(R.id.tvXpAmigo);
        tvLingots = rootView.findViewById(R.id.tvLingotsAmigo);
        tvOfensiva = rootView.findViewById(R.id.tvOfensivaAmigo);
        tvLocal = rootView.findViewById(R.id.tvLocalAmigo);
        ivFoto = rootView.findViewById(R.id.ivFotoAmigo);
        rvCursos = rootView.findViewById(R.id.rvCursosAmigo);
    }

    public void setNome(String nome){
        tvNome.setText(nome);
    }

    public void setTvBio(String bio){
        tvBio.setText(bio);
    }

    public void setId(String id){
        tvId.setText(id);
    }

    public void setEmail(String email){
        tvEmail.setText(email);
    }

    public void setLocal(String local){
        tvLocal.setText(local);
    }

    public void setXp(int xp){
        tvXp.setText(String.format(Locale.ENGLISH,"%d XP", xp));
    }

    public void setLingots(int lingots){
        tvLingots.setText(String.format(Locale.ENGLISH,"%d", lingots));
    }

    public void setOfensiva(int ofensiva){
        tvOfensiva.setText(String.format(Locale.ENGLISH,"%d", ofensiva));
    }

    public void setFoto(String path){
        ImageUtils.setImageViewPath(ivFoto, path);
    }

    public void setCursosAdapter(CursosAdapter adapter){
        rvCursos.setHasFixedSize(true);
        rvCursos.setLayoutManager(new LinearLayoutManager(MyApplication.getActivity()));
        rvCursos.setAdapter(adapter);
    }

    public void setVisibility(boolean visible){
        content.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
