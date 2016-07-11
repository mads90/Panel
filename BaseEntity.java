package componentPanel;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable{
	String cancellato = "N";
	String nuovo = "S";

	Date dataInizio;
	Date dataFine;
	Integer from = 1;
	Integer to = 10000;
	String	orderBy;

	public boolean checkIsNuovo() {
		return "S".equals(nuovo);
	}

	public String getNuovo() {
		return nuovo;
	}

	public void setNuovo(String nuovo) {
		this.nuovo = nuovo;
	}

	public String getCancellato() {
		return cancellato;
	}

	public void setCancellato(String cancellato) {
		this.cancellato = cancellato;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	
	
}
