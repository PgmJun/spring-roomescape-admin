package roomescape.reservation.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.dao.ReservationTimeDao;
import roomescape.reservation.domain.ReservationTime;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @BeforeEach
    void init() {
        reservationTimeDao.insert(new ReservationTime("10:00"));
    }

    @DisplayName("예약 정보 조회 테스트")
    @Test
    void reservationReadTest() {
        RestAssured.given().log().all()
                .port(randomServerPort)
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("reservations.size()", is(0));
    }

    @DisplayName("예약 삭제 테스트")
    @Test
    void reservationsDeleteTest() {
        Map<String, String> params = Map.of(
                "name", "새양",
                "date", "1998-02-24",
                "timeId", "1"
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .port(randomServerPort)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .body("id", is(1));

        RestAssured.given().log().all()
                .port(randomServerPort)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);
    }

    @DisplayName("예약 등록 테스트")
    @Test
    void reservationAddTest() {
        Map<String, String> params = Map.of(
                "name", "새양",
                "date", "1998-02-24",
                "timeId", "1"
        );

        RestAssured.given().log().all()
                .port(randomServerPort)
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .body("id", is(1));
    }
}
