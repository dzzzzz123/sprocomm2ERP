package ext.kingdeeERP.batchReplace;

import com.ptc.core.components.forms.FormProcessingStatus;
import com.ptc.core.components.forms.FormProcessorController;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.util.FeedbackMessage;
import com.ptc.core.ui.resources.FeedbackType;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import wt.session.SessionHelper;
import wt.util.WTException;

import java.util.Map;

public class Controller implements FormProcessorController {
    @Override
    public FormResult execute(NmCommandBean nmCommandBean) throws WTException {
        Map<String, Object> paramMap = nmCommandBean.getParameterMap();
        String[] numbers = new String[]{};
        String[] newPrices = new String[]{};
        String[] newPriceUnits = new String[]{};
        for (String key : paramMap.keySet()) {
            Object value = paramMap.get(key);
//			String singleValue = value instanceof String[] ? ((String[]) value)[0] : value.toString();
            if (key.equals("number")) {
                numbers = (String[]) value;
            } else if (key.equals("newPrice")) {
                newPrices = (String[]) value;
            } else if (key.equals("newPriceUnit")) {
                newPriceUnits = (String[]) value;
            }
        }

        FormResult result = new FormResult(FormProcessingStatus.SUCCESS);
        result.addFeedbackMessage(new FeedbackMessage(FeedbackType.SUCCESS, SessionHelper.getLocale(), null, null,
                new String[]{"更新BOM替代料成功!"}));
        return result;
    }
}
