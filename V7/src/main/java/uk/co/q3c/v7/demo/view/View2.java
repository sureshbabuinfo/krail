package uk.co.q3c.v7.demo.view;

import javax.inject.Inject;

import uk.co.q3c.v7.base.view.components.FooterBar;
import uk.co.q3c.v7.base.view.components.HeaderBar;

public class View2 extends DemoViewBase {

	@Inject
	protected View2(FooterBar footerBar, HeaderBar headerBar) {
		super(footerBar, headerBar);
		addNavButton("view 1", view1);
		addNavButton("view 1 with parameters", view1 + "/id=22");
		addNavButton("home", home);
		addNavButton("home with parameters", home + "/id=2/age=15");
		addNavButton("invalid uri", "view3/id=22");

	}

}
