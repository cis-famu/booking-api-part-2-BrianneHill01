package edu.famu.booking.controllers;
import edu.famu.booking.models.Rooms;
import edu.famu.booking.models.Rooms;
import edu.famu.booking.service.RoomService;
import edu.famu.booking.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/rooms")

public class RoomController {
    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllRooms() {
        try {
            return ResponseEntity.ok(new ApiResponse(true, "Success", roomService.getAllRooms(), null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(false, "An error occurred.", null, e.getMessage()));
        }
    }

    /*@GetMapping("/{roomId}")
    public ResponseEntity<ApiResponse> getRoomsbyId(@PathVariable String roomsId) {
        try {
            return ResponseEntity.ok(new ApiResponse(true, "Success", roomService.getRoomsById(roomsId), null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(false, "An error occurred.", null, e.getMessage()));
        }

    }*/
    @PostMapping
    public ResponseEntity<ApiResponse> createNewRooms(@RequestBody Rooms rooms){
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Success", roomService.createRooms(rooms),null));
        } catch (ExecutionException e){
            return ResponseEntity.status(401).body(new ApiResponse(false, "An error occurred", null, e.getMessage()));
        } catch (InterruptedException e){
            return ResponseEntity.status(500).body(new ApiResponse(false,"An error occurred", null, e.getMessage()));
        }
    }
    @PutMapping("/{rooms}")
    public ResponseEntity<ApiResponse> updateRooms(@PathVariable String rooms,@RequestBody Map<String, String> j ){
        try{
            roomService.updateRooms(rooms,j);
            return ResponseEntity.ok(new ApiResponse(true,"Update Success",null,null));
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(false, "An error occurred", null, e.getMessage()));
        }
    }
    @DeleteMapping("/{roomID}")
    public ResponseEntity<ApiResponse> deleteRoom(@PathVariable String roomID){
        try{
            roomService.deleteRoom(roomID);
            return ResponseEntity.ok(new ApiResponse(true,"Update Success",null,null));
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(false, "An error occurred", null, e.getMessage()));
        }

    }


    @GetMapping("/{roomId}")
    public ResponseEntity<ApiResponse> getRoomsById(@PathVariable String roomId){
        try {
            return ResponseEntity.ok(new ApiResponse(true,"Success", roomService.getRoomsById(roomId), null));
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(false, "An error occurred.", null, e.getMessage()));
        }
    }
}

