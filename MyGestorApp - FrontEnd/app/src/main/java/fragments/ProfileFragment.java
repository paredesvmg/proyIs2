package fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.is2.mygestorapp.Homepage;
import com.is2.mygestorapp.R;
import com.is2.mygestorapp.User;

import org.json.JSONException;
import org.json.JSONObject;

import helper.Connection;

import helper.URLs;

public class ProfileFragment extends Fragment {

    private FloatingActionButton editButton;
    EditMyProfileFragment editMyProfileFragment = new EditMyProfileFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        final Bundle bundle = new Bundle();
        final Connection connection = new Connection();
        JSONObject usuarioJson = null;
        User user = null;
        final Integer idUsuario, idRol;
        final String nombre, usuario, correo, contrasenha, telefono, message,url;

        ((Homepage) getActivity()).getSupportActionBar().setTitle("Mi Perfil");


        //Extrae los campos pasados como argumentos
        idUsuario = getArguments().getInt(Homepage.ID_KEY);
        nombre =  getArguments().getString(Homepage.NOMBRE_KEY);
        usuario =  getArguments().getString(Homepage.USUARIO_KEY);
        correo =  getArguments().getString(Homepage.CORREO_KEY);
        telefono =  getArguments().getString(Homepage.TELEFONO_KEY);
        idRol = getArguments().getInt(Homepage.ID_ROL_KEY);
        contrasenha = getArguments().getString(Homepage.CONTRASENHA_KEY);

        url = URLs.URL_USER + idUsuario;
        message = connection.executeGet(url, getContext());
        try {
            usuarioJson = new JSONObject(message);
            user = new User(usuarioJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Presenta los campos extraidos
        TextView profileName = (TextView) view.findViewById(R.id.profile_name);
        TextView profileUser = (TextView) view.findViewById(R.id.profile_user);
        TextView profileEmail = (TextView) view.findViewById(R.id.profile_email);
        TextView profilePhone = (TextView) view.findViewById(R.id.profile_phone);
        TextView profileTipo = (TextView) view.findViewById(R.id.profile_tipo);

        profileName.setText(nombre);
        profileUser.setText(usuario);
        profileEmail.setText(correo);
        profilePhone.setText(telefono);
        profileTipo.setText(user.getIdRol().getNombreRol());

        editButton = (FloatingActionButton) view.findViewById(R.id.fab);

        //Si presiona el boton flotante, empieza la actividad EditUser.
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putInt(Homepage.ID_KEY, idUsuario);
                bundle.putString(Homepage.NOMBRE_KEY, nombre);
                bundle.putString(Homepage.USUARIO_KEY, usuario);
                bundle.putString(Homepage.CORREO_KEY, correo);
                bundle.putString(Homepage.TELEFONO_KEY, telefono);
                bundle.putInt(Homepage.ID_ROL_KEY, idRol);
                bundle.putString(Homepage.CONTRASENHA_KEY, contrasenha);

                editMyProfileFragment.setArguments(bundle);

                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, editMyProfileFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;

    }
}