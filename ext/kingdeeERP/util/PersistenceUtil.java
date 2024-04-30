package ext.kingdeeERP.util;

import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.common.impl.DefaultIdentifierFactory;
import com.ptc.core.meta.type.mgmt.common.TypeDefinitionDefaultView;
import com.ptc.core.meta.type.mgmt.server.impl.WTTypeDefinition;
import org.apache.log4j.Logger;
import wt.epm.util.EPMSoftTypeServerUtilities;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.services.ServiceFactory;
import wt.type.TypeDefinitionReference;
import wt.type.TypedUtilityService;
import wt.util.WTException;
import wt.vc.Iterated;
import wt.vc.Mastered;
import wt.vc.config.ConfigHelper;
import wt.vc.config.LatestConfigSpec;
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

    public static String getPartTypeInternalName(WTPart part) {
        String InternalName = null;
        TypeDefinitionReference ref = (part).getTypeDefinitionReference();
        try {
            TypeDefinitionDefaultView view = EPMSoftTypeServerUtilities.getTypeDefinition(ref);
            WTTypeDefinition definition = (WTTypeDefinition) PersistenceHelper.manager.refresh(view.getObjectID());
            InternalName = definition.getName();
        } catch (WTException e) {
            logger.error("获取部件子类型内部名称出错" + e.getMessage(), e);
        }
        return InternalName;
    }

    /**
     * 获取部件子类型显示名称
     *
     * @param part 部件对象
     * @return {@link String}
     */
    public static String getPartTypeDisPlayName(WTPart part) {
        String displayName = null;
        try {
            String internalName = getPartTypeInternalName(part);
            DefaultIdentifierFactory factory = new DefaultIdentifierFactory();
            TypeIdentifier tid = factory.newWCTypeIdentifier(internalName);
            TypedUtilityService service = ServiceFactory.getService(TypedUtilityService.class);
            displayName = service.getLocalizedTypeName(tid, Locale.CHINA);
            // String displayName = TypeHelper.getTypeIdentifierDisplayName(
            //        TypeIdentifierHelper.getTypeIdentifier(typeDisplayName), Locale.CHINA);
        } catch (WTException | RemoteException e) {
            logger.error("获取部件子类型显示名称出错" + e.getMessage(), e);
        }
        return displayName;
    }

    /**
     * 获取最新版本的对象
     *
     * @param master 对象
     * @return Iterated 最新对象
     */
    public static Iterated getLatestObj(Mastered master) {
        try {
            // 获取master对象的所有版本的Iterated对象
            QueryResult localQueryResult = ConfigHelper.service.filteredIterationsOf(master, new LatestConfigSpec());
            while ((localQueryResult.hasMoreElements())) {
                Iterated iterated = (Iterated) localQueryResult.nextElement();
                // 判断对象是否为最新
                if (iterated.isLatestIteration()) {
                    return iterated;
                }
            }
        } catch (WTException e) {
            logger.error("获取对象的最新版本对象时出错", e);
        }
        return null;
    }
}
