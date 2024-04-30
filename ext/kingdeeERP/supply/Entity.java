package ext.kingdeeERP.supply;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class Entity {

    @JsonProperty("Number")
    private String Number;
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("EngName")
    private String EngName;
    @JsonProperty("AcctNO")
    private String acctNO;
    @JsonProperty("Status")
    private String status;

    @JsonIgnore
    public String getNumber() {
        return Number;
    }

    @JsonIgnore
    public void setNumber(String Number) {
        this.Number = Number;
    }

    @JsonIgnore
    public String getName() {
        return Name;
    }

    @JsonIgnore
    public void setName(String Name) {
        this.Name = Name;
    }

    @JsonIgnore
    public String getEngName() {
        return EngName;
    }

    @JsonIgnore
    public void setEngName(String EngName) {
        this.EngName = EngName;
    }

    public String getAcctNO() {
        return acctNO;
    }

    public void setAcctNO(String acctNO) {
        this.acctNO = acctNO;
    }

    @JsonIgnore
    public String getStatus() {
        return status;
    }

    @JsonIgnore
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "Number='" + Number + '\'' +
                ", Name='" + Name + '\'' +
                ", EngName='" + EngName + '\'' +
                ", acctNO='" + acctNO + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
