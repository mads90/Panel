package componentPanel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8721534434765106356L;
	int N=10;
	List<Persona> persone = new ArrayList();	
		
	 public List getPersone()
	{
		persone.clear();
		for(int i=0;	i<N;	i++)
		{
			Persona p = new Persona();
			p.setCognome("cognome"+i);
			p.setNome("nome"+i);
			p.setDatadecesso(new Date());
			p.setDatanascita(new Date());
			p.setUnid(""+i);
			p.setComunenascita("comune"+i);
			persone.add( p);
		}
		return persone;
	}
}
