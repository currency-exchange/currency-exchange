package pl.jw.currency.exchange.dao.kantor2008;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pl.jw.currency.exchange.dao.api.CurrencyState;
import pl.jw.currency.exchange.dao.api.ICurrencyDAO;
import pl.jw.currency.exchange.dao.api.Transaction;
import pl.jw.currency.exchange.dao.api.TransactionType;

@Repository
public class Kantor2008DAO implements ICurrencyDAO {

	@Autowired
	private DSLContext sql;

	private static final class CurrencyDataMapper implements
	RowMapper<CurrencyState> {

		@Override
		public CurrencyState mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			CurrencyState data = new CurrencyState();
			data.setSymbol(rs.getString("KOD").trim());
			data.setName(rs.getString("NAZWA").trim());
			data.setSellPrice(rs.getBigDecimal("SPRZEDAZ"));
			data.setBuyPrice(rs.getBigDecimal("SKUP"));
			data.setOrdinal(rs.getInt("LP"));
			data.setChangeableCourse(rs.getBigDecimal("KURS_ZMIENNY"));
			data.setCount(rs.getInt("ILOSC"));

			try {
				InputStream flagStream = rs.getBinaryStream("FLAGA");

				data.setFlag(flagStream == null ? null : ImageIO
						.read(flagStream));
			} catch (IOException e) {
				data.setFlag(null);
			}
			data.setForks(rs.getBigDecimal("WIDELKI"));
			data.setState(rs.getBigDecimal("STAN"));
			return data;
		}
	}

	private static final class TransactionaMapper implements
	RowMapper<Transaction> {

		@Override
		public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
			Transaction data = new Transaction();
			data.setCurrencySymbol(rs.getString("KOD").trim());
			data.setNumber(rs.getString("NUMER_OPISOWY").trim());
			data.setPrice(rs.getBigDecimal("CENA"));
			data.setQuantity(rs.getBigDecimal("ILOSC"));
			data.setType(TransactionType.byName(rs.getString("TYP")));
			data.setValue(rs.getBigDecimal("WARTOSC"));
			return data;
		}
	}

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<CurrencyState> get() {
		return this.jdbcTemplate
				.query("SELECT ID,LP,KOD,NAZWA,ILOSC,SKUP,SPRZEDAZ,STAN,KURS_ZMIENNY,WIDELKI,FLAGA FROM WALUTA ORDER BY LP;",
						new CurrencyDataMapper());
	}

	@Override
	public List<Transaction> getTransactions(LocalDate date) {
		Objects.requireNonNull(date);
		String today = date.toString();

		// TODO: jooq
		// sql.select(
		// field(name("PARAGON.NUMER", "PARAGON.NUMER_OPISOWY",
		// "POZ_PARAGONU.rodzaj TYP", "waluta.KOD",
		// "POZ_PARAGONU.ILOSC", "POZ_PARAGONU.CENA",
		// "POZ_PARAGONU.WARTOSC"))).from("POZ_PARAGONU")
		// .join(table(name("PARAGON")), JoinType.JOIN).on(condition(Operator.,
		// conditions));

		String caseRodzaj = " when 10 then 'BUY'" // KUPNO
				+ "    when 11 then 'SELL'" // SPRZEDAŻ
				+ "    when 18 then 'SELL_TO_BANK'" // SPRZEDAŻ BANK
				+ "    when 14 then 'CANCELATION'" // ANULOWANIE
				+ "    when 12 then 'IN'" // WPŁATA
				+ "    when 13 then 'OUT'" // WYPŁATA
				+ "    when 19 then 'IN_BANK'"// WPŁATA BANK
				+ "    when 20 then 'OUT_BANK'"// WYPŁATA BANK
				+ "    when 22 then 'INPUT'"// WPROWADZENIE
				+ "    when 23 then 'OUTPUT'"// WYPROWADZENIE
				+ "    else 'UNKNOWN'"
				+ "   end ";

		List<Transaction> list = this.jdbcTemplate
				.query("select PARAGON.NUMER, PARAGON.NUMER_OPISOWY, "
						+ "case POZ_PARAGONU.rodzaj"
						+ caseRodzaj
						+ " TYP ,"
						+ "   waluta.KOD,"
						+ "   POZ_PARAGONU.ILOSC,"
						+ "   POZ_PARAGONU.CENA,"
						+ "   POZ_PARAGONU.WARTOSC"
						+ "   from POZ_PARAGONU"
						+ " inner join PARAGON on PARAGON.id = POZ_PARAGONU.PARAGON_ID AND PARAGON.DATA = '"
						+ today
						+ "'"
						+ " inner join WALUTA on WALUTA.id=POZ_PARAGONU.WALUTA_ID"
						+ " union "
						+ " select PARAGON.NUMER, PARAGON.NUMER_OPISOWY, "
						+ "case PARAGON.rodzaj"
						+ caseRodzaj
						+ " TYP ,"
						+ "   'PLN',"
						+ "   PARAGON.WARTOSC,"
						+ "   1.00,"
						+ "   PARAGON.WARTOSC"
						+ "    from PARAGON"
						+ " where PARAGON.RODZAJ in (12,13) and PARAGON.SPOSOB_ZAPLATY_ID is null and PARAGON.DATA = '"
						+ today + "'" + " order by 1 desc",
						new TransactionaMapper());

		return list;
	}

	@Override
	public BigDecimal getCashboxState() {
		return this.jdbcTemplate
				.queryForObject(
						"select  sum("
								+ " case"
								+ " when PARAGON.RODZAJ in (11, 18, 12, 19, 22) then PARAGON.WARTOSC"
								+ " when PARAGON.RODZAJ in (14) then 0"
								+ " when PARAGON.RODZAJ  in (10,13, 20, 23) then  -PARAGON.WARTOSC"
								+ " end " + ") from PARAGON", BigDecimal.class);
	}
}
