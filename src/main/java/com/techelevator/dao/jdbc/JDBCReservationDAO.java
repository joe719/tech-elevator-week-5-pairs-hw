package com.techelevator.dao.jdbc;

//import com.techelevator.city.City;
import com.techelevator.dao.ReservationDAO;
import com.techelevator.model.Reservation;
import com.techelevator.model.Site;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCReservationDAO implements ReservationDAO {

    private JdbcTemplate jdbcTemplate;

    public JDBCReservationDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
	@Override
	public void createReservation(Reservation newReservation) {
		String sqlInsertCity = "INSERT INTO reservation(reservation_id, site_id, name, from_date, to_date, create_date) " +
							   "VALUES(?, ?, ?, ?, ?)";
		newReservation.setReservationId(getNextReservationId());
		jdbcTemplate.update(sqlInsertCity, newReservation.getReservationId(),
										  newReservation.getSiteId(),
										  newReservation.getName(),
										  newReservation.getFromDate(),
										  newReservation.getToDate(),
										  newReservation.getCreateDate());
	}
	
	private int getNextReservationId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_reservation_id')");
		if(nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new city");
		}
	}

//    @Override
//    public int createReservation(int siteId, String name, LocalDate fromDate, LocalDate toDate) {
//        return -1;
//    }

//    private Reservation mapRowToReservation(SqlRowSet results) {
//        Reservation r = new Reservation();
//        r.setReservationId(results.getInt("reservation_id"));
//        r.setSiteId(results.getInt("site_id"));
//        r.setName(results.getString("name"));
//        r.setFromDate(results.getDate("from_date").toLocalDate());
//        r.setToDate(results.getDate("to_date").toLocalDate());
//        r.setCreateDate(results.getDate("create_date").toLocalDate());
//        return r;
//    }


}
