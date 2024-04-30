package ext.kingdeeERP.BOM.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class SubPartEntity {
    @JsonProperty("FReplaceGroup")
    private String fReplaceGroup;
    @JsonProperty("FMATERIALIDCHILD")
    private FMATERIALIDCHILD fMaterialIdChild;
    @JsonProperty("FMATERIALTYPE")
    private String fMaterialType;
    @JsonProperty("FNUMERATOR")
    private String fNumberator;
    @JsonProperty("FDENOMINATOR")
    private String fDenominator;
    @JsonProperty("FFIXSCRAPQTY")
    private String fFixScrapQty;
    @JsonProperty("FSCRAPRATE")
    private String fScrapRate;
    @JsonProperty("FReplacePolicy")
    private String fReplacePolicy;
    @JsonProperty("FReplaceType")
    private String fReplaceType;
    @JsonProperty("FReplacePriority")
    private String fReplacePriority;
    @JsonProperty("FIskeyItem")
    private String fIskeyItem;
    @JsonProperty("FISSkip")
    private String fIsSkip;
    @JsonProperty("FIsMulCsd")
    private String fIsMulCsd;
    @JsonProperty("FEFFECTDATE")
    private String fEffectDate;
    @JsonProperty("FEXPIREDATE")
    private String fExpireDate;
    @JsonProperty("FISGETSCRAP")
    private String fIsGetScrap;
    @JsonProperty("FISKEYCOMPONENT")
    private String fIsKeyComponent;
    @JsonProperty("FOWNERTYPEID")
    private String fOwnerTypeId;
    @JsonProperty("FOWNERID")
    private FOWNERID fownerid;
    @JsonProperty("FIsMrpRun")
    private String fIsMrpRun;
    @JsonProperty("FISCUSTOMERSERVICE")
    private String fIsCustomerService;
    @JsonProperty("FISBYCUST")
    private String fIsByCust;
    @JsonProperty("FITEMNOTE")
    private String fItemNote;
    @JsonProperty("FPOSITIONNO")
    private String fPositionNo;

    @JsonIgnore
    public String getfReplaceGroup() {
        return fReplaceGroup;
    }

    @JsonIgnore
    public void setfReplaceGroup(String fReplaceGroup) {
        this.fReplaceGroup = fReplaceGroup;
    }

    @JsonIgnore
    public FMATERIALIDCHILD getfMaterialIdChild() {
        return fMaterialIdChild;
    }

    @JsonIgnore
    public void setfMaterialIdChild(FMATERIALIDCHILD fMaterialIdChild) {
        this.fMaterialIdChild = fMaterialIdChild;
    }

    @JsonIgnore
    public String getfMaterialType() {
        return fMaterialType;
    }

    @JsonIgnore
    public void setfMaterialType(String fMaterialType) {
        this.fMaterialType = fMaterialType;
    }

    @JsonIgnore
    public String getfNumberator() {
        return fNumberator;
    }

    @JsonIgnore
    public void setfNumberator(String fNumberator) {
        this.fNumberator = fNumberator;
    }

    @JsonIgnore
    public String getfDenominator() {
        return fDenominator;
    }

    @JsonIgnore
    public void setfDenominator(String fDenominator) {
        this.fDenominator = fDenominator;
    }

    @JsonIgnore
    public String getfFixScrapQty() {
        return fFixScrapQty;
    }

    @JsonIgnore
    public void setfFixScrapQty(String fFixScrapQty) {
        this.fFixScrapQty = fFixScrapQty;
    }

    @JsonIgnore
    public String getfScrapRate() {
        return fScrapRate;
    }

    @JsonIgnore
    public void setfScrapRate(String fScrapRate) {
        this.fScrapRate = fScrapRate;
    }

    @JsonIgnore
    public String getfReplacePolicy() {
        return fReplacePolicy;
    }

    @JsonIgnore
    public void setfReplacePolicy(String fReplacePolicy) {
        this.fReplacePolicy = fReplacePolicy;
    }

    @JsonIgnore
    public String getfReplaceType() {
        return fReplaceType;
    }

    @JsonIgnore
    public void setfReplaceType(String fReplaceType) {
        this.fReplaceType = fReplaceType;
    }

    @JsonIgnore
    public String getfReplacePriority() {
        return fReplacePriority;
    }

    @JsonIgnore
    public void setfReplacePriority(String fReplacePriority) {
        this.fReplacePriority = fReplacePriority;
    }

    @JsonIgnore
    public String getfIskeyItem() {
        return fIskeyItem;
    }

    @JsonIgnore
    public void setfIskeyItem(String fIskeyItem) {
        this.fIskeyItem = fIskeyItem;
    }

    @JsonIgnore
    public String getfIsSkip() {
        return fIsSkip;
    }

    @JsonIgnore
    public void setfIsSkip(String fIsSkip) {
        this.fIsSkip = fIsSkip;
    }

    @JsonIgnore
    public String getfIsMulCsd() {
        return fIsMulCsd;
    }

    @JsonIgnore
    public void setfIsMulCsd(String fIsMulCsd) {
        this.fIsMulCsd = fIsMulCsd;
    }

    @JsonIgnore
    public String getfEffectDate() {
        return fEffectDate;
    }

    @JsonIgnore
    public void setfEffectDate(String fEffectDate) {
        this.fEffectDate = fEffectDate;
    }

    @JsonIgnore
    public String getfExpireDate() {
        return fExpireDate;
    }

    @JsonIgnore
    public void setfExpireDate(String fExpireDate) {
        this.fExpireDate = fExpireDate;
    }

    @JsonIgnore
    public String getfIsGetScrap() {
        return fIsGetScrap;
    }

    @JsonIgnore
    public void setfIsGetScrap(String fIsGetScrap) {
        this.fIsGetScrap = fIsGetScrap;
    }

    @JsonIgnore
    public String getfIsKeyComponent() {
        return fIsKeyComponent;
    }

    @JsonIgnore
    public void setfIsKeyComponent(String fIsKeyComponent) {
        this.fIsKeyComponent = fIsKeyComponent;
    }

    @JsonIgnore
    public String getfOwnerTypeId() {
        return fOwnerTypeId;
    }

    @JsonIgnore
    public void setfOwnerTypeId(String fOwnerTypeId) {
        this.fOwnerTypeId = fOwnerTypeId;
    }

    @JsonIgnore
    public FOWNERID getFownerid() {
        return fownerid;
    }

    @JsonIgnore
    public void setFownerid(FOWNERID fownerid) {
        this.fownerid = fownerid;
    }

    @JsonIgnore
    public String getfIsMrpRun() {
        return fIsMrpRun;
    }

    @JsonIgnore
    public void setfIsMrpRun(String fIsMrpRun) {
        this.fIsMrpRun = fIsMrpRun;
    }

    @JsonIgnore
    public String getfIsCustomerService() {
        return fIsCustomerService;
    }

    @JsonIgnore
    public void setfIsCustomerService(String fIsCustomerService) {
        this.fIsCustomerService = fIsCustomerService;
    }

    @JsonIgnore
    public String getfIsByCust() {
        return fIsByCust;
    }

    @JsonIgnore
    public void setfIsByCust(String fIsByCust) {
        this.fIsByCust = fIsByCust;
    }

    @JsonIgnore
    public String getfItemNote() {
        return fItemNote;
    }

    @JsonIgnore
    public void setfItemNote(String fItemNote) {
        this.fItemNote = fItemNote;
    }

    @JsonIgnore
    public String getfPositionNo() {
        return fPositionNo;
    }

    @JsonIgnore
    public void setfPositionNo(String fPositionNo) {
        this.fPositionNo = fPositionNo;
    }

    public static class FMATERIALIDCHILD {
        @JsonProperty("FNumber")
        private String fNumber;

        @JsonIgnore
        public String getFNumber() {
            return fNumber;
        }

        @JsonIgnore
        public void setFNumber(String fNumber) {
            this.fNumber = fNumber;
        }
    }

    public static class FOWNERID {
        @JsonProperty("FNumber")
        private String fNumber;

        @JsonIgnore
        public String getFNumber() {
            return fNumber;
        }

        @JsonIgnore
        public void setFNumber(String fNumber) {
            this.fNumber = fNumber;
        }
    }
}