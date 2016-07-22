package it.cup2000.carceri.testing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import it.cup2000.carceri.web.CarceriWebConstants;
import it.cup2000.carceri.web.common.CarceriPrivilege;
import it.cup2000.carceri.web.common.Cloner;
import it.cup2000.carceri.web.common.ProtectedPanel;
import it.cup2000.wicket.component.CCheckBox;
import it.cup2000.wicket.component.CDropDownChoice;
import it.cup2000.wicket.component.CDropDownChoice.DropDownChoiceNull;
import it.cup2000.wicket.component.CLabel;
import it.cup2000.wicket.component.CTextArea;
import it.cup2000.wicket.component.CTextField;
import it.cup2000.wicket.table.AccessibleAjaxDataTable;
import it.cup2000.wicket.table.AccessibleDataTable;

public class TestingPanel extends ProtectedPanel {

	private static final long serialVersionUID = 1L;

	private Persona persona;
	private List<String> uomoDonna;
	private Boolean boolAreaOk;

	private List<Persona> persone;
	private IModel<Persona> personaModel;

	public IModel<Persona> getPersonaModel() {
		return personaModel;
	}

	public void setPersonaModel(IModel<Persona> testModel) {
		this.personaModel = testModel;
	}

	private IModel<Boolean> modelCheck;
	private AccessibleDataTable<Persona> personeTable;

	public TestingPanel(String id) {
		super(id, CarceriPrivilege.WRITE);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		persona = new Persona();
		persone = new ArrayList<Persona>();
		personaModel = new Model<Persona>(persona);
		this.setBoolAreaOk(true);
		uomoDonna = new ArrayList<String>();
		uomoDonna.add("uomo");
		uomoDonna.add("donna");

		modelCheck = new Model<Boolean>(boolAreaOk);

		this.add(new PersonaForm());
	}

	public Boolean getBoolAreaOk() {
		return boolAreaOk;
	}

	public void setBoolAreaOk(Boolean boolAreaOk) {
		this.boolAreaOk = boolAreaOk;
	}

	public List<Persona> getPersone() {
		return persone;
	}

	public void setPersone(List<Persona> persone) {
		this.persone = persone;
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
			final CTextField<String> cognomeC = new CTextField<String>("cognome", true);
			final CDropDownChoice<String> sessoC = new CDropDownChoice<String>("sessoCh", true,
					DropDownChoiceNull.EMPTY, new PropertyModel<String>(personaModel, "sesso"), uomoDonna);
			final CTextArea<String> txAreaC = new CTextArea<String>("txArea", true);

			final CCheckBox cTxAreaC = new CCheckBox("txAreaOk", true, modelCheck);
			cTxAreaC.add(new OnChangeAjaxBehavior() {
				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					setBoolAreaOk(!getBoolAreaOk());
					txAreaC.setRequired(getBoolAreaOk());
					target.add(txAreaC);
				}
			});

			final AjaxButton button = new AjaxButton("button") {
				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					Persona persona2 = new Persona();
					persona2 = Cloner.clone(persona);
					persone.add(persona2);

					target.add(personeTable);
				}
			};
			final List<IColumn<Persona, String>> columns = new ArrayList<>();
			columns.add(new AbstractColumn<Persona, String>(new Model<String>("cognome")) {
				private static final long serialVersionUID = 1L;

				@Override
				public void populateItem(Item<ICellPopulator<Persona>> cellItem, String componentId,
						IModel<Persona> rowModel) {
					cellItem.add(new CLabel(componentId, rowModel.getObject().getCognome()));
				}
			});
			columns.add(new AbstractColumn<Persona, String>(new Model<String>("nome")) {
				private static final long serialVersionUID = 1L;

				@Override
				public void populateItem(Item<ICellPopulator<Persona>> cellItem, String componentId,
						IModel<Persona> rowModel) {

					cellItem.add(new CLabel(componentId, rowModel.getObject().getNome()));
				}
			});
			columns.add(new AbstractColumn<Persona, String>(new Model<String>("sesso")) {
				private static final long serialVersionUID = 1L;

				@Override
				public void populateItem(Item<ICellPopulator<Persona>> cellItem, String componentId,
						IModel<Persona> rowModel) {
					cellItem.add(new CLabel(componentId, rowModel.getObject().getSesso()));
				}
			});
			columns.add(new AbstractColumn<Persona, String>(new Model<String>("desc")) {
				private static final long serialVersionUID = 1L;

				@Override
				public void populateItem(Item<ICellPopulator<Persona>> cellItem, String componentId,
						IModel<Persona> rowModel) {
					cellItem.add(new CLabel(componentId, rowModel.getObject().getTxArea()));
				}
			});

			personeTable = new AccessibleAjaxDataTable<Persona>("personeTable", columns,
					new SortableDataProvider<Persona, String>() {

						private static final long serialVersionUID = 1L;

						@Override
						public long size() {
							return getPersone().size();
						}

						@Override
						public Iterator<? extends Persona> iterator(long first, long count) {
							return getPersone().iterator();
						}

						@Override
						public IModel<Persona> model(Persona object) {

							return new Model<Persona>(object);
						}

					}, 10);

			this.add(nomeC);
			this.add(cognomeC);
			this.add(sessoC);
			this.add(button);
			this.add(txAreaC);
			this.add(cTxAreaC);
			this.add(personeTable);
		}
	}

	public class Persona implements Serializable {

		private static final long serialVersionUID = 1L;
		private String nome;
		private String cognome;
		private String sesso;
		private String txArea;

		@Override
		public String toString() {
			return getNome() + " " + getCognome() + " " + getSesso() + " " + getTxArea() + " ";
		}

		public Persona() {
			this.setNome("");
			this.setCognome("");
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

		public String getSesso() {
			return sesso;
		}

		public void setSesso(String sesso) {
			this.sesso = sesso;
		}

		public String getTxArea() {
			return txArea;
		}

		public void setTxArea(String txArea) {
			this.txArea = txArea;
		}

		public Boolean getBoolAreaOk() {
			return boolAreaOk;
		}

	}

}
