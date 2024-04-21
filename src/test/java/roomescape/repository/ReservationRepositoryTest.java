package roomescape.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.repository.reservation.ReservationRepository;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationRepositoryTest {

    private ReservationRepository reservationRepository = new ReservationRepositoryFake();

    @DisplayName("예약 정보 삽입 테스트")
    @Test
    void insertTest() {
        Reservation insert = reservationRepository.insert(new Reservation("name", "2000-09-07", "10:00"));
        assertThat(insert.getId()).isEqualTo(1L);
    }

    @DisplayName("예약 정보 전체 조회 테스트")
    @Test
    void findAllTest() {
        reservationRepository.insert(new Reservation("name1", "2000-09-07", "10:00"));
        reservationRepository.insert(new Reservation("name2", "2000-09-07", "10:00"));
        reservationRepository.insert(new Reservation("name3", "2000-09-07", "10:00"));

        int findSize = reservationRepository.findAll().size();
        assertThat(findSize).isEqualTo(3);
    }

    @DisplayName("예약 정보 삭제 테스트")
    @Test
    void deleteTest() {
        Reservation insert = reservationRepository.insert(new Reservation("name", "2000-09-07", "10:00"));
        int deleteCount = reservationRepository.deleteById(insert.getId());
        int findSize = reservationRepository.findAll().size();

        assertThat(deleteCount).isEqualTo(1);
        assertThat(findSize).isEqualTo(0);
    }
}