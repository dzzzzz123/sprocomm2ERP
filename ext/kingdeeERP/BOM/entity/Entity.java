package ext.kingdeeERP.BOM.entity;

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
            @JsonProperty("FID")
            private String fID;
            @JsonProperty("FCreateOrgId")
            private FCreateOrgId fCreateOrgId;
            @JsonProperty("FUseOrgId")
            private FUseOrgId fUseOrgId;
            @JsonProperty("FNumber")
            private String fNumber;
            @JsonProperty("FName")
            private String fName;
            @JsonProperty("FBILLTYPE")
            private FBILLTYPE fBillType;
            @JsonProperty("FBOMCATEGORY")
            private String fBomCategory;
            @JsonProperty("FMATERIALID")
            private FMATERIALID fMaterialId;
            @JsonProperty("FNOTE")
            private String fNote;
            @JsonProperty("FTreeEntity")
            private Object[] fTreeEntity;

            @JsonIgnore
            public String getfID() {
                return fID;
            }

            @JsonIgnore
            public void setfID(String fID) {
                this.fID = fID;
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
            public FBILLTYPE getfBillType() {
                return fBillType;
            }

            @JsonIgnore
            public void setfBillType(FBILLTYPE fBillType) {
                this.fBillType = fBillType;
            }

            @JsonIgnore
            public String getfBomCategory() {
                return fBomCategory;
            }

            @JsonIgnore
            public void setfBomCategory(String fBomCategory) {
                this.fBomCategory = fBomCategory;
            }

            @JsonIgnore
            public FMATERIALID getfMaterialId() {
                return fMaterialId;
            }

            @JsonIgnore
            public void setfMaterialId(FMATERIALID fMaterialId) {
                this.fMaterialId = fMaterialId;
            }

            @JsonIgnore
            public String getfNote() {
                return fNote;
            }

            @JsonIgnore
            public void setfNote(String fNote) {
                this.fNote = fNote;
            }

            @JsonIgnore
            public Object[] getfTreeEntity() {
                return fTreeEntity;
            }

            @JsonIgnore
            public void setfTreeEntity(Object[] fTreeEntity) {
                this.fTreeEntity = fTreeEntity;
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


            public static class FBILLTYPE {
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

            public static class FMATERIALID {
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
    }
}