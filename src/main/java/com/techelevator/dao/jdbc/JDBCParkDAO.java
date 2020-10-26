package com.techelevator.dao.jdbc;

import com.techelevator.dao.ParkDAO;
import com.techelevator.model.Park;
//import com.techelevator.projects.model.Department;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCParkDAO implements ParkDAO {

    private JdbcTemplate jdbcTemplate;

    public JDBCParkDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Park> getAllParks() {
		List<Park> results = new ArrayList<>();
		String sql = "SELECT park_id, name, location, establish_date, area, visitors, description FROM park order by location asc";
		SqlRowSet parks = jdbcTemplate.queryForRowSet(sql);
		
		while(parks.next()) {
			Integer parkId = parks.getInt("park_id");
			String name = parks.getString("name");
			String location = parks.getString("location");
			LocalDate establishDate = parks.getDate("establish_date").toLocalDate();
			Integer area = parks.getInt("area");
			Integer visitors = parks.getInt("visitors");
			String description = parks.getString("description");
			
			Park thisPark = new Park();
			thisPark.setParkId(parkId);
			thisPark.setName(name);
			thisPark.setLocation(location);
			thisPark.setEstablishDate(establishDate);
			thisPark.setArea(area);
			thisPark.setVisitors(visitors);
			thisPark.setDescription(description);
			
			results.add(thisPark);
		}
		return results;	
		}
    

    //private Park mapRowToPark(SqlRowSet results) {
//        Park park = new Park();
//        park.setParkId(results.getInt("park_id"));
//        park.setName(results.getString("name"));
//        park.setLocation(results.getString("location"));
//        park.setEstablishDate(results.getDate("establish_date").toLocalDate());
//        park.setArea(results.getInt("area"));
//        park.setVisitors(results.getInt("visitors"));
//        park.setDescription(results.getString("description"));
//        return park;
//    }

}
