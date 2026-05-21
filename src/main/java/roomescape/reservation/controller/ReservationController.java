package roomescape.reservation.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.auth.LoginUser;
import roomescape.reservation.dto.CreateReservationRequest;
import roomescape.reservation.dto.ReservationsResponse;
import roomescape.reservation.dto.UpdateReservationRequest;
import roomescape.reservation.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @LoginUser String userName,
            @RequestBody @Valid CreateReservationRequest request) {
        Long id = reservationService.create(userName, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<ReservationsResponse> findMyReservations(
            @LoginUser String userName) {
        ReservationsResponse responses = reservationService.findReservationsByUserName(userName);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMyReservation(
            @PathVariable @NotNull(message = "예약 ID는 필수입니다.") @Positive(message = "예약 ID는 양수여야 합니다.") Long id,
            @LoginUser String userName) {
        reservationService.delete(id, userName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMyReservation(
            @PathVariable @NotNull(message = "예약 ID는 필수입니다.") @Positive(message = "예약 ID는 양수여야 합니다.") Long id,
            @LoginUser String userName,
            @RequestBody @Valid UpdateReservationRequest request) {
        reservationService.update(id, request.scheduleId(), userName);
        return ResponseEntity.noContent().build();
    }
}
