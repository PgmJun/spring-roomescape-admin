package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.dto.ReservationsResponseDto;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public ReservationsResponseDto findAllReservation() {
        List<ReservationResponseDto> response = reservationDao.findAll().stream()
                .map(r -> new ReservationResponseDto(r.getId(), r.getName(), r.getDate(), r.getTime()))
                .toList();

        return new ReservationsResponseDto(response);
    }

    public ReservationResponseDto addReservation(final ReservationRequestDto request) {
        Reservation reservation = reservationDao.insert(new Reservation(request.name(), request.date(), request.time()));

        return new ReservationResponseDto(
                reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public void deleteReservationById(final Long id) {
        int updateCount = reservationDao.deleteById(id);
        if (updateCount == 0) {
            throw new IllegalArgumentException(String.format("ID %d - Reservation 정보가 존재하지 않습니다.", id));
        }
    }
}
