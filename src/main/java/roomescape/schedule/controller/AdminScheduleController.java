package roomescape.schedule.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.auth.LoginUser;
import roomescape.exception.BadRequestException;
import roomescape.exception.ErrorCode;
import roomescape.reservation.service.ReservationService;
import roomescape.schedule.dto.AdminScheduleRequest;
import roomescape.schedule.dto.SchedulesResponse;
import roomescape.schedule.service.ScheduleService;
import roomescape.user.model.User;
import roomescape.user.service.UserService;

@RestController
@RequestMapping("/admin/schedules")
public class AdminScheduleController {

    private final ScheduleService scheduleService;
    private final ReservationService reservationService;
    private final UserService userService;

    public AdminScheduleController(ScheduleService scheduleService, ReservationService reservationService, UserService userService) {
        this.scheduleService = scheduleService;
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid AdminScheduleRequest request, @LoginUser String userName) {
        User user = userService.findByUserName(userName);
        scheduleService.create(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<SchedulesResponse> findAll(@LoginUser String userName) {
        User user = userService.findByUserName(userName);
        SchedulesResponse responses = scheduleService.findAllByUser(user);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable @NotNull(message = "스케줄 ID는 필수입니다.") @Positive(message = "스케줄 ID는 양수여야 합니다.") Long id,
            @LoginUser String userName) {
        if (reservationService.existsByScheduleId(id)) {
            throw new BadRequestException(ErrorCode.SCHEDULE_IN_USE);
        }
        User user = userService.findByUserName(userName);
        scheduleService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
