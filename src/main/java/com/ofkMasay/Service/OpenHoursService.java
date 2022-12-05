package com.ofkMasay.Service;

import com.ofkMasay.Entity.OpenHours;
import com.ofkMasay.Exception.CustomException;
import com.ofkMasay.Repository.OpenHoursRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenHoursService {

    private final OpenHoursRepository openHoursRepository;

    public OpenHoursService(OpenHoursRepository openHoursRepository) {
        this.openHoursRepository = openHoursRepository;
    }

    public boolean createOpenHours(OpenHours openHours) {
        try{
            return openHoursRepository.createOpenHours(openHours);
        }catch (Exception e){
            throw new CustomException("Open hours could not be saved due to: "+e.getMessage());
        }
    }

    public List<OpenHours> getAllOpenHours() {
        try{
            return openHoursRepository.getOpenHours();
        }catch (Exception e){
            throw new CustomException("Open hours could not be found due to: "+e.getMessage());
        }
    }

    public OpenHours getOpenHoursById(Long id) {
        try{
            return openHoursRepository.getOpenHoursById(id);
        }catch (Exception e){
            throw new CustomException("Open hours could not be found due to: "+e.getMessage());
        }
    }

    public boolean updateOpenHours(Long id, OpenHours openHours) {
        try{
            return openHoursRepository.updateOpenHours(id, openHours);
        }catch (Exception e){
            throw new CustomException("Open hours could not be updated due to: "+e.getMessage());
        }
    }

    public boolean deleteOpenHours(Long id) {
        try{
            return openHoursRepository.deleteOpenHours(id);
        }catch (Exception e){
            throw new CustomException("Open hours could not be deleted due to: "+e.getMessage());
        }
    }
}
