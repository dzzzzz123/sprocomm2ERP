package ext.kingdeeERP.masterData;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class Entity {
    @JsonProperty("parameters")
    private Object[] parameters;

    @JsonIgnore
    public Object[] getParameters() {
        return parameters;
    }

    @JsonIgnore
    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public static class SubEntity {

        @JsonProperty("NeedUpDateFields")
        private List<?> needUpDateFields;
        @JsonProperty("NeedReturnFields")
        private List<?> needReturnFields;
        @JsonProperty("IsDeleteEntry")
        private String isDeleteEntry;
        @JsonProperty("SubSystemId")
        private String subSystemId;
        @JsonProperty("IsVerifyBaseDataField")
        private String isVerifyBaseDataField;
        @JsonProperty("IsEntryBatchFill")
        private String isEntryBatchFill;
        @JsonProperty("ValidateFlag")
        private String validateFlag;
        @JsonProperty("NumberSearch")
        private String numberSearch;
        @JsonProperty("IsAutoAdjustField")
        private String isAutoAdjustField;
        @JsonProperty("InterationFlags")
        private String interationFlags;
        @JsonProperty("IgnoreInterationFlag")
        private String ignoreInterationFlag;
        @JsonProperty("IsControlPrecision")
        private String isControlPrecision;
        @JsonProperty("ValidateRepeatJson")
        private String validateRepeatJson;
        @JsonProperty("IsAutoSubmitAndAudit")
        private String IsAutoSubmitAndAudit;

        @JsonProperty("Model")
        private Model model;

        @JsonIgnore
        public List<?> getNeedUpDateFields() {
            return needUpDateFields;
        }

        @JsonIgnore
        public void setNeedUpDateFields(List<?> needUpDateFields) {
            this.needUpDateFields = needUpDateFields;
        }

        @JsonIgnore
        public List<?> getNeedReturnFields() {
            return needReturnFields;
        }

        @JsonIgnore
        public void setNeedReturnFields(List<?> needReturnFields) {
            this.needReturnFields = needReturnFields;
        }

        @JsonIgnore
        public String getIsDeleteEntry() {
            return isDeleteEntry;
        }

        @JsonIgnore
        public void setIsDeleteEntry(String isDeleteEntry) {
            this.isDeleteEntry = isDeleteEntry;
        }

        @JsonIgnore
        public String getSubSystemId() {
            return subSystemId;
        }

        @JsonIgnore
        public void setSubSystemId(String subSystemId) {
            this.subSystemId = subSystemId;
        }

        @JsonIgnore
        public String getIsVerifyBaseDataField() {
            return isVerifyBaseDataField;
        }

        @JsonIgnore
        public void setIsVerifyBaseDataField(String isVerifyBaseDataField) {
            this.isVerifyBaseDataField = isVerifyBaseDataField;
        }

        @JsonIgnore
        public String getIsEntryBatchFill() {
            return isEntryBatchFill;
        }

        @JsonIgnore
        public void setIsEntryBatchFill(String isEntryBatchFill) {
            this.isEntryBatchFill = isEntryBatchFill;
        }

        @JsonIgnore
        public String getValidateFlag() {
            return validateFlag;
        }

        @JsonIgnore
        public void setValidateFlag(String validateFlag) {
            this.validateFlag = validateFlag;
        }

        @JsonIgnore
        public String getNumberSearch() {
            return numberSearch;
        }

        @JsonIgnore
        public void setNumberSearch(String numberSearch) {
            this.numberSearch = numberSearch;
        }

        @JsonIgnore
        public String getIsAutoAdjustField() {
            return isAutoAdjustField;
        }

        @JsonIgnore
        public void setIsAutoAdjustField(String isAutoAdjustField) {
            this.isAutoAdjustField = isAutoAdjustField;
        }

        @JsonIgnore
        public String getInterationFlags() {
            return interationFlags;
        }

        @JsonIgnore
        public void setInterationFlags(String interationFlags) {
            this.interationFlags = interationFlags;
        }

        @JsonIgnore
        public String getIgnoreInterationFlag() {
            return ignoreInterationFlag;
        }

        @JsonIgnore
        public void setIgnoreInterationFlag(String ignoreInterationFlag) {
            this.ignoreInterationFlag = ignoreInterationFlag;
        }

        @JsonIgnore
        public String getIsControlPrecision() {
            return isControlPrecision;
        }

        @JsonIgnore
        public void setIsControlPrecision(String isControlPrecision) {
            this.isControlPrecision = isControlPrecision;
        }

        @JsonIgnore
        public String getValidateRepeatJson() {
            return validateRepeatJson;
        }

        @JsonIgnore
        public void setValidateRepeatJson(String validateRepeatJson) {
            this.validateRepeatJson = validateRepeatJson;
        }

        @JsonIgnore
        public String getIsAutoSubmitAndAudit() {
            return IsAutoSubmitAndAudit;
        }

        @JsonIgnore
        public void setIsAutoSubmitAndAudit(String isAutoSubmitAndAudit) {
            IsAutoSubmitAndAudit = isAutoSubmitAndAudit;
        }

        @JsonIgnore
        public Model getModel() {
            return model;
        }

        @JsonIgnore
        public void setModel(Model model) {
            this.model = model;
        }

        public static class Model {
            @JsonProperty("FMATERIALID")
            private String fMaterialId;
            @JsonProperty("FCreateOrgId")
            private FCreateOrgId fCreateOrgId;
            @JsonProperty("FUseOrgId")
            private FUseOrgId fUseOrgId;
            @JsonProperty("FNumber")
            private String fNumber;
            @JsonProperty("FName")
            private String fName;
            @JsonProperty("FSpecification")
            private String fSpecification;
            @JsonProperty("FDescription")
            private String fDescription;
            @JsonProperty("FSUPPLIERFULLNAME")
            private String fSupplierFullName;
            @JsonProperty("FISCHECKSAMPLE")
            private String fIsCheckSample;
            @JsonProperty("FCLASSIFICATION")
            private String fClassification;
            @JsonProperty("FMnemonicCode")
            private String FMnemonicCode;
            @JsonProperty("FMaterialGroup")
            private FMaterialGroup fMaterialGroup;
            @JsonProperty("SubHeadEntity")
            private SubHeadEntity subHeadEntity;
            @JsonProperty("SubHeadEntity3")
            private SubHeadEntity3 subHeadEntity3;
            @JsonProperty("SubHeadEntity4")
            private SubHeadEntity4 subHeadEntity4;
            @JsonProperty("SubHeadEntity5")
            private SubHeadEntity5 subHeadEntity5;

            @JsonIgnore
            public String getfMaterialId() {
                return fMaterialId;
            }

            @JsonIgnore
            public void setfMaterialId(String fMaterialId) {
                this.fMaterialId = fMaterialId;
            }

            @JsonIgnore
            public FCreateOrgId getFCreateOrgId() {
                return fCreateOrgId;
            }

            @JsonIgnore
            public void setFCreateOrgId(FCreateOrgId fCreateOrgId) {
                this.fCreateOrgId = fCreateOrgId;
            }

            @JsonIgnore
            public FUseOrgId getFUseOrgId() {
                return fUseOrgId;
            }

            @JsonIgnore
            public void setFUseOrgId(FUseOrgId fUseOrgId) {
                this.fUseOrgId = fUseOrgId;
            }

            @JsonIgnore
            public String getFNumber() {
                return fNumber;
            }

            @JsonIgnore
            public void setFNumber(String fNumber) {
                this.fNumber = fNumber;
            }

            @JsonIgnore
            public String getFName() {
                return fName;
            }

            @JsonIgnore
            public void setFName(String fName) {
                this.fName = fName;
            }

            @JsonIgnore
            public String getFSpecification() {
                return fSpecification;
            }

            @JsonIgnore
            public void setFSpecification(String fSpecification) {
                this.fSpecification = fSpecification;
            }

            @JsonIgnore
            public String getFDescription() {
                return fDescription;
            }

            @JsonIgnore
            public void setFDescription(String fDescription) {
                this.fDescription = fDescription;
            }

            @JsonIgnore
            public String getfSupplierFullName() {
                return fSupplierFullName;
            }

            @JsonIgnore
            public void setfSupplierFullName(String fSupplierFullName) {
                this.fSupplierFullName = fSupplierFullName;
            }

            @JsonIgnore
            public String getfIsCheckSample() {
                return fIsCheckSample;
            }

            @JsonIgnore
            public void setfIsCheckSample(String fIsCheckSample) {
                this.fIsCheckSample = fIsCheckSample;
            }

            @JsonIgnore
            public String getfClassification() {
                return fClassification;
            }

            @JsonIgnore
            public void setfClassification(String fClassification) {
                this.fClassification = fClassification;
            }

            @JsonIgnore
            public String getFMnemonicCode() {
                return FMnemonicCode;
            }

            @JsonIgnore
            public void setFMnemonicCode(String FMnemonicCode) {
                this.FMnemonicCode = FMnemonicCode;
            }

            @JsonIgnore
            public FMaterialGroup getFMaterialGroup() {
                return fMaterialGroup;
            }

            @JsonIgnore
            public void setFMaterialGroup(FMaterialGroup fMaterialGroup) {
                this.fMaterialGroup = fMaterialGroup;
            }

            @JsonIgnore
            public SubHeadEntity getSubHeadEntity() {
                return subHeadEntity;
            }

            @JsonIgnore
            public void setSubHeadEntity(SubHeadEntity subHeadEntity) {
                this.subHeadEntity = subHeadEntity;
            }

            @JsonIgnore
            public SubHeadEntity3 getSubHeadEntity3() {
                return subHeadEntity3;
            }

            @JsonIgnore
            public void setSubHeadEntity3(SubHeadEntity3 subHeadEntity3) {
                this.subHeadEntity3 = subHeadEntity3;
            }

            @JsonIgnore
            public SubHeadEntity4 getSubHeadEntity4() {
                return subHeadEntity4;
            }

            @JsonIgnore
            public void setSubHeadEntity4(SubHeadEntity4 subHeadEntity4) {
                this.subHeadEntity4 = subHeadEntity4;
            }

            @JsonIgnore
            public SubHeadEntity5 getSubHeadEntity5() {
                return subHeadEntity5;
            }

            @JsonIgnore
            public void setSubHeadEntity5(SubHeadEntity5 subHeadEntity5) {
                this.subHeadEntity5 = subHeadEntity5;
            }

            public static class FCreateOrgId {
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

            public static class FUseOrgId {
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

            public static class FMaterialGroup {
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

            public static class SubHeadEntity {
                @JsonProperty("FErpClsID")
                private String fErpClsID;
                @JsonProperty("FBaseUnitId")
                private FBaseUnitId fBaseUnitId;


                @JsonIgnore
                public String getFErpClsID() {
                    return fErpClsID;
                }

                @JsonIgnore
                public void setFErpClsID(String fErpClsID) {
                    this.fErpClsID = fErpClsID;
                }

                @JsonIgnore
                public FBaseUnitId getFBaseUnitId() {
                    return fBaseUnitId;
                }

                @JsonIgnore
                public void setFBaseUnitId(FBaseUnitId fBaseUnitId) {
                    this.fBaseUnitId = fBaseUnitId;
                }


                public static class FBaseUnitId {
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

            public static class SubHeadEntity3 {
                @JsonProperty("FPurchaseOrgId")
                private FPurchaseOrgId fPurchaseOrgId;
                @JsonProperty("FDefaultVendor")
                private FDefaultVendor fDefaultVendor;

                @JsonIgnore
                public FPurchaseOrgId getfPurchaseOrgId() {
                    return fPurchaseOrgId;
                }

                @JsonIgnore
                public void setfPurchaseOrgId(FPurchaseOrgId fPurchaseOrgId) {
                    this.fPurchaseOrgId = fPurchaseOrgId;
                }

                @JsonIgnore
                public FDefaultVendor getfDefaultVendor() {
                    return fDefaultVendor;
                }

                @JsonIgnore
                public void setfDefaultVendor(FDefaultVendor fDefaultVendor) {
                    this.fDefaultVendor = fDefaultVendor;
                }

                public static class FPurchaseOrgId {
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

                public static class FDefaultVendor {
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

            public static class SubHeadEntity4 {
                @JsonProperty("FPlanningStrategy")
                private String fPlanningStrategy;

                @JsonIgnore
                public String getFPlanningStrategy() {
                    return fPlanningStrategy;
                }

                @JsonIgnore
                public void setFPlanningStrategy(String fPlanningStrategy) {
                    this.fPlanningStrategy = fPlanningStrategy;
                }
            }

            public static class SubHeadEntity5 {
                @JsonProperty("FLOSSPERCENT")
                private Integer flosspercent;
                @JsonProperty("FIsKitting")
                private Boolean fIsKitting;
                @JsonProperty("FIsCompleteSet")
                private Boolean fIsCompleteSet;

                @JsonIgnore
                public Integer getFlosspercent() {
                    return flosspercent;
                }

                @JsonIgnore
                public void setFlosspercent(Integer flosspercent) {
                    this.flosspercent = flosspercent;
                }

                @JsonIgnore
                public Boolean getFIsKitting() {
                    return fIsKitting;
                }

                @JsonIgnore
                public void setFIsKitting(Boolean fIsKitting) {
                    this.fIsKitting = fIsKitting;
                }

                @JsonIgnore
                public Boolean getFIsCompleteSet() {
                    return fIsCompleteSet;
                }

                @JsonIgnore
                public void setFIsCompleteSet(Boolean fIsCompleteSet) {
                    this.fIsCompleteSet = fIsCompleteSet;
                }
            }
        }
    }
}