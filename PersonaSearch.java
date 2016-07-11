package componentPanel;

/* Bean : PersonaSearch Begin */
public class PersonaSearch extends Persona {

	private String stringaCriterioLike="";
	private int criterio=0;
	
	public PersonaSearch()
	{}
	
	public int getCriterio() {
		return criterio;
	}
	public void setCriterio(int criterio) {
		this.criterio = criterio;
	}
	public String getStringaCriterioLike() {
		return stringaCriterioLike;
	}
	public void setStringaCriterioLike(String stringa) {
		this.stringaCriterioLike = stringa;
	}
}
/* Persona End */