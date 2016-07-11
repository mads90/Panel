package panelDDC.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/* Bean : Persona Begin */
public class Persona extends BaseEntity implements Serializable{
	
	private String cognome;
	private String nome;
	private String comunenascita;
	private Date datadecesso;
	private Date datanascita;
	private String unid;

	public Persona()
	{
		this.cognome="Di Lisa";
		this.nome="Davide";
		this.comunenascita="Galatonio";
		this.datadecesso = new Date();
		this.datanascita = new Date();
		this.unid=""+13;
		this.setNuovo("F");
	}
	public void setCognome(String a) {
		cognome = a;
	}

	public String getCognome() {
		return cognome;
	}

	public void setComunenascita(String a) {
		comunenascita = a;
	}

	public String getComunenascita() {
		return comunenascita;
	}

	public void setDatadecesso(Date a) {
		datadecesso = a;
	}

	public Date getDatadecesso() {
		return datadecesso;
	}

	public void setDatanascita(Date a) {
		datanascita = a;
	}

	public Date getDatanascita() {
		return datanascita;
	}

	public void setNome(String a) {
		nome = a;
	}

	public String getNome() {
		return nome;
	}

	public void setUnid(String a) {
		unid = a;
	}

	public String getUnid() {
		return unid;
	}
	
	public String getShortCombo(){
		return ""+getUnid()+getNome()+getCognome();
	}
	@Override
	public boolean equals(Object obj) {
		if( obj instanceof Persona)
		{
			return ((Persona)obj).getUnid().equalsIgnoreCase(this.getUnid());
		}
		return super.equals(obj);
	}
	@Override
	public String toString() {
		return "Persona [cognome=" + cognome + ", comunenascita=" + comunenascita + ", datadecesso=" + datadecesso
				+ ", datanascita=" + datanascita + ", nome=" + nome + ", unid=" + unid+
				",]";
	}

	
}
/* Persona End */
