package panelDDC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import panelAutoComplete.PanelAutoComplete;
import panelAutoComplete.SecondPage;
import panelDDC.domain.Persona;
import panelDDC.test.TestDomain;

public class PanelDDC extends Panel implements Serializable{

	private Persona persona, persona2;
	private TestDomain test;
	
	public PanelDDC(String id) {
		super(id);

		this.setOutputMarkupId(true);
		
		Form<?> form = new Form("form");
		persona = new Persona();
		test = new TestDomain();
		test.setRandomPersone(10);
		
		final List<Persona> persone = new ArrayList<Persona>();	
		
		final DropDownChoice<Persona> ddcCriterio = new DropDownChoice<Persona>("ddcPersone",
				new PropertyModel(this, "persona"), test.getPersone(), new ChoiceRenderer<Persona>( "shortCombo", "unid")) {
			@Override
			public List<? extends Persona> getChoices() {

				return test.getPersone();
			}
		};
		ddcCriterio.setOutputMarkupId(true);
		
		AjaxButton button = new AjaxButton("button") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {

				//System.out.println(persona+" hashCode: "+System.identityHashCode( persona));
				persona2 = new Persona();
				persona2.setCognome(persona.getCognome());
				persona2.setNome(persona.getNome());
				persona2.setUnid(persona.getUnid());
				
				System.out.println(persona+" hashCode: "+System.identityHashCode( persona));
				System.out.println(persona2+" hashCode: "+System.identityHashCode( persona2));
				System.out.println(System.identityHashCode( persona)+" e "+System.identityHashCode( persona2)+
						"sono uguali: "+persona.equals(persona2));
				target.add(PanelDDC.this);
			}
		};
		AjaxButton cambia = new AjaxButton("cambia") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {

				setResponsePage( SecondPage.class);
			}
		};
		cambia.setOutputMarkupId(true);
		button.setOutputMarkupId(true);
		form.add(ddcCriterio);
		form.add(button);
		form.add(cambia);
		this.add(form);
	}


}
