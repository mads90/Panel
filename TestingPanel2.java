package it.cup2000.carceri.testing;

import it.cup2000.carceri.web.CarceriWebConstants;
import it.cup2000.carceri.web.cartella.domain.OperazioneFoto;
import it.cup2000.carceri.web.cartella.fase2.domain.AnagSoggetto;
import it.cup2000.carceri.web.common.CarceriPrivilege;
import it.cup2000.carceri.web.common.ProtectedPanel;
import it.cup2000.carceri.web.prescrizioni.specialistiche.domain.PrestazioneSearch;
import it.cup2000.codevalue.domain.CodeValue;
import it.cup2000.wicket.component.CCheckBox;
import it.cup2000.wicket.component.CDatePicker;
import it.cup2000.wicket.component.CDropDownChoice;
import it.cup2000.wicket.component.CLabel;
import it.cup2000.wicket.component.CNumericTextField;
import it.cup2000.wicket.component.CTextArea;
import it.cup2000.wicket.component.CTextField;
import it.cup2000.wicket.component.CodeValueDropDownChoice2;
import it.cup2000.wicket.component.CodeValueKeyOnlyDropDownChoice;
import it.cup2000.wicket.component.CDropDownChoice.DropDownChoiceNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class TestingPanel extends ProtectedPanel {

	private static final long serialVersionUID = 1L;

	Test t;
	private Persona persona;
	private List<String> uomoDonna;
	private IModel testModel;

	public TestingPanel(String id) {
		super(id, CarceriPrivilege.WRITE);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		persona = new Persona();
		testModel = new Model(persona);
		uomoDonna = new ArrayList<String>();
		uomoDonna.add("uomo");
		uomoDonna.add("donna");
		this.add(new PersonaForm());
	}

	private class PersonaForm extends Form<Persona> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5013911894294111805L;

		public PersonaForm() {
			super(CarceriWebConstants.FORM, new CompoundPropertyModel<Persona>(persona));
			setOutputMarkupPlaceholderTag(true);
			final CTextField<String> nomeC = new CTextField<String>("nome", true);
			final CCheckBox cNomeC = new CCheckBox("nomeOk", false);
			final CTextField<String> cognomeC = new CTextField<String>("cognome", true);
			final CCheckBox cCognomeC = new CCheckBox("cognomeOk", false);
			final CDropDownChoice<String> sessoC = new CDropDownChoice<String>("sessoCh", false, DropDownChoiceNull.EMPTY, new PropertyModel<String>(testModel, "sesso"),
			        uomoDonna);
			final CCheckBox cSessoC = new CCheckBox("sessoOk", false);
			final CTextArea<String> txAreaC = new CTextArea<String>("txArea", true);
			txAreaC.setEnabled(false);
			final CTextArea<String> txSecAreaC = new CTextArea<String>("txSecArea", true);
			txSecAreaC.setVisible(false);
			final CCheckBox cTxSecAreaC = new CCheckBox("txAreaOk", false);
			System.out.println(uomoDonna);
			//nomeC.add(onChangeSubmit());
			//cognomeC.add(onChangeSubmit());
			//cNomeC.add( onChangeSubmit());
			//cCognomeC.add(onChangeSubmit());
			cTxSecAreaC.add(new OnChangeAjaxBehavior() {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					if (persona.txAreaOk)
						txSecAreaC.setVisible(false);
					else
						txSecAreaC.setVisible(true);
				}

			});
			final AjaxButton button = new AjaxButton("stampaPersona") {

				@Override
				public void onSubmit(AjaxRequestTarget target, Form form) {

					persona.setTxArea("");
					if (persona.isCognomeOk()) {
						persona.setTxArea("COGNOME :\t" + persona.getCognome() + "\n");
					}
					if (persona.isNomeOk()) {
						persona.setTxArea(persona.getTxArea() + "NOME:\t" + persona.getNome());
					}
					if (persona.isSessoOk()) {
						persona.setTxArea(persona.getTxArea() + "SESSO:\t" + persona.getSesso());
					}
					target.add(txAreaC);
				}
			};

			this.add(nomeC);
			this.add(cNomeC);
			this.add(cognomeC);
			this.add(cCognomeC);
			this.add(sessoC);
			this.add(cSessoC);
			this.add(button);
			this.add(txAreaC);
			this.add(txSecAreaC);
			this.add(cTxSecAreaC);
		}
	}

	private AjaxFormComponentUpdatingBehavior onChangeSubmit() {
		return new OnChangeAjaxBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {

			}

		};
	}

	public class Persona implements Serializable {

		private static final long serialVersionUID = 1L;
		private String unid;
		private String nome;
		private String cognome;
		private String sesso;
		private Date dataNascita;
		private boolean nomeOk;
		private boolean cognomeOk;
		private boolean sessoOk;
		private boolean txAreaOk;
		private String txArea;
		private String txSecArea;

		@Override
		public String toString() {
			return "nome = " + getNome() + "\tCognome: " + getCognome() + "\tnomeOK = " + isNomeOk() + "\tcognomeOk = " + isCognomeOk() + "\n";
		}

		public Persona() {
			this.setNomeOk(false);
			this.setCognomeOk(false);
			this.txAreaOk = false;
			this.setNome("");
			this.setCognome("");
		}

		public String getUnid() {
			return unid;
		}

		public void setUnid(String unid) {
			this.unid = unid;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getCognome() {
			return cognome;
		}

		public void setCognome(String cognome) {
			this.cognome = cognome;
		}

		public Date getDataNascita() {
			return dataNascita;
		}

		public void setDataNascita(Date dataNascita) {
			this.dataNascita = dataNascita;
		}

		public String getSesso() {
			return sesso;
		}

		public void setSesso(String sesso) {
			this.sesso = sesso;
		}

		public String getShortCombo() {
			return "" + getUnid() + getNome() + getCognome();
		}

		public boolean isNomeOk() {
			return nomeOk;
		}

		public void setNomeOk(boolean nomeOk) {
			this.nomeOk = nomeOk;
		}

		public boolean isCognomeOk() {
			return cognomeOk;
		}

		public void setCognomeOk(boolean cognomeOk) {
			this.cognomeOk = cognomeOk;
		}

		public String getTxArea() {
			return txArea;
		}

		public void setTxArea(String txArea) {
			this.txArea = txArea;
		}

		public boolean isSessoOk() {
			return sessoOk;
		}

		public void setSessoOk(boolean sessoOk) {
			this.sessoOk = sessoOk;
		}

		public String getTxSecArea() {
			return txSecArea;
		}

		public void setTxSecArea(String txSecArea) {
			this.txSecArea = txSecArea;
		}

	}

	public class Test implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8721534434765106356L;
		int N = 10;
		List<Persona> persone = new ArrayList();

		public List getPersone() {
			persone.clear();
			for (int i = 0; i < N; i++) {
				Persona p = new Persona();
				p.setCognome("cognome" + i);
				p.setNome("nome" + i);
				p.setUnid("" + i);
				persone.add(p);
			}
			return persone;
		}
	}

}
