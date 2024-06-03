package com.brakesindia.testgroundapp1.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brakesindia.testgroundapp1.ApproverEditActivity;
import com.brakesindia.testgroundapp1.ProcessActivity;
import com.brakesindia.testgroundapp1.R;
import com.brakesindia.testgroundapp1.networking.ApiClient;
import com.brakesindia.testgroundapp1.networking.Response.EditResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditingFragment extends Fragment {

    private LinearLayout buttonContainer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_editing, container, false);
        buttonContainer = root.findViewById(R.id.button_container);
        setInit();
        return root;
    }
    private void setInit() {
        Call<List<EditResponse>> call = ApiClient.getUserService().getEditdata();
        call.enqueue(new Callback<List<EditResponse>>() {
            @Override
            public void onResponse(Call<List<EditResponse>> call, Response<List<EditResponse>> response) {
                if (response.isSuccessful()) {
                    List<EditResponse> editResponse = response.body();
                    if (editResponse != null) {
                        Map<String, List<EditResponse>> groupedData = groupDataByDate(editResponse);
                        for (Map.Entry<String, List<EditResponse>> entry : groupedData.entrySet()) {
                            addDateTextView(entry.getKey());
                            for (EditResponse resp : entry.getValue()) {
                                addButton(resp);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<EditResponse>> call, Throwable t) {
                System.out.println("error is: " + t);
            }
        });
    }
    private Map<String, List<EditResponse>> groupDataByDate(List<EditResponse> editResponses) {
        Map<String, List<EditResponse>> groupedData = new HashMap<>();
        for (EditResponse response : editResponses) {
            String date = response.getDate();
            if (!groupedData.containsKey(date)) {
                groupedData.put(date, new ArrayList<>());
            }
            groupedData.get(date).add(response);
        }
        return groupedData;
    }
    private void addDateTextView(String date) {
        TextView textView = new TextView(getContext());
        textView.setText(date);
        textView.setTextSize(38);
        textView.setTextColor(Color.BLACK);
        buttonContainer.addView(textView);
    }

    @SuppressLint("SetTextI18n")
    private void addButton(final EditResponse response) {
        // Create a new Button instance
        Button button = new Button(getContext());

        // Set text for the button using the data from the response
        String buttonText =
                "Plant: " + response.getPlant() +
                        "\nUnit: " + response.getUnit() +
//                        "\nCell Type: " + response.getCelltype() +
//                        "\nCell number: " + response.getCellno()+
                        "\nModel: "+response.getModel()+
                        "\nProcess: "+response.getProcess();

        SpannableString spannableString = new SpannableString(buttonText);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), buttonText.indexOf("Process:"), buttonText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        button.setText(spannableString);
        button.setTextSize(16);
        button.setTextColor(Color.BLACK);
        button.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL); // Left align text
        button.setPadding(30, 50, 20, 50);  // Add padding
        button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.approve_button_background));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 200, 30); // Add margin (adjust as needed)
        button.setLayoutParams(params);

        boolean buttonExists = false;
        for (int i = 0; i < buttonContainer.getChildCount(); i++) {
            View view = buttonContainer.getChildAt(i);
            if (view instanceof Button) {
                Button existingButton = (Button) view;
                if (existingButton.getText().toString().equals(buttonText) && existingButton.getTag().equals(response.getDate())) {
                    buttonExists = true;
                    break;
                }
            }
        }

        if(!buttonExists) {
            button.setText(buttonText);

            // Set onClickListener for the button
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Extracting date from the button's container (assuming it's a LinearLayout)
                    String date = (String) v.getTag();
                    System.out.println("Sending date:"+date);
                    System.out.println("\n\n\n");


                    Intent intent = new Intent(getContext(), ApproverEditActivity.class);
                    intent.putExtra("spinner_a_val", response.getPlant());
                    intent.putExtra("spinner_b_val", String.valueOf(response.getUnit()));
                    intent.putExtra("spinner_c_val", response.getCelltype());
                    intent.putExtra("spinner_d_val", String.valueOf(response.getCellno()));
                    intent.putExtra("spinner_e_val", response.getModel());
                    intent.putExtra("spinner_f_val", response.getProcess());
                    intent.putExtra("date", date);
                    startActivity(intent);
                }
            });
            // Add the button to the buttonContainer LinearLayout
            button.setTag(response.getDate());
            buttonContainer.addView(button);
        }
    }
}
