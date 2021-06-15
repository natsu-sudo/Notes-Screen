package com.coding.aisleassignment.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coding.aisleassignment.Constants;
import com.coding.aisleassignment.R;
import com.coding.aisleassignment.apiservice.ApiService;
import com.coding.aisleassignment.apiservice.ApiServiceInterface;
import com.coding.aisleassignment.databinding.FragmentRegistrationBinding;
import com.coding.aisleassignment.pojo.Login;
import com.coding.aisleassignment.pojo.LoginResponse;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistrationFragment extends Fragment {
    private static final String TAG = "RegistrationFragment";
    Retrofit retrofit;
    private FragmentRegistrationBinding binding;
    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.continueRegistration.setOnClickListener(v ->
                callRetrofit());
    }

    private void callRetrofit() {
        boolean status=validate();
        if(status){return;}
        retrofit=ApiService.getInstance();
        ApiServiceInterface apiServiceInterface=retrofit.create(ApiServiceInterface.class);
        String sendNumber=binding.countryCode.getText().toString()+
                binding.phoneNumber.getText().toString();

        Login login = new Login(Constants.NUMBER,sendNumber);
        Call<LoginResponse> call = apiServiceInterface.sendPhoneNumberToServer(login);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                serverResponse(response.body().getStatus());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                new MaterialAlertDialogBuilder(getActivity())
                        .setTitle(getString(R.string.error))
                        .setMessage(t.getMessage())
                        .setPositiveButton(getString(R.string.ok), (dialog, which) -> {

                        })
                        .setNegativeButton(getString(R.string.cancel), (dialog, which) -> {

                        }).
                        setCancelable(true).show();
            }
        });

    }

    private void serverResponse(Boolean status) {
        if(status){
            NavHostFragment.findNavController(RegistrationFragment.this).navigate(RegistrationFragmentDirections.Companion
                    .actionRegistrationFragmentToOTPValidationFragment(binding.countryCode.getText().toString()+
                            binding.phoneNumber.getText().toString()));
        }else {
            new MaterialAlertDialogBuilder(getActivity())
                    .setTitle(getString(R.string.wrong))
                    .setMessage(getString(R.string.mention_number))
                    .setPositiveButton(getString(R.string.ok), (dialog, which) -> {

                    })
                    .setNegativeButton(getString(R.string.cancel), (dialog, which) -> {

                    }).
                    setCancelable(true).show();
        }
    }

    private boolean validate() {
        if (binding.countryCode.getText().toString().length()==0 ||binding.phoneNumber.getText().toString().length()==0){
            Snackbar.make(binding.getRoot(),getString(R.string.madatory),Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.ok), v -> {

                    })
            .show();
            return true;
        }
        return false;
    }
}