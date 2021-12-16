package com.syftgolf.syftgolf.event.newteesheet;

import com.syftgolf.syftgolf.error.ApiError;
import com.syftgolf.syftgolf.event.EventRepository;
import com.syftgolf.syftgolf.event.teesheet.TeeSheet;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/1.0")
@CrossOrigin
public class NewTeeSheetController {

    @Autowired
    NewTeeSheetService newTeeSheetService;

    @Autowired
    NewTeeSheetRepository newTeeSheetRepository;

    @Autowired
    EventRepository eventRepository;

    @GetMapping("/event/getNewTeeSheet/{eventid:[0-9]+}")
    @CrossOrigin
    public List<NewTeeSheet> getNewTeeSheet(@PathVariable long eventid){
        return newTeeSheetService.findAllForEvent(eventid);
    }

    @GetMapping("/event/getASingleTeeSheet/{teesheetid:[0-9]+}")
    @CrossOrigin
    public Optional<NewTeeSheet> getTeeSheet(@PathVariable long teesheetid){
        return newTeeSheetRepository.findById(teesheetid);
    }

    @PostMapping("/event/teesheet/create/{id:[0-9]+}")
    @CrossOrigin
    public GenericResponse newTeeSheet(@Valid @RequestBody NewTeeSheet newTeeSheet, @PathVariable long id) {
        System.out.println(newTeeSheet);
        newTeeSheetService.saveTeeSheet(newTeeSheet, id);
        return new GenericResponse("New teesheet added");
    }

    //Update tee sheet
    @PutMapping("/management/events/teesheet/update/{id:[0-9]+}")
    @CrossOrigin
    GenericResponse updateTeeSheet(@PathVariable long id, @RequestBody NewTeeSheet teeSheetUpdate) {
        newTeeSheetService.updateTeeSheet(id, teeSheetUpdate);

        return new GenericResponse("teesheet updated");
    }

    //Delete teesheet
    @DeleteMapping("/management/events/teesheet/delete/{teesheetid:[0-9]+}")
    @CrossOrigin
    GenericResponse deleteTeeSheet(@PathVariable long teesheetid) {
        newTeeSheetRepository.deleteById(teesheetid);
        return new GenericResponse("Tee sheet deleted");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        ApiError apiError = new ApiError(400, "Validation error", request.getServletPath());

        BindingResult result = exception.getBindingResult();

        Map<String, String> validationErrors = new HashMap<>();

        for(FieldError fieldError: result.getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        apiError.setValidationErrors(validationErrors);

        return apiError;
    }


}
