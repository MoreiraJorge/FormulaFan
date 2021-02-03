package pt.ipp.estg.formulafan.Repositories;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import pt.ipp.estg.formulafan.Databases.DriverPositionDAO;
import pt.ipp.estg.formulafan.Databases.DriverPositionDatabase;
import pt.ipp.estg.formulafan.Models.DriverPosition;
import pt.ipp.estg.formulafan.Services.DriverPositionDetailsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverPositionRepository {

    private final DriverPositionDAO driverPositionDAO;
    private LiveData<List<DriverPosition>> allDriversPositions;
    private final Context toastContext;

    public DriverPositionRepository(Application application) {
        toastContext = application.getApplicationContext();
        DriverPositionDatabase db = DriverPositionDatabase.getDatabase(application);
        driverPositionDAO = db.getDriverPositionDAO();
        allDriversPositions = driverPositionDAO.getDriversPositions();
        refreshDriversPositions();
    }

    public LiveData<List<DriverPosition>> getAllDriversPositions() {
        return allDriversPositions;
    }

    public void insertDriverPosition(DriverPosition driverPosition) {
        DriverPositionDatabase.databaseWriteExecutor.execute(() -> {
            driverPositionDAO.insertDriversPosition(driverPosition);
        });
    }

    public void deleteDriverPosition(DriverPosition driverPosition) {
        DriverPositionDatabase.databaseWriteExecutor.execute(() -> {
            driverPositionDAO.deleteDriversPosition(driverPosition);
        });
    }

    public void refreshDriversPositions() {
        DriverPositionDetailsAPI.driverPositionDetailsAPI.getListOfDriversPositions().enqueue(new Callback<List<DriverPosition>>() {
            @Override
            public void onResponse(@NotNull Call<List<DriverPosition>> call, @NotNull Response<List<DriverPosition>> response) {
                List<DriverPosition> driverPositions = response.body();
                DriverPositionDatabase.databaseWriteExecutor.execute(() -> {
                    driverPositionDAO.insertAllDriversPositions(driverPositions);
                });
                DriverPositionDatabase.databaseWriteExecutor.execute(() -> {
                    allDriversPositions = driverPositionDAO.getDriversPositions();
                });
            }

            @Override
            public void onFailure(@NotNull Call<List<DriverPosition>> call, @NotNull Throwable t) {
                Toast.makeText(toastContext, "Erro ao processar Pedido!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
