package ext.kingdeeERP.masterData;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormProcessingStatus;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.util.FeedbackMessage;
import com.ptc.core.ui.resources.FeedbackType;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import org.apache.log4j.Logger;
import wt.fc.WTObject;
import wt.session.SessionHelper;
import wt.util.WTException;

import java.util.List;

public class Processor extends DefaultObjectFormProcessor {

    static Logger logger = Logger.getLogger(Processor.class.getName());

    @Override
    public FormResult doOperation(NmCommandBean arg0, List<ObjectBean> arg1) throws WTException {
        WTObject ref = (WTObject) arg0.getPrimaryOid().getRef();
        FormResult formresult;
        String msg;

        try {
            msg = Controller.sendParts2ERP(ref);
        } catch (Exception e) {
            formresult = new FormResult(FormProcessingStatus.FAILURE);
            formresult.addFeedbackMessage(new FeedbackMessage(FeedbackType.FAILURE, SessionHelper.getLocale(), null,
                    null, "发送物料主数据失败！", e.getMessage()));
            logger.error("发送物料主数据失败", e);
            return formresult;
        }

        if (msg != null) {
            formresult = new FormResult(FormProcessingStatus.FAILURE);
            formresult.addFeedbackMessage(
                    new FeedbackMessage(FeedbackType.FAILURE, SessionHelper.getLocale(), null, null, msg));
        } else {
            formresult = new FormResult(FormProcessingStatus.SUCCESS);
            formresult.addFeedbackMessage(new FeedbackMessage(FeedbackType.SUCCESS, SessionHelper.getLocale(), null,
                    null, "物料发送到ERP成功!"));
        }
        return formresult;

    }

}
