package componentPanel;

import org.apache.wicket.markup.html.panel.Panel;

public class BasePanel extends Panel {


	private static final long serialVersionUID = 6450346136632720874L;
	
	public BasePanel(String id) {
		super(id);
		this.setOutputMarkupId(true);
	}

	/*public OspedaleManager getManager() {
		return OspedaleManager.getManager();
	}*/
}
