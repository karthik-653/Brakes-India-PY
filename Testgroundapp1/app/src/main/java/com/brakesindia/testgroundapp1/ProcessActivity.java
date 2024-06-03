package com.brakesindia.testgroundapp1;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.brakesindia.testgroundapp1.networking.ApiClient;
import com.brakesindia.testgroundapp1.networking.Requests.PydataRequest;
import com.brakesindia.testgroundapp1.networking.Response.CheckOneResponse;
import com.brakesindia.testgroundapp1.networking.Response.DocResponse;
import com.brakesindia.testgroundapp1.networking.Response.PydataResponse;
import com.brakesindia.testgroundapp1.networking.Requests.SubmitRequest;
import com.brakesindia.testgroundapp1.ui.gallery.GalleryFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProcessActivity extends AppCompatActivity {
    String plant, celltype,model,process, username, docno, date, recdate;
    int unit, cellno;

    ArrayList<String> checklist;
    Date currentDate;
    List<RadioButton> selectedRadioButtons = new ArrayList<>();
    Button submit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_process);
        setInit();
        Oldvalues();
        getDocnoData();
        setupSubmitButton();
        setupBackButtonNavigation();

    }

    private void setInit(){
        submit = findViewById(R.id.final_submit);
        Intent intent = getIntent();
        plant = intent.getStringExtra("spinner_a_val");
        unit = parseIntOrDefault(intent.getStringExtra("spinner_b_val"), 0); // default value if null
        celltype = intent.getStringExtra("spinner_c_val");
        cellno = parseIntOrDefault(intent.getStringExtra("spinner_d_val"), 0); // default value if null
        model = intent.getStringExtra("spinner_e_val");
        process = intent.getStringExtra("spinner_f_val");
        recdate = intent.getStringExtra("date");

        checklist = new ArrayList<>();

        currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = dateFormat.format(currentDate);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");

        System.out.println("CurrentDate:"+ date);
        System.out.println("Name received are:"+username);
        System.out.println("Values received are:"+plant);
        System.out.println("Values received are:"+unit);
        System.out.println("Values received are:"+celltype);
        System.out.println("Values received are:"+cellno);
        System.out.println("Values received are:"+model);
        System.out.println("Values received are:"+process);
        System.out.println("Values received are:"+recdate);

        if(recdate==null){
            recdate=date;
            System.out.println("NO DATE CHOSEN. Date taken is: "+recdate);
        }

    }


    private void Oldvalues(){
        Call<CheckOneResponse> call = ApiClient.getUserService().getCheck(plant, unit, celltype, cellno, model, process,recdate);
        call.enqueue(new Callback<CheckOneResponse>() {
            @Override
            public void onResponse(Call<CheckOneResponse> call, Response<CheckOneResponse> response) {
                System.out.println("Check code: "+response.code());
                if(response.isSuccessful()){
                    CheckOneResponse checkOneResponse = response.body();
                    List<String> checkdata = checkOneResponse.getCheck();
                    if(checkdata != null){
                        checklist.clear();
                        checklist.addAll(checkdata);
                        System.out.println("Check List : "+ checklist);
                    }
                    else{
                        System.out.println("No data in approver check");
                    }
                }
                else{
                    System.out.println("no response in approver check");
                }
            }
            @Override
            public void onFailure(Call<CheckOneResponse> call, Throwable t) {
            }
        });
        getallData();
    }
    private int parseIntOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            Log.e("parseIntOrDefault", "Failed to parse integer: " + value);
            return defaultValue;
        }
    }

    private void getDocnoData(){
        Call<DocResponse> call = ApiClient.getUserService().getDocno(plant, unit, celltype, cellno, model);

        call.enqueue(new Callback<DocResponse>() {
            @Override
            public void onResponse(Call<DocResponse> call, Response<DocResponse> response) {
                if (response.isSuccessful()) {
                    DocResponse docResponse = response.body();
                    if (docResponse != null) {
                        docno = docResponse.getDocno();
                        System.out.println("Docno : "+docno);
                    }
                    else{
                        System.out.println("Null Response getDocno");
                    }
                } else{
                    System.out.println("No Response getDocno");
                }
            }
            @Override
            public void onFailure(Call<DocResponse> call, Throwable t) {
                System.out.println("Failure getDocno API");
            }
        });
    }
    private void getallData(){
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        PydataRequest pydataRequest = new PydataRequest();
        pydataRequest.setPlant(plant);
        pydataRequest.setUnit(unit);
        pydataRequest.setCelltype(celltype);
        pydataRequest.setCellno(cellno);
        pydataRequest.setModel(model);
        pydataRequest.setProcess(process);

        Call<List<PydataResponse>> call = ApiClient.getUserService().getPydata(pydataRequest.getPlant(),
                pydataRequest.getUnit(), pydataRequest.getCelltype(), pydataRequest.getCellno(),
                pydataRequest.getModel(), pydataRequest.getProcess());

        call.enqueue(new Callback<List<PydataResponse>>() {
            @Override
            public void onResponse(Call<List<PydataResponse>> call, Response<List<PydataResponse>> response) {
                System.out.println(response.body() + " front");
                if (response.isSuccessful()) {
                    List<PydataResponse> response_data = response.body();
                    TableRow headingsRow = new TableRow(ProcessActivity.this);
                    headingsRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    addTextViewToRow(headingsRow, "Pyorder", 50);
                    addTextViewToRow(headingsRow, "Pynumber", 300);
                    addTextViewToRow(headingsRow, "Pydesc", 300);
                    addTextViewToRow(headingsRow, "Pypurpose", 200);
                    addTextViewToRow(headingsRow, "Pytype", 50);
                    addTextViewToRow(headingsRow, "Status", 100);
                    headingsRow.setBackgroundColor(ContextCompat.getColor(ProcessActivity.this, R.color.biblue));
                    setTextViewColorToWhite(headingsRow);
                    tableLayout.addView(headingsRow);
                    addRowSeparator(tableLayout);

                    if (response_data != null) {
                        if(checklist.isEmpty()){
                            for (PydataResponse pydataResponse : response_data) {
                                TableRow row = new TableRow(ProcessActivity.this);
                                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                addTextViewToRow(row, String.valueOf(pydataResponse.getPyorder()), 50);
                                addTextViewToRow(row, pydataResponse.getPyno(), 300);
                                addTextViewToRow(row, pydataResponse.getPydesc(), 300);
                                addTextViewToRow(row, pydataResponse.getPypurpose(), 200);
                                addTextViewToRow(row, pydataResponse.getPytype(), 50);
                                addRadioButtonToRow(row, 250);

                                tableLayout.addView(row);
                                addRowSeparator(tableLayout);
                            }
                        }
                        else{
                            int i=0;
                            for (PydataResponse pydataResponse : response_data) {
                                TableRow row = new TableRow(ProcessActivity.this);
                                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                addTextViewToRow(row, String.valueOf(pydataResponse.getPyorder()), 50);
                                addTextViewToRow(row, pydataResponse.getPyno(), 300);
                                addTextViewToRow(row, pydataResponse.getPydesc(), 300);
                                addTextViewToRow(row, pydataResponse.getPypurpose(), 200);
                                addTextViewToRow(row, pydataResponse.getPytype(), 50);
                                addTextViewToRow(row, checklist.get(i),250);
                                i++;
                                tableLayout.addView(row);
                                addRowSeparator(tableLayout);
                            }
                            submit.setVisibility(View.GONE);
                        }
                    }
                    else {
                        System.out.println("Null Response fetchData");
                    }
                } else {
                    System.out.println("No Response fetchData");
                }
            }
            @Override
            public void onFailure(Call<List<PydataResponse>> call, Throwable t) {
                System.out.println("error is: "+t);
            }
        });
    }

    private void addTextViewToRow(TableRow row, String text, int width) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextColor(Color.BLACK);
        textView.setPadding(16, 8, 16, 8);
        textView.setGravity(Gravity.CENTER);
        textView.setMaxWidth(500);
        row.addView(textView);
    }

    private void addRadioButtonToRow(TableRow row, int width) {
        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setLayoutParams(new TableRow.LayoutParams(width, TableRow.LayoutParams.WRAP_CONTENT));
        radioGroup.setOrientation(RadioGroup.VERTICAL);

        RadioButton radioButtonYes = new RadioButton(this);
        radioButtonYes.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT, 1f));
        radioButtonYes.setText("Yes");
        radioButtonYes.setTextColor(Color.BLACK);


        RadioButton radioButtonNo = new RadioButton(this);
        radioButtonNo.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT, 1f));
        radioButtonNo.setText("No");
        radioButtonNo.setTextColor(Color.BLACK);

        RadioButton radioButtonNA = new RadioButton(this);
        radioButtonNA.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT, 1f));
        radioButtonNA.setText("NA");
        radioButtonNA.setTextColor(Color.BLACK);

        radioGroup.addView(radioButtonYes);
        radioGroup.addView(radioButtonNo);
        radioGroup.addView(radioButtonNA);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                selectedRadioButtons.removeIf(rb -> rb.getParent() == row);
                RadioButton selectedRadioButton = findViewById(checkedId);
                selectedRadioButtons.add(selectedRadioButton);
            }
        });
        row.addView(radioGroup);
    }
    private void addRowSeparator(TableLayout tableLayout) {
        View separator = new View(this);
        separator.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 4));
        separator.setBackgroundColor(Color.parseColor("#BBBBBB"));
        tableLayout.addView(separator);
    }
    private void setupSubmitButton() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SubmitRequest> submitRequests = new ArrayList<>();
                for (RadioButton radioButton : selectedRadioButtons) {

                    TableRow row = (TableRow) radioButton.getParent().getParent();
                    String pyorder = ((TextView) row.getChildAt(0)).getText().toString();
                    String pynumber = ((TextView) row.getChildAt(1)).getText().toString();
                    String pydesc = ((TextView) row.getChildAt(2)).getText().toString();
                    String pypurpose = ((TextView) row.getChildAt(3)).getText().toString();
                    String pytype = ((TextView) row.getChildAt(4)).getText().toString();

                    SubmitRequest submitRequest = new SubmitRequest();
                    submitRequest.setPlant(plant);
                    submitRequest.setUnit(unit);
                    submitRequest.setCelltype(celltype);
                    submitRequest.setCellno(cellno);
                    submitRequest.setModel(model);
                    submitRequest.setProcess(process);
                    submitRequest.setPyorder(Integer.parseInt(pyorder));
                    submitRequest.setPyno(pynumber);
                    submitRequest.setPydesc(pydesc);
                    submitRequest.setDocno(docno);
                    submitRequest.setDate(recdate);
                    submitRequest.setPypurpose(pypurpose);
                    submitRequest.setPytype(pytype);
                    submitRequest.setCheck(radioButton.getText().toString());
                    submitRequest.setUpdatename(username);
                    submitRequests.add(submitRequest);
                }
                submitData(submitRequests);
            }
        });
    }
    private void submitData(List<SubmitRequest> submitRequests) {
        Call<Void> call = ApiClient.getUserService().updateMultipleClientSide(submitRequests);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println("Response code is "+response.code());
                System.out.println(response.body());
                if (response.isSuccessful()) {
                    displaySuccessMessageAndMoveToLoginActivity();
                } else {
                    Toast.makeText(ProcessActivity.this, "Failed to submit data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ProcessActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("SubmitData", "Network error", t);
            }
        });
    }
    private void displaySuccessMessageAndMoveToLoginActivity() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProcessActivity.this);
        builder.setMessage("Data submitted successfully");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                finish();
            }
        }, 3000);
    }
    private void setupBackButtonNavigation() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(ProcessActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
    private void setTextViewColorToWhite(TableRow row) {
        for (int i = 0; i < row.getChildCount(); i++) {
            View child = row.getChildAt(i);
            if (child instanceof TextView) {
                ((TextView) child).setTextColor(Color.WHITE);
            }
        }
    }

}