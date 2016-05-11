package pizzicato.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pizzicato.model.Asiakas;
import pizzicato.model.Pizza;
import pizzicato.model.PizzaTilaus;
import pizzicato.model.Tilaus;

public class PizzaTilausDAO extends DataAccessObject {
	
	public PizzaTilaus readPizzaTilaus(ResultSet rs) {
		try {
			int tilausId=rs.getInt("tilaus_id");
			int pizzaId=rs.getInt("pizza_id");
			String pNimi=rs.getString("p_nimi");
			String pSaatavuus=rs.getString("p_saatavuus");
			double pHinta=rs.getDouble("p_hinta");
			int lkm=rs.getInt("lkm");
			Pizza pizza;
			pizza = new Pizza(pizzaId, pNimi, pHinta, pSaatavuus);
			return new PizzaTilaus(pizza, tilausId, lkm);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void addPizzaTilaus(PizzaTilaus pizzatil) throws SQLException {
		Connection conn = null;
		PreparedStatement stmtInsert = null;		
		try {
			conn = getConnection();
			String sqlInsert = "INSERT INTO pizzatilaus(tilaus_id, pizza_id, lkm) VALUES ("+pizzatil.getTilausId()+","+pizzatil.getPizza().getPizzaId()+","+pizzatil.getLkm()+");";
			stmtInsert = conn.prepareStatement(sqlInsert);		
			stmtInsert.executeUpdate();
					            
		}catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(stmtInsert, conn); 
		}		
	}
	
	public PizzaTilaus deletePizzafromTilaus(PizzaTilaus pizzatil){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sqlDelete ="DELETE FROM pizzatilaus WHERE tilaus_id="+pizzatil.getTilausId()+" AND pizza_id="+pizzatil.getPizza().getPizzaId()+";";
			stmt=conn.prepareStatement(sqlDelete);
			rs=stmt.executeQuery();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(rs,stmt,conn);
		}
		return null;
		
	}
	
	public void modifyPizzaLkm(PizzaTilaus pizzatil) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			String sqlUpdate =  "UPDATE pizzatilaus SET lkm="+pizzatil.getLkm()+" WHERE tilaus_id="+pizzatil.getTilausId()+" AND pizza_id="+pizzatil.getPizza().getPizzaId()+";";
			stmt = conn.prepareStatement(sqlUpdate);
					
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally {
			close(stmt,conn);
		}
	}
	
}
