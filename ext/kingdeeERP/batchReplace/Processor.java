package ext.kingdeeERP.batchReplace;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import wt.util.WTException;

import java.util.List;

public class Processor extends DefaultObjectFormProcessor {
    @Override
    public FormResult doOperation(NmCommandBean arg0, List<ObjectBean> arg1) throws WTException {
        return super.doOperation(arg0, arg1);
    }
}
