package com.brakesindia.testgroundapp1.ui.slideshow;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.brakesindia.testgroundapp1.MainActivity;
import com.brakesindia.testgroundapp1.ProcessActivity;
import com.brakesindia.testgroundapp1.R;
import com.brakesindia.testgroundapp1.databinding.FragmentSlideshowBinding;
import com.brakesindia.testgroundapp1.networking.ApiClient;
import com.brakesindia.testgroundapp1.networking.Requests.NewDataRequest;
import com.brakesindia.testgroundapp1.networking.Requests.SubmitRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlideshowFragment extends Fragment {
    Button submit_end;

    EditText a,b,c,d,e,f,g,h,i,j,k,l;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        getActivity().setTitle("New Poka yoke");
        setInit(root);
        return root;
    }


    private void setInit(View root){
        a = root.findViewById(R.id.edit_text_a);
        b = root.findViewById(R.id.edit_text_b);
        c = root.findViewById(R.id.edit_text_c);
        d = root.findViewById(R.id.edit_text_d);
        e = root.findViewById(R.id.edit_text_e);
        f = root.findViewById(R.id.edit_text_f);
        g = root.findViewById(R.id.edit_text_g);
        h = root.findViewById(R.id.edit_text_h);
        i = root.findViewById(R.id.edit_text_i);
        j = root.findViewById(R.id.edit_text_j);
        k = root.findViewById(R.id.edit_text_k);
        l = root.findViewById(R.id.edit_text_l);
        submit_end = root.findViewById(R.id.submit_button);

        submit_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPyData();
            }
        });
    }

    public void newPyData(){
        System.out.println("Inside new data function");
        NewDataRequest newDataRequest = new NewDataRequest();
        System.out.println("l.getText().toString()");
        newDataRequest.setPlant(l.getText().toString());
        System.out.println("a.getText().toString()");
        newDataRequest.setUnit(Integer.parseInt(a.getText().toString()));
        System.out.println("b.getText().toString()");
        newDataRequest.setCelltype(b.getText().toString());
        newDataRequest.setCellno(Integer.parseInt(k.getText().toString()));
        newDataRequest.setModel(c.getText().toString());
        newDataRequest.setDocno(d.getText().toString());
        newDataRequest.setProcess(e.getText().toString());
        newDataRequest.setPyorder(Integer.parseInt(f.getText().toString()));
        newDataRequest.setPyno(g.getText().toString());
        newDataRequest.setPydesc(h.getText().toString());
        newDataRequest.setPypurpose(i.getText().toString());
        newDataRequest.setPytype(j.getText().toString());

        submitData(newDataRequest);

    }

    private void submitData(NewDataRequest submitRequests) {
        Call<Void> call = ApiClient.getUserService().updateNewData(submitRequests);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Display success message
                    showSuccessDialog();

                    // Delay moving to MainActivity
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            moveToMainActivity();
                        }
                    }, 3000); // 3 seconds delay
                } else {
                    Toast.makeText(getContext(), "Failed to submit data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("SubmitData", "Network error", t);
            }
        });
    }


    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Submitting successfully")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do nothing, just close the dialog
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
    private void moveToMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

}