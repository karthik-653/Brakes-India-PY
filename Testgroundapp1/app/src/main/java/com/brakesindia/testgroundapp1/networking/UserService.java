package com.brakesindia.testgroundapp1.networking;

import com.brakesindia.testgroundapp1.networking.Approve.Response.AppPlantResponse;
import com.brakesindia.testgroundapp1.networking.Requests.AdminRequest;
import com.brakesindia.testgroundapp1.networking.Requests.ApprovalSubmitRequest;
import com.brakesindia.testgroundapp1.networking.Requests.LoginRequest;
import com.brakesindia.testgroundapp1.networking.Requests.NewDataRequest;
import com.brakesindia.testgroundapp1.networking.Requests.SubmitRequest;
import com.brakesindia.testgroundapp1.networking.Response.ApprovalResponse;
import com.brakesindia.testgroundapp1.networking.Response.CellNumberResponse;
import com.brakesindia.testgroundapp1.networking.Response.CellTypeResponse;
import com.brakesindia.testgroundapp1.networking.Requests.CheckRequest;
import com.brakesindia.testgroundapp1.networking.Response.CheckOneResponse;
import com.brakesindia.testgroundapp1.networking.Response.CheckResponse;
import com.brakesindia.testgroundapp1.networking.Response.DocResponse;
import com.brakesindia.testgroundapp1.networking.Response.EditResponse;
import com.brakesindia.testgroundapp1.networking.Response.LoginResponse;
import com.brakesindia.testgroundapp1.networking.Response.ModelResponse;
import com.brakesindia.testgroundapp1.networking.Response.NameResponse;
import com.brakesindia.testgroundapp1.networking.Response.PlantResponse;
import com.brakesindia.testgroundapp1.networking.Response.ProcessResponse;
import com.brakesindia.testgroundapp1.networking.Response.PydataResponse;
import com.brakesindia.testgroundapp1.networking.Response.UnitResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    @POST("/api/Userdata/")
    Call<LoginResponse> userSignup(@Body LoginRequest loginrequest);

    @POST("/api/Clientside/updatemultipleclientside")
    Call<Void> updateMultipleClientSide(@Body List<SubmitRequest> submitRequests);

    @PUT("/api/ClientSide/updatechoiceclientside/")
    Call<Void> adminUpdateMultipleClientSide(@Body List<AdminRequest> submitRequests);

    @POST("/api/Approverside/updatemultipleapprovalside")
    Call<Void> updateMultipleApprovals(@Body List<ApprovalSubmitRequest> submitRequests);


    @POST("/api/Serverside/newdata")
    Call<Void> updateNewData(@Body NewDataRequest newDataRequest);

    @GET("/api/Clientside/getplants/")
    Call<PlantResponse> getPlants();

    @GET("/api/Clientside/getunits/{plant}")
    Call<UnitResponse> getUnits(@Path(("plant")) String plant);

    @GET("/api/Clientside/getcelltypes/{plant}/{unit}")
    Call<CellTypeResponse> getCelltypes(@Path(("plant")) String plant,
                                        @Path(("unit")) int unit);

    @GET("/api/Clientside/getcellnumbers/{plant}/{unit}/{celltype}")
    Call<CellNumberResponse> getCellnumbers(@Path(("plant")) String plant,
                                            @Path(("unit")) int unit,
                                            @Path(("celltype")) String celltype);

    @GET("api/Clientside/getmodels/{plant}/{unit}/{celltype}/{cellno}")
    Call<ModelResponse> getModels(@Path(("plant")) String plant,
                                  @Path(("unit")) int unit,
                                  @Path(("celltype")) String celltype,
                                  @Path(("cellno")) int cellno);

    @GET("/api/Clientside/getprocess/{plant}/{unit}/{celltype}/{cellno}/{model}")
    Call<ProcessResponse> getProcess(@Path(("plant")) String plant,
                                     @Path(("unit")) int unit,
                                     @Path(("celltype")) String celltype,
                                     @Path(("cellno")) int cellno,
                                     @Path(("model")) String model);

    @GET("/api/Clientside/getdocno/{plant}/{unit}/{celltype}/{cellno}/{model}")
    Call<DocResponse> getDocno(@Path(("plant")) String plant,
                               @Path(("unit")) int unit,
                               @Path(("celltype")) String celltype,
                               @Path(("cellno")) int cellno,
                               @Path(("model")) String model);

    @GET("/api/Clientside/getname/{plant}/{unit}/{celltype}/{cellno}/{model}/{date}/{process}")
    Call<NameResponse> getName(@Path(("plant")) String plant,
                               @Path(("unit")) int unit,
                               @Path(("celltype")) String celltype,
                               @Path(("cellno")) int cellno,
                               @Path(("model")) String model,
                               @Path(("date")) String date,
                               @Path(("process")) String process
                               );

    @GET("/api/Clientside/getpydata/{plant}/{unit}/{celltype}/{cellno}/{model}/{process}")
    Call<List<PydataResponse>> getPydata(@Path(("plant")) String plant,
                                         @Path(("unit")) int unit,
                                         @Path(("celltype")) String celltype,
                                         @Path(("cellno")) int cellno,
                                         @Path(("model")) String model,
                                         @Path(("process")) String process);

    @GET("/api/Clientside/getcheck")
    Call<CheckOneResponse> getCheck(@Query(("plant")) String plant,
                                    @Query(("unit")) int unit,
                                    @Query(("celltype")) String celltype,
                                    @Query(("cellno")) int cellno,
                                    @Query(("model")) String model,
                                    @Query(("process")) String process,
                                    @Query(("date")) String date);



    @GET("/api/Approverside/getdataforapproval/{plant}/{unit}/{celltype}/{cellno}/{model}/{process}/{date}")
    Call<List<ApprovalResponse>> getApproval(@Path(("plant")) String plant,
                                             @Path(("unit")) int unit,
                                             @Path(("celltype")) String celltype,
                                             @Path(("cellno")) int cellno,
                                             @Path(("model")) String model,
                                             @Path(("process")) String process,
                                             @Path(("date")) String date);



    @GET("/api/Clientside/getalldatafromquery_another")
    Call<AppPlantResponse> getdataApprovalPlant(@Query(("date")) String date);

    @GET("/api/Clientside/getalldatafromquery_another")
    Call<UnitResponse> getdataApprovalUnit(@Query(("date")) String date,
                                           @Query(("plant")) String plant);
    @GET("/api/Clientside/getalldatafromquery_another")
    Call<CellTypeResponse> getdataApprovalCellType(@Query(("date")) String date,
                                                   @Query(("plant")) String plant,
                                                    @Query(("unit")) int unit);

    @GET("/api/Clientside/getalldatafromquery_another")
    Call<CellNumberResponse> getdataApprovalCellNo(@Query(("date")) String date,
                                                   @Query(("plant")) String plant,
                                                   @Query(("unit")) int unit,
                                                    @Query(("celltype")) String celltype);

    @GET("/api/Clientside/getalldatafromquery_another")
    Call<ModelResponse> getdataApprovalModel(@Query(("date")) String date,
                                                   @Query(("plant")) String plant,
                                                   @Query(("unit")) int unit,
                                                   @Query(("celltype")) String celltype,
                                                  @Query(("cellno")) int cellno);

    @GET("/api/Clientside/getalldatafromquery_another")
    Call<ProcessResponse> getdataApprovalProcess(@Query(("date")) String date,
                                             @Query(("plant")) String plant,
                                             @Query(("unit")) int unit,
                                             @Query(("celltype")) String celltype,
                                             @Query(("cellno")) int cellno,
                                               @Query(("model")) String model);

    @GET("/api/Clientside/getalldatafromquery_approvercheck")
    Call<CheckResponse> getdataApprovalCheck(@Query(("date")) String date,
                                               @Query(("plant")) String plant,
                                               @Query(("unit")) int unit,
                                               @Query(("celltype")) String celltype,
                                               @Query(("cellno")) int cellno,
                                               @Query(("model")) String model,
                                               @Query(("process")) String process);

    @GET("/api/Clientside/getsorteddatawithoutapproval/")
    Call<List<EditResponse>> getEditdata();

    @GET("/api/Userdata/{id}")
    Call<LoginResponse> userLogin(@Path("id") int id);
}
