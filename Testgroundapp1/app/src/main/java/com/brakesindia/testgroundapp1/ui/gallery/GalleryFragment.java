package com.brakesindia.testgroundapp1.ui.gallery;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brakesindia.testgroundapp1.MainActivity;
import com.brakesindia.testgroundapp1.ProcessActivity;
import com.brakesindia.testgroundapp1.R;

import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.brakesindia.testgroundapp1.databinding.FragmentGalleryBinding;
import com.brakesindia.testgroundapp1.networking.ApiClient;
import com.brakesindia.testgroundapp1.networking.Requests.CellNumberRequest;
import com.brakesindia.testgroundapp1.networking.Requests.CellTypeRequest;
import com.brakesindia.testgroundapp1.networking.Requests.ModelRequest;
import com.brakesindia.testgroundapp1.networking.Requests.ProcessRequest;
import com.brakesindia.testgroundapp1.networking.Requests.UnitRequest;
import com.brakesindia.testgroundapp1.networking.Response.CellNumberResponse;
import com.brakesindia.testgroundapp1.networking.Response.CellTypeResponse;
import com.brakesindia.testgroundapp1.networking.Response.ModelResponse;
import com.brakesindia.testgroundapp1.networking.Response.PlantResponse;
import com.brakesindia.testgroundapp1.networking.Response.ProcessResponse;
import com.brakesindia.testgroundapp1.networking.Response.UnitResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryFragment extends Fragment {

    private Spinner spinner_a,spinner_b, spinner_c, spinner_d, spinner_e, spinner_f;
    private Button submit, date_button;
    private Calendar selectedDate;
    TextView date_text;
    String date,username;
    ArrayList<String> plantsList, celltypesList, modelsList, processList;
    ArrayList<Integer> unitsList, cellNumbersList;
    ArrayAdapter<String> adapter_spinner_a, adapter_spinner_c,adapter_spinner_e, adapter_spinner_f;
    ArrayAdapter<Integer> adapter_spinner_b, adapter_spinner_d;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        setInit(root);
        setAction();
        setAdapter();
        getPlantData();
        return root;

    }

    private void setInit(View root) {

//        Intent prev_intent = getIntent();
//        username = prev_intent.getStringExtra("username");
//        System.out.println("Username from prev page: "+username);

        plantsList = new ArrayList<>();
        unitsList = new ArrayList<>();
        celltypesList = new ArrayList<>();
        cellNumbersList = new ArrayList<>();
        modelsList = new ArrayList<>();
        processList = new ArrayList<>();

        spinner_a = root.findViewById(R.id.spinner_a);
        spinner_b = root.findViewById(R.id.spinner_b);
        spinner_c = root.findViewById(R.id.spinner_c);
        spinner_d = root.findViewById(R.id.spinner_d);
        spinner_e = root.findViewById(R.id.spinner_e);
        spinner_f = root.findViewById(R.id.spinner_f);
        date_text = root.findViewById(R.id.date_text);

        date_button = root.findViewById(R.id.date_button);
        submit = root.findViewById(R.id.submit_area);
        selectedDate = Calendar.getInstance();
        submit.setEnabled(false);
        spinner_a.setSelection(-1);
   }

    private void setAction(){

        spinner_a.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Spinner A item selected");
                clearL5();
                getUnitsData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_b.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clearL4();
                getCellType();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_c.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clearL3();
                getCellNumber();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_d.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clearL2();
                getModels();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_e.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clearL1();
                getProcess();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDate();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), ProcessActivity.class);
                intent.putExtra("spinner_a_val", spinner_a.getSelectedItem().toString());
                intent.putExtra("spinner_b_val", spinner_b.getSelectedItem().toString());
                intent.putExtra("spinner_c_val", spinner_c.getSelectedItem().toString());
                intent.putExtra("spinner_d_val", spinner_d.getSelectedItem().toString());
                intent.putExtra("spinner_e_val", spinner_e.getSelectedItem().toString());
                intent.putExtra("spinner_f_val", spinner_f.getSelectedItem().toString());
                intent.putExtra("username", username);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }

    private void getPlantData(){
        Call<PlantResponse> call = ApiClient.getUserService().getPlants();

        call.enqueue(new Callback<PlantResponse>() {
            @Override
            public void onResponse(Call<PlantResponse> call, Response<PlantResponse> response) {
                if (response.isSuccessful()) {
                    PlantResponse plantResponse = response.body();
                    if (plantResponse != null) {
                        List<String> plants_imp = plantResponse.getPlants();
                        plantsList.clear();
                        plantsList.addAll(plants_imp);
                        System.out.println("Plants : "+plantsList);
                        adapter_spinner_a.notifyDataSetChanged();
                        clearL5();

                    }
                    else{
                        System.out.println("Null Response getPlant");
                    }
                } else{
                    System.out.println("No Response getPlant");
                }
            }
            @Override
            public void onFailure(Call<PlantResponse> call, Throwable t) {
                System.out.println("Failure getPlant API");
            }
        });
    }
    private void getUnitsData() {

        UnitRequest unitRequest = new UnitRequest();

        Call<UnitResponse> call = ApiClient.getUserService().getUnits(spinner_a.getSelectedItem().toString());

        call.enqueue(new Callback<UnitResponse>() {
            @Override
            public void onResponse(Call<UnitResponse> call, Response<UnitResponse> response) {
                if (response.isSuccessful()) {
                    UnitResponse unitsResponse = response.body();
                    if (unitsResponse != null) {
                        ArrayList<Integer> unitsData = (ArrayList<Integer>) response.body().getUnits();
                        unitsList.clear();
                        unitsList.addAll(unitsData);
                        adapter_spinner_b.notifyDataSetChanged();
                        clearL4();
                        System.out.println("Units : "+unitsList);
                    }else{
                        System.out.println("Null response getUnit" +response.code());
                    }
                }
                else{
                    System.out.println("No response getUnit" +response.code());
                }
            }

            @Override
            public void onFailure(Call<UnitResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getCellType(){
        CellTypeRequest cellTypeRequest = new CellTypeRequest();
        cellTypeRequest.setPlant(spinner_a.getSelectedItem().toString());
        cellTypeRequest.setUnit(Integer.parseInt(spinner_b.getSelectedItem().toString()));
        Call<CellTypeResponse> call = ApiClient.getUserService().getCelltypes(cellTypeRequest.getPlant(), cellTypeRequest.getUnit());
        call.enqueue(new Callback<CellTypeResponse>() {
            @Override
            public void onResponse(Call<CellTypeResponse> call, Response<CellTypeResponse> response) {
                if (response.isSuccessful()) {
                    CellTypeResponse cellTypeResponse = response.body();
                    List<String> celltypesData = cellTypeResponse.getCellTypes();
                    if (celltypesData != null) {
                        celltypesList.clear();
                        celltypesList.addAll(celltypesData);
                        System.out.println("Cell Type List : "+celltypesList);
                        clearL3();
                        adapter_spinner_c.notifyDataSetChanged();
                        adapter_spinner_d.notifyDataSetChanged();
                    } else {
                        System.out.println("Null Response getCellType");
                    }

                } else {
                    System.out.println("No Response getCellType");
                }
            }
            @Override
            public void onFailure(Call<CellTypeResponse> call, Throwable t) {

                System.out.println("API Failure");
            }
        });

    }
    private void getCellNumber(){
        CellNumberRequest cellNumberRequest = new CellNumberRequest();
        cellNumberRequest.setPlant(spinner_a.getSelectedItem().toString());
        cellNumberRequest.setUnit(Integer.parseInt(spinner_b.getSelectedItem().toString()));
        cellNumberRequest.setCellType(spinner_c.getSelectedItem().toString());

        Call<CellNumberResponse> call = ApiClient.getUserService().getCellnumbers(cellNumberRequest.getPlant(),
                cellNumberRequest.getUnit(), cellNumberRequest.getCellType());
        call.enqueue(new Callback<CellNumberResponse>() {
            @Override
            public void onResponse(Call<CellNumberResponse> call, Response<CellNumberResponse> response) {
                System.out.println(response.body()+" front");
                if (response.isSuccessful()) {
                    System.out.println("Response is valid");
                    CellNumberResponse cellNumberResponse = response.body();

                    List<Integer> cellNumbersData = cellNumberResponse.getCellNumbers();
                    if (cellNumbersData != null) {
                        cellNumbersList.clear();
                        cellNumbersList.addAll(cellNumbersData);
                        System.out.println("Cell Numbers List : "+ cellNumbersList);
                        clearL2();
                        adapter_spinner_d.notifyDataSetChanged();
                    } else {
                        System.out.println("Null Response getCellNumber");
                    }

                } else {
                    System.out.println("No Response getCellNumber");
                }
            }

            @Override
            public void onFailure(Call<CellNumberResponse> call, Throwable t) {
                System.out.println("OnFailure of cell number");
                Toast.makeText(requireContext(), "Throwable "+t.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getModels(){
        ModelRequest modelRequest = new ModelRequest();
        modelRequest.setPlant(spinner_a.getSelectedItem().toString());
        modelRequest.setUnit(Integer.parseInt(spinner_b.getSelectedItem().toString()));
        modelRequest.setCellType(spinner_c.getSelectedItem().toString());
        modelRequest.setCellno(Integer.parseInt(spinner_d.getSelectedItem().toString()));
        System.out.println("Get model spinner called");
        Call<ModelResponse> call = ApiClient.getUserService().getModels(modelRequest.getPlant(),
                modelRequest.getUnit(), modelRequest.getCellType(), modelRequest.getCellno());
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                System.out.println(response.body()+" front");
                if (response.isSuccessful()) {
                    ModelResponse modelResponse = response.body();
                    List<String> modelsData = modelResponse.getModels();
                    if (modelsData != null) {
                        modelsList.clear();
                        modelsList.addAll(modelsData);
                        System.out.println("Models List : "+ modelsList);
                        clearL1();
                        adapter_spinner_e.notifyDataSetChanged();

                    } else {
                        System.out.println("Null Response getModels");
                    }

                } else {
                    System.out.println("No Response getModels");
                }
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                System.out.println("OnFailure Models Page");
                Toast.makeText(requireContext(), "Throwable "+t.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getProcess(){
        ProcessRequest processRequest = new ProcessRequest();
        processRequest.setPlant(spinner_a.getSelectedItem().toString());
        processRequest.setUnit(Integer.parseInt(spinner_b.getSelectedItem().toString()));
        processRequest.setCellType(spinner_c.getSelectedItem().toString());
        processRequest.setCellno(Integer.parseInt(spinner_d.getSelectedItem().toString()));
        processRequest.setModel(spinner_e.getSelectedItem().toString());

        Call<ProcessResponse> call = ApiClient.getUserService().getProcess(processRequest.getPlant(),
                processRequest.getUnit(), processRequest.getCellType(),
                processRequest.getCellno(), processRequest.getModel());

        call.enqueue(new Callback<ProcessResponse>() {
            @Override
            public void onResponse(Call<ProcessResponse> call, Response<ProcessResponse> response) {
                System.out.println(response.body()+" front");
                if (response.isSuccessful()) {
                    ProcessResponse processResponse = response.body();
                    List<String> processData = processResponse.getProcess();
                    if (processData != null) {
                        processList.clear();
                        processList.addAll(processData);
                        System.out.println("Process List : "+ processList);
                        submit.setEnabled(true);
                        adapter_spinner_f.notifyDataSetChanged();

                    } else {
                        System.out.println("Null Response getProcess");
                    }

                } else {
                    System.out.println("No Response getProcess");
                }
            }

            @Override
            public void onFailure(Call<ProcessResponse> call, Throwable t) {
                System.out.println("Print from onFailure of Process");
                Toast.makeText(requireContext(), "Throwable "+t.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private void clearL5(){
        System.out.println("CLEAR L5 called");
        unitsList.clear();
        adapter_spinner_b.notifyDataSetChanged();
        celltypesList.clear();
        adapter_spinner_c.notifyDataSetChanged();
        cellNumbersList.clear();
        adapter_spinner_d.notifyDataSetChanged();
        modelsList.clear();
        adapter_spinner_e.notifyDataSetChanged();
        processList.clear();
        adapter_spinner_f.notifyDataSetChanged();
    }

    private void clearL4(){
        System.out.println("CLEAR L4 called");
        celltypesList.clear();
        adapter_spinner_c.notifyDataSetChanged();
        cellNumbersList.clear();
        adapter_spinner_d.notifyDataSetChanged();
        modelsList.clear();
        adapter_spinner_e.notifyDataSetChanged();
        processList.clear();
        adapter_spinner_f.notifyDataSetChanged();
    }

    private void clearL3(){
        System.out.println("CLEAR L3 called");
        cellNumbersList.clear();
        adapter_spinner_d.notifyDataSetChanged();
        modelsList.clear();
        adapter_spinner_e.notifyDataSetChanged();
        processList.clear();
        adapter_spinner_f.notifyDataSetChanged();
    }

    private void clearL2(){
        System.out.println("CLEAR L2 called");
        modelsList.clear();
        adapter_spinner_e.notifyDataSetChanged();
        processList.clear();
        adapter_spinner_f.notifyDataSetChanged();
    }

    private void clearL1(){
        System.out.println("CLEAR L1 called");
        processList.clear();
        adapter_spinner_f.notifyDataSetChanged();
    }

    private void openDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), R.style.datetheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, month);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // Set the date variable here
                date = year + "-" + (month + 1) + "-" + dayOfMonth;
                // Print the selected date

            }
        }, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void setAdapter(){

//        adapter_spinner_a = new ArrayAdapter<>(GalleryFragment.this,
//                android.R.layout.simple_spinner_item, plantsList);

        adapter_spinner_a = new ArrayAdapter<>(requireContext(),
                R.layout.spinner_item_black_text, plantsList);
        adapter_spinner_a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_a.setAdapter(adapter_spinner_a);



        adapter_spinner_b = new ArrayAdapter<>(requireContext(),
                R.layout.spinner_item_black_text, unitsList);

        adapter_spinner_b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_b.setAdapter(adapter_spinner_b);

        adapter_spinner_c = new ArrayAdapter<>(requireContext(),
                R.layout.spinner_item_black_text, celltypesList);

        adapter_spinner_c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_c.setAdapter(adapter_spinner_c);

        adapter_spinner_d = new ArrayAdapter<>(requireContext(),
                R.layout.spinner_item_black_text, cellNumbersList);

        adapter_spinner_d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_d.setAdapter(adapter_spinner_d);

        adapter_spinner_e = new ArrayAdapter<>(requireContext(),
                R.layout.spinner_item_black_text, modelsList);

        adapter_spinner_e.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_e.setAdapter(adapter_spinner_e);

        adapter_spinner_f = new ArrayAdapter<>(requireContext(),
                R.layout.spinner_item_black_text, processList);

        adapter_spinner_f.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_f.setAdapter(adapter_spinner_f);

    }



}