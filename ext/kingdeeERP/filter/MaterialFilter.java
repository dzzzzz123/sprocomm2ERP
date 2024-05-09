package ext.kingdeeERP.filter;

import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;
import ext.kingdeeERP.Config;

public class MaterialFilter extends DefaultSimpleValidationFilter {

    @Override
    public UIValidationStatus preValidateAction(UIValidationKey uiValidationKey, UIValidationCriteria uiValidationCriteria) {
        return BaseFilter.baseValidateAction(uiValidationCriteria, Config.MaterialGroup(), Config.MaterialState());
    }
}
