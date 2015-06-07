package pl.jw.currency.exchange.dao.kantor2008;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pl.jw.currency.exchange.dao.api.CurrencyData;
import pl.jw.currency.exchange.dao.api.ICurrencyDAO;

@Repository
public class Kantor2008DAO implements ICurrencyDAO {

	private static final class CurrencyDataMapper implements RowMapper<CurrencyData> {

		@Override
		public CurrencyData mapRow(ResultSet rs, int rowNum) throws SQLException {
			CurrencyData data = new CurrencyData();
			data.setSymbol(rs.getString("KOD").trim());
			data.setName(rs.getString("NAZWA").trim());
			data.setSellPrice(rs.getBigDecimal("SPRZEDAZ"));
			data.setBuyPrice(rs.getBigDecimal("SKUP"));
			data.setOrdinal(rs.getInt("LP"));
			data.setChangeableCourse(rs.getBigDecimal("KURS_ZMIENNY"));
			data.setCount(rs.getInt("ILOSC"));

			try {
				InputStream flagStream = rs.getBinaryStream("FLAGA");

				data.setFlag(flagStream == null ? null : ImageIO.read(flagStream));
			} catch (IOException e) {
				data.setFlag(null);
			}
			data.setForks(rs.getBigDecimal("WIDELKI"));
			data.setState(rs.getBigDecimal("STAN"));
			return data;
		}
	}

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<CurrencyData> get() {
		return this.jdbcTemplate.query("SELECT ID,LP,KOD,NAZWA,ILOSC,SKUP,SPRZEDAZ,STAN,KURS_ZMIENNY,WIDELKI,FLAGA FROM WALUTA;", new CurrencyDataMapper());
	}

}
