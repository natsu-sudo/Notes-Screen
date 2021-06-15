package com.coding.aisleassignment.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.coding.aisleassignment.Constants;
import com.coding.aisleassignment.R;
import com.coding.aisleassignment.apiservice.ApiService;
import com.coding.aisleassignment.apiservice.ApiServiceInterface;
import com.coding.aisleassignment.databinding.FragmentOTPValidationBinding;
import com.coding.aisleassignment.pojo.GetResponse;
import com.coding.aisleassignment.pojo.Invites;
import com.coding.aisleassignment.pojo.Token;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.coding.aisleassignment.Constants.ONE_MINUTE;
import static com.coding.aisleassignment.Constants.ONE_SECOND;
import static com.coding.aisleassignment.Constants.OTP;

public class OTPValidationFragment extends Fragment {
    private static final String TAG = "OTPValidationFragment";
    private FragmentOTPValidationBinding binding;
    private CountDownTimer timer;
    long second = ONE_MINUTE;
    Retrofit retrofit;

    public OTPValidationFragment() {
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
        binding = FragmentOTPValidationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        String argument = OTPValidationFragmentArgs.fromBundle(getArguments()).getNumber();
        binding.otpNumber.setText(argument);
        binding.continueOtp.setOnClickListener(v -> {
            callRetrofit(argument);
        });
        timer = new CountDownTimer(ONE_MINUTE, ONE_SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                second -= ONE_SECOND;
                binding.timerForOtp.setText(convertToSecond(second));
            }

            @Override
            public void onFinish() {
                new MaterialAlertDialogBuilder(getActivity())
                        .setTitle(getString(R.string.session))
                        .setMessage(getString(R.string.token_expired))
                        .setPositiveButton(getString(R.string.try_again), (dialog, which) -> {
                            NavHostFragment.findNavController(OTPValidationFragment.this)
                                    .navigate(OTPValidationFragmentDirections.Companion.actionOTPValidationFragmentToRegistrationFragment());
                        })
                        .setNegativeButton(getString(R.string.cancel), (dialog, which) -> {
                            NavHostFragment.findNavController(OTPValidationFragment.this)
                                    .navigate(OTPValidationFragmentDirections.Companion.actionOTPValidationFragmentToRegistrationFragment());
                        }).
                        setCancelable(true).show();

                timer.cancel();
            }
        };
        timer.start();
    }

    private String convertToSecond(long second) {
        if(second<=10000){
            return String.format("00:0%s", second / 1000);
        }
        return String.format("00:%s", second / 1000);
    }

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
    }

    private void callRetrofit(String argument) {
        boolean status=validate();
        if(status){return;}
        retrofit = ApiService.getInstance();
        ApiServiceInterface apiServiceInterface=retrofit.create(ApiServiceInterface.class);
        String otpNumber=binding.otpCode.getText().toString();
        HashMap<String,String>map = new HashMap<>();
        map.put(Constants.NUMBER,argument);
        map.put(OTP,otpNumber);
        Call<Token> call = apiServiceInterface.verifyOTP(map);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Log.d(TAG, "onResponse: "+response.body().getToken());
                if(response.body().getToken()!=null){
                    callWithToken(response.body().getToken());
                }else{
                    new MaterialAlertDialogBuilder(getActivity())
                            .setTitle(getString(R.string.invalid))
                            .setMessage(getString(R.string.try_again_1))
                            .setPositiveButton(getString(R.string.ok), (dialog, which) -> {

                            })
                            .setNegativeButton(getString(R.string.cancel), (dialog, which) -> {
                            }).
                            setCancelable(true).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                new MaterialAlertDialogBuilder(getActivity())
                        .setTitle(getString(R.string.wrong))
                        .setMessage(getString(R.string.mention_number))
                        .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                            NavHostFragment.findNavController(OTPValidationFragment.this).navigate(OTPValidationFragmentDirections.Companion
                                    .actionOTPValidationFragmentToRegistrationFragment());
                        })
                        .setNegativeButton(getString(R.string.cancel), (dialog, which) -> {
                            NavHostFragment.findNavController(OTPValidationFragment.this).navigate(OTPValidationFragmentDirections.Companion
                                    .actionOTPValidationFragmentToRegistrationFragment());
                        }).
                        setCancelable(true).show();
            }
        });


    }

    private void callWithToken(String token) {
        Log.d(TAG, "callWithToken: ");
        ApiServiceInterface apiServiceInterface=retrofit.create(ApiServiceInterface.class);
        Call<GetResponse> getResponse= apiServiceInterface.getResponseFromToken(token);
        getResponse.enqueue(new Callback<GetResponse>() {
            @Override
            public void onResponse(Call<GetResponse> call, Response<GetResponse> response) {
                Log.d(TAG, "onResponse: "+response.body());
                GetResponse getResponse1=response.body();
                Log.d(TAG, "onResponse: "+getResponse1.getInvites().getProfiles().size());
                NavHostFragment.findNavController(OTPValidationFragment.this).navigate(OTPValidationFragmentDirections.Companion
                        .actionOTPValidationFragmentToHomeFragment());

            }

            @Override
            public void onFailure(Call<GetResponse> call, Throwable t) {
                new MaterialAlertDialogBuilder(getActivity())
                        .setTitle(getString(R.string.wrong))
                        .setMessage(getString(R.string.mention_number))
                        .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                            NavHostFragment.findNavController(OTPValidationFragment.this).navigate(OTPValidationFragmentDirections.Companion
                                    .actionOTPValidationFragmentToRegistrationFragment());
                        })
                        .setNegativeButton(getString(R.string.cancel), (dialog, which) -> {
                            NavHostFragment.findNavController(OTPValidationFragment.this).navigate(OTPValidationFragmentDirections.Companion
                                    .actionOTPValidationFragmentToRegistrationFragment());
                        }).
                        setCancelable(true).show();
            }
        });


    }

    private boolean validate() {
        if(binding.otpCode.getText().toString().length()==0){
            Snackbar.make(binding.getRoot(),getString(R.string.madatory),Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.ok), v -> {

                    })
                    .show();
            return true;
        }
        return false;
    }
}