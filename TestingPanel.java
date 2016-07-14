

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class TestingPanel extends ProtectedPanel {

	private static final long serialVersionUID = 1L;

	private static final String[] names = { "Doe, John", "Presley, Elvis", "Presly, Priscilla", "John, Elton", "Jackson, Michael", "Bush, George", "Baker, George",
	        "Stallone, Sylvester", "Murphy, Eddie", "Potter, Harry", "Balkenende, Jan Peter", "Two Shoes, Goody", "Goodman, John", "Candy, John", "Belushi, James",
	        "Jones, James Earl", "Kelly, Grace", "Osborne, Kelly", "Cartman", "Kenny", "Schwarzenegger, Arnold", "Pitt, Brad", "Richie, Nicole", "Richards, Denise",
	        "Sheen, Charlie", "Sheen, Martin", "Esteves, Emilio", "Baldwin, Alec", "Knowles, Beyonce", "Affleck, Ben", "Lavigne, Avril", "Cuthbert, Elisha", "Longoria, Eva",
	        "Clinton, Bill", "Willis, Bruce", "Farrell, Colin", "Hasselhoff, David", "Moore, Demi", };
	
	private final IModel searchModel = new Model();
	 CodeValueDropDownChoice2<CodeValue> statoPrestazione;
	 private final IModel modelDomainObject = new Model();
	public TestingPanel(String id) {
		super(id, CarceriPrivilege.WRITE);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();


		PrestazioneSearch search = new PrestazioneSearch();
		System.out.println(search.getOrderBy());

		WebMarkupContainer contenitore = new WebMarkupContainer("data");
		contenitore.setOutputMarkupId(true);
		add(contenitore);
		final List<CodeValue> listaEsiti = getManager().getCommonManager().loadCodiciEsiti();
		PageableListView<String> listview = new PageableListView<String>("rows", Arrays.asList(names), 10) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<String> item) {
				item.add(new CLabel("name", item.getDefaultModelObjectAsString()));
				item.add( new CNumericTextField("num", false));
				item.add(  new CodeValueDropDownChoice2<CodeValue>("tipoEsito", false, DropDownChoiceNull.EMPTY,
						new PropertyModel<CodeValue>(getSearchModel(), "statoPrestazione"), listaEsiti));
			}

		};
	
		final CTextArea<String> note = new CTextArea<String>("descrizione", false, new PropertyModel<String>(getModelDomainObject(), "note"));
		contenitore.add(listview);
		contenitore.add(new AjaxPagingNavigator("navigator", listview));
		contenitore.add( note);
		contenitore.setVersioned(false);

	}
	public IModel getModelDomainObject() {
		return modelDomainObject;
	}
	public final IModel getSearchModel() {
		return searchModel;
	}


}
