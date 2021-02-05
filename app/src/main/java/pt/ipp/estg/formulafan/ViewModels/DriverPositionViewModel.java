package pt.ipp.estg.formulafan.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pt.ipp.estg.formulafan.Models.DriverPosition;
import pt.ipp.estg.formulafan.Repositories.DriverPositionRepository;

public class DriverPositionViewModel extends AndroidViewModel {

    private final LiveData<List<DriverPosition>> driverPositions;
    private final DriverPositionRepository driverPositionRepository;

    public DriverPositionViewModel(@NonNull Application application) {
        super(application);
        driverPositionRepository = new DriverPositionRepository(application);
        driverPositions = driverPositionRepository.getAllDriversPositions();
    }

    public LiveData<List<DriverPosition>> getAllDriversPositions() {
        return driverPositions;
    }

    public void insertDriverPosition(DriverPosition driverPosition) {
        driverPositionRepository.insertDriverPosition(driverPosition);
    }

    public void deleteDriverPosition(DriverPosition driverPosition) {
        driverPositionRepository.deleteDriverPosition(driverPosition);
    }

}
