package ext.kingdeeERP.viewMaterial;


import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

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
        @JsonProperty("CreateOrgId")
        private String createOrgId;
        @JsonProperty("Number")
        private String number;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("IsSortBySeq")
        private String isSortBySeq;

        @JsonIgnore
        public String getCreateOrgId() {
            return createOrgId;
        }

        @JsonIgnore
        public void setCreateOrgId(String createOrgId) {
            this.createOrgId = createOrgId;
        }

        @JsonIgnore
        public String getNumber() {
            return number;
        }

        @JsonIgnore
        public void setNumber(String number) {
            this.number = number;
        }

        @JsonIgnore
        public String getId() {
            return id;
        }

        @JsonIgnore
        public void setId(String id) {
            this.id = id;
        }

        @JsonIgnore
        public String getIsSortBySeq() {
            return isSortBySeq;
        }

        @JsonIgnore
        public void setIsSortBySeq(String isSortBySeq) {
            this.isSortBySeq = isSortBySeq;
        }
    }
}