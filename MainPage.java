package componentPanel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;


public class MainPage extends BasePage {

	public MainPage()
	{
		this.setOutputMarkupId(true);
		final Test test = new Test();
		final List<Persona> persone = new ArrayList();
		final PanelListView<Persona> panelList = new PanelListView<Persona>("panelList") {
			@Override
			final public List getHateList() {
				//return null;
				for(int i=0;	i<10;	i++)
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
				
				//return t.getPersone();
		
			}
			
			@Override
			public String[] getColumns() {
				String[] ret= new String[]{"unid","cognome","nome"};
				return ret;
			}
			
			

			@Override
			public void onSelect(AjaxRequestTarget target,Persona mli) {
				
				System.out.println(mli);
				//setResponsePage(new EditPersonaPage(mli));
				//setResponsePage(new EditPersonaPage(mli));
				//setResponsePage(new EditPersonaPage(mli));
				final Panel THIS=this;
				
				//PanelListPersona.this.replace(getEditor( target, mli));
				//THIS.setVisible(false);
				//target.add(this);
			}

		};
		this.add(panelList);
	}
}
