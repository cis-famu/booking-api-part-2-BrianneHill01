package edu.famu.booking.controllers;
import edu.famu.booking.models.Hotel;
import edu.famu.booking.service.HotelService;
import edu.famu.booking.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/hotel")

public class HotelController {
    private HotelService hotelService;

    public HotelController(HotelService hotelService){
        this.hotelService = hotelService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllHotels(){
        try{
            return ResponseEntity.ok(new ApiResponse(true, "Success", hotelService.getAllHotels(), null));
        }
        catch(Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(false,"An error occurred.", null, e.getMessage()));
        }
    }
    @GetMapping("/{hotelId}")
    public ResponseEntity<ApiResponse> getHotelsbyId(@PathVariable String hotelId){
        try{
            return ResponseEntity.ok(new ApiResponse(true, "Success", hotelService.getHotelsById(hotelId), null));
        }
        catch(Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(false,"An error occurred.", null, e.getMessage()));
        }

    }
    @PostMapping
    public ResponseEntity<ApiResponse> createNewHotels(@RequestBody Hotel hotel){
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Success", hotelService.createHotels(hotel),null));
        } catch (ExecutionException e){
            return ResponseEntity.status(401).body(new ApiResponse(false, "An error occured", null, e.getMessage()));
        } catch (InterruptedException e){
            return ResponseEntity.status(500).body(new ApiResponse(false,"An error occurred", null, e.getMessage()));
        }
    }
    @PutMapping("/{hotel}")
    public ResponseEntity<ApiResponse> updateHotels(@PathVariable String hotel,@RequestBody Map<String, String> j ){
        try{
            hotelService.updateHotels(hotel,j);
            return ResponseEntity.ok(new ApiResponse(true,"Update Success",null,null));
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(false, "An error occurred", null, e.getMessage()));
        }
    }
    @DeleteMapping("/{hotelID}")
    public ResponseEntity<ApiResponse> deleteHotel(@PathVariable String hotelID){
        try{
            hotelService.deleteHotel(hotelID);
            return ResponseEntity.ok(new ApiResponse(true,"Update Success",null,null));
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(false, "An error occurred", null, e.getMessage()));
        }

    }


}