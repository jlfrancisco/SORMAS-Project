/*******************************************************************************
 * SORMAS® - Surveillance Outbreak Response Management & Analysis System
 * Copyright © 2016-2018 Helmholtz-Zentrum für Infektionsforschung GmbH (HZI)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package de.symeda.sormas.ui.utils;

import java.util.Arrays;
import java.util.Collections;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

public final class CssStyles {

	// Headlines
	public static final String H1 = "h1";
	public static final String H2 = "h2";
	public static final String H3 = "h3";
	public static final String H4 = "h4";
	public static final String H5 = "h5";
	
	public static final String VR = "vertical-rule";

	// Vertical space
	public static final String VSPACE_0 = "vspace-0";
	public static final String VSPACE_1 = "vspace-1";
	public static final String VSPACE_2 = "vspace-2";
	public static final String VSPACE_3 = "vspace-3";
	public static final String VSPACE_4 = "vspace-4";
	public static final String VSPACE_5 = "vspace-5";
	public static final String VSPACE_NONE = "vspace-none";
	
	public static final String VSPACE_TOP_0 = "vspace-top-0";
	public static final String VSPACE_TOP_1 = "vspace-top-1";
	public static final String VSPACE_TOP_2 = "vspace-top-2";
	public static final String VSPACE_TOP_3 = "vspace-top-3";
	public static final String VSPACE_TOP_4 = "vspace-top-4";
	public static final String VSPACE_TOP_5 = "vspace-top-5";
	public static final String VSPACE_TOP_NONE = "vspace-top-none";
	
	// Horizontal space
	public static final String HSPACE_LEFT_0 = "hspace-left-0";
	public static final String HSPACE_LEFT_1 = "hspace-left-1";
	public static final String HSPACE_LEFT_2 = "hspace-left-2";
	public static final String HSPACE_LEFT_3 = "hspace-left-3";
	public static final String HSPACE_LEFT_4 = "hspace-left-4";
	public static final String HSPACE_LEFT_5 = "hspace-left-5";
	public static final String HSPACE_LEFT_NONE = "hspace-left-none";
	
	public static final String HSPACE_RIGHT_0 = "hspace-right-0";
	public static final String HSPACE_RIGHT_1 = "hspace-right-1";
	public static final String HSPACE_RIGHT_2 = "hspace-right-2";
	public static final String HSPACE_RIGHT_3 = "hspace-right-3";
	public static final String HSPACE_RIGHT_4 = "hspace-right-4";
	public static final String HSPACE_RIGHT_5 = "hspace-right-5";
	public static final String HSPACE_RIGHT_NONE = "hspace-right-none";
	
	public static final String INDENT_LEFT_1 = "indent-left-1";
	public static final String INDENT_LEFT_2 = "indent-left-2";

	public static final String VAADIN_LABEL = "v-label";
	
	// Font colors
	public static final String LABEL_PRIMARY = "primary";
	public static final String LABEL_SECONDARY = "secondary";
	public static final String LABEL_CRITICAL = "critical";
	public static final String LABEL_WARNING = "warning";
	public static final String LABEL_IMPORTANT = "important";
	public static final String LABEL_RELEVANT = "relevant";
	public static final String LABEL_NEUTRAL = "neutral";
	public static final String LABEL_POSITIVE = "positive";
	public static final String LABEL_MINOR = "minor";
	public static final String LABEL_DISCARDED = "discarded";
	public static final String LABEL_DONE = "done";
	public static final String LABEL_NOT = "not";
	
	// Font sizes
	public static final String LABEL_SMALL = "small";
	public static final String LABEL_MEDIUM = "medium";
	public static final String LABEL_LARGE = "large";
	public static final String LABEL_XLARGE = "xlarge";
	public static final String LABEL_XXLARGE = "xxlarge";
	public static final String LABEL_XXXLARGE = "xxxlarge";
	public static final String LABEL_REALLY_LARGE = "really-large";
	
	// Font styles
	public static final String LABEL_BOLD = "bold";
	public static final String LABEL_UPPERCASE = "uppercase";
	
	// Label styles
	public static final String LABEL_BOTTOM_LINE = "bottom-line";
	public static final String LABEL_ROUNDED_CORNERS = "rounded-corners";
	public static final String LABEL_ROUNDED_CORNERS_SLIM = "rounded-corners-slim";
	public static final String LABEL_BACKGROUND_FOCUS_LIGHT = "background-focus-light";
	
	// Layout stlyes
	public static final String LAYOUT_MINIMAL = "minimal";
	public static final String BACKGROUND_ROUNDED_CORNERS = "background-rounded-corners";
	public static final String BACKGROUND_CRITERIA = "background-criteria";
	public static final String BACKGROUND_SUB_CRITERIA = "background-sub-criteria";
	public static final String BACKGROUND_SUSPECT_CRITERIA = "background-suspect-criteria";
	public static final String BACKGROUND_PROBABLE_CRITERIA = "background-probable-criteria";
	public static final String BACKGROUND_CONFIRMED_CRITERIA = "background-confirmed-criteria";
	public static final String NO_BORDER = "no-border";
	
	// Button styles
	public static final String VAADIN_BUTTON = "v-button";
	public static final String BUTTON_CRITICAL = "critical";
	public static final String BUTTON_WARNING = "warning";
	public static final String BUTTON_SUBTLE = "subtle";
	public static final String BUTTON_BORDER_NEUTRAL = "border-neutral";
	public static final String BUTTON_NO_PADDING = "no-padding";
	public static final String BUTTON_NO_UPPERCASE = "no-uppercase";
	public static final String LINK_ACTIVE = "active";
	public static final String LINK_HIGHLIGHTED = "link-highlighted";
	public static final String LINK_HIGHLIGHTED_LIGHT = "link-highlighted-light";
	public static final String LINK_HIGHLIGHTED_DARK = "link-highlighted-dark";
	public static final String LINK_HIGHLIGHTED_SMALL = "link-highlighted-small";
	public static final String BUTTON_FONT_SIZE_LARGE = "font-size-large";
	
	// List styles
	public static final String SORMAS_LIST = "s-list";
	public static final String SORMAS_LIST_ENTRY = "s-list-entry";
	
	
	// SVG fill and stroke styles
	public static final String SVG_FILL_PRIMARY = "svg-fill-primary";
	public static final String SVG_FILL_BACKGROUND = "svg-fill-background";
	public static final String SVG_FILL_CRITICAL = "svg-fill-critical";
	public static final String SVG_FILL_IMPORTANT = "svg-fill-important";
	public static final String SVG_FILL_POSITIVE = "svg-fill-positive";
	public static final String SVG_FILL_MINOR = "svg-fill-minor";
	public static final String SVG_FILL_NEUTRAL = "svg-fill-neutral";
	public static final String SVG_STROKE_PRIMARY = "svg-stroke-primary";
	public static final String SVG_STROKE_CRITICAL = "svg-stroke-critical";
	public static final String SVG_STROKE_IMPORTANT = "svg-stroke-important";
	public static final String SVG_STROKE_POSITIVE = "svg-stroke-positive";
	public static final String SVG_STROKE_MINOR = "svg-stroke-minor";
	public static final String SVG_STROKE_BACKGROUND = "svg-stroke-background";

	/**
	 * Stellt sicher, dass CM-Checkboxen dieselbe Höhe wie Textfelder etc. haben
	 */
	public static final String FORCE_CAPTION = "force-caption";
	public static final String CAPTION_HIDDEN = "caption-hidden";
	
	public static final String SOFT_REQUIRED = "soft-required";
	public static final String ERROR_COLOR_PRIMARY = "error-color-primary";

	public static final String ALIGN_CENTER = "align-center";
	public static final String ALIGN_RIGHT = "align-right";

	public static final String OPTIONGROUP_HORIZONTAL_SUBTLE = "horizontal-subtle";
	public static final String OPTIONGROUP_HORIZONTAL_PRIMARY = "horizontal-primary";
	public static final String OPTIONGROUP_HORIZONTAL_SWITCH_CRITICAL = "horizontal-switch-critical";
	public static final String OPTIONGROUP_CAPTION_INLINE = "caption-inline";

	public static final String GRID_CELL_PRIORITY_HIGH = "priority-high";
	public static final String GRID_CELL_PRIORITY_NORMAL = "priority-normal";
	public static final String GRID_CELL_PRIORITY_LOW = "priority-low";
	public static final String GRID_CELL_WARNING = "warning";

	public static final String GRID_CELL_ODD = "odd";

	public static final String GRID_ROW_STATUS_DISCARDED = "status-discarded";
	public static final String GRID_ROW_STATUS_NOT = "status-not";
	public static final String GRID_ROW_STATUS_DONE = "status-done";
	public static final String GRID_ROW_STATUS_PENDING = "status-pending";
	public static final String GRID_ROW_TITLE = "row-title";
	
	public static final String LABEL_CONFIGURATION_SEVERITY_INDICATOR = "severity-indicator";
	public static final String BADGE = "badge";
	
	public static final String SPACING_SMALL = "spacing-small";
	
	// Checkbox styles
	public static final String CHECKBOX_FILTER_INLINE = "filter-inline";
	
	// Combo Box styles
	public static final String COMBO_BOX_SUBTLE = "subtle";
	
	// Layout components
	public static final String ROOT_COMPONENT = "root-component";
	public static final String MAIN_COMPONENT = "main-component";
	public static final String SIDE_COMPONENT = "side-component";
	
	// Statistics layout
	public static final String STATISTICS_TITLE_BOX = "title-box";
	public static final String STATISTICS_TITLE = "title";
	
	/**
	 * Example: <code>LayoutUtil.fluidColumnLocCss(CssStyles.LAYOUT_COL_HIDE_INVSIBLE, 3, 0, PersonDto.CAUSE_OF_DEATH_DISEASE)</code>
	 */
	public static final String LAYOUT_COL_HIDE_INVSIBLE = "hide-invisble";
	
	@Deprecated
	public static final String CALLOUT = "callout";
	
	public static String buildVaadinStyle(String primaryStyle, String... styles) {
		StringBuilder styleBuilder = new StringBuilder();
		styleBuilder.append(primaryStyle);
		for (String style : styles) {
			styleBuilder.append(" ").append(primaryStyle).append("-").append(style);
		}
		return styleBuilder.toString();
	}

	public static void style(Component component, String... styles) {
		for (String style : styles)
			component.addStyleName(style);
	}
	
	public static void style(String style, Component... components) {
		for (Component component : components) {
			component.addStyleName(style);
		}
	}
	
	public static void removeStyles(Component component, String... styles) {
		for (String style : styles) {
			component.removeStyleName(style);
		}
	}

	public static void stylePrimary(Component component, String primaryStyle, String... styles) {
		component.setPrimaryStyleName(primaryStyle);
		for (String style : styles)
			component.addStyleName(style);
	}

	/**
	 * Styles and (de-)activates the given buttons.
	 * 
	 * @param activeButton
	 *		This button is styled as active but disabled.
	 * @param allOrOtherButtons
	 *		These buttons lose their active styling and become enabled. {@code activeButton} may be included here.
	 */
	public static void styleSectionFilterButton(Button activeButton, Button... allOrOtherButtons) {

		styleSectionFilterButton(activeButton, allOrOtherButtons == null ? Collections.emptyList() : Arrays.asList(allOrOtherButtons));
	}

	/**
	 * Styles and (de-)activates the given buttons.
	 * 
	 * @param activeButton
	 *		This button is styled as active but disabled.
	 * @param allOrOtherButtons
	 *		These buttons lose their active styling and become enabled. {@code activeButton} may be included here.
	 */
	public static <B extends Button> void styleSectionFilterButton(Button activeButton, Iterable<B> allOrOtherButtons) {

		for (Button button : allOrOtherButtons) {
			button.setEnabled(true);
			button.removeStyleName(CssStyles.LINK_ACTIVE);
		}

		activeButton.setEnabled(false);
		activeButton.addStyleName(CssStyles.LINK_ACTIVE);
	}
}
