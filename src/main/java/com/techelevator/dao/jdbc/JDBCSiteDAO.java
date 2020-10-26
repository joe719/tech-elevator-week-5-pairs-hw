package com.techelevator.dao.jdbc;

//import com.techelevator.city.City;
import com.techelevator.dao.SiteDAO;
import com.techelevator.model.Site;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCSiteDAO implements SiteDAO {

    private JdbcTemplate jdbcTemplate;

    public JDBCSiteDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Site> getSitesThatAllowRVs(int parkId) {
		ArrayList<Site> sites = new ArrayList<>();
		String sqlFindRvSites = "SELECT site.site_id, site.campground_id, site.site_number, site.max_occupancy, site.accessible, site.max_rv_length, site.utilities "+
								"FROM site "+
								"JOIN campground ON site.campground_id = campground.campground_id "+ 
								"JOIN park ON campground.park_id = park.park_id " +
								"WHERE site.max_rv_length!=0 AND park.park_id = ?";
								
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindRvSites, parkId);
		while(results.next()) {
			Site theSite = mapRowToSite(results);
			sites.add(theSite);
		}
		return sites;
	}
    

    private Site mapRowToSite(SqlRowSet results) {
        Site site = new Site();
        site.setSiteId(results.getInt("site_id"));
        site.setCampgroundId(results.getInt("campground_id"));
        site.setSiteNumber(results.getInt("site_number"));
        site.setMaxOccupancy(results.getInt("max_occupancy"));
        site.setAccessible(results.getBoolean("accessible"));
        site.setMaxRvLength(results.getInt("max_rv_length"));
        site.setUtilities(results.getBoolean("utilities"));
        return site;
    }
}
