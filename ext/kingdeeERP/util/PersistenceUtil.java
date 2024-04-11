package ext.kingdeeERP.util;

import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.common.impl.DefaultIdentifierFactory;
import com.ptc.core.meta.type.mgmt.common.TypeDefinitionDefaultView;
import com.ptc.core.meta.type.mgmt.server.impl.WTTypeDefinition;
import org.apache.log4j.Logger;
import wt.epm.util.EPMSoftTypeServerUtilities;
import wt.fc.PersistenceHelper;
import wt.part.WTPart;
import wt.services.ServiceFactory;
import wt.type.TypeDefinitionReference;
import wt.type.TypedUtilityService;
import wt.util.WTException;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;

import java.rmi.RemoteException;
import java.util.Locale;

public class PersistenceUtil {

    static Logger logger = Logger.getLogger(PersistenceUtil.class.getName());


    /**
     * 判断对象是否处于检出状态
     *
     * @param workable 对象
     * @return boolean 结果
     */
    public static boolean isCheckOut(Workable workable) {
        try {
            return WorkInProgressHelper.isCheckedOut(workable);
        } catch (WTException e) {
            logger.error("判断对象是否检出报错" + e.getMessage(), e);
        }
        return false;
    }


    public static String getPartTypeDisPlayName(WTPart part) {
        String displayName = null;
        TypeDefinitionReference ref = (part).getTypeDefinitionReference();
        DefaultIdentifierFactory factory = new DefaultIdentifierFactory();
        try {
            TypeDefinitionDefaultView view = EPMSoftTypeServerUtilities.getTypeDefinition(ref);
            WTTypeDefinition definition = (WTTypeDefinition) PersistenceHelper.manager.refresh(view.getObjectID());
            String typeInternalName = definition.getName();
            TypeIdentifier tid = factory.newWCTypeIdentifier(typeInternalName);
            TypedUtilityService service = ServiceFactory.getService(TypedUtilityService.class);
            displayName = service.getLocalizedTypeName(tid, Locale.CHINA);
            // String displayName = TypeHelper.getTypeIdentifierDisplayName(
            //        TypeIdentifierHelper.getTypeIdentifier(typeDisplayName), Locale.CHINA);
        } catch (WTException | RemoteException e) {
            logger.error("获取部件子类型显示名称出错" + e.getMessage(), e);
        }
        return displayName;
    }

}
