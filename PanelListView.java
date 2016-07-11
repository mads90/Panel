package componentPanel;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringResourceStream;
import org.apache.wicket.util.time.Duration;


public abstract class PanelListView<T> extends BasePanel implements Serializable, IMarkupCacheKeyProvider {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2764633536761330321L;
	Test t;
	private String selezionatoAttualmente;



	public PanelListView(String id) {
		
		super(id);
		this.setOutputMarkupId(true);

		Form<?> form = new Form("form");

		selezionatoAttualmente = "Adulto";
		final List<String> TYPES = Arrays.asList(new String[] { "Bambino","Ragazzo","Adulto" });
		/*final RadioChoice<String> eta = new RadioChoice<String>(
				"eta", new PropertyModel<String>( this, "selezionatoAttualmente"), TYPES){
		
	 };
		eta.setOutputMarkupId(true);*/
		final IModel hateList = new LoadableDetachableModel() {
			protected Object load() {
				return getHateList();
			}
		};
		/*ListView hateView = new ListView("hateList", hateList) {
			protected void populateItem(final ListItem item) {
				this.populateItem(item);	
				
			}
		};*/
		ListView hateView = new ListView("hateList", hateList) {
			protected void populateItem(final ListItem item) {
				final Persona mli = (Persona) item.getModelObject();

				item.add(new Label("nome", mli.getNome()));
				item.add(new Label("cognome", mli.getCognome()));
				item.add(new Label("unid", mli.getUnid()));
				AjaxButton button = new AjaxButton("seleziona") {

					@Override
					public void onSubmit(AjaxRequestTarget target, Form form) {
					
						onSelect(target,mli);
					}
				};
				/*final RadioGroup adminRadioGroup = new RadioGroup("radio-admin", new Model());
		        
		        adminRadioGroup.add(new Radio("radio-sys-admin", new Model()));
		        adminRadioGroup.add(new Radio("radio-dept-admin", new Model()));
		        adminRadioGroup.add(new Radio("radio-no-admin", new Model()));
				item.add(adminRadioGroup);*/

				item.add(button);
			}
		};
		
		// encapsulate the ListView in a WebMarkupContainer in order for it to
		// update
		final WebMarkupContainer listContainer = new WebMarkupContainer("theContainer");
		// generate a markup-id so the contents can be updated through an AJAX
		// call
		listContainer.setOutputMarkupId(true);
		//listContainer.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)));
		// add the list view to the container
		listContainer.add(hateView);
		// finally add the container to the page
		AjaxButton button = new AjaxButton("nuovo") {

			@Override
			public void onSubmit(AjaxRequestTarget target, Form form) {
			
				System.out.println(selezionatoAttualmente);
				//onNuovo(target);
				setResponsePage(MainPage.class);

			}
		};
		 
		final RadioChoice<String> eta = new RadioChoice<String>(
				"eta", new PropertyModel<String>( this, "selezionatoAttualmente"), TYPES);
		eta.setOutputMarkupId(true);

		form.add(listContainer);
		form.add(button);
		form.add(eta);
		this.add(form);
	}

	abstract public List<T> getHateList();
	//abstract public Panel getEditor(AjaxRequestTarget target,T t);
	//abstract public void onNuovo(AjaxRequestTarget target);
	abstract public void onSelect(AjaxRequestTarget target,Persona mli);
	public String[] getColumns() {
		String[] ret= new String[]{"unid","cognome","nome"};
		return ret;
	}
	
	@Override
	public String getCacheKey(MarkupContainer arg0, Class<?> arg1) {
		return null;
	}
	
	/*@Override
	public IResourceStream getMarkupResourceStream(MarkupContainer container,
			Class<?> containerClass) {
		/*String markup = "<wicket:panel><form wicket:id=\"form\"><table wicket:id=\"theContainer\"><tr wicket:id=\"hateList\">";
		
		//markup +=<tr>
		/*markup+="<th>UNID</th><th>NOME</th><th>COGNOME</th>";

		for(String s:getColumns()){
			markup += "<td><span wicket:id=\""+s+"\">"+s+"</span></td>";
		}
		markup += "<td><input type=\"submit\" wicket:id=\"edit\" value=\"invia\"></td>";
		//markup +="</tr>";
		/*for(String s:getColumns()){
			markup += "<td><span wicket:id=\""+s+"\">"+s+"</span></td>";
		}
		markup += "</wicket:panel>";*/
		
		/* markup +="</tr> </table><input type=\"submit\" wicket:id=\"invia\" value=\"invia\"></form></wicket:panel>";*/
	   
	
		/*String markup="<wicket:panel><form wicket:id=\"form\"><table wicket:id=\"theContainer\">"
	        +"<tr><th>UNID</th><th>NOME</th><th>COGNOME</th></tr> <tr wicket:id=\"hateList\">";
	    markup+="<td><span wicket:id=\"unid\">UNIDENTIFICATIVO</span></td><td><span wicket:id=\"nome\">NOME</span></td>"+
            "<td><span wicket:id=\"cognome\">COGNOME</span></td> <td><input type=\"submit\" wicket:id=\"seleziona\" value=\"seleziona\"></td></tr>";
	    markup+="</table><input type=\"submit\" wicket:id=\"nuovo\" value=\"nuovo\">";
	    markup+="</form></wicket:panel>";
		StringResourceStream resourceStream = new StringResourceStream(markup);

		return resourceStream;
	}*/
	
	/*public void populateItem(ListItem item) {
		Persona mli = (Persona) item.getModelObject();
		item.add(new Label("nome", mli.getNome()));
		item.add(new Label("cognome", mli.getCognome()));
		item.add(new Label("unid", mli.getUnid()));
		for(String s:getColumns()){
			item.add( new Label(s, new PropertyModel(mli, s) ) );			
		}
		AjaxButton button = new AjaxButton("salva") {

			@Override
			public void onSubmit(AjaxRequestTarget target, Form form) {
			
				onSelect(target,mli);
			}
		};
		item.add(button);
	}*/
}
