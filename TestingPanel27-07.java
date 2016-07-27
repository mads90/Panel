package it.cup2000.carceri.testing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
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
import it.cup2000.carceri.web.common.components.AbstractConfirmPanel;
import it.cup2000.codevalue.domain.CodeValue;
import it.cup2000.wicket.component.CCheckBox;
import it.cup2000.wicket.component.CDropDownChoice;
import it.cup2000.wicket.component.CDropDownChoice.DropDownChoiceNull;
import it.cup2000.wicket.component.CLabel;
import it.cup2000.wicket.component.CModalWindow;
import it.cup2000.wicket.component.CTextArea;
import it.cup2000.wicket.component.CTextField;
import it.cup2000.wicket.component.CodeValueDropDownChoice2;
import it.cup2000.wicket.table.AJAXLinkColumn;
import it.cup2000.wicket.table.AccessibleAjaxDataTable;
import it.cup2000.wicket.table.AccessibleDataTable;

public class TestingPanel extends ProtectedPanel {

	private static final long serialVersionUID = 1L;

	private Persona persona;
	private final ModalWindow modaleCancella = new CModalWindow("modale");
	private CDropDownChoice<Persona> personeC;

	private List<String> uomoDonna;
	private Boolean boolAreaOk;

	private List<Persona> persone;
	private IModel<Persona> personaModel;

	private IModel<Boolean> modelCheck;
	private AccessibleDataTable<Persona> personeTable;

	private int labelText;
	private AvvisoCancellazionePanel avv;

	public int getLabelText() {
		return labelText;
	}

	public void setLabelText(int labelText) {
		this.labelText = labelText;
	}

	public IModel<Integer> getLabelModel() {
		return labelModel;
	}

	public void setLabelModel(IModel<Integer> labelModel) {
		this.labelModel = labelModel;
	}

	private IModel<Integer> labelModel;

	private CLabel labelTxt;

	public IModel<Persona> getPersonaModel() {
		return personaModel;
	}

	public void setPersonaModel(IModel<Persona> testModel) {
		this.personaModel = testModel;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public AccessibleDataTable<Persona> getPersoneTable() {
		return personeTable;
	}

	public void setPersoneTable(AccessibleDataTable<Persona> personeTable) {
		this.personeTable = personeTable;
	}

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
		setLabelModel(new Model<Integer>(getLabelText()));
		modelCheck = new Model<Boolean>(boolAreaOk);
		final IModel<Persona> personaCan = new Model<Persona>();
		this.add(new PersonaForm());

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
		columns.add(new AJAXLinkColumn<Persona>(new Model<String>("Cancella"), new Model<String>("Cancella")) {

			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(String componentId, IModel<Persona> model, AjaxRequestTarget target) {

				personaCan.setObject(model.getObject());
				modaleCancella.show(target);
			}

		});

		avv = new AvvisoCancellazionePanel("content", "Sicuro?", personaCan);
		modaleCancella.setContent(avv);

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

				}, 10) {
			/**
					 *
					 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {

				return !persone.isEmpty();
			}
		}

		;

		columns.add(new AbstractColumn<Persona, String>(new Model<String>("Unid")) {
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem(Item<ICellPopulator<Persona>> cellItem, String componentId,
					IModel<Persona> rowModel) {
				cellItem.add(new CLabel(componentId, rowModel.getObject().getUnid()));
			}
		});
		personeC = new CDropDownChoice<Persona>("personaCh", true, DropDownChoiceNull.EMPTY, personaModel,
				getPersone());
		personeC.add(new OnChangeAjaxBehavior() {

			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				getLabelModel().setObject(personeC.getModelObject().getUnid());
				target.add(labelTxt);
			}

		});

		labelTxt = new CLabel("labelTxt", getLabelModel());

		final CodeValueDropDownChoice2<CodeValue> codeDDC = new CodeValueDropDownChoice2<CodeValue>("codeDDC", false,
				DropDownChoiceNull.Trattini, new Model<CodeValue>(),
				getManager().getCarceriPrescrizioneManager().loadPrioritaFarmacologiche());
		codeDDC.add(new OnChangeAjaxBehavior() {
			/**
			*
			*/
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if (codeDDC.getModelObject() != null)
					System.out.println("Code: " + codeDDC.getModelObject().getValue());

			}
		});
		add(labelTxt);
		add(personeC);
		add(codeDDC);
		modaleCancella.setOutputMarkupPlaceholderTag(true);
		modaleCancella.setTitle("Cancellare Elemento?");
		modaleCancella.setResizable(false);
		modaleCancella.setInitialWidth(500);
		modaleCancella.setInitialHeight(200);
		modaleCancella.setWidthUnit("px");
		modaleCancella.setHeightUnit("px");
		modaleCancella.setCookieName(null);

		add(personeTable);
		add(modaleCancella);
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

			final AjaxButton button = new AjaxButton("inserisci") {
				/**
				*
				*/
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					this.setOutputMarkupId(true);
					getPersona().setUnid(getPersona().getUnid() + 1);
					Persona persona2 = new Persona();
					persona2 = Cloner.clone(getPersona());
					getPersone().add(persona2);

					target.add(getPersoneTable());
					target.add(personeC);

				}
			};

			this.add(nomeC);
			this.add(cognomeC);
			this.add(sessoC);
			this.add(button);
			this.add(txAreaC);
			this.add(cTxAreaC);
		}
	}

	public class Persona implements Serializable {

		private static final long serialVersionUID = 1L;
		private String nome;
		private String cognome;
		private String sesso;
		private String txArea;
		private int unid;

		public int getUnid() {
			return unid;
		}

		public void setUnid(int unid) {
			this.unid = unid;
		}

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

	public class AvvisoCancellazionePanel extends AbstractConfirmPanel {

		// private final String domanda;
		private IModel<Persona> personaCancellare;

		public AvvisoCancellazionePanel(String id, String domanda, IModel<Persona> personaCan) {
			super(id);
			this.personaCancellare = personaCan;
			// this.domanda = domanda;
		}

		@Override
		protected void onInitialize() {
			super.onInitialize();
			// getConfirmPanelForm().add(new Label("domandaConferma",
			// Model.of(domanda)).setEscapeModelStrings(false));
		}

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected void onAnswerNo(AjaxRequestTarget target) {
			modaleCancella.close(target);
		}

		@Override
		protected void onAnswerYes(AjaxRequestTarget target) {
			getPersone().remove(personaCancellare.getObject());

			target.add(personeTable);
			target.add(personeC);
			modaleCancella.close(target);
		}

	}
}
