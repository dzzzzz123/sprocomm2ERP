package ext.kingdeeERP.filter;

import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationStatus;
import ext.kingdeeERP.util.CommonUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import wt.fc.Persistable;
import wt.fc.WTReference;
import wt.org.OrganizationServicesHelper;
import wt.org.WTGroup;
import wt.org.WTPrincipal;
import wt.part.WTPart;
import wt.session.SessionHelper;

import java.util.Locale;
import java.util.Set;

public class BaseFilter {

    static Logger logger = LogManager.getLogger(BaseFilter.class);

    public static UIValidationStatus baseValidateAction(UIValidationCriteria uiValidationCriteria, String Group, Set<String> permissionState) {
        UIValidationStatus status = UIValidationStatus.HIDDEN;
        try {
            WTPrincipal currentUser = SessionHelper.manager.getPrincipal();
            WTGroup foundGroup = CommonUtil.getGroup(Group);
            boolean groupFlag = OrganizationServicesHelper.manager.isMember(foundGroup, currentUser);
            WTReference contextObject = uiValidationCriteria.getContextObject();
            Persistable object = contextObject.getObject();
            boolean stateFlag = false;
            if (object instanceof WTPart) {
                WTPart part = new WTPart();
                String partDisState = part.getState().getState().getDisplay(Locale.CHINA);
                stateFlag = permissionState.contains(partDisState);
            }
            if (groupFlag && stateFlag) {
                status = UIValidationStatus.ENABLED;
            }
        } catch (Exception e) {
            logger.error("判断用户是否有发送物料权限出错", e);
        }
        return status;
    }
}
