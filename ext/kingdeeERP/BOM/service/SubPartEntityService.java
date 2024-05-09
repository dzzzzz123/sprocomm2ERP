package ext.kingdeeERP.BOM.service;

import ext.kingdeeERP.BOM.entity.SubPartEntity;
import ext.kingdeeERP.Config;
import ext.kingdeeERP.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import wt.fc.ObjectToObjectLink;
import wt.part.WTPart;
import wt.part.WTPartSubstituteLink;
import wt.part.WTPartUsageLink;

public class SubPartEntityService {

    /**
     * Base 获取部件对应的实体类
     *
     * @param part 部件对象
     * @return {@link SubPartEntity}
     */
    public static SubPartEntity getSubPartEntity(WTPart part) {
        SubPartEntity subPartEntity = new SubPartEntity();
        subPartEntity.setfMaterialIdChild(fillFMaterialIdChild(part));
        subPartEntity.setfFixScrapQty("0.0");
        subPartEntity.setfScrapRate("0.0");
        subPartEntity.setfReplacePolicy("1");
        subPartEntity.setfReplaceType("1");
        subPartEntity.setfIsMulCsd("false");
        subPartEntity.setfEffectDate(Config.EffectDate());
        subPartEntity.setfExpireDate(Config.ExpireDate());
        subPartEntity.setfIsMrpRun("true");
        return subPartEntity;
    }

    /**
     * 获取BOM子件 主料部件对象所映射成的实体类
     *
     * @param part 部件对象
     * @param link 部件使用关系
     * @return {@link SubPartEntity}
     */
    public static SubPartEntity getMasterPartEntity(WTPart part, WTPartUsageLink link) {
        SubPartEntity subPartEntity = getSubPartEntity(part);
        subPartEntity.setfMaterialType("1");
        subPartEntity.setfReplacePriority("0");
        subPartEntity.setfIskeyItem("true");
        subPartEntity.setfIsSkip(Config.IsSkip(link));
        subPartEntity.setfIsGetScrap(Config.IsGetScrap(link));
        subPartEntity.setfIsKeyComponent(Config.IsKeyComponent(link));
        subPartEntity.setFownerid(fillFOwnerId(part));
        fillBD_OwnerOrg(subPartEntity);
        subPartEntity.setfIsCustomerService(Config.IsCustomerService(link));
        subPartEntity.setfIsByCust(Config.IsByCust(part));
        subPartEntity.setfItemNote(Config.ItemNote(link));
        subPartEntity.setfPositionNo(Config.PositionNo(link));
        return subPartEntity;
    }

    /**
     * 获取BOM子件 替代料部件对象所映射成的实体类
     *
     * @param part 部件
     * @param link 替代料使用关系
     * @return {@link SubPartEntity}
     */
    public static SubPartEntity getSubstitutePartEntity(WTPart part, WTPartSubstituteLink link) {
        SubPartEntity subPartEntity = getSubPartEntity(part);
        subPartEntity.setfMaterialType("3");
        subPartEntity.setfIskeyItem("false");
        subPartEntity.setfIsSkip("false");
        subPartEntity.setfIsGetScrap("false");
        subPartEntity.setfIsKeyComponent(Config.IsKeyComponent(link));
        subPartEntity.setFownerid(fillFOwnerId(part));
        fillBD_OwnerOrg(subPartEntity);
        subPartEntity.setfIsByCust(Config.IsByCust(part));
        return subPartEntity;
    }

    /**
     * 将BOM中两种使用关系的数量转换为分数
     *
     * @param link 部件使用关系
     * @return {@link String[]}
     */
    public static String[] getFraction(ObjectToObjectLink link) {
        String[] nums = new String[]{"1", "1"};
        if (link instanceof WTPartSubstituteLink) {
            WTPartSubstituteLink substituteLink = (WTPartSubstituteLink) link;
            if (substituteLink.getQuantity() != null) {
                nums = CommonUtil.double2Fraction(substituteLink.getQuantity().getAmount());
            }
        } else if (link instanceof WTPartUsageLink) {
            WTPartUsageLink usageLink = (WTPartUsageLink) link;
            if (usageLink.getQuantity() != null) {
                nums = CommonUtil.double2Fraction(usageLink.getQuantity().getAmount());
            }
        }
        return nums;
    }

    private static SubPartEntity.FMATERIALIDCHILD fillFMaterialIdChild(WTPart part) {
        SubPartEntity.FMATERIALIDCHILD fmaterialidchild = new SubPartEntity.FMATERIALIDCHILD();
        fmaterialidchild.setFNumber(part.getNumber());
        return fmaterialidchild;
    }


    private static SubPartEntity.FOWNERID fillFOwnerId(WTPart part) {
        SubPartEntity.FOWNERID fownerid = new SubPartEntity.FOWNERID();
        String ownerId = Config.OwnerId(part);
        if (StringUtils.isBlank(ownerId)) {
            fownerid.setFNumber("100");
        } else {
            fownerid.setFNumber(Config.OwnerId(part));
        }
        return fownerid;
    }

    private static void fillBD_OwnerOrg(SubPartEntity subPartEntity) {
        SubPartEntity.FOWNERID fownerid = subPartEntity.getFownerid();
        String fNumber = fownerid.getFNumber();
        if (fNumber.equals("100")) {
            subPartEntity.setfOwnerTypeId("BD_OwnerOrg");
        } else {
            subPartEntity.setfOwnerTypeId("BD_Customer");
        }
    }
}
